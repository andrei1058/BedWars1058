package com.andrei1058.bedwars.shop;

import org.bukkit.inventory.ItemStack;

public class ShopItem {

    private ItemStack itemStack;
    private boolean permanent, autoequip;

    public ShopItem(ItemStack itemStack, boolean permanent, boolean autoequip){
        this.itemStack = itemStack;
        this.permanent = permanent;
        this.autoequip = autoequip;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public boolean isAutoequip() {
        return autoequip;
    }
}
