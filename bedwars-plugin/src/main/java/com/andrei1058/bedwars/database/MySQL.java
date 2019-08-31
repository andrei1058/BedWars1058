package com.andrei1058.bedwars.database;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.stats.StatsCache;
import com.andrei1058.bedwars.stats.StatsManager;

import java.sql.*;
import java.util.UUID;

import static com.andrei1058.bedwars.Main.config;

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
            return !connection.isClosed();
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
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT id FROM global_stats WHERE uuid = '" + uuid.toString() + "';");
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void init() {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS global_stats (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(200), uuid VARCHAR(200), first_play TIMESTAMP NULL DEFAULT NULL, " +
                    "last_play TIMESTAMP NULL DEFAULT NULL, wins INT(200), kills INT(200), " +
                    "final_kills INT(200), looses INT(200), deaths INT(200), final_deaths INT(200), beds_destroyed INT(200), games_played INT(200));");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS quick_buy (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(200), " +
                    "slot_19 VARCHAR(200), slot_20 VARCHAR(200), slot_21 VARCHAR(200), slot_22 VARCHAR(200), slot_23 VARCHAR(200), slot_24 VARCHAR(200), slot_25 VARCHAR(200)," +
                    "slot_28 VARCHAR(200), slot_29 VARCHAR(200), slot_30 VARCHAR(200), slot_31 VARCHAR(200), slot_32 VARCHAR(200), slot_33 VARCHAR(200), slot_34 VARCHAR(200)," +
                    "slot_37 VARCHAR(200), slot_38 VARCHAR(200), slot_39 VARCHAR(200), slot_40 VARCHAR(200), slot_41 VARCHAR(200), slot_42 VARCHAR(200), slot_43 VARCHAR(200));");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS player_levels (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(200), " +
                    "level INT(200), xp INT(200), name VARCHAR(200) CHARACTER SET utf8, next_cost INT(200));");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS player_language (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(200), " +
                    "iso VARCHAR(200));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveStats(UUID uuid, String username, Timestamp firstPlay, Timestamp lastPlay, int wins, int kills, int finalKills, int losses, int deaths, int finalDeaths, int bedsDestroyed, int gamesPlayed) {
        if (!isConnected()) connect();
        if (hasStats(uuid)) {
            try {
                PreparedStatement ps = connection.prepareStatement("UPDATE global_stats SET last_play=?, wins=?, kills=?, final_kills=?, looses=?, deaths=?, final_deaths=?, beds_destroyed=?, games_played=? WHERE uuid = '" + uuid.toString() + "';");
                ps.setTimestamp(1, lastPlay);
                ps.setInt(2, wins);
                ps.setInt(3, kills);
                ps.setInt(4, finalKills);
                ps.setInt(5, losses);
                ps.setInt(6, deaths);
                ps.setInt(7, finalDeaths);
                ps.setInt(8, bedsDestroyed);
                ps.setInt(9, gamesPlayed);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO global_stats VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                ps.setInt(1, 0);
                ps.setString(2, username);
                ps.setString(3, uuid.toString());
                ps.setTimestamp(4, firstPlay);
                ps.setTimestamp(5, lastPlay);
                ps.setInt(6, wins);
                ps.setInt(7, kills);
                ps.setInt(8, finalKills);
                ps.setInt(9, losses);
                ps.setInt(10, deaths);
                ps.setInt(11, finalDeaths);
                ps.setInt(12, bedsDestroyed);
                ps.setInt(13, gamesPlayed);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateLocalCache(UUID uuid) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM global_stats WHERE uuid = '" + uuid.toString() + "';");
            if (rs.next()) {
                StatsCache cs = StatsManager.getStatsCache();
                cs.setFirstPlay(uuid, rs.getTimestamp("first_play"));
                cs.setLastPlay(uuid, rs.getTimestamp("last_play"));
                cs.setWins(uuid, rs.getInt("wins"));
                cs.setKills(uuid, rs.getInt("kills"));
                cs.setFinalKills(uuid, rs.getInt("final_kills"));
                cs.setLosses(uuid, rs.getInt("looses"));
                cs.setDeaths(uuid, rs.getInt("deaths"));
                cs.setFinalKills(uuid, rs.getInt("final_deaths"));
                cs.setBedsDestroyed(uuid, rs.getInt("beds_destroyed"));
                cs.setGamesPlayed(uuid, rs.getInt("games_played"));
            }
            rs.close();
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
    public void setQuickBuySlot(UUID p, String shopPath, int slot) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.prepareStatement("SELECT id FROM quick_buy WHERE uuid = '" + p.toString() + "';").executeQuery();
            if (!rs.next()) {
                connection.prepareStatement("INSERT INTO quick_buy VALUES(0,'" + p.toString() + "',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ');").executeUpdate();
            }
            Main.debug("UPDATE SET SLOT " + slot + " identifier " + shopPath);
            connection.prepareStatement("UPDATE quick_buy SET slot_" + slot + " = '" + shopPath + "' WHERE uuid = '" + p.toString() + "';").executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public String getQuickBuySlots(UUID p, int slot) {
        String result = "";
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.prepareStatement("SELECT slot_" + slot + " FROM quick_buy WHERE uuid = '" + p.toString() + "';").executeQuery();
            if (rs.next()) result = rs.getString("slot_" + slot);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean hasQuickBuy(UUID uuid) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.prepareStatement("SELECT id FROM quick_buy WHERE uuid = '" + uuid.toString() + "';").executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Object[] getLevelData(UUID player) {
        if (!isConnected()) connect();
        Object[] r = new Object[] {1, 0, "", 0};
        try {
            ResultSet rs = connection.prepareStatement("SELECT level, xp, name, next_cost FROM player_levels WHERE uuid = '"+player.toString()+"';").executeQuery();
            if (rs.next()){
                r[0] = rs.getInt("level");
                r[1] = rs.getInt("xp");
                r[2] = rs.getString("name");
                r[3] = rs.getInt("next_cost");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public void setLevelData(UUID player, int level, int xp, String displayName, int nextCost) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.prepareStatement("SELECT id from player_levels WHERE uuid = '"+player.toString()+"';").executeQuery();
            if (!rs.next()){
                PreparedStatement ps = connection.prepareStatement("INSERT INTO player_levels VALUES (?, ?, ?, ?, ?, ?);");
                ps.setInt(1, 0);
                ps.setString(2, player.toString());
                ps.setInt(3, level);
                ps.setInt(4, xp);
                ps.setString(5, displayName);
                ps.setInt(6, nextCost);
                ps.executeUpdate();
            } else {
                PreparedStatement ps;
                if (displayName == null){
                    ps = connection.prepareStatement("UPDATE player_levels SET level=?, xp=? WHERE uuid = '"+player.toString()+"';");
                } else {
                    ps = connection.prepareStatement("UPDATE player_levels SET level=?, xp=?, name=?, next_cost=? WHERE uuid = '"+player.toString()+"';");
                }
                ps.setInt(1, level);
                ps.setInt(2, xp);
                if (displayName != null) {
                    ps.setString(3, displayName);
                    ps.setInt(4, nextCost);
                }
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLanguage(UUID player, String iso) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT iso FROM player_language WHERE uuid = '" + player.toString() + "';");
            if (rs.next()) {
                connection.createStatement().executeUpdate("UPDATE player_language SET iso='" + iso + "' WHERE uuid = '" + player.toString() + "';");
            } else {
                connection.createStatement().executeUpdate("INSERT INTO player_language VALUES (0, '" + player.toString() + "', '" + iso + "');");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLanguage(UUID player) {
        if (!isConnected()) connect();
        String iso = Main.lang.getIso();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT iso FROM player_language WHERE uuid = '" + player.toString() + "';");
            if (rs.next()) {
                iso = rs.getString("iso");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return iso;
    }
}
