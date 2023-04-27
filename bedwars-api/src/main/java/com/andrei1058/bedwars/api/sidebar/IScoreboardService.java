package com.andrei1058.bedwars.api.sidebar;

import com.andrei1058.bedwars.api.arena.IArena;
import me.neznamy.tab.api.scoreboard.Scoreboard;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * BedWars scoreboard manager.
 */
public interface IScoreboardService {

    /**
     * Send player scoreboard based on conditions.
     */
    void giveTabFeatures(@NotNull Player player, @Nullable IArena arena, boolean delay);

    /**
     * Remove a player scoreboard.
     */
    void remove(@NotNull Player player);

    /**
     * @return true if tab formatting is disabled for current sidebar/ arena stage
     */
    boolean isTabFormattingDisabled(IArena arena);

    /**
     * @return Get the current active scoreboard of a player.
     */
    @Nullable
    Scoreboard getScoreboard(@NotNull Player player);
}
