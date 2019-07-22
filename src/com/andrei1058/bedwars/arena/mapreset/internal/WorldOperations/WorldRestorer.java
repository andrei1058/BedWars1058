package com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations;

import java.io.File;
import java.io.IOException;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.mapreset.MapManager;
import com.andrei1058.bedwars.arena.mapreset.Util.FileUtil;
import com.andrei1058.bedwars.arena.mapreset.Util.ZipFileUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class WorldRestorer implements WorldOperator {

    private final String worldName;
    private Arena arena;

    public WorldRestorer(String worldName, Arena arena) {
        this.worldName = worldName;
        this.arena = arena;
    }

    public void execute() {
        Main.debug("Restoring arenaworld " + worldName + " : Kicking players out of the world ...");
        kickPlayers();
        Main.debug("Restoring arenaworld " + worldName + " : Unloading world ...");
        unloadWorld();
        Main.debug("Restoring arenaworld " + worldName + " : Cleaning world ...");
        cleanData();
        Main.debug("Restoring arenaworld " + worldName + " : Restoring data ...");
        restoreData();
        Main.debug("Reloading arenaworld " + worldName + " : Reloading world ...");
        if (arena != null) {
            reloadWorld();
            Main.debug("Reloading arenaworld " + worldName + " : Done !");
        }
    }

    private void kickPlayers() {
        if (arena == null) return;
        World world = Bukkit.getWorld(worldName);
        if (world == null) return;
        Location teleportLocation = Bukkit.getWorlds().get(0).getSpawnLocation();
        for (Player p : world.getPlayers()) {
            p.teleport(teleportLocation);
            p.sendMessage(ChatColor.BLUE + "The arena you were in was restored. You were kicked out of it.");
        }
    }

    private void unloadWorld() {
        World world = Bukkit.getWorld(worldName);
        if (world == null) return;
        Bukkit.unloadWorld(world, false);
    }

    public void cleanData() {
        if (getBackupFile().exists()) FileUtil.delete(getWorldFolder());
    }

    public void restoreData() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            if (!getBackupFile().exists()) {
                if (arena != null) arena.getMapManager().backupWorld(true);
                return;
            }
            try {
                ZipFileUtil.unzipFileIntoDirectory(getBackupFile(), getWorldFolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void reloadWorld() {
        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
            if (Bukkit.getWorlds().get(0).getName().equals(worldName)) {
                if (Main.getServerType() != ServerType.BUNGEE) {
                    Main.plugin.getLogger().severe("You can't use an arena as level-name in MULTIARENA mode!");
                    return;
                }
                try {
                    Main.plugin.getLogger().severe("For a better performance please do not use arena worlds as level-name in server.properties");
                    Main.plugin.getLogger().severe("Please use a void map instead.");
                    World w = Bukkit.getWorlds().get(0);
                    w.setKeepSpawnInMemory(true);
                    w.setAutoSave(false);
                    Arena.setGamesBeforeRestart(1);
                    arena.init(w);
                } catch (IllegalArgumentException e) {
                    if (e.getMessage().contains("ChunkNibbleArrays should be 2048 bytes")) {
                        Main.plugin.getLogger().severe("Could not load arena: " + worldName);
                        Main.plugin.getLogger().severe("Your world has corrupt chunks!");
                    }
                }
            } else {
                try {
                    World w = Bukkit.createWorld(new WorldCreator(worldName));
                    w.setKeepSpawnInMemory(false);
                    w.setAutoSave(false);
                    arena.init(w);
                } catch (IllegalArgumentException e) {
                    if (e.getMessage().contains("ChunkNibbleArrays should be 2048 bytes")) {
                        Main.plugin.getLogger().severe("Could not load arena: " + worldName);
                        Main.plugin.getLogger().severe("Your world has corrupt chunks!");
                    }
                }
            }
        }, 25L);
    }

    private File getWorldFolder() {
        File worldContainer = Bukkit.getWorldContainer();
        return new File(worldContainer, worldName);
    }

    private File getBackupFile() {
        File backupFolder = MapManager.backupFolder;
        return new File(backupFolder, worldName + ".zip");
    }
}