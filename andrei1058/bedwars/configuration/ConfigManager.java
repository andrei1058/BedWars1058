package com.andrei1058.bedwars.configuration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.andrei1058.bedwars.Main.plugin;

public class ConfigManager {

    private YamlConfiguration yml;
    private File config;
    private String name;
    private boolean firstTime = false;

    public ConfigManager(String name, String dir, boolean arena) {
        File d = new File(dir);
        if (!d.exists()) {
            d.mkdir();
            firstTime = true;
        }
        config = new File(dir + "/" + name + ".yml");
        if (!config.exists()) {
            firstTime = true;
            try {
                config.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            plugin.getLogger().info("Creating " + dir + "/" + name + ".yml");
        }
        yml = YamlConfiguration.loadConfiguration(config);
        if (arena) {
            yml.options().header(plugin.getName() + " arena configuration file.\n" +
                    "Documentation here: https://gitlab.com/andrei1058/BedWars1058/wikis/configuration/Arena-Configuration");
            yml.addDefault("group", "Default");
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
            yml.addDefault(ConfigPath.ARENA_NORMAL_DEATH_DROPS, false);
            yml.addDefault(ConfigPath.ARENA_USE_BED_HOLO, true);
            yml.options().copyDefaults(true);
            save();

            //convert old configuration
            if (yml.get("spawnProtection") != null){
                set(ConfigPath.ARENA_SPAWN_PROTECTION, yml.getInt("spawnProtection"));
                set("spawnProtection", null);
            }
            if (yml.get("shopProtection") != null){
                set(ConfigPath.ARENA_SHOP_PROTECTION, yml.getInt("shopProtection"));
                set("shopProtection", null);
            }
            if (yml.get("upgradesProtection") != null){
                set(ConfigPath.ARENA_UPGRADES_PROTECTION, yml.getInt("upgradesProtection"));
                set("upgradesProtection", null);
            }
            if (yml.get("islandRadius") != null){
                set(ConfigPath.ARENA_ISLAND_RADIUS, yml.getInt("islandRadius"));
            }
        }
        this.name = name;
    }

    public void reload() {
        yml = YamlConfiguration.loadConfiguration(config);
    }

    public String stringLocationArenaFormat(Location loc) {
        return Double.valueOf(loc.getX()) + "," + Double.valueOf(loc.getY()) + "," + Double.valueOf(loc.getZ()) + "," + Double.valueOf(loc.getYaw()) + "," + Double.valueOf(loc.getPitch());
    }

    public String stringLocationConfigFormat(Location loc) {
        return Double.valueOf(loc.getX()) + "," + Double.valueOf(loc.getY()) + "," + Double.valueOf(loc.getZ()) + "," + Double.valueOf(loc.getYaw()) + "," + Double.valueOf(loc.getPitch()) + "," + loc.getWorld().getName();
    }

    public void saveConfigLoc(String path, Location loc) {
        String data = Double.valueOf(loc.getX()) + "," + Double.valueOf(loc.getY()) + "," + Double.valueOf(loc.getZ()) + "," + Double.valueOf(loc.getYaw()) + "," + Double.valueOf(loc.getPitch()) + "," + loc.getWorld().getName();
        yml.set(path, data);
        save();
    }

    public void saveArenaLoc(String path, Location loc) {
        String data = Double.valueOf(loc.getX()) + "," + Double.valueOf(loc.getY()) + "," + Double.valueOf(loc.getZ()) + "," + Double.valueOf(loc.getYaw()) + "," + Double.valueOf(loc.getPitch());
        yml.set(path, data);
        save();
    }

    public String getConfigLoc(Location loc) {
        return loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch() + "," + loc.getWorld().getName();
    }

    public Location getConfigLoc(String path) {
        String d = yml.getString(path);
        String[] data = d.replace("[", "").replace("]", "").split(",");
        return new Location(Bukkit.getWorld(data[5]), Double.valueOf(data[0]), Double.valueOf(data[1]), Double.valueOf(data[2]), Float.valueOf(data[3]), Float.valueOf(data[4]));
    }

    public Location getArenaLoc(String path) {
        String d = yml.getString(path);
        String[] data = d.replace("[", "").replace("]", "").split(",");
        return new Location(Bukkit.getWorld(name), Double.valueOf(data[0]), Double.valueOf(data[1]), Double.valueOf(data[2]), Float.valueOf(data[3]), Float.valueOf(data[4]));
    }

    public Location fromArenaStringList(String string) {
        String[] data = string.split(",");
        return new Location(Bukkit.getWorld(name), Double.valueOf(data[0]), Double.valueOf(data[1]), Double.valueOf(data[2]), Float.valueOf(data[3]), Float.valueOf(data[4]));

    }

    public List<Location> getLocations(String path){
        List<Location> l = new ArrayList<>();
        for (String s : yml.getStringList(path)){
            Location loc = fromArenaStringList(s);
            if (loc != null){
                l.add(loc);
            }
        }
        return l;
    }

    public void set(String path, Object value) {
        yml.set(path, value);
        save();
    }

    public YamlConfiguration getYml() {
        return yml;
    }

    public void save() {
        try {
            yml.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLobbyWorldName() {
        if (yml.get("lobbyLoc") == null) return "";
        String d = yml.getString("lobbyLoc");
        String[] data = d.replace("[", "").replace("]", "").split(",");
        return data[data.length - 1];
    }

    public List<String> l(String path) {
        return yml.getStringList(path).stream().map(s -> s.replace("&", "§")).collect(Collectors.toList());
    }

    public boolean getBoolean(String path) {
        return yml.getBoolean(path);
    }

    public int getInt(String path) {
        return yml.getInt(path);
    }


    /**
     * @since 0.6.5beta
     */
    public String getString(String path) {
        return yml.getString(path);
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    /** Return true if same */
    public boolean compareArenaLoc(Location l1, Location l2){
        return l1.getBlockX() == l2.getBlockX() && l1.getBlockZ() == l2.getBlockZ() && l1.getBlockY() == l2.getBlockY();
    }

    /** Add Multi Arena Lobby Command Item To Config.
     * This won't create the item back if you delete it.
      */
    public void saveLobbyCommandItem(String name, String cmd, boolean enchanted, String material, int data, int slot){
        if (isFirstTime()){
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_COMMAND.replace("%path%", name), cmd);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_MATERIAL.replace("%path%", name), material);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_DATA.replace("%path%", name), data);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_ENCHANTED.replace("%path%", name), enchanted);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_SLOT.replace("%path%", name), slot);
            yml.options().copyDefaults(true);
            save();
        }
    }

    /** Add Pre Game Command Item To Config.
     * This won't create the item back if you delete it.
     */
    public void savePreGameCommandItem(String name, String cmd, boolean enchanted, String material, int data, int slot){
        if (isFirstTime()){
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_COMMAND.replace("%path%", name), cmd);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_MATERIAL.replace("%path%", name), material);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_DATA.replace("%path%", name), data);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_ENCHANTED.replace("%path%", name), enchanted);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_SLOT.replace("%path%", name), slot);
            yml.options().copyDefaults(true);
            save();
        }
    }

    /** Add Spectator Command Item To Config.
     * This won't create the item back if you delete it.
     */
    public void saveSpectatorCommandItem(String name, String cmd, boolean enchanted, String material, int data, int slot){
        if (isFirstTime()){
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_COMMAND.replace("%path%", name), cmd);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_MATERIAL.replace("%path%", name), material);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_DATA.replace("%path%", name), data);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_ENCHANTED.replace("%path%", name), enchanted);
            yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_SLOT.replace("%path%", name), slot);
            yml.options().copyDefaults(true);
            save();
        }
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }
}
