package com.andrei1058.bedwars;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.GameAPI;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.*;
import com.andrei1058.bedwars.arena.despawnables.TargetListener;
import com.andrei1058.bedwars.arena.mapreset.MapManager;
import com.andrei1058.bedwars.arena.mapreset.ResetAdaptor;
import com.andrei1058.bedwars.arena.mapreset.fawe.FastAsyncWorldEdit;
import com.andrei1058.bedwars.arena.spectator.v1_9PlusListener;
import com.andrei1058.bedwars.commands.party.PartyCommand;
import com.andrei1058.bedwars.commands.rejoin.RejoinCommand;
import com.andrei1058.bedwars.commands.shout.ShoutCommand;
import com.andrei1058.bedwars.database.Database;
import com.andrei1058.bedwars.database.SQLite;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.levels.Level;
import com.andrei1058.bedwars.levels.internal.InternalLevel;
import com.andrei1058.bedwars.levels.internal.LevelListeners;
import com.andrei1058.bedwars.arena.spectator.SpectatorListeners;
import com.andrei1058.bedwars.arena.upgrades.BaseListener;
import com.andrei1058.bedwars.commands.leave.LeaveCommand;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.configuration.*;
import com.andrei1058.bedwars.listeners.*;
import com.andrei1058.bedwars.listeners.arenaselector.ArenaSelectorListener;
import com.andrei1058.bedwars.listeners.blockstatus.BlockStatusListener;
import com.andrei1058.bedwars.lobbysocket.*;
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.stats.StatsManager;
import com.andrei1058.bedwars.support.bStats;
import com.andrei1058.bedwars.support.bukkit.*;
import com.andrei1058.bedwars.support.bukkit.v1_10_R1.v1_10_R1;
import com.andrei1058.bedwars.support.bukkit.v1_11_R1.v1_11_R1;
import com.andrei1058.bedwars.support.bukkit.v1_12_R1.v1_12_R1;
import com.andrei1058.bedwars.support.bukkit.v1_13_R1.v1_13_R1;
import com.andrei1058.bedwars.support.bukkit.v1_13_R2.v1_13_R2;
import com.andrei1058.bedwars.support.bukkit.v1_14_R1.v1_14_R1;
import com.andrei1058.bedwars.support.bukkit.v1_8_R3.v1_8_R3;
import com.andrei1058.bedwars.support.bukkit.v1_9_R1.v1_9_R1;
import com.andrei1058.bedwars.support.bukkit.v1_9_R2.v1_9_R2;
import com.andrei1058.bedwars.support.citizens.CitizensListener;
import com.andrei1058.bedwars.support.citizens.JoinNPC;
import com.andrei1058.bedwars.support.leaderheads.LeaderHeadsSupport;
import com.andrei1058.bedwars.support.papi.PAPISupport;
import com.andrei1058.bedwars.support.papi.SupportPAPI;
import com.andrei1058.bedwars.support.party.NoParty;
import com.andrei1058.bedwars.support.party.Party;
import com.andrei1058.bedwars.support.party.Parties;
import com.andrei1058.bedwars.support.vault.*;
import com.andrei1058.bedwars.arena.tasks.OneTick;
import com.andrei1058.bedwars.arena.tasks.Refresh;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.andrei1058.bedwars.language.Language.setupLang;

@SuppressWarnings("WeakerAccess")
public class Main extends JavaPlugin {

    private static ServerType serverType = ServerType.MULTIARENA;
    public static boolean safeMode = false, lobbyServer = false, debug = true;
    public static String mainCmd = "bw", link = "https://www.spigotmc.org/resources/50942/";
    public static ConfigManager config, signs, generators;
    public static ShopManager shop;
    public static StatsManager statsManager;
    public static UpgradesManager upgrades;
    public static Language lang;
    public static Main plugin;
    public static NMS nms;

    private static Party party = null;
    private static Chat chat;
    protected static Level level;
    private static Economy economy;
    private static String version = Bukkit.getServer().getClass().getName().split("\\.")[3];
    private static String lobbyWorld = "";
    public static BedWars api;
    private static ResetAdaptor resetAdaptor = ResetAdaptor.INTERNAL;

    //remote database
    private static Database remoteDatabase;

    private boolean serverSoftwareSupport = true;

