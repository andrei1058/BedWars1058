package com.andrei1058.bedwars.arena.mapreset;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.configuration.ConfigManager;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class MapManager {

    private static MapResetter mapResetter = null;
    protected static Connection connection;

    protected String table, table2;
    protected ConfigManager configManager;

    /**
     * Create a new map manager to cache placed blocks.
     */
    public MapManager(ConfigManager configManager) {
        this.configManager = configManager;
        this.table = configManager.getName() + "_placed";
        this.table2 = configManager.getName() + "_removed";
        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS '" + table + "' (x INTEGER, y INTEGER, z INTEGER);");
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS '" + table2 + "' (x INTEGER, y INTEGER, z INTEGER, material VARCHAR, data VARCHAR);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Backup lobby.
     */
    public void backupLobby() {
        mapResetter.backupLobby(configManager.getArenaLoc(ConfigPath.ARENA_WAITING_POS1), configManager.getArenaLoc(ConfigPath.ARENA_WAITING_POS2));
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
     * Add removed block.
     * Used for lobby reset.
     */
    @SuppressWarnings("deprecation")
    public void addRemovedBlock(Block b) {
        //todo keep trace:
        //MultipleFacing, Rotatable, Stairs
        try {
            connection.createStatement().execute("INSERT INTO '" + table2 + "' VALUES ('" + b.getX() + "', '" + b.getY() + "', '" + b.getZ() + "', '" + b.getType().toString() + "', '" + Main.nms.getBlockData(b) + "');");
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
     * Truncate table.
     */
    public void clearTable2() {
        try {
            connection.createStatement().executeUpdate("DROP TABLE '" + table2 + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS '" + table2 + "' (x INTEGER, y INTEGER, z INTEGER, material VARCHAR, data VARCHAR);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restore map.
     */
    public void restoreMap() {
        mapResetter.resetMap(this);
        mapResetter.restoreLobby(this);
    }

    /**
     * Get world name.
     */
    public String getWorldName() {
        return configManager.getName();
    }

    /**
     * Initialize on plugin loadStructure before loading arenas.
     *
     * @return false if something went wrong.
     */
    public static boolean init() {
        if (Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit") != null) {
            if (Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit").isEnabled()) {
                mapResetter = new FaweReset();
                Main.plugin.getLogger().info("Hook into FastAsyncWorldEdit support!");
            }
        }
        if (mapResetter == null) mapResetter = new InternalReset();

        File folder = new File(Main.plugin.getDataFolder() + "/Cache");
        if (!folder.exists()) folder.mkdir();
        File dataFolder = new File(folder.getPath() + "/blocks.db");
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

    /**
     * Close blocks cache connection.
     */
    public static void closeConnection() {
        if (connection == null) return;
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MapResetter getMapResetter() {
        return mapResetter;
    }
}
