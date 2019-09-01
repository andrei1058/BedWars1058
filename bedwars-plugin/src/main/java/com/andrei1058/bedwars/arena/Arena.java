package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.NextEvent;
import com.andrei1058.bedwars.api.arena.generator.GeneratorType;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.player.PlayerJoinArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReJoinEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.server.ArenaDisableEvent;
import com.andrei1058.bedwars.api.events.server.ArenaEnableEvent;
import com.andrei1058.bedwars.api.events.server.ArenaRestartEvent;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.language.Messages;
import com.andrei1058.bedwars.levels.internal.InternalLevel;
import com.andrei1058.bedwars.levels.internal.PerMinuteTask;
import com.andrei1058.bedwars.listeners.blockstatus.BlockStatusListener;
import com.andrei1058.bedwars.region.Region;
import com.andrei1058.bedwars.shop.ShopCache;
import com.andrei1058.bedwars.support.citizens.JoinNPC;
import com.andrei1058.bedwars.arena.tasks.GamePlayingTask;
import com.andrei1058.bedwars.arena.tasks.GameRestartingTask;
import com.andrei1058.bedwars.arena.tasks.GameStartingTask;
import com.andrei1058.bedwars.arena.tasks.ReJoinTask;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.arena.upgrades.BaseListener.isOnABase;
import static com.andrei1058.bedwars.language.Language.*;

@SuppressWarnings("WeakerAccess")
public class Arena implements Comparable<Arena>, IArena {

    private static HashMap<String, Arena> arenaByName = new HashMap<>();
    private static HashMap<Player, Arena> arenaByPlayer = new HashMap<>();
    private static ArrayList<Arena> arenas = new ArrayList<>();
    private static int gamesBeforeRestart = config.getInt(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_MODE_GAMES_BEFORE_RESTART);
    public static HashMap<UUID, Integer> afkCheck = new HashMap<>();
    public static HashMap<UUID, Integer> magicMilk = new HashMap<>();


    private List<Player> players = new ArrayList<>();
    private List<Player> spectators = new ArrayList<>();
    private List<BlockState> signs = new ArrayList<>();
    private GameState status = GameState.waiting;
    private YamlConfiguration yml;
    private ConfigManager cm;
    private int minPlayers = 2, maxPlayers = 10, maxInTeam = 1, islandRadius = 10;
    public int upgradeDiamondsCount = 0, upgradeEmeraldsCount = 0;
    public boolean allowSpectate = true;
    private World world;
    private String group = "Default", worldName;
    private List<BedWarsTeam> teams = new ArrayList<>();
    private LinkedList<Block> placed = new LinkedList<>();
    private LinkedList<Block> broken = new LinkedList<>();
    private List<String> nextEvents = new ArrayList<>();
    private List<Region> regionsList = new ArrayList<>();

    /**
     * Current event, used at scoreboard
     */
    private NextEvent nextEvent = NextEvent.DIAMOND_GENERATOR_TIER_II;
    private int diamondTier = 1, emeraldTier = 1;

    /**
     * Players in respawn session
     */
    private ConcurrentHashMap<Player, Integer> respawn = new ConcurrentHashMap<>();

    /**
     * Invisibility for armor when you drink an invisibility potion
     */
    private ConcurrentHashMap<Player, Integer> showTime = new ConcurrentHashMap<>();

    /**
     * Player location before joining.
     * The player is teleported to this location if the server is running in SHARED mode.
     */
    private static HashMap<Player, Location> playerLocation = new HashMap<>();

    /**
     * temp stats
     */
    private HashMap<Player, Integer> playerKills = new HashMap<>();
    private HashMap<Player, Integer> playerBedsDestroyed = new HashMap<>();
    private HashMap<Player, Integer> playerFinalKills = new HashMap<>();
    private HashMap<Player, Integer> playerDeaths = new HashMap<>();
    private HashMap<Player, Integer> playerFinalKillDeaths = new HashMap<>();


    /* ARENA TASKS */
    private GameStartingTask startingTask = null;
    private GamePlayingTask playingTask = null;
    private GameRestartingTask restartingTask = null;

    /* ARENA GENERATORS */
    private List<OreGenerator> oreGenerators = new ArrayList<>();

    private PerMinuteTask perMinuteTask;

    private static LinkedList<IArena> enableQueue = new LinkedList<>();