    @Override
    public void onLoad() {

        //Spigot support
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (Exception ex) {
            serverSoftwareSupport = false;
            return;
        }

        plugin = this;

        /* Load version support */
        boolean support = true;
        switch (version) {
            case "v1_8_R3":
                nms = new v1_8_R3();
                break;
            case "v1_9_R1":
                nms = new v1_9_R1();
                break;
            case "v1_9_R2":
                nms = new v1_9_R2();
                break;
            case "v1_10_R1":
                nms = new v1_10_R1();
                break;
            case "v1_11_R1":
                nms = new v1_11_R1();
                break;
            case "v1_12_R1":
                nms = new v1_12_R1();
                break;
            case "v1_13_R1":
                nms = new v1_13_R1();
                break;
            case "v1_13_R2":
                nms = new v1_13_R2();
                break;
            case "v1_14_R1":
                nms = new v1_14_R1();
                break;
            default:
                support = false;
        }

        if (!support) {
            this.setEnabled(false);
            this.getLogger().severe("I can't run on your version: " + version);
            return;
        } else {
            this.getLogger().info("Loading support for paper/ spigot " + version + ".");
        }

        config = new ConfigManager("config", "plugins/" + this.getName(), false);

        Language en = new Language("en");
        setupLang(en);
        Language.getLanguages().remove(en);

        Language ro = new Language("ro");
        setupLang(ro);
        Language.getLanguages().remove(ro);

        Language it = new Language("it");
        setupLang(it);
        Language.getLanguages().remove(it);

        Language pl = new Language("pl");
        setupLang(pl);
        Language.getLanguages().remove(pl);

        Language es = new Language("es");
        setupLang(es);
        Language.getLanguages().remove(es);

        Language ru = new Language("ru");
        setupLang(ru);
        Language.getLanguages().remove(ru);

        setupConfig();

        generators = new ConfigManager("generators", "plugins/" + this.getName(), false);
        setupGeneratorsCfg();

        //loadArenasAndSigns();

        upgrades = new UpgradesManager("upgrades", "plugins/" + this.getName());
    }

