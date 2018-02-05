package com.andrei1058.bedwars;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.GameAPI;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.*;
import com.andrei1058.bedwars.commands.LeaveCommand;
import com.andrei1058.bedwars.commands.MainCommand;
import com.andrei1058.bedwars.configuration.ConfigManager;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.ShopManager;
import com.andrei1058.bedwars.configuration.UpgradesManager;
import com.andrei1058.bedwars.listeners.*;
import com.andrei1058.bedwars.support.Metrics;
import com.andrei1058.bedwars.support.bukkit.*;
import com.andrei1058.bedwars.support.lang.Internal;
import com.andrei1058.bedwars.support.lang.Lang;
import com.andrei1058.bedwars.support.levels.Level;
import com.andrei1058.bedwars.support.levels.NoLevel;
import com.andrei1058.bedwars.support.party.Party;
import com.andrei1058.bedwars.support.vault.*;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.*;

import static com.andrei1058.bedwars.configuration.Language.getMsg;
import static com.andrei1058.bedwars.configuration.Language.setupLang;

public class Main extends JavaPlugin {

    private static ServerType serverType = ServerType.MULTIARENA;
    public static boolean safeMode, lobbyServer = false, debug = false;
    public static HashMap<Entity, String> npcs = new HashMap<>();
    public static String mainCmd = "bw", link = "https://www.spigotmc.org/resources/bedwars1058-the-most-modern-bedwars-plugin.50942/";
    public static ConfigManager config, signs;
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

    public void onLoad() {
        plugin = this;
        config = new ConfigManager("config", "plugins/" + this.getName(), false);

        Language en = new Language("en");
        setupLang(en);
        Language.getLanguages().remove(en);
        setupConfig();
        upgrades = new UpgradesManager("upgrades", "plugins/" + this.getName());
        /* Load version support 1.8 - 1.12*/
        switch (version) {
            case "v1_8_R1":
                nms = new v1_8_R1();
                break;
            case "v1_8_R2":
                nms = new v1_8_R2();
                break;
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
                this.setEnabled(false);
                this.getLogger().severe("I can't run on your version: " + version);
                return;
        }

    }

