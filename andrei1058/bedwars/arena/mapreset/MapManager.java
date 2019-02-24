package com.andrei1058.bedwars.arena.mapreset;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class MapManager {

    private static MapResetter mapResetter = null;
    protected static Connection connection;

    protected String table;

    /**
     * Create a new map manager to cache placed blocks.
     */
    public MapManager(Arena arena) {
        this.table = arena.getWorldName();
        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS '" + table + "' (x INTEGER, y INTEGER, z INTEGER);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a placed block to cache.
     */
    public void addPlacedBlock(int x, int y, int z) {
        try {
            connection.createStatement().executeUpdate("INSERT INTO '" + table + "' VALUES(" + x + ", " + y + ", " + z + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Truncate table.
     */
    public void clearTable() {
        try {
            connection.createStatement().executeUpdate("DROP TABLE '" + table + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS '" + table + "' (x INTEGER, y INTEGER, z INTEGER);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restore map
     */
    public void restoreMap() {
        mapResetter.resetMap(this);
    }

    /**
     * Initialize on plugin load before loading arenas.
     *
     * @return false if something went wrong.
     */
    public static boolean init() {
        if (Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit") != null) {
            if (Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit").isEnabled()) {
                mapResetter = new FaweReset();
            }
        }
        if (mapResetter == null) mapResetter = new InternalReset();

        File folder = new File(Main.plugin.getDataFolder() + "/Cache");
        if (!folder.exists()) folder.mkdir();
        File dataFolder = new File(folder.getPath() + "/placedBlocks.db");
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ClassLoader cl = Bukkit.getServer().getClass().getClassLoader();
            Driver d = (Driver) cl.loadClass("org.sqlite.JDBC").newInstance();
            DriverManager.registerDriver(d);
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            if (e instanceof ClassNotFoundException)
                Main.plugin.getLogger().severe("Could Not Found SQLite Driver on your system!");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
