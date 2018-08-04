package com.andrei1058.bedwars.support.stats;

import com.andrei1058.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class SQLite implements Database {

    private static String databaseName = "stats";
    private static Connection connection;

    public SQLite() {
        if (connect()) {
            Main.plugin.getLogger().info("Using SQLite support.");
        } else {
            Main.plugin.database = new None();
        }
    }

    private boolean connect() {
        if (!isConnected()) {
            File dataFolder = new File(Main.plugin.getDataFolder(), databaseName + ".db");
            if (!dataFolder.exists()) {
                try {
                    dataFolder.createNewFile();
                } catch (IOException e) {
                    Main.plugin.getLogger().log(Level.SEVERE, "File write error: " + databaseName + ".db");
                }
            }
            try {
                if (connection != null && !connection.isClosed()) {
                    return true;
                }
                ClassLoader cl = Bukkit.getServer().getClass().getClassLoader();
                Driver d = (Driver) cl.loadClass("org.sqlite.JDBC").newInstance();
                DriverManager.registerDriver(d);
                connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
                return true;
            } catch (SQLException ex) {
                Main.plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
                return false;
            } catch (ClassNotFoundException ex) {
                return false;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean isConnected() {
        if (connection == null) return false;
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPlayerSet(Player p) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT id FROM global_stats WHERE uuid = '" + p.getUniqueId().toString() + "';");
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean isStats() {
        return true;
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
    public void setupGeneralTables() {
        if (!isConnected()) connect();
        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS global_stats (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name VARCHAR(200), uuid VARCHAR(200), first_play TIMESTAMP NULL DEFAULT NULL, last_play TIMESTAMP NULL DEFAULT NULL, wins INTEGER(200)," +
                    " kills INTEGER(200), final_kills INTEGER(200), looses INTEGER(200), deaths INTEGER(200), final_deaths INTEGER(200), beds_destroyed INTEGER(200), games_played INTEGER(200))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setupGroupTable(String group) {

    }

    @Override
    public void saveStats(Player p, Timestamp last_play, int wins, int kills, int final_kills, int looses, int deaths, int final_deaths, int beds_destroyed, int games_played) {
        if (!isConnected()) connect();
        if (isPlayerSet(p)) {
            try {
                PreparedStatement ps = connection.prepareStatement("UPDATE global_stats SET last_play = ?, wins = wins + '" + wins + "', kills = kills + '" + kills + "', " +
                        "final_kills = final_kills + '" + final_kills + "', looses = looses + '" + looses + "'," +
                        " deaths = deaths + '" + deaths + "', final_deaths = final_deaths + '" + final_deaths + "', beds_destroyed = beds_destroyed + '" + beds_destroyed + "', " +
                        "games_played = games_played + '" + games_played + "' WHERE uuid = '" + p.getUniqueId().toString() + "';");
                ps.setTimestamp(1, last_play);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO global_stats VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                ps.setInt(1, 0);
                ps.setString(2, p.getName());
                ps.setString(3, p.getUniqueId().toString());
                ps.setTimestamp(4, last_play);
                ps.setTimestamp(5, last_play);
                ps.setInt(6, wins);
                ps.setInt(7, kills);
                ps.setInt(8, final_kills);
                ps.setInt(9, looses);
                ps.setInt(10, deaths);
                ps.setInt(11, final_deaths);
                ps.setInt(12, beds_destroyed);
                ps.setInt(13, games_played);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Timestamp getFirstPlay(Player p) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT first_play FROM global_stats WHERE uuid='" + p.getUniqueId().toString() + "';");
            if (rs.next())
                return rs.getTimestamp("first_play");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Timestamp getLastPlay(Player p) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT last_play FROM global_stats WHERE uuid='" + p.getUniqueId().toString() + "';");
            if (rs.next())
                return rs.getTimestamp("last_play");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getKills(Player p) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT kills FROM global_stats WHERE uuid='" + p.getUniqueId().toString() + "';");
            if (rs.next())
                return rs.getInt("kills");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getWins(Player p) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT wins FROM global_stats WHERE uuid='" + p.getUniqueId().toString() + "';");
            if (rs.next())
                return rs.getInt("wins");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getFinalKills(Player p) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT final_kills FROM global_stats WHERE uuid='" + p.getUniqueId().toString() + "';");
            if (rs.next())
                return rs.getInt("final_kills");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getLooses(Player p) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT looses FROM global_stats WHERE uuid='" + p.getUniqueId().toString() + "';");
            if (rs.next())
                return rs.getInt("looses");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getFinalDeaths(Player p) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT final_deaths FROM global_stats WHERE uuid='" + p.getUniqueId().toString() + "';");
            if (rs.next())
                return rs.getInt("final_deaths");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getDeaths(Player p) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT deaths FROM global_stats WHERE uuid='" + p.getUniqueId().toString() + "';");
            if (rs.next())
                return rs.getInt("deaths");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getBedsDestroyed(Player p) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT beds_destroyed FROM global_stats WHERE uuid='" + p.getUniqueId().toString() + "';");
            if (rs.next())
                return rs.getInt("beds_destroyed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getGamesPlayed(Player p) {
        if (!isConnected()) connect();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT games_played FROM global_stats WHERE uuid='" + p.getUniqueId().toString() + "';");
            if (rs.next())
                return rs.getInt("games_played");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public HashMap<UUID, Integer> getTopWins(int x) {
        if (!isConnected()) connect();
        HashMap<UUID, Integer> result = new HashMap<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT wins, uuid FROM global_stats ORDER BY MAX(wins);");
            while (rs.next()) {
                result.put(UUID.fromString(rs.getString("uuid")), rs.getInt("wins"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public HashMap<UUID, Integer> getTopKills(int x) {
        if (!isConnected()) connect();
        HashMap<UUID, Integer> result = new HashMap<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT kills, uuid FROM global_stats ORDER BY MAX(kills);");
            while (rs.next()) {
                result.put(UUID.fromString(rs.getString("uuid")), rs.getInt("kills"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<UUID, Integer> getTopFinalKills(int x) {
        if (!isConnected()) connect();
        HashMap<UUID, Integer> result = new HashMap<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT final_kills, uuid FROM global_stats ORDER BY MAX(final_kills);");
            while (rs.next()) {
                result.put(UUID.fromString(rs.getString("uuid")), rs.getInt("final_kills"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<UUID, Integer> getTopLooses(int x) {
        if (!isConnected()) connect();
        HashMap<UUID, Integer> result = new HashMap<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT looses, uuid FROM global_stats ORDER BY MAX(looses);");
            while (rs.next()) {
                result.put(UUID.fromString(rs.getString("uuid")), rs.getInt("looses"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<UUID, Integer> getTopDeaths(int x) {
        if (!isConnected()) connect();
        HashMap<UUID, Integer> result = new HashMap<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT deaths, uuid FROM global_stats ORDER BY MAX(deaths);");
            while (rs.next()) {
                result.put(UUID.fromString(rs.getString("uuid")), rs.getInt("deaths"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<UUID, Integer> getTopFinalDeaths(int x) {
        if (!isConnected()) connect();
        HashMap<UUID, Integer> result = new HashMap<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT final_deaths, uuid FROM global_stats ORDER BY MAX(final_deaths);");
            while (rs.next()) {
                result.put(UUID.fromString(rs.getString("uuid")), rs.getInt("final_deaths"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<UUID, Integer> getTopBedsDestroyed(int x) {
        if (!isConnected()) connect();
        HashMap<UUID, Integer> result = new HashMap<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT beds_destroyed, uuid FROM global_stats ORDER BY MAX(beds_destroyed);");
            while (rs.next()) {
                result.put(UUID.fromString(rs.getString("uuid")), rs.getInt("beds_destroyed"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<UUID, Integer> getTopGamesPlayed(int x) {
        if (!isConnected()) connect();
        HashMap<UUID, Integer> result = new HashMap<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT games_played, uuid FROM global_stats ORDER BY MAX(games_played);");
            while (rs.next()) {
                result.put(UUID.fromString(rs.getString("uuid")), rs.getInt("games_played"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
