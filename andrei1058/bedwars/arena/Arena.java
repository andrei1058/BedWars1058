package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.*;
import com.andrei1058.bedwars.configuration.ConfigManager;
import com.andrei1058.bedwars.configuration.Language;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.util.*;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.configuration.Language.getList;
import static com.andrei1058.bedwars.configuration.Language.getMsg;
import static com.andrei1058.bedwars.configuration.Language.getScoreboard;
import static com.andrei1058.bedwars.listeners.PvP.isOnABase;

public class Arena {
    private static HashMap<String, Arena> arenaByName = new HashMap<>();
    private static HashMap<Player, Arena> arenaByPlayer = new HashMap<>();
    private static ArrayList<Arena> arenas = new ArrayList<>();
    public static HashMap<Player, Integer> respawn = new HashMap<>();


    private List<Player> players = new ArrayList<>();
    private List<Player> spectators = new ArrayList<>();
    private List<BlockState> signs = new ArrayList<>();
    private GameState status = GameState.waiting;
    private YamlConfiguration yml;
    private ConfigManager cm;
    private int minPlayers = 2, maxPlayers = 10, countdownS, slot = -1, maxInTeam = 1, countUp = 0, restarting = 12, islandRadius = 10;
    public static int upgradeDiamondsCount = 0, upgradeEmeraldsCount = 0;
    private boolean allowSpectate = true;
    private World world;
    private String group = "DEFAULT";
    private List<BedWarsTeam> teams = new ArrayList<>();
    private List<Block> placed = new ArrayList<>();
    private HashMap<Block, BlockState> broken = new HashMap<>();
    private List<SBoard> scoreboards = new ArrayList<>();
    /** Bed block */
    private Material bedBlock = Material.BED_BLOCK;

    /*Various stuff about player's inventory and owns*/
    private static HashMap<Player, HashMap<ItemStack, Integer>> playerItems = new HashMap<>();
    private static HashMap<Player, Location> playerLocation = new HashMap<>();
    private static HashMap<Player, List<PotionEffect>> playerPotions = new HashMap<>();
    private static HashMap<Player, Float> playerExp = new HashMap<>();
    private static HashMap<Player, ItemStack[]> playerArmor = new HashMap<>();
    private static HashMap<String, Integer> playerKills = new HashMap<>();
    private static HashMap<Player, Integer> playerBedsDestroyed = new HashMap<>();
    private static HashMap<String, Integer> playerFinalKills = new HashMap<>();
    private static HashMap<Player, HashMap<ItemStack, Integer>> playerEnderchest = new HashMap<>();
    /*End of player's stuff*/

    public Arena(String name) {
        cm = new ConfigManager(name, "plugins/" + plugin.getName() + "/Arenas", true);
        yml = cm.getYml();
        if (yml.get("Team") == null) {
            plugin.getLogger().severe("You didn't set any team for arena: " + name);
            return;
        }
        if (yml.getConfigurationSection("Team").getKeys(false).size() < 2) {
            plugin.getLogger().severe("You must set at least 2 teams on: " + name);
            return;
        }
        maxInTeam = yml.getInt("maxInTeam");
        maxPlayers = yml.getConfigurationSection("Team").getKeys(false).size() * maxInTeam;
        minPlayers = yml.getInt("minPlayers");
        allowSpectate = yml.getBoolean("allowSpectate");
        countdownS = config.getYml().getInt("startingCountdown");
        islandRadius = yml.getInt("islandRadius");
        if (config.getYml().get("arenaGroups") != null) {
            if (config.getYml().getStringList("arenaGroups").contains(yml.getString("group"))) {
                group = yml.getString("group");
            }
        }
        if (new File(plugin.getServer().getWorldContainer().getPath() + "/" + name) == null) {
            plugin.getLogger().severe("There isn't any map called " + name);
            return;
        }
        boolean error = false;
        for (String team : yml.getConfigurationSection("Team").getKeys(false)) {
            if (TeamColor.valueOf(yml.getString("Team." + team + ".Color")) == null) {
                plugin.getLogger().severe("Invalid color at team: " + team + " in arena: " + name);
                error = true;
            }
            for (String stuff : Arrays.asList("Color", "Spawn", "Bed", "Shop", "Upgrade", "Iron", "Gold")) {
                if (yml.get("Team." + team + "." + stuff) == null) {
                    plugin.getLogger().severe(stuff + " not set for " + team + " team on: " + name);
                    error = true;
                }
            }
        }
        if (yml.get("generator.Diamond") == null) {
            plugin.getLogger().severe("There isn't set any Diamond generator on: " + name);
        } else {
        }
        if (yml.get("generator.Emerald") == null) {
            plugin.getLogger().severe("There isn't set any Emerald generator on: " + name);
        } else {
        }
        if (yml.get("waiting.Loc") == null) {
            plugin.getLogger().severe("Waiting spawn not set on: " + name);
            return;
        }
        if (error) return;
        try {
            world = Bukkit.createWorld(new WorldCreator(name));
        } catch (Exception ex) {
            plugin.getLogger().severe("I can't load the map called " + name);
            ex.printStackTrace();
            return;
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> world.getEntities().stream().filter(e -> e.getType() != EntityType.PLAYER).filter(e -> e.getType() != EntityType.PAINTING).filter(e -> e.getType() != EntityType.ITEM_FRAME).forEach(Entity::remove), 30L);
        world.setGameRuleValue("doMobSpawning", "false");
        world.setAutoSave(false);
        for (String team : yml.getConfigurationSection("Team").getKeys(false)) {
            teams.add(new BedWarsTeam(team, TeamColor.valueOf(yml.getString("Team." + team + ".Color").toUpperCase()),
                    cm.getArenaLoc("Team." + team + ".Spawn"), cm.getArenaLoc("Team." + team + ".Bed"), cm.getArenaLoc("Team." + team + ".Shop"),
                    cm.getArenaLoc("Team." + team + ".Upgrade"), this));
        }

        arenas.add(this);
        arenaByName.put(world.getName(), this);
        if (yml.get("bedBlock") != null){
            try {
                Material.valueOf(yml.getString("bedBlock"));
                bedBlock = Material.valueOf(yml.getString("bedBlock"));
            } catch (Exception ex){
                plugin.getLogger().severe(yml.getString("bedBlock")+" is not a Material at "+getWorldName()+".yml");
            }
        }
    }

