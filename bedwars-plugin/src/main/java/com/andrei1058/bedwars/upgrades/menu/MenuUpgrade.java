package com.andrei1058.bedwars.upgrades.menu;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MenuUpgrade implements MenuContent {

    private String name;
    private List<UpgradeTier> tiers = new LinkedList<>();

    /**
     * Create a new upgrade element.
     *
     * @param name identifier.
     */
    public MenuUpgrade(String name) {
        this.name = name;
    }

    @Override
    public ItemStack getDisplayItem() {
        //return next tier display item
        return null;
    }

    @Override
    public void onClick(ClickType clickType) {

    }

    /**
     * Load a upgrade element tiers.
     *
     * @return false if something went wrong.
     */
    private boolean loadTiers() {
        //todo
        return false;
    }

    /**
     * @return tiers list.
     */
    public List<UpgradeTier> getTiers() {
        return Collections.unmodifiableList(tiers);
    }
}
