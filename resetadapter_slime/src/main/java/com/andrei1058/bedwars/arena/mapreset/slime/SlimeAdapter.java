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
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

@SuppressWarnings({"CallToPrintStackTrace", "unused"})
public class SlimeAdapter extends RestoreAdapter {

    private final SlimePlugin slime;
    private final BedWars api;

    public SlimeAdapter(Plugin plugin) {
        super(plugin);
        slime = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        api = Objects.requireNonNull(Bukkit.getServer().getServicesManager().getRegistration(BedWars.class)).getProvider();
    }

    @Override
    public void onEnable(@NotNull IArena a) {
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

            try {
                // Note that this method should be called asynchronously
                SlimeWorld world = slime.loadWorld(flat, a.getArenaName(), true, this.buildPropertyMap(spawn));
                if (api.getServerType() == ServerType.BUNGEE && api.isAutoScale()) {
                    world = world.clone(a.getWorldName());
                }

                // This method must be called synchronously
                SlimeWorld finalWorld = world;
                Bukkit.getScheduler().runTask(getOwner(), () -> slime.generateWorld(finalWorld));
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                     WorldInUseException ex) {
                api.getArenaUtil().removeFromEnableQueue(a);
                ex.printStackTrace();
            } catch (ConcurrentModificationException thisShouldNotHappenSWM) {
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

    private @NotNull SlimePropertyMap buildPropertyMap(String @NotNull [] spawn) {
        SlimePropertyMap spm = new SlimePropertyMap();
        spm.setString(SlimeProperties.WORLD_TYPE, "flat");
        spm.setInt(SlimeProperties.SPAWN_X, (int) Double.parseDouble(spawn[0]));
        spm.setInt(SlimeProperties.SPAWN_Y, (int) Double.parseDouble(spawn[1]));
        spm.setInt(SlimeProperties.SPAWN_Z, (int) Double.parseDouble(spawn[2]));
        spm.setBoolean(SlimeProperties.ALLOW_ANIMALS, false);
        spm.setBoolean(SlimeProperties.ALLOW_MONSTERS, false);
        spm.setString(SlimeProperties.DIFFICULTY, "easy");
        spm.setBoolean(SlimeProperties.PVP, true);

        return spm;
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
        if (api.isShuttingDown()) {
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
            SlimePropertyMap spm = this.buildPropertyMap(spawn);

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
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                     WorldInUseException | WorldAlreadyExistsException | InvalidWorldException | WorldTooBigException |
                     WorldLoadedException ex) {
                s.getPlayer().sendMessage(ChatColor.RED + "An error occurred! Please check console.");
                ex.printStackTrace();
                s.close();
            }
        });
    }

