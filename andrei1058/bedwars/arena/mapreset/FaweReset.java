package com.andrei1058.bedwars.arena.mapreset;

import com.andrei1058.bedwars.Main;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.boydti.fawe.util.TaskManager;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FaweReset implements MapResetter {

    @Override
    public void resetMap(MapManager mapManager) {
        Bukkit.getScheduler().runTask(Main.plugin, () -> {
            EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(mapManager.table)).fastmode(true).build();
            try {
                ResultSet rs = MapManager.connection.createStatement().executeQuery("SELECT x,y,z FROM '" + mapManager.table + "';");
                while (rs.next()) {
                    editSession.setBlock(new Vector(rs.getInt(1), rs.getInt(2), rs.getInt(3)), BlockTypes.AIR.getDefaultState());
                }
                editSession.flushQueue();
                Bukkit.getWorld(mapManager.table).save();
                mapManager.clearTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        Bukkit.getScheduler().runTask(Main.plugin, () -> {
            EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(mapManager.table2)).fastmode(true).build();
            try {
                ResultSet rs = MapManager.connection.createStatement().executeQuery("SELECT x,y,z,material,data FROM '" + mapManager.table + "';");
                while (rs.next()) {
                    editSession.setBlock(new Vector(rs.getInt(1), rs.getInt(2), rs.getInt(3)), BlockTypes.valueOf(rs.getString(4)));
                }
                editSession.flushQueue();
                Bukkit.getWorld(mapManager.table).save();
                mapManager.clearTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
