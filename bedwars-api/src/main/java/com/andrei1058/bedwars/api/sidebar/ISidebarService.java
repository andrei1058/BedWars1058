package com.andrei1058.bedwars.api.sidebar;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * BedWars scoreboard manager.
 */
public interface ISidebarService {

    /**
     * Send player scoreboard based on conditions.
     */
    void giveSidebar(@NotNull Player player, @Nullable IArena arena, boolean delay);

    /**
     * Remove a player scoreboard.
     */
    void remove(@NotNull Player player);

    /**
     * Refresh title on all scoreboards.
     */
    void refreshTitles();

    /**
     * Refresh placeholders on all sidebars.
     */
    void refreshPlaceholders();

    /**
     * Refresh placeholders for sidebars in a given arena;
     */
    void refreshPlaceholders(IArena arena);

    /**
     * Refresh all tab-list header and footer strings for every sidebar.
     */
    void refreshTabList();

    /**
     * Refresh player healths.
     */
    void refreshHealth();

    @Nullable
    ISidebar getSidebar(@NotNull Player player);

}
