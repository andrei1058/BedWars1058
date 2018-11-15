package com.andrei1058.bedwars.support.stats;

import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

public interface Database {

    /**
     * Check if stats are stored in sqlite, mysql or something
     */
    boolean isStats();

    /**
     * Close database connection
     */
    void close();

    /**
     * Setup general stuff
     */
    void setupGeneralTables();

    /**
     * Create a new custom table
     */
    void setupGroupTable(String group);

    /**
     * Save player stats
     */
    void saveStats(Player p, Timestamp last_play, int wins, int kills, int final_kills, int looses, int deaths, int final_deaths, int beds_destroyed, int games_played);

    /**
     * Get player first play from stats
     */
    Timestamp getFirstPlay(Player p);

    /**
     * Get player last play from stats
     */
    Timestamp getLastPlay(Player p);

    /**
     * Get player total kills
     */
    int getKills(Player p);

    /**
     * Get player total wins
     */
    int getWins(Player p);

    /**
     * Get player total final kills
     */
    int getFinalKills(Player p);

    /**
     * Get player total looses
     */
    int getLooses(Player p);

    /**
     * Get player total final deaths
     */
    int getFinalDeaths(Player p);

    /**
     * Get player total deaths
     */
    int getDeaths(Player p);

    /**
     * Get player beds destroyed
     */
    int getBedsDestroyed(Player p);

    /**
     * Get player games played
     */
    int getGamesPlayed(Player p);

    /**
     * Get top x by wins.
     */
    HashMap<UUID, Integer> getTopWins(int x);

    /**
     * Get top x by kills.
     */
    HashMap<UUID, Integer> getTopKills(int x);

    /**
     * Get top x by final kills .
     */
    HashMap<UUID, Integer> getTopFinalKills(int x);

    /**
     * Get top x by looses.
     */
    HashMap<UUID, Integer> getTopLooses(int x);

    /**
     * Get top x by deaths.
     */
    HashMap<UUID, Integer> getTopDeaths(int x);

    /**
     * Get top x by final deaths.
     */
    HashMap<UUID, Integer> getTopFinalDeaths(int x);

    /**
     * Get top x by beds destroyed.
     */
    HashMap<UUID, Integer> getTopBedsDestroyed(int x);

    /**
     * Get top x by games played.
     */
    HashMap<UUID, Integer> getTopGamesPlayed(int x);


    //QUICK BUY

    /**
     * Set quick buy slot value
     */
    void setQuickBuySlot(UUID uuid, String shopPath, int slot);

    /**
     * Get quick buy slot value
     */
    public String getQuickBuySlots(UUID uuid, int slot);

    /** Check if has quick buy*/
    boolean hasQuickBuy(UUID uuid);
}
