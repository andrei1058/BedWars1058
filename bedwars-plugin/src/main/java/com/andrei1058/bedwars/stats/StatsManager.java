package com.andrei1058.bedwars.stats;

import com.andrei1058.bedwars.Main;
import org.bukkit.Bukkit;

public class StatsManager {

    private static StatsCache statsCache;

    public StatsManager() {
        registerListeners();
        statsCache = new StatsCache();
    }

    /**
     * Register listeners related to stats cache.
     */
    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new StatsListener(), Main.plugin);
    }

    /**
     * Get stats cache.
     */
    public static StatsCache getStatsCache() {
        return statsCache;
    }
}
