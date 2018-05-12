package com.andrei1058.bedwars;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.GameAPI;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.*;
import com.andrei1058.bedwars.commands.LeaveCommand;
import com.andrei1058.bedwars.commands.MainCommand;
import com.andrei1058.bedwars.configuration.*;
import com.andrei1058.bedwars.listeners.*;
import com.andrei1058.bedwars.support.Metrics;
import com.andrei1058.bedwars.support.bukkit.*;
import com.andrei1058.bedwars.support.bukkit.v1_10_R1.v1_10_R1;
import com.andrei1058.bedwars.support.bukkit.v1_11_R1.v1_11_R1;
import com.andrei1058.bedwars.support.bukkit.v1_12_R1.v1_12_R1;
import com.andrei1058.bedwars.support.bukkit.v1_8_R3.v1_8_R3;
import com.andrei1058.bedwars.support.bukkit.v1_9_R1.v1_9_R1;
import com.andrei1058.bedwars.support.bukkit.v1_9_R2.v1_9_R2;
import com.andrei1058.bedwars.support.lang.Internal;
import com.andrei1058.bedwars.support.lang.Lang;
import com.andrei1058.bedwars.support.levels.Level;
import com.andrei1058.bedwars.support.levels.NoLevel;
import com.andrei1058.bedwars.support.papi.PAPISupport;
import com.andrei1058.bedwars.support.party.Party;
import com.andrei1058.bedwars.support.stats.MySQL;
import com.andrei1058.bedwars.support.stats.None;
import com.andrei1058.bedwars.support.vault.*;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.*;

import static com.andrei1058.bedwars.configuration.Language.setupLang;

public class Main extends JavaPlugin {

    private static ServerType serverType = ServerType.MULTIARENA;
    public static boolean safeMode, lobbyServer = false, debug = true;
    public static HashMap<Entity, String> npcs = new HashMap<>();
    public static String mainCmd = "bw", link = "https://www.spigotmc.org/resources/50942/";
    public static ConfigManager config, signs, spigot, generators;
    public static ShopManager shop;
    public static UpgradesManager upgrades;
    public static Language lang;
    public static Main plugin;
    public static NMS nms;
    private static Lang langSupport;
    private static BukkitTask ticks, tick;
    private static Party party;
    private static Chat chat;
    private static Level level;
    private static Economy economy;
    private static String version = Bukkit.getServer().getClass().getName().split("\\.")[3];
    public static com.andrei1058.bedwars.support.stats.Database database;

    @Override
    public void onLoad() {
        plugin = this;
        config = new ConfigManager("config", "plugins/" + this.getName(), false);

        Language en = new Language("en");
        setupLang(en);
        Language.getLanguages().remove(en);
        setupConfig();
        generators = new ConfigManager("generators", "plugins/"+ this.getName(), false);
        setupGeneratorsCfg();
        upgrades = new UpgradesManager("upgrades", "plugins/" + this.getName());
    }

    @Override
    public void onEnable() {
        boolean support = true;
        /** Load version support 1.8 - 1.12 */
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
            default:
                support = false;
        }

        if (!support){
            this.setEnabled(false);
            this.getLogger().severe("I can't run on your version: " + version);
            return;
        }

        /** Register main command */
        nms.registerCommand(mainCmd, new MainCommand(mainCmd));

