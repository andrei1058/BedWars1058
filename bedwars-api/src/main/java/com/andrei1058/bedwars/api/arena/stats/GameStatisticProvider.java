package com.andrei1058.bedwars.api.arena.stats;

import org.bukkit.plugin.Plugin;

public interface GameStatisticProvider<T extends GameStatistic<?>> {

    /**
     * Unique statistic identifier.
     */
    String getIdentifier();

    /**
     * Plugin provider.
     * @return statistic owner.
     */
    Plugin getOwner();

    /**
     * Default value used when initializing game stats.
     */
    T getDefault();
}
