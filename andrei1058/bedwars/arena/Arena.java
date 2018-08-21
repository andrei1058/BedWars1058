package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.*;
import com.andrei1058.bedwars.arena.spectator.SpectateItems;
import com.andrei1058.bedwars.configuration.ConfigManager;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.Messages;
import com.andrei1058.bedwars.support.nte.NametagEdit;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.sql.Timestamp;
import java.util.*;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.arena.upgrades.BaseEnterListener.isOnABase;
import static com.andrei1058.bedwars.configuration.Language.*;

public class Arena {
    private static HashMap<String, Arena> arenaByName = new HashMap<>();
    private static HashMap<Player, Arena> arenaByPlayer = new HashMap<>();
    private static ArrayList<Arena> arenas = new ArrayList<>();
    public static HashMap<Player, Integer> respawn = new HashMap<>();
    private static int gamesBeforeRestart = config.getInt(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_MODE_GAMES_BEFORE_RESTART);


    private List<Player> players = new ArrayList<>();
    private List<Player> spectators = new ArrayList<>();
    private List<BlockState> signs = new ArrayList<>();
    private GameState status = GameState.waiting;
    private YamlConfiguration yml;
    private ConfigManager cm;
    private int minPlayers = 2, maxPlayers = 10, countdownS, slot = -1, maxInTeam = 1, restarting = 12, islandRadius = 10;
    public int upgradeDiamondsCount = 0, upgradeEmeraldsCount = 0;
    public boolean allowSpectate = true;
    private World world;
    private String group = "DEFAULT";
    private List<BedWarsTeam> teams = new ArrayList<>();
    private List<Block> placed = new ArrayList<>();
    private HashMap<Block, BlockState> broken = new HashMap<>();

    /**
     * Current event, used at scoreboard
     */
    private NextEvent nextEvent = NextEvent.DIAMOND_GENERATOR_TIER_II;
    public int diamondTier = 1, emeraldTier = 1;

    /**
     * bed block
     */
    private Material bedBlock = Material.BED_BLOCK;
    private int bedsDestroyCountdown = config.getInt("bedsDestroyCountdown");
    private int dragonCountdown = config.getInt("dragonSpawnCountdown");
    private int gameEndCountdown = config.getInt("gameEndCountdown");

    /**
     * player location before joining
     */
    private static HashMap<Player, Location> playerLocation = new HashMap<>();

    /**
     * temp storage
     */
    private HashMap<Player, Integer> playerKills = new HashMap<>();
    private static HashMap<Player, Integer> playerBedsDestroyed = new HashMap<>();
    private static HashMap<Player, Integer> playerFinalKills = new HashMap<>();
    private static HashMap<Player, Integer> playerDeaths = new HashMap<>();
    private static HashMap<Player, Integer> playerFinalKillDeaths = new HashMap<>();

    /**
     * Load an arena.
     * This will check if it was set up right.
     *
     * @param name - world name
     * @param p    - This will send messages to the player if something went wrong while loading the arena. Can be NULL.
     */
    public Arena(String name, Player p) {
        cm = new ConfigManager(name, "plugins/" + plugin.getName() + "/Arenas", true);
        yml = cm.getYml();
        if (yml.get("Team") == null) {
            if (p != null) p.sendMessage("You didn't set any team for arena: " + name);
            plugin.getLogger().severe("You didn't set any team for arena: " + name);
            return;
        }
        if (yml.getConfigurationSection("Team").getKeys(false).size() < 2) {
            if (p != null) p.sendMessage("§cYou must set at least 2 teams on: " + name);
            plugin.getLogger().severe("You must set at least 2 teams on: " + name);
            return;
        }
        maxInTeam = yml.getInt("maxInTeam");
        maxPlayers = yml.getConfigurationSection("Team").getKeys(false).size() * maxInTeam;
        minPlayers = yml.getInt("minPlayers");
        allowSpectate = yml.getBoolean("allowSpectate");
        countdownS = config.getYml().getInt("startingCountdown");
        islandRadius = yml.getInt(ConfigPath.ARENA_ISLAND_RADIUS);
        if (config.getYml().get("arenaGroups") != null) {
            if (config.getYml().getStringList("arenaGroups").contains(yml.getString("group"))) {
                group = yml.getString("group");
            }
        }
        if (new File(plugin.getServer().getWorldContainer().getPath() + "/" + name) == null) {
            if (p != null) p.sendMessage("§cThere isn't any map called " + name);
            plugin.getLogger().severe("There isn't any map called " + name);
            return;
        }
        boolean error = false;
        for (String team : yml.getConfigurationSection("Team").getKeys(false)) {
            if (TeamColor.valueOf(yml.getString("Team." + team + ".Color")) == null) {
                if (p != null) p.sendMessage("§cInvalid color at team: " + team + " in arena: " + name);
                plugin.getLogger().severe("Invalid color at team: " + team + " in arena: " + name);
                error = true;
            }
            for (String stuff : Arrays.asList("Color", "Spawn", "Bed", "Shop", "Upgrade", "Iron", "Gold")) {
                if (yml.get("Team." + team + "." + stuff) == null) {
                    if (p != null) p.sendMessage("§c" + stuff + " not set for " + team + " team on: " + name);
                    plugin.getLogger().severe(stuff + " not set for " + team + " team on: " + name);
                    error = true;
                }
            }
        }
        if (yml.get("generator.Diamond") == null) {
            if (p != null) p.sendMessage("§cThere isn't set any Diamond generator on: " + name);
            plugin.getLogger().severe("There isn't set any Diamond generator on: " + name);
        } else {
        }
        if (yml.get("generator.Emerald") == null) {
            if (p != null) p.sendMessage("§cThere isn't set any Emerald generator on: " + name);
            plugin.getLogger().severe("There isn't set any Emerald generator on: " + name);
        } else {
        }
        if (yml.get("waiting.Loc") == null) {
            if (p != null) p.sendMessage("§cWaiting spawn not set on: " + name);
            plugin.getLogger().severe("Waiting spawn not set on: " + name);
            return;
        }
        if (error) return;
        try {
            world = Bukkit.createWorld(new WorldCreator(name));
        } catch (Exception ex) {
            if (p != null) p.sendMessage("§cI can't load the map called " + name);
            plugin.getLogger().severe("I can't load the map called " + name);
            ex.printStackTrace();
            return;
        }
        Bukkit.getScheduler().runTaskLater(plugin,
                () -> world.getEntities().stream().filter(e -> e.getType() != EntityType.PLAYER)
                        .filter(e -> e.getType() != EntityType.PAINTING).filter(e -> e.getType() != EntityType.ITEM_FRAME)
                        .forEach(Entity::remove), 30L);
        world.setGameRuleValue("doMobSpawning", "false");
        world.setAutoSave(false);
        for (String team : yml.getConfigurationSection("Team").getKeys(false)) {
            teams.add(new BedWarsTeam(team, TeamColor.valueOf(yml.getString("Team." + team + ".Color").toUpperCase()),
                    cm.getArenaLoc("Team." + team + ".Spawn"), cm.getArenaLoc("Team." + team + ".Bed"), cm.getArenaLoc("Team." + team + ".Shop"),
                    cm.getArenaLoc("Team." + team + ".Upgrade"), this));
        }

        arenas.add(this);
        arenaByName.put(world.getName(), this);
        if (yml.get("bedBlock") != null) {
            try {
                Material.valueOf(yml.getString("bedBlock"));
                bedBlock = Material.valueOf(yml.getString("bedBlock"));
            } catch (Exception ex) {
                if (p != null)
                    p.sendMessage("§c" + yml.getString("bedBlock") + " is not a Material at " + getWorldName() + ".yml");
                plugin.getLogger().severe(yml.getString("bedBlock") + " is not a Material at " + getWorldName() + ".yml");
            }
        }
        world.getWorldBorder().setCenter(cm.getArenaLoc("waiting.Loc"));
        world.getWorldBorder().setSize(yml.getInt("worldBorder"));

        /* Check if lobby removal is set */
        if (!getCm().getYml().isSet(ConfigPath.ARENA_WAITING_POS1) && getCm().getYml().isSet(ConfigPath.ARENA_WAITING_POS2)) {
            plugin.getLogger().severe("Lobby Pos1 isn't set! The arena's lobby won't be removed!");
        }
        if (getCm().getYml().isSet(ConfigPath.ARENA_WAITING_POS1) && !getCm().getYml().isSet(ConfigPath.ARENA_WAITING_POS2)) {
            plugin.getLogger().severe("Lobby Pos2 isn't set! The arena's lobby won't be removed!");
        }

        //Call event
        Bukkit.getPluginManager().callEvent(new ArenaEnableEvent(this));
    }

