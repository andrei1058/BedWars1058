package com.andrei1058.bedwars.arena.stats;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.stats.PlayerGameStats;
import com.andrei1058.bedwars.api.arena.stats.GameStatistic;
import com.andrei1058.bedwars.api.language.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TopStringParser {

    private int index = 0;
    private final List<PlayerGameStats> ordered;
    private final IArena arena;

    public TopStringParser(@NotNull IArena arena, String orderBy) {
        this.arena = arena;
        if (!arena.getStatsHolder().hasStatistic(orderBy)) {
            throw new RuntimeException("Invalid order by. Provided: " + orderBy);
        }
        ordered = arena.getStatsHolder().getOrderedBy(orderBy);
    }

    /**
     *
     * @param string string to be placeholder replaced.
     * @param emptyReplacement replace empty top position with this string.
     */
    public String parseString(String string, @Nullable Language lang, String emptyReplacement) {
        if (index >= ordered.size()) {
            return string.replace("{topPlayerName}", emptyReplacement).replace("{topValue-kills}", "");
        }

        PlayerGameStats stats = ordered.get(index);

        boolean increment = string.contains("{topPlayerName}") || string.contains("{topPlayerDisplayName}");

        string = string.replace("{topPlayerName}", stats.getUsername())
                .replace("{topPlayerDisplayName}", stats.getDisplayPlayer());

        // todo foreach registered in that game
        for (String registered : arena.getStatsHolder().getRegistered()) {
            GameStatistic<?> statistic = stats.getStatistic(registered);

            if (!increment && string.contains("{topValue-"+registered+"}")){
                increment = true;
            }

            // todo replace with n.a. n.d. when null if there is a message already in messages
            string = string.replace("{topValue-"+registered+"}", null == statistic ? "" : statistic.getDisplayValue(lang));
        }

        if (increment) {
            index++;
        }
        return string;
    }

    public void resetIndex() {
        index = 0;
    }
}
