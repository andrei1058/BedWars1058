package com.andrei1058.bedwars.database;

import com.andrei1058.bedwars.Main;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class SQLite implements Database {

    private Connection connection;

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

    @Override
    public void init() {
        File folder = new File(Main.plugin.getDataFolder() + "/Cache");
        if (!folder.exists()) folder.mkdir();
        File dataFolder = new File(folder.getPath() + "/shop.db");
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile();
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
                Main.plugin.getLogger().severe("Could Not Found SQLite Driver on your system!");
            e.printStackTrace();
            return;
        }

        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS quick_buy (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid VARCHAR(200), " +
                    "slot_19 VARCHAR(200), slot_20 VARCHAR(200), slot_21 VARCHAR(200), slot_22 VARCHAR(200), slot_23 VARCHAR(200), slot_24 VARCHAR(200), slot_25 VARCHAR(200)," +
                    "slot_28 VARCHAR(200), slot_29 VARCHAR(200), slot_30 VARCHAR(200), slot_31 VARCHAR(200), slot_32 VARCHAR(200), slot_33 VARCHAR(200), slot_34 VARCHAR(200)," +
                    "slot_37 VARCHAR(200), slot_38 VARCHAR(200), slot_39 VARCHAR(200), slot_40 VARCHAR(200), slot_41 VARCHAR(200), slot_42 VARCHAR(200), slot_43 VARCHAR(200));");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS player_levels (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid VARCHAR(200), " +
                    "level INTEGER, xp INTEGER, name VARCHAR(200), next_cost INTEGER);");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS  player_language (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid VARCHAR(200), " +
                    "iso VARCHAR(200));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveStats(UUID uuid, String username, Timestamp firstPlay, Timestamp lastPlay, int wins, int kills, int finalKills, int losses, int deaths, int finalDeaths, int bedsDestroyed, int gamesPlayed) {

    }

    @Override
    public void updateLocalCache(UUID uuid) {

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
        if (!isConnected()) init();
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
        if (!isConnected()) init();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT id FROM quick_buy WHERE uuid = '" + uuid.toString() + "';");
            if (rs.next()) {
                rs.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Object[] getLevelData(UUID player) {
        if (!isConnected()) init();
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
        if (!isConnected()) init();
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
        if (!isConnected()) init();
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
        if (!isConnected()) init();
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