    /**
     * Add a player to the arena
     *
     * @param p              - Player to add.
     * @param skipOwnerCheck - True if you want to skip the party checking for this player. This
     */
    public void addPlayer(Player p, boolean skipOwnerCheck) {
        debug("Player added: " + p.getName() + " arena: " + getWorldName());
        /* used for base enter/leave event */
        if (isOnABase.containsKey(p)) {
            isOnABase.remove(p);
        }
        //
        if (getArenaByPlayer(p) != null) {
            return;
        }
        if (getParty().hasParty(p)) {
            if (!skipOwnerCheck) {
                if (!getParty().isOwner(p)) {
                    p.sendMessage(getMsg(p, Messages.ARENA_JOIN_DENIED_NOT_PARTY_LEADER));
                    return;
                }

            /*if (getParty().partySize(p) > maxInTeam) {
                p.sendMessage(getMsg(p, Language.partyTooBig));
                return;
            }*/
                if (getParty().partySize(p) > maxInTeam * getTeams().size() - getPlayers().size()) {
                    p.sendMessage(getMsg(p, Messages.ARENA_JOIN_DENIED_PARTY_TOO_BIG));
                    return;
                }
                for (Player mem : getParty().getMembers(p)) {
                    if (mem == p) continue;
                    Arena a = Arena.getArenaByPlayer(mem);
                    if (a != null) {
                        if (a.isPlayer(mem)) {
                            a.removePlayer(mem, false);
                        } else if (a.isSpectator(mem)) {
                            a.removeSpectator(mem, false);
                        }
                    }
                    addPlayer(mem, true);
                }
            }
        }
        if (status == GameState.waiting || (status == GameState.starting && countdownS > 2)) {
            if (players.size() >= maxPlayers && !isVip(p)) {
                TextComponent text = new TextComponent(getMsg(p, Messages.ARENA_JOIN_DENIED_IS_FULL));
                text.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, config.getYml().getString("storeLink")));
                p.spigot().sendMessage(text);
                return;
            } else if (players.size() >= maxPlayers && isVip(p)) {
                boolean canJoin = false;
                for (Player on : players) {
                    if (!isVip(on)) {
                        canJoin = true;
                        removePlayer(on, false);
                        TextComponent vipKick = new TextComponent(getMsg(p, Messages.ARENA_JOIN_VIP_KICK));
                        vipKick.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, config.getYml().getString("storeLink")));
                        p.spigot().sendMessage(vipKick);
                    }
                }
                if (!canJoin) {
                    p.sendMessage(getMsg(p, Messages.ARENA_JOIN_DENIED_IS_FULL_VIP_REQUIRED));
                    return;
                }
            }

            /* NametagEdit Support */
            NametagEdit.saveNametag(p);

            p.closeInventory();
            players.add(p);
            updateNPCs(getGroup());
            for (Player on : players) {
                on.sendMessage(getMsg(on, Messages.ARENA_JOIN_PLAYER_JOIN_MSG).replace("{player}", p.getDisplayName()).replace("{on}", String.valueOf(getPlayers().size())).replace("{max}", String.valueOf(getMaxPlayers())));
            }
            setArenaByPlayer(p, false);

            /* check if you can start the arena */
            if (status == GameState.waiting) {
                int teams = 0, teammates = 0;
                for (Player on : getPlayers()) {
                    if (getParty().isOwner(on)) {
                        teams++;
                    }
                    if (getParty().hasParty(on)) {
                        teammates++;
                    }
                }
                if (minPlayers <= players.size() && teams > 0 && players.size() != teammates / teams) {
                    setStatus(GameState.starting);
                } else if (players.size() >= minPlayers && teams == 0) {
                    setStatus(GameState.starting);
                }
            }

            /* save player inventory etc */
            new PlayerGoods(p, true);
            playerLocation.put(p, p.getLocation());
            p.teleport(cm.getArenaLoc("waiting.Loc"));
            if (getStatus() == GameState.waiting) {
                new SBoard(p, getScoreboard(p, "scoreboard." + getGroup() + "Waiting", Messages.SCOREBOARD_DEFAULT_WAITING), this);
            } else if (getStatus() == GameState.starting) {
                new SBoard(p, getScoreboard(p, "scoreboard." + getGroup() + "Starting", Messages.SCOREBOARD_DEFAULT_STARTING), this);
            }
            leaveItem(p);

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
    }

    /**
     * Add a player as Spectator
     *
     * @param p            Player to be added
     * @param playerBefore True if the player has played in this arena before and he died so now should be a spectator.
     */
    public void addSpectator(Player p, boolean playerBefore) {
        debug("Spectator added: " + p.getName() + " arena: " + getWorldName());
        if (allowSpectate || playerBefore) {

            /* NametagEdit Support */
            NametagEdit.saveNametag(p);

            p.closeInventory();
            if (!playerBefore) p.teleport(cm.getArenaLoc("waiting.Loc"));
            spectators.add(p);
            players.remove(p);

            for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
                if (sb.getP().equals(p)) {
                    sb.remove();
                }
            }

            if (!playerBefore) {
                /* save player inv etc if isn't saved yet*/
                new PlayerGoods(p, true);
                setArenaByPlayer(p, true);
                playerLocation.put(p, p.getLocation());
            }

            new SBoard(p, this);
            nms.setCollide(p, this, false);

            /* Hide spectator  */
            //p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0), true);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (Player on : Bukkit.getOnlinePlayers()) {
                if (on == p) continue;
                if (getSpectators().contains(on)) {
                    on.showPlayer(p);
                    p.showPlayer(p);
                } else if (getPlayers().contains(on)) {
                    on.hidePlayer(p);
                    p.showPlayer(on);
                }
            }
            p.setAllowFlight(true);
            p.setFlying(true);

                /* Spectator items */
                SpectateItems.giveTeleporter(p);
                SpectateItems.giveLeaveItem(p);
            }, 10L);

            p.setGameMode(GameMode.ADVENTURE);
            p.sendMessage(getMsg(p, Messages.ARENA_JOIN_SPECTATOR_MSG).replace("{arena}", this.getDisplayName()));

            /* update generator holograms for spectators */
            String iso = Language.getPlayerLanguage(p).getIso();
            for (OreGenerator o : OreGenerator.getGenerators()) {
                if (o.getArena() == this) {
                    o.updateHolograms(p, iso);
                }
            }
            for (ShopHolo sh : ShopHolo.getShopHolo()) {
                if (sh.getA() == this) {
                    sh.updateForPlayer(p, iso);
                }
            }
            //todo call spectator join event
        } else {
            //todo msg spectate not allowed
        }
    }

    /**
     * Remove a player from the arena
     *
     * @param p Player to be removed
     */
    @Deprecated
    public void removePlayer(Player p) {
        removePlayer(p, false);
    }

    /**
     * Remove a player from the arena
     *
     * @param p          Player to be removed
     * @param disconnect True if the player was disconnected
     * @since API 8
     */
    public void removePlayer(Player p, boolean disconnect) {
        debug("Player removed: " + p.getName() + " arena: " + getWorldName());
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
        updateNPCs(getGroup());
        removeArenaByPlayer(p);
        players.remove(p);
        for (PotionEffect pf : p.getActivePotionEffects()) {
            p.removePotionEffect(pf.getType());
        }

        if (status != GameState.restarting) {
            for (Player on : players) {
                on.sendMessage(getMsg(p, Messages.ARENA_LEAVE_PLAYER_LEAVE_MSG).replace("{player}", p.getName()));
            }
        }

        if (getServerType() != ServerType.BUNGEE) {
            /* restore player inventory */
            if (PlayerGoods.hasGoods(p)) {
                PlayerGoods.getPlayerGoods(p).restore();
            }
        }

        if (respawn.containsKey(p)) {
            respawn.remove(p);
            BedWarsTeam t = this.getTeam(p);
            if (t != null) {
                if (t.getMembers().isEmpty()) {
                    for (Player p2 : this.getPlayers()) {
                        p2.sendMessage(getMsg(p2, Messages.TEAM_ELIMINATED_CHAT).replace("{TeamColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{TeamName}", t.getName()));
                    }
                    for (Player p2 : this.getSpectators()) {
                        p2.sendMessage(getMsg(p2, Messages.TEAM_ELIMINATED_CHAT).replace("{TeamColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{TeamName}", t.getName()));
                    }
                }
            }
        }
        if (status == GameState.playing) {
            int deaths = playerDeaths.containsKey(p) ? playerDeaths.get(p) : 0;
            int final_deaths = playerFinalKillDeaths.containsKey(p) ? playerFinalKillDeaths.get(p) : 0;
            int beds = playerBedsDestroyed.containsKey(p) ? playerBedsDestroyed.get(p) : 0;
            database.saveStats(p, new Timestamp(System.currentTimeMillis()), 0, this.getPlayerKills(p, false), this.getPlayerKills(p, true),
                    1, deaths, final_deaths, beds, 1);
        } else if (status == GameState.restarting) {
            /* winners */
            int deaths = playerDeaths.containsKey(p) ? playerDeaths.get(p) : 0;
            int final_deaths = playerFinalKillDeaths.containsKey(p) ? playerFinalKillDeaths.get(p) : 0;
            int beds = playerBedsDestroyed.containsKey(p) ? playerBedsDestroyed.get(p) : 0;
            database.saveStats(p, new Timestamp(System.currentTimeMillis()), 1, this.getPlayerKills(p, false), this.getPlayerKills(p, true),
                    0, deaths, final_deaths, beds, 1);
        }
        boolean teamuri = false;
        for (Player on : getPlayers()) {
            if (getParty().hasParty(on)) {
                teamuri = true;
            }
        }
        if (status == GameState.starting && (maxInTeam > players.size() && teamuri || players.size() < minPlayers && !teamuri)) {
            setStatus(GameState.waiting);
            for (Player on : players) {
                on.sendMessage(getMsg(p, Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS));
            }
        } else if (status == GameState.playing && players.size() <= 1) {
            checkWinner();
            setStatus(GameState.restarting);
        } else if (status == GameState.playing) {
            int alive_teams = 0;
            for (BedWarsTeam t : getTeams()) {
                if (t == null) continue;
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
                on.sendMessage(getMsg(on, Messages.ARENA_LEAVE_PLAYER_LEAVE_MSG).replace("{player}", p.getDisplayName()));
            }
        }
        for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
            if (sb.getP() == p) {
                sb.remove();
            }
        }
        if (getServerType() == ServerType.SHARED) {
            p.teleport(playerLocation.get(p));
        } else if (getServerType() == ServerType.BUNGEE) {
            Misc.moveToLobbyOrKick(p);
            return;
        } else {
            p.teleport(config.getConfigLoc("lobbyLoc"));
        }
        playerLocation.remove(p);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (Player on : Bukkit.getOnlinePlayers()) {
                if (getArenaByPlayer(on) == null) {
                    on.showPlayer(p);
                    p.showPlayer(on);
                } else {
                    p.hidePlayer(on);
                    on.hidePlayer(p);
                }
            }
            if (!disconnect) Misc.giveLobbySb(p);
        }, 5L);

        /* NametagEdit Support */
        NametagEdit.restoreNametag(p);

        /* Remove also the party */
        if (getParty().hasParty(p)){
            if (getParty().isOwner(p)){
                for (Player pa : new ArrayList<>(getParty().getMembers(p))){
                    if (pa == p) continue;
                    removePlayer(pa, Main.getServerType() == ServerType.MULTIARENA);
                }
            }
        }
    }

    /**
     * Remove a spectator from the arena
     *
     * @param p Player to be removed
     */
    @Deprecated
    public void removeSpectator(Player p) {
        removeSpectator(p, false);
    }

    /**
     * Remove a spectator from the arena
     *
     * @param p          Player to be removed
     * @param disconnect True if the player was disconnected
     * @since API 8
     */
    public void removeSpectator(Player p, boolean disconnect) {
        debug("Spectator removed: " + p.getName() + " arena: " + getWorldName());
        removeArenaByPlayer(p);
        spectators.remove(p);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        /* restore player inventory */
        if (PlayerGoods.hasGoods(p)) {
            PlayerGoods.getPlayerGoods(p).restore();
        }
        nms.setCollide(p, this, true);

        for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
            if (sb.getP() == p) {
                sb.remove();
            }
        }

        for (BedWarsTeam bwt : getTeams()) {
            if (bwt.getMembersCache().contains(p)) {
                if (status == GameState.playing) {
                    int deaths = playerDeaths.containsKey(p) ? playerDeaths.get(p) : 0;
                    int final_deaths = playerFinalKillDeaths.containsKey(p) ? playerFinalKillDeaths.get(p) : 0;
                    int beds = playerBedsDestroyed.containsKey(p) ? playerBedsDestroyed.get(p) : 0;
                    database.saveStats(p, new Timestamp(System.currentTimeMillis()), 0, this.getPlayerKills(p, false), this.getPlayerKills(p, true),
                            1, deaths, final_deaths, beds, 1);
                } else if (status == GameState.restarting) {
                    /** looser */
                    int deaths = playerDeaths.containsKey(p) ? playerDeaths.get(p) : 0;
                    int final_deaths = playerFinalKillDeaths.containsKey(p) ? playerFinalKillDeaths.get(p) : 0;
                    int beds = playerBedsDestroyed.containsKey(p) ? playerBedsDestroyed.get(p) : 0;
                    database.saveStats(p, new Timestamp(System.currentTimeMillis()), 0, this.getPlayerKills(p, false), this.getPlayerKills(p, true),
                            1, deaths, final_deaths, beds, 1);
                }
                break;
            }
        }

        if (getServerType() == ServerType.SHARED) {
            p.teleport(playerLocation.get(p));
        } else if (getServerType() == ServerType.MULTIARENA) {
            p.teleport(config.getConfigLoc("lobbyLoc"));
        }
        if (getServerType() == ServerType.BUNGEE) {
            Misc.moveToLobbyOrKick(p);
            return;
        }
        playerLocation.remove(p);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (Player on : Bukkit.getOnlinePlayers()) {
                if (getArenaByPlayer(on) == null) {
                    on.showPlayer(p);
                    p.showPlayer(on);
                } else {
                    on.hidePlayer(p);
                    p.hidePlayer(on);
                }
            }
            if (!disconnect) Misc.giveLobbySb(p);
        }, 10L);

        /* NametagEdit Support */
        NametagEdit.restoreNametag(p);
    }

    /**
     * Disable the arena.
     * This will automatically kick/ remove the people from the arena.
     */
    public void disable() {
        if (world == null) {
            arenas.remove(this);
            return;
        }
        plugin.getLogger().info("Disabling arena: " + getDisplayName());
        for (Player on : players) {
            removePlayer(on, Main.getServerType() == ServerType.BUNGEE);
        }
        for (Player on : spectators) {
            removeSpectator(on, Main.getServerType() == ServerType.BUNGEE);
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
        for (Entity e : world.getEntities()) {
            if (e.getType() == EntityType.PLAYER) {
                Player p = (Player) e;
                p.kickPlayer(getMsg(p, Messages.ARENA_RESTART_PLAYER_KICK));
            }
        }
        players.clear();
        spectators.clear();
        Bukkit.unloadWorld(world, false);
        arenaByName.remove(world.getName());
        arenas.remove(this);
        //Call event
        Bukkit.getPluginManager().callEvent(new ArenaDisableEvent(getWorldName()));
    }

    /**
     * Restart the arena
     */
    private void restart() {
        if (getServerType() == ServerType.BUNGEE) {
            //todo games before restart dezactivat temporar pentru ca, cauzeaza probleme
            gamesBeforeRestart = 0;
            if (gamesBeforeRestart <= 0) {
                Bukkit.getServer().spigot().restart();
                return;
            }
            gamesBeforeRestart--;
        }
        diamondTier = 1;
        emeraldTier = 1;
        upgradeDiamondsCount = 0;
        upgradeEmeraldsCount = 0;
        bedsDestroyCountdown = config.getInt("bedsDestroyCountdown");
        dragonCountdown = config.getInt("dragonSpawnCountdown");
        gameEndCountdown = config.getInt("gameEndCountdown");
        nextEvent = NextEvent.EMERALD_GENERATOR_TIER_II;
        ShopHolo.clearForArena(this);
        for (Entity e : world.getEntities()) {
            if (e.getType() == EntityType.PLAYER) {
                Player p = (Player) e;
                Misc.moveToLobbyOrKick(p);
                if (isSpectator(p)) removeSpectator(p);
                if (isPlayer(p)) removePlayer(p);
            }
        }
        players.clear();
        spectators.clear();
        OreGenerator.removeIfArena(this);
        String name = world.getName();
        Bukkit.unloadWorld(world, false);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            world = Bukkit.createWorld(new WorldCreator(name));
            world.setAutoSave(false);
            this.setStatus(GameState.waiting);
            for (String team : yml.getConfigurationSection("Team").getKeys(false)) {
                teams.add(new BedWarsTeam(team, TeamColor.valueOf(yml.getString("Team." + team + ".Color").toUpperCase()),
                        cm.getArenaLoc("Team." + team + ".Spawn"), cm.getArenaLoc("Team." + team + ".Bed"), cm.getArenaLoc("Team." + team + ".Shop"),
                        cm.getArenaLoc("Team." + team + ".Upgrade"), this));
            }
        }, 100L);

        teams.clear();
        countdownS = config.getYml().getInt("startingCountdown");
        restarting = 12;
        playerKills.clear();
        playerBedsDestroyed.clear();
        playerFinalKills.clear();
        playerDeaths.clear();
        playerFinalKillDeaths.clear();
        //System.gc();
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
                    Collections.shuffle(getTeams());
                    /* check parties first */
                    for (Player p : getPlayers()) {
                        if (owners.contains(p)) {
                            for (BedWarsTeam t : getTeams()) {
                                if (skip.contains(p)) continue;
                                if (t.getSize() + getParty().partySize(p) <= maxInTeam) {
                                    skip.add(p);
                                    p.closeInventory();
                                    t.addPlayers(p);
                                    for (Player mem : getParty().getMembers(p)) {
                                        if (mem != p) {
                                            t.addPlayers(mem);
                                            skip.add(mem);
                                            mem.closeInventory();
                                        }
                                    }
                                }
                            }
                        }
                    }

                    /* players without a party */
                    for (Player p : getPlayers()) {
                        if (skip.contains(p)) continue;
                        BedWarsTeam addhere = getTeams().get(0);
                        for (BedWarsTeam t : getTeams()) {
                            if (t.getMembers().size() < maxInTeam && t.getMembers().size() < addhere.getMembers().size()) {
                                addhere = t;
                            }
                        }
                        addhere.addPlayers(p);
                        p.closeInventory();
                    }

                    setStatus(GameState.playing);

                    /* Spawn bed block */
                    for (BedWarsTeam team : getTeams()) {
                        team.setGenerators(cm.getArenaLoc("Team." + team.getName() + ".Iron"), cm.getArenaLoc("Team." + team.getName() + ".Gold"));
                        team.setBedDestroyed(false);
                        for (Entity e : team.getArena().getWorld().getNearbyEntities(team.getBed(), 2, 2, 2)) {
                            if (e.getType() == EntityType.DROPPED_ITEM) {
                                e.remove();
                            }
                        }
                    }

                    /* Spawn generators */
                    for (String type : Arrays.asList("Diamond", "Emerald")) {
                        if (yml.get("generator." + type) != null) {
                            for (String s : yml.getStringList("generator." + type)) {
                                new OreGenerator(cm.fromArenaStringList(s), this, GeneratorType.valueOf(type.toUpperCase()));
                            }
                        }
                    }

                    /* Remove lobby */
                    if (!(yml.get(ConfigPath.ARENA_WAITING_POS1) == null && yml.get(ConfigPath.ARENA_WAITING_POS2) == null)) {
                        Location loc1 = cm.getArenaLoc(ConfigPath.ARENA_WAITING_POS1), loc2 = cm.getArenaLoc(ConfigPath.ARENA_WAITING_POS2);
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

                    /* Spawn shops */
                    for (BedWarsTeam t : getTeams()) {
                        if (t.getSize() == 0) {
                            t.setBedDestroyed(true);
                            continue;
                        }
                        if (maxInTeam > 1) {
                            nms.spawnShop(t.getTeamUpgrades(), Messages.UPGRADES_TEAM_NPC_NAME, getPlayers(), this);
                            nms.spawnShop(t.getShop(), Messages.SHOP_TEAM_NAME, getPlayers(), this);
                        } else {
                            nms.spawnShop(t.getTeamUpgrades(), Messages.UPGRADES_SOLO_NPC_NAME, getPlayers(), this);
                            nms.spawnShop(t.getShop(), Messages.SHOP_SOLO_NAME, getPlayers(), this);
                        }
                    }

                    /* Ad heart on head */
                    for (SBoard sb : SBoard.getScoreboards()) {
                        sb.addHealthIcon();
                    }
                    for (BedWarsTeam bwt : getTeams()) {
                        for (Player p : bwt.getMembers()) {
                            bwt.firstSpawn(p);
                            p.setHealth(p.getHealth() - 0.0001);
                            nms.sendTitle(p, getMsg(p, Messages.ARENA_STATUS_START_PLAYER_TITLE), null, 0, 20, 0);
                            for (String tut : getList(p, Messages.ARENA_STATUS_START_PLAYER_TUTORIAL)) {
                                p.sendMessage(tut);
                            }
                        }
                    }
                    setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_II);
                    return;
                }
                if (countdownS % 10 == 0 || countdownS <= 5) {
                    for (Player p : getPlayers()) {
                        p.playSound(p.getLocation(), nms.countdownTick(), 1f, 1f);
                        if (countdownS >= 10) {
                            nms.sendTitle(p, "§a" + countdownS, null, 0, 20, 0);
                            p.sendMessage(getMsg(p, Messages.ARENA_STATUS_START_COUNTDOWN).replace("{time}", String.valueOf(countdownS)));
                        } else if (countdownS > 3) {
                            nms.sendTitle(p, "§e" + countdownS, null, 0, 20, 0);
                            p.sendMessage(getMsg(p, Messages.ARENA_STATUS_START_COUNTDOWN).replace("{time}", "§c" + String.valueOf(countdownS)));
                        } else {
                            nms.sendTitle(p, "§c" + countdownS, null, 0, 20, 0);
                            p.sendMessage(getMsg(p, Messages.ARENA_STATUS_START_COUNTDOWN).replace("{time}", "§c" + String.valueOf(countdownS)));
                        }
                    }
                }
                countdownS--;
                break;
            case playing:
                switch (nextEvent) {
                    case EMERALD_GENERATOR_TIER_II:
                    case EMERALD_GENERATOR_TIER_III:
                    case DIAMOND_GENERATOR_TIER_II:
                    case DIAMOND_GENERATOR_TIER_III:
                        if (upgradeDiamondsCount > 0) {
                            upgradeDiamondsCount--;
                            if (upgradeDiamondsCount == 0) {
                                for (OreGenerator o : OreGenerator.getGenerators()) {
                                    if (o.getArena() == this) {
                                        if (o.getOre().getType() == Material.DIAMOND) {
                                            o.upgrade();
                                        }
                                    }
                                }
                                updateNextEvent();
                            }
                        }
                        if (upgradeEmeraldsCount > 0) {
                            upgradeEmeraldsCount--;
                            if (upgradeEmeraldsCount == 0) {
                                for (OreGenerator o : OreGenerator.getGenerators()) {
                                    if (o.getArena() == this) {
                                        if (o.getOre().getType() == Material.EMERALD) {
                                            o.upgrade();
                                        }
                                    }
                                }
                                updateNextEvent();
                            }
                        }
                        break;
                    case BEDS_DESTROY:
                        bedsDestroyCountdown--;
                        if (bedsDestroyCountdown == 0) {
                            for (Player p : getPlayers()) {
                                nms.sendTitle(p, getMsg(p, Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED), getMsg(p, Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED), 0, 30, 0);
                                p.sendMessage(getMsg(p, Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED));
                            }
                            for (Player p : getSpectators()) {
                                nms.sendTitle(p, getMsg(p, Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED), getMsg(p, Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED), 0, 30, 0);
                                p.sendMessage(getMsg(p, Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED));
                            }
                            for (BedWarsTeam t : getTeams()) {
                                t.setBedDestroyed(true);
                            }
                            updateNextEvent();
                        }
                        break;
                    case ENDER_DRAGON:
                        dragonCountdown--;
                        if (dragonCountdown == 0) {
                            for (Player p : getPlayers()) {
                                nms.sendTitle(p, getMsg(p, Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH), getMsg(p, Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH), 0, 30, 0);
                                for (BedWarsTeam t : getTeams()) {
                                    if (t.getMembers().isEmpty()) continue;
                                    p.sendMessage(getMsg(p, Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH).replace("{TeamDragons}", String.valueOf(t.getDragons()))
                                            .replace("{TeamColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{TeamName}", t.getName()));
                                }
                            }
                            for (Player p : getSpectators()) {
                                nms.sendTitle(p, getMsg(p, Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH), getMsg(p, Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH), 0, 30, 0);
                                for (BedWarsTeam t : getTeams()) {
                                    if (t.getMembers().isEmpty()) continue;
                                    p.sendMessage(getMsg(p, Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH).replace("{TeamDragons}", String.valueOf(t.getDragons()))
                                            .replace("{TeamColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{TeamName}", t.getName()));
                                }
                            }
                            updateNextEvent();
                            for (OreGenerator og : OreGenerator.getGenerators()) {
                                if (og.getArena() == this) {
                                    Location l = og.getLocation();
                                    for (int y = 0; y < 20; y++) {
                                        l.clone().subtract(0, y, 0).getBlock().setType(Material.AIR);
                                    }
                                }
                            }
                            for (BedWarsTeam t : getTeams()) {
                                if (t.getMembers().isEmpty()) continue;
                                for (int x = 0; x < t.getDragons(); x++) {
                                    nms.spawnDragon(cm.getArenaLoc("waiting.Loc").add(0, 10, 0), t);
                                }
                            }
                        }
                        break;
                    case GAME_END:
                        gameEndCountdown--;
                        if (gameEndCountdown == 0) {
                            checkWinner();
                            setStatus(GameState.restarting);
                        }
                        break;
                }
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
                            nms.playAction(p, getMsg(p, Messages.FORMATTING_ACTION_BAR_TRACKING).replace("{team}", TeamColor.getChatColor(t.getColor()) + t.getName())
                                    .replace("{distance}", TeamColor.getChatColor(t.getColor()).toString() + distance).replace("&", "§"));
                        }
                    }
                }
                break;
            case restarting:
                for (Player p : players) {
                    for (int i = 0; i < 2; i++) {
                        launchFirework(p);
                    }
                }
                restarting--;
                if (restarting == 5) {
                    for (Player on : new ArrayList<>(players)) {
                        removePlayer(on, Main.getServerType() == ServerType.BUNGEE);
                    }
                    for (Player on : new ArrayList<>(spectators)) {
                        removeSpectator(on, Main.getServerType() == ServerType.BUNGEE);
                    }
                }
                if (restarting == 0) {
                    restart();
                }
                break;
        }
    }

    //GETTER METHODS

    /**
     * Get the arena world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Get the max number of teammates in a team
     */
    public int getMaxInTeam() {
        return maxInTeam;
    }

    /**
     * Get an arena by world name
     *
     * @param name World name
     */
    public static Arena getArenaByName(String name) {
        return arenaByName.get(name);
    }

    /**
     * Get an arena by a player. Spectator or Player.
     *
     * @param p Target player
     * @return The arena where the player is in. Can be NULL.
     */
    public static Arena getArenaByPlayer(Player p) {
        return arenaByPlayer.get(p);
    }

    /**
     * Get an arenas list
     */
    @Contract(pure = true)
    public static ArrayList<Arena> getArenas() {
        return arenas;
    }

    /**
     * Get the display status for an arena.
     * A message that can be used on signs etc.
     */
    public String getDisplayStatus() {
        String s = "";
        switch (status) {
            case waiting:
                s = getMsg(null, Messages.ARENA_STATUS_WAITING_NAME).replace("{full}", this.getPlayers().size() == this.getMaxPlayers() ? "FULL" : "");
                break;
            case starting:
                s = getMsg(null, Messages.ARENA_STATUS_STARTING_NAME).replace("{full}", this.getPlayers().size() == this.getMaxPlayers() ? "FULL" : "");
                ;
                break;
            case restarting:
                s = getMsg(null, Messages.ARENA_STATUS_RESTARTING_NAME).replace("{full}", this.getPlayers().size() == this.getMaxPlayers() ? "FULL" : "");
                ;
                break;
            case playing:
                s = getMsg(null, Messages.ARENA_STATUS_PLAYING_NAME).replace("{full}", this.getPlayers().size() == this.getMaxPlayers() ? "FULL" : "");
                ;
                break;
        }
        return s;
    }

    /**
     * Get the players list
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Get the max number of players that can play on this arena.
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Get the arena name as a message that can be used on signs etc.
     *
     * @return A string with - and _ replaced by a space.
     */
    public String getDisplayName() {
        return world.getName().replace("_", " ").replace("-", " ");
    }

    /**
     * Get the arena's group.
     */
    public String getGroup() {
        return group;
    }

    /**
     * Get the current slot for the arena in the ARENA SELECTOR
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Get the arena's world name.
     */
    public String getWorldName() {
        return world.getName();
    }

    /**
     * Get the teams list
     */
    public List<BedWarsTeam> getTeams() {
        return teams;
    }

    /**
     * Get the arena's configuration
     */
    public ConfigManager getCm() {
        return cm;
    }

    /**
     * Get the placed blocks list
     */
    public List<Block> getPlaced() {
        return placed;
    }

    /**
     * Get the countdown seconds for the arena when is waiting/ starting
     */
    public int getCountdownS() {
        return countdownS;
    }


    /**
     * Get a player kills count.
     *
     * @param p          Target player
     * @param finalKills True if you want to get the Final Kills. False for regular kills.
     */
    public int getPlayerKills(Player p, boolean finalKills) {
        if (finalKills) {
            if (playerFinalKills.containsKey(p)) return playerFinalKills.get(p);
            return 0;
        }
        if (playerKills.containsKey(p)) return playerKills.get(p);
        return 0;
    }

    /**
     * Get the player beds destroyed count
     *
     * @param p Target player
     */
    public int getPlayerBedsDestroyed(Player p) {
        if (playerBedsDestroyed.containsKey(p)) return playerBedsDestroyed.get(p);
        return 0;
    }

    /**
     * Get the bed block for this arena
     */
    public Material getBedBlock() {
        return bedBlock;
    }

    /**
     * Get the join signs for this arena
     */
    public List<BlockState> getSigns() {
        return signs;
    }

    /**
     * Get the island radius
     */
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
        Bukkit.getPluginManager().callEvent(new PlayerLeaveArenaEvent(p, this));
        refreshSigns();
    }

    public void setStatus(GameState status) {
        this.status = status;
        Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(this, status));
        refreshSigns();
        if (status == GameState.starting) {
            for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
                if (sb.getArena() == this) {
                    sb.setStrings(getScoreboard(sb.getP(), "scoreboard." + getGroup() + "Starting", Messages.SCOREBOARD_DEFAULT_STARTING));
                }
            }
        } else if (status == GameState.waiting) {
            countdownS = config.getYml().getInt("startingCountdown");
            for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
                if (sb.getArena() == this) {
                    sb.setStrings(getScoreboard(sb.getP(), "scoreboard." + getGroup() + "Waiting", Messages.SCOREBOARD_DEFAULT_WAITING));
                }
            }
        } else if (status == GameState.playing) {
            for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
                if (sb.getArena() == this) {
                    sb.setStrings(getScoreboard(sb.getP(), "scoreboard." + getGroup() + "Playing", Messages.SCOREBOARD_DEFAULT_PLAYING));
                    sb.giveTeamColorTag();
                }
            }
        }
    }

    /**
     * Check if a player has vip perms
     */
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

    public void addPlayerKill(Player p, boolean finalKill, Player victim) {
        if (p == null) return;
        if (playerKills.containsKey(p)) {
            playerKills.replace(p, playerKills.get(p) + 1);
        } else {
            playerKills.put(p, 1);
        }
        if (finalKill) {
            if (playerFinalKills.containsKey(p)) {
                playerFinalKills.replace(p, playerFinalKills.get(p) + 1);
            } else {
                playerFinalKills.put(p, 1);
            }
            playerFinalKillDeaths.put(victim, 1);
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
        List<Arena> arenas = new ArrayList<>(getArenas());
        Collections.shuffle(arenas);
        for (Arena a : arenas) {
            if (a.getGroup().equalsIgnoreCase(group)) {
                if (a.getStatus() == GameState.waiting) {
                    a.addPlayer(p, false);
                    return true;
                } else if (a.getStatus() == GameState.starting) {
                    if (a.getPlayers().size() < a.getMaxPlayers()) {
                        a.addPlayer(p, false);
                        return true;
                    } else if (a.getPlayers().size() <= a.getMaxPlayers() && a.isVip(p)) {
                        a.addPlayer(p, false);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Update the Join NPCs for a group
     *
     * @param group arena group
     * @since API 8
     */
    public static void updateNPCs(String group) {
        String x = String.valueOf(Arena.getPlayers(group));
        for (Map.Entry<ArmorStand, List<String>> e : npcs_holos.entrySet()) {
            if (e.getValue().get(0).equalsIgnoreCase(group)) {
                if (!(e.getKey() == null && e.getKey().isDead())) {
                    e.getKey().setCustomName(e.getValue().get(1).replace("{players}", x));
                }
            }
        }
    }

    public static void sendMultiarenaLobbyItems(Player p) {
        p.getInventory().clear();
        if (config.getBoolean("items.arenaGui.enable") && !config.getLobbyWorldName().isEmpty()) {
            p.getInventory().setItem(config.getInt("items.arenaGui.slot"), Misc.getArenaGUI(p));
        }
        if (getServerType() == ServerType.MULTIARENA && spigot.getBoolean("settings.bungeecord")) {
            leaveItem(p);
        }
        if (config.getBoolean("items.storage.enable") && !config.getLobbyWorldName().isEmpty()) {
            p.getInventory().setItem(config.getInt("items.storage.slot"), Misc.getStatsItem(p));
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
                    (byte) config.getInt("items.leave.data"), getMsg(p, Messages.ARENA_LEAVE_ITEM_NAME), getList(p, Messages.ARENA_LEAVE_ITEM_LORE)));
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
                if (winner != null) {
                    if (!winner.getMembers().isEmpty()) {
                        for (Player p : winner.getMembers()) {
                            if (!p.isOnline()) continue;
                            p.getInventory().clear();
                        }
                    }
                    String firstName = "", secondName = "", thirdName = "", winners = "";
                    for (Player p : winner.getMembers()) {
                        nms.sendTitle(p, getMsg(p, Messages.ARENA_VICTORY_PLAYER_TITLE), null, 0, 40, 0);
                        winners = winners + p.getName() + " ";
                    }
                    if (winners.endsWith(" ")) {
                        winners = winners.substring(0, winners.length() - 1);
                    }
                    int first = 0, second = 0, third = 0;
                    if (!playerKills.isEmpty()) {
                        for (Map.Entry<Player, Integer> e : playerKills.entrySet()) {
                            if (e.getKey() == null) continue;
                            if (e.getValue() > first) {
                                firstName = e.getKey().getName();
                                first = e.getValue();
                            } else if (e.getValue() > second) {
                                secondName = e.getKey().getName();
                                second = e.getValue();
                            } else if (e.getValue() > third) {
                                thirdName = e.getKey().getName();
                                third = e.getValue();
                            }
                        }
                    }
                    for (Player p : world.getPlayers()) {
                        p.sendMessage(getMsg(p, Messages.ARENA_TEAM_WON_CHAT).replace("{TeamColor}", TeamColor.getChatColor(winner.getColor()).toString()).replace("{TeamName}", winner.getName()));
                        if (!winner.getMembers().contains(p)) {
                            nms.sendTitle(p, getMsg(p, Messages.ARENA_GAME_OVER_PLAYER_TITLE), null, 0, 40, 0);
                        }
                        for (String s : getList(p, Messages.ARENA_GAME_OVER_PLAYER_CHAT)) {
                            p.sendMessage(s.replace("{firstName}", firstName.isEmpty() ? getMsg(p, Messages.MEANING_NOBODY) : firstName).replace("{firstKills}", String.valueOf(first))
                                    .replace("{secondName}", secondName.isEmpty() ? getMsg(p, Messages.MEANING_NOBODY) : secondName).replace("{secondKills}", String.valueOf(second))
                                    .replace("{thirdName}", thirdName.isEmpty() ? getMsg(p, Messages.MEANING_NOBODY) : thirdName).replace("{thirdKills}", String.valueOf(third))
                                    .replace("{winnerFormat}", getMaxInTeam() > 1 ? getMsg(p, Messages.FORMATTING_TEAM_WINNER_FORMAT).replace("{members}", winners) : getMsg(p, Messages.FORMATTING_SOLO_WINNER_FORMAT).replace("{members}", winners))
                                    .replace("{TeamColor}", TeamColor.getChatColor(winner.getColor()).toString()).replace("{TeamName}", winner.getName()));
                        }
                    }
                }
                setStatus(GameState.restarting);

                //Game end event
                List<UUID> winners = new ArrayList<>(), losers = new ArrayList<>(), aliveWinners = new ArrayList<>();
                for (Player p : getPlayers()) {
                    aliveWinners.add(p.getUniqueId());
                }
                if (winner != null) {
                    for (Player p : winner.getMembersCache()) {
                        winners.add(p.getUniqueId());
                    }
                }
                for (BedWarsTeam bwt : getTeams()) {
                    if (winner != null) {
                        if (bwt == winner) continue;
                    }
                    for (Player p : bwt.getMembersCache()) {
                        losers.add(p.getUniqueId());
                    }
                }
                Bukkit.getPluginManager().callEvent(new GameEndEvent(this, winners, losers, winner, aliveWinners));
                //

            }
            if (players.size() == 0 && getStatus() != GameState.restarting) {
                setStatus(GameState.restarting);
            }
        }
    }

    public void addPlayerDeath(Player p) {
        if (playerDeaths.containsKey(p)) {
            playerDeaths.replace(p, playerDeaths.get(p) + 1);
        } else {
            playerDeaths.put(p, 1);
        }
    }

    private void setNextEvent(NextEvent nextEvent) {
        for (Player p : getPlayers()) {
            p.getWorld().playSound(p.getLocation(), nms.bedDestroy(), 1f, 1f);
        }
        for (Player p : getSpectators()) {
            p.getWorld().playSound(p.getLocation(), nms.bedDestroy(), 1f, 1f);
        }
        this.nextEvent = nextEvent;
    }

    public void updateNextEvent() {
        if (nextEvent == NextEvent.DIAMOND_GENERATOR_TIER_II) {
            setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_III);
        } else if (nextEvent == NextEvent.DIAMOND_GENERATOR_TIER_III) {
            if (emeraldTier == 1) {
                setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_II);
            } else if (emeraldTier == 2) {
                setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_III);
            } else if (emeraldTier == 3) {
                setNextEvent(NextEvent.BEDS_DESTROY);
            }
        } else if (emeraldTier >= 3 && diamondTier >= 3 && bedsDestroyCountdown > 0) {
            setNextEvent(NextEvent.BEDS_DESTROY);
        } else if (nextEvent == NextEvent.BEDS_DESTROY && dragonCountdown > 0) {
            setNextEvent(NextEvent.ENDER_DRAGON);
        } else if (nextEvent == NextEvent.ENDER_DRAGON && dragonCountdown == 0 && bedsDestroyCountdown == 0) {
            setNextEvent(NextEvent.GAME_END);
        }
        debug(nextEvent.toString());
    }


    public static void launchFirework(Player p) {
        Color[] colors = {Color.WHITE, Color.AQUA, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.RED,
                Color.YELLOW, Color.BLACK, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE};
        Random r = new Random();
        Firework fw = p.getWorld().spawn(p.getEyeLocation(), Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        meta.setPower(1);
        meta.addEffect(FireworkEffect.builder()
                .withFade(colors[r.nextInt(colors.length - 1)])
                .withTrail().withColor(colors[r.nextInt(colors.length - 1)]).with(FireworkEffect.Type.BALL_LARGE).build());
        fw.setFireworkMeta(meta);
        fw.setVelocity(p.getEyeLocation().getDirection());
    }

    public static HashMap<Player, Arena> getArenaByPlayer() {
        return arenaByPlayer;
    }

    public NextEvent getNextEvent() {
        return nextEvent;
    }

    public int getBedsDestroyCountdown() {
        return bedsDestroyCountdown;
    }

    public int getDragonCountdown() {
        return dragonCountdown;
    }

    public int getGameEndCountdown() {
        return gameEndCountdown;
    }

    public void setCountdownS(int countdownS) {
        this.countdownS = countdownS;
    }

    /**
     * Get players count for a group
     *
     * @since API v8
     */
    public static int getPlayers(String group) {
        int i = 0;
        for (Arena a : getArenas()) {
            if (a.getGroup().equalsIgnoreCase(group)) i += a.getPlayers().size();
        }
        return i;
    }
}
