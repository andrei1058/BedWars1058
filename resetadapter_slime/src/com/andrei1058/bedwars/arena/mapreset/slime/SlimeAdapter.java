package com.andrei1058.bedwars.arena.mapreset.slime;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.arena.RestoreAdapter;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.exceptions.*;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getName;

public class SlimeAdapter extends RestoreAdapter {

    private SlimePlugin slime;

    public SlimeAdapter(Plugin plugin) {
        super(plugin);
        slime = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
    }

    @Override
    public void onEnable(Arena a) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            SlimeLoader sqlLoader = slime.getLoader("mysql");
            String[] spawn = a.getCm().getString("waiting.Loc").split(",");
            SlimeWorld.SlimeProperties props = SlimeWorld.SlimeProperties.builder().difficulty(1).allowAnimals(false).allowMonsters(false).spawnX(Double.parseDouble(spawn[0]))
                    .spawnY(Double.parseDouble(spawn[1])).spawnZ(Double.parseDouble(spawn[2])).pvp(true).readOnly(true).build();
            try {
                // Note that this method should be called asynchronously
                SlimeWorld world = slime.loadWorld(sqlLoader, getName(), props);

                // This method must be called synchronously
                Bukkit.getScheduler().runTask(Main.plugin, () -> {
                    slime.generateWorld(world);
                    a.init(Bukkit.getWorld(getName()));
                });
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldInUseException | UnsupportedWorldException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void onRestart(Arena a) {

    }

    @Override
    public void onDisable(Arena a) {

    }

    @Override
    public boolean onSetupSessionStart(SetupSession s) {
        return false;
    }

    @Override
    public void onSetupSessionClose(SetupSession s) {

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
            return slime.getLoader("mysql").worldExists(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteWorld(String name) {
        try {
            slime.getLoader("mysql").deleteWorld(name);
        } catch (UnknownWorldException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cloneArena(String name1, String name2) {
        //todo not available atm
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
}
