package com.andrei1058.bedwars.stats;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

@SuppressWarnings("WeakerAccess")
public class StatsCache implements BedWars.IStats {

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
            @SuppressWarnings("deprecation") Driver d = (Driver) cl.loadClass("org.sqlite.JDBC").newInstance();
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
                    " kills INTEGER(200), final_kills INTEGER(200), looses INTEGER(200), deaths INTEGER(200), final_deaths INTEGER(200), beds_destroyed INTEGER(200), games_played INTEGER(200));");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Check if database is connected.
     */
    @SuppressWarnings("WeakerAccess")
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
    @SuppressWarnings("UnusedReturnValue")
    public boolean createStatsCache(Player player) {
        if (isPlayerSet(player.getUniqueId())) return false;
        if (!isConnected()) connect();

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO '" + table + "' VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
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
    public void addBedsDestroyed(UUID uuid, int amount) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET beds_destroyed = beds_destroyed + '" + amount + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addFinalKill(UUID uuid, int amount) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET final_kills = final_kills + '" + amount + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addKill(UUID uuid, int amount) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET kills = kills + '" + amount + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addDeaths(UUID uuid, int amount) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET deaths = deaths + '" + amount + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addFinalDeaths(UUID uuid, int amount) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET final_deaths = final_deaths + '" + amount + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addWins(UUID uuid, int amount) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET wins = wins + '" + amount + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addLosses(UUID uuid, int amount) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET looses = looses + '" + amount + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addGamesPlayed(UUID uuid, int amount) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET games_played = games_played + '" + amount + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get last play date.
     */
    public Timestamp getPlayerLastPlay(UUID uuid) {
        if (!isConnected()) connect();

        Timestamp t = new Timestamp(0);
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT last_play FROM '" + table + "' WHERE uuid = '" + uuid.toString() + "';");
            if (rs.next()) {
                t = rs.getTimestamp("last_play");
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * Get last play date.
     */
    public Timestamp getPlayerFirstPlay(UUID uuid) {
        if (!isConnected()) connect();

        Timestamp t = new Timestamp(0);
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT first_play FROM '" + table + "' WHERE uuid = '" + uuid.toString() + "';");
            if (rs.next()) {
                t = rs.getTimestamp("first_play");
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }


    /**
     * Get cached kills.
     */
    public int getPlayerKills(UUID uuid) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT kills FROM '" + table + "' WHERE uuid = '" + uuid.toString() + "';");
            if (rs.next()) {
                int i = rs.getInt("kills");
                rs.close();
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get cached wins.
     */
    public int getPlayerWins(UUID uuid) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT wins FROM '" + table + "' WHERE uuid='" + uuid.toString() + "';");
            if (rs.next()) {
                int i = rs.getInt("wins");
                rs.close();
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get cached value.
     */
    public int getPlayerFinalKills(UUID uuid) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT final_kills FROM '" + table + "' WHERE uuid='" + uuid.toString() + "';");
            if (rs.next()) {
                int i = rs.getInt("final_kills");
                rs.close();
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get cached value.
     */
    public int getPlayerLoses(UUID uuid) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT looses FROM '" + table + "' WHERE uuid='" + uuid.toString() + "';");
            if (rs.next()) {
                int i = rs.getInt("looses");
                rs.close();
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get cached value.
     */
    public int getPlayerDeaths(UUID uuid) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT deaths FROM '" + table + "' WHERE uuid='" + uuid.toString() + "';");
            if (rs.next()) {
                int i = rs.getInt("deaths");
                rs.close();
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get cached value.
     */
    public int getPlayerFinalDeaths(UUID uuid) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT final_deaths FROM '" + table + "' WHERE uuid='" + uuid.toString() + "';");
            if (rs.next()) {
                int i = rs.getInt("final_deaths");
                rs.close();
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get cached value.
     */
    public int getPlayerBedsDestroyed(UUID uuid) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT beds_destroyed FROM '" + table + "' WHERE uuid='" + uuid.toString() + "';");
            if (rs.next()) {
                int i = rs.getInt("beds_destroyed");
                rs.close();
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get cached value.
     */
    public int getPlayerGamesPlayed(UUID uuid) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT games_played FROM '" + table + "' WHERE uuid='" + uuid.toString() + "';");
            if (rs.next()) {
                int i = rs.getInt("games_played");
                rs.close();
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Set first play.
     */
    public void setFirstPlay(UUID uuid, Timestamp time) {
        if (!isConnected()) connect();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE '" + table + "' SET first_play = ? WHERE uuid = '" + uuid.toString() + "';");
            ps.setTimestamp(1, time);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set last play.
     */
    public void setLastPlay(UUID uuid, Timestamp time) {
        if (!isConnected()) connect();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE '" + table + "' SET last_play = ? WHERE uuid = '" + uuid.toString() + "';");
            ps.setTimestamp(1, time);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set wins.
     */
    public void setWins(UUID uuid, int value) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET wins = '" + value + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value.
     */
    public void setKills(UUID uuid, int value) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET kills = '" + value + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value.
     */
    public void setFinalKills(UUID uuid, int value) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET final_kills = '" + value + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value.
     */
    public void setLosses(UUID uuid, int value) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET looses = '" + value + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value.
     */
    public void setDeaths(UUID uuid, int value) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET deaths = '" + value + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value.
     */
    public void setFinalDeaths(UUID uuid, int value) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET final_deaths = '" + value + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value.
     */
    public void setBedsDestroyed(UUID uuid, int value) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET beds_destroyed = '" + value + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value.
     */
    public void setGamesPlayed(UUID uuid, int value) {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("UPDATE '" + table + "' SET games_played = '" + value + "' WHERE uuid = '" + uuid.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update remote database.
     */
    public void updateRemote(UUID uuid, String username) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM '" + table + "' WHERE uuid = '" + uuid.toString() + "';");
            if (rs.next()) {
                Main.getRemoteDatabase().saveStats(uuid, username, rs.getTimestamp("first_play"), new Timestamp(System.currentTimeMillis()), rs.getInt("wins"),
                        rs.getInt("kills"), rs.getInt("final_kills"), rs.getInt("looses"), rs.getInt("deaths"), rs.getInt("final_deaths"),
                        rs.getInt("beds_destroyed"), rs.getInt("games_played"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
