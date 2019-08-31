package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.api.server.ISetupSession;
import com.andrei1058.bedwars.api.server.RestoreAdapter;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.server.ServerType;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.UUID;

public interface BedWars {


    // SetupSession related.

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

    //

    /* GENERAL */

    /**
     * Get server type.
     **/
    ServerType getServerType();

    /**
     * Get a player language iso code
     */
    String getLangIso(Player p);

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
    Integer getPlayerTimeAFK(Player player);

    /* STATS */

    /**
     * Get player first play date.
     * You get data from the local cache.
     */
    Timestamp getPlayerFirstPlay(Player p);

    /**
     * Get player last play date.
     * You get data from the local cache.
     */
    Timestamp getPlayerLastPlay(Player p);

    /**
     * Get player total wins.
     * You get data from the local cache.
     */
    int getPlayerWins(Player p);

    /**
     * Get player total kills.
     * You get data from the local cache.
     */
    int getPlayerKills(Player p);

    /**
     * Get player total final kills.
     * You get data from the local cache.
     */
    int getPlayerFinalKills(Player p);

    /**
     * Get player total looses.
     * You get data from the local cache.
     */
    int getPlayerLooses(Player p);

    /**
     * Get player total deaths.
     * You get data from the local cache.
     */
    int getPlayerDeaths(Player p);

    /**
     * Get player total final deaths.
     * You get data from the local cache.
     */
    int getPlayerFinalDeaths(Player p);

    /**
     * Get player beds destroyed.
     * You get data from the local cache.
     */
    int getPlayerBedsDestroyed(Player p);

    /**
     * Get player games played.
     * You get data from the local cache.
     */
    int getPlayerGamesPlayed(Player p);

    /* GAME RELATED */

    /**
     * Check if a player is playing.
     */
    boolean isPlaying(Player p);

    /**
     * Check if a player is spectating.
     */
    boolean isSpectating(Player p);

    /* COMMANDS RELATED */

    /**
     * Get bedWars main command
     */
    ParentCommand getBedWarsCommand();

    /* ARENA RESTORE */

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
