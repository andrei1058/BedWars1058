package com.andrei1058.bedwars.api.arena.stats;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface GameStatsHolder {

    IArena getArena();

    /**
     * Register statistic.
     * Throws a runtime exception if statistic is already registered.
     * @param statistic new statistic.
     */
    void register(@NotNull GameStatisticProvider<?> statistic);

    /**
     * Initialize game session stats for given player.
     * @param player stats holder.
     */
    PlayerGameStats init(Player player);

    /**
     * Remove player tracked data.
     * @param uuid holder.
     */
    void unregisterPlayer(UUID uuid);

    /**
     * Get existing or initialize statistic for given player.
     * @param holder player holder.
     * @return Existing or new statistic.
     */
    @NotNull PlayerGameStats getCreate(@NotNull Player holder);

    /**
     * Get existing or initialize statistic for given player.
     * @param holder player holder.
     * @return Existing or new statistic.
     */
    @Nullable PlayerGameStats get(@NotNull UUID holder);

    /**
     * Get existing or initialize statistic for given player.
     * @param holder player holder.
     * @return Existing or new statistic.
     */
    default @Nullable PlayerGameStats get(@NotNull Player holder) {
        return get(holder.getUniqueId());
    }

    /**
     * Get tracked players.
     * @return Unmodifiable list of tracked players.
     */
    Collection<PlayerGameStats> getPlayers();


    /**
     * @param statistic Order collection by given statistic.
     * @return Unmodifiable top list.
     */
    default Collection<PlayerGameStats> getOrderedBy(@NotNull DefaultStatistics statistic) {
        return getOrderedBy(statistic.toString());
    }

    /**
     * @param statistic Order collection by given statistic.
     * @return Unmodifiable top list.
     */
    List<PlayerGameStats> getOrderedBy(@NotNull String statistic);

    /**
     * Check if given statistic is registered.
     */
    boolean hasStatistic(String orderBy);

    /**
     * @return unmodifiable list of registered game statistics.
     */
    List<String> getRegistered();
}
