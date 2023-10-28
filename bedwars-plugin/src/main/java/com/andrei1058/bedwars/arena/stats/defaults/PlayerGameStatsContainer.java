package com.andrei1058.bedwars.arena.stats.defaults;

import com.andrei1058.bedwars.api.arena.stats.PlayerGameStats;
import com.andrei1058.bedwars.api.arena.stats.GameStatistic;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerGameStatsContainer implements PlayerGameStats {

    private final UUID player;
    private final String username;
    // last tracked display name
    private final String lastDisplayName;
    private final HashMap<String, Optional<GameStatistic<?>>> statsById = new HashMap<>();

    public PlayerGameStatsContainer(@NotNull Player player) {
        this.player = player.getUniqueId();
        this.username = player.getName();
        this.lastDisplayName = player.getDisplayName();
    }

    @Override
    public @NotNull UUID getPlayer() {
        return player;
    }

    @Override
    public @NotNull String getDisplayPlayer() {
        Player online = Bukkit.getPlayer(getPlayer());
        if (null == online) {
            return lastDisplayName;
        }
        return online.getDisplayName();
    }

    @Override
    public @NotNull String getUsername() {
        return username;
    }

    @Override
    public void registerStatistic(@NotNull String id, @NotNull GameStatistic<?> defaultValue) {
        if (statsById.containsKey(id)) {
            throw new RuntimeException("Statistic "+id+" already registered for player "+ getPlayer());
        }
        statsById.put(id, Optional.of(defaultValue));
    }

    @Override
    public @Nullable Optional<GameStatistic<?>> getStatistic(@NotNull String id) {
        return statsById.getOrDefault(id, Optional.empty());
    }

    @Override
    public List<String> getRegistered() {
        return statsById.keySet().stream().collect(Collectors.toUnmodifiableList());
    }
}
