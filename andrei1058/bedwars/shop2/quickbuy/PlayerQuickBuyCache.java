package com.andrei1058.bedwars.shop2.quickbuy;

import com.andrei1058.bedwars.configuration.Language;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerQuickBuyCache {

    private List<QuickBuyElement> elements = new ArrayList<>();
    private String emptyItemNamePath, emptyItemLorePath;
    private ItemStack emptyItem;
    private Player player;

    public PlayerQuickBuyCache(Player player, ItemStack emptyItem, String emptyItemNamePath, String emptyItemLorePath) {
        this.player = player;
        this.emptyItem = emptyItem;
        this.emptyItemNamePath = emptyItemNamePath;
        this.emptyItemLorePath = emptyItemLorePath;
    }


    /**
     * Add the player's preferences to the given inventory.
     * This will also add the red empty item.
     */
    public void addInInventory(Inventory inv) {

        for (QuickBuyElement qbe : elements) {
            inv.setItem(qbe.getSlot(), qbe.getCategoryContent().getItemStack(player));
        }

        if (elements.size() == 21) return;

        ItemStack i = getEmptyItem(player);
        for (int x = 19; x < 26; x++) {
            if (inv.getItem(x) == null) {
                inv.setItem(x, i);
            }
        }
        for (int x = 28; x < 35; x++) {
            if (inv.getItem(x) == null) {
                inv.setItem(x, i);
            }
        }
        for (int x = 37; x < 43; x++) {
            if (inv.getItem(x) == null) {
                inv.setItem(x, i);
            }
        }
    }

    private ItemStack getEmptyItem(Player player) {
        ItemStack i = emptyItem.clone();
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Language.getMsg(player, emptyItemNamePath));
        im.setLore(Language.getList(player, emptyItemLorePath));
        i.setItemMeta(im);
        return i;
    }
}
