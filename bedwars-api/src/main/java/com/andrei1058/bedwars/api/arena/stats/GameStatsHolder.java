package com.andrei1058.bedwars.api.arena.stats;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("unused")
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
    Optional<PlayerGameStats> get(@NotNull UUID holder);

    /**
     * Get existing or initialize statistic for given player.
     * @param holder player holder.
     * @return Existing or new statistic.
     */
    default Optional<PlayerGameStats> get(@NotNull Player holder) {
        return get(holder.getUniqueId());
    }

    /**
     * Get tracked players.
     *
     * @return Unmodifiable list of tracked players.
     */
    Collection<Optional<PlayerGameStats>> getTrackedPlayers();


    /**
     * @param statistic Order collection by given statistic.
     * @return top list.
     */
    default Collection<Optional<PlayerGameStats>> getOrderedBy(@NotNull DefaultStatistics statistic) {
        return getOrderedBy(statistic.toString());
    }

    /**
     * @param statistic Order collection by given statistic.
     * @return top list.
     */
    List<Optional<PlayerGameStats>> getOrderedBy(@NotNull String statistic);

    /**
     * Check if given statistic is registered.
     */
    boolean hasStatistic(String orderBy);

    /**
     * @return unmodifiable list of registered game statistics.
     */
    List<String> getRegistered();

    /**
     * Get statistic provider.
     */
    @Nullable GameStatisticProvider<?> getProvider(String registered);
}
