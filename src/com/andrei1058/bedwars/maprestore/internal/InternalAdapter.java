package com.andrei1058.bedwars.maprestore.internal;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.api.arena.RestoreAdapter;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.maprestore.internal.files.FileUtil;
import com.andrei1058.bedwars.maprestore.internal.files.WorldZipper;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.maprestore.internal.files.ZipFileUtil;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static com.andrei1058.bedwars.Main.config;
import static com.andrei1058.bedwars.Main.plugin;

public class InternalAdapter extends RestoreAdapter {

    public static File backupFolder = new File(Main.plugin.getDataFolder() + "/Cache");

    public InternalAdapter(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void onEnable(Arena a) {
        if (a == null) return;
        World world = Bukkit.getWorld(a.getWorldName());
        if (world != null) {
            if (Main.getServerType() == ServerType.BUNGEE) {
                for (Player p : world.getPlayers()) {
                    p.kickPlayer("The arena you were in was restore. You were kicked out of it. You were not supposed to be there.");
                }
            } else {
                for (Player p : world.getPlayers()) {
                    p.teleport(Bukkit.getWorld(Main.getLobbyWorld()).getSpawnLocation());
                    p.sendMessage(ChatColor.BLUE + "The arena you were in was restored. You were kicked out of it. You were not supposed to be there.");
                }
            }
            Bukkit.unloadWorld(a.getWorldName(), false);
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            File bf = new File(backupFolder, a.getWorldName() + ".zip");
            if (bf.exists()) {
                FileUtil.delete(bf);
            }

            if (!bf.exists()) {
                new WorldZipper(a.getWorldName(), true);
            } else {
                try {
                    ZipFileUtil.unzipFileIntoDirectory(bf, new File(Bukkit.getWorldContainer(), a.getWorldName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (Bukkit.getWorlds().get(0).getName().equals(a.getWorldName())) {
                if (Main.getServerType() != ServerType.BUNGEE) {
                    Main.plugin.getLogger().log(Level.SEVERE, "You can't use an arena world in server.properties as level-name when running the server in MULTIARENA mode!");
                    Main.plugin.getLogger().log(Level.SEVERE, a.getWorldName() + " won't load.");
                    Arena.removeFromEnableQueue(a);
                    return;
                }
                try {
                    Main.plugin.getLogger().severe("For a better performance please do not use arena worlds as level-name in server.properties");
                    Main.plugin.getLogger().severe("Use a void map instead.");
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        World w = Bukkit.getWorlds().get(0);
                        w.setKeepSpawnInMemory(true);
                        w.setAutoSave(false);
                        Arena.setGamesBeforeRestart(1);
                        a.init(w);
                    });
                } catch (IllegalArgumentException e) {
                    if (e.getMessage().contains("ChunkNibbleArrays should be 2048 bytes")) {
                        Main.plugin.getLogger().log(Level.SEVERE, "Could not load arena: " + a.getWorldName());
                        Main.plugin.getLogger().log(Level.SEVERE, "Your world has corrupt chunks!");
                    }
                }
            } else {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    World w = Bukkit.createWorld(new WorldCreator(a.getWorldName()));
                    w.setKeepSpawnInMemory(false);
                    w.setAutoSave(false);
                    a.init(w);
                });
            }
        });
    }

