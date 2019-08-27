package com.andrei1058.bedwars.maprestore.internal.files;

import java.io.File;
import java.io.IOException;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.maprestore.internal.InternalAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class WorldRestorer {

    private final String worldName;
    private Arena arena;

    public WorldRestorer(String worldName, Arena arena) {
        this.worldName = worldName;
        this.arena = arena;
    }

    public void execute() {
        Main.debug("Reloading arenaworld " + worldName + " : Reloading world ...");
        if (arena != null) {
            reloadWorld();
            Main.debug("Reloading arenaworld " + worldName + " : Done !");
        }
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
        File backupFolder = InternalAdapter.backupFolder;
        return new File(backupFolder, worldName + ".zip");
    }
}