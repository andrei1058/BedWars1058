package com.andrei1058.bedwars.api.sidebar;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.spigot.sidebar.PlaceholderProvider;
import com.andrei1058.spigot.sidebar.Sidebar;
import com.andrei1058.spigot.sidebar.SidebarLine;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ISidebar {

    /**
     * Sidebar holder.
     */
    Player getPlayer();

    /**
     * Coincides with the arena where the player is on.
     */
    @Nullable
    IArena getArena();

    /**
     * Get sidebar lib handle.
     */
    Sidebar getHandle();

    /**
     * Set sidebar content.
     */
    void setContent(List<String> titleArray, List<String> lineArray, @Nullable IArena arena);

    /**
     * Convert an animated string to an object.
     */
    SidebarLine normalizeTitle(@Nullable List<String> titleArray);

    /**
     * Convert string lines to string objects.
     */
    @NotNull List<SidebarLine> normalizeLines(@NotNull List<String> lineArray);

    /**
     * Will update tab prefix and suffix for the given player on current sidebar.
     *
     * @param player         format given player on current holder's sidebar.
     * @param skipStateCheck will skip checking if tab formatting is disabled.
     * @param spectator when you already know the player is a spectator. E.g. on join. Null will let the plugin whether the player is spectator or not.
     */
    void giveUpdateTabFormat(@NotNull Player player, boolean skipStateCheck, @Nullable Boolean spectator);

    /**
     * Will update tab prefix and suffix for the given player on current sidebar.
     *
     * @param player         format given player on current holder's sidebar.
     * @param skipStateCheck will skip checking if tab formatting is disabled.
     */
    default void giveUpdateTabFormat(@NotNull Player player, boolean skipStateCheck) {
        giveUpdateTabFormat(player, skipStateCheck, null);
    }


    /**
     * @return true if tab formatting is disabled for current sidebar/ arena stage.
     */
    @Deprecated(forRemoval = true)
    boolean isTabFormattingDisabled();

    /**
     * Register a placeholder that is not going to be removed trough game state changes.
     */
    boolean registerPersistentPlaceholder(PlaceholderProvider placeholderProvider);
}
