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

package com.andrei1058.bedwars.arena.mapreset.slime;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.server.ISetupSession;
import com.andrei1058.bedwars.api.server.RestoreAdapter;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.util.FileUtil;
import com.andrei1058.bedwars.api.util.ZipFileUtil;
import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.flowpowered.nbt.IntTag;
import com.flowpowered.nbt.stream.NBTInputStream;
import com.flowpowered.nbt.stream.NBTOutputStream;
import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.exceptions.*;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimeProperties;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class SlimeAdapter extends RestoreAdapter {

    private final SlimePlugin slime;
    private final BedWars api;

    public SlimeAdapter(Plugin plugin) {
        super(plugin);
        slime = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        api = Bukkit.getServer().getServicesManager().getRegistration(BedWars.class).getProvider();
    }

    @Override
    public void onEnable(IArena a) {
        if (api.getVersionSupport().getMainLevel().equalsIgnoreCase(a.getWorldName())) {
            if (!(api.getServerType() == ServerType.BUNGEE && api.getArenaUtil().getGamesBeforeRestart() == 1)) {
                FileUtil.setMainLevel("ignore_main_level", api.getVersionSupport());
                getOwner().getLogger().log(Level.SEVERE, "Cannot use level-name as arenas. Automatically creating a new void map for level-name.");
                getOwner().getLogger().log(Level.SEVERE, "The server is restarting...");
                Bukkit.getServer().spigot().restart();
                return;
            }
        }
        Bukkit.getScheduler().runTaskAsynchronously(getOwner(), () -> {
            if (Bukkit.getWorld(a.getWorldName()) != null) {
                Bukkit.getScheduler().runTask(getOwner(), () -> {
                    World w = Bukkit.getWorld(a.getWorldName());
                    a.init(w);
                });
                return;
            }

            SlimeLoader flat = slime.getLoader("file");
            String[] spawn = a.getConfig().getString("waiting.Loc").split(",");

            SlimePropertyMap spm = new SlimePropertyMap();
            spm.setString(SlimeProperties.WORLD_TYPE, "flat");
            spm.setInt(SlimeProperties.SPAWN_X, (int) Double.parseDouble(spawn[0]));
            spm.setInt(SlimeProperties.SPAWN_Y, (int) Double.parseDouble(spawn[1]));
            spm.setInt(SlimeProperties.SPAWN_Z, (int) Double.parseDouble(spawn[2]));
            spm.setBoolean(SlimeProperties.ALLOW_ANIMALS, false);
            spm.setBoolean(SlimeProperties.ALLOW_MONSTERS, false);
            spm.setString(SlimeProperties.DIFFICULTY, "easy");
            spm.setBoolean(SlimeProperties.PVP, true);

            try {
                // Note that this method should be called asynchronously
                SlimeWorld world = slime.loadWorld(flat, a.getArenaName(), true, spm);
                if (api.getServerType() == ServerType.BUNGEE && api.isAutoScale()) {
                    world = world.clone(a.getWorldName());
                }

                // This method must be called synchronously
                SlimeWorld finalWorld = world;
                Bukkit.getScheduler().runTask(getOwner(), () -> slime.generateWorld(finalWorld));
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldInUseException ex) {
                api.getArenaUtil().removeFromEnableQueue(a);
                ex.printStackTrace();
            } catch (ConcurrentModificationException thisShouldNotHappenSWM){
                // this should not happen since they say to use #load async
                // https://github.com/Grinderwolf/Slime-World-Manager/blob/develop/.docs/api/load-world.md
                thisShouldNotHappenSWM.printStackTrace();
                api.getArenaUtil().removeFromEnableQueue(a);
                getOwner().getLogger().severe("This is a SlimeWorldManager issue!");
                getOwner().getLogger().severe("I've submitted a bug report: https://github.com/Grinderwolf/Slime-World-Manager/issues/174");
                getOwner().getLogger().severe("Trying again to load arena: " + a.getArenaName());

                // hope not to get an overflow
                onEnable(a);
            }
        });
    }

    @Override
    public void onRestart(IArena a) {
        if (api.getServerType() == ServerType.BUNGEE) {
            if (api.getArenaUtil().getGamesBeforeRestart() == 0) {
                if (api.getArenaUtil().getArenas().size() == 1 && api.getArenaUtil().getArenas().get(0).getStatus() == GameState.restarting) {
                    getOwner().getLogger().info("Dispatching command: " + api.getConfigs().getMainConfig().getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD));
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), api.getConfigs().getMainConfig().getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD));
                }
            } else {
                if (api.getArenaUtil().getGamesBeforeRestart() != -1) {
                    api.getArenaUtil().setGamesBeforeRestart(api.getArenaUtil().getGamesBeforeRestart() - 1);
                }
                Bukkit.getScheduler().runTask(getOwner(), () -> {
                    Bukkit.unloadWorld(a.getWorldName(), false);
                    if (api.getArenaUtil().canAutoScale(a.getArenaName())) {
                        Bukkit.getScheduler().runTaskLater(getOwner(), () -> api.getArenaUtil().loadArena(a.getArenaName(), null), 80L);
                    }
                });
            }
        } else {
            Bukkit.getScheduler().runTask(getOwner(), () -> {
                Bukkit.unloadWorld(a.getWorldName(), false);
                Bukkit.getScheduler().runTaskLater(getOwner(), () -> api.getArenaUtil().loadArena(a.getArenaName(), null), 80L);
            });
        }
    }

    @Override
    public void onDisable(IArena a) {
        if(api.isShuttingDown()) {
            Bukkit.unloadWorld(a.getWorldName(), false);
            return;
        }
        Bukkit.getScheduler().runTask(getOwner(), () -> Bukkit.unloadWorld(a.getWorldName(), false));
    }

    @Override
    public void onSetupSessionStart(ISetupSession s) {
        Bukkit.getScheduler().runTaskAsynchronously(getOwner(), () -> {
            SlimeLoader sLoader = slime.getLoader("file");
            String[] spawn = new String[]{"0", "50", "0"};
            if (s.getConfig().getYml().getString("waiting.Loc") != null) {
                spawn = s.getConfig().getString("waiting.Loc").split(",");
            }
            SlimePropertyMap spm = new SlimePropertyMap();
            spm.setString(SlimeProperties.WORLD_TYPE, "flat");
            spm.setInt(SlimeProperties.SPAWN_X, (int) Double.parseDouble(spawn[0]));
            spm.setInt(SlimeProperties.SPAWN_Y, (int) Double.parseDouble(spawn[1]));
            spm.setInt(SlimeProperties.SPAWN_Z, (int) Double.parseDouble(spawn[2]));
            spm.setBoolean(SlimeProperties.ALLOW_ANIMALS, false);
            spm.setBoolean(SlimeProperties.ALLOW_MONSTERS, false);
            spm.setString(SlimeProperties.DIFFICULTY, "easy");
            spm.setBoolean(SlimeProperties.PVP, true);

            try {

                if (Bukkit.getWorld(s.getWorldName()) != null) {
                    Bukkit.getScheduler().runTask(getOwner(), () -> Bukkit.unloadWorld(s.getWorldName(), false));
                }

                SlimeWorld world;
                if (sLoader.worldExists(s.getWorldName())) {
                    world = slime.loadWorld(sLoader, s.getWorldName(), false, spm);
                    Bukkit.getScheduler().runTask(getOwner(), () -> s.getPlayer().sendMessage(ChatColor.GREEN + "Loading world from SlimeWorldManager container."));
                } else {
                    if (new File(Bukkit.getWorldContainer(), s.getWorldName() + "/level.dat").exists()) {
                        Bukkit.getScheduler().runTask(getOwner(), () -> s.getPlayer().sendMessage(ChatColor.GREEN + "Importing world to the SlimeWorldManager container."));
                        slime.importWorld(new File(Bukkit.getWorldContainer(), s.getWorldName()), s.getWorldName().toLowerCase(), sLoader);
                        world = slime.loadWorld(sLoader, s.getWorldName(), false, spm);
                    } else {
                        Bukkit.getScheduler().runTask(getOwner(), () -> s.getPlayer().sendMessage(ChatColor.GREEN + "Creating anew void map."));
                        world = slime.createEmptyWorld(sLoader, s.getWorldName(), false, spm);
                    }
                }

                SlimeWorld sw = world;
                // This method must be called synchronously
                Bukkit.getScheduler().runTask(getOwner(), () -> {
                    slime.generateWorld(sw);
                    s.teleportPlayer();
                });
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldInUseException | WorldAlreadyExistsException | InvalidWorldException | WorldTooBigException | WorldLoadedException ex) {
                s.getPlayer().sendMessage(ChatColor.RED + "An error occurred! Please check console.");
                ex.printStackTrace();
                s.close();
            }
        });
    }

    @Override
    public void onSetupSessionClose(ISetupSession s) {
        Bukkit.getWorld(s.getWorldName()).save();
        Bukkit.getScheduler().runTask(getOwner(), () -> Bukkit.unloadWorld(s.getWorldName(), true));
    }

    @Override
    public void onLobbyRemoval(IArena a) {
        Location loc1 = a.getConfig().getArenaLoc(ConfigPath.ARENA_WAITING_POS1),
                loc2 = a.getConfig().getArenaLoc(ConfigPath.ARENA_WAITING_POS2);
        if (loc1 == null || loc2 == null) return;
        Bukkit.getScheduler().runTask(getOwner(), () -> {
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

            Bukkit.getScheduler().runTaskLater(getOwner(), () ->
                    loc1.getWorld().getEntities().forEach(e -> {
                        if (e instanceof Item) e.remove();
                    }), 15L);
        });
    }

    @Override
    public boolean isWorld(String name) {
        try {
            return slime.getLoader("file").worldExists(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteWorld(String name) {
        Bukkit.getScheduler().runTaskAsynchronously(getOwner(), () -> {
            try {
                slime.getLoader("file").deleteWorld(name);
            } catch (UnknownWorldException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void cloneArena(String name1, String name2) {
        Bukkit.getScheduler().runTaskAsynchronously(getOwner(), () -> {
            SlimePropertyMap spm = new SlimePropertyMap();
            spm.setString(SlimeProperties.WORLD_TYPE, "flat");
            spm.setInt(SlimeProperties.SPAWN_X, 0);
            spm.setInt(SlimeProperties.SPAWN_Y, 118);
            spm.setInt(SlimeProperties.SPAWN_Z, 0);
            spm.setBoolean(SlimeProperties.ALLOW_ANIMALS, false);
            spm.setBoolean(SlimeProperties.ALLOW_MONSTERS, false);
            spm.setString(SlimeProperties.DIFFICULTY, "easy");
            spm.setBoolean(SlimeProperties.PVP, true);

            try {
                // Note that this method should be called asynchronously
                SlimeWorld world = slime.loadWorld(slime.getLoader("file"), name1, true, spm);
                world.clone(name2, slime.getLoader("file"));
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldInUseException | WorldAlreadyExistsException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public List<String> getWorldsList() {
        try {
            return slime.getLoader("file").listWorlds();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Convert vanilla worlds to the slime format.
     */
    public void convertWorlds() {
        File dir = new File(getOwner().getDataFolder(), "/Arenas");
        File ff;
        SlimeLoader sl = slime.getLoader("file");
        if (dir.exists()) {
            File[] fls = dir.listFiles();
            if (fls != null) {
                for (File fl : fls) {
                    if (fl.isFile()) {
                        if (fl.getName().endsWith(".yml")) {
                            final String name = fl.getName().replace(".yml", "").toLowerCase();
                            ff = new File(Bukkit.getWorldContainer(), fl.getName().replace(".yml", ""));
                            try {
                                if (!sl.worldExists(name)) {
                                    if (!fl.getName().equals(name)) {
                                        if (!fl.renameTo(new File(dir, name + ".yml"))) {
                                            getOwner().getLogger().log(Level.WARNING, "Could not rename " + fl.getName() + ".yml to " + name + ".yml");
                                        }
                                    }
                                    File bc = new File(getOwner().getDataFolder() + "/Cache", ff.getName() + ".zip");
                                    if (ff.exists() && bc.exists()) {
                                        FileUtil.delete(ff);
                                        ZipFileUtil.unzipFileIntoDirectory(bc, new File(Bukkit.getWorldContainer(), name));
                                    }
                                    deleteWorldTrash(name);
                                    handleLevelDat(name);

                                    convertWorld(name, null);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        Bukkit.getScheduler().runTaskAsynchronously(getOwner(), () -> {
            File[] files = Bukkit.getWorldContainer().listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f != null && f.isDirectory()) {
                        if (f.getName().contains("bw_temp_")) {
                            try {
                                FileUtils.deleteDirectory(f);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public String getDisplayName() {
        return "Slime World Manager by Grinderwolf";
    }

    private void convertWorld(String name, Player player) {
        SlimeLoader sl = slime.getLoader("file");
        try {
            getOwner().getLogger().log(Level.INFO, "Converting " + name + " to the Slime format.");
            slime.importWorld(new File(Bukkit.getWorldContainer(), name), name, sl);
        } catch (WorldAlreadyExistsException | InvalidWorldException | WorldLoadedException | WorldTooBigException | IOException e) {
            if (player != null) {
                player.sendMessage(ChatColor.RED + "Could not convert " + name + " to the Slime format.");
                player.sendMessage(ChatColor.RED + "Check the console for details.");
                ISetupSession s = api.getSetupSession(player.getUniqueId());
                if (s != null) {
                    s.close();
                }
            }
            getOwner().getLogger().log(Level.WARNING, "Could not convert " + name + " to the Slime format.");
            e.printStackTrace();
        }
    }

    private void deleteWorldTrash(String world) {
        for (File f : new File[]{/*new File(Bukkit.getWorldContainer(), world+"/level.dat"),*/
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

    private void handleLevelDat(String world) throws IOException {

        File level = new File(Bukkit.getWorldContainer(), world + "/level.dat");

        if (!level.exists()) {
            if (level.createNewFile()) {
                File regions = new File(Bukkit.getWorldContainer(), "world/region");
                if (regions.exists() && Objects.requireNonNull(regions.list()).length > 0) {
                    if (Arrays.stream(Objects.requireNonNull(regions.list())).filter(p -> p.endsWith(".mca")).toArray().length > 0) {
                        File region = new File(Bukkit.getWorldContainer(), world + "/" + Arrays.stream(Objects.requireNonNull(regions.list())).filter(p -> p.endsWith(".mca")).toArray()[0]);
                        NBTInputStream inputStream = new NBTInputStream(new FileInputStream(region));
                        Optional<CompoundTag> tag = inputStream.readTag().getAsCompoundTag();
                        inputStream.close();
                        if (tag.isPresent()) {
                            Optional<CompoundTag> dataTag = tag.get().getAsCompoundTag("Chunk");
                            if (dataTag.isPresent()) {
                                int dataVersion = dataTag.get().getIntValue("DataVersion").orElse(-1);

                                NBTOutputStream outputStream = new NBTOutputStream(new FileOutputStream(level));

                                CompoundMap cm = new CompoundMap();
                                cm.put(new IntTag("SpawnX", 0));
                                cm.put(new IntTag("SpawnY", 255));
                                cm.put(new IntTag("SpawnZ", 0));
                                if (dataVersion != -1) {
                                    cm.put(new IntTag("DataVersion", dataVersion));
                                }
                                CompoundTag root = new CompoundTag("Data", cm);
                                outputStream.writeTag(root);
                                outputStream.flush();
                                outputStream.close();
                            }
                        }
                    }
                }
            }
        }
    }
}
