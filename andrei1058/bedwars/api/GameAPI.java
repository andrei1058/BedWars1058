package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.api.command.ParentCommand;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

public interface GameAPI {

    /* GENERAL */

    /**
     * BedWars api = (BedWars) Bukkit.getServicesManager().getRegistration(GameAPI.class).getProvider();
     * Get server type.
     **/
    ServerType getServerType();

    /**
     * Get api version.
     *
     * @since api v2
     */
    int getApiVersion();

    /**
     * Get a player language iso code
     *
     * @since api v6
     */
    String getLangIso(Player p);

    /**
     * Check if a player is AFK.
     *
     * @since API 10
     */
    boolean isPlayerAFK(Player player);

    /**
     * Set a player afk.
     *
     * @since API 10
     */
    void setPlayerAFK(Player player, boolean value);

    /**
     * Get the seconds since the player is AFK
     *
     * @since API 10
     */
    Integer getPlayerTimeAFK(Player player);

    /* STATS */

    /**
     * Check if stats is enabled.
     *
     * @since api v3
     */
    boolean isStats();

    /**
     * Get player first play date.
     * Please note that stats are not cached. You get data directly from database.
     *
     * @since api v3
     */
    @Nullable
    Timestamp getPlayerFirstPlay(Player p);

    /**
     * Get player last play date.
     * Please note that stats are not cached. You get data directly from database.
     *
     * @since api v3
     */
    @Nullable
    Timestamp getPlayerLastPlay(Player p);

    /**
     * Get player total wins.
     * Please note that stats are not cached. You get data directly from database.
     *
     * @since api v3
     */
    int getPlayerWins(Player p);

    /**
     * Get player total kills.
     * Please note that stats are not cached. You get data directly from database.
     *
     * @since api v3
     */
    int getPlayerKills(Player p);

    /**
     * Get player total final kills.
     * Please note that stats are not cached. You get data directly from database.
     *
     * @since api v3
     */
    int getPlayerFinalKills(Player p);

    /**
     * Get player total looses.
     * Please note that stats are not cached. You get data directly from database.
     *
     * @since api v3
     */
    int getPlayerLooses(Player p);

    /**
     * Get player total deaths.
     * Please note that stats are not cached. You get data directly from database.
     *
     * @since api v3
     */
    int getPlayerDeaths(Player p);

    /**
     * Get player total final deaths.
     * Please note that stats are not cached. You get data directly from database.
     *
     * @since api v3
     */
    int getPlayerFinalDeaths(Player p);

    /**
     * Get player beds destroyed.
     * Please note that stats are not cached. You get data directly from database.
     *
     * @since api v3
     */
    int getPlayerBedsDestroyed(Player p);

    /**
     * Get player games played.
     * Please note that stats are not cached. You get data directly from database.
     *
     * @since api v3
     */
    int getPlayerGamesPlayed(Player p);

    /**
     * Get top x by wins.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be greater than x if there aren't enough players on the database.
     *
     * @since api v3
     */
    HashMap<UUID, Integer> getTopWins(int x);

    /**
     * Get top x by kills.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be greater than x if there aren't enough players on the database.
     *
     * @since api v3
     */
    HashMap<UUID, Integer> getTopKills(int x);

    /**
     * Get top x by final kills .
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be greater than x if there aren't enough players on the database.
     *
     * @since api v3
     */
    HashMap<UUID, Integer> getTopFinalKills(int x);

    /**
     * Get top x by looses.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be greater than x if there aren't enough players on the database.
     *
     * @since api v3
     */
    HashMap<UUID, Integer> getTopLooses(int x);

    /**
     * Get top x by deaths.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be greater than x if there aren't enough players on the database.
     *
     * @since api v3
     */
    HashMap<UUID, Integer> getTopDeaths(int x);

    /**
     * Get top x by final deaths.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be greater than x if there aren't enough players on the database.
     *
     * @since api v3
     */
    HashMap<UUID, Integer> getTopFinalDeaths(int x);

    /**
     * Get top x by beds destroyed.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be greater than x if there aren't enough players on the database.
     *
     * @since api v3
     */
    HashMap<UUID, Integer> getTopBedsDestroyed(int x);

    /**
     * Get top x by games played.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be greater than x if there aren't enough players on the database.
     *
     * @since api v3
     */
    HashMap<UUID, Integer> getTopGamesPlayed(int x);


    /* GAME RELATED */

    /**
     * Check if a player is playing.
     *
     * @since api v1
     */
    boolean isPlaying(Player p);

    /**
     * Check if a player is spectating.
     *
     * @since api v1
     */
    boolean isSpectating(Player p);

    /* COMMANDS RELATED */

    /**
     * Get bedWars main command
     *
     * @since API 11
     */
    ParentCommand getBedWarsCommand();
}
