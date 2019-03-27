package com.andrei1058.bedwars.database;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

public interface Database {

    /**
     * Initialize database.
     */
    void init();

    /**
     * Create or replace stats for a player.
     */
    void saveStats(UUID uuid, String username, Timestamp firstPlay, Timestamp lastPlay, int wins, int kills, int finalKills, int losses, int deaths, int finalDeaths, int bedsDestroyed, int gamesPlayed);

    /**
     * Update local cache from remote database.
     */
    void updateLocalCache(UUID uuid);

    /**
     * Close connection.
     */
    void close();

    /**
     * Set quick buy slot value.
     */
    void setQuickBuySlot(UUID uuid, String shopPath, int slot);

    /**
     * Get quick buy slot value.
     */
    String getQuickBuySlots(UUID uuid, int slot);

    /**
     * Check if has quick buy.
     */
    boolean hasQuickBuy(UUID player);

    /**
     * Get a player level and xp.
     * <p>
     * args 0 is level.
     * args 1 is xp.
     * args 2 is display name.
     * args 3 next level cost.
     */
    Object[] getLevelData(UUID player);

    /**
     * Set a player level data.
     */
    void setLevelData(UUID player, int level, int xp, String displayName, int nextCost);
}