    /**
     * Load an arena.
     * This will check if it was set up right.
     *
     * @param name - world name
     * @param p    - This will send messages to the player if something went wrong while loading the arena. Can be NULL.
     */
    public Arena(String name, Player p) {
        for (IArena mm : enableQueue) {
            if (mm.getWorldName().equalsIgnoreCase(name)) {
                plugin.getLogger().severe("Tried to load arena " + name + " but it is already in the enable queue.");
                if (p != null)
                    p.sendMessage(ChatColor.RED + "Tried to load arena " + name + " but it is already in the enable queue.");
                return;
            }
        }
        if (getArenaByName(name) != null) {
            plugin.getLogger().severe("Tried to load arena " + name + " but it is already enabled.");
            if (p != null) p.sendMessage(ChatColor.RED + "Tried to load arena " + name + " but it is already enabled.");
            return;
        }
        this.worldName = name;

        cm = new ConfigManager(Main.plugin, name, "plugins/" + plugin.getName() + "/Arenas");

        //if (mapManager.isLevelWorld()) {
        //    Main.plugin.getLogger().severe("COULD NOT LOAD ARENA: " + name);
        //    //return;
        //}

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
        islandRadius = yml.getInt(ConfigPath.ARENA_ISLAND_RADIUS);
        if (config.getYml().get("arenaGroups") != null) {
            if (config.getYml().getStringList("arenaGroups").contains(yml.getString("group"))) {
                group = yml.getString("group");
            }
        }


        if (!Main.getAPI().getRestoreAdapter().isWorld(name)) {
            if (p != null) p.sendMessage(ChatColor.RED + "There isn't any map called " + name);
            plugin.getLogger().log(Level.WARNING, "There isn't any map called " + name);
            return;
        }

        boolean error = false;
        for (String team : yml.getConfigurationSection("Team").getKeys(false)) {
            String colorS = yml.getString("Team." + team + ".Color");
            if (colorS == null) continue;
            colorS = colorS.toUpperCase();
            try {
                TeamColor.valueOf(colorS);
            } catch (Exception e) {
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
        }
        if (yml.get("generator.Emerald") == null) {
            if (p != null) p.sendMessage("§cThere isn't set any Emerald generator on: " + name);
            plugin.getLogger().severe("There isn't set any Emerald generator on: " + name);
        }
        if (yml.get("waiting.Loc") == null) {
            if (p != null) p.sendMessage("§cWaiting spawn not set on: " + name);
            plugin.getLogger().severe("Waiting spawn not set on: " + name);
            return;
        }
        if (error) return;

        enableQueue.add(this);
        plugin.getLogger().info("Arena " + getWorldName() + " was added to the enable queue.");
        if (enableQueue.size() == 1) {
            Main.getAPI().getRestoreAdapter().onEnable(this);
            plugin.getLogger().info("Loading arena: " + getWorldName());
        }
    }

    /**
     * Use this method when the world was loaded successfully.
     */
    @Override
    public void init(World world) {
        if (getArenaByName(worldName) != null) return;
        enableQueue.remove(this);
        if (!enableQueue.isEmpty()) {
            Main.getAPI().getRestoreAdapter().onEnable(enableQueue.get(0));
        }
        this.world = world;
        world.getEntities().stream().filter(e -> e.getType() != EntityType.PLAYER)
                .filter(e -> e.getType() != EntityType.PAINTING).filter(e -> e.getType() != EntityType.ITEM_FRAME)
                .forEach(Entity::remove);
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("announceAdvancements", "false");
        world.setAutoSave(false);
        //todo change item merge radius

        /* Clear setup armor-stands */
        for (Entity e : world.getEntities()) {
            if (e.getType() == EntityType.ARMOR_STAND) {
                if (!((ArmorStand) e).isVisible()) e.remove();
            }
        }

        //Create teams
        for (String team : yml.getConfigurationSection("Team").getKeys(false)) {
            if (getTeam(team) != null) {
                Main.plugin.getLogger().severe("A team with name: " + team + " was already loaded for arena: " + getWorldName());
                continue;
            }
            BedWarsTeam bwt = new BedWarsTeam(team, TeamColor.valueOf(yml.getString("Team." + team + ".Color").toUpperCase()), cm.getArenaLoc("Team." + team + ".Spawn"),
                    cm.getArenaLoc("Team." + team + ".Bed"), cm.getArenaLoc("Team." + team + ".Shop"), cm.getArenaLoc("Team." + team + ".Upgrade"), this);
            teams.add(bwt);
            bwt.setGenerators(getConfig().getArenaLoc("Team." + bwt.getName() + ".Iron"), getConfig().getArenaLoc("Team." + bwt.getName() + ".Gold"));
        }

        //Load diamond/ emerald generators
        Location location;
        for (String type : Arrays.asList("Diamond", "Emerald")) {
            if (yml.get("generator." + type) != null) {
                for (String s : yml.getStringList("generator." + type)) {
                    location = cm.convertStringToArenaLocation(s);
                    if (location == null) {
                        plugin.getLogger().severe("Invalid location for " + type + " generator: " + s);
                        continue;
                    }
                    oreGenerators.add(new OreGenerator(location, this, GeneratorType.valueOf(type.toUpperCase()), null));
                }
            }
        }

        arenas.add(this);
        arenaByName.put(world.getName(), this);
        world.getWorldBorder().setCenter(cm.getArenaLoc("waiting.Loc"));
        world.getWorldBorder().setSize(yml.getInt("worldBorder"));

        /* Check if lobby removal is set */
        if (!getConfig().getYml().isSet(ConfigPath.ARENA_WAITING_POS1) && getConfig().getYml().isSet(ConfigPath.ARENA_WAITING_POS2)) {
            plugin.getLogger().severe("Lobby Pos1 isn't set! The arena's lobby won't be removed!");
        }
        if (getConfig().getYml().isSet(ConfigPath.ARENA_WAITING_POS1) && !getConfig().getYml().isSet(ConfigPath.ARENA_WAITING_POS2)) {
            plugin.getLogger().severe("Lobby Pos2 isn't set! The arena's lobby won't be removed!");
        }

        /* Register arena signs */
        registerSigns();
        //Call event
        Bukkit.getPluginManager().callEvent(new ArenaEnableEvent(this));

        changeStatus(GameState.waiting);

        //
        for (NextEvent ne : NextEvent.values()) {
            nextEvents.add(ne.toString());
        }

        upgradeDiamondsCount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_START) == null ?
                "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_II_START : getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_START);
        upgradeEmeraldsCount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_START) == null ?
                "Default." + ConfigPath.GENERATOR_EMERALD_TIER_II_START : getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_START);
        plugin.getLogger().info("Load done: " + getWorldName());

    }

    /**
     * Add a player to the arena
     *
     * @param p              - Player to add.
     * @param skipOwnerCheck - True if you want to skip the party checking for this player. This
     * @return true if was added.
     */
    public boolean addPlayer(Player p, boolean skipOwnerCheck) {
        debug("Player added: " + p.getName() + " arena: " + getWorldName());
        /* used for base enter/leave event */
        isOnABase.remove(p);
        //
        if (getArenaByPlayer(p) != null) {
            return false;
        }
        if (getParty().hasParty(p)) {
            if (!skipOwnerCheck) {
                if (!getParty().isOwner(p)) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_JOIN_DENIED_NOT_PARTY_LEADER));
                    return false;
                }
                if (getParty().partySize(p) > maxInTeam * getTeams().size() - getPlayers().size()) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_JOIN_DENIED_PARTY_TOO_BIG));
                    return false;
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

        if (status == GameState.waiting || (status == GameState.starting && (startingTask != null && startingTask.getCountdown() > 1))) {
            if (players.size() >= maxPlayers && !isVip(p)) {
                TextComponent text = new TextComponent(getMsg(p, Messages.COMMAND_JOIN_DENIED_IS_FULL));
                text.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, config.getYml().getString("storeLink")));
                p.spigot().sendMessage(text);
                return false;
            } else if (players.size() >= maxPlayers && isVip(p)) {
                boolean canJoin = false;
                for (Player on : new ArrayList<>(players)) {
                    if (!isVip(on)) {
                        canJoin = true;
                        removePlayer(on, false);
                        TextComponent vipKick = new TextComponent(getMsg(p, Messages.ARENA_JOIN_VIP_KICK));
                        vipKick.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, config.getYml().getString("storeLink")));
                        p.spigot().sendMessage(vipKick);
                        break;
                    }
                }
                if (!canJoin) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS));
                    return false;
                }
            }

            PlayerJoinArenaEvent ev = new PlayerJoinArenaEvent(this, p, false);
            Bukkit.getPluginManager().callEvent(ev);
            if (ev.isCancelled()) return false;

            //Remove from ReJoin
            if (ReJoin.exists(p)) //noinspection ConstantConditions
                ReJoin.getPlayer(p).destroy();

            p.closeInventory();
            players.add(p);
            p.setFlying(false);
            p.setAllowFlight(false);
            for (Player on : players) {
                on.sendMessage(getMsg(on, Messages.COMMAND_JOIN_PLAYER_JOIN_MSG).replace("{player}", p.getDisplayName()).replace("{on}", String.valueOf(getPlayers().size())).replace("{max}", String.valueOf(getMaxPlayers())));
            }
            setArenaByPlayer(p);

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
                    changeStatus(GameState.starting);
                } else if (players.size() >= minPlayers && teams == 0) {
                    changeStatus(GameState.starting);
                }
            }

            //half full arena time shorten
            if (players.size() >= (teams.size() * maxInTeam / 2)) {
                if (startingTask != null) {
                    if (Bukkit.getScheduler().isCurrentlyRunning(startingTask.getTask())) {
                        if (startingTask.getCountdown() > getConfig().getInt(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_HALF))
                            startingTask.setCountdown(Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_HALF));
                    }
                }
            }

            /* save player inventory etc */
            if (getServerType() != ServerType.BUNGEE) {
                new PlayerGoods(p, true);
                playerLocation.put(p, p.getLocation());
            }
            p.teleport(cm.getArenaLoc("waiting.Loc"), PlayerTeleportEvent.TeleportCause.PLUGIN);
            if (getStatus() == GameState.waiting) {
                Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                    if (p.isOnline())
                        new SBoard(p, getScoreboard(p, "scoreboard." + getGroup() + ".waiting", Messages.SCOREBOARD_DEFAULT_WAITING), this);
                }, 15L);
            } else if (getStatus() == GameState.starting) {
                Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                    if (p.isOnline())
                        new SBoard(p, getScoreboard(p, "scoreboard." + getGroup() + ".starting", Messages.SCOREBOARD_DEFAULT_STARTING), this);
                }, 15L);
            }
            sendPreGameCommandItems(p);
        } else if (status == GameState.playing) {
            addSpectator(p, false, null);
            /* stop code if stauts playing*/
            return false;
        }
        for (Player on : Bukkit.getOnlinePlayers()) {
            if (getPlayers().contains(on)) {
                on.showPlayer(p);
                p.showPlayer(on);
            } else {
                on.hidePlayer(p);
                p.hidePlayer(on);
            }
        }
        if (getPlayers().size() == getMaxInTeam() * getTeams().size()) {
            if (startingTask != null) {
                if (Bukkit.getScheduler().isCurrentlyRunning(startingTask.getTask())) {
                    startingTask.setCountdown(Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_SHORTENED));
                }
            }
        }

        refreshSigns();
        JoinNPC.updateNPCs(getGroup());
        return true;
    }

    /**
     * Add a player as Spectator
     *
     * @param p            Player to be added
     * @param playerBefore True if the player has played in this arena before and he died so now should be a spectator.
     */
    public boolean addSpectator(Player p, boolean playerBefore, Location staffTeleport) {
        debug("Spectator added: " + p.getName() + " arena: " + getWorldName());
        if (allowSpectate || playerBefore) {

            if (!playerBefore) {
                PlayerJoinArenaEvent ev = new PlayerJoinArenaEvent(this, p, true);
                Bukkit.getPluginManager().callEvent(ev);
                if (ev.isCancelled()) return false;
            }

            //Remove from ReJoin
            if (ReJoin.exists(p)) //noinspection ConstantConditions
                ReJoin.getPlayer(p).destroy();

            p.closeInventory();
            spectators.add(p);
            players.remove(p);

            for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
                if (sb.getP().equals(p)) {
                    sb.remove();
                }
            }

            if (!playerBefore) {
                /* save player inv etc if isn't saved yet*/
                if (getServerType() != ServerType.BUNGEE) {
                    new PlayerGoods(p, true);
                    playerLocation.put(p, p.getLocation());
                }
                setArenaByPlayer(p);
            }

            Bukkit.getScheduler().runTaskLater(plugin, () -> new SBoard(p, this), 35L);
            nms.setCollide(p, this, false);

            if (!playerBefore) {
                if (staffTeleport == null) {
                    p.teleport(cm.getArenaLoc("waiting.Loc"), PlayerTeleportEvent.TeleportCause.PLUGIN);
                } else {
                    p.teleport(staffTeleport, PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
            }

            /* Hide spectator  */
            //p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0), true);
            p.setGameMode(GameMode.ADVENTURE);

            p.setAllowFlight(true);
            p.setFlying(true);

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                for (Player on : Bukkit.getOnlinePlayers()) {
                    if (on == p) continue;
                    if (getSpectators().contains(on)) {
                        on.showPlayer(p);
                        p.showPlayer(on);
                    } else if (getPlayers().contains(on)) {
                        on.hidePlayer(p);
                        p.showPlayer(on);
                    }
                }

                if (!playerBefore) {
                    if (staffTeleport == null) {
                        p.teleport(cm.getArenaLoc("waiting.Loc"), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    } else {
                        p.teleport(staffTeleport);
                    }
                } else {
                    p.teleport(p.getLocation());
                }

                /* Spectator items */
                sendSpectatorCommandItems(p);

            }, 25L);

            p.sendMessage(getMsg(p, Messages.COMMAND_JOIN_SPECTATOR_MSG).replace("{arena}", this.getDisplayName()));

            /* update generator holograms for spectators */
            String iso = Language.getPlayerLanguage(p).getIso();
            for (OreGenerator o : getOreGenerators()) {
                o.updateHolograms(p, iso);
            }
            for (ShopHolo sh : ShopHolo.getShopHolo()) {
                if (sh.getA() == this) {
                    sh.updateForPlayer(p, iso);
                }
            }

        } else {
            p.sendMessage(getMsg(p, Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG));
        }

        showTime.remove(p);
        refreshSigns();
        JoinNPC.updateNPCs(getGroup());
        return true;
    }

    /**
     * Remove a player from the arena
     *
     * @param p          Player to be removed
     * @param disconnect True if the player was disconnected
     */
    public void removePlayer(Player p, boolean disconnect) {
        debug("Player removed: " + p.getName() + " arena: " + getWorldName());
        respawn.remove(p);

        BedWarsTeam team = null;

        if (getStatus() == GameState.playing) {
            for (BedWarsTeam t : getTeams()) {
                if (t.isMember(p)) {
                    team = t;
                    t.getMembers().remove(p);
                    if (t.getBedHolo(p) != null) {
                        t.getBedHolo(p).destroy();
                    }
                }
            }
        }

        List<ShopCache.CachedItem> cacheList = new ArrayList<>();
        if (ShopCache.getShopCache(p.getUniqueId()) != null) {
            //noinspection ConstantConditions
            cacheList = ShopCache.getShopCache(p.getUniqueId()).getCachedPermanents();
        }

        Bukkit.getPluginManager().callEvent(new PlayerLeaveArenaEvent(p, this));
        //players.remove must be under call event in order to check if the player is a spectator or not
        players.remove(p);
        removeArenaByPlayer(p);

        for (PotionEffect pf : p.getActivePotionEffects()) {
            p.removePotionEffect(pf.getType());
        }

        if (getServerType() != ServerType.BUNGEE) {
            /* restore player inventory */
            PlayerGoods pg = PlayerGoods.getPlayerGoods(p);
            if (pg != null) {
                pg.restore();
            }
        }

        boolean teamuri = false;
        for (Player on : getPlayers()) {
            if (getParty().hasParty(on)) {
                teamuri = true;
            }
        }
        if (status == GameState.starting && (maxInTeam > players.size() && teamuri || players.size() < minPlayers && !teamuri)) {
            changeStatus(GameState.waiting);
            for (Player on : players) {
                on.sendMessage(getMsg(on, Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS));
            }
        } else if (status == GameState.playing) {
            int alive_teams = 0;
            for (BedWarsTeam t : getTeams()) {
                if (t == null) continue;
                if (!t.getMembers().isEmpty()) {
                    alive_teams++;
                }
            }
            if (alive_teams == 1) {
                checkWinner();
                Bukkit.getScheduler().runTaskLater(Main.plugin, () -> changeStatus(GameState.restarting), 10L);
                if (team != null) {
                    if (!team.isBedDestroyed()) {
                        for (Player p2 : this.getPlayers()) {
                            p2.sendMessage(getMsg(p2, Messages.TEAM_ELIMINATED_CHAT).replace("{TeamColor}", TeamColor.getChatColor(team.getColor()).toString()).replace("{TeamName}", team.getName()));
                        }
                        for (Player p2 : this.getSpectators()) {
                            p2.sendMessage(getMsg(p2, Messages.TEAM_ELIMINATED_CHAT).replace("{TeamColor}", TeamColor.getChatColor(team.getColor()).toString()).replace("{TeamName}", team.getName()));
                        }
                    }
                }
            } else if (alive_teams == 0) {
                Bukkit.getScheduler().runTaskLater(Main.plugin, () -> changeStatus(GameState.restarting), 10L);
            } else {
                //ReJoin feature
                new ReJoin(p, this, getPlayerTeam(p.getName()), cacheList);
            }
        }
        if (status == GameState.starting || status == GameState.waiting) {
            for (Player on : players) {
                on.sendMessage(getMsg(on, Messages.COMMAND_LEAVE_MSG).replace("{player}", p.getDisplayName()));
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

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
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

        /* Remove also the party */
        if (getParty().hasParty(p)) {
            if (getParty().isOwner(p)) {
                if (status != GameState.restarting) {
                    for (Player mem : new ArrayList<>(getParty().getMembers(p))) {
                        mem.sendMessage(getMsg(mem, Messages.ARENA_LEAVE_PARTY_DISBANDED));
                    }
                    getParty().disband(p);
                }
            }
        }
        p.setFlying(false);
        p.setAllowFlight(false);

        //Remove from ReJoin if game ended
        if (status == GameState.restarting) {
            if (ReJoin.exists(p)) {
                //noinspection ConstantConditions
                if (ReJoin.getPlayer(p).getArena() == this) {
                    //noinspection ConstantConditions
                    ReJoin.getPlayer(p).destroy();
                }
            }
        }

        //Remove from magic milk
        if (magicMilk.containsKey(p.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(magicMilk.get(p.getUniqueId()));
            magicMilk.remove(p.getUniqueId());
        }

        showTime.remove(p);

        refreshSigns();
        JoinNPC.updateNPCs(getGroup());
    }

    /**
     * Remove a spectator from the arena
     *
     * @param p          Player to be removed
     * @param disconnect True if the player was disconnected
     */
    public void removeSpectator(Player p, boolean disconnect) {
        debug("Spectator removed: " + p.getName() + " arena: " + getWorldName());
        Bukkit.getPluginManager().callEvent(new PlayerLeaveArenaEvent(p, this));
        spectators.remove(p);
        removeArenaByPlayer(p);
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

        /* Remove also the party */
        if (getParty().hasParty(p)) {
            if (getParty().isOwner(p)) {
                if (status != GameState.restarting) {
                    for (Player mem : new ArrayList<>(getParty().getMembers(p))) {
                        mem.sendMessage(getMsg(mem, Messages.ARENA_LEAVE_PARTY_DISBANDED));
                    }
                    getParty().disband(p);
                }
            }
        }

        p.setFlying(false);
        p.setAllowFlight(false);

        //Remove from ReJoin if game ended
        if (ReJoin.exists(p)) {
            //noinspection ConstantConditions
            if (ReJoin.getPlayer(p).getArena() == this) {
                //noinspection ConstantConditions
                ReJoin.getPlayer(p).destroy();
            }
        }

        //Remove from magic milk
        if (magicMilk.containsKey(p.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(magicMilk.get(p.getUniqueId()));
            magicMilk.remove(p.getUniqueId());
        }

        refreshSigns();
        JoinNPC.updateNPCs(getGroup());
    }

    /**
     * Rejoin an arena
     */
    public boolean reJoin(ReJoin reJoin, Player p) {
        if (reJoin.getArena() != this) return false;
        if (!reJoin.canReJoin()) return false;

        if (reJoin.getTask() != null) {
            reJoin.getTask().destroy();
        }

        PlayerReJoinEvent ev = new PlayerReJoinEvent(p, this);
        Bukkit.getPluginManager().callEvent(ev);
        if (ev.isCancelled()) return false;

        for (Player on : Bukkit.getOnlinePlayers()) {
            if (getPlayers().contains(on)) {
                on.showPlayer(p);
                p.showPlayer(on);
            } else {
                on.hidePlayer(p);
                p.hidePlayer(on);
            }
        }

        p.closeInventory();
        players.add(p);
        for (Player on : players) {
            on.sendMessage(getMsg(on, Messages.COMMAND_REJOIN_PLAYER_RECONNECTED).replace("{player}", p.getDisplayName()).replace("{on}", String.valueOf(getPlayers().size())).replace("{max}", String.valueOf(getMaxPlayers())));
        }
        for (Player on : spectators) {
            on.sendMessage(getMsg(on, Messages.COMMAND_REJOIN_PLAYER_RECONNECTED).replace("{player}", p.getDisplayName()).replace("{on}", String.valueOf(getPlayers().size())).replace("{max}", String.valueOf(getMaxPlayers())));
        }
        setArenaByPlayer(p);
        /* save player inventory etc */
        if (Main.getServerType() != ServerType.BUNGEE) {
            new PlayerGoods(p, true);
            playerLocation.put(p, p.getLocation());
        }

        p.teleport(getConfig().getArenaLoc("waiting.Loc"));
        p.getInventory().clear();

        //restore items before re-spawning in team
        ShopCache sc = ShopCache.getShopCache(p.getUniqueId());
        if (sc != null) sc.destroy();
        sc = new ShopCache(p.getUniqueId());
        for (ShopCache.CachedItem ci : reJoin.getPermanentsAndNonDowngradables()) {
            sc.getCachedItems().add(ci);
        }

        reJoin.getBwt().reJoin(p);
        reJoin.destroy();

        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> new SBoard(p, getScoreboard(p, "scoreboard." + getGroup() + ".playing", Messages.SCOREBOARD_DEFAULT_PLAYING), this), 40L);
        return true;
    }

    /**
     * Disable the arena.
     * This will automatically kick/ remove the people from the arena.
     */
    public void disable() {
        plugin.getLogger().log(Level.WARNING, "Disabling arena: " + getWorldName());
        for (Player p : players) {
            removePlayer(p, false);
        }
        for (Player p : spectators) {
            removeSpectator(p, false);
        }
        destroyData();
        Main.getAPI().getRestoreAdapter().onDisable(this);
        Bukkit.getPluginManager().callEvent(new ArenaDisableEvent(getWorldName()));
    }

    /**
     * Restart the arena.
     */
    public void restart() {
        plugin.getLogger().log(Level.FINE, "Restarting arena: " + getWorldName());
        destroyData();
        Main.getAPI().getRestoreAdapter().onRestart(this);
        Bukkit.getPluginManager().callEvent(new ArenaRestartEvent(getWorldName()));
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

    @Override
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
    public static ArrayList<Arena> getArenas() {
        return arenas;
    }

    /**
     * Get the display status for an arena.
     * A message that can be used on signs etc.
     */
    public String getDisplayStatus(Language lang) {
        String s = "";
        switch (status) {
            case waiting:
                s = lang.m(Messages.ARENA_STATUS_WAITING_NAME);
                break;
            case starting:
                s = lang.m(Messages.ARENA_STATUS_STARTING_NAME);
                break;
            case restarting:
                s = lang.m(Messages.ARENA_STATUS_RESTARTING_NAME);
                break;
            case playing:
                s = lang.m(Messages.ARENA_STATUS_PLAYING_NAME);
                break;
        }
        return s.replace("{full}", this.getPlayers().size() == this.getMaxPlayers() ? lang.m(Messages.MEANING_FULL) : "");
    }

    /**
     * Get the players list
     */
    @Override
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Get the max number of players that can play on this arena.
     */
    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Get the arena name as a message that can be used on signs etc.
     *
     * @return A string with - and _ replaced by a space.
     */
    @Override
    public String getDisplayName() {
        return getConfig().getString(ConfigPath.ARENA_DISPLAY_NAME).trim().isEmpty() ? (Character.toUpperCase(worldName.charAt(0)) + worldName.substring(1)).replace("_", " ").replace("-", " ")
                : getConfig().getString(ConfigPath.ARENA_DISPLAY_NAME);
    }

    /**
     * Get the arena's group.
     */
    @Override
    public String getGroup() {
        return group;
    }

    /**
     * Get the arena's world name.
     */
    @Override
    public String getWorldName() {
        return worldName;
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
    @Override
    public ConfigManager getConfig() {
        return cm;
    }

    /**
     * Add placed block to cache.
     */
    public void addPlacedBlock(Block block) {
        placed.add(block);
    }

    /**
     * Remove placed block.
     */
    public void removePlacedBlock(Block block) {
        placed.remove(block);
    }

    /**
     * Get the placed blocks list.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isBlockPlaced(Block block) {
        return placed.contains(block);
    }

    /**
     * Get a player kills count.
     *
     * @param p          Target player
     * @param finalKills True if you want to get the Final Kills. False for regular kills.
     */
    int getPlayerKills(Player p, boolean finalKills) {
        if (finalKills) return playerFinalKills.getOrDefault(p, 0);
        return playerKills.getOrDefault(p, 0);
    }

    /**
     * Get the player beds destroyed count
     *
     * @param p Target player
     */
    int getPlayerBedsDestroyed(Player p) {
        if (playerBedsDestroyed.containsKey(p)) return playerBedsDestroyed.get(p);
        return 0;
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

    private void setArenaByPlayer(Player p) {
        arenaByPlayer.put(p, this);
        refreshSigns();
        JoinNPC.updateNPCs(getGroup());
    }

    private void removeArenaByPlayer(Player p) {
        arenaByPlayer.remove(p, this);
        refreshSigns();
        JoinNPC.updateNPCs(getGroup());
    }

    /**
     * Set game status without starting stats.
     */
    public void setStatus(GameState status) {
        this.status = status;
    }

    /**
     * Change game status starting tasks.
     */
    public void changeStatus(GameState status) {
        GameState old = status;
        this.status = status;
        Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(this, status, old));
        refreshSigns();

        //Stop active tasks to prevent issues
        BukkitScheduler bs = Bukkit.getScheduler();
        if (startingTask != null) {
            if (bs.isCurrentlyRunning(startingTask.getTask()) || bs.isQueued(startingTask.getTask()))
                startingTask.cancel();
        }
        startingTask = null;

        if (playingTask != null) {
            if (bs.isCurrentlyRunning(playingTask.getTask()) || bs.isQueued(playingTask.getTask()))
                playingTask.cancel();
        }
        playingTask = null;

        if (restartingTask != null) {
            if (bs.isCurrentlyRunning(restartingTask.getTask()) || bs.isQueued(restartingTask.getTask()))
                restartingTask.cancel();
        }
        restartingTask = null;

        if (status == GameState.starting) {
            for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
                if (sb.getArena() == this) {
                    sb.setStrings(getScoreboard(sb.getP(), "scoreboard." + getGroup() + ".Starting", Messages.SCOREBOARD_DEFAULT_STARTING));
                }
            }
            startingTask = new GameStartingTask(this);
        } else if (status == GameState.waiting) {
            for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
                if (sb.getArena() == this) {
                    sb.setStrings(getScoreboard(sb.getP(), "scoreboard." + getGroup() + ".Waiting", Messages.SCOREBOARD_DEFAULT_WAITING));
                }
            }
        } else if (status == GameState.playing) {
            if (Main.getLevelSupport() instanceof InternalLevel) perMinuteTask = new PerMinuteTask(this);
            playingTask = new GamePlayingTask(this);
            for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
                if (sb.getArena() == this) {
                    sb.setStrings(getScoreboard(sb.getP(), "scoreboard." + getGroup() + ".playing", Messages.SCOREBOARD_DEFAULT_PLAYING));
                    sb.giveTeamColorTag();
                }
            }
        } else if (status == GameState.restarting) {
            restartingTask = new GameRestartingTask(this);
            if (perMinuteTask != null) perMinuteTask.cancel();
        }
    }

    /**
     * Check if a player has vip perms
     */
    public static boolean isVip(Player p) {
        return p.hasPermission(mainCmd + ".*") || p.hasPermission(mainCmd + ".vip");
    }

    /**
     * Check if a player is playing.
     */
    @Override
    public boolean isPlayer(Player p) {
        return players.contains(p);
    }

    /**
     * Check if a player is spectating.
     */
    @Override
    public boolean isSpectator(Player p) {
        return spectators.contains(p);
    }

    /**
     * Check if player is respawning.
     */
    public boolean isRespawning(Player p) {
        return respawn.containsKey(p);
    }

    /**
     * Add a join sign for the arena.
     */
    public void addSign(Location loc) {
        if (loc == null) return;
        if (loc.getBlock().getType() == Material.SIGN || loc.getBlock().getType() == Material.WALL_SIGN) {
            signs.add(loc.getBlock().getState());
            refreshSigns();
            BlockStatusListener.updateBlock(this);
        }
    }

    /**
     * Get game stage.
     */
    @Override
    public GameState getStatus() {
        return status;
    }


    /**
     * Refresh signs.
     */
    @SuppressWarnings("WeakerAccess")
    public void refreshSigns() {
        for (BlockState b : getSigns()) {
            Sign s = (Sign) b;
            int line = 0;
            for (String string : Main.signs.getList("format")) {
                s.setLine(line, string.replace("[on]", String.valueOf(getPlayers().size())).replace("[max]", String.valueOf(getMaxPlayers())).replace("[arena]", getDisplayName()).replace("[status]", getDisplayStatus(Main.lang)));
                line++;
            }
            s.update();
        }
    }

    /**
     * Get a list of spectators for this arena.
     */
    @Override
    public List<Player> getSpectators() {
        return spectators;
    }

    /**
     * Add a kill point to the game stats.
     */
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

    /**
     * Add a destroyed bed poin to the player temp stats.
     */
    public void addPlayerBedDestroyed(Player p) {
        if (playerBedsDestroyed.containsKey(p)) {
            playerBedsDestroyed.replace(p, playerBedsDestroyed.get(p) + 1);
            return;
        }
        playerBedsDestroyed.put(p, 1);
    }

    /**
     * This will give the lobby items to the player.
     * Not used in serverType BUNGEE.
     * This will clear the inventory first.
     */
    public static void sendLobbyCommandItems(Player p) {
        if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH) == null) return;
        p.getInventory().clear();

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            for (String item : config.getYml().getConfigurationSection(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH).getKeys(false)) {
                if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_MATERIAL.replace("%path%", item)) == null) {
                    Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_MATERIAL.replace("%path%", item) + " is not set!");
                    continue;
                }
                if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_DATA.replace("%path%", item)) == null) {
                    Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_DATA.replace("%path%", item) + " is not set!");
                    continue;
                }
                if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_SLOT.replace("%path%", item)) == null) {
                    Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_SLOT.replace("%path%", item) + " is not set!");
                    continue;
                }
                if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_ENCHANTED.replace("%path%", item)) == null) {
                    Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_ENCHANTED.replace("%path%", item) + " is not set!");
                    continue;
                }
                if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_COMMAND.replace("%path%", item)) == null) {
                    Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_COMMAND.replace("%path%", item) + " is not set!");
                    continue;
                }
                ItemStack i = Misc.createItem(Material.valueOf(config.getYml().getString(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_MATERIAL.replace("%path%", item))),
                        (byte) config.getInt(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_DATA.replace("%path%", item)),
                        config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_ENCHANTED.replace("%path%", item)),
                        getMsg(p, Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", item)), getList(p, Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", item)),
                        p, "RUNCOMMAND", config.getYml().getString(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_COMMAND.replace("%path%", item)));

                p.getInventory().setItem(config.getInt(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_SLOT.replace("%path%", item)), i);
            }
        }, 15L);
    }

    /**
     * This will give the pre-game command Items.
     * This will clear the inventory first.
     */
    private void sendPreGameCommandItems(Player p) {
        if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH) == null) return;
        p.getInventory().clear();

        for (String item : config.getYml().getConfigurationSection(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH).getKeys(false)) {
            if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_MATERIAL.replace("%path%", item)) == null) {
                Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_MATERIAL.replace("%path%", item) + " is not set!");
                continue;
            }
            if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_DATA.replace("%path%", item)) == null) {
                Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_DATA.replace("%path%", item) + " is not set!");
                continue;
            }
            if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_SLOT.replace("%path%", item)) == null) {
                Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_SLOT.replace("%path%", item) + " is not set!");
                continue;
            }
            if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_ENCHANTED.replace("%path%", item)) == null) {
                Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_ENCHANTED.replace("%path%", item) + " is not set!");
                continue;
            }
            if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_COMMAND.replace("%path%", item)) == null) {
                Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_COMMAND.replace("%path%", item) + " is not set!");
                continue;
            }
            ItemStack i = Misc.createItem(Material.valueOf(config.getYml().getString(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_MATERIAL.replace("%path%", item))),
                    (byte) config.getInt(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_DATA.replace("%path%", item)),
                    config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_ENCHANTED.replace("%path%", item)),
                    getMsg(p, Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", item)), getList(p, Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", item)),
                    p, "RUNCOMMAND", config.getYml().getString(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_COMMAND.replace("%path%", item)));

            p.getInventory().setItem(config.getInt(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_SLOT.replace("%path%", item)), i);
        }
    }

    /**
     * This will give the spectator command Items.
     * This will clear the inventory first.
     */
    public void sendSpectatorCommandItems(Player p) {
        if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH) == null) return;
        p.getInventory().clear();

        for (String item : config.getYml().getConfigurationSection(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH).getKeys(false)) {
            if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_MATERIAL.replace("%path%", item)) == null) {
                Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_MATERIAL.replace("%path%", item) + " is not set!");
                continue;
            }
            if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_DATA.replace("%path%", item)) == null) {
                Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_DATA.replace("%path%", item) + " is not set!");
                continue;
            }
            if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_SLOT.replace("%path%", item)) == null) {
                Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_SLOT.replace("%path%", item) + " is not set!");
                continue;
            }
            if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_ENCHANTED.replace("%path%", item)) == null) {
                Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_ENCHANTED.replace("%path%", item) + " is not set!");
                continue;
            }
            if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_COMMAND.replace("%path%", item)) == null) {
                Main.plugin.getLogger().severe(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_COMMAND.replace("%path%", item) + " is not set!");
                continue;
            }
            ItemStack i = Misc.createItem(Material.valueOf(config.getYml().getString(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_MATERIAL.replace("%path%", item))),
                    (byte) config.getInt(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_DATA.replace("%path%", item)),
                    config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_ENCHANTED.replace("%path%", item)),
                    getMsg(p, Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", item)), getList(p, Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", item)),
                    p, "RUNCOMMAND", config.getYml().getString(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_COMMAND.replace("%path%", item)));

            p.getInventory().setItem(config.getInt(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_SLOT.replace("%path%", item)), i);
        }
    }

    /**
     * Check if a player is in the arena.
     *
     * @return true if is playing or spectating.
     */
    public static boolean isInArena(Player p) {
        return arenaByPlayer.containsKey(p);
    }

    /**
     * Get team by player.
     * Make sure the player is in this arena first.
     */
    @Override
    public BedWarsTeam getTeam(Player p) {
        for (BedWarsTeam t : getTeams()) {
            if (t.isMember(p)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Get ex team by player.
     * Check the team where he played before leaving or losing.
     */
    @Override
    public BedWarsTeam getExTeam(UUID p) {
        for (BedWarsTeam t : getTeams()) {
            if (t.wasMember(p)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Get arena by player name.
     * Used to get the team for a player that has left the arena.
     * Make sure the player is in this arena first.
     */
    @SuppressWarnings("WeakerAccess")
    public BedWarsTeam getPlayerTeam(String playerCache) {
        for (BedWarsTeam t : getTeams()) {
            for (Player p : t.getMembersCache()) {
                if (p.getName().equals(playerCache)) return t;
            }
        }
        return null;
    }

    /**
     * Check winner. You can always do that.
     * It will manage the arena restart and the needed stuff.
     */
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
                    String firstName = "";
                    String secondName = "";
                    String thirdName = "";
                    StringBuilder winners = new StringBuilder();
                    for (Player p : winner.getMembers()) {
                        nms.sendTitle(p, getMsg(p, Messages.GAME_END_VICTORY_PLAYER_TITLE), null, 0, 40, 0);
                        winners.append(p.getName()).append(" ");
                    }
                    if (winners.toString().endsWith(" ")) {
                        winners = new StringBuilder(winners.substring(0, winners.length() - 1));
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
                        p.sendMessage(getMsg(p, Messages.GAME_END_TEAM_WON_CHAT).replace("{TeamColor}", TeamColor.getChatColor(winner.getColor()).toString()).replace("{TeamName}", winner.getName()));
                        if (!winner.getMembers().contains(p)) {
                            nms.sendTitle(p, getMsg(p, Messages.GAME_END_GAME_OVER_PLAYER_TITLE), null, 0, 40, 0);
                        }
                        for (String s : getList(p, Messages.GAME_END_TOP_PLAYER_CHAT)) {
                            p.sendMessage(s.replace("{firstName}", firstName.isEmpty() ? getMsg(p, Messages.MEANING_NOBODY) : firstName).replace("{firstKills}", String.valueOf(first))
                                    .replace("{secondName}", secondName.isEmpty() ? getMsg(p, Messages.MEANING_NOBODY) : secondName).replace("{secondKills}", String.valueOf(second))
                                    .replace("{thirdName}", thirdName.isEmpty() ? getMsg(p, Messages.MEANING_NOBODY) : thirdName).replace("{thirdKills}", String.valueOf(third))
                                    .replace("{winnerFormat}", getMaxInTeam() > 1 ? getMsg(p, Messages.FORMATTING_TEAM_WINNER_FORMAT).replace("{members}", winners.toString()) : getMsg(p, Messages.FORMATTING_SOLO_WINNER_FORMAT).replace("{members}", winners.toString()))
                                    .replace("{TeamColor}", TeamColor.getChatColor(winner.getColor()).toString()).replace("{TeamName}", winner.getName()));
                        }
                    }
                }
                changeStatus(GameState.restarting);

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
                changeStatus(GameState.restarting);
            }
        }
    }

    /**
     * Add a kill to the player temp stats.
     */
    public void addPlayerDeath(Player p) {
        if (playerDeaths.containsKey(p)) {
            playerDeaths.replace(p, playerDeaths.get(p) + 1);
        } else {
            playerDeaths.put(p, 1);
        }
    }


    /**
     * Set next event for the arena.
     */
    public void setNextEvent(NextEvent nextEvent) {
        for (Player p : getPlayers()) {
            p.getWorld().playSound(p.getLocation(), nms.bedDestroy(), 1f, 1f);
        }
        for (Player p : getSpectators()) {
            p.getWorld().playSound(p.getLocation(), nms.bedDestroy(), 1f, 1f);
        }
        this.nextEvent = nextEvent;
    }

    /**
     * This will attempt to upgrade the next event if it is the case.
     */
    public void updateNextEvent() {

        debug("---");
        debug("updateNextEvent called");
        if (nextEvent == NextEvent.EMERALD_GENERATOR_TIER_II && upgradeEmeraldsCount == 0) {
            // next diamond time < next emerald time
            int next = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_START) == null ?
                    "Default." + ConfigPath.GENERATOR_EMERALD_TIER_III_START : getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_START);
            if (upgradeDiamondsCount < next && diamondTier == 1) {
                setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_II);
            } else if (upgradeDiamondsCount < next && diamondTier == 2) {
                setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_III);
            } else {
                setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_III);
            }
            upgradeEmeraldsCount = next;
            emeraldTier = 2;
            sendEmeraldsUpgradeMessages();
            for (OreGenerator o : getOreGenerators()) {
                if (o.getType() == GeneratorType.EMERALD && o.getBwt() == null) {
                    o.upgrade();
                }
            }
        } else if (nextEvent == NextEvent.DIAMOND_GENERATOR_TIER_II && upgradeDiamondsCount == 0) {
            int next = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_START) == null ?
                    "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_III_START : getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_START);
            if (upgradeEmeraldsCount < next && emeraldTier == 1) {
                setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_II);
            } else if (upgradeEmeraldsCount < next && emeraldTier == 2) {
                setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_III);
            } else {
                setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_III);
            }
            upgradeDiamondsCount = next;
            diamondTier = 2;
            sendDiamondsUpgradeMessages();
            for (OreGenerator o : getOreGenerators()) {
                if (o.getType() == GeneratorType.DIAMOND && o.getBwt() == null) {
                    o.upgrade();
                }
            }
        } else if (nextEvent == NextEvent.EMERALD_GENERATOR_TIER_III && upgradeEmeraldsCount == 0) {
            emeraldTier = 3;
            sendEmeraldsUpgradeMessages();
            if (diamondTier == 1 && upgradeDiamondsCount > 0) {
                setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_II);
            } else if (diamondTier == 2 && upgradeDiamondsCount > 0) {
                setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_III);
            } else {
                setNextEvent(NextEvent.BEDS_DESTROY);
            }
            for (OreGenerator o : getOreGenerators()) {
                if (o.getType() == GeneratorType.EMERALD && o.getBwt() == null) {
                    o.upgrade();
                }
            }
        } else if (nextEvent == NextEvent.DIAMOND_GENERATOR_TIER_III && upgradeDiamondsCount == 0) {
            diamondTier = 3;
            sendDiamondsUpgradeMessages();
            if (emeraldTier == 1 && upgradeEmeraldsCount > 0) {
                setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_II);
            } else if (emeraldTier == 2 && upgradeEmeraldsCount > 0) {
                setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_III);
            } else {
                setNextEvent(NextEvent.BEDS_DESTROY);
            }
            for (OreGenerator o : getOreGenerators()) {
                if (o.getType() == GeneratorType.DIAMOND && o.getBwt() == null) {
                    o.upgrade();
                }
            }
        } else if (nextEvent == NextEvent.BEDS_DESTROY && getPlayingTask().getBedsDestroyCountdown() == 0) {
            setNextEvent(NextEvent.ENDER_DRAGON);
        } else if (nextEvent == NextEvent.ENDER_DRAGON && getPlayingTask().getDragonSpawnCountdown() == 0) {
            setNextEvent(NextEvent.GAME_END);
        }

        //if (nextEvent.getValue(this) > 0) return;

        //nextEvents.remove(nextEvent.toString());

        //for (String s : nextEvents) {
        //    debug(s);
        //}

        //if (nextEvents.isEmpty()) return;

        //NextEvent next = NextEvent.valueOf(nextEvents.get(0));
        //int lowest = next.getValue(this);

        //for (String ne : nextEvents) {
        //    int value = NextEvent.valueOf(ne).getValue(this);
        //    if (value == -1) continue;
        //    if (lowest > value) next = NextEvent.valueOf(ne);
        //}

        debug("---");

    /*if (nextEvent == NextEvent.DIAMOND_GENERATOR_TIER_II) {
        setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_III);
    } else if (nextEvent == NextEvent.DIAMOND_GENERATOR_TIER_III) {
        if (emeraldTier == 1) {
            setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_II);
        } else if (emeraldTier == 2) {
            setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_III);
        } else {
            setNextEvent(NextEvent.BEDS_DESTROY);
        }
    } else if (emeraldTier >= 3 && diamondTier >= 3 && (playingTask != null && playingTask.getBedsDestroyCountdown() == 0)) {
        setNextEvent(NextEvent.BEDS_DESTROY);
    } else if (nextEvent == NextEvent.BEDS_DESTROY && (playingTask != null && playingTask.getDragonSpawnCountdown() >= 0)) {
        setNextEvent(NextEvent.ENDER_DRAGON);
    } else if (nextEvent == NextEvent.ENDER_DRAGON && (playingTask != null && playingTask.getBedsDestroyCountdown() == 0) && (playingTask != null && playingTask.getDragonSpawnCountdown() == 0)) {
        setNextEvent(NextEvent.GAME_END);
    }*/
        debug(nextEvent.toString());
    }

    /**
     * Get arena by players list.
     */
    public static HashMap<Player, Arena> getArenaByPlayer() {
        return arenaByPlayer;
    }

    /**
     * Get next event.
     */
    public NextEvent getNextEvent() {
        return nextEvent;
    }

    /**
     * Get players count for a group
     */
    public static int getPlayers(String group) {
        int i = 0;
        for (Arena a : getArenas()) {
            if (a.getGroup().equalsIgnoreCase(group)) i += a.getPlayers().size();
        }
        return i;
    }

    /**
     * Register join-signs for arena
     */
    private void registerSigns() {
        if (getServerType() != ServerType.BUNGEE) {
            if (Main.signs.getYml().get("locations") != null) {
                for (String st : Main.signs.getYml().getStringList("locations")) {
                    String[] data = st.split(",");
                    if (data[0].equals(getWorld().getName())) {
                        Location l;
                        try {
                            l = new Location(Bukkit.getWorld(data[6]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]));
                        } catch (Exception e) {
                            //noinspection ImplicitArrayToString
                            plugin.getLogger().severe("Could not load sign at: " + data.toString());
                            continue;
                        }
                        addSign(l);
                    }
                }
            }
        }
    }

    /**
     * Get a team by name
     */
    public BedWarsTeam getTeam(String name) {
        for (BedWarsTeam bwt : getTeams()) {
            if (bwt.getName().equals(name)) return bwt;
        }
        return null;
    }

    /**
     * Get respawn session
     */
    public ConcurrentHashMap<Player, Integer> getRespawn() {
        return respawn;
    }

    /**
     * Get invisibility for armor
     */
    public ConcurrentHashMap<Player, Integer> getShowTime() {
        return showTime;
    }

    /**
     * Get instance of the starting task.
     */
    public GameStartingTask getStartingTask() {
        return startingTask;
    }

    /**
     * Get instance of the playing task.
     */
    public GamePlayingTask getPlayingTask() {
        return playingTask;
    }

    /**
     * Get instance of the game restarting task.
     */
    public GameRestartingTask getRestartingTask() {
        return restartingTask;
    }

    /**
     * Get Ore Generators.
     */
    public List<OreGenerator> getOreGenerators() {
        return oreGenerators;
    }

    /**
     * Add a player to the most filled arena.
     * Check if is the party owner first.
     */
    public static boolean joinRandomArena(Player p) {
        List<Arena> arenas = new ArrayList<>(Arena.getArenas());
        Collections.sort(arenas);
        int amount = getParty().hasParty(p) ? getParty().getMembers(p).size() : 1;

        for (Arena a : arenas) {
            if (a.getPlayers().size() == a.getMaxPlayers()) continue;
            if (a.getMaxPlayers() - a.getPlayers().size() >= amount) {
                if (a.addPlayer(p, false)) break;
            }
        }
        return true;
    }

    /**
     * Add a player to the most filled arena from a group.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean joinRandomFromGroup(Player p, String group) {

        List<Arena> arenaList = new ArrayList<>(getArenas());
        Collections.sort(arenaList);

        int amount = getParty().hasParty(p) ? getParty().getMembers(p).size() : 1;
        for (Arena a : arenaList) {
            if (!a.getGroup().equalsIgnoreCase(group)) continue;
            if (a.getPlayers().size() == a.getMaxPlayers()) continue;
            if (a.getMaxPlayers() - a.getPlayers().size() >= amount) {
                if (a.addPlayer(p, false)) break;
            }
        }

        return true;
    }

    /**
     * Get the list of next events to come.
     * Not ordered.
     */
    public List<String> getNextEvents() {
        return new ArrayList<>(nextEvents);
    }

    /**
     * Get player deaths.
     */
    public int getPlayerDeaths(Player p, boolean finalDeaths) {
        if (finalDeaths) return playerFinalKillDeaths.getOrDefault(p, 0);
        return playerDeaths.getOrDefault(p, 0);
    }

    @Override
    public int compareTo(Arena o) {
        if (getStatus() == GameState.starting && o.getStatus() == GameState.starting) {
            return Integer.compare(o.getPlayers().size(), getPlayers().size());
        } else if (getStatus() == GameState.starting && o.getStatus() != GameState.starting) {
            return -1;
        } else if (o.getStatus() == GameState.starting && getStatus() != GameState.starting) {
            return 1;
        } else if (getStatus() == GameState.waiting && o.getStatus() == GameState.waiting) {
            return Integer.compare(o.getPlayers().size(), getPlayers().size());
        } else if (getStatus() == GameState.waiting && o.getStatus() != GameState.waiting) {
            return -1;
        } else if (o.getStatus() == GameState.waiting && getStatus() != GameState.waiting) {
            return 1;
        } else if (getStatus() == GameState.playing && o.getStatus() == GameState.playing) {
            return 0;
        } else if (getStatus() == GameState.playing && o.getStatus() != GameState.playing) {
            return -1;
        } else return 1;
    }

    /**
     * Show upgrade announcement to players.
     * Change diamondTier value first.
     */
    private void sendDiamondsUpgradeMessages() {
        for (Player p : getPlayers()) {
            p.sendMessage(getMsg(p, Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT).replace("{generatorType}",
                    getMsg(p, Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND)).replace("{tier}", getMsg(p, (diamondTier == 2 ? Messages.FORMATTING_GENERATOR_TIER2 : Messages.FORMATTING_GENERATOR_TIER3))));
        }
        for (Player p : getSpectators()) {
            p.sendMessage(getMsg(p, Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT).replace("{generatorType}",
                    getMsg(p, Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND)).replace("{tier}", getMsg(p, (diamondTier == 2 ? Messages.FORMATTING_GENERATOR_TIER2 : Messages.FORMATTING_GENERATOR_TIER3))));
        }
    }

    /**
     * Show upgrade announcement to players.
     * Change emeraldTier value first.
     */
    private void sendEmeraldsUpgradeMessages() {
        for (Player p : getPlayers()) {
            p.sendMessage(getMsg(p, Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT).replace("{generatorType}",
                    getMsg(p, Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD)).replace("{tier}", getMsg(p, (emeraldTier == 2 ? Messages.FORMATTING_GENERATOR_TIER2 : Messages.FORMATTING_GENERATOR_TIER3))));
        }
        for (Player p : getSpectators()) {
            p.sendMessage(getMsg(p, Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT).replace("{generatorType}",
                    getMsg(p, Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD)).replace("{tier}", getMsg(p, (emeraldTier == 2 ? Messages.FORMATTING_GENERATOR_TIER2 : Messages.FORMATTING_GENERATOR_TIER3))));
        }
    }

    public static int getGamesBeforeRestart() {
        return gamesBeforeRestart;
    }

    public static void setGamesBeforeRestart(int gamesBeforeRestart) {
        Arena.gamesBeforeRestart = gamesBeforeRestart;
    }

    public List<Region> getRegionsList() {
        return regionsList;
    }

    public List<Block> getPlaced() {
        return placed;
    }

    public LinkedList<Block> getBroken() {
        return broken;
    }

    public static LinkedList<IArena> getEnableQueue() {
        return enableQueue;
    }

    private void destroyData() {
        arenas.remove(this);
        for (ReJoinTask rjt : ReJoinTask.getReJoinTasks()) {
            if (rjt.getArena() == this) {
                rjt.destroy();
            }
        }
        arenaByName.remove(worldName);
        arenaByPlayer.entrySet().removeIf(entry -> entry.getValue() == this);
        players = null;
        spectators = null;
        signs = null;
        yml = null;
        cm = null;
        world = null;
        for (OreGenerator og : oreGenerators) {
            og.destroyData();
        }
        for (BedWarsTeam bwt : teams) {
            bwt.destroyData();
        }
        playerLocation.entrySet().removeIf(e -> e.getValue().getWorld().getName().equalsIgnoreCase(worldName));
        teams = null;
        placed = null;
        broken = null;
        nextEvents = null;
        regionsList = null;
        respawn = null;
        showTime = null;
        playerKills = null;
        playerBedsDestroyed = null;
        playerFinalKills = null;
        playerDeaths = null;
        playerFinalKillDeaths = null;
        startingTask = null;
        playingTask = null;
        restartingTask = null;
        oreGenerators = null;
        perMinuteTask = null;
    }

    /**
     * Remove an arena from the enable queue.
     */
    public static void removeFromEnableQueue(IArena a) {
        enableQueue.remove(a);
        if (!enableQueue.isEmpty()) {
            Main.getAPI().getRestoreAdapter().onEnable(enableQueue.get(0));
            plugin.getLogger().info("Loading arena: " + enableQueue.get(0).getWorldName());
        }
    }
}
