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

package com.andrei1058.bedwars.configuration;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.stats.DefaultStatistics;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.arena.Misc;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.andrei1058.bedwars.BedWars.getForCurrentVersion;

public class MainConfig extends ConfigManager {

    public MainConfig(Plugin plugin, String name) {
        super(plugin, name, BedWars.plugin.getDataFolder().getPath());

        YamlConfiguration yml = getYml();

        yml.options().header(plugin.getDescription().getName() + " by andrei1058. https://www.spigotmc.org/members/39904/\n" +
                "Documentation here: https://gitlab.com/andrei1058/BedWars1058/wikis/home\n");
        yml.addDefault("serverType", "MULTIARENA");
        yml.addDefault("language", "en");
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DISABLED_LANGUAGES, Collections.singletonList("your language iso here"));
        yml.addDefault("storeLink", "https://www.spigotmc.org/resources/authors/39904/");
        yml.addDefault("lobbyServer", "hub");
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ENABLE_HALLOWEEN, true);
        yml.addDefault(ConfigPath.GENERAL_CHAT_GLOBAL, yml.get("globalChat", false));
        yml.addDefault(ConfigPath.GENERAL_CHAT_FORMATTING, yml.get("formatChat", true));
        yml.addDefault("debug", false);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_MARK_LEAVE_AS_ABANDON, false);
        // parties category
        yml.addDefault(ConfigPath.GENERAL_ENABLE_PARTY_CMD, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ALLOW_PARTIES, true);
        yml.addDefault(ConfigPath.GENERAL_ALESSIODP_PARTIES_RANK, 10);
        //
        yml.addDefault(ConfigPath.SB_CONFIG_SIDEBAR_USE_LOBBY_SIDEBAR, true);
        yml.addDefault(ConfigPath.SB_CONFIG_SIDEBAR_USE_GAME_SIDEBAR, true);
        yml.addDefault(ConfigPath.SB_CONFIG_SIDEBAR_TITLE_REFRESH_INTERVAL, 4);
        yml.addDefault(ConfigPath.SB_CONFIG_SIDEBAR_PLACEHOLDERS_REFRESH_INTERVAL, 20);
        yml.addDefault(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY, false);
        yml.addDefault(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_WAITING, false);
        yml.addDefault(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_STARTING, false);
        yml.addDefault(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_PLAYING, true);
        yml.addDefault(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_RESTARTING, true);
        yml.addDefault(ConfigPath.SB_CONFIG_SIDEBAR_LIST_REFRESH, 1200);
        yml.addDefault(ConfigPath.SB_CONFIG_SIDEBAR_HEALTH_ENABLE, true);
        yml.addDefault(ConfigPath.SB_CONFIG_SIDEBAR_HEALTH_IN_TAB, true);
        yml.addDefault(ConfigPath.SB_CONFIG_SIDEBAR_HEALTH_REFRESH, 300);
        yml.addDefault(ConfigPath.SB_CONFIG_TAB_HEADER_FOOTER_ENABLE, true);
        yml.addDefault(ConfigPath.SB_CONFIG_TAB_HEADER_FOOTER_REFRESH_INTERVAL, 10);

        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_REJOIN_TIME, 60 * 5);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_RE_SPAWN_INVULNERABILITY, 4000);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_MODE_GAMES_BEFORE_RESTART, 30);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD, "restart");
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_AUTO_SCALE_LIMIT, 5);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_LOBBY_SERVERS, Collections.singletonList("0.0.0.0:2019"));
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_REGULAR, 40);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_HALF, 25);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_SHORTENED, 5);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_RESTART, 45);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_RE_SPAWN_COUNTDOWN, 5);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_BEDS_DESTROY_COUNTDOWN, 360);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_DRAGON_SPAWN_COUNTDOWN, 600);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_GAME_END_COUNTDOWN, 120);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_SHOUT_COOLDOWN, 30);
        yml.addDefault(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP, "yourServer.Com");
        yml.addDefault(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_POWERED_BY, "BedWars1058");
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_ID, "bw1");
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_BWP_TIME_OUT, 5000);

        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_HUNGER_WAITING, false);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_HUNGER_INGAME, false);

        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ALLOW_FIRE_EXTINGUISH, true);

        //heal pool category
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_HEAL_POOL_ENABLE, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_HEAL_POOL_SEEN_TEAM_ONLY, true);

        // tnt jump category
        yml.addDefault(ConfigPath.GENERAL_TNT_JUMP_BARYCENTER_IN_Y, 0.5);
        yml.addDefault(ConfigPath.GENERAL_TNT_JUMP_STRENGTH_REDUCTION, 5);
        yml.addDefault(ConfigPath.GENERAL_TNT_JUMP_Y_REDUCTION, 2);
        yml.addDefault(ConfigPath.GENERAL_TNT_JUMP_DAMAGE_SELF, 1);
        yml.addDefault(ConfigPath.GENERAL_TNT_JUMP_DAMAGE_TEAMMATES, 5);
        yml.addDefault(ConfigPath.GENERAL_TNT_JUMP_DAMAGE_OTHERS, 10);

        // tnd block blast resistance
        // on 1.8.8 it has to be around 69, on 1.20 and 1.18 it works fine with 12 (tested)
        yml.addDefault(ConfigPath.GENERAL_TNT_PROTECTION_END_STONE_BLAST, BedWars.nms.getVersion() == 0 ? 69f : 12f);
        yml.addDefault(ConfigPath.GENERAL_TNT_PROTECTION_GLASS_BLAST, 300f);
        yml.addDefault(ConfigPath.GENERAL_TNT_RAY_BLOCKED_BY_GLASS, true);

        // tnt prime settings
        yml.addDefault(ConfigPath.GENERAL_TNT_AUTO_IGNITE, true);
        yml.addDefault(ConfigPath.GENERAL_TNT_FUSE_TICKS, 45);

        // fireball category
        yml.addDefault(ConfigPath.GENERAL_FIREBALL_EXPLOSION_SIZE, 3);
        yml.addDefault(ConfigPath.GENERAL_FIREBALL_SPEED_MULTIPLIER, 10);
        yml.addDefault(ConfigPath.GENERAL_FIREBALL_MAKE_FIRE, false);
        yml.addDefault(ConfigPath.GENERAL_FIREBALL_KNOCKBACK_HORIZONTAL, 1.0);
        yml.addDefault(ConfigPath.GENERAL_FIREBALL_KNOCKBACK_VERTICAL, 0.65);
        yml.addDefault(ConfigPath.GENERAL_FIREBALL_COOLDOWN, 0.5);
        yml.addDefault(ConfigPath.GENERAL_FIREBALL_DAMAGE_SELF, 2.0);
        yml.addDefault(ConfigPath.GENERAL_FIREBALL_DAMAGE_ENEMY, 2.0);
        yml.addDefault(ConfigPath.GENERAL_FIREBALL_DAMAGE_TEAMMATES, 0.0);
        //
        yml.addDefault("database.enable", false);
        yml.addDefault("database.host", "localhost");
        yml.addDefault("database.port", 3306);
        yml.addDefault("database.database", "bedwars1058");
        yml.addDefault("database.user", "root");
        yml.addDefault("database.pass", "cheese");
        yml.addDefault("database.ssl", false);

        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PERFORMANCE_ROTATE_GEN, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PERFORMANCE_SPOIL_TNT_PLAYERS, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PERFORMANCE_PAPER_FEATURES, true);

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
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_EXPERIMENTAL_TEAM_ASSIGNER, true);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_ENABLE_GEN_SPLIT, true);

        yml.addDefault(ConfigPath.LOBBY_VOID_TELEPORT_ENABLED, true);
        yml.addDefault(ConfigPath.LOBBY_VOID_TELEPORT_HEIGHT, 0);
        yml.addDefault(ConfigPath.GENERAL_GAME_END_SHOW_ELIMINATED, true);
        yml.addDefault(ConfigPath.GENERAL_GAME_END_TELEPORT_ELIMINATED, true);
        yml.addDefault(ConfigPath.GENERAL_GAME_END_CHAT_TOP_STATISTIC, DefaultStatistics.KILLS.toString());
        yml.addDefault(ConfigPath.GENERAL_GAME_END_CHAT_TOP_HIDE_MISSING, true);

        yml.addDefault(ConfigPath.GENERAL_GAME_END_SB_TOP_STATISTIC, DefaultStatistics.KILLS.toString());
        yml.addDefault(ConfigPath.GENERAL_GAME_END_SB_TOP_HIDE_MISSING, true);
        yml.options().copyDefaults(true);
        save();

        //remove old config
        //Convert old configuration

        yml.set("formatChat", null);
        yml.set("globalChat", null);

        if (yml.get("bungee-settings.lobby-servers") != null) {
            List<String> sockets = new ArrayList<>(yml.getStringList("bungee-settings.lobby-servers"));
            yml.set(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_LOBBY_SERVERS, sockets);
            yml.set("bungee-settings.lobby-servers", null);
        }

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

        if (getYml().get("fireball.damage-multiplier") != null) {
            set("fireball.damage-multiplier", null);
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

        if (yml.get("server-name") != null) {
            set(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_ID, yml.get("server-name"));
        }
        if (yml.get("lobby-scoreboard") != null) {
            set(ConfigPath.SB_CONFIG_SIDEBAR_USE_LOBBY_SIDEBAR, yml.getBoolean("lobby-scoreboard"));
            set("lobby-scoreboard", null);
        }
        if (yml.get("game-scoreboard") != null) {
            set(ConfigPath.SB_CONFIG_SIDEBAR_USE_GAME_SIDEBAR, yml.getBoolean("game-scoreboard"));
            set("game-scoreboard", null);
        }
        if (yml.get("enable-party-cmd") != null) {
            set(ConfigPath.GENERAL_ENABLE_PARTY_CMD, yml.getBoolean("enable-party-cmd"));
            set("enable-party-cmd", null);
        }
        if (yml.get("allow-parties") != null) {
            set(ConfigPath.GENERAL_CONFIGURATION_ALLOW_PARTIES, yml.getBoolean("allow-parties"));
            set("allow-parties", null);
        }
        set("server-name", null);
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
        set("performance-settings.disable-armor-packets", null);
        set("performance-settings.disable-respawn-packets", null);

        //Finished old configuration conversion

        //set default server language
        String whatLang = "en";
        File[] langs = new File(plugin.getDataFolder(), "/Languages").listFiles();
        if (langs != null) {
            for (File f : langs) {
                if (f.isFile()) {
                    if (f.getName().contains("messages_") && f.getName().contains(".yml")) {
                        String lang = f.getName().replace("messages_", "").replace(".yml", "");
                        if (lang.equalsIgnoreCase(yml.getString("language"))) {
                            whatLang = f.getName().replace("messages_", "").replace(".yml", "");
                        }
                        if (Language.getLang(lang) == null) new Language(BedWars.plugin, lang);
                    }
                }
            }
        }
        Language def = Language.getLang(whatLang);

        if (def == null) throw new IllegalStateException("Could not found default language: " + whatLang);
        Language.setDefaultLanguage(def);

        //remove languages if disabled
        //server language can t be disabled
        for (String iso : yml.getStringList(ConfigPath.GENERAL_CONFIGURATION_DISABLED_LANGUAGES)) {
            Language l = Language.getLang(iso);
            if (l != null) {
                if (l != def) Language.getLanguages().remove(l);
            }
        }
        //

        BedWars.setDebug(yml.getBoolean("debug"));
        new ConfigManager(plugin, "bukkit", Bukkit.getWorldContainer().getPath()).set("ticks-per.autosave", -1);

        Bukkit.spigot().getConfig().set("commands.send-namespaced", false);
        try {
            Bukkit.spigot().getConfig().save("spigot.yml");
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }

        try {
            BedWars.setServerType(ServerType.valueOf(Objects.requireNonNull(yml.getString("serverType")).toUpperCase()));
        } catch (Exception e) {
            if (Objects.requireNonNull(yml.getString("serverType")).equalsIgnoreCase("BUNGEE_LEGACY")) {
                BedWars.setServerType(ServerType.BUNGEE);
                BedWars.setAutoscale(false);
            } else {
                set("serverType", "MULTIARENA");
            }
        }

        BedWars.setLobbyWorld(getLobbyWorldName());
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
    @SuppressWarnings("WeakerAccess")
    public void saveLobbyCommandItem(String name, String cmd, boolean enchanted, String material, int data, int slot) {
        if (isFirstTime()) {
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_COMMAND.replace("%path%", name), cmd);
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_MATERIAL.replace("%path%", name), material);
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_DATA.replace("%path%", name), data);
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_ENCHANTED.replace("%path%", name), enchanted);
            getYml().addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_SLOT.replace("%path%", name), slot);
            getYml().options().copyDefaults(true);
            save();
        }
    }


    /**
     * Add Pre Game Command Item To
     * This won't create the item back if you delete it.
     */
    @SuppressWarnings("WeakerAccess")
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
    @SuppressWarnings("WeakerAccess")
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