    @Override
    public void onSetupSessionClose(@NotNull ISetupSession session) {
        World world = Bukkit.getWorld(session.getWorldName());
        if (null == world) {
            return;
        }
        world.save();
        Bukkit.getScheduler().runTask(getOwner(), () -> Bukkit.unloadWorld(session.getWorldName(), true));
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
            SlimePropertyMap spm = this.buildPropertyMap(new String[]{"0", "118", "0"});

            try {
                // Note that this method should be called asynchronously
                SlimeWorld world = slime.loadWorld(slime.getLoader("file"), name1, true, spm);
                world.clone(name2, slime.getLoader("file"));
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                     WorldInUseException | WorldAlreadyExistsException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public List<String> getWorldsList() {
        try {
            return slime.getLoader("file").listWorlds();
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
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
                                    // clean up world folder
                                    deleteWorldTrash(name);
                                    // check if level.dat is missing in world folder
                                    handleLevelDat(name);
                                    // start Slime conversion
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

    private void convertWorld(String name, @SuppressWarnings("SameParameterValue") @Nullable Player player) {
        File worldFolder = new File(Bukkit.getWorldContainer(), name);
        if (!worldFolder.exists() || !worldFolder.isDirectory()) {
            getOwner().getLogger().severe("Tried converting arena " + name + " to Slime format, but couldn't find any bukkit world folder.");
            return;
        }

        // todo allow data loaders
        SlimeLoader sl = slime.getLoader("file");
        try {
            getOwner().getLogger().log(Level.INFO, "Converting " + name + " to the Slime format.");
            slime.importWorld(new File(Bukkit.getWorldContainer(), name), name, sl);
        } catch (WorldAlreadyExistsException | InvalidWorldException | WorldLoadedException | WorldTooBigException |
                 IOException e) {
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

    /**
     * Create level.dat in world folder before converting to Slime format.
     * References:
     * <a href="https://github.com/cijaaimee/Slime-World-Manager/blob/develop/slimeworldmanager-importer/src/main/java/com/grinderwolf/swm/importer/SWMImporter.java">Slime importer requirements</a>
     * <a href="https://wiki.vg/Map_Format#level.dat">level.dat NBT TAG</a>
     *
     * @param world folder name.
     */
    private void handleLevelDat(String world) throws IOException {
        File worldFolder = new File(Bukkit.getWorldContainer(), world);

        if (!worldFolder.exists() || !worldFolder.isDirectory()) {
            return;
        }

        File levelFile = new File(worldFolder, "level.dat");
        if (levelFile.exists()) {
            return;
        }

        // try detecting world version from region files
        File regionFolder = new File(worldFolder, "region");
        File[] regionFiles = regionFolder.listFiles();

        if (!regionFolder.exists() || null == regionFiles || !regionFolder.isDirectory()) {
            getOwner().getLogger().severe("Tried detecting world version, but it has no regions! (" + world + ")");
            return;
        }

        Optional<File> firstRegion = Arrays.stream(regionFiles).filter(
                regionFile -> regionFile.isFile() && regionFile.getName().endsWith(".mca")
        ).findFirst();

        Optional<Integer> dataVersion = Optional.empty();

        // try getting world version from NBT TAG
        if (firstRegion.isPresent()) {
            try {
                NBTInputStream inputStream = new NBTInputStream(new FileInputStream(firstRegion.get()));
                Optional<CompoundTag> tag = inputStream.readTag().getAsCompoundTag();
                inputStream.close();

                if (tag.isPresent()) {
                    Optional<CompoundTag> dataTag = tag.get().getAsCompoundTag("Chunk");
                    Optional<Integer> version = dataTag.flatMap(tagMap -> tagMap.getIntValue("DataVersion"));
                    if (version.isPresent()) {
                        dataVersion = version;
                        Bukkit.getLogger().info(
                                "Detected world version from region file for level.dat creation: v" +
                                        version.get() + " (" + world + ")"
                        );
                    }
                }
            } catch (Exception ignored) {
            }
        }

        String errorMessage = "Cannot create level.dat in " + worldFolder;

        // if world version was not detected we assume it is 1.8.8 and move on :)
        // create new level.dat file and write TAG
        if (!levelFile.createNewFile()) {
            getOwner().getLogger().severe(errorMessage);
            return;
        }

        try (NBTOutputStream outputStream = new NBTOutputStream(new FileOutputStream(levelFile))) {
            CompoundMap cm = new CompoundMap();
            cm.put(new IntTag("SpawnX", 0));
            cm.put(new IntTag("SpawnY", 255));
            cm.put(new IntTag("SpawnZ", 0));
            dataVersion.ifPresent(integer -> cm.put(new IntTag("DataVersion", integer)));

            CompoundTag dataTag = new CompoundTag("Data", cm);
            CompoundMap rootTag = new CompoundMap();
            rootTag.put(dataTag);

            outputStream.writeTag(new CompoundTag("", rootTag));
            outputStream.flush();
        } catch (Exception ignored) {
            try {
                // clean up empty file
                // noinspection ResultOfMethodCallIgnored
                levelFile.delete();
            } catch (Exception ignored2) {
            }
            getOwner().getLogger().severe(errorMessage);
        }
    }
}
