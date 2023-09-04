package com.andrei1058.bedwars.arena.stats;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.stats.*;
import com.andrei1058.bedwars.arena.stats.defaults.PlayerGameStatsContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class GameStatsManager implements GameStatsHolder {

    private final HashMap<String, GameStatisticProvider<?>> registeredStats = new HashMap<>();
    private final HashMap<UUID, PlayerGameStats> playerSessionStats = new HashMap<>();

    private final IArena arena;

    public GameStatsManager(IArena arena) {
        this.arena = arena;

        for (DefaultStatistics statistic : DefaultStatistics.values()) {
            if (statistic.isIncrementable()) {
                register(new GenericStatistic(){
                    @Override
                    public String getIdentifier() {
                        return statistic.toString();
                    }
                });
            }
        }
    }

    public void register(@NotNull GameStatisticProvider<?> statistic) {
        if (statistic.getIdentifier().isBlank()) {
            throw new RuntimeException("Identifier cannot be blank: "+ statistic.getClass().getName());
        }
        if (!statistic.getIdentifier().trim().equals(statistic.getIdentifier())) {
            throw new RuntimeException("Identifier should not start/end with white spaces: "+statistic.getClass().getName());
        }
        if (null != registeredStats.getOrDefault(statistic.getIdentifier(), null)) {
            throw new RuntimeException("Statistic already registered: "+statistic.getIdentifier());
        }
        registeredStats.put(statistic.getIdentifier(), statistic);
        BedWars.debug("Registered new game statistic: "+statistic.getIdentifier());
    }


    @Override
    public IArena getArena() {
        return arena;
    }

    public PlayerGameStats init(@NotNull Player player) {
        if (playerSessionStats.containsKey(player.getUniqueId())) {
            throw new RuntimeException(player.getName()+" is already registered for game stats!");
        }

        PlayerGameStats stats = new PlayerGameStatsContainer(player);
        this.registeredStats.forEach((id, provider) -> stats.registerStatistic(id, provider.getDefault()));

        playerSessionStats.put(player.getUniqueId(), stats);
        return stats;
    }

    @Override
    public void unregisterPlayer(UUID uuid) {
        if (getArena().getStatus() == GameState.restarting) {
            throw new RuntimeException("You cannot unregister player stats during restarting phase!");
        }
        this.playerSessionStats.remove(uuid);
    }

    @Override
    public @NotNull PlayerGameStats getCreate(@NotNull Player holder) {
        PlayerGameStats stats = playerSessionStats.getOrDefault(holder.getUniqueId(), null);
        if (null == stats) {
            stats = init(holder);
            playerSessionStats.put(holder.getUniqueId(), stats);
        }
        return stats;
    }

    @Override
    public @Nullable PlayerGameStats get(@NotNull UUID holder) {
        return playerSessionStats.getOrDefault(holder, null);
    }

    @Override
    public Collection<PlayerGameStats> getPlayers() {
        return Collections.unmodifiableCollection(playerSessionStats.values());
    }

    @Override
    public List<PlayerGameStats> getOrderedBy(@NotNull String statistic) {
        //noinspection DataFlowIssue
        List<PlayerGameStats> list = playerSessionStats.values().stream()
                .filter(data -> null != data.getStatistic(statistic))
                .sorted(Comparator.comparing(a -> a.getStatistic(statistic)))
                .collect(Collectors.toList());
        Collections.reverse(list);
        return Collections.unmodifiableList(list);
    }

    @Override
    public boolean hasStatistic(String orderBy) {
        return registeredStats.containsKey(orderBy);
    }

    @Override
    public List<String> getRegistered() {
        return registeredStats.keySet().stream().collect(Collectors.toUnmodifiableList());
    }
}
