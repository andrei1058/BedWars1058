package com.andrei1058.bedwars.database;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.stats.PlayerStats;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class SQLite implements Database {

    private Connection connection;

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

    @Override
    public void init() {
        File folder = new File(BedWars.plugin.getDataFolder() + "/Cache");
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                BedWars.plugin.getLogger().severe("Could not create /Cache folder!");
            }
        }
        File dataFolder = new File(folder.getPath() + "/shop.db");
        if (!dataFolder.exists()) {
            try {
                if (!dataFolder.createNewFile()) {
                    BedWars.plugin.getLogger().severe("Could not create /Cache/shop.db file!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            ClassLoader cl = Bukkit.getServer().getClass().getClassLoader();
            Driver d = (Driver) cl.loadClass("org.sqlite.JDBC").newInstance();
            DriverManager.registerDriver(d);
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            if (e instanceof ClassNotFoundException)
                BedWars.plugin.getLogger().severe("Could Not Found SQLite Driver on your system!");
            e.printStackTrace();
            return;
        }

        String sql = "CREATE TABLE IF NOT EXISTS global_stats (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(200), uuid VARCHAR(36), first_play TIMESTAMP NULL DEFAULT NULL, " +
                "last_play TIMESTAMP DEFAULT NULL, wins INTEGER(10), kills INTEGER(10), " +
                "final_kills INTEGER(10), looses INTEGER(10), deaths INTEGER(10), final_deaths INTEGER(10), beds_destroyed INTEGER(10), games_played INTEGER(10));";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Statement st = connection.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS quick_buy (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid VARCHAR(200), " +
                    "slot_19 VARCHAR(200), slot_20 VARCHAR(200), slot_21 VARCHAR(200), slot_22 VARCHAR(200), slot_23 VARCHAR(200), slot_24 VARCHAR(200), slot_25 VARCHAR(200)," +
                    "slot_28 VARCHAR(200), slot_29 VARCHAR(200), slot_30 VARCHAR(200), slot_31 VARCHAR(200), slot_32 VARCHAR(200), slot_33 VARCHAR(200), slot_34 VARCHAR(200)," +
                    "slot_37 VARCHAR(200), slot_38 VARCHAR(200), slot_39 VARCHAR(200), slot_40 VARCHAR(200), slot_41 VARCHAR(200), slot_42 VARCHAR(200), slot_43 VARCHAR(200));");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Statement st = connection.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS player_levels (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid VARCHAR(200), " +
                    "level INTEGER, xp INTEGER, name VARCHAR(200), next_cost INTEGER);");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Statement st = connection.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS  player_language (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid VARCHAR(200), " +
                    "iso VARCHAR(200));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasStats(UUID uuid) {
        if (!isConnected()) init();

        String sql = "SELECT id FROM global_stats WHERE uuid = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void saveStats(PlayerStats stats) {
        if (!isConnected()) init();

        String sql;
        if (hasStats(stats.getUuid())) {
            sql = "UPDATE global_stats SET last_play=?, wins=?, kills=?, final_kills=?, looses=?, deaths=?, final_deaths=?, beds_destroyed=?, games_played=?, name=? WHERE uuid = ?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setTimestamp(1, Timestamp.from(stats.getLastPlay()));
                statement.setInt(2, stats.getWins());
                statement.setInt(3, stats.getKills());
                statement.setInt(4, stats.getFinalKills());
                statement.setInt(5, stats.getLosses());
                statement.setInt(6, stats.getDeaths());
                statement.setInt(7, stats.getFinalDeaths());
                statement.setInt(8, stats.getBedsDestroyed());
                statement.setInt(9, stats.getGamesPlayed());
                statement.setString(10, stats.getName());
                statement.setString(11, stats.getUuid().toString());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            sql = "INSERT INTO global_stats VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, 0);
                statement.setString(2, stats.getName());
                statement.setString(3, stats.getUuid().toString());
                statement.setTimestamp(4, Timestamp.from(stats.getFirstPlay()));
                statement.setTimestamp(5, Timestamp.from(stats.getLastPlay()));
                statement.setInt(6, stats.getWins());
                statement.setInt(7, stats.getKills());
                statement.setInt(8, stats.getFinalKills());
                statement.setInt(9, stats.getLosses());
                statement.setInt(10, stats.getDeaths());
                statement.setInt(11, stats.getFinalDeaths());
                statement.setInt(12, stats.getBedsDestroyed());
                statement.setInt(13, stats.getGamesPlayed());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public PlayerStats fetchStats(UUID uuid) {
        if (!isConnected()) init();
        PlayerStats stats = new PlayerStats(uuid);

        String sql = "SELECT * FROM global_stats WHERE uuid = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    stats.setFirstPlay(result.getTimestamp("first_play").toInstant());
                    stats.setLastPlay(result.getTimestamp("last_play").toInstant());
                    stats.setWins(result.getInt("wins"));
                    stats.setKills(result.getInt("kills"));
                    stats.setFinalKills(result.getInt("final_kills"));
                    stats.setLosses(result.getInt("looses"));
                    stats.setDeaths(result.getInt("deaths"));
                    stats.setFinalDeaths(result.getInt("final_deaths"));
                    stats.setBedsDestroyed(result.getInt("beds_destroyed"));
                    stats.setGamesPlayed(result.getInt("games_played"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
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
        if (!isConnected()) init();
        try (PreparedStatement statement = connection.prepareStatement("SELECT id FROM quick_buy WHERE uuid = ?;")) {
            statement.setString(1, p.toString());
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) {
                    try (PreparedStatement ps = connection.prepareStatement("INSERT INTO quick_buy (uuid, slot_19, slot_20, slot_21, slot_22, slot_23, slot_24, slot_25, slot_28, slot_29, slot_30, slot_31, slot_32, slot_33, slot_34, slot_37, slot_38, slot_39, slot_40, slot_41, slot_42, slot_43) VALUES(?,' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ');")) {
                        ps.setString(1, p.toString());
                        ps.execute();
                    }
                }
                BedWars.debug("UPDATE SET SLOT " + slot + " identifier " + shopPath);
                try (PreparedStatement ps = connection.prepareStatement("UPDATE quick_buy SET slot_" + slot + " = '?' WHERE uuid = ?;")) {
                    ps.setString(1, shopPath);
                    ps.setString(2, p.toString());
                    ps.executeUpdate();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getQuickBuySlots(UUID p, int slot) {
        String result = "";
        if (!isConnected()) init();
        try (PreparedStatement ps = connection.prepareStatement("SELECT slot_" + slot + " FROM quick_buy WHERE uuid = ?;")) {
            ps.setString(1, p.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) result = rs.getString("slot_" + slot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean hasQuickBuy(UUID uuid) {
        if (!isConnected()) init();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT id FROM quick_buy WHERE uuid = '" + uuid.toString() + "';")) {
                if (rs.next()) {
                    rs.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Object[] getLevelData(UUID player) {
        if (!isConnected()) init();
        Object[] r = new Object[]{1, 0, "", 0};
        try (PreparedStatement ps = connection.prepareStatement("SELECT level, xp, name, next_cost FROM player_levels WHERE uuid = ?;")) {
            ps.setString(1, player.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    r[0] = rs.getInt("level");
                    r[1] = rs.getInt("xp");
                    r[2] = rs.getString("name");
                    r[3] = rs.getInt("next_cost");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public void setLevelData(UUID player, int level, int xp, String displayName, int nextCost) {
        if (!isConnected()) init();
        try (PreparedStatement pss = connection.prepareStatement("SELECT id from player_levels WHERE uuid = ?;")) {
            pss.setString(1, player.toString());
            try (ResultSet rs = pss.executeQuery()) {
                if (!rs.next()) {
                    try (PreparedStatement ps = connection.prepareStatement("INSERT INTO player_levels (uuid, level, xp, name, next_cost) VALUES (?, ?, ?, ?, ?);")) {
                        //ps.setInt(1, 0);
                        ps.setString(1, player.toString());
                        ps.setInt(2, level);
                        ps.setInt(3, xp);
                        ps.setString(4, displayName);
                        ps.setInt(5, nextCost);
                        ps.executeUpdate();
                    }
                } else {
                    try (PreparedStatement ps = displayName == null ? connection.prepareStatement("UPDATE player_levels SET level=?, xp=? WHERE uuid = '" + player.toString() + "';") : connection.prepareStatement("UPDATE player_levels SET level=?, xp=?, name=?, next_cost=? WHERE uuid = '" + player.toString() + "';")) {
                        ps.setInt(1, level);
                        ps.setInt(2, xp);
                        if (displayName != null) {
                            ps.setString(3, displayName);
                            ps.setInt(4, nextCost);
                        }
                        ps.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLanguage(UUID player, String iso) {
        if (!isConnected()) init();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT iso FROM player_language WHERE uuid = '" + player.toString() + "';")) {
                if (rs.next()) {
                    try (Statement st = connection.createStatement()) {
                        st.executeUpdate("UPDATE player_language SET iso='" + iso + "' WHERE uuid = '" + player.toString() + "';");
                    }
                } else {
                    try (PreparedStatement st = connection.prepareStatement("INSERT INTO player_language VALUES (0, ?, ?);")) {
                        st.setString(1, player.toString());
                        st.setString(2, iso);
                        st.execute();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLanguage(UUID player) {
        if (!isConnected()) init();
        String iso = Language.getDefaultLanguage().getIso();
        try (PreparedStatement ps = connection.prepareStatement("SELECT iso FROM player_language WHERE uuid = ?;")) {
            ps.setString(1, player.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    iso = rs.getString("iso");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return iso;
    }
}
