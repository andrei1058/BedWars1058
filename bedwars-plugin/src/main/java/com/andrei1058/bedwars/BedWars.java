/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.levels.Level;
import com.andrei1058.bedwars.api.party.Party;
import com.andrei1058.bedwars.api.server.RestoreAdapter;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.server.VersionSupport;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.ArenaManager;
import com.andrei1058.bedwars.arena.VoidChunkGenerator;
import com.andrei1058.bedwars.arena.despawnables.TargetListener;
import com.andrei1058.bedwars.arena.feature.SpoilPlayerTNTFeature;
import com.andrei1058.bedwars.arena.spectator.SpectatorListeners;
import com.andrei1058.bedwars.arena.tasks.OneTick;
import com.andrei1058.bedwars.arena.tasks.Refresh;
import com.andrei1058.bedwars.arena.upgrades.BaseListener;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.commands.leave.LeaveCommand;
import com.andrei1058.bedwars.commands.party.PartyCommand;
import com.andrei1058.bedwars.commands.rejoin.RejoinCommand;
import com.andrei1058.bedwars.commands.shout.ShoutCommand;
import com.andrei1058.bedwars.configuration.*;
import com.andrei1058.bedwars.database.Database;
import com.andrei1058.bedwars.database.SQLite;
import com.andrei1058.bedwars.halloween.HalloweenSpecial;
import com.andrei1058.bedwars.language.*;
import com.andrei1058.bedwars.levels.internal.InternalLevel;
import com.andrei1058.bedwars.levels.internal.LevelListeners;
import com.andrei1058.bedwars.listeners.*;
import com.andrei1058.bedwars.listeners.arenaselector.ArenaSelectorListener;
import com.andrei1058.bedwars.listeners.blockstatus.BlockStatusListener;
import com.andrei1058.bedwars.listeners.chat.ChatAFK;
import com.andrei1058.bedwars.listeners.chat.ChatFormatting;
import com.andrei1058.bedwars.listeners.joinhandler.*;
import com.andrei1058.bedwars.lobbysocket.ArenaSocket;
import com.andrei1058.bedwars.lobbysocket.LoadedUsersCleaner;
import com.andrei1058.bedwars.lobbysocket.SendTask;
import com.andrei1058.bedwars.maprestore.internal.InternalAdapter;
import com.andrei1058.bedwars.money.internal.MoneyListeners;
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.sidebar.*;
import com.andrei1058.bedwars.stats.StatsManager;
import com.andrei1058.bedwars.support.citizens.CitizensListener;
import com.andrei1058.bedwars.support.citizens.JoinNPC;
import com.andrei1058.bedwars.support.papi.PAPISupport;
import com.andrei1058.bedwars.support.papi.SupportPAPI;
import com.andrei1058.bedwars.support.party.NoParty;
import com.andrei1058.bedwars.support.party.PAF;
import com.andrei1058.bedwars.support.party.PAFBungeecordRedisApi;
import com.andrei1058.bedwars.support.party.PartiesAdapter;
import com.andrei1058.bedwars.support.preloadedparty.PrePartyListener;
import com.andrei1058.bedwars.support.vault.*;
import com.andrei1058.bedwars.support.vipfeatures.VipFeatures;
import com.andrei1058.bedwars.support.vipfeatures.VipListeners;
import com.andrei1058.spigotutils.SpigotUpdater;
import com.andrei1058.vipfeatures.api.IVipFeatures;
import com.andrei1058.vipfeatures.api.MiniGameAlreadyRegistered;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@SuppressWarnings("WeakerAccess")
public class BedWars extends JavaPlugin {

    private static ServerType serverType = ServerType.MULTIARENA;
    public static boolean debug = true, autoscale = false;
    public static String mainCmd = "bw", link = "https://www.spigotmc.org/resources/50942/";
    public static ConfigManager signs, generators;
    public static MainConfig config;
    public static ShopManager shop;
    public static StatsManager statsManager;
    public static BedWars plugin;
    public static VersionSupport nms;

