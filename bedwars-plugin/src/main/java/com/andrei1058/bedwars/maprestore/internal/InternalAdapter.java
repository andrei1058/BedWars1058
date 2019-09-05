package com.andrei1058.bedwars.maprestore.internal;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.server.ISetupSession;
import com.andrei1058.bedwars.api.server.RestoreAdapter;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.api.util.FileUtil;
import com.andrei1058.bedwars.maprestore.internal.files.WorldZipper;
import com.andrei1058.bedwars.api.util.ZipFileUtil;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static com.andrei1058.bedwars.BedWars.config;
import static com.andrei1058.bedwars.BedWars.plugin;

public class InternalAdapter extends RestoreAdapter {

    public static File backupFolder = new File(BedWars.plugin.getDataFolder() + "/Cache");

    public InternalAdapter(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void onEnable(IArena a) {
        if (a == null) return;
        Bukkit.getScheduler().runTask(getOwner(), () -> {
            World world = Bukkit.getWorld(a.getWorldName());
            if (world != null) {
                if (BedWars.getServerType() == ServerType.BUNGEE) {
                    for (Player p : world.getPlayers()) {
                        p.kickPlayer("The arena you were in was restore. You were kicked out of it. You were not supposed to be there.");
                    }
                } else {
                    for (Player p : world.getPlayers()) {
                        p.teleport(Bukkit.getWorld(BedWars.getLobbyWorld()).getSpawnLocation());
                        p.sendMessage(ChatColor.BLUE + "The arena you were in was restored. You were kicked out of it. You were not supposed to be there.");
                    }
                }
                Bukkit.unloadWorld(a.getWorldName(), false);
            }
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                File bf = new File(backupFolder, a.getWorldName() + ".zip"), af = new File(Bukkit.getWorldContainer(), a.getWorldName());
                if (bf.exists()) {
                    FileUtil.delete(af);
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
                    if (BedWars.getServerType() != ServerType.BUNGEE) {
                        BedWars.plugin.getLogger().log(Level.SEVERE, "You can't use an arena world in server.properties as level-name when running the server in " + BedWars.getServerType().toString() + " mode!");
                        BedWars.plugin.getLogger().log(Level.SEVERE, a.getWorldName() + " will not be loaded.");
                        Arena.removeFromEnableQueue(a);
                        return;
                    }
                    try {
                        BedWars.plugin.getLogger().log(Level.WARNING, "For a better performance please do not use arena worlds as level-name in server.properties");
                        BedWars.plugin.getLogger().log(Level.WARNING, "Use a void map instead and never touch it. Minecraft requires a main level that can't be restored without restarting the server.");
                        BedWars.plugin.getLogger().log(Level.WARNING, "Your server will be restarted after each game.");
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            World w = Bukkit.getWorlds().get(0);
                            w.setKeepSpawnInMemory(true);
                            w.setAutoSave(false);
                            Arena.setGamesBeforeRestart(1);
                            a.init(w);
                        });
                    } catch (IllegalArgumentException e) {
                        if (e.getMessage().contains("ChunkNibbleArrays should be 2048 bytes")) {
                            BedWars.plugin.getLogger().log(Level.SEVERE, "Could not load arena: " + a.getWorldName());
                            BedWars.plugin.getLogger().log(Level.SEVERE, "Your world has corrupt chunks!");
                        }
                    }
                } else {
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        WorldCreator wc = new WorldCreator(a.getWorldName());
                        wc.type(WorldType.FLAT);
                        wc.generatorSettings("1;0;1");
                        World w = Bukkit.createWorld(wc);
                        w.setKeepSpawnInMemory(false);
                        w.setAutoSave(false);
                        a.init(w);
                    });
                }
            });
        });
    }

    @Override
    public void onRestart(IArena a) {
        Bukkit.getScheduler().runTask(getOwner(), () -> {
            if (BedWars.getServerType() == ServerType.BUNGEE) {
                Arena.setGamesBeforeRestart(Arena.getGamesBeforeRestart() - 1);
                if (Arena.getGamesBeforeRestart() == 0) {
                    plugin.getLogger().info("Dispatching command: " + config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD));
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
        });
    }

    @Override
    public void onDisable(IArena a) {
        Bukkit.getScheduler().runTask(getOwner(), () -> Bukkit.unloadWorld(a.getWorldName(), false));
    }

    @Override
    public void onSetupSessionStart(ISetupSession s) {
        Bukkit.getScheduler().runTask(getOwner(), () -> {
            WorldCreator wc = new WorldCreator(s.getWorldName());
            wc.type(WorldType.FLAT);
            wc.generatorSettings("1;0;1");
            try {
                File level = new File(Bukkit.getWorldContainer(), s.getWorldName() + "/level.dat");
                if (level.exists()) {
                    s.getPlayer().sendMessage(ChatColor.GREEN + "Loading " + s.getWorldName() + " from Bukkit worlds container.");
                } else {
                    s.getPlayer().sendMessage(ChatColor.GREEN + "Creating a new void map.");
                }
                Bukkit.createWorld(wc);
            } catch (Exception ex) {
                File uid = new File(Bukkit.getServer().getWorldContainer(), s.getWorldName() + "/uid.dat");
                if (uid.exists() && uid.delete()) {
                    try {
                        Bukkit.createWorld(wc);
                    } catch (Exception exx) {
                        exx.printStackTrace();
                        return;
                    }
                } else {
                    plugin.getLogger().log(Level.WARNING, "Could not delete uid.dat from " + s.getWorldName());
                    plugin.getLogger().log(Level.WARNING, "Please delete it manually and try again.");
                    return;
                }
            }
            s.teleportPlayer();
        });
    }

    @Override
    public void onSetupSessionClose(ISetupSession s) {
        Bukkit.getScheduler().runTask(getOwner(), ()-> {
            Bukkit.unloadWorld(s.getWorldName(), false);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> new WorldZipper(s.getWorldName(), true));
        });
    }

    @Override
    public void onLobbyRemoval(IArena a) {
        Location loc1 = a.getConfig().getArenaLoc(ConfigPath.ARENA_WAITING_POS1),
                loc2 = a.getConfig().getArenaLoc(ConfigPath.ARENA_WAITING_POS2);
        if (loc1 == null || loc2 == null) return;
        Bukkit.getScheduler().runTask(BedWars.plugin, () -> {
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
        Bukkit.getScheduler().runTaskAsynchronously(getOwner(), ()-> {
            try {
                FileUtils.deleteDirectory(new File(Bukkit.getWorldContainer(), name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void cloneArena(String name1, String name2) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                FileUtils.copyDirectory(new File(Bukkit.getWorldContainer(), name1), new File(Bukkit.getWorldContainer(), name2));
                if (!new File(new File(Bukkit.getWorldContainer(), name2).getPath() + "/uid.dat").delete()) {
                    plugin.getLogger().log(Level.WARNING, "Could not delete uid.dat from " + name2 + ". Please do it manually.");
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
                    if (BedWars.nms.getLevelName().equals(file.getName().replace(".yml", ""))) continue;
                    newName = new File(dir.getPath() + "/" + file.getName().toLowerCase());
                    if (!file.renameTo(newName)) {
                        toRemove.add(file);
                        BedWars.plugin.getLogger().severe("Could not rename " + file.getName() + " to " + file.getName().toLowerCase() + "! Please do it manually!");
                    } else {
                        toAdd.add(newName);
                        toRemove.add(file);
                    }
                    folder = new File(plugin.getServer().getWorldContainer(), file.getName().replace(".yml", ""));
                    if (folder.exists()) {
                        if (!folder.getName().equals(folder.getName().toLowerCase())) {
                            if (!folder.renameTo(new File(plugin.getServer().getWorldContainer().getPath() + "/" + folder.getName().toLowerCase()))) {
                                BedWars.plugin.getLogger().severe("Could not rename " + folder.getName() + " folder to " + folder.getName().toLowerCase() + "! Please do it manually!");
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