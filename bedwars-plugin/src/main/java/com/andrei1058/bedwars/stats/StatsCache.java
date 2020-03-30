package com.andrei1058.bedwars.stats;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

@SuppressWarnings("WeakerAccess")
public class StatsCache implements com.andrei1058.bedwars.api.BedWars.IStats {

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
        File folder = new File(BedWars.plugin.getDataFolder() + "/Cache");
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
                BedWars.plugin.getLogger().severe("Could Not Found SQLite Driver on your system!");
            e.printStackTrace();
        }
    }

    /**
     * Create cache table.
     */
    private void createTable() {
        if (!isConnected()) connect();

        String sql = "CREATE TABLE IF NOT EXISTS '" + table + "' (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(200), uuid VARCHAR(200), first_play TIMESTAMP NULL DEFAULT NULL, last_play TIMESTAMP NULL DEFAULT NULL, wins INTEGER(200)," +
                " kills INTEGER(200), final_kills INTEGER(200), looses INTEGER(200), deaths INTEGER(200), final_deaths INTEGER(200), beds_destroyed INTEGER(200), games_played INTEGER(200));";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
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

        String sql = "SELECT id FROM '" + table + "' WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                return result.next();
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

        String sql = "INSERT INTO '" + table + "' VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, player.getName());
            statement.setString(2, player.getUniqueId().toString());
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            statement.setInt(5, 0);
            statement.setInt(6, 0);
            statement.setInt(7, 0);
            statement.setInt(8, 0);
            statement.setInt(9, 0);
            statement.setInt(10, 0);
            statement.setInt(11, 0);
            statement.setInt(12, 0);
            statement.executeUpdate();
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

        String sql = "UPDATE '" + table + "' SET beds_destroyed = beds_destroyed + ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addFinalKill(UUID uuid, int amount) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET final_kills = final_kills + ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addKill(UUID uuid, int amount) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET kills = kills + ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addDeaths(UUID uuid, int amount) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET deaths = deaths + ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addFinalDeaths(UUID uuid, int amount) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET final_deaths = final_deaths + ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addWins(UUID uuid, int amount) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET wins = wins + ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addLosses(UUID uuid, int amount) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET looses = looses + ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add amount to existing cache.
     */
    public void addGamesPlayed(UUID uuid, int amount) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET games_played = games_played + ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get last play date.
     */
    public Timestamp getPlayerLastPlay(UUID uuid) {
        if (!isConnected()) connect();

        String sql = "SELECT last_play FROM '" + table + "' WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getTimestamp("last_play");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Timestamp(0);
    }

    /**
     * Get last play date.
     */
    public Timestamp getPlayerFirstPlay(UUID uuid) {
        if (!isConnected()) connect();

        String sql = "SELECT first_play FROM '" + table + "' WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getTimestamp("first_play");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Timestamp(0);
    }


    /**
     * Get cached kills.
     */
    public int getPlayerKills(UUID uuid) {
        if (!isConnected()) connect();

        String sql = "SELECT kills FROM '" + table + "' WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("kills");
                }
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

        String sql = "SELECT wins FROM '" + table + "' WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("wins");
                }
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

        String sql = "SELECT final_kills FROM '" + table + "' WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("final_kills");
                }
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

        String sql = "SELECT looses FROM '" + table + "' WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("looses");
                }
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

        String sql = "SELECT deaths FROM '" + table + "' WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("deaths");
                }
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

        String sql = "SELECT final_deaths FROM '" + table + "' WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("final_deaths");
                }
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

        String sql = "SELECT beds_destroyed FROM '" + table + "' WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("beds_destroyed");
                }
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

        String sql = "SELECT games_played FROM '" + table + "' WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("games_played");
                }
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

        String sql = "UPDATE '" + table + "' SET first_play = ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, time);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set last play.
     */
    public void setLastPlay(UUID uuid, Timestamp time) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET last_play = ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, time);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set wins.
     */
    public void setWins(UUID uuid, int value) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET wins = ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, value);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value.
     */
    public void setKills(UUID uuid, int value) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET kills = ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, value);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value.
     */
    public void setFinalKills(UUID uuid, int value) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET final_kills = ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, value);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value.
     */
    public void setLosses(UUID uuid, int value) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET looses = ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, value);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value.
     */
    public void setDeaths(UUID uuid, int value) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET deaths = ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, value);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value.
     */
    public void setFinalDeaths(UUID uuid, int value) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET final_deaths = ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, value);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value.
     */
    public void setBedsDestroyed(UUID uuid, int value) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET beds_destroyed = ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, value);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set value.
     */
    public void setGamesPlayed(UUID uuid, int value) {
        if (!isConnected()) connect();

        String sql = "UPDATE '" + table + "' SET games_played = ? WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, value);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update remote database.
     */
    public void updateRemote(UUID uuid, String username) {
        if (!isConnected()) connect();
        String sql = "SELECT * FROM '" + table + "' WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    BedWars.getRemoteDatabase().saveStats(uuid, username, result.getTimestamp("first_play"), new Timestamp(System.currentTimeMillis()), result.getInt("wins"),
                            result.getInt("kills"), result.getInt("final_kills"), result.getInt("looses"), result.getInt("deaths"),
                            result.getInt("final_deaths"), result.getInt("beds_destroyed"), result.getInt("games_played"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
