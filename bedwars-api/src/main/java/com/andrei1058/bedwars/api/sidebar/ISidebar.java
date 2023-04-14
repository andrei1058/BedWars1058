package com.andrei1058.bedwars.api.sidebar;

import com.andrei1058.bedwars.api.arena.IArena;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ISidebar {

    /**
     * Sidebar holder.
     */
    TabPlayer getTabPlayer();

    /**
     * Coincides with the arena where the player is on.
     */
    @Nullable
    IArena getArena();

    /**
     * Get tab api handle.
     */
    TabAPI getHandle();

    /**
     * Set sidebar content.
     */
    void setContent(String status, String title, List<String> lineArray);


    /**
     * Will update tab prefix and suffix for the given player on current sidebar.
     *
     * @param player         format given player on current holder's sidebar.
     * @param skipStateCheck will skip checking if tab formatting is disabled.
     */
    void giveUpdateTabFormat(@NotNull Player player, boolean skipStateCheck);


}
