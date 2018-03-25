package com.andrei1058.bedwars.api;

import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

public interface GameAPI {

    /* GENERAL */

    /** BedWars api = (BedWars) Bukkit.getServicesManager().getRegistration(GameAPI.class).getProvider();
     * Get server type.**/
    ServerType getServerType();

    /** Get api version. Since API 2*/
    int getApiVersion();

    /* STATS */

    /** Check if stats is enabled. Since API 3*/
    boolean isStats();

    /** Get player first play date. Since API 3.
     * Please note that stats are not cached. You get data directly from database.*/
    @Nullable
    Timestamp getPlayerFirstPlay(Player p);

    /** Get player last play date. Since API 3.
     * Please note that stats are not cached. You get data directly from database.*/
    @Nullable
    Timestamp getPlayerLastPlay(Player p);

    /** Get player total wins. Since API 3.
     * Please note that stats are not cached. You get data directly from database.*/
    int getPlayerWins(Player p);

    /** Get player total kills. Since API 3.
     * Please note that stats are not cached. You get data directly from database.*/
    int getPlayerKills(Player p);

    /** Get player total final kills. Since API 3.
     * Please note that stats are not cached. You get data directly from database.*/
    int getPlayerFinalKills(Player p);

    /** Get player total looses. Since API 3.
     * Please note that stats are not cached. You get data directly from database.*/
    int getPlayerLooses(Player p);

    /** Get player total deaths. Since API 3.
     * Please note that stats are not cached. You get data directly from database.*/
    int getPlayerDeaths(Player p);

    /** Get player total final deaths. Since API 3.
     * Please note that stats are not cached. You get data directly from database.*/
    int getPlayerFinalDeaths(Player p);

    /** Get player beds destroyed. Since API 3.
     * Please note that stats are not cached. You get data directly from database.*/
    int getPlayerBedsDestroyed(Player p);

    /** Get player games played. Since API 3.
     * Please note that stats are not cached. You get data directly from database.*/
    int getPlayerGamesPlayed(Player p);

    /** Get top x by wins. Since API 3.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be < x if there aren't enough players on the database.*/
    HashMap<UUID, Integer> getTopWins(int x);

    /** Get top x by kills. Since API 3.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be < x if there aren't enough players on the database.*/
    HashMap<UUID, Integer> getTopKills(int x);

    /** Get top x by final kills . Since API 3.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be < x if there aren't enough players on the database.*/
    HashMap<UUID, Integer> getTopFinalKills(int x);

    /** Get top x by looses. Since API 3.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be < x if there aren't enough players on the database.*/
    HashMap<UUID, Integer> getTopLooses(int x);

    /** Get top x by deaths. Since API 3.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be < x if there aren't enough players on the database.*/
    HashMap<UUID, Integer> getTopDeaths(int x);

    /** Get top x by final deaths. Since API 3.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be < x if there aren't enough players on the database.*/
    HashMap<UUID, Integer> getTopFinalDeaths(int x);

    /** Get top x by beds destroyed. Since API 3.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be < x if there aren't enough players on the database.*/
    HashMap<UUID, Integer> getTopBedsDestroyed(int x);

    /** Get top x by games played. Since API 3.
     * Please note that stats are not cached. You get data directly from database.
     * HashMap size can be < x if there aren't enough players on the database.*/
    HashMap<UUID, Integer> getTopGamesPlayed(int x);


    /* GAME RELATED */
    /** Check if a player is playing. */
    boolean isPlaying(Player p);

    /** Check if a player is spectating. */
    boolean isSpectating(Player p);
}
