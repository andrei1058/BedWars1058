package com.andrei1058.bedwars.arena.mapreset.fawe;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.mapreset.MapManager;
import com.andrei1058.bedwars.arena.mapreset.Util.ZipFileUtil;
import com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations.WorldOperator;
import com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations.WorldZipper;
import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import com.boydti.fawe.util.TaskManager;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class FastAsyncWorldEdit extends MapManager {

    public FastAsyncWorldEdit(Arena arena, String name) {
        super(arena, name);
    }

    @Override
    public void loadWorld() {
        TaskManager.IMP.async(() -> {
            AsyncWorld world = AsyncWorld.create(new WorldCreator(getName()));
            //world.setKeepSpawnInMemory(false);
            //world.commit();
            if (getArena() != null) getArena().init(Bukkit.getWorld(getName()));
        });
    }

    @Override
    public void unloadWorld() {
        Bukkit.getScheduler().runTask(Main.plugin , ()-> {
            Bukkit.unloadWorld(Bukkit.getWorld(getName()), false);
        });
    }

    @Override
    public void restoreWorld(String name, Arena arena) {
        if (arena == null) return;
        World world = Bukkit.getWorld(name);
        if (world != null) {

            if (Bukkit.getWorlds().get(0).getName().equals(getName())) {
                if (Main.getServerType() != ServerType.BUNGEE) {
                    Main.plugin.getLogger().severe("You can't use an arena as level-name in MULTIARENA mode!");
                    return;
                }
            }


            if (!world.getPlayers().isEmpty()) {
                Main.debug("Restoring arena world " + name + " : Kicking players out of the world ...");
                Location teleportLocation = Bukkit.getWorlds().get(0).getSpawnLocation();
                for (Player p : world.getPlayers()) {
                    p.teleport(teleportLocation);
                    p.sendMessage(ChatColor.BLUE + "The arena you were in was restored. You were kicked out of it.");
                }
            }

                /*AsyncWorld aw = AsyncWorld.wrap(Bukkit.getWorld(getName()));
                for (Chunk c : aw.getLoadedChunks()) {
                    aw.unloadChunk(c);
                }*/
            Bukkit.unloadWorld(Bukkit.getWorld(getName()), false);
        }

        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
            TaskManager.IMP.async(() -> {
                if (!getBackupFile().exists()) {
                    arena.getMapManager().backupWorld(true);
                    return;
                }

                try {
                    ZipFileUtil.unzipFileIntoDirectory(getBackupFile(), getWorldFolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (Bukkit.getWorlds().get(0).getName().equals(getName())) {
                    Main.plugin.getLogger().severe("For a better performance please do not use arena worlds as level-name in server.properties");
                    Main.plugin.getLogger().severe("Please use a void map instead.");
                    try {
                        World w = Bukkit.getWorlds().get(0);
                        w.setKeepSpawnInMemory(true);
                        w.setAutoSave(false);
                        Arena.setGamesBeforeRestart(1);
                        arena.init(w);
                    } catch (IllegalArgumentException e) {
                        if (e.getMessage().contains("ChunkNibbleArrays should be 2048 bytes")) {
                            Main.plugin.getLogger().severe("Could not load arena: " + getName());
                            Main.plugin.getLogger().severe("Your world has corrupt chunks!");
                        }
                    }
                } else {
                    AsyncWorld w = AsyncWorld.create(new WorldCreator(name));
                    w.setKeepSpawnInMemory(false);
                    w.setAutoSave(false);
                    arena.init(w.getBukkitWorld());
                    //AsyncWorld aw = AsyncWorld.create(new WorldCreator(getName()));
                    //aw.setKeepSpawnInMemory(false);
                    //aw.setAutoSave(false);
                    //aw.commit();
                    //arena.init(aw.getBukkitWorld());
                }
            });
        }, 40L);
    }

    /**
     * Backup arena world.
     */
    public void backupWorld(boolean replace) {
        //if (isLevelWorld()) return;
        TaskManager.IMP.async(() -> {
            WorldOperator worldOperator = new WorldZipper(getName(), replace);
            worldOperator.execute();
        });
    }


    private File getWorldFolder() {
        File worldContainer = Bukkit.getWorldContainer();
        return new File(worldContainer, getName());
    }

    private File getBackupFile() {
        File backupFolder = MapManager.backupFolder;
        return new File(backupFolder, getName() + ".zip");
    }
}
