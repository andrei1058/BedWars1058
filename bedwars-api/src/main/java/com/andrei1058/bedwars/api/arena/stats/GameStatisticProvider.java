package com.andrei1058.bedwars.api.arena.stats;

import com.andrei1058.bedwars.api.language.Language;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

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

    /**
     * Display value for undetermined values.
     * @param language desired translation.
     */
    String getVoidReplacement(@Nullable Language language);
}
