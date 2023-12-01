package dev.andrei1058.bedwars.common.api.database;

import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.UUID;

/***
 * Do not cache this please.
 * This is a read only object used to map data from your database source.
 * The plugin will handle cache itself.
 */
// todo I'd actually like to make stats as objects. this is kind of hard coded. similar to the session stats we introduced in 23.11
public interface PlayerStats {

    /**
     * Player first game.
     * @return date.
     */
    @Nullable
    Date getFirstPlay();

    /**
     * @return user identifier.
     */
    UUID getPlayer();

    /**
     * @return last gameplay.
     */
    Date getLastPlay();

    /**
     * @return total of games played.
     */
    int getGamesPlayed();

    /**
     * @return total of abandoned games.
     */
    int getGamesAbandoned();

    /**
     * @return total of games won.
     */
    int getGamesWon();

    /**
     * @return games lost count.
     */
    int getGamesLost();

    //todo sync method names with PlayerStatsCache
}
