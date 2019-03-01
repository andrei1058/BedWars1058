package com.andrei1058.bedwars.stats;

import com.andrei1058.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class StatsCache {

    private Connection connection;
    private String table = "stats_cache";

    public StatsCache() {
        connect();
        createTable();
    }

    /**
     * Connect to database.
     */
    private void connect() {
        File folder = new File(Main.plugin.getDataFolder() + "/Cache");
        if (!folder.exists()) folder.mkdir();
        File dataFolder = new File(folder.getPath() + "/stats.db");
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
        }
    }

    /**
     * Create cache table.
     */
    private void createTable() {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS '" + table + "' (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name VARCHAR(200), uuid VARCHAR(200), first_play TIMESTAMP NULL DEFAULT NULL, last_play TIMESTAMP NULL DEFAULT NULL, wins INTEGER(200)," +
                    " kills INTEGER(200), final_kills INTEGER(200), looses INTEGER(200), deaths INTEGER(200), final_deaths INTEGER(200), beds_destroyed INTEGER(200), games_played INTEGER(200))");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Check if database is connected.
     */
    public boolean isConnected() {
        if (connection == null) return false;
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Close connection.
     */
    public void close() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Check if player is contained in table.
     */
    public boolean isPlayerSet(UUID uuid) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT id FROM '" + table + "' WHERE uuid = '" + uuid.toString() + "';");
            if (rs.next()) {
                rs.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Create stats cache for given player.
     *
     * @return true if the player did not have cache and it was created.
     */
    public boolean createStatsCache(Player player) {
        if (isPlayerSet(player.getUniqueId())) return false;
        if (!isConnected()) connect();

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO global_stats VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1, player.getName());
            ps.setString(2, player.getUniqueId().toString());
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.setInt(5, 0);
            ps.setInt(6, 0);
            ps.setInt(7, 0);
            ps.setInt(8, 0);
            ps.setInt(9, 0);
            ps.setInt(10, 0);
            ps.setInt(11, 0);
            ps.setInt(12, 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Add amount to existing cache.
     */
    public void addBedsDestroyed(Player player, int amount) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET beds_destroyed = beds_destroyed + '" + amount + "' WHERE uuid = '" + player.getUniqueId().toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addFinalKill(Player player, int amount) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET final_kills = final_kills + '" + amount + "' WHERE uuid = '" + player.getUniqueId().toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addKill(Player player, int amount) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET kills = kills + '" + amount + "' WHERE uuid = '" + player.getUniqueId().toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addDeaths(Player player, int amount) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET deaths = deaths + '" + amount + "' WHERE uuid = '" + player.getUniqueId().toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addFinalDeaths(Player player, int amount) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET final_deaths = final_deaths + '" + amount + "' WHERE uuid = '" + player.getUniqueId().toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get last play date.
     */
    public Timestamp getLastPlay(Player player) {
        if (!isConnected()) connect();

        Timestamp t = new Timestamp(0);
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT last_play FROM '" + table + "' WHERE uuid = '" + player.getUniqueId().toString() + "';");
            if (rs.next()) {
                t = rs.getTimestamp("last_play");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * Get last play date.
     */
    public Timestamp getFirstPlay(Player player) {
        if (!isConnected()) connect();

        Timestamp t = new Timestamp(0);
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT first_play FROM '" + table + "' WHERE uuid = '" + player.getUniqueId().toString() + "';");
            if (rs.next()) {
                t = rs.getTimestamp("first_play");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }


    /**
     * Get cached kills.
     */
    public int getKills(Player p) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT kills FROM '" + table + "' WHERE uuid = '" + p.getUniqueId().toString() + "';");
            if (rs.next())
                return rs.getInt("kills");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** */
    public int getWins(Player p) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT wins FROM '" + table + "' WHERE uuid='" + p.getUniqueId().toString() + "';");
            if (rs.next())
                return rs.getInt("wins");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Update remote database with given
     */
    public void updateRemote(Player player) {
        //Main.database.saveStats(getLastPlay(player));
    }
}