    private static Party party = new NoParty();
    private static Chat chat = new NoChat();
    protected static Level level;
    private static Economy economy;
    private static final String version = Bukkit.getServer().getClass().getName().split("\\.")[3];
    private static String lobbyWorld = "";
    private static boolean shuttingDown = false;

    public static ArenaManager arenaManager = new ArenaManager();

    //remote database
    private static Database remoteDatabase;

    private boolean serverSoftwareSupport = true;

    private static com.andrei1058.bedwars.api.BedWars api;

    @Override
    public void onLoad() {

        //Spigot support
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (Exception ignored) {
            this.getLogger().severe("I can't run on your server software. Please check:");
            this.getLogger().severe("https://gitlab.com/andrei1058/BedWars1058/wikis/compatibility");
            serverSoftwareSupport = false;
            return;
        }

        plugin = this;

        /* Load version support */
        //noinspection rawtypes
        Class supp;

        try {
            supp = Class.forName("com.andrei1058.bedwars.support.version." + version + "." + version);
        } catch (ClassNotFoundException e) {
            serverSoftwareSupport = false;
            this.getLogger().severe("I can't run on your version: " + version);
            return;
        }

        api = new API();
        Bukkit.getServicesManager().register(com.andrei1058.bedwars.api.BedWars.class, api, this, ServicePriority.Highest);

        try {
            //noinspection unchecked
            nms = (VersionSupport) supp.getConstructor(Class.forName("org.bukkit.plugin.Plugin"), String.class).newInstance(this, version);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            serverSoftwareSupport = false;
            this.getLogger().severe("Could not load support for server version: " + version);
            return;
        }

        this.getLogger().info("Loading support for paper/spigot: " + version);

        // Setup languages
        new English();
        new Romanian();
        new Italian();
        new Polish();
        new Spanish();
        new Russian();
        new Bangla();
        new Persian();
        new Hindi();
        new Indonesia();
        new Portuguese();

        config = new MainConfig(this, "config");

        generators = new GeneratorsConfig(this, "generators", this.getDataFolder().getPath());
        // Initialize signs config after the main config
        if (getServerType() != ServerType.BUNGEE) {
            signs = new SignsConfig(this, "signs", this.getDataFolder().getPath());
        }
    }

