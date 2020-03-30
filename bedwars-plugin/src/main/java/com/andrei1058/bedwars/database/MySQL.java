package com.andrei1058.bedwars.database;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.stats.StatsCache;
import com.andrei1058.bedwars.stats.StatsManager;
import org.bukkit.Bukkit;

import javax.swing.plaf.nimbus.State;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.*;
import java.util.UUID;

import static com.andrei1058.bedwars.BedWars.config;

@SuppressWarnings("WeakerAccess")
public class MySQL implements Database {

    private Connection connection;
    private String host, database, user, pass;
    private int port;
    private boolean ssl;

    /**
     * Create new MySQL connection.
     */
    public MySQL() {
        this.host = config.getYml().getString("database.host");
        this.database = config.getYml().getString("database.database");
        this.user = config.getYml().getString("database.user");
        this.pass = config.getYml().getString("database.pass");
        this.port = config.getYml().getInt("database.port");
        this.ssl = config.getYml().getBoolean("database.ssl");
    }

    /**
     * Connect to remote database.
     *
     * @return true if connected successfully.
     */
    public boolean connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&user=" + user
                    + "&password=" + pass + "&useSSL=" + ssl + "&useUnicode=true&characterEncoding=UTF-8");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if database is connected.
     */
    public boolean isConnected() {
        if (connection == null) return false;
        try {
            return connection.isValid(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Check if player has remote stats.
     */
    public boolean hasStats(UUID uuid) {
        if (!isConnected()) connect();

        String sql = "SELECT id FROM global_stats WHERE uuid = ?;";
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

    @Override
    public void init() {
        if (!isConnected()) connect();

        String sql = "CREATE TABLE IF NOT EXISTS global_stats (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(200), uuid VARCHAR(200), first_play TIMESTAMP NULL DEFAULT NULL, " +
                "last_play TIMESTAMP NULL DEFAULT NULL, wins INT(200), kills INT(200), " +
                "final_kills INT(200), looses INT(200), deaths INT(200), final_deaths INT(200), beds_destroyed INT(200), games_played INT(200));";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "CREATE TABLE IF NOT EXISTS player_levels (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(200), " +
                "level INT(200), xp INT(200), name VARCHAR(200) CHARACTER SET utf8, next_cost INT(200));";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "CREATE TABLE IF NOT EXISTS player_language (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(200), iso VARCHAR(200));";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(BedWars.plugin, new SessionKeeper(this), 20*60, 20*3600);
    }

    @Override
    public void saveStats(UUID uuid, String username, Timestamp firstPlay, Timestamp lastPlay, int wins, int kills, int finalKills, int losses, int deaths, int finalDeaths, int bedsDestroyed, int gamesPlayed) {
        if (!isConnected()) connect();

        String sql;
        if (hasStats(uuid)) {
            sql = "UPDATE global_stats SET last_play=?, wins=?, kills=?, final_kills=?, looses=?, deaths=?, final_deaths=?, beds_destroyed=?, games_played=? WHERE uuid = ?;";
            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setTimestamp(1, lastPlay);
                statement.setInt(2, wins);
                statement.setInt(3, kills);
                statement.setInt(4, finalKills);
                statement.setInt(5, losses);
                statement.setInt(6, deaths);
                statement.setInt(7, finalDeaths);
                statement.setInt(8, bedsDestroyed);
                statement.setInt(9, gamesPlayed);
                statement.setString(10, uuid.toString());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            sql = "INSERT INTO global_stats VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, 0);
                statement.setString(2, username);
                statement.setString(3, uuid.toString());
                statement.setTimestamp(4, firstPlay);
                statement.setTimestamp(5, lastPlay);
                statement.setInt(6, wins);
                statement.setInt(7, kills);
                statement.setInt(8, finalKills);
                statement.setInt(9, losses);
                statement.setInt(10, deaths);
                statement.setInt(11, finalDeaths);
                statement.setInt(12, bedsDestroyed);
                statement.setInt(13, gamesPlayed);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateLocalCache(UUID uuid) {
        if (!isConnected()) connect();

        String sql = "SELECT * FROM global_stats WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    StatsCache cache = StatsManager.getStatsCache();
                    cache.setFirstPlay(uuid, result.getTimestamp("first_play"));
                    cache.setLastPlay(uuid, result.getTimestamp("last_play"));
                    cache.setWins(uuid, result.getInt("wins"));
                    cache.setKills(uuid, result.getInt("kills"));
                    cache.setFinalKills(uuid, result.getInt("final_kills"));
                    cache.setLosses(uuid, result.getInt("looses"));
                    cache.setDeaths(uuid, result.getInt("deaths"));
                    cache.setFinalDeaths(uuid, result.getInt("final_deaths"));
                    cache.setBedsDestroyed(uuid, result.getInt("beds_destroyed"));
                    cache.setGamesPlayed(uuid, result.getInt("games_played"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setQuickBuySlot(UUID uuid, String shopPath, int slot) {
        if (!isConnected()) connect();

        String sql = "SELECT id FROM quick_buy WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    sql = "INSERT INTO quick_buy VALUES(0, ?, ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');";
                    try(PreparedStatement statement2 = connection.prepareStatement(sql)) {
                        statement2.setString(1, uuid.toString());
                        statement2.executeUpdate();
                    }
                }
                BedWars.debug("UPDATE SET SLOT " + slot + " identifier " + shopPath);
                sql = "UPDATE quick_buy SET slot_" + slot + " = ? WHERE uuid = ?;";
                try(PreparedStatement statement2 = connection.prepareStatement(sql)) {
                    statement2.setString(1, shopPath);
                    statement2.setString(2, uuid.toString());
                    statement2.executeUpdate();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public String getQuickBuySlots(UUID uuid, int slot) {
        if (!isConnected()) connect();

        String sql = "SELECT slot_" + slot + " FROM quick_buy WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getString("slot_" + slot);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean hasQuickBuy(UUID uuid) {
        if (!isConnected()) connect();

        String sql = "SELECT id FROM quick_buy WHERE uuid = ?;";
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

    @Override
    public Object[] getLevelData(UUID uuid) {
        if (!isConnected()) connect();

        String sql = "SELECT level, xp, name, next_cost FROM player_levels WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new Object[]{
                            result.getInt("level"),
                            result.getInt("xp"),
                            result.getString("name"),
                            result.getInt("next_cost")
                    };
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Object[]{1, 0, "", 0};
    }

    @Override
    public void setLevelData(UUID uuid, int level, int xp, String displayName, int nextCost) {
        if (!isConnected()) connect();

        String sql = "SELECT id from player_levels WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    sql = "INSERT INTO player_levels VALUES (?, ?, ?, ?, ?, ?);";
                    try(PreparedStatement statement2 = connection.prepareStatement(sql)) {
                        statement2.setInt(1, 0);
                        statement2.setString(2, uuid.toString());
                        statement2.setInt(3, level);
                        statement2.setInt(4, xp);
                        statement2.setString(5, displayName);
                        statement2.setInt(6, nextCost);
                        statement2.executeUpdate();
                    }
                } else {
                    if (displayName == null) {
                        sql = "UPDATE player_levels SET level=?, xp=? WHERE uuid = ?;";
                    } else {
                        sql = "UPDATE player_levels SET level=?, xp=?, name=?, next_cost=? WHERE uuid = ?;";
                    }
                    try(PreparedStatement statement2 = connection.prepareStatement(sql)) {
                        statement2.setInt(1, level);
                        statement2.setInt(2, xp);
                        if (displayName != null) {
                            statement2.setString(3, displayName);
                            statement2.setInt(4, nextCost);
                            statement2.setString(5, uuid.toString());
                        } else {
                            statement2.setString(3, uuid.toString());
                        }
                        statement2.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLanguage(UUID uuid, String iso) {
        if (!isConnected()) connect();

        String sql = "SELECT iso FROM player_language WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if(result.next()) {
                    sql = "UPDATE player_language SET iso = ? WHERE uuid = ?;";
                    try(PreparedStatement statement2 = connection.prepareStatement(sql)) {
                        statement2.setString(1, iso);
                        statement2.setString(2, uuid.toString());
                        statement2.executeUpdate();
                    }
                } else {
                    sql = "INSERT INTO player_language VALUES (0, ?, ?);";
                    try(PreparedStatement statement2 = connection.prepareStatement(sql)) {
                        statement2.setString(1, uuid.toString());
                        statement2.setString(2, iso);
                        statement2.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLanguage(UUID uuid) {
        if (!isConnected()) connect();

        String sql = "SELECT iso FROM player_language WHERE uuid = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getString("iso");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Language.getDefaultLanguage().getIso();
    }

    /**
     * Ping the database in order to keep the session open.
     */
    public void ping() {
        try(Statement statement = connection.createStatement()) {
            statement.execute("SELECT id FROM player_levels WHERE id=0;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
