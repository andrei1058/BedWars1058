package com.andrei1058.bedwars.shop;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static com.andrei1058.bedwars.Main.nms;

public class ShopItem {

    private ItemStack itemStack;
    private boolean permanent, autoequip;

    public ShopItem(ItemStack itemStack, boolean permanent, boolean autoequip, String identifier){
        if (nms.isArmor(itemStack) || nms.isSword(itemStack)){
            ItemMeta im = itemStack.getItemMeta();
            im.spigot().setUnbreakable(true);
            itemStack.setItemMeta(im);
        }
        this.itemStack = itemStack;
        this.permanent = permanent;
        this.autoequip = autoequip;
        if (!identifier.isEmpty()){
            this.itemStack = nms.addCustomData(this.itemStack, identifier);
        }
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
