package com.andrei1058.bedwars.arena.mapreset;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InternalReset implements MapResetter {

    @SuppressWarnings("deprecation")
    @Override
    public void resetMap(MapManager mapManager) {
        final World w = Bukkit.getWorld(mapManager.getWorldName());
        Bukkit.getScheduler().runTask(Main.plugin, () -> {
            try {
                ResultSet rs = MapManager.connection.createStatement().executeQuery("SELECT x,y,z FROM '" + mapManager.table + "';");
                while (rs.next()) {
                    w.getBlockAt(rs.getInt(1), rs.getInt(2), rs.getInt(3)).setType(Material.AIR);
                }
                w.save();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        Bukkit.getScheduler().runTaskLater(Main.plugin, mapManager::clearTable, 100L);
    }

    @Override
    public void backupLobby(Location loc1, Location loc2) {

    }

    @Override
    public void restoreLobby(MapManager mapManager) {
        final World w = Bukkit.getWorld(mapManager.getWorldName());
        Bukkit.getScheduler().runTask(Main.plugin, () -> {
            try {
                ResultSet rs = MapManager.connection.createStatement().executeQuery("SELECT x,y,z,material,data FROM '" + mapManager.table2 + "';");
                Block b;
                while (rs.next()) {
                    b = w.getBlockAt(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                    try {
                        b.setType(Material.valueOf(rs.getString(4)));
                    } catch (Exception e) {
                        try {
                            b.setType(Material.valueOf("LEGACY_" + rs.getString(4)));
                        } catch (Exception ex) {
                            e.printStackTrace();
                        }
                    }
                    b.getState().update(true);
                    if (!rs.getString(5).isEmpty()) Main.nms.setBlockData(b, rs.getString(5));
                }
                w.save();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        Bukkit.getScheduler().runTaskLater(Main.plugin, mapManager::clearTable2, 100L);
    }

    @Override
    public void removeLobby(MapManager mapManager) {
        final Location loc1 = mapManager.configManager.getArenaLoc(ConfigPath.ARENA_WAITING_POS1), loc2 = mapManager.configManager.getArenaLoc(ConfigPath.ARENA_WAITING_POS2);
        if (loc1 == null || loc2 == null) return;
        Bukkit.getScheduler().runTask(Main.plugin, ()-> {
            int minX = Math.min(loc1.getBlockX(), loc2.getBlockX()), maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
            int minY = Math.min(loc1.getBlockY(), loc2.getBlockY()), maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
            int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ()), maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
            final World w = Bukkit.getWorld(mapManager.configManager.getName());
            for (int x = minX; x < maxX; x++) {
                for (int y = minY; y < maxY; y++) {
                    for (int z = minZ; z < maxZ; z++) {
                        Block b = new Location(w, x, y, z).getBlock();
                        if (b.getType() != Material.AIR) {
                            mapManager.addRemovedBlock(b);
                            b.setType(Material.AIR);
                        }
                    }
                }
            }
            //remove items dropped from lobby
            w.getEntities().stream().filter(e -> e.getType() == EntityType.DROPPED_ITEM).forEach(Entity::remove);
        });
    }
}
