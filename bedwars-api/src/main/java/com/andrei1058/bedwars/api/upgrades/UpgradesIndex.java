package com.andrei1058.bedwars.api.upgrades;

import org.bukkit.entity.Player;

public interface UpgradesIndex {

    /**
     * Get menu name.
     */
    String getName();

    /**
     * Open this menu to a player.
     * Make sure to use {@link com.andrei1058.bedwars.api.BedWars.TeamUpgradesUtil#setWatchingGUI(Player)}
     *
     * @param player target player.
     */
    void open(Player player);

    /**
     * Add content to a menu.
     *
     * @param content content instance.
     * @param slot    where to put the content in the menu.
     * @return false if te given slot is in use.
     */
    boolean addContent(MenuContent content, int slot);
}