    public void onEnable() {
        Bukkit.getServicesManager().register(GameAPI.class, new BedWars(), this, ServicePriority.Highest);
        nms.registerCommand(mainCmd, new MainCommand(mainCmd));
        if (config.getLobbyWorldName().isEmpty() && serverType != ServerType.BUNGEE) {
            plugin.getLogger().severe("Lobby location is not set!");
            return;
        }
        if (!config.getLobbyWorldName().equalsIgnoreCase(Bukkit.getServer().getWorlds().get(0).getName())) {
            Bukkit.createWorld(new WorldCreator(config.getLobbyWorldName()));
        }
        if (!config.getLobbyWorldName().isEmpty()) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.getWorld(config.getLobbyWorldName()).getEntities().stream().filter(e -> e.getType() != EntityType.PLAYER).filter(e -> e.getType() != EntityType.PAINTING).filter(e -> e.getType() != EntityType.ITEM_FRAME).forEach(Entity::remove), 30L);
        }
        registerEvents(new JoinLeave(), new BreakPlace(), new PvP(), new Inventory(), new Interact(), new RefreshGUI(), new HungerWeatherSpawn(), new PlayerChat(), new CmdProcess());
        if (getServerType() == ServerType.BUNGEE) {
            registerEvents(new Ping());
        }
        switch (version) {
            case "v1_12_R1":
                registerEvents(new EntityPickUp());
                break;
            default:
                registerEvents(new PlayerPickUp());
                break;
        }
        Misc.checkLobbyServer();
        loadArenasAndSigns();
        //todo check for party api
        //todo levels addon
        party = new com.andrei1058.bedwars.support.party.internal.Internal();
        Bukkit.getScheduler().runTaskLater(this, () -> {
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
        }, 15L);
        level = new NoLevel();

        try {
            langSupport = Internal.class.newInstance();
            new ConfigManager("database", "plugins/" + this.getName() + "/Languages", false);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ticks = new Refresh().runTaskTimer(this, 20l, 20l);
        tick = new Rotate().runTaskTimer(this, 120, 1);
        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("default_language", () -> lang.getLangName()));
        nms.registerEntities();
        shop = new ShopManager("shop", "plugins/" + this.getName());
        shop.loadShop();
        Misc.checkUpdate();
    }

    public void onDisable() {
        tick.cancel();
        ticks.cancel();
    }

    private void setupConfig() {
        YamlConfiguration yml = config.getYml();

        yml.options().header(plugin.getDescription().getName() + " by andrei1058. https://www.spigotmc.org/members/39904/\n" +
                "Documentation here: \n" + //todo add documentation link
                "\nserverType: MULTIARENA     (Types: BUNGEE, MULTIARENA and SHARED. Shared is a multi-world server with multiple minigames)\n" +
                "safeMode: true             (Protect your setup from beeing changed by any op)\n" +
                "storeLink: http://yoursite.com/store   (It is use as click action on \"consider donating\" messages)\n" +
                "lobbyServer: hub       (Your main lobby for this minigame. If bungee mode.)\n" +
                "startingCountdown: 40       (When the minimum players amount is reached it will start the countdown)");
        yml.addDefault("serverType", "MULTIARENA");
        yml.addDefault("safeMode", false);
        yml.addDefault("language", "en");
        yml.addDefault("storeLink", "https://www.spigotmc.org/resources/authors/39904/");
        yml.addDefault("lobbyServer", "hub");
        yml.addDefault("startingCountdown", 40);
        yml.addDefault("globalChat", false);
        yml.addDefault("disableCrafting", true);
        //yml.addDefault("gameCountdown", 20); //todo It depends on game type which could be countUp instead

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
        yml.addDefault("arenaGui.settings.size", 27);
        yml.addDefault("arenaGui.settings.startSlot", 10);
        yml.addDefault("arenaGui.settings.endSlot", 16);
        yml.addDefault("arenaGui.settings.showPlaying", false);
        yml.addDefault("arenaGui.waiting.itemStack", "STAINED_CLAY");
        yml.addDefault("arenaGui.waiting.data", 5);
        yml.addDefault("arenaGui.starting.itemStack", "STAINED_CLAY");
        yml.addDefault("arenaGui.starting.data", 7);
        yml.addDefault("arenaGui.playing.itemStack", "STAINED_CLAY");
        yml.addDefault("arenaGui.playing.data", 4);
        yml.addDefault("generators.diamond.tier1.delay", 30);
        yml.addDefault("generators.diamond.tier1.max", 4);
        yml.addDefault("generators.diamond.tier2.delay", 20);
        yml.addDefault("generators.diamond.tier2.max", 6);
        yml.addDefault("generators.diamond.tier2.start", 360);
        yml.addDefault("generators.diamond.tier3.delay", 15);
        yml.addDefault("generators.diamond.tier3.max", 8);
        yml.addDefault("generators.diamond.tier3.start", 1080);
        yml.addDefault("generators.emerald.tier1.delay", 70);
        yml.addDefault("generators.emerald.tier1.max", 4);
        yml.addDefault("generators.emerald.tier2.delay", 50);
        yml.addDefault("generators.emerald.tier2.max", 6);
        yml.addDefault("generators.emerald.tier2.start", 720);
        yml.addDefault("generators.emerald.tier3.delay", 30);
        yml.addDefault("generators.emerald.tier3.max", 8);
        yml.addDefault("generators.emerald.tier3.start", 1440);
        yml.addDefault("startItems", Arrays.asList("WOOD_SWORD"));
        yml.addDefault("blockedCmds", Arrays.asList("spawn", "tpa", "tpaccept", "warp", "goto", "tp", "tphere", "gamemode", "fly", "kill"));
        yml.options().copyDefaults(true);
        config.save();

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
        new ConfigManager("bukkit", Bukkit.getWorldContainer().getPath(), false).set("ticks-per.autosave", -1);
        //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-off");
        switch (yml.getString("serverType").toUpperCase()) {
            case "BUNGEE":
                serverType = ServerType.BUNGEE;
                new ConfigManager("bukkit", Bukkit.getWorldContainer().getPath(), false).set("settings.allow-end", false);
                new ConfigManager("spigot", Bukkit.getWorldContainer().getPath(), false).set("settings.bungeecord", true);
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
            for (File f : dir.listFiles()) {
                if (f.isFile()) {
                    if (f.getName().contains(".yml")) {
                        files.add(f);
                    }
                }
            }
            if (serverType == ServerType.BUNGEE) {
                Random r = new Random();
                int x = r.nextInt(files.size());
                new Arena(files.get(x).getName().replace(".yml", ""));
                //nms.setProperties("level-name", Arena.getArenas().get(0).getWorldName());
            } else {
                for (File f : files) {
                    new Arena(f.getName().replace(".yml", ""));
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
    }

    public static void debug(String message) {
        if (debug) {
            plugin.getLogger().info("DEBUG: " + message);
        }
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
}
