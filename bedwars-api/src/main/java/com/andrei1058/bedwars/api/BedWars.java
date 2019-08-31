package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.api.server.ISetupSession;
import com.andrei1058.bedwars.api.server.RestoreAdapter;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.server.ServerType;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.UUID;

public interface BedWars {

    /**
     * Get stats utils.
     */
    IStats getStatsCache();

    interface IStats {
        /**
         * Get player first play date.
         * You get data from the local cache.
         */
        Timestamp getPlayerFirstPlay(UUID p);

        /**
         * Get player last play date.
         * You get data from the local cache.
         */
        Timestamp getPlayerLastPlay(UUID p);

        /**
         * Get player total wins.
         * You get data from the local cache.
         */
        int getPlayerWins(UUID p);

        /**
         * Get player total kills.
         * You get data from the local cache.
         */
        int getPlayerKills(UUID p);

        /**
         * Get player total final kills.
         * You get data from the local cache.
         */
        int getPlayerFinalKills(UUID p);

        /**
         * Get player total looses.
         * You get data from the local cache.
         */
        int getPlayerLoses(UUID p);

        /**
         * Get player total deaths.
         * You get data from the local cache.
         */
        int getPlayerDeaths(UUID p);

        /**
         * Get player total final deaths.
         * You get data from the local cache.
         */
        int getPlayerFinalDeaths(UUID p);

        /**
         * Get player beds destroyed.
         * You get data from the local cache.
         */
        int getPlayerBedsDestroyed(UUID p);

        /**
         * Get player games played.
         * You get data from the local cache.
         */
        int getPlayerGamesPlayed(UUID p);
    }


    /**
     * Get afk system methods
     */
    IAFK getAFKSystem();

    interface IAFK {
        /**
         * Check if a player is AFK.
         */
        boolean isPlayerAFK(Player player);

        /**
         * Set a player afk.
         */
        void setPlayerAFK(Player player, boolean value);

        /**
         * Get the seconds since the player is AFK
         */
        int getPlayerTimeAFK(Player player);
    }


    /**
     * Get active setup session.
     *
     * @param player player uuid.
     * @return null if no session was found.
     */
    ISetupSession getSetupSession(UUID player);

    /**
     * Check if a player is in setup session.
     */
    boolean isInSetupSession(UUID player);

    /**
     * Get server type.
     **/
    ServerType getServerType();

    /**
     * Get a player language iso code
     */
    String getLangIso(Player p);

    /**
     * Check if a player is playing.
     */
    boolean isPlaying(Player p);

    /**
     * Check if a player is spectating.
     */
    boolean isSpectating(Player p);

    /**
     * Get bedWars main command
     */
    ParentCommand getBedWarsCommand();

    /**
     * Get the restore adapter.
     */
    RestoreAdapter getRestoreAdapter();

    /**
     * Change the arena restore adapter.
     *
     * @param restoreAdapter your custom adapter.
     */
    void setRestoreAdapter(RestoreAdapter restoreAdapter) throws IllegalAccessError;
}
