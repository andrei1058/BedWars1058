package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.api.arena.RestoreAdapter;
import com.andrei1058.bedwars.api.command.ParentCommand;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.Timestamp;

public interface GameAPI {

    /* GENERAL */

    /**
     * BedWars api = (BedWars) Bukkit.getServicesManager().getRegistration(GameAPI.class).getProvider();
     * Get server type.
     **/
    ServerType getServerType();

    /**
     * Get api version.
     */
    int getApiVersion();

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
    @Nullable
    Timestamp getPlayerFirstPlay(Player p);

    /**
     * Get player last play date.
     * You get data from the local cache.
     */
    @Nullable
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
