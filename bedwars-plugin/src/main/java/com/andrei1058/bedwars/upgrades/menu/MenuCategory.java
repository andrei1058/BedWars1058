package com.andrei1058.bedwars.upgrades.menu;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class MenuCategory implements MenuContent {

    private ItemStack displayItem;

    @Override
    public ItemStack getDisplayItem() {
        return displayItem;
    }

    @Override
    public void onClick(ClickType clickType) {

    }
}
