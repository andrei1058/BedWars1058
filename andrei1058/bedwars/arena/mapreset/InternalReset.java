package com.andrei1058.bedwars.arena.mapreset;

import com.andrei1058.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InternalReset implements MapResetter {

    @Override
    public void removeEntities() {

    }

    @Override
    public void resetMap(MapManager mapManager) {
        final World w = Bukkit.getWorld(mapManager.table);
        Bukkit.getScheduler().runTask(Main.plugin, () -> {
            try {
                ResultSet rs = MapManager.connection.createStatement().executeQuery("SELECT x,y,z FROM '" + mapManager.table + "';");
                while (rs.next()) {
                    w.getBlockAt(rs.getInt(1), rs.getInt(2), rs.getInt(3)).setType(Material.AIR);
                }
                mapManager.clearTable();
                w.save();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
