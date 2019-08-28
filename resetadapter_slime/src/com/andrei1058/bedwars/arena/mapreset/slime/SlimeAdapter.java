package com.andrei1058.bedwars.arena.mapreset.slime;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.api.arena.RestoreAdapter;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.maprestore.internal.files.WorldZipper;
import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.exceptions.*;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static com.andrei1058.bedwars.Main.config;
import static com.andrei1058.bedwars.Main.plugin;

public class SlimeAdapter extends RestoreAdapter {

    private SlimePlugin slime;

    public SlimeAdapter(Plugin plugin) {
        super(plugin);
        slime = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
    }

    @Override
    public void onEnable(Arena a) {
        if (Bukkit.getWorld(a.getWorldName()) != null) {
            a.init(Bukkit.getWorld(a.getWorldName()));
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            SlimeLoader flat = slime.getLoader("file");

            String[] spawn = a.getCm().getString("waiting.Loc").split(",");
            SlimeWorld.SlimeProperties props = SlimeWorld.SlimeProperties.builder().difficulty(1).allowAnimals(false).allowMonsters(false).spawnX(Double.parseDouble(spawn[0]))
                    .spawnY(Double.parseDouble(spawn[1])).spawnZ(Double.parseDouble(spawn[2])).pvp(true).readOnly(true).build();
            try {
                // Note that this method should be called asynchronously
                SlimeWorld world = slime.loadWorld(flat, a.getWorldName(), props);

                // This method must be called synchronously
                Bukkit.getScheduler().runTask(Main.plugin, () -> {
                    try {
                        slime.generateWorld(world);
                        a.init(Bukkit.getWorld(a.getWorldName()));
                    } catch (Exception e) {
                        Arena.removeFromEnableQueue(a);
                    }
                });
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldInUseException | UnsupportedWorldException ex) {
                ex.printStackTrace();
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
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            SlimeLoader sqlLoader = slime.getLoader("file");
            String[] spawn = new String[]{"0", "118", "0"};
            if (s.getCm().getYml().getString("waiting.Loc") != null) {
                spawn = s.getCm().getString("waiting.Loc").split(",");
            }
            SlimeWorld.SlimeProperties props = SlimeWorld.SlimeProperties.builder().difficulty(1).allowAnimals(false).allowMonsters(false).spawnX(Double.parseDouble(spawn[0]))
                    .spawnY(Double.parseDouble(spawn[1])).spawnZ(Double.parseDouble(spawn[2])).pvp(true).readOnly(false).build();
            try {
                // Note that this method should be called asynchronously
                SlimeWorld world = slime.loadWorld(sqlLoader, s.getWorldName(), props);

                // This method must be called synchronously
                Bukkit.getScheduler().runTask(Main.plugin, () -> {
                    slime.generateWorld(world);
                    s.setupStarted();
                });
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldInUseException | UnsupportedWorldException ex) {
                ex.printStackTrace();
            }
        });
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
        try {
            return slime.getLoader("file").worldExists(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteWorld(String name) {
        try {
            slime.getLoader("file").deleteWorld(name);
        } catch (UnknownWorldException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cloneArena(String name1, String name2) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            SlimeWorld.SlimeProperties props = SlimeWorld.SlimeProperties.builder().difficulty(1).allowAnimals(false).allowMonsters(false).spawnX(0)
                    .spawnY(118).spawnZ(0).pvp(true).readOnly(true).build();
            try {
                // Note that this method should be called asynchronously
                SlimeWorld world = slime.loadWorld(slime.getLoader("file"), name1, props);
                world.clone(name2, slime.getLoader("file"));
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldInUseException | UnsupportedWorldException | WorldAlreadyExistsException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public List<String> getWorldsList() {
        try {
            return slime.getLoader("mysql").listWorlds();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Convert vanilla worlds to the slime format.
     */
    public void convertWorlds() {
        File dir = new File("plugins/" + plugin.getName() + "/Arenas");
        File ff;
        SlimeLoader sl = slime.getLoader("file");
        if (dir.exists()) {
            File[] fls = dir.listFiles();
            for (File fl : Objects.requireNonNull(fls)) {
                if (fl.isFile()) {
                    if (fl.getName().contains(".yml")) {
                        String name = fl.getName().replace(".yml", "");
                        ff = new File(Bukkit.getWorldContainer(), name);
                        if (ff.exists()) {
                            if (fl.getName().equals(fl.getName().toLowerCase())) {
                                if (!fl.renameTo(new File(dir, fl.getName().toLowerCase()))) {
                                    Bukkit.getLogger().log(Level.WARNING, "Could not rename " + fl.getName() + ".yml to " + fl.getName().toLowerCase() + ".yml");
                                }
                            }
                            try {
                                if (!sl.worldExists(ff.getName().toLowerCase())) {
                                    try {
                                        Bukkit.getLogger().log(Level.INFO, "Converting " + ff.getName() + " to the Slime format.");
                                        slime.importWorld(ff, ff.getName().toLowerCase(), sl);
                                    } catch (WorldAlreadyExistsException | InvalidWorldException | WorldLoadedException | WorldTooBigException | IOException e) {
                                        Bukkit.getLogger().log(Level.WARNING, "Could not convert " + ff.getName() + " to the Slime format.");
                                        e.printStackTrace();
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
