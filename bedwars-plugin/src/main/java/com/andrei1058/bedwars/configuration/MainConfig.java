package com.andrei1058.bedwars.configuration;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static com.andrei1058.bedwars.Main.getForCurrentVersion;
import static com.andrei1058.bedwars.Main.lang;

public class MainConfig extends ConfigManager {

    public MainConfig(Plugin plugin, String name, String dir) {
        super(plugin, name, dir);

        YamlConfiguration yml = getYml();

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
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_LOBBY_SERVERS, Collections.singletonList("0.0.0.0:2019"));
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_REGULAR, 40);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_HALF, 25);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_SHORTENED, 10);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_BEDS_DESTROY_COUNTDOWN, 360);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DRAGON_SPAWN_COUNTDOWN, 600);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_GAME_END_COUNTDOWN, 120);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_SHOUT_COOLDOWN, 30);
        yml.addDefault(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP, "yourServer.Com");
        yml.addDefault(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_NAME, "bw1");

        yml.addDefault("database.enable", false);
        yml.addDefault("database.host", "localhost");
        yml.addDefault("database.port", 3306);
        yml.addDefault("database.database", "bedwars1058");
        yml.addDefault("database.user", "root");
        yml.addDefault("database.pass", "cheese");
        yml.addDefault("database.ssl", false);

        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PERFORMANCE_ROTATE_GEN, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PERFORMANCE_DISABLE_ARMOR_PACKETS, false);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PERFORMANCE_DISABLE_RESPAWN_PACKETS, false);

        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DISABLE_CRAFTING, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DISABLE_ENCHANTING, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DISABLE_FURNACE, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DISABLE_BREWING_STAND, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DISABLE_ANVIL, true);

        /* Multi-Arena Lobby Command Items */
        saveLobbyCommandItem("stats", "bw stats", false, getForCurrentVersion("SKULL_ITEM", "SKULL_ITEM", "PLAYER_HEAD"), 3, 0);
        saveLobbyCommandItem("arena-selector", "bw gui", true, "CHEST", 5, 4);
        saveLobbyCommandItem("leave", "bw leave", false, getForCurrentVersion("BED", "BED", "RED_BED"), 0, 8);

        /* Pre Game Command Items */
        savePreGameCommandItem("stats", "bw stats", false, getForCurrentVersion("SKULL_ITEM", "SKULL_ITEM", "PLAYER_HEAD"), 3, 0);
        savePreGameCommandItem("leave", "bw leave", false, getForCurrentVersion("BED", "BED", "RED_BED"), 0, 8);

        /* Spectator Command Items */
        saveSpectatorCommandItem("teleporter", "bw teleporter", false, getForCurrentVersion("SKULL_ITEM", "SKULL_ITEM", "PLAYER_HEAD"), 3, 0);
        saveSpectatorCommandItem("leave", "bw leave", false, getForCurrentVersion("BED", "BED", "RED_BED"), 0, 8);

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
        if (isFirstTime()) {
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
        save();

        //remove old config
        //Convert old configuration

        if (yml.get("arenaGui.settings.showPlaying") != null) {
            set(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_SHOW_PLAYING, yml.getBoolean("arenaGui.settings.showPlaying"));
        }
        if (yml.get("arenaGui.settings.size") != null) {
            set(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_SIZE, yml.getInt("arenaGui.settings.size"));
        }
        if (yml.get("arenaGui.settings.useSlots") != null) {
            set(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_USE_SLOTS, yml.getString("arenaGui.settings.useSlots"));
        }
        if (getYml().get("arenaGui") != null) {
            for (String path : getYml().getConfigurationSection("arenaGui").getKeys(false)) {
                if (path.equalsIgnoreCase("settings")) continue;
                String new_path = path;
                if ("skippedSlot".equals(path)) {
                    new_path = "skipped-slot";
                }
                if (getYml().get("arenaGui." + path + ".itemStack") != null) {
                    set(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_MATERIAL.replace("%path%", new_path), getYml().getString("arenaGui." + path + ".itemStack"));
                }
                if (getYml().get("arenaGui." + path + ".data") != null) {
                    set(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_DATA.replace("%path%", new_path), getYml().getInt("arenaGui." + path + ".data"));
                }
                if (getYml().get("arenaGui." + path + ".enchanted") != null) {
                    set(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_ENCHANTED.replace("%path%", new_path), getYml().getBoolean("arenaGui." + path + ".enchanted"));
                }
            }
        }
        
        set("arenaGui", null);

        if (getYml().get("npcLoc") != null) {
            set(ConfigPath.GENERAL_CONFIGURATION_NPC_LOC_STORAGE, getYml().getString("npcLoc"));
        }
        if (getYml().get("statsGUI.invSize") != null) {
            set(ConfigPath.GENERAL_CONFIGURATION_STATS_GUI_SIZE, getInt("statsGUI.invSize"));
        }
        if (getYml().get("disableCrafting") != null) {
            set(ConfigPath.GENERAL_CONFIGURATION_DISABLE_CRAFTING, getString("disableCrafting"));
        }
        if (getYml().get("statsGUI") != null) {
            for (String stats_path : getYml().getConfigurationSection("statsGUI").getKeys(false)) {
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
                if (getYml().get("statsGUI." + stats_path + ".itemStack") != null) {
                    set(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_MATERIAL.replace("%path%", new_path), getYml().getString("statsGUI." + stats_path + ".itemStack"));
                }
                if (getYml().get("statsGUI." + stats_path + ".data") != null) {
                    set(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_DATA.replace("%path%", new_path), getYml().getInt("statsGUI." + stats_path + ".data"));
                }
                if (getYml().get("statsGUI." + stats_path + ".slot") != null) {
                    set(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_SLOT.replace("%path%", new_path), getYml().getInt("statsGUI." + stats_path + ".slot"));
                }
            }
        }

        set("statsGUI", null);
        set("startItems", null);
        set("generators", null);
        set("bedsDestroyCountdown", null);
        set("dragonSpawnCountdown", null);
        set("gameEndCountdown", null);
        set("npcLoc", null);
        set("blockedCmds", null);
        set("lobbyScoreboard", null);
        set("arenaGui.settings.startSlot", null);
        set("arenaGui.settings.endSlot", null);
        set("items", null);
        set("start-items-per-arena", null);
        set("safeMode", null);
        set("disableCrafting", null);

        //Finished old configuration conversion

        //set default server language
        String whatLang = "en";
        for (File f : Objects.requireNonNull(new File("plugins/" + plugin.getDescription().getName() + "/Languages").listFiles())) {
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

        Main.setDebug(yml.getBoolean("debug"));
        new ConfigManager(plugin,"bukkit", Bukkit.getWorldContainer().getPath(), false).set("ticks-per.autosave", -1);

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
                set("serverType", "MULTIARENA");
                new ConfigManager("bukkit", Bukkit.getWorldContainer().getPath(), false).set("settings.allow-end", false);
                break;
        }
        lobbyWorld = getLobbyWorldName();
    }

    public String getLobbyWorldName() {
        if (getYml().get("lobbyLoc") == null) return "";
        String d = getYml().getString("lobbyLoc");
        String[] data = d.replace("[", "").replace("]", "").split(",");
        return data[data.length - 1];
    }

    /**
     * Add Multi Arena Lobby Command Item To 
     * This won't create the item back if you delete it.
     */
    public void saveLobbyCommandItem(String name, String cmd, boolean enchanted, String material, int data, int slot) {
        if (isFirstTime()) {
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_COMMAND.replace("%path%", name), cmd);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_MATERIAL.replace("%path%", name), material);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_DATA.replace("%path%", name), data);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_ENCHANTED.replace("%path%", name), enchanted);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_SLOT.replace("%path%", name), slot);
            yml.options().copyDefaults(true);
            save();
        }
    }


    /**
     * Add Pre Game Command Item To 
     * This won't create the item back if you delete it.
     */
    public void savePreGameCommandItem(String name, String cmd, boolean enchanted, String material, int data, int slot) {
        if (isFirstTime()) {
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_COMMAND.replace("%path%", name), cmd);
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_MATERIAL.replace("%path%", name), material);
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_DATA.replace("%path%", name), data);
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_ENCHANTED.replace("%path%", name), enchanted);
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_SLOT.replace("%path%", name), slot);
            getYml().options().copyDefaults(true);
            save();
        }
    }

    /**
     * Add Spectator Command Item To 
     * This won't create the item back if you delete it.
     */
    public void saveSpectatorCommandItem(String name, String cmd, boolean enchanted, String material, int data, int slot) {
        if (isFirstTime()) {
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_COMMAND.replace("%path%", name), cmd);
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_MATERIAL.replace("%path%", name), material);
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_DATA.replace("%path%", name), data);
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_ENCHANTED.replace("%path%", name), enchanted);
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_SLOT.replace("%path%", name), slot);
            getYml().options().copyDefaults(true);
            save();
        }
    }
}
