package com.andrei1058.bedwars.upgrades.menu;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class MenuTrapSlot implements MenuContent {

    private ItemStack displayItem;
    private String name;

    /**
     * @param displayItem display item.
     */
    public MenuTrapSlot(String name, ItemStack displayItem) {
        this.displayItem = displayItem;
        this.name = name;
    }

    @Override
    public ItemStack getDisplayItem() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void onClick(ClickType clickType) {

    }
}
