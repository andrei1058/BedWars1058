package com.andrei1058.bedwars.api.arena.stats;

import com.andrei1058.bedwars.api.language.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Game statistic.
 * @param <T> statistic type. We usually work with integers.
 */
public interface GameStatistic<T> extends Comparable<GameStatistic<T>> {

    /**
     * Current value.
     */
    T getValue();

    /**
     * Value displayed in tops etc.
     * @param language - message receiver.
     */
    String getDisplayValue(@Nullable Language language);

    /**
     * Comparison for tops.
     * @param o the object to be compared.
     */
    int compareTo(@NotNull GameStatistic<T> o);
}
