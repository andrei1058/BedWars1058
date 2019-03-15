package com.andrei1058.bedwars.arena.mapreset;

import com.andrei1058.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InternalReset implements MapResetter {

    @SuppressWarnings("deprecation")
    @Override
    public void resetMap(MapManager mapManager) {
        final World w = Bukkit.getWorld(mapManager.table.replace("_placed", ""));
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
        Bukkit.getScheduler().runTaskLater(Main.plugin, mapManager::clearTable, 100L);
    }
}
