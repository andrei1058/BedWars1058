package com.andrei1058.bedwars.arena.stats;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.stats.GameStatisticProvider;
import com.andrei1058.bedwars.api.arena.stats.PlayerGameStats;
import com.andrei1058.bedwars.api.arena.stats.GameStatistic;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * This cannot be used for live tops.
 */
public class StatisticsOrdered {

    private final List<Optional<PlayerGameStats>> ordered;
    private final IArena arena;
    private BoundsPolicy boundsPolicy = BoundsPolicy.EMPTY;
    private final String orderBy;

    public StatisticsOrdered(@NotNull IArena arena, String orderBy) {
        this.arena = arena;
        if (null == arena.getStatsHolder()) {
            throw new RuntimeException("Arena stats holder is null.");
        }
        if (!arena.getStatsHolder().hasStatistic(orderBy)) {
            throw new RuntimeException("Invalid order by. Provided: " + orderBy);
        }
        ordered = arena.getStatsHolder().getOrderedBy(orderBy);
        this.orderBy = orderBy;
    }

    public StringParser newParser() {
        return new StringParser();
    }

    public class StringParser {
        private int index = 0;

        /**
         * @param string           string to be placeholder replaced.
         * @param emptyReplacement replace empty top position with this string.
         */
        public @Nullable String parseString(String string, @Nullable Language lang, String emptyReplacement) {
            if (null == arena.getStatsHolder()) {
                return null;
            }

            if (index >= ordered.size()) {
                if (boundsPolicy == BoundsPolicy.SKIP) {
                    if (string.isBlank()){
                        return string;
                    }

                    boolean hasPlaceholders = false;

                    for (String placeholder : new String[]{
                            "{topPlayerName}", "{topPlayerDisplayName}", "{topTeamColor}", "{topTeamName}", "{topValue}"
                    }) {
                        if (string.contains(placeholder)) {
                            hasPlaceholders = true;
                            break;
                        }
                    }

                    if (!hasPlaceholders) {
                        for (String registered : arena.getStatsHolder().getRegistered()) {
                            if (string.contains("{topValue-" + registered + "}")) {
                                hasPlaceholders = true;
                                break;
                            }
                        }
                    }
                    return hasPlaceholders ? null : string;
                }

                string = string
                        .replace("{topPlayerName}", emptyReplacement)
                        .replace("{topPlayerDisplayName}", emptyReplacement)
                        .replace("{topTeamColor}", "")
                        .replace("{topTeamName}", "")
                        .replace("{topValue}", "{topValue-"+orderBy+"}");

                for (String registered : arena.getStatsHolder().getRegistered()) {
                    String displayValue = "null";
                    GameStatisticProvider<?> provider = arena.getStatsHolder().getProvider(registered);
                    if (null != provider) {
                        displayValue = provider.getVoidReplacement(lang);
                    }
                    string = string.replace("{topValue-" + registered + "}", displayValue);
                }

                return string;
            }

            Optional<PlayerGameStats> statsOptional = ordered.get(index);

            if (statsOptional.isEmpty()) {
                return string;
            }

            PlayerGameStats stats = statsOptional.get();

            boolean increment = string.contains("{topPlayerName}") || string.contains("{topPlayerDisplayName}");

            Player online = Bukkit.getPlayer(stats.getPlayer());
            ITeam team = null == online ? arena.getExTeam(stats.getPlayer()) : arena.getTeam(online);
            if (null == team) {
                // if player online but eliminated
                team = arena.getExTeam(stats.getPlayer());
            }

            string = string.replace("{topPlayerName}", stats.getUsername())
                    .replace("{topPlayerDisplayName}", stats.getDisplayPlayer())
                    .replace("{topTeamColor}", null == team ? "" : team.getColor().chat().toString())
                    .replace("{topTeamName}", null == team ? "" : team.getDisplayName(lang))
                    .replace("{topValue}", "{topValue-"+orderBy+"}");

            for (String registered : arena.getStatsHolder().getRegistered()) {
                Optional<GameStatistic<?>> statistic = stats.getStatistic(registered);

                if (!increment && string.contains("{topValue-" + registered + "}")) {
                    increment = true;
                }

                String displayValue = statistic.map(gameStatistic -> gameStatistic.getDisplayValue(lang)).orElse(null);
                if (null == displayValue) {
                    GameStatisticProvider<?> provider = arena.getStatsHolder().getProvider(registered);
                    if (null == provider) {
                        displayValue = "null";
                    } else {
                        displayValue = provider.getVoidReplacement(lang);
                    }
                }

                string = string.replace("{topValue-" + registered + "}", displayValue);
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

    public void setBoundsPolicy(BoundsPolicy boundsPolicy) {
        this.boundsPolicy = boundsPolicy;
    }

    /**
     * What to do when iterating stats line and there are no more players to show.
     * This is used when there are more placeholders used than the actual player count.
     */

    public enum BoundsPolicy {
        /**
         * Skip line. Do not send it to receivers.
         */
        SKIP,
        /**
         * Send empty line to receivers.
         */
        EMPTY
    }
}
