package com.andrei1058.bedwars.stats;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StatsManager {

    private Map<UUID, PlayerStats> stats;

    public StatsManager() {
        stats = new ConcurrentHashMap<>();
        registerListeners();
    }

    public void remove(UUID uuid) {
        stats.remove(uuid);
    }

    public void put(UUID uuid, PlayerStats playerStats) {
        stats.put(uuid, playerStats);
    }

    public PlayerStats get(UUID uuid) {
        PlayerStats playerStats = stats.get(uuid);
        if (playerStats == null) {
            throw new IllegalStateException("Trying to get stats data of an unloaded player!");
        }
        return playerStats;
    }

    public PlayerStats getUnsafe(UUID uuid) {
        return stats.get(uuid);
    }

    /**
     * Register listeners related to stats cache.
     */
    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new StatsListener(), BedWars.plugin);
    }
}