    @Override
    public void onRestart(Arena a) {
        if (Main.getServerType() == ServerType.BUNGEE) {
            Arena.setGamesBeforeRestart(Arena.getGamesBeforeRestart() - 1);
            if (Arena.getGamesBeforeRestart() == 0) {
                Bukkit.getLogger().info("Dispatching command: " + config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD));
            } else {
                if (Arena.getGamesBeforeRestart() != -1) {
                    Arena.setGamesBeforeRestart(Arena.getGamesBeforeRestart() - 1);
                }
                Bukkit.unloadWorld(a.getWorldName(), false);
                Bukkit.getScheduler().runTaskLater(plugin, () -> new Arena(a.getWorldName(), null), 80L);
            }
        } else {
            Bukkit.unloadWorld(a.getWorldName(), false);
            Bukkit.getScheduler().runTaskLater(plugin, () -> new Arena(a.getWorldName(), null), 80L);
        }
    }

    @Override
    public void onDisable(Arena a) {
        Bukkit.unloadWorld(a.getWorldName(), false);
    }

    @Override
    public void onSetupSessionStart(SetupSession s) {
        try {
            Bukkit.createWorld(new WorldCreator(s.getWorldName()));
        } catch (Exception ex) {
            File uid = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + s.getWorldName() + "/uid.dat");
            if (uid.exists() && uid.delete()) {
                try {
                    Bukkit.createWorld(new WorldCreator(s.getWorldName()));
                } catch (Exception exx) {
                    exx.printStackTrace();
                    return;
                }
            } else {
                Bukkit.getLogger().log(Level.WARNING, "Could not delete uid.dat from " + s.getWorldName());
                Bukkit.getLogger().log(Level.WARNING, "Please delete it manually and try again.");
                return;
            }
        }
        s.setupStarted();
    }

    @Override
    public void onSetupSessionClose(SetupSession s) {
        Bukkit.unloadWorld(s.getWorldName(), false);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> new WorldZipper(s.getWorldName(), true));
    }

    @Override
    public void onLobbyRemoval(Arena a) {
        Location loc1 = a.getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS1),
                loc2 = a.getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS2);
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

    @Override
    public boolean isWorld(String name) {
        return new File(Bukkit.getWorldContainer(), name + "/level.dat").exists();
    }

    @Override
    public void deleteWorld(String name) {
        try {
            FileUtils.deleteDirectory(new File(Bukkit.getWorldContainer(), name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cloneArena(String name1, String name2) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                FileUtils.copyDirectory(new File(Bukkit.getWorldContainer(), name1), new File(Bukkit.getWorldContainer(), name2));
                if (!new File(new File(Bukkit.getWorldContainer(), name2).getPath() + "/uid.dat").delete()) {
                    Bukkit.getLogger().log(Level.WARNING, "Could not delete uid.dat from " + name2 + ". Please do it manually.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public List<String> getWorldsList() {
        List<String> worlds = new ArrayList<>();
        File dir = Bukkit.getWorldContainer();
        if (dir.exists()) {
            File[] fls = dir.listFiles();
            for (File fl : Objects.requireNonNull(fls)) {
                if (fl.isDirectory()) {
                    File dat = new File(fl.getName() + "/level.dat");
                    if (dat.exists()) {
                        worlds.add(fl.getName());
                    }
                }
            }
        }
        return worlds;
    }

    @Override
    public void convertWorlds() {
        File dir = new File("plugins/" + plugin.getName() + "/Arenas");
        if (dir.exists()) {
            List<File> files = new ArrayList<>();
            File[] fls = dir.listFiles();
            for (File fl : Objects.requireNonNull(fls)) {
                if (fl.isFile()) {
                    if (fl.getName().contains(".yml")) {
                        files.add(fl);
                    }
                }
            }

            // lowerCase arena names - new 1.14 standard
            File folder, newName;

            List<File> toRemove = new ArrayList<>(), toAdd = new ArrayList<>();
            for (File file : files) {
                if (!file.getName().equals(file.getName().toLowerCase())) {
                    //level-name will not be renamed
                    if (Main.nms.getLevelName().equals(file.getName().replace(".yml", ""))) continue;
                    newName = new File(dir.getPath() + "/" + file.getName().toLowerCase());
                    if (!file.renameTo(newName)) {
                        toRemove.add(file);
                        Main.plugin.getLogger().severe("Could not rename " + file.getName() + " to " + file.getName().toLowerCase() + "! Please do it manually!");
                    } else {
                        toAdd.add(newName);
                        toRemove.add(file);
                    }
                    folder = new File(plugin.getServer().getWorldContainer(), file.getName().replace(".yml", ""));
                    if (folder.exists()) {
                        if (!folder.getName().equals(folder.getName().toLowerCase())) {
                            if (!folder.renameTo(new File(plugin.getServer().getWorldContainer().getPath() + "/" + folder.getName().toLowerCase()))) {
                                Main.plugin.getLogger().severe("Could not rename " + folder.getName() + " folder to " + folder.getName().toLowerCase() + "! Please do it manually!");
                                toRemove.add(file);
                                return;
                            }
                        }
                    }
                }
            }

            for (File f : toRemove) {
                files.remove(f);
            }

            files.addAll(toAdd);
        }
    }
}