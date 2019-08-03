package com.andrei1058.bedwars.arena.mapreset;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations.WorldOperator;
import com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations.WorldRestorer;
import com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations.WorldZipper;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.*;
import org.bukkit.block.Block;

import java.io.File;

import static com.andrei1058.bedwars.Main.config;

public class MapManager {

    private Arena arena;
    private String name;

    public static File backupFolder = new File(Main.plugin.getDataFolder() + "/Cache");

    /**
     * Create a new map manager for an arena.
     */
    public MapManager(Arena arena, String name) {
        this.arena = arena;
        this.name = name;
        //isLevelWorld();
    }

    /**
     * Load world. Used for load the map at setup mode.
     */
    public void onSetupSession() {
        isLevelWorld();
        Bukkit.getScheduler().runTask(Main.plugin, () -> {
            World w = Bukkit.getServer().createWorld(new WorldCreator(name));

            w.setKeepSpawnInMemory(false);
            w.setAutoSave(false);
        });
    }

    /**
     * Unload world.
     */
    public void onRestart() {
        //if (isLevelWorld()) return;
        if (Main.getServerType() == ServerType.BUNGEE) {
            Arena.setGamesBeforeRestart(Arena.getGamesBeforeRestart() - 1);
            if (Arena.getGamesBeforeRestart() == 0) {
                Bukkit.getLogger().info("Dispatching command: " + config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD));
            } else {
                if (Arena.getGamesBeforeRestart() != -1) {
                    Arena.setGamesBeforeRestart(Arena.getGamesBeforeRestart() - 1);
                }
                Bukkit.getScheduler().runTask(Main.plugin, () -> {
                    Bukkit.unloadWorld(Bukkit.getWorld(name), false);
                    Bukkit.getScheduler().runTaskLater(Main.plugin, ()-> new Arena(name, null), 40L);
                });
            }
        } else {
            Bukkit.getScheduler().runTask(Main.plugin, () -> {
                Bukkit.unloadWorld(Bukkit.getWorld(name), false);
                Bukkit.getScheduler().runTaskLater(Main.plugin, ()-> new Arena(name, null), 40L);
            });
        }
    }

    /**
     * Restore arena world. Used to load/ enable the arena.
     */
    public void onEnable() {
        //if (isLevelWorld()) return;
        new WorldRestorer(name, arena).execute();
    }

    /**
     * Backup arena world.
     */
    public void backupWorld(boolean replace) {
        //if (isLevelWorld()) return;
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            WorldOperator worldOperator = new WorldZipper(name, replace);
            worldOperator.execute();
        });
    }

    public void onDisable(){
        Bukkit.unloadWorld(getArena().getWorld(), false);
    }

    /**
     * Remove lobby.
     */
    public void removeLobby() {
        Location loc1 = arena.getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS1),
                loc2 = arena.getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS2);
        if (loc1 == null || loc2 == null) return;
        Bukkit.getScheduler().runTask(Main.plugin, () -> {
            int minX, minY, minZ;
            int maxX, maxY, maxZ;
            minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
            maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
            minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
            maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
            minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
            maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

            for (int x = minX; x < maxX; x++) {
                for (int y = minY; y < maxY; y++) {
                    for (int z = minZ; z < maxZ; z++) {
                        loc1.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
                    }
                }
            }
        });
    }

    /**
     * Get world name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get arena.
     */
    public Arena getArena() {
        return arena;
    }

    public void isLevelWorld() {

        if (Bukkit.getWorlds().isEmpty()) return;
        if (Bukkit.getWorlds().get(0).getName().equalsIgnoreCase(name)) {
            Main.plugin.getLogger().severe("-------------------");
            if (Main.getServerType() == ServerType.BUNGEE) {
                Main.plugin.getLogger().severe("PLEASE CONSIDER ADDING A VOID MAP AS level-name IN server.properties");
                return;
            }
            Main.plugin.getLogger().severe("ARENA WORLDS MUSTN'T BE USED AS MAIN WORLD AT level-name IN server.properties");
        }
    }
}