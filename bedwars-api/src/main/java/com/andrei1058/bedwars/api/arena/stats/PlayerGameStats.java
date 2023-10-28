package com.andrei1058.bedwars.api.arena.stats;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Player stats container for a game.
 */
public interface PlayerGameStats {

    @NotNull UUID getPlayer();

    /**
     * @return player display name.
     */
    @NotNull String getDisplayPlayer();

    /**
     * @return player username.
     */
    @NotNull String getUsername();

    void registerStatistic(@NotNull String id, @NotNull GameStatistic<?> defaultValue);

    Optional<GameStatistic<?>> getStatistic(@NotNull String id);

    default Optional<GameStatistic<?>> getStatistic(@NotNull DefaultStatistics id) {
        return getStatistic(id.toString());
    }

    /**
     * List of registered statistics.
     */
    @SuppressWarnings("unused")
    List<String> getRegistered();
}