    @Override
    public void onEnable() {

        if (!serverSoftwareSupport) {
            this.getLogger().severe("I can't run on your server software. Please check:");
            this.getLogger().severe("https://gitlab.com/andrei1058/BedWars1058/wikis/compatibility");
            this.setEnabled(false);
            return;
        }

        // Load FastAsyncWorldEdit support
        if (Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit") != null) {
            resetAdaptor = ResetAdaptor.FAWE;
            this.getLogger().info("Hook into FastAsyncWorldEdit support!");
        }

        ArenaSocket.serverIdentifier = Bukkit.getServer().getIp() + ":" + Bukkit.getServer().getPort();

        /* Register commands */
        nms.registerCommand(mainCmd, new MainCommand(mainCmd));

        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (!nms.isBukkitCommandRegistered("shout")) {
                nms.registerCommand("shout", new ShoutCommand("shout"));
            }
            nms.registerCommand("rejoin", new RejoinCommand("rejoin"));
            if (!(nms.isBukkitCommandRegistered("leave") && getServerType() == ServerType.BUNGEE)) {
                nms.registerCommand("leave", new LeaveCommand("leave"));
            }
            if (!(nms.isBukkitCommandRegistered("party") && getServerType() == ServerType.BUNGEE)) {
                nms.registerCommand("party", new PartyCommand("party"));
            }
        }, 20L);

        /* Setup plugin messaging channel */
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        //Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new Bungee());
        Bukkit.getServicesManager().register(GameAPI.class, new BedWars(), this, ServicePriority.Highest);
        api = (BedWars) this.getServer().getServicesManager().getRegistration(GameAPI.class).getProvider();

        /* Check if lobby location is set. Required for non Bungee servers */
        if (config.getLobbyWorldName().isEmpty() && serverType != ServerType.BUNGEE) {
            plugin.getLogger().severe("Lobby location is not set!");
        }

        /* Load lobby world if not main level
         * when the server finishes loading. */
        if (getServerType() == ServerType.MULTIARENA)
            Bukkit.getScheduler().runTaskLater(this, () -> {
                if (!config.getLobbyWorldName().isEmpty()) {
                    if (!config.getLobbyWorldName().equalsIgnoreCase(Bukkit.getServer().getWorlds().get(0).getName())) {
                        Bukkit.getScheduler().runTaskLater(this, () -> {
                            Bukkit.createWorld(new WorldCreator(config.getLobbyWorldName()));

                            if (Bukkit.getWorld(config.getLobbyWorldName()) != null) {
                                Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getWorld(config.getLobbyWorldName())
                                        .getEntities().stream().filter(e -> e instanceof Monster).forEach(Entity::remove), 20L);
                            }
                        }, 100L);
                    }
                }
            }, 1L);

        /* Register events */
        registerEvents(new JoinLeaveTeleport(), new BreakPlace(), new DamageDeathMove(), new Inventory(), new Interact(), new RefreshGUI(), new HungerWeatherSpawn(), new CmdProcess(),
                new EggBridge(), new SpectatorListeners(), new BaseListener(), new TargetListener());
        if (getServerType() == ServerType.BUNGEE) {
            registerEvents(new Ping());
            registerEvents(new ArenaListeners());
            ArenaSocket.lobbies.addAll(config.l(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_LOBBY_SERVERS));
            new SendTask();
        } else if (getServerType() == ServerType.MULTIARENA || getServerType() == ServerType.SHARED) {
            registerEvents(new ArenaSelectorListener(), new BlockStatusListener());
        }

        // Register setup-holograms fix
        registerEvents(new ChunkLoad());

        /* Load version support */
        switch (version) {
            case "v1_13_R2":
            case "v1_13_R1":
            case "v1_14_R1":
                registerEvents(new v1_3_Interact());
            case "v1_12_R1":
                registerEvents(new EntityDropPickListener());
                break;
            default:
                registerEvents(new PlayerDropPickListener());
                break;
        }

        if (!"v1_8_R3".equals(version)) {
            registerEvents(new v1_9PlusListener(), new v1_9_SwapItem());
        }

        /* Deprecated versions */
        switch (version) {
            case "v1_8_R3":
            case "v1_9_R1":
            case "v1_9_R2":
            case "v1_10_R1":
            case "v1_11_R1":
                Bukkit.getScheduler().runTaskLater(this,
                        () -> System.out.println("\u001B[31m[WARN] BedWars1058 may drop support for this server version in the future.\nPlease consider upgrading to a newer paper/spigot version.\u001B[0m"), 40L);
                break;
        }

        /* Load join signs. */
        loadArenasAndSigns();

        statsManager = new StatsManager();

        /* Party support */
        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (config.getYml().getBoolean(ConfigPath.GENERAL_CONFIGURATION_ALLOW_PARTIES)) {
                if (Bukkit.getPluginManager().getPlugin("Parties") != null) {
                    if (getServer().getPluginManager().getPlugin("Parties").isEnabled()) {
                        getLogger().info("Hook into Parties (by AlessioDP) support!");
                        party = new Parties();
                    }
                }
                if (party == null) {
                    party = new com.andrei1058.bedwars.support.party.Internal();
                    getLogger().info("Loading internal Party system. /party");
                }
            } else {
                party = new NoParty();
            }
        }, 10L);

        /* Levels support */
        setLevelAdapter(new InternalLevel());

        /* Register tasks */
        Bukkit.getScheduler().runTaskTimer(this, new Refresh(), 20L, 20L);
        //new Refresh().runTaskTimer(this, 20L, 20L);

        if (config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_PERFORMANCE_ROTATE_GEN)) {
            //new OneTick().runTaskTimer(this, 120, 1);
            Bukkit.getScheduler().runTaskTimer(this, new OneTick(), 120, 1);
        }

        /* Register NMS entities */
        nms.registerEntities();

        /* Check for updates */
        Misc.checkUpdate();

        /* Database support */
        if (config.getBoolean("database.enable")) {
            com.andrei1058.bedwars.database.MySQL mySQL = new com.andrei1058.bedwars.database.MySQL();
            long time = System.currentTimeMillis();
            if (!mySQL.connect()) {
                this.getLogger().severe("Could not connect to database! Please verify your credentials and make sure that the server IP is whitelisted in MySQL.");
                remoteDatabase = new SQLite();
            } else {
                remoteDatabase = mySQL;
            }
            if (System.currentTimeMillis() - time >= 5000) {
                this.getLogger().severe("It took " + ((System.currentTimeMillis() - time) / 1000) + " ms to establish a database connection!\n" +
                        "Using this remote connection is not recommended!");
            }
            remoteDatabase.init();
        } else {
            remoteDatabase = new SQLite();
        }

        /* Citizens support */
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (this.getServer().getPluginManager().getPlugin("Citizens") != null) {
                JoinNPC.setCitizensSupport(true);
                getLogger().info("Hook into Citizens support. /bw npc");
                registerEvents(new CitizensListener());
            }

            //spawn NPCs
            try {
                JoinNPC.spawnNPCs();
            } catch (Exception e) {
                this.getLogger().severe("Could not spawn CmdJoin NPCs. Make sure you have right version of Citizens for your server!");
                JoinNPC.setCitizensSupport(false);
            }
            if (getServerType() == ServerType.BUNGEE) {
                if (Arena.getArenas().size() > 0) {
                    ArenaSocket.sendMessage(Arena.getArenas().get(0));
                }
            }
        }, 40L);

        /* Save messages for stats gui items if custom items added, for each language */
        Language.setupCustomStatsMessages();


        /* PlaceholderAPI Support */
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("Hook into PlaceholderAPI support!");
            new PAPISupport().register();
            SupportPAPI.setSupportPAPI(new SupportPAPI.withPAPI());
        }
        /* Vault support */
        if (this.getServer().getPluginManager().getPlugin("Vault") != null) {
            try {
                RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
                WithChat.setChat((net.milkbowl.vault.chat.Chat) rsp.getProvider());
                plugin.getLogger().info("Hook into vault chat support!");
                chat = new WithChat();
            } catch (Exception var2_2) {
                chat = new NoChat();
            }
            try {
                RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
                WithEconomy.setEconomy((net.milkbowl.vault.economy.Economy) rsp.getProvider());
                plugin.getLogger().info("Hook into vault economy support!");
                economy = new WithEconomy();
            } catch (Exception var2_2) {
                economy = new NoEconomy();
            }
        } else {
            chat = new NoChat();
            economy = new NoEconomy();
        }

        /* Chat support */
        if (config.getBoolean("formatChat")) {
            registerEvents(new PlayerChat());
        }

        /* Protect glass walls from tnt explosion */
        nms.registerTntWhitelist();

        /* Prevent issues on reload */
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.kickPlayer("BedWars1058 was RELOADED! (do not reload plugins)");
        }

        /* Load sounds configuration */
        new Sounds();

        /* LeaderHeads Support */
        Bukkit.getScheduler().runTaskLater(this, LeaderHeadsSupport::initLeaderHeads, 40L);

        /* Initialize shop */
        shop = new ShopManager();

        //Leave this code at the end of the enableRotation method
        for (Language l : Language.getLanguages()) {
            l.setupUnSetCategories();
            Language.addDefaultMessagesCommandItems(l);
        }

        LevelsConfig.init();

        // bStats metrics
        bStats metrics = new bStats(this);
        metrics.addCustomChart(new bStats.SimplePie("server_type", () -> getServerType().toString()));
        metrics.addCustomChart(new bStats.SimplePie("default_language", () -> lang.getIso()));
    }

    public void onDisable() {
        if (!serverSoftwareSupport) return;
        try {
            for (Arena a : Arena.getArenas()) {
                a.disable();
            }
        } catch (Exception ignored) {
        }
        StatsManager.getStatsCache().close();
        remoteDatabase.close();
        if (getServerType() == ServerType.BUNGEE) {
            ArenaSocket.disable();
        }
    }

    private void setupConfig() {
        YamlConfiguration yml = config.getYml();

        yml.options().header(plugin.getDescription().getName() + " by andrei1058. https://www.spigotmc.org/members/39904/\n" +
                "Documentation here: https://gitlab.com/andrei1058/BedWars1058/wikis/home\n");
        yml.addDefault("serverType", "MULTIARENA");
        yml.addDefault("language", "en");
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DISABLED_LANGUAGES, Collections.singletonList("your language iso here"));
        yml.addDefault("storeLink", "https://www.spigotmc.org/resources/authors/39904/");
        yml.addDefault("lobbyServer", "hub");
        yml.addDefault("globalChat", false);
        yml.addDefault("formatChat", true);
        yml.addDefault("debug", false);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_SCOREBOARD, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ALLOW_PARTIES, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_REJOIN_TIME, 60 * 5);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_MODE_GAMES_BEFORE_RESTART, 30);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD, "restart");
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_LOBBY_SERVERS, Arrays.asList("0.0.0.0:2019"));
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_REGULAR, 40);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_HALF, 25);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_SHORTENED, 10);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_BEDS_DESTROY_COUNTDOWN, 360);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DRAGON_SPAWN_COUNTDOWN, 600);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_GAME_END_COUNTDOWN, 120);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_SHOUT_COOLDOWN, 30);
        yml.addDefault(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP, "yourServer.Com");

        yml.addDefault("database.enable", false);
        yml.addDefault("database.host", "localhost");
        yml.addDefault("database.port", 3306);
        yml.addDefault("database.database", "bedwars1058");
        yml.addDefault("database.user", "root");
        yml.addDefault("database.pass", "cheez");
        yml.addDefault("database.ssl", false);

        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PERFORMANCE_ROTATE_GEN, true);

        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DISABLE_CRAFTING, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DISABLE_ENCHANTING, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DISABLE_FURNACE, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DISABLE_BREWING_STAND, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DISABLE_ANVIL, true);

        /* Multi-Arena Lobby Command Items */
        config.saveLobbyCommandItem("stats", "bw stats", false, getForCurrentVersion("SKULL_ITEM", "SKULL_ITEM", "PLAYER_HEAD"), 3, 0);
        config.saveLobbyCommandItem("arena-selector", "bw gui", true, "CHEST", 5, 4);
        config.saveLobbyCommandItem("leave", "bw leave", false, getForCurrentVersion("BED", "BED", "RED_BED"), 0, 8);

        /* Pre Game Command Items */
        config.savePreGameCommandItem("stats", "bw stats", false, getForCurrentVersion("SKULL_ITEM", "SKULL_ITEM", "PLAYER_HEAD"), 3, 0);
        config.savePreGameCommandItem("leave", "bw leave", false, getForCurrentVersion("BED", "BED", "RED_BED"), 0, 8);

        /* Spectator Command Items */
        config.saveSpectatorCommandItem("teleporter", "bw teleporter", false, getForCurrentVersion("SKULL_ITEM", "SKULL_ITEM", "PLAYER_HEAD"), 3, 0);
        config.saveSpectatorCommandItem("leave", "bw leave", false, getForCurrentVersion("BED", "BED", "RED_BED"), 0, 8);

        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_SIZE, 27);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_SHOW_PLAYING, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_USE_SLOTS, "10,11,12,13,14,15,16");
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_MATERIAL.replace("%path%", "waiting"), getForCurrentVersion("STAINED_GLASS_PANE", "CONCRETE", "LIME_CONCRETE"));
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_DATA.replace("%path%", "waiting"), 5);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_ENCHANTED.replace("%path%", "waiting"), false);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_MATERIAL.replace("%path%", "starting"), getForCurrentVersion("STAINED_GLASS_PANE", "CONCRETE", "YELLOW_CONCRETE"));
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_DATA.replace("%path%", "starting"), 4);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_ENCHANTED.replace("%path%", "starting"), true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_MATERIAL.replace("%path%", "playing"), getForCurrentVersion("STAINED_GLASS_PANE", "CONCRETE", "RED_CONCRETE"));
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_DATA.replace("%path%", "playing"), 14);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_ENCHANTED.replace("%path%", "playing"), false);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_MATERIAL.replace("%path%", "skipped-slot"), getForCurrentVersion("STAINED_GLASS_PANE", "STAINED_GLASS_PANE", "BLACK_STAINED_GLASS_PANE"));
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_DATA.replace("%path%", "skipped-slot"), 15);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_ENCHANTED.replace("%path%", "skipped-slot"), false);

        /* default stats GUI items */
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_STATS_GUI_SIZE, 27);
        if (config.isFirstTime()) {
            Misc.addDefaultStatsItem(yml, 10, Material.DIAMOND, 0, "wins");
            Misc.addDefaultStatsItem(yml, 11, Material.REDSTONE, 0, "losses");
            Misc.addDefaultStatsItem(yml, 12, Material.IRON_SWORD, 0, "kills");
            Misc.addDefaultStatsItem(yml, 13, Material.valueOf(getForCurrentVersion("SKULL_ITEM", "SKULL_ITEM", "SKELETON_SKULL")), 0, "deaths");
            Misc.addDefaultStatsItem(yml, 14, Material.DIAMOND_SWORD, 0, "final-kills");
            Misc.addDefaultStatsItem(yml, 15, Material.valueOf(getForCurrentVersion("SKULL_ITEM", "SKULL_ITEM", "SKELETON_SKULL")), 1, "final-deaths");
            Misc.addDefaultStatsItem(yml, 16, Material.valueOf(getForCurrentVersion("BED", "BED", "RED_BED")), 0, "beds-destroyed");
            Misc.addDefaultStatsItem(yml, 21, Material.valueOf(getForCurrentVersion("STAINED_GLASS_PANE", "STAINED_GLASS_PANE", "BLACK_STAINED_GLASS_PANE")), 0, "first-play");
            Misc.addDefaultStatsItem(yml, 22, Material.CHEST, 0, "games-played");
            Misc.addDefaultStatsItem(yml, 23, Material.valueOf(getForCurrentVersion("STAINED_GLASS_PANE", "STAINED_GLASS_PANE", "BLACK_STAINED_GLASS_PANE")), 0, "last-play");
        }

        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DEFAULT_ITEMS + ".Default", Collections.singletonList(getForCurrentVersion("WOOD_SWORD", "WOOD_SWORD", "WOODEN_SWORD")));
        yml.addDefault(ConfigPath.CENERAL_CONFIGURATION_ALLOWED_COMMANDS, Arrays.asList("shout", "bw", "leave"));
        yml.options().copyDefaults(true);
        config.save();

        //remove old config
        //Convert old configuration

        if (yml.get("arenaGui.settings.showPlaying") != null) {
            config.set(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_SHOW_PLAYING, yml.getBoolean("arenaGui.settings.showPlaying"));
        }
        if (yml.get("arenaGui.settings.size") != null) {
            config.set(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_SIZE, yml.getInt("arenaGui.settings.size"));
        }
        if (yml.get("arenaGui.settings.useSlots") != null) {
            config.set(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_USE_SLOTS, yml.getString("arenaGui.settings.useSlots"));
        }
        if (config.getYml().get("arenaGui") != null) {
            for (String path : config.getYml().getConfigurationSection("arenaGui").getKeys(false)) {
                if (path.equalsIgnoreCase("settings")) continue;
                String new_path = path;
                if ("skippedSlot".equals(path)) {
                    new_path = "skipped-slot";
                }
                if (config.getYml().get("arenaGui." + path + ".itemStack") != null) {
                    config.set(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_MATERIAL.replace("%path%", new_path), config.getYml().getString("arenaGui." + path + ".itemStack"));
                }
                if (config.getYml().get("arenaGui." + path + ".data") != null) {
                    config.set(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_DATA.replace("%path%", new_path), config.getYml().getInt("arenaGui." + path + ".data"));
                }
                if (config.getYml().get("arenaGui." + path + ".enchanted") != null) {
                    config.set(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_ENCHANTED.replace("%path%", new_path), config.getYml().getBoolean("arenaGui." + path + ".enchanted"));
                }
            }
        }

        config.set("arenaGui", null);

        if (config.getYml().get("npcLoc") != null) {
            config.set(ConfigPath.GENERAL_CONFIGURATION_NPC_LOC_STORAGE, config.getYml().getString("npcLoc"));
        }
        if (config.getYml().get("statsGUI.invSize") != null) {
            config.set(ConfigPath.GENERAL_CONFIGURATION_STATS_GUI_SIZE, config.getInt("statsGUI.invSize"));
        }
        if (config.getYml().get("disableCrafting") != null) {
            config.set(ConfigPath.GENERAL_CONFIGURATION_DISABLE_CRAFTING, config.getString("disableCrafting"));
        }
        if (config.getYml().get("statsGUI") != null) {
            for (String stats_path : config.getYml().getConfigurationSection("statsGUI").getKeys(false)) {
                String new_path = stats_path;
                switch (stats_path) {
                    case "gamesPlayed":
                        new_path = "games-played";
                        break;
                    case "lastPlay":
                        new_path = "last-play";
                        break;
                    case "firstPlay":
                        new_path = "first-play";
                        break;
                    case "bedsDestroyed":
                        new_path = "beds-destroyed";
                        break;
                    case "finalDeaths":
                        new_path = "final-deaths";
                        break;
                    case "finalKills":
                        new_path = "final-kills";
                        break;
                }
                if (config.getYml().get("statsGUI." + stats_path + ".itemStack") != null) {
                    config.set(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_MATERIAL.replace("%path%", new_path), config.getYml().getString("statsGUI." + stats_path + ".itemStack"));
                }
                if (config.getYml().get("statsGUI." + stats_path + ".data") != null) {
                    config.set(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_DATA.replace("%path%", new_path), config.getYml().getInt("statsGUI." + stats_path + ".data"));
                }
                if (config.getYml().get("statsGUI." + stats_path + ".slot") != null) {
                    config.set(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_SLOT.replace("%path%", new_path), config.getYml().getInt("statsGUI." + stats_path + ".slot"));
                }
            }
        }

        config.set("statsGUI", null);
        config.set("startItems", null);
        config.set("generators", null);
        config.set("bedsDestroyCountdown", null);
        config.set("dragonSpawnCountdown", null);
        config.set("gameEndCountdown", null);
        config.set("npcLoc", null);
        config.set("blockedCmds", null);
        config.set("lobbyScoreboard", null);
        config.set("arenaGui.settings.startSlot", null);
        config.set("arenaGui.settings.endSlot", null);
        config.set("items", null);
        config.set("start-items-per-arena", null);
        config.set("safeMode", null);
        config.set("disableCrafting", null);

        //Finished old configuration conversion

        //set default server language
        String whatLang = "en";
        for (File f : Objects.requireNonNull(new File("plugins/" + this.getDescription().getName() + "/Languages").listFiles())) {
            if (f.isFile()) {
                if (f.getName().contains("messages_") && f.getName().contains(".yml")) {
                    String lang = f.getName().replace("messages_", "").replace(".yml", "");
                    if (lang.equalsIgnoreCase(yml.getString("language"))) {
                        whatLang = f.getName().replace("messages_", "").replace(".yml", "");
                    }
                    Language.setupLang(new Language(lang));
                }
            }
        }
        lang = Language.getLang(whatLang);

        //remove languages if disabled
        //server language can t be disabled
        for (String iso : yml.getStringList(ConfigPath.GENERAL_CONFIGURATION_DISABLED_LANGUAGES)) {
            Language l = Language.getLang(iso);
            if (l != null) {
                if (l != lang) Language.getLanguages().remove(l);
            }
        }

        debug = yml.getBoolean("debug");
        new ConfigManager("bukkit", Bukkit.getWorldContainer().getPath(), false).set("ticks-per.autosave", -1);

        Bukkit.spigot().getConfig().set("commands.send-namespaced", false);
        try {
            Bukkit.spigot().getConfig().save("spigot.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (yml.getString("serverType").toUpperCase()) {
            case "BUNGEE":
                serverType = ServerType.BUNGEE;
                new ConfigManager("bukkit", Bukkit.getWorldContainer().getPath(), false).set("settings.allow-end", false);
                //Bukkit.spigot().getConfig().set("settings.bungeecord", true);
                try {
                    Bukkit.spigot().getConfig().save("spigot.yml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "SHARED":
                serverType = ServerType.SHARED;
                setupSignsConfiguration();
                break;
            default:
                setupSignsConfiguration();
                config.set("serverType", "MULTIARENA");
                new ConfigManager("bukkit", Bukkit.getWorldContainer().getPath(), false).set("settings.allow-end", false);
                break;
        }
        lobbyWorld = config.getLobbyWorldName();
    }

    private void setupSignsConfiguration() {
        signs = new ConfigManager("signs", "plugins/" + plugin.getName(), false);
        YamlConfiguration yml = signs.getYml();
        yml.addDefault("format", Arrays.asList("&a[arena]", "", "&2[on]&9/&2[max]", "[status]"));
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_WAITING_MATERIAL, getForCurrentVersion("STAINED_CLAY", "STAINED_CLAY", "GREEN_CONCRETE"));
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_WAITING_DATA, 5);
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_STARTING_MATERIAL, getForCurrentVersion("STAINED_CLAY", "STAINED_CLAY", "YELLOW_CONCRETE"));
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_STARTING_DATA, 14);
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_PLAYING_MATERIAL, getForCurrentVersion("STAINED_CLAY", "STAINED_CLAY", "RED_CONCRETE"));
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_PLAYING_DATA, 4);
        yml.options().copyDefaults(true);
        signs.save();
        if (yml.getStringList("format").size() < 4) {
            signs.set("format", yml.getStringList("format").subList(0, 3));
        }
    }

    private void loadArenasAndSigns() {
        File dir = new File("plugins/" + plugin.getName() + "/Arenas");
        if (dir.exists()) {
            List<File> files = new ArrayList<>();
            File[] fls = dir.listFiles();
            for (File fl : Objects.requireNonNull(fls)) {
                if (fl.isFile()) {
                    if (fl.getName().contains(".yml")) {
                        files.add(fl);
                    }
                }
            }

            // lowerCase arena names - new 1.14 standard
            File folder, newName;

            List<File> toRemove = new ArrayList<>(), toAdd = new ArrayList<>();
            for (File file : files) {
                if (!file.getName().equals(file.getName().toLowerCase())) {
                    //level-name will not be renamed
                    if (nms.getLevelName().equals(file.getName().replace(".yml", ""))) continue;
                    newName = new File(dir.getPath() + "/" + file.getName().toLowerCase());
                    if (!file.renameTo(newName)) {
                        toRemove.add(file);
                        Main.plugin.getLogger().severe("Could not rename " + file.getName() + " to " + file.getName().toLowerCase() + "! Please do it manually!");
                    } else {
                        toAdd.add(newName);
                        toRemove.add(file);
                    }
                    folder = new File(plugin.getServer().getWorldContainer(), file.getName().replace(".yml", ""));
                    if (folder.exists()) {
                        if (!folder.getName().equals(folder.getName().toLowerCase())) {
                            if (!folder.renameTo(new File(plugin.getServer().getWorldContainer().getPath() + "/" + folder.getName().toLowerCase()))) {
                                Main.plugin.getLogger().severe("Could not rename " + folder.getName() + " folder to " + folder.getName().toLowerCase() + "! Please do it manually!");
                                toRemove.add(file);
                                return;
                            }
                        }
                    }
                }
            }

            for (File f : toRemove) {
                files.remove(f);
            }

            files.addAll(toAdd);

            if (serverType == ServerType.BUNGEE) {
                if (files.isEmpty()) {
                    this.getLogger().info("Could not find any arena!");
                    return;
                }
                Random r = new Random();
                int x = r.nextInt(files.size());

                String name = files.get(x).getName().replace(".yml", "");
                new Arena(name, null);
            } else {
                for (File file : files) {
                    new Arena(file.getName().replace(".yml", ""), null);
                }
            }
        }
    }

    private void registerEvents(Listener... listeners) {
        Arrays.stream(listeners).forEach(l -> plugin.getServer().getPluginManager().registerEvents(l, this));
    }

    public static void debug(String message) {
        if (debug) {
            plugin.getLogger().info("DEBUG: " + message);
        }
    }

    private static void setupGeneratorsCfg() {
        YamlConfiguration yml = generators.getYml();

        yml.options().header(plugin.getDescription().getName() + " by andrei1058." +
                "\ngenerators.yml Documentation: https://gitlab.com/andrei1058/BedWars1058/wikis/generators-configuration\n");
        yml.addDefault("Default." + ConfigPath.GENERATOR_IRON_DELAY, 2);
        yml.addDefault("Default." + ConfigPath.GENERATOR_IRON_AMOUNT, 2);
        yml.addDefault("Default." + ConfigPath.GENERATOR_GOLD_DELAY, 6);
        yml.addDefault("Default." + ConfigPath.GENERATOR_GOLD_AMOUNT, 2);
        yml.addDefault("Default." + ConfigPath.GENERATOR_IRON_SPAWN_LIMIT, 32);
        yml.addDefault("Default." + ConfigPath.GENERATOR_GOLD_SPAWN_LIMIT, 7);
        yml.addDefault(ConfigPath.GENERATOR_STACK_ITEMS, false);

        yml.addDefault("Default." + ConfigPath.GENERATOR_DIAMOND_TIER_I_DELAY, 30);
        yml.addDefault("Default." + ConfigPath.GENERATOR_DIAMOND_TIER_I_SPAWN_LIMIT, 4);
        yml.addDefault("Default." + ConfigPath.GENERATOR_DIAMOND_TIER_II_DELAY, 20);
        yml.addDefault("Default." + ConfigPath.GENERATOR_DIAMOND_TIER_II_SPAWN_LIMIT, 6);
        yml.addDefault("Default." + ConfigPath.GENERATOR_DIAMOND_TIER_II_START, 360);
        yml.addDefault("Default." + ConfigPath.GENERATOR_DIAMOND_TIER_III_DELAY, 15);
        yml.addDefault("Default." + ConfigPath.GENERATOR_DIAMOND_TIER_III_SPAWN_LIMIT, 8);
        yml.addDefault("Default." + ConfigPath.GENERATOR_DIAMOND_TIER_III_START, 1080);
        yml.addDefault("Default." + ConfigPath.GENERATOR_EMERALD_TIER_I_DELAY, 70);
        yml.addDefault("Default." + ConfigPath.GENERATOR_EMERALD_TIER_I_SPAWN_LIMIT, 4);
        yml.addDefault("Default." + ConfigPath.GENERATOR_EMERALD_TIER_II_DELAY, 50);
        yml.addDefault("Default." + ConfigPath.GENERATOR_EMERALD_TIER_II_SPAWN_LIMIT, 6);
        yml.addDefault("Default." + ConfigPath.GENERATOR_EMERALD_TIER_II_START, 720);
        yml.addDefault("Default." + ConfigPath.GENERATOR_EMERALD_TIER_III_DELAY, 30);
        yml.addDefault("Default." + ConfigPath.GENERATOR_EMERALD_TIER_III_SPAWN_LIMIT, 8);
        yml.addDefault("Default." + ConfigPath.GENERATOR_EMERALD_TIER_III_START, 1440);
        yml.options().copyDefaults(true);
        generators.save();
    }

    public static String getForCurrentVersion(String v18, String v12, String v13) {
        switch (getServerVersion()) {
            case "v1_8_R3":
            case "v1_9_R1":
            case "v1_9_R2":
            case "v1_10_R1":
            case "v1_11_R1":
            case "v1_12_R1":
                return v12;
            case "v1_13_R1":
            case "v1_13_R2":
                return v13;
        }
        return v13;
    }

    public static ServerType getServerType() {
        return serverType;
    }

    public static Party getParty() {
        return party;
    }

    public static Chat getChatSupport() {
        return chat;
    }

    /**
     * Get current levels manager.
     */
    public static Level getLevelSupport() {
        return level;
    }

    /**
     * Set the levels manager.
     * You can use this to add your own levels manager just implement
     * the Level interface so the plugin will be able to display
     * the level internally.
     */
    public static void setLevelAdapter(Level levelsManager) {
        if (levelsManager instanceof InternalLevel) {
            if (LevelListeners.instance == null) {
                Bukkit.getPluginManager().registerEvents(new LevelListeners(), Main.plugin);
            }
        } else {
            if (LevelListeners.instance != null) {
                PlayerJoinEvent.getHandlerList().unregister(LevelListeners.instance);
                PlayerQuitEvent.getHandlerList().unregister(LevelListeners.instance);
                LevelListeners.instance = null;
            }
        }
        level = levelsManager;
    }

    public static Economy getEconomy() {
        return economy;
    }

    public static ConfigManager getGeneratorsCfg() {
        return generators;
    }

    public static void setLobbyWorld(String lobbyWorld) {
        Main.lobbyWorld = lobbyWorld;
    }

    /**
     * Get the server version
     * Ex: v1_8_R3
     *
     * @since v0.6.5beta
     */
    public static String getServerVersion() {
        return version;
    }

    public static String getLobbyWorld() {
        return lobbyWorld;
    }

    /**
     * Get remote database.
     */
    public static Database getRemoteDatabase() {
        return remoteDatabase;
    }

    /**
     * Get map manager.
     */
    public static MapManager getMapManager(Arena arena, String name) {
        MapManager manager;

        if (resetAdaptor == ResetAdaptor.FAWE) {
            manager = new FastAsyncWorldEdit(arena, name);
        } else {
            manager = new MapManager(arena, name);
        }

        return manager;
    }
}
