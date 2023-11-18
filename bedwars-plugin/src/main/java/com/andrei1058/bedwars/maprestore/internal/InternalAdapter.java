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

package com.andrei1058.bedwars.maprestore.internal;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.server.ISetupSession;
import com.andrei1058.bedwars.api.server.RestoreAdapter;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.util.FileUtil;
import com.andrei1058.bedwars.api.util.ZipFileUtil;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.VoidChunkGenerator;
import com.andrei1058.bedwars.maprestore.internal.files.WorldZipper;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.andrei1058.bedwars.BedWars.config;
import static com.andrei1058.bedwars.BedWars.plugin;

@SuppressWarnings("CallToPrintStackTrace")
public class InternalAdapter extends RestoreAdapter {

    public static File backupFolder = new File(BedWars.plugin.getDataFolder() + "/Cache");
    public InternalAdapter(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void onEnable(IArena a) {
        Bukkit.getScheduler().runTask(getOwner(), () -> {
            if (Bukkit.getWorld(a.getWorldName()) != null) {
                Bukkit.getScheduler().runTask(getOwner(), () -> {
                    World w = Bukkit.getWorld(a.getWorldName());
                    a.init(w);
                });
                return;
            }
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                File bf = new File(backupFolder, a.getArenaName() + ".zip"), af = new File(Bukkit.getWorldContainer(), a.getArenaName());
                if (bf.exists()) {
                    FileUtil.delete(af);
                }

                if (!bf.exists()) {
                    new WorldZipper(a.getArenaName(), true);
                } else {
                    try {
                        ZipFileUtil.unzipFileIntoDirectory(bf, new File(Bukkit.getWorldContainer(), a.getWorldName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                deleteWorldTrash(a.getWorldName());

                Bukkit.getScheduler().runTask(plugin, () -> {
                    WorldCreator wc = new WorldCreator(a.getWorldName());
                    wc.generateStructures(false);
                    wc.generator(new VoidChunkGenerator());
                    World w = Bukkit.createWorld(wc);
                    if (w == null){
                        throw new IllegalStateException("World should be null");
                    }
                    w.setKeepSpawnInMemory(true);
                    w.setAutoSave(false);
                });
            });
        });
    }

    @Override
    public void onRestart(IArena a) {
        Bukkit.getScheduler().runTask(getOwner(), () -> {
            if (BedWars.getServerType() == ServerType.BUNGEE) {
                if (Arena.getGamesBeforeRestart() == 0) {
                    if (Arena.getArenas().isEmpty()) {
                        plugin.getLogger().info("Dispatching command: " + config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD));
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD));
                    }
                } else {
                    if (Arena.getGamesBeforeRestart() != -1) {
                        Arena.setGamesBeforeRestart(Arena.getGamesBeforeRestart() - 1);
                    }
                    Bukkit.unloadWorld(a.getWorldName(), false);
                    if (Arena.canAutoScale(a.getArenaName())) {
                        Bukkit.getScheduler().runTaskLater(plugin, () -> new Arena(a.getArenaName(), null), 80L);
                    }
                }
            } else {
                Bukkit.unloadWorld(a.getWorldName(), false);
                Bukkit.getScheduler().runTaskLater(plugin, () -> new Arena(a.getArenaName(), null), 80L);
            }
            if (!a.getWorldName().equals(a.getArenaName())) {
                deleteWorld(a.getWorldName());
            }
        });
    }

    @Override
    public void onDisable(IArena a) {
        if(BedWars.isShuttingDown()) {
            Bukkit.unloadWorld(a.getWorldName(), false);
            return;
        }
        Bukkit.getScheduler().runTask(getOwner(), () -> Bukkit.unloadWorld(a.getWorldName(), false));
    }

    @Override
    public void onSetupSessionStart(ISetupSession s) {
        Bukkit.getScheduler().runTaskAsynchronously(getOwner(), () -> {
            File bf = new File(backupFolder, s.getWorldName() + ".zip"), af = new File(Bukkit.getWorldContainer(), s.getWorldName());
            if (bf.exists()) {
                FileUtil.delete(af);
                try {
                    ZipFileUtil.unzipFileIntoDirectory(bf, new File(Bukkit.getWorldContainer(), s.getWorldName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            WorldCreator wc = new WorldCreator(s.getWorldName());
            wc.generator(new VoidChunkGenerator());
            wc.generateStructures(false);
            Bukkit.getScheduler().runTask(getOwner(), () -> {
                try {
                    File level = new File(Bukkit.getWorldContainer(), s.getWorldName() + "/region");
                    if (level.exists()) {
                        s.getPlayer().sendMessage(ChatColor.GREEN + "Loading " + s.getWorldName() + " from Bukkit worlds container.");
                        deleteWorldTrash(s.getWorldName());
                        World w = Bukkit.createWorld(wc);
                        w.setKeepSpawnInMemory(true);
                    } else {
                        try {
                            s.getPlayer().sendMessage(ChatColor.GREEN + "Creating a new void map: " + s.getWorldName());
                            World w = Bukkit.createWorld(wc);
                            w.setKeepSpawnInMemory(true);
                            Bukkit.getScheduler().runTaskLater(plugin, s::teleportPlayer, 20L);
                        } catch (Exception ex){
                            ex.printStackTrace();
                            s.close();
                        }
                        return;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    s.close();
                    return;
                }
                Bukkit.getScheduler().runTaskLater(plugin, s::teleportPlayer, 20L);
            });
        });
    }

    @Override
    public void onSetupSessionClose(ISetupSession s) {
        Bukkit.getScheduler().runTask(getOwner(), () -> {
            Bukkit.getWorld(s.getWorldName()).save();
            Bukkit.unloadWorld(s.getWorldName(), true);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> new WorldZipper(s.getWorldName(), true));
        });
    }

    @Override
    public boolean isWorld(String name) {
        return new File(Bukkit.getWorldContainer(), name + "/region").exists();
    }

    @Override
    public void deleteWorld(String name) {
        Bukkit.getScheduler().runTaskAsynchronously(getOwner(), () -> {
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
                deleteWorldTrash(name2);
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
                    File dat = new File(fl.getName() + "/region");
                    if (dat.exists() && !fl.getName().startsWith("bw_temp")) {
                        worlds.add(fl.getName());
                    }
                }
            }
        }
        return worlds;
    }

    @Override
    public void convertWorlds() {
        File dir = new File(plugin.getDataFolder(), "/Arenas");
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
        Bukkit.getScheduler().runTaskAsynchronously(getOwner(), () -> {
            File[] files = Bukkit.getWorldContainer().listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f != null && f.isDirectory()) {
                        if (f.getName().contains("bw_temp_")) {
                            deleteWorld(f.getName());
                        }
                    }
                }
            }
        });
    }

    @Override
    public String getDisplayName() {
        return "Internal Restore Adapter";
    }

    private void deleteWorldTrash(String world) {
        for (File f : new File[]{new File(Bukkit.getWorldContainer(), world + "/level.dat"),
                new File(Bukkit.getWorldContainer(), world + "/level.dat_mcr"),
                new File(Bukkit.getWorldContainer(), world + "/level.dat_old"),
                new File(Bukkit.getWorldContainer(), world + "/session.lock"),
                new File(Bukkit.getWorldContainer(), world + "/uid.dat")}) {
            if (f.exists()) {
                if (!f.delete()) {
                    getOwner().getLogger().warning("Could not delete: " + f.getPath());
                    getOwner().getLogger().warning("This may cause issues!");
                }
            }
        }
    }
}