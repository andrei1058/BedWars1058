package com.andrei1058.bedwars.arena.mapreset;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import com.boydti.fawe.object.schematic.Schematic;
import com.boydti.fawe.util.TaskManager;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FaweReset implements MapResetter {

    private ClipboardFormat format;

    @SuppressWarnings("deprecation")
    public FaweReset() {
        format = Main.getServerVersion().equalsIgnoreCase("v1_8_R3") ? ClipboardFormat.SCHEMATIC : ClipboardFormat.STRUCTURE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void backupLobby(Location loc1, Location loc2) {
        if (loc1 == null || loc2 == null) return;

        File file = new File(Main.plugin.getDataFolder() + "/Cache/lobby_" + loc1.getWorld().getName() + "." + format.getExtension());
        Vector bot = new Vector(Math.min(loc1.getBlockX(), loc2.getBlockX()), Math.min(loc1.getBlockY(), loc2.getBlockY()), Math.min(loc1.getBlockZ(), loc2.getBlockZ())); //MUST be a whole number eg integer
        Vector top = new Vector(Math.max(loc1.getBlockX(), loc2.getBlockX()), Math.max(loc1.getBlockY(), loc2.getBlockY()), Math.max(loc1.getBlockZ(), loc2.getBlockZ())); //MUST be a whole number eg integer

        CuboidRegion region = new CuboidRegion(new BukkitWorld(loc1.getWorld()), bot, top);
        Schematic schem = new Schematic(region);
        try {
            schem.save(file, format);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void restoreLobby(MapManager mapManager) {
        final Location loc1 = mapManager.configManager.getArenaLoc(ConfigPath.ARENA_WAITING_POS1), loc2 = mapManager.configManager.getArenaLoc(ConfigPath.ARENA_WAITING_POS2);
        if (loc1 == null || loc2 == null) return;
        File f = new File(Main.plugin.getDataFolder() + "/Cache/lobby_" + mapManager.getWorldName() + "." + format.getExtension());
        if (!f.exists()) return;
        Vector pos1 = new Vector(Math.min(loc1.getBlockX(), loc2.getBlockX()), Math.min(loc1.getBlockY(), loc2.getBlockY()), Math.min(loc1.getBlockZ(), loc2.getBlockZ()));

        try {
            EditSession editSession = format.load(f).paste(FaweAPI.getWorld(loc1.getWorld().getName()), pos1, false, false, null);
            editSession.flushQueue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeLobby(MapManager mapManager) {
        final Location loc1 = mapManager.configManager.getArenaLoc(ConfigPath.ARENA_WAITING_POS1), loc2 = mapManager.configManager.getArenaLoc(ConfigPath.ARENA_WAITING_POS2);
        if (loc1 == null || loc2 == null) return;
        TaskManager.IMP.async(() -> {
            int minX = Math.min(loc1.getBlockX(), loc2.getBlockX()), maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
            int minY = Math.min(loc1.getBlockY(), loc2.getBlockY()), maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
            int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ()), maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
            AsyncWorld world = AsyncWorld.create(new WorldCreator(loc1.getWorld().getName()));
            for (int x = minX; x < maxX; x++) {
                for (int y = minY; y < maxY; y++) {
                    for (int z = minZ; z < maxZ; z++) {
                        world.getBlockAt(x, y, z).setType(Material.AIR);
                    }
                }
            }
            world.commit();
        });
    }

    @Override
    public void resetMap(MapManager mapManager) {
        TaskManager.IMP.async(() -> {
            AsyncWorld world = AsyncWorld.create(new WorldCreator(mapManager.getWorldName()));
            try {
                ResultSet rs = MapManager.connection.createStatement().executeQuery("SELECT x,y,z FROM '" + mapManager.table + "';");
                while (rs.next()) {
                    world.getBlockAt(rs.getInt(1), rs.getInt(2), rs.getInt(3)).setType(Material.AIR);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            world.commit();
            mapManager.clearTable();
        });
    }
}
