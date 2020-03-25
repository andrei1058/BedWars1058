package com.andrei1058.bedwars.api.upgrades;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public interface MenuContent {

    /**
     * Item that represent the item in the GUI.
     *
     * @return item.
     */
    ItemStack getDisplayItem(Player player, ITeam team);

    /**
     * Manage what to do on click.
     */
    void onClick(Player player, ClickType clickType, ITeam team);

    /**
     * Get identifier.
     *
     * @return content name.
     */
    String getName();
}
