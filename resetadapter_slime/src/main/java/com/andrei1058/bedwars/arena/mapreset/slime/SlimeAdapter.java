package com.andrei1058.bedwars.arena.mapreset.slime;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.server.ISetupSession;
import com.andrei1058.bedwars.api.server.RestoreAdapter;
import com.andrei1058.bedwars.api.server.ServerType;
import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.exceptions.*;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class SlimeAdapter extends RestoreAdapter {

    private SlimePlugin slime;
    private BedWars api;

    public SlimeAdapter(Plugin plugin) {
        super(plugin);
        slime = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        api = Bukkit.getServer().getServicesManager().getRegistration(BedWars.class).getProvider();
    }

    @Override
    public void onEnable(IArena a) {

        Bukkit.getScheduler().runTaskAsynchronously(getOwner(), () -> {
            if (Bukkit.getWorlds().get(0).getName().equals(a.getWorldName())) {
                if (api.getServerType() != ServerType.BUNGEE) {
                    getOwner().getLogger().log(Level.SEVERE, "You can't use an arena world in server.properties as level-name when running the server in " + api.getServerType().toString() + " mode!");
                    getOwner().getLogger().log(Level.SEVERE, a.getWorldName() + " will not be loaded.");
                    api.getArenaUtil().removeFromEnableQueue(a);
                    return;
                }
                try {
                    getOwner().getLogger().severe("For a better performance please do not use arena worlds as level-name in server.properties");
                    getOwner().getLogger().severe("Use a void map instead and never touch it. Minecraft requires a main level that can't be restored without restarting the server.");
                    getOwner().getLogger().log(Level.WARNING, "Your server will be restarted after each game.");
                    Bukkit.getScheduler().runTask(getOwner(), () -> {
                        World w = Bukkit.getWorlds().get(0);
                        w.setKeepSpawnInMemory(true);
                        w.setAutoSave(false);
                        api.getArenaUtil().setGamesBeforeRestart(1);
                        a.init(w);
                    });
                } catch (IllegalArgumentException e) {
                    if (e.getMessage().contains("ChunkNibbleArrays should be 2048 bytes")) {
                        getOwner().getLogger().log(Level.SEVERE, "Could not load arena: " + a.getWorldName());
                        getOwner().getLogger().log(Level.SEVERE, "Your world has corrupt chunks!");
                    }
                }
                return;
            }

            SlimeLoader flat = slime.getLoader("file");
            String[] spawn = a.getConfig().getString("waiting.Loc").split(",");
            SlimeWorld.SlimeProperties props = SlimeWorld.SlimeProperties.builder().difficulty(1).allowAnimals(false).allowMonsters(false).spawnX(Double.parseDouble(spawn[0]))
                    .spawnY(Double.parseDouble(spawn[1])).spawnZ(Double.parseDouble(spawn[2])).pvp(true).readOnly(true).build();
            try {
                // Note that this method should be called asynchronously
                SlimeWorld world = slime.loadWorld(flat, a.getWorldName(), props);

                // This method must be called synchronously
                Bukkit.getScheduler().runTask(getOwner(), () -> {
                    try {
                        slime.generateWorld(world);
                        a.init(Bukkit.getWorld(a.getWorldName()));
                    } catch (Exception e) {
                        api.getArenaUtil().removeFromEnableQueue(a);
                    }
                });
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldInUseException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void onRestart(IArena a) {
        if (api.getServerType() == ServerType.BUNGEE) {
            api.getArenaUtil().setGamesBeforeRestart(api.getArenaUtil().getGamesBeforeRestart() - 1);
            if (api.getArenaUtil().getGamesBeforeRestart() == 0) {
                getOwner().getLogger().info("Dispatching command: " + api.getConfigs().getMainConfig().getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), api.getConfigs().getMainConfig().getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD));
            } else {
                if (api.getArenaUtil().getGamesBeforeRestart() != -1) {
                    api.getArenaUtil().setGamesBeforeRestart(api.getArenaUtil().getGamesBeforeRestart() - 1);
                }
                Bukkit.unloadWorld(a.getWorldName(), false);
                Bukkit.getScheduler().runTaskLater(getOwner(), () -> api.getArenaUtil().loadArena(a.getWorldName(), null), 80L);
            }
        } else {
            Bukkit.unloadWorld(a.getWorldName(), false);
            Bukkit.getScheduler().runTaskLater(getOwner(), () -> api.getArenaUtil().loadArena(a.getWorldName(), null), 80L);
        }
    }

    @Override
    public void onDisable(IArena a) {
        Bukkit.unloadWorld(a.getWorldName(), false);
    }

    @Override
    public void onSetupSessionStart(ISetupSession s) {
        Bukkit.getScheduler().runTaskAsynchronously(getOwner(), () -> {
            SlimeLoader sqlLoader = slime.getLoader("file");
            String[] spawn = new String[]{"0", "118", "0"};
            if (s.getConfig().getYml().getString("waiting.Loc") != null) {
                spawn = s.getConfig().getString("waiting.Loc").split(",");
            }
            SlimeWorld.SlimeProperties props = SlimeWorld.SlimeProperties.builder().difficulty(1).allowAnimals(false).allowMonsters(false).spawnX(Double.parseDouble(spawn[0]))
                    .spawnY(Double.parseDouble(spawn[1])).spawnZ(Double.parseDouble(spawn[2])).pvp(true).readOnly(false).build();
            try {

                if (Bukkit.getWorld(s.getWorldName()) != null) {
                    Bukkit.unloadWorld(s.getWorldName(), false);
                }

                // Note that this method should be called asynchronously
                SlimeWorld world = slime.loadWorld(sqlLoader, s.getWorldName(), props);

                // This method must be called synchronously
                Bukkit.getScheduler().runTask(getOwner(), () -> {
                    slime.generateWorld(world);
                    s.teleportPlayer();
                });
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldInUseException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void onSetupSessionClose(ISetupSession s) {
        Bukkit.unloadWorld(s.getWorldName(), true);
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
        Bukkit.getScheduler().runTaskAsynchronously(getOwner(), () -> {
            SlimeWorld.SlimeProperties props = SlimeWorld.SlimeProperties.builder().difficulty(1).allowAnimals(false).allowMonsters(false).spawnX(0)
                    .spawnY(118).spawnZ(0).pvp(true).readOnly(true).build();
            try {
                // Note that this method should be called asynchronously
                SlimeWorld world = slime.loadWorld(slime.getLoader("file"), name1, props);
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
        File dir = new File("plugins/" + getOwner().getName() + "/Arenas");
        File ff;
        SlimeLoader sl = slime.getLoader("file");
        if (dir.exists()) {
            File[] fls = dir.listFiles();
            if (fls != null) {
                for (File fl : fls) {
                    if (fl.isFile()) {
                        if (fl.getName().contains(".yml")) {
                            String name = fl.getName().replace(".yml", "");
                            ff = new File(Bukkit.getWorldContainer(), name);
                            if (ff.exists()) {
                                if (fl.getName().equals(fl.getName().toLowerCase())) {
                                    if (!fl.renameTo(new File(dir, fl.getName().toLowerCase()))) {
                                        getOwner().getLogger().log(Level.WARNING, "Could not rename " + fl.getName() + ".yml to " + fl.getName().toLowerCase() + ".yml");
                                    }
                                }
                                try {
                                    if (!sl.worldExists(ff.getName().toLowerCase())) {
                                        try {
                                            getOwner().getLogger().log(Level.INFO, "Converting " + ff.getName() + " to the Slime format.");
                                            slime.importWorld(ff, ff.getName().toLowerCase(), sl);
                                        } catch (WorldAlreadyExistsException | InvalidWorldException | WorldLoadedException | WorldTooBigException | IOException e) {
                                            getOwner().getLogger().log(Level.WARNING, "Could not convert " + ff.getName() + " to the Slime format.");
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
}
