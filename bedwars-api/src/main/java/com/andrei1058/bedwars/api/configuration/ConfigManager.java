package com.andrei1058.bedwars.api.configuration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class ConfigManager {

    private YamlConfiguration yml;
    private File config;
    private String name;
    private boolean firstTime = false;

    /**
     * Create a new configuration file.
     *
     * @param plugin config owner.
     * @param name   config name. Do not include .yml in it.
     */
    public ConfigManager(Plugin plugin, String name, String dir) {
        File d = new File(dir);
        if (!d.exists()) {
            if (!d.mkdir()) {
                plugin.getLogger().log(Level.SEVERE, "Could not create " + d.getPath());
                return;
            }
        }

        config = new File(dir, name + ".yml");
        if (!config.exists()) {
            firstTime = true;
            plugin.getLogger().info("Creating " + config.getPath());
            try {
                if (!config.createNewFile()) {
                    plugin.getLogger().log(Level.SEVERE, "Could not create " + config.getPath());
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        yml = YamlConfiguration.loadConfiguration(config);

        // todo move this to a new arena config
        if (true) {
            yml.options().header(plugin.getName() + " arena configuration file.\n" +
                    "Documentation here: https://gitlab.com/andrei1058/BedWars1058/wikis/configuration/Arena-Configuration");
            yml.addDefault("group", "Default");
            yml.addDefault(ConfigPath.ARENA_DISPLAY_NAME, "");
            yml.addDefault("minPlayers", 2);
            yml.addDefault("maxInTeam", 1);
            yml.addDefault("allowSpectate", true);
            yml.addDefault(ConfigPath.ARENA_SPAWN_PROTECTION, 5);
            yml.addDefault(ConfigPath.ARENA_SHOP_PROTECTION, 1);
            yml.addDefault(ConfigPath.ARENA_UPGRADES_PROTECTION, 1);
            yml.addDefault(ConfigPath.ARENA_ISLAND_RADIUS, 17);
            yml.addDefault("worldBorder", 300);
            yml.addDefault("voidKill", false);
            //yml.addDefault("disableGeneratorsOnOrphanIslands", false);
            yml.addDefault(ConfigPath.ARENA_CONFIGURATION_MAX_BUILD_Y, 180);
            yml.addDefault(ConfigPath.ARENA_DISABLE_GENERATOR_FOR_EMPTY_TEAMS, false);
            yml.addDefault(ConfigPath.ARENA_DISABLE_NPCS_FOR_EMPTY_TEAMS, true);
            yml.addDefault(ConfigPath.ARENA_NORMAL_DEATH_DROPS, false);
            yml.addDefault(ConfigPath.ARENA_USE_BED_HOLO, true);
            yml.addDefault(ConfigPath.ARENA_ALLOW_MAP_BREAK, false);
            yml.options().copyDefaults(true);
            save();

            //convert old configuration
            if (yml.get("spawnProtection") != null) {
                set(ConfigPath.ARENA_SPAWN_PROTECTION, yml.getInt("spawnProtection"));
                set("spawnProtection", null);
            }
            if (yml.get("shopProtection") != null) {
                set(ConfigPath.ARENA_SHOP_PROTECTION, yml.getInt("shopProtection"));
                set("shopProtection", null);
            }
            if (yml.get("upgradesProtection") != null) {
                set(ConfigPath.ARENA_UPGRADES_PROTECTION, yml.getInt("upgradesProtection"));
                set("upgradesProtection", null);
            }
            if (yml.get("islandRadius") != null) {
                set(ConfigPath.ARENA_ISLAND_RADIUS, yml.getInt("islandRadius"));
            }
        }
        this.name = name;
    }

    /**
     * Reload configuration.
     */
    public void reload() {
        yml = YamlConfiguration.loadConfiguration(config);
    }

    /**
     * Convert a location to an arena location syntax
     */
    public String stringLocationArenaFormat(Location loc) {
        return loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + (double) loc.getYaw() + "," + (double) loc.getPitch();
    }

    /**
     * Convert a location to a string for general use
     * Use {@link #stringLocationArenaFormat(Location)} for arena locations
     */
    public String stringLocationConfigFormat(Location loc) {
        return loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + (double) loc.getYaw() + "," + (double) loc.getPitch() + "," + loc.getWorld().getName();
    }

    /**
     * Save a general location to the config.
     * Use {@link #saveArenaLoc(String, Location)} for arena locations
     */
    public void saveConfigLoc(String path, Location loc) {
        String data = loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + (double) loc.getYaw() + "," + (double) loc.getPitch() + "," + loc.getWorld().getName();
        yml.set(path, data);
        save();
    }

    /**
     * Save a location for arena use
     */
    public void saveArenaLoc(String path, Location loc) {
        String data = loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + (double) loc.getYaw() + "," + (double) loc.getPitch();
        yml.set(path, data);
        save();
    }

    /**
     * Get a general location
     * Use {@link #getArenaLoc(String)} for locations stored using {@link #saveArenaLoc(String, Location)}
     */
    public Location getConfigLoc(String path) {
        String d = yml.getString(path);
        if (d == null) return null;
        String[] data = d.replace("[", "").replace("]", "").split(",");
        return new Location(Bukkit.getWorld(data[5]), Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Float.parseFloat(data[3]), Float.parseFloat(data[4]));
    }

    /**
     * Get a location for arena use
     * Use {@link #getConfigLoc(String)} (String)} for locations stored using {@link #saveConfigLoc(String, Location)} (String, Location)}
     */
    public Location getArenaLoc(String path) {
        String d = yml.getString(path);
        if (d == null) return null;
        String[] data = d.replace("[", "").replace("]", "").split(",");
        return new Location(Bukkit.getWorld(name), Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Float.parseFloat(data[3]), Float.parseFloat(data[4]));
    }

    /**
     * Convert string to arena location syntax
     */
    public Location convertStringToArenaLocation(String string) {
        String[] data = string.split(",");
        return new Location(Bukkit.getWorld(name), Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Float.parseFloat(data[3]), Float.parseFloat(data[4]));

    }

    /**
     * Get list of arena locations at given path
     */
    public List<Location> getArenaLocations(String path) {
        List<Location> l = new ArrayList<>();
        for (String s : yml.getStringList(path)) {
            Location loc = convertStringToArenaLocation(s);
            if (loc != null) {
                l.add(loc);
            }
        }
        return l;
    }

    /**
     * Set data to config
     */
    public void set(String path, Object value) {
        yml.set(path, value);
        save();
    }

    /**
     * Get yml instance
     */
    public YamlConfiguration getYml() {
        return yml;
    }

    /**
     * Save config changes to file
     */
    public void save() {
        try {
            yml.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //todo move to general config class
    public String getLobbyWorldName() {
        if (yml.get("lobbyLoc") == null) return "";
        String d = yml.getString("lobbyLoc");
        String[] data = d.replace("[", "").replace("]", "").split(",");
        return data[data.length - 1];
    }

    /**
     * Get list of strings at given path
     *
     * @return a list of string with colors translated
     */
    public List<String> getList(String path) {
        return yml.getStringList(path).stream().map(s -> s.replace("&", "§")).collect(Collectors.toList());
    }

    /**
     * Get boolean at given path
     */
    public boolean getBoolean(String path) {
        return yml.getBoolean(path);
    }

    /**
     * Get Integer at given path
     */
    public int getInt(String path) {
        return yml.getInt(path);
    }


    /**
     * Get string at given path
     */
    public String getString(String path) {
        return yml.getString(path);
    }

    /**
     * Check if the config file was created for the first time
     * Can be used to add default values
     */
    public boolean isFirstTime() {
        return firstTime;
    }

    /**
     * Compare two arena locations
     * Return true if same location
     */
    public boolean compareArenaLoc(Location l1, Location l2) {
        return l1.getBlockX() == l2.getBlockX() && l1.getBlockZ() == l2.getBlockZ() && l1.getBlockY() == l2.getBlockY();
    }

    /**
     * Add Multi Arena Lobby Command Item To Config.
     * This won't create the item back if you delete it.
     */
    //todo move to the general config class
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
     * Add Pre Game Command Item To Config.
     * This won't create the item back if you delete it.
     */
    //todo move to the general config class
    public void savePreGameCommandItem(String name, String cmd, boolean enchanted, String material, int data, int slot) {
        if (isFirstTime()) {
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_COMMAND.replace("%path%", name), cmd);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_MATERIAL.replace("%path%", name), material);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_DATA.replace("%path%", name), data);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_ENCHANTED.replace("%path%", name), enchanted);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_SLOT.replace("%path%", name), slot);
            yml.options().copyDefaults(true);
            save();
        }
    }

    /**
     * Add Spectator Command Item To Config.
     * This won't create the item back if you delete it.
     */
    //todo move to the general config class
    public void saveSpectatorCommandItem(String name, String cmd, boolean enchanted, String material, int data, int slot) {
        if (isFirstTime()) {
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_COMMAND.replace("%path%", name), cmd);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_MATERIAL.replace("%path%", name), material);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_DATA.replace("%path%", name), data);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_ENCHANTED.replace("%path%", name), enchanted);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_SLOT.replace("%path%", name), slot);
            yml.options().copyDefaults(true);
            save();
        }
    }

    /**
     * Get config name
     */
    public String getName() {
        return name;
    }
}