        /** Setup plugin messaging channel */
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new Bungee());
        Bukkit.getServicesManager().register(GameAPI.class, new BedWars(), this, ServicePriority.Highest);

        /** Check if lobby location is set. Required for non Bungee servers */
        if (config.getLobbyWorldName().isEmpty() && serverType != ServerType.BUNGEE) {
            plugin.getLogger().severe("Lobby location is not set!");
            return;
        }

        /** Load lobby world if not main level */
        if (!config.getLobbyWorldName().equalsIgnoreCase(Bukkit.getServer().getWorlds().get(0).getName())) {
            Bukkit.createWorld(new WorldCreator(config.getLobbyWorldName()));
        }

        /** Remove entities from lobby */
        if (!config.getLobbyWorldName().isEmpty()) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getWorld(config.getLobbyWorldName())
                    .getEntities().stream().filter(e -> e instanceof Monster).forEach(Entity::remove), 20L);
        }

        /** Register events */
        registerEvents(new JoinLeaveTeleport(), new BreakPlace(), new DamageDeathMove(), new Inventory(), new Interact(), new RefreshGUI(), new HungerWeatherSpawn(), new CmdProcess());
        if (getServerType() == ServerType.BUNGEE) {
            registerEvents(new Ping());
        }

        /** Load version support */
        switch (version) {
            case "v1_12_R1":
                registerEvents(new EntityDropPick());
                break;
            default:
                registerEvents(new PlayerDropPick());
                break;
            case "v1_8_R2":
            case "v1_8_R3":
                registerEvents(new PlayerDropPick());
                /**Bukkit.getScheduler().runTaskLater(this, ()-> {
                    System.out.println("\u001B[31m[WARN] BedWars1058 is going to abort support for this server version in the future.\nPlease consider upgrading to a newer paper/spigot version.\u001B[0m");
                }, 40L);*/
                break;
        }

        /** Load join signs */
        loadArenasAndSigns();

        /** Party support */
        //todo check for party api
        party = new com.andrei1058.bedwars.support.party.internal.Internal();
        getLogger().info("Loading internal Party system. /party");

        /** Levels support */
        //todo levels addon
        level = new NoLevel();

        /** Language support */
        try {
            langSupport = Internal.class.newInstance();
            new ConfigManager("database", "plugins/" + this.getName() + "/Languages", false);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        /** Register tasks */
        ticks = new Refresh().runTaskTimer(this, 20l, 20l);
        tick = new Rotate().runTaskTimer(this, 120, 1);

        /** Setup bStats metrics */
        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("server_type", () -> getServerType().toString()));
        metrics.addCustomChart(new Metrics.SimplePie("default_language", () -> lang.getIso()));

        /** Register NMS entities */
        nms.registerEntities();

        /** Setup shop */
        shop = new ShopManager("shop", "plugins/" + this.getName());
        shop.loadShop();

        /** Check for updates */
        Misc.checkUpdate();

        /** Database support */
        if (config.getBoolean("database.enable")) {
            database = new MySQL();
        } else {
            database = new None();
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            database.setupGeneralTables();
            plugin.spawnNPCs();
        }, 40L);

        /** Save messages for stats gui items if custom items added, for each language */
        Language.setupCustomStatsMessages();

        /** PlaceholderAPI Support */
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            getLogger().info("Hook into PlaceholderAPI support!");
            new PAPISupport().register();
        }

        /** Vault support */
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

        /** Chat support */
        if (config.getBoolean("formatChat")){
            registerEvents(new PlayerChat());
        }
    }

    public void onDisable() {
        /** Close database */
        database.close();

        /** Cancel tasks */
        if (tick != null) {
            tick.cancel();
        }
        if (ticks != null) {
            ticks.cancel();
        }
    }

    private void setupConfig() {
        YamlConfiguration yml = config.getYml();

        yml.options().header(plugin.getDescription().getName() + " by andrei1058. https://www.spigotmc.org/members/39904/\n" +
                "Documentation here: https://github.com/andrei1058/BedWars1058/wiki\n");
        yml.addDefault("serverType", "MULTIARENA");
        yml.addDefault("safeMode", false);
        yml.addDefault("language", "en");
        yml.addDefault("storeLink", "https://www.spigotmc.org/resources/authors/39904/");
        yml.addDefault("lobbyServer", "hub");
        yml.addDefault("startingCountdown", 40);
        yml.addDefault("bedsDestroyCountdown", 360);
        yml.addDefault("dragonSpawnCountdown", 600);
        yml.addDefault("gameEndCountdown", 120);
        yml.addDefault("globalChat", false);
        yml.addDefault("formatChat", true);
        yml.addDefault("disableCrafting", true);
        yml.addDefault("debug", false);
        yml.addDefault("lobbyScoreboard", true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_MODE_GAMES_BEFORE_RESTART, 30);

        yml.addDefault("database.enable", false);
        yml.addDefault("database.host", "localhost");
        yml.addDefault("database.port", 3306);
        yml.addDefault("database.database", "bedwars1058");
        yml.addDefault("database.user", "root");
        yml.addDefault("database.pass", "p4ss");
        yml.addDefault("database.ssl", false);

        yml.addDefault("items.arenaGui.enable", true);
        yml.addDefault("items.arenaGui.itemStack", "STAINED_CLAY");
        yml.addDefault("items.arenaGui.data", 5);
        yml.addDefault("items.arenaGui.enchanted", true);
        yml.addDefault("items.arenaGui.slot", 4);
        yml.addDefault("items.leave.enable", true);
        yml.addDefault("items.leave.itemStack", "BED");
        yml.addDefault("items.leave.data", 0);
        yml.addDefault("items.leave.enchanted", false);
        yml.addDefault("items.leave.slot", 8);
        yml.addDefault("items.stats.enable", true);
        yml.addDefault("items.stats.itemStack", "PAPER");
        yml.addDefault("items.stats.data", 0);
        yml.addDefault("items.stats.enchanted", false);
        yml.addDefault("items.stats.slot", 0);
        yml.addDefault("arenaGui.settings.size", 27);
        yml.addDefault("arenaGui.settings.startSlot", 10);
        yml.addDefault("arenaGui.settings.endSlot", 16);
        yml.addDefault("arenaGui.settings.showPlaying", false);
        yml.addDefault("arenaGui.waiting.itemStack", "STAINED_CLAY");
        yml.addDefault("arenaGui.waiting.data", 5);
        yml.addDefault("arenaGui.waiting.enchanted", false);
        yml.addDefault("arenaGui.starting.itemStack", "STAINED_CLAY");
        yml.addDefault("arenaGui.starting.data", 7);
        yml.addDefault("arenaGui.starting.enchanted", true);
        yml.addDefault("arenaGui.playing.itemStack", "STAINED_CAY");
        yml.addDefault("arenaGui.playing.data", 4);
        yml.addDefault("arenaGui.playing.enchanted", false);

        /** default stats GUI items */
        yml.addDefault("statsGUI.invSize", 27);
        Misc.addDefaultStatsItem(yml, 10, Material.DIAMOND, 0, "wins");
        Misc.addDefaultStatsItem(yml, 11, Material.REDSTONE, 0, "looses");
        Misc.addDefaultStatsItem(yml, 12, Material.IRON_SWORD, 0, "kills");
        Misc.addDefaultStatsItem(yml, 13, Material.SKULL_ITEM, 0, "deaths");
        Misc.addDefaultStatsItem(yml, 14, Material.DIAMOND_SWORD, 0, "finalKills");
        Misc.addDefaultStatsItem(yml, 15, Material.SKULL_ITEM, 1,  "finalDeaths");
        Misc.addDefaultStatsItem(yml, 16, Material.BED, 0, "bedsDestroyed");
        Misc.addDefaultStatsItem(yml, 21, Material.STAINED_GLASS_PANE, 0, "firstPlay");
        Misc.addDefaultStatsItem(yml, 22, Material.CHEST, 0, "gamesPlayed");
        Misc.addDefaultStatsItem(yml, 23, Material.STAINED_GLASS_PANE, 0, "lastPlay");

        yml.addDefault("startItems", Arrays.asList("WOOD_SWORD"));
        yml.addDefault("blockedCmds", Arrays.asList("spawn", "tpa", "tpaccept", "warp", "goto", "tp", "tphere", "gamemode", "fly", "kill"));
        yml.options().copyDefaults(true);
        config.save();
        config.set("generators", null);

        String whatLang = "en";
        for (File f : new File("plugins/" + this.getDescription().getName() + "/Languages").listFiles()) {
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
        safeMode = yml.getBoolean("safeMode");
        debug = yml.getBoolean("debug");
        new ConfigManager("bukkit", Bukkit.getWorldContainer().getPath(), false).set("ticks-per.autosave", -1);
        spigot = new ConfigManager("spigot", Bukkit.getWorldContainer().getPath(), false);
        switch (yml.getString("serverType").toUpperCase()) {
            case "BUNGEE":
                serverType = ServerType.BUNGEE;
                new ConfigManager("bukkit", Bukkit.getWorldContainer().getPath(), false).set("settings.allow-end", false);
                spigot.set("settings.bungeecord", true);
                break;
            case "SHARED":
                serverType = ServerType.SHARED;
                setupSigns();
                break;
            default:
                setupSigns();
                config.set("serverType", "MULTIARENA");
                new ConfigManager("bukkit", Bukkit.getWorldContainer().getPath(), false).set("settings.allow-end", false);
                break;
        }
    }

    public static void spawnNPCs() {
        if (config.getYml().get("npcLoc") != null) {
            for (String s : config.getYml().getStringList("npcLoc")) {
                String[] data = s.split(",");
                Location l = new Location(Bukkit.getWorld(data[5]), Double.valueOf(data[0]), Double.valueOf(data[1]), Double.valueOf(data[2]), Float.valueOf(data[3]), Float.valueOf(data[4]));
                try {
                    EntityType.valueOf(data[6]);
                } catch (Exception ex) {
                    plugin.getLogger().severe("Invalid EntityType at npcLoc: " + data[6]);
                    continue;
                }
                List<Entity> enti = (List<Entity>) l.getWorld().getNearbyEntities(l, 0, 1, 0);
                for (Entity en : enti) {
                    if (en.getType() == EntityType.valueOf(data[6])) {
                        en.remove();
                    }
                    if (en.getType() == EntityType.ARMOR_STAND) {
                        en.remove();
                    }
                }
                Bukkit.getWorld(config.getLobbyWorldName()).save();
                nms.spawnNPC(EntityType.valueOf(data[6]), l, ChatColor.translateAlternateColorCodes('&', data[7]), data[8]);
            }
        }
    }

    private void setupSigns() {
        signs = new ConfigManager("signs", "plugins/" + plugin.getName(), false);
        YamlConfiguration yml = signs.getYml();
        yml.addDefault("format", Arrays.asList("&a[arena]", "", "&2[on]&9/&2[max]", "[status]"));
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
            for (int x = 0; x < fls.length; x++) {
                if (fls[x].isFile()) {
                    if (fls[x].getName().contains(".yml")) {
                        files.add(fls[x]);
                    }
                }
            }
            if (serverType == ServerType.BUNGEE) {
                Random r = new Random();
                int x = r.nextInt(files.size());
                new Arena(files.get(x).getName().replace(".yml", ""));
            } else {
                for (int x = 0; x < files.size(); x++){
                    new Arena(files.get(x).getName().replace(".yml", ""));
                }
            }
            if (Arena.getArenas().isEmpty()) {
                if (getServerType() == ServerType.BUNGEE) {
                    plugin.getLogger().severe("Please set the server type to MULTIARENA and do the setup.");
                    config.set("serverType", "MULTIARENA");
                    Bukkit.getServer().spigot().restart();
                    plugin.setEnabled(false);
                    return;
                }
            } else {
                if (getServerType() != ServerType.BUNGEE) {
                    if (signs.getYml().get("locations") != null) {
                        for (String st : signs.getYml().getStringList("locations")) {
                            String[] data = st.split(",");
                            Arena a = Arena.getArenaByName(data[0]);
                            if (a != null) {
                                a.addSign(new Location(Bukkit.getWorld(data[6]), Double.valueOf(data[1]), Double.valueOf(data[2]), Double.valueOf(data[3])));
                            }
                        }
                    }
                }
            }
        } else {
            if (getServerType() == ServerType.BUNGEE) {
                plugin.getLogger().severe("Please set the server type to MULTIARENA and do the setup.");
                config.set("serverType", "MULTIARENA");
                Bukkit.getServer().spigot().restart();
                plugin.setEnabled(false);
                return;
            }
        }
    }

    private void registerEvents(Listener... listeners) {
        Arrays.stream(listeners).forEach(l -> plugin.getServer().getPluginManager().registerEvents(l, this));
        if (!nms.isBukkitCommandRegistered("leave")) {
            nms.registerCommand("leave", new LeaveCommand("leave"));
        }
        if (!nms.isBukkitCommandRegistered("party")){
            nms.registerCommand("party", new com.andrei1058.bedwars.commands.Party("party"));
        }
    }

    public static void debug(String message) {
        if (debug) {
            plugin.getLogger().info("DEBUG: " + message);
        }
    }

    private static void setupGeneratorsCfg(){
        YamlConfiguration yml = generators.getYml();

        yml.options().header(plugin.getDescription().getName() + " by andrei1058." +
                "\ngenerators.yml Documentation\n");
        yml.addDefault("Default."+ConfigPath.GENERATOR_IRON_DELAY, 2);
        yml.addDefault("Default."+ConfigPath.GENERATOR_IRON_AMOUNT, 2);
        yml.addDefault("Default."+ConfigPath.GENERATOR_GOLD_DELAY, 6);
        yml.addDefault("Default."+ConfigPath.GENERATOR_GOLD_AMOUNT, 2);
        yml.addDefault(ConfigPath.GENERATOR_STACK_ITEMS, false);

        yml.addDefault("Default."+ConfigPath.GENERATOR_DIAMOND_TIER_I_DELAY, 30);
        yml.addDefault("Default."+ConfigPath.GENERATOR_DIAMOND_TIER_I_MAX, 4);
        yml.addDefault("Default."+ConfigPath.GENERATOR_DIAMOND_TIER_II_DELAY, 20);
        yml.addDefault("Default."+ConfigPath.GENERATOR_DIAMOND_TIER_II_MAX, 6);
        yml.addDefault("Default."+ConfigPath.GENERATOR_DIAMOND_TIER_II_START, 360);
        yml.addDefault("Default."+ConfigPath.GENERATOR_DIAMOND_TIER_III_DELAY, 15);
        yml.addDefault("Default."+ConfigPath.GENERATOR_DIAMOND_TIER_III_MAX, 8);
        yml.addDefault("Default."+ConfigPath.GENERATOR_DIAMOND_TIER_III_START, 1080);
        yml.addDefault("Default."+ConfigPath.GENERATOR_EMERALD_TIER_I_DELAY, 70);
        yml.addDefault("Default."+ConfigPath.GENERATOR_EMERALD_TIER_I_MAX, 4);
        yml.addDefault("Default."+ConfigPath.GENERATOR_EMERALD_TIER_II_DELAY, 50);
        yml.addDefault("Default."+ConfigPath.GENERATOR_EMERALD_TIER_II_MAX, 6);
        yml.addDefault("Default."+ConfigPath.GENERATOR_EMERALD_TIER_II_START, 720);
        yml.addDefault("Default."+ConfigPath.GENERATOR_EMERALD_TIER_III_DELAY, 30);
        yml.addDefault("Default."+ConfigPath.GENERATOR_EMERALD_TIER_III_MAX, 8);
        yml.addDefault("Default."+ConfigPath.GENERATOR_EMERALD_TIER_III_START, 1440);
        yml.options().copyDefaults(true);
        generators.save();
    }

    public static ServerType getServerType() {
        return serverType;
    }

    public static Lang getLangSupport() {
        return langSupport;
    }

    public static Party getParty() {
        return party;
    }

    public static Chat getChatSupport() {
        return chat;
    }

    public static Level getLevelSupport() {
        return level;
    }

    public static Economy getEconomy() {
        return economy;
    }

    public static ConfigManager getGeneratorsCfg() {
        return generators;
    }

}