    @Override
    public void onEnable() {
        if (!serverSoftwareSupport) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        nms.registerVersionListeners();

        if (Bukkit.getPluginManager().getPlugin("Enhanced-SlimeWorldManager") != null) {
            try {
                //noinspection rawtypes
                Constructor constructor = Class.forName("com.andrei1058.bedwars.arena.mapreset.eswm.ESlimeAdapter").getConstructor(Plugin.class);
                try {
                    api.setRestoreAdapter((RestoreAdapter) constructor.newInstance(this));
                    this.getLogger().info("Hook into Enhanced-SlimeWorldManager support!");
                } catch (InstantiationException e) {
                    e.printStackTrace();
                    api.setRestoreAdapter(new InternalAdapter(this));
                    this.getLogger().info("Failed to hook into Enhanced-SlimeWorldManager support! Using the internal reset adapter.");
                }
            } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                api.setRestoreAdapter(new InternalAdapter(this));
                this.getLogger().info("Failed to hook into Enhanced-SlimeWorldManager support! Using the internal reset adapter.");
            }
        } else if (checkSWM()) {
            try {
                //noinspection rawtypes
                Constructor constructor = Class.forName("com.andrei1058.bedwars.arena.mapreset.slime.SlimeAdapter").getConstructor(Plugin.class);
                try {
                    api.setRestoreAdapter((RestoreAdapter) constructor.newInstance(this));
                    this.getLogger().info("Hook into SlimeWorldManager support!");
                } catch (InstantiationException e) {
                    e.printStackTrace();
                    api.setRestoreAdapter(new InternalAdapter(this));
                    this.getLogger().info("Failed to hook into SlimeWorldManager support! Using internal reset adapter.");
                }
            } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                api.setRestoreAdapter(new InternalAdapter(this));
                this.getLogger().info("Failed to hook into SlimeWorldManager support! Using internal reset adapter.");
            }
        } else {
            api.setRestoreAdapter(new InternalAdapter(this));
        }

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
            if (getServerType() != ServerType.BUNGEE && config.getBoolean(ConfigPath.GENERAL_ENABLE_PARTY_CMD)) {
                nms.registerCommand("party", new PartyCommand("party"));
            }
        }, 20L);

        /* Setup plugin messaging channel */
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        /* Check if lobby location is set. Required for non Bungee servers */
        if (config.getLobbyWorldName().isEmpty() && serverType != ServerType.BUNGEE) {
            plugin.getLogger().log(java.util.logging.Level.WARNING, "Lobby location is not set!");
        }

        /* Load lobby world if not main level
         * when the server finishes loading. */
        if (getServerType() == ServerType.MULTIARENA)
            Bukkit.getScheduler().runTaskLater(this, () -> {
                if (!config.getLobbyWorldName().isEmpty()) {
                    if (Bukkit.getWorld(config.getLobbyWorldName()) == null && new File(Bukkit.getWorldContainer(), config.getLobbyWorldName() + "/level.dat").exists()) {
                        if (!config.getLobbyWorldName().equalsIgnoreCase(Bukkit.getServer().getWorlds().get(0).getName())) {
                            Bukkit.getScheduler().runTaskLater(this, () -> {
                                Bukkit.createWorld(new WorldCreator(config.getLobbyWorldName()));

                                if (Bukkit.getWorld(config.getLobbyWorldName()) != null) {
                                    Bukkit.getScheduler().runTaskLater(plugin, () -> Objects.requireNonNull(Bukkit.getWorld(config.getLobbyWorldName()))
                                            .getEntities().stream().filter(e -> e instanceof Monster).forEach(Entity::remove), 20L);
                                }
                            }, 100L);
                        }
                    }
                    Location l = config.getConfigLoc("lobbyLoc");
                    if (l != null) {
                        World w = Bukkit.getWorld(config.getLobbyWorldName());
                        if (w != null) {
                            w.setSpawnLocation(l.getBlockX(), l.getBlockY(), l.getBlockZ());
                        }
                    }
                }
            }, 1L);

        // Register events
        registerEvents(new EnderPearlLanded(), new QuitAndTeleportListener(), new BreakPlace(), new DamageDeathMove(), new Inventory(), new Interact(), new RefreshGUI(), new HungerWeatherSpawn(), new CmdProcess(),
                new FireballListener(), new EggBridge(), new SpectatorListeners(), new BaseListener(), new TargetListener(), new LangListener(), new Warnings(this), new ChatAFK());
        if (getServerType() == ServerType.BUNGEE) {
            if (autoscale) {
                //registerEvents(new ArenaListeners());
                ArenaSocket.lobbies.addAll(config.getList(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_LOBBY_SERVERS));
                new SendTask();
                registerEvents(new AutoscaleListener(), new PrePartyListener(), new JoinListenerBungee());
                Bukkit.getScheduler().runTaskTimerAsynchronously(this, new LoadedUsersCleaner(), 60L, 60L);
            } else {
                registerEvents(new ServerPingListener(), new JoinListenerBungeeLegacy());
            }
        } else if (getServerType() == ServerType.MULTIARENA || getServerType() == ServerType.SHARED) {
            registerEvents(new ArenaSelectorListener(), new BlockStatusListener());
            if (getServerType() == ServerType.MULTIARENA) {
                registerEvents(new JoinListenerMultiArena());
            } else {
                registerEvents(new JoinListenerShared());
            }
        }

        registerEvents(new WorldLoadListener());

        if (!(getServerType() == ServerType.BUNGEE && autoscale)) {
            registerEvents(new JoinHandlerCommon());
        }

        // Register setup-holograms fix
        registerEvents(new ChunkLoad());


        /* Deprecated versions */
        switch (version) {
            case "v1_8_R3":
            case "v1_9_R1":
            case "v1_9_R2":
            case "v1_10_R1":
            case "v1_11_R1":
            case "v1_12_R1":
                registerEvents(new InvisibilityPotionListener());
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

                if (getServer().getPluginManager().isPluginEnabled("Parties")) {
                    getLogger().info("Hook into Parties (by AlessioDP) support!");
                    party = new PartiesAdapter();
                } else if (Bukkit.getServer().getPluginManager().isPluginEnabled("PartyAndFriends")) {
                    getLogger().info("Hook into Party and Friends for Spigot (by Simonsator) support!");
                    party = new PAF();
                } else if (Bukkit.getServer().getPluginManager().isPluginEnabled("Spigot-Party-API-PAF")) {
                    getLogger().info("Hook into Spigot Party API for Party and Friends Extended (by Simonsator) support!");
                    party = new PAFBungeecordRedisApi();
                }

                if (party instanceof NoParty) {
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
            remoteDatabase.init();
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
            /*if (getServerType() == ServerType.BUNGEE) {
                if (Arena.getArenas().size() > 0) {
                    ArenaSocket.sendMessage(Arena.getArenas().get(0));
                }
            }*/
        }, 40L);

        /* Save messages for stats gui items if custom items added, for each language */
        Language.setupCustomStatsMessages();


        /* PlaceholderAPI Support */
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("Hooked into PlaceholderAPI support!");
            new PAPISupport().register();
            SupportPAPI.setSupportPAPI(new SupportPAPI.withPAPI());
        }
        /*
         * Vault support
         * The task is to initialize after all plugins have loaded,
         *  to make sure any economy/chat plugins have been loaded and registered.
         */
        Bukkit.getScheduler().runTask(this, () -> {
            if (this.getServer().getPluginManager().getPlugin("Vault") != null) {
                try {
                    //noinspection rawtypes
                    RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
                   if(rsp != null) {
                       WithChat.setChat((net.milkbowl.vault.chat.Chat) rsp.getProvider());
                       plugin.getLogger().info("Hooked into vault chat support!");
                       chat = new WithChat();
                   } else {
                       plugin.getLogger().info("Vault found, but no chat provider!");
                       chat = new NoChat();
                   }
                } catch (Exception var2_2) {
                    chat = new NoChat();
                }
                try {
                    registerEvents(new MoneyListeners());
                    RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> rsp = this.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
                    if (rsp != null) {
                        WithEconomy.setEconomy(rsp.getProvider());
                        plugin.getLogger().info("Hooked into vault economy support!");
                        economy = new WithEconomy();
                    } else {
                        plugin.getLogger().info("Vault found, but no economy provider!");
                        economy = new NoEconomy();
                    }
                } catch (Exception var2_2) {
                    economy = new NoEconomy();
                }
            } else {
                chat = new NoChat();
                economy = new NoEconomy();
            }
        });

        /* Chat support */
        if (config.getBoolean(ConfigPath.GENERAL_CHAT_FORMATTING)) {
            registerEvents(new ChatFormatting());
        }

        /* Protect glass walls from tnt explosion */
        nms.registerTntWhitelist();

        /* Prevent issues on reload */
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.kickPlayer("BedWars1058 was RELOADED! (do not reload plugins)");
        }

        /* Load sounds configuration */
        Sounds.init();

        /* Initialize shop */
        shop = new ShopManager();

        //Leave this code at the end of the enable method
        for (Language l : Language.getLanguages()) {
            l.setupUnSetCategories();
            Language.addDefaultMessagesCommandItems(l);
        }

        LevelsConfig.init();

        /* Load Money Configuration */
        MoneyConfig.init();

        // bStats metrics
        Metrics metrics = new Metrics(this, 1885);
        metrics.addCustomChart(new SimplePie("server_type", () -> getServerType().toString()));
        metrics.addCustomChart(new SimplePie("default_language", () -> Language.getDefaultLanguage().getIso()));
        metrics.addCustomChart(new SimplePie("auto_scale", () -> String.valueOf(autoscale)));
        metrics.addCustomChart(new SimplePie("party_adapter", () -> party.getClass().getName()));
        metrics.addCustomChart(new SimplePie("chat_adapter", () -> chat.getClass().getName()));
        metrics.addCustomChart(new SimplePie("level_adapter", () -> getLevelSupport().getClass().getName()));
        metrics.addCustomChart(new SimplePie("db_adapter", () -> getRemoteDatabase().getClass().getName()));
        metrics.addCustomChart(new SimplePie("map_adapter", () -> String.valueOf(getAPI().getRestoreAdapter().getOwner().getName())));

        if (Bukkit.getPluginManager().getPlugin("VipFeatures") != null) {
            try {
                IVipFeatures vf = Bukkit.getServicesManager().getRegistration(IVipFeatures.class).getProvider();
                vf.registerMiniGame(new VipFeatures(this));
                registerEvents(new VipListeners(vf));
                getLogger().log(java.util.logging.Level.INFO, "Hook into VipFeatures support.");
            } catch (Exception e) {
                getLogger().warning("Could not load support for VipFeatures.");
            } catch (MiniGameAlreadyRegistered miniGameAlreadyRegistered) {
                miniGameAlreadyRegistered.printStackTrace();
            }
        }

        /* Check updates */
        new SpigotUpdater(this, 50942, true).checkUpdate();


        Bukkit.getScheduler().runTaskLater(this, () -> getLogger().info("This server is running in " + getServerType().toString() + " with auto-scale " + autoscale), 100L);

        // Initialize team upgrades
        com.andrei1058.bedwars.upgrades.UpgradesManager.init();

        int playerListRefreshInterval = config.getInt(ConfigPath.SB_CONFIG_SIDEBAR_LIST_REFRESH);
        if (playerListRefreshInterval < 1) {
            Bukkit.getLogger().info("Scoreboard names list refresh is disabled. (Is set to " + playerListRefreshInterval + ").");
        } else {
            if (playerListRefreshInterval < 20) {
                Bukkit.getLogger().warning("Scoreboard names list refresh interval is set to: " + playerListRefreshInterval);
                Bukkit.getLogger().warning("It is not recommended to use a value under 20 ticks.");
                Bukkit.getLogger().warning("If you expect performance issues please increase its timer.");
            }
            Bukkit.getScheduler().runTaskTimer(this, new SidebarListRefresh(), 23L, playerListRefreshInterval);
        }

        int placeholdersRefreshInterval = config.getInt(ConfigPath.SB_CONFIG_SIDEBAR_PLACEHOLDERS_REFRESH_INTERVAL);
        if (placeholdersRefreshInterval < 1) {
            Bukkit.getLogger().info("Scoreboard placeholders refresh is disabled. (Is set to " + placeholdersRefreshInterval + ").");
        } else {
            if (placeholdersRefreshInterval < 20) {
                Bukkit.getLogger().warning("Scoreboard placeholders refresh interval is set to: " + placeholdersRefreshInterval);
                Bukkit.getLogger().warning("It is not recommended to use a value under 20 ticks.");
                Bukkit.getLogger().warning("If you expect performance issues please increase its timer.");
            }
            Bukkit.getScheduler().runTaskTimer(this, new SidebarPlaceholderRefresh(), 28L, placeholdersRefreshInterval);
        }

        int titleRefreshInterval = config.getInt(ConfigPath.SB_CONFIG_SIDEBAR_TITLE_REFRESH_INTERVAL);
        if (titleRefreshInterval < 1) {
            Bukkit.getLogger().info("Scoreboard title refresh is disabled. (Is set to " + titleRefreshInterval + ").");
        } else {
            if (titleRefreshInterval < 4) {
                Bukkit.getLogger().warning("Scoreboard title refresh interval is set to: " + titleRefreshInterval);
                Bukkit.getLogger().warning("If you expect performance issues please increase its timer.");
            }
            Bukkit.getScheduler().runTaskTimerAsynchronously(this, new SidebarTitleRefresh(), 32L, titleRefreshInterval);
        }

        int healthAnimationInterval = config.getInt(ConfigPath.SB_CONFIG_SIDEBAR_HEALTH_REFRESH);
        if (healthAnimationInterval < 1) {
            Bukkit.getLogger().info("Scoreboard health animation refresh is disabled. (Is set to " + healthAnimationInterval + ").");
        } else {
            if (healthAnimationInterval < 20) {
                Bukkit.getLogger().warning("Scoreboard health animation refresh interval is set to: " + healthAnimationInterval);
                Bukkit.getLogger().warning("It is not recommended to use a value under 20 ticks.");
                Bukkit.getLogger().warning("If you expect performance issues please increase its timer.");
            }
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new SidebarLifeRefresh(), 40L, healthAnimationInterval);
        }

        registerEvents(new ScoreboardListener());

        // Halloween Special
        HalloweenSpecial.init();

        SpoilPlayerTNTFeature.init();
    }

    public void onDisable() {
        shuttingDown = true;
        if (!serverSoftwareSupport) return;
        if (getServerType() == ServerType.BUNGEE) {
            ArenaSocket.disable();
        }
        for (IArena a : Arena.getArenas()) {
            try {
                a.disable();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private void loadArenasAndSigns() {

        api.getRestoreAdapter().convertWorlds();

        File dir = new File(plugin.getDataFolder(), "/Arenas");
        if (dir.exists()) {
            List<File> files = new ArrayList<>();
            File[] fls = dir.listFiles();
            for (File fl : Objects.requireNonNull(fls)) {
                if (fl.isFile()) {
                    if (fl.getName().endsWith(".yml")) {
                        files.add(fl);
                    }
                }
            }

            if (serverType == ServerType.BUNGEE && !autoscale) {
                if (files.isEmpty()) {
                    this.getLogger().log(java.util.logging.Level.WARNING, "Could not find any arena!");
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

    public static void registerEvents(Listener... listeners) {
        Arrays.stream(listeners).forEach(l -> plugin.getServer().getPluginManager().registerEvents(l, plugin));
    }

    public static void setDebug(boolean value) {
        debug = value;
    }

    public static void setServerType(ServerType serverType) {
        BedWars.serverType = serverType;
        if (serverType == ServerType.BUNGEE) autoscale = true;
    }

    public static void setAutoscale(boolean autoscale) {
        BedWars.autoscale = autoscale;
    }

    public static void debug(String message) {
        if (debug) {
            plugin.getLogger().info("DEBUG: " + message);
        }
    }

    public static String getForCurrentVersion(String v18, String v12, String v13) {
        switch (getServerVersion()) {
            case "v1_8_R3":
                return v18;
            case "v1_9_R1":
            case "v1_9_R2":
            case "v1_10_R1":
            case "v1_11_R1":
            case "v1_12_R1":
                return v12;
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
                Bukkit.getPluginManager().registerEvents(new LevelListeners(), BedWars.plugin);
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
        BedWars.lobbyWorld = lobbyWorld;
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

    public static StatsManager getStatsManager() {
        return statsManager;
    }

    public static com.andrei1058.bedwars.api.BedWars getAPI() {
        return api;
    }

    /**
     * This is used to check if can hook in SlimeWorldManager support.
     *
     * @return true if can load swm support.
     */
    private boolean checkSWM() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        if (plugin == null) return false;
        switch (plugin.getDescription().getVersion()) {
            case "2.2.0":
            case "2.1.3":
            case "2.1.2":
            case "2.1.1":
            case "2.1.0":
            case "2.0.5":
            case "2.0.4":
            case "2.0.3":
            case "2.0.2":
            case "2.0.1":
            case "2.0.0":
            case "1.1.4":
            case "1.1.3":
            case "1.1.2":
            case "1.1.1":
            case "1.1.0":
            case "1.0.2":
            case "1.0.1":
            case "1.0.0-BETA":
                getLogger().warning("Could not hook into SlimeWorldManager support! You are running an unsupported version");
                return false;
            default:
                return true;
        }
    }

    public static boolean isShuttingDown() {
        return shuttingDown;
    }

    public static void setParty(Party party) {
        BedWars.party = party;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new VoidChunkGenerator();
    }
}