    public void addPlayer(Player p) {
        /* used for base enter/leave event */
        if (isOnABase.containsKey(p)){
            isOnABase.remove(p);
        }
        //
        if (getArenaByPlayer(p) != null) {
            p.sendMessage(getMsg(p, Language.youreInGame));
            return;
        }
        if (getParty().hasParty(p)) {
            if (!getParty().isOwner(p)) {
                p.sendMessage(getMsg(p, Language.leaderChoose));
                return;
            }
            if (getParty().partySize(p) > maxInTeam) {
                p.sendMessage(getMsg(p, Language.partyTooBig));
                return;
            }
        }
        if (status == GameState.waiting || (status == GameState.starting && countdownS > 2)) {
            if (players.size() >= maxPlayers && !isVip(p)) {
                TextComponent text = new TextComponent(getMsg(p, Language.fullArena));
                text.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, config.getYml().getString("storeLink")));
                p.spigot().sendMessage(text);
                return;
            } else if (players.size() >= maxPlayers && isVip(p)) {
                boolean canJoin = false;
                for (Player on : players) {
                    if (!isVip(on)) {
                        canJoin = true;
                        removePlayer(on);
                        TextComponent vipKick = new TextComponent(getMsg(p, Language.vipJoinedSlot));
                        vipKick.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, config.getYml().getString("storeLink")));
                        p.spigot().sendMessage(vipKick);
                    }
                }
                if (!canJoin) {
                    p.sendMessage(getMsg(p, Language.vipNoJoin));
                    return;
                }
            }
            players.add(p);
            for (Player on : players) {
                on.sendMessage(getMsg(on, lang.playerJoin).replace("{player}", p.getDisplayName()).replace("{on}", String.valueOf(getPlayers().size())).replace("{max}", String.valueOf(getMaxPlayers())));
            }
            setArenaByPlayer(p, false);
            if (getStatus() == GameState.waiting) {
                scoreboards.add(new SBoard(p, getScoreboard(p, "scoreboard." + getGroup() + "Waiting", lang.scoreboardDefaultWaiting), this));
            } else if (getStatus() == GameState.starting) {
                scoreboards.add(new SBoard(p, getScoreboard(p, "scoreboard." + getGroup() + "Starting", lang.scoreboardDefaultStarting), this));
            }
            if (players.size() >= minPlayers && status == GameState.waiting) {
                setStatus(GameState.starting);
            }
            HashMap<ItemStack, Integer> items = new HashMap<>();
            int x = 0;
            for (ItemStack i : p.getInventory()) {
                if (i != null) {
                    if (i.getType() != Material.AIR) {
                        items.put(i, x);
                    }
                }
                x++;
            }
            playerItems.put(p, items);
            playerArmor.put(p, p.getInventory().getArmorContents());
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            playerLocation.put(p, p.getLocation());
            p.teleport(cm.getArenaLoc("waiting.Loc"));
            leaveItem(p);
            List<PotionEffect> potions = new ArrayList<>();
            for (PotionEffect ef : p.getActivePotionEffects()) {
                potions.add(ef);
                p.removePotionEffect(ef.getType());
            }
            playerPotions.put(p, potions);
            playerExp.put(p, p.getExp());
            p.setExp(0);

            HashMap<ItemStack, Integer> items2 = new HashMap<>();
            int x2 = 0;
            for (ItemStack i : p.getEnderChest()) {
                if (i != null) {
                    if (i.getType() != Material.AIR) {
                        items2.put(i, x2);
                    }
                }
                x2++;
            }
            playerEnderchest.put(p, items2);
            p.getEnderChest().clear();
        } else if (status == GameState.playing) {
            addSpectator(p, false);
        }
        for (Player on : Bukkit.getOnlinePlayers()) {
            if (getPlayers().contains(on)) {
                on.showPlayer(p);
                p.showPlayer(on);
            } else {
                on.hidePlayer(p);
                p.hidePlayer(p);
            }
        }
        p.setAllowFlight(false);
        p.setFlying(false);
    }

    public void addSpectator(Player p, boolean playerBefore) {
        if (allowSpectate || playerBefore) {
            spectators.add(p);
            players.remove(p);
            nms.setCollidable(p, false);
            //nms.hideEntity(players, p);
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            if (!playerBefore) {
                setArenaByPlayer(p, true);
                HashMap<ItemStack, Integer> items = new HashMap<>();
                int x = 0;
                for (ItemStack i : p.getInventory()) {
                    if (i != null) {
                        if (i.getType() != Material.AIR) {
                            items.put(i, x);
                        }
                    }
                    x++;
                }
                playerItems.put(p, items);
                playerArmor.put(p, p.getInventory().getArmorContents());
                p.getInventory().clear();
                p.getInventory().setArmorContents(null);
                playerLocation.put(p, p.getLocation());
                List<PotionEffect> potions = new ArrayList<>();
                for (PotionEffect ef : p.getActivePotionEffects()) {
                    potions.add(ef);
                    p.removePotionEffect(ef.getType());
                }
                playerPotions.put(p, potions);
                playerExp.put(p, p.getExp());
                p.setExp(0);
            }
            p.setAllowFlight(true);
            p.setFlying(true);
            p.teleport(cm.getArenaLoc("waiting.Loc"));

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                for (Player on : Bukkit.getOnlinePlayers()) {
                    if (getSpectators().contains(on)) {
                        on.showPlayer(p);
                        p.showPlayer(on);
                    } else if (getPlayers().contains(on)) {
                        on.hidePlayer(p);
                        p.showPlayer(on);
                    }
                }
            }, 10L);

            leaveItem(p);
        } else {
            //msg spectate not allowed
        }
    }

    public void removePlayer(Player p) {
        if (getStatus() == GameState.playing) {
            for (BedWarsTeam t : getTeams()) {
                if (t.isMember(p)) {
                    t.getMembers().remove(p);
                    if (t.getBedHolo(p) != null) {
                        t.getBedHolo(p).destroy();
                    }
                }
            }
        }
        players.remove(p);
        removeArenaByPlayer(p);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        for (PotionEffect pf : p.getActivePotionEffects()) {
            p.removePotionEffect(pf.getType());
        }
        p.setExp(0);
        if (playerItems.containsKey(p)) {
            for (Map.Entry<ItemStack, Integer> entry : playerItems.get(p).entrySet()) {
                p.getInventory().setItem(entry.getValue(), entry.getKey());
            }
            p.updateInventory();
            playerItems.remove(p);
        }

        for (Player on : players) {
            on.sendMessage(getMsg(p, Language.playerLeft).replace("{player}", p.getName()));
        }
        if (playerItems.containsKey(p)) {
            for (Map.Entry<ItemStack, Integer> entry : playerItems.get(p).entrySet()) {
                p.getInventory().setItem(entry.getValue(), entry.getKey());
            }
        }
        p.updateInventory();
        playerItems.remove(p);

        playerItems.remove(p);
        p.getInventory().setArmorContents(playerArmor.get(p));
        playerArmor.remove(0);
        for (PotionEffect pe : playerPotions.get(p)) {
            p.addPotionEffect(pe);
        }
        playerPotions.remove(p);
        p.setExp(playerExp.get(p));
        playerExp.remove(p);
        if (getServerType() == ServerType.SHARED) {
            p.teleport(playerLocation.get(p));
        } else if (getServerType() == ServerType.BUNGEE) {
            Misc.moveToLobbyOrKick(p);
        } else {
            p.teleport(config.getConfigLoc("lobbyLoc"));
        }
        playerLocation.remove(p);
        if (playerEnderchest.containsKey(p)) {
            p.getEnderChest().clear();
            for (Map.Entry<ItemStack, Integer> entry : playerEnderchest.get(p).entrySet()) {
                p.getEnderChest().setItem(entry.getValue(), entry.getKey());
            }
            playerEnderchest.remove(p);
        }
        if (respawn.containsKey(p)) {
            respawn.remove(p);
            BedWarsTeam t = this.getTeam(p);
            if (t != null) {
                if (t.getMembers().isEmpty()) {
                    for (Player p2 : this.getPlayers()) {
                        p2.sendMessage(getMsg(p2, lang.teamEliminatedChat).replace("{TeamColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{TeamName}", t.getName()));
                    }
                    for (Player p2 : this.getSpectators()) {
                        p2.sendMessage(getMsg(p2, lang.teamEliminatedChat).replace("{TeamColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{TeamName}", t.getName()));
                    }
                }
            }
        }
        if (status == GameState.starting && players.size() < minPlayers) {
            setStatus(GameState.waiting);
            for (Player on : players) {
                on.sendMessage(getMsg(p, Language.insufficientPlayers));
            }
        } else if (status == GameState.playing && players.size() <= 1) {
            checkWinner();
            setStatus(GameState.restarting);
        } else if (status == GameState.playing) {
            int alive_teams = 0;
            for (BedWarsTeam t : getTeams()) {
                if (t.getSize() != 0) {
                    alive_teams++;
                    if (t.getMembers().contains(p)) {
                        t.getMembers().remove(p);
                    }
                }
            }
            if (alive_teams == 1) {
                checkWinner();
                setStatus(GameState.restarting);
            } else if (alive_teams == 0) {
                setStatus(GameState.restarting);
            }
        }
        if (status == GameState.starting || status == GameState.waiting) {
            for (Player on : players) {
                on.sendMessage(getMsg(on, lang.playerLeave).replace("{player}", p.getDisplayName()));
            }
        }
        for (SBoard sb : scoreboards) {
            if (sb.getP() == p) {
                sb.remove();
            }
        }
        p.setPlayerListName(p.getName());
        for (Player on : Bukkit.getOnlinePlayers()) {
            if (getArenaByPlayer(on) == null) {
                on.showPlayer(p);
                p.showPlayer(on);
            } else {
                p.hidePlayer(on);
                on.hidePlayer(p);
            }
        }
    }

    public void removeSpectator(Player p) {
        spectators.remove(p);
        removeArenaByPlayer(p);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        for (PotionEffect pf : p.getActivePotionEffects()) {
            p.removePotionEffect(pf.getType());
        }
        p.setExp(0);
        p.setFlying(false);
        p.setAllowFlight(false);
        if (getServerType() == ServerType.SHARED) {
            p.teleport(playerLocation.get(p));
        } else if (getServerType() == ServerType.MULTIARENA) {
            p.teleport(config.getConfigLoc("lobbyLoc"));
        }
        if (playerItems.containsKey(p)) {
            for (Map.Entry<ItemStack, Integer> entry : playerItems.get(p).entrySet()) {
                p.getInventory().setItem(entry.getValue(), entry.getKey());
            }
            p.updateInventory();
            playerItems.remove(p);
        }
        playerItems.remove(p);
        p.getInventory().setArmorContents(playerArmor.get(p));
        playerArmor.remove(0);
        for (PotionEffect pe : playerPotions.get(p)) {
            p.addPotionEffect(pe);
        }
        playerPotions.remove(p);
        p.setExp(playerExp.get(p));
        playerExp.remove(p);
        if (getServerType() == ServerType.BUNGEE) {
            Misc.moveToLobbyOrKick(p);
        }
        if (playerEnderchest.containsKey(p)) {
            p.getEnderChest().clear();
            for (Map.Entry<ItemStack, Integer> entry : playerEnderchest.get(p).entrySet()) {
                p.getEnderChest().setItem(entry.getValue(), entry.getKey());
            }
            playerEnderchest.remove(p);
        }
        playerLocation.remove(p);
        nms.setCollidable(p, true);
        for (SBoard sb : scoreboards) {
            if (sb.getP() == p) {
                sb.remove();
            }
        }
        p.setPlayerListName(p.getName());
        for (Player on : Bukkit.getOnlinePlayers()) {
            if (getArenaByPlayer(on) == null) {
                on.showPlayer(p);
                p.showPlayer(on);
            } else {
                on.hidePlayer(p);
                p.hidePlayer(on);
            }
        }
    }

    public void disable() {
        if (world == null) {
            arenas.remove(this);
            return;
        }
        plugin.getLogger().info("Disabling arena: " + getDisplayName());
        for (Player on : players) {
            removePlayer(on);
        }
        for (Player on : spectators) {
            removeSpectator(on);
        }
        for (Block b : placed) {
            b.setType(Material.AIR);
        }
        placed.clear();
        for (Map.Entry<Block, BlockState> e : broken.entrySet()) {
            e.getKey().setType(e.getValue().getType());
            e.getKey().setData(e.getValue().getData().getData()); //todo nu o sa mearga pe 1.13
            e.getKey().getState().update();
        }
        broken.clear();
        players.clear();
        spectators.clear();
        for (Entity e : world.getEntities()) {
            if (e.getType() == EntityType.PLAYER) {
                Player p = (Player) e;
                p.kickPlayer(getMsg(p, Language.restartKick));
            }
        }
        Bukkit.unloadWorld(world, false);
        Arena a = arenaByName.get(world.getName());
        arenaByName.remove(world.getName());
        arenas.remove(a);
    }

    private void restart() {
        upgradeDiamondsCount = 0;
        upgradeEmeraldsCount = 0;
        try {
            for (Player on : players) {
                removePlayer(on);
            }
            for (Player on : spectators) {
                removeSpectator(on);
            }
        } catch (Exception ex) {
        }
        players.clear();
        spectators.clear();
        for (Entity e : world.getEntities()) {
            if (e.getType() == EntityType.PLAYER) {
                Player p = (Player) e;
                p.kickPlayer(getMsg(p, Language.restartKick));
            }
        }
        countUp = 0;
        OreGenerator.removeIfArena(this);
        if (getServerType() == ServerType.BUNGEE) {
            Bukkit.getServer().spigot().restart();
            //todo if true in config
        }
        String name = world.getName();
        Bukkit.unloadWorld(world, false);
        world = Bukkit.createWorld(new WorldCreator(name));
        teams.clear();
        this.setStatus(GameState.waiting);
        countdownS = config.getYml().getInt("startingCountdown");
        restarting = 12;
        for (String team : yml.getConfigurationSection("Team").getKeys(false)) {
            teams.add(new BedWarsTeam(team, TeamColor.valueOf(yml.getString("Team." + team + ".Color").toUpperCase()),
                    cm.getArenaLoc("Team." + team + ".Spawn"), cm.getArenaLoc("Team." + team + ".Bed"), cm.getArenaLoc("Team." + team + ".Shop"),
                    cm.getArenaLoc("Team." + team + ".Upgrade"), this));
        }
        world.setAutoSave(false);
        playerKills.clear();
        playerBedsDestroyed.clear();
        playerFinalKills.clear();
        System.gc();
    }

    public void refresh() {
        if (status == GameState.waiting) return;
        switch (status) {
            case starting:
                if (countdownS == 0) {
                    List<Player> skip = new ArrayList<>(), owners = new ArrayList<>();
                    for (Player p : getPlayers()) {
                        if (getParty().hasParty(p) && getParty().isOwner(p)) {
                            owners.add(p);
                        }
                    }
                    for (Player p2 : owners) {
                        getParty().getMembers(p2).stream().forEach(p3 -> skip.add(p3));
                    }
                    for (Player p : getPlayers()) {
                        if (owners.contains(p)) {
                            for (BedWarsTeam t : getTeams()) {
                                if (t.getSize() + getParty().partySize(p) <= maxInTeam) {
                                    t.addPlayers((Player) getParty().getMembers(p));
                                }
                            }
                        } else if (skip.contains(p)) continue;
                        BedWarsTeam addhere = getTeams().get(0);
                        for (BedWarsTeam t : getTeams()) {
                            if (t.getMembers().size() < maxInTeam && t.getMembers().size() < addhere.getMembers().size()) {
                                addhere = t;
                            }
                        }
                        addhere.addPlayers(p);
                        p.setGameMode(GameMode.SURVIVAL);
                        for (String s : Language.getList(p, lang.gameTutorialStrings)) {
                            p.sendMessage(s);
                        }
                    }
                    setStatus(GameState.playing);
                    for (BedWarsTeam team : getTeams()) {
                        team.setGenerators(cm.getArenaLoc("Team." + team.getName() + ".Iron"), cm.getArenaLoc("Team." + team.getName() + ".Gold"));
                        team.getBed().getBlock().setType(Material.AIR);
                        if (getBedBlock() == Material.BED_BLOCK){

                            if (Misc.getDirection(team.getBed()) == BlockFace.WEST || Misc.getDirection(team.getBed()) == BlockFace.EAST){
                                BlockState baseState = team.getBed().getBlock().getState();
                                BlockState localBlockState = team.getBed().add(-1, 0, 0).getBlock().getState();


                                baseState.setType(Material.BED_BLOCK);
                                localBlockState.setType(Material.BED_BLOCK);

                                baseState.setRawData((byte) 0x05);
                                localBlockState.setRawData((byte) 0x09);

                                baseState.update(true, false);
                                localBlockState.update(true, false);
                            } else {
                                BlockState baseState = team.getBed().getBlock().getState();
                                BlockState localBlockState = team.getBed().add(0, 0, -1).getBlock().getState();


                                baseState.setType(Material.BED_BLOCK);
                                localBlockState.setType(Material.BED_BLOCK);

                                baseState.setRawData((byte) 0x08);
                                localBlockState.setRawData((byte) 0x00);

                                baseState.update(true, false);
                                localBlockState.update(true, false);
                            }
                            for (Entity e : team.getArena().getWorld().getNearbyEntities(team.getBed(), 2, 2, 2)) {
                                if (e.getType() == EntityType.DROPPED_ITEM) {
                                    e.remove();
                                }
                            }
                        } else {
                            team.getBed().getBlock().setType(getBedBlock());
                        }
                    }
                    for (String type : Arrays.asList("Diamond", "Emerald")) {
                        if (yml.get("generator." + type) != null) {
                            for (String s : yml.getStringList("generator." + type)) {
                                new OreGenerator(cm.fromArenaStringList(s), this, GeneratorType.valueOf(type.toUpperCase()));
                            }
                        }
                    }
                    if (!(yml.get("waiting.Pos1") == null && yml.get("waiting.Pos2") == null)) {
                        Location loc1 = cm.getArenaLoc("waiting.Pos1"), loc2 = cm.getArenaLoc("waiting.Pos2");
                        int minX = Math.min(loc1.getBlockX(), loc2.getBlockX()), maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
                        int minY = Math.min(loc1.getBlockY(), loc2.getBlockY()), maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
                        int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ()), maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
                        for (int x = minX; x < maxX; x++) {
                            for (int y = minY; y < maxY; y++) {
                                for (int z = minZ; z < maxZ; z++) {
                                    Block b = new Location(world, x, y, z).getBlock();
                                    if (b.getType() != Material.AIR) {
                                        broken.put(b, b.getState());
                                        b.setType(Material.AIR);
                                    }
                                }
                            }
                        }
                    }
                    for (BedWarsTeam t : getTeams()) {
                        if (t.getSize() == 0) {
                            t.setBedDestroyed(true);
                            continue;
                        }
                        if (maxInTeam > 1) {
                            nms.spawnShop(t.getTeamUpgrades(), lang.teamUpgradesName, getPlayers());
                            nms.spawnShop(t.getShop(), lang.shopTeamName, getPlayers());
                        } else {
                            nms.spawnShop(t.getTeamUpgrades(), lang.soloUpgradesName, getPlayers());
                            nms.spawnShop(t.getShop(), lang.shopSoloName, getPlayers());
                        }
                    }
                    return;
                }
                if (countdownS % 10 == 0 || countdownS <= 5) {
                    for (Player p : getPlayers()) {
                        p.playSound(p.getLocation(), nms.countdownTick(), 1f, 1f);
                        if (countdownS >= 10) {
                            nms.sendTitle(p, "§a" + countdownS, null, 0, 20, 0);
                            p.sendMessage(getMsg(p, lang.arenaStartsIn).replace("{time}", String.valueOf(countdownS)));
                        } else if (countdownS > 3) {
                            nms.sendTitle(p, "§e" + countdownS, null, 0, 20, 0);
                            p.sendMessage(getMsg(p, lang.arenaStartsIn).replace("{time}", "§c" + String.valueOf(countdownS)));
                        } else {
                            nms.sendTitle(p, "§c" + countdownS, null, 0, 20, 0);
                            p.sendMessage(getMsg(p, lang.arenaStartsIn).replace("{time}", "§c" + String.valueOf(countdownS)));
                        }
                    }
                }
                countdownS--;
                break;
            case playing:
                upgradeDiamondsCount--;
                upgradeEmeraldsCount--;
                if (upgradeEmeraldsCount == 0) {
                    for (OreGenerator o : OreGenerator.getGenerators()) {
                        if (o.getArena() == this) {
                            if (o.getOre().getType() == Material.EMERALD) {
                                o.upgrade();
                            }
                        }
                    }
                }
                if (upgradeDiamondsCount == 0) {
                    for (OreGenerator o : OreGenerator.getGenerators()) {
                        if (o.getArena() == this) {
                            if (o.getOre().getType() == Material.DIAMOND) {
                                o.upgrade();
                            }
                        }
                    }
                }
                if (countUp == 1) {
                    for (Player p : getPlayers()) {
                        nms.sendTitle(p, getMsg(p, lang.titleStart), null, 0, 20, 0);
                    }
                    for (Player p : getWorld().getPlayers()) {
                        p.setHealthScale(20);
                        p.setHealth(18);
                    }
                }
                countUp++;
                int distance = 0;
                for (BedWarsTeam t : getTeams()) {
                    if (t.getSize() > 1) {
                        for (Player p : t.getMembers()) {
                            for (Player p2 : t.getMembers()) {
                                if (p2 == p) continue;
                                if (distance == 0) {
                                    distance = (int) p.getLocation().distance(p2.getLocation());
                                } else if ((int) p.getLocation().distance(p2.getLocation()) < distance) {
                                    distance = (int) p.getLocation().distance(p2.getLocation());
                                }
                            }
                            nms.playAction(p, getMsg(p, lang.actionBarTracking).replace("{team}", t.getColor() + t.getName())
                                    .replace("{distance}", t.getColor().toString() + distance).replace("&", "§"));
                        }
                    }
                }
                break;
            case restarting:
                restarting--;
                if (restarting == 2) {
                    for (Player p : world.getPlayers()) {
                        if (isSpectator(p)) {
                            removeSpectator(p);
                        } else if (isPlayer(p)) {
                            removePlayer(p);
                        }
                    }
                }
                if (restarting == 0) {
                    restart();
                }
                break;
        }
    }

    //GETTER METHODS


    public World getWorld() {
        return world;
    }

    public int getMaxInTeam() {
        return maxInTeam;
    }

    public static Arena getArenaByName(String name) {
        return arenaByName.get(name);
    }

    public static Arena getArenaByPlayer(Player p) {
        return arenaByPlayer.get(p);
    }

    @Contract(pure = true)
    public static ArrayList<Arena> getArenas() {
        return arenas;
    }

    public String getDisplayStatus() {
        String s = "";
        switch (status) {
            case waiting:
                s = getMsg(null, Language.statusWaiting);
                break;
            case starting:
                s = getMsg(null, Language.statusStarting);
                break;
            case restarting:
                s = getMsg(null, Language.statusRestarting);
                break;
            case playing:
                s = getMsg(null, Language.statusPlaying);
                break;
        }
        return s;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getDisplayName() {
        return world.getName().replace("_", " ").replace("-", " ");
    }

    public String getGroup() {
        return group;
    }

    public int getSlot() {
        return slot;
    }

    public String getWorldName() {
        return world.getName();
    }

    public int getCountUp() {
        return countUp;
    }

    public List<BedWarsTeam> getTeams() {
        return teams;
    }

    public ConfigManager getCm() {
        return cm;
    }

    public List<Block> getPlaced() {
        return placed;
    }

    public int getCountdownS() {
        return countdownS;
    }

    public int getPlayerKills(Player p, boolean finalKills) {
        if (finalKills) {
            if (playerFinalKills.containsKey(p.getName())) return playerFinalKills.get(p.getName());
            return 0;
        }
        if (playerKills.containsKey(p.getName())) return playerKills.get(p.getName());
        return 0;
    }

    public int getPlayerBedsDestroyed(Player p) {
        if (playerBedsDestroyed.containsKey(p)) return playerBedsDestroyed.get(p);
        return 0;
    }

    public Material getBedBlock() {
        return bedBlock;
    }

    public List<BlockState> getSigns() {
        return signs;
    }

    public int getIslandRadius() {
        return islandRadius;
    }

    //SETTER METHODS
    public void setGroup(String group) {
        this.group = group;
    }

    private void setArenaByPlayer(Player p, boolean spectator) {
        arenaByPlayer.put(p, this);
        Bukkit.getPluginManager().callEvent(new PlayerJoinArenaEvent(p, spectator));
        refreshSigns();
    }

    private void removeArenaByPlayer(Player p) {
        arenaByPlayer.remove(p, this);
        Bukkit.getPluginManager().callEvent(new PlayerLeaveArenaEvent(p, isSpectator(p)));
        refreshSigns();
    }

    public void setStatus(GameState status) {
        this.status = status;
        Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(this, status));
        refreshSigns();
        if (status == GameState.starting) {
            for (SBoard sb : scoreboards) {
                sb.setStrings(getScoreboard(sb.getP(), "scoreboard." + getGroup() + "Starting", lang.scoreboardDefaultStarting));
            }
        } else if (status == GameState.waiting) {
            countdownS = config.getYml().getInt("startingCountdown");
            for (SBoard sb : scoreboards) {
                sb.setStrings(getScoreboard(sb.getP(), "scoreboard." + getGroup() + "Waiting", lang.scoreboardDefaultWaiting));
            }
        } else if (status == GameState.playing) {
            for (SBoard sb : scoreboards) {
                sb.setStrings(getScoreboard(sb.getP(), "scoreboard." + getGroup() + "Playing", lang.scoreboardDefaultPlaying));
                sb.addHealthSbAndTabStuff();
            }
        }
    }

    public static boolean isVip(Player p) {
        return p.hasPermission(mainCmd + ".*") || p.hasPermission(mainCmd + ".vip");
    }

    public boolean isPlayer(Player p) {
        return players.contains(p);
    }

    public boolean isSpectator(Player p) {
        return spectators.contains(p);
    }

    public void addSign(Location loc) {
        if (loc.getBlock().getType() == Material.SIGN || loc.getBlock().getType() == Material.SIGN_POST || loc.getBlock().getType() == Material.WALL_SIGN) {
            signs.add(loc.getBlock().getState());
            refreshSigns();
        }
    }

    public GameState getStatus() {
        return status;
    }

    public void refreshSigns() {
        for (BlockState b : signs) {
            Sign s = (Sign) b;
            int line = 0;
            for (String string : Main.signs.l("format")) {
                s.setLine(line, string.replace("[on]", String.valueOf(getPlayers().size())).replace("[max]", String.valueOf(getMaxPlayers())).replace("[arena]", getDisplayName()).replace("[status]", getDisplayStatus()));
                line++;
            }
            b.update(true);
        }
    }

    public List<Player> getSpectators() {
        return spectators;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void addPlayerKill(Player p, boolean finalKill) {
        if (p == null) return;
        if (playerKills.containsKey(p.getName())) {
            playerKills.replace(p.getName(), playerKills.get(p.getName()) + 1);
        } else {
            playerKills.put(p.getName(), 1);
        }
        if (finalKill) {
            if (playerFinalKills.containsKey(p.getName())) {
                playerFinalKills.replace(p.getName(), playerFinalKills.get(p.getName()) + 1);
            } else {
                playerFinalKills.put(p.getName(), 1);
            }
        }
    }

    public void addPlayerBedDestroyed(Player p) {
        if (playerBedsDestroyed.containsKey(p)) {
            playerBedsDestroyed.replace(p, playerBedsDestroyed.get(p) + 1);
            return;
        }
        playerBedsDestroyed.put(p, 1);
    }

    public static boolean joinRandomFromGroup(Player p, String group) {
        for (Arena a : getArenas()) {
            if (a.getGroup().equalsIgnoreCase(group)) {
                if (a.getStatus() == GameState.waiting) {
                    a.addPlayer(p);
                    return true;
                } else if (a.getStatus() == GameState.starting) {
                    if (a.getPlayers().size() < a.getMaxPlayers()) {
                        a.addPlayer(p);
                        return true;
                    } else if (a.getPlayers().size() <= a.getMaxPlayers() && a.isVip(p)){
                        a.addPlayer(p);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void sendMultiarenaLobbyItems(Player p) {
        p.getInventory().clear();
        if (config.getBoolean("items.arenaGui.enable") && !config.getLobbyWorldName().isEmpty()) {
            p.getInventory().setItem(config.getInt("items.arenaGui.slot"), Misc.getArenaGUI(p));
        }
    }

    @Contract(pure = true)
    public static boolean isInArena(Player p) {
        return arenaByPlayer.containsKey(p);
    }

    public BedWarsTeam getTeam(Player p) {
        for (BedWarsTeam t : getTeams()) {
            if (t.isMember(p)) {
                return t;
            }
        }
        return null;
    }

    public static void leaveItem(Player p) {
        if (config.getBoolean("items.leave.enable")) {
            p.getInventory().setItem(config.getInt("items.leave.slot"), Misc.createItem(Material.valueOf(config.getYml().getString("items.leave.itemStack")),
                    (byte) config.getInt("items.leave.data"), getMsg(p, lang.leaveItemName), getList(p, lang.leaveItemLore)));
        }
    }

    public void checkWinner() {
        if (getStatus() != GameState.restarting) {
            int max = getTeams().size(), eliminated = 0;
            BedWarsTeam winner = null;
            for (BedWarsTeam t : getTeams()) {
                if (t.getMembers().isEmpty()) {
                    eliminated++;
                } else {
                    winner = t;
                }
            }
            if (max - eliminated == 1) {
                setStatus(GameState.restarting);
                if (winner != null) {
                    String firstName = "", secondName = "", thirdName = "", winners = "";
                    for (Player p : winner.getMembers()) {
                        nms.sendTitle(p, getMsg(p, lang.victoryTitle), null, 0, 40, 0);
                        winners = winners + p.getName() + " ";
                    }
                    if (winners.endsWith(" ")) {
                        winners = winners.substring(0, winners.length() - 1);
                    }
                    int first = 0, second = 0, third = 0;
                    if (!playerKills.isEmpty()) {
                        for (Map.Entry<String, Integer> e : playerKills.entrySet()) {
                            if (e.getKey() == null) continue;
                            if (e.getValue() > first) {
                                firstName = e.getKey();
                                first = e.getValue();
                            } else if (e.getValue() > second) {
                                secondName = e.getKey();
                                second = e.getValue();
                            } else if (e.getValue() > third) {
                                thirdName = e.getKey();
                                third = e.getValue();
                            }
                        }
                    }
                    for (Player p : world.getPlayers()) {
                        if (winner.getMembers().contains(p)) {
                            p.sendMessage(getMsg(p, lang.teamWonChat).replace("{TeamColor}", TeamColor.getChatColor(winner.getColor()).toString()).replace("{TeamName}", winner.getName()));
                        } else {
                            nms.sendTitle(p, getMsg(p, lang.gameOverTitle), null, 0, 40, 0);
                        }
                        for (String s : getList(p, lang.gameEndStats)) {
                            p.sendMessage(s.replace("{firstName}", firstName.isEmpty() ? getMsg(p, lang.nobody) : firstName).replace("{firstKills}", String.valueOf(first))
                                    .replace("{secondName}", secondName.isEmpty() ? getMsg(p, lang.nobody) : secondName).replace("{secondKills}", String.valueOf(second))
                                    .replace("{thirdName}", thirdName.isEmpty() ? getMsg(p, lang.nobody) : thirdName).replace("{thirdKills}", String.valueOf(third))
                                    .replace("{winnerFormat}", getMaxInTeam() > 1 ? getMsg(p, lang.winnerFormatTeam).replace("{members}", winners) : getMsg(p, lang.winnerFormatSolo).replace("{members}", winners))
                                    .replace("{TeamColor}", TeamColor.getChatColor(winner.getColor()).toString()).replace("{TeamName}", winner.getName()));
                        }
                    }
                }
            }
            if (players.size() == 0 && getStatus() != GameState.restarting) {
                setStatus(GameState.restarting);
            }
        }
    }
}
