package com.andrei1058.bedwars.upgrades.menu;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class UpgradesMenu {

    private String name;
    private HashMap<MenuContent, Integer> menuContentBySlot = new HashMap<>();

    /**
     * Create an upgrade menu for an arena group.
     *
     * @param groupName arena group name.
     */
    public UpgradesMenu(String groupName) {
        this.name = groupName;
    }

    /**
     * Open this menu to a player.
     * @param player target player.
     */
    public void open(Player player) {
        if (!BedWars.getAPI().getArenaUtil().isPlaying(player)) return;
        UpgradesManager.setWatchingUpgrades(player.getUniqueId());
    }

    /**
     * Add content to a menu.
     *
     * @param content content instance.
     * @param slot    where to put the content in the menu.
     * @return false if te given slot is in use.
     */
    public boolean addContent(MenuContent content, int slot) {
        //todo
        return false;
    }
}
