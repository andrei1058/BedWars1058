package com.andrei1058.bedwars.shop.main;

import com.andrei1058.bedwars.api.language.Language;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QuickBuyButton {

    private int slot;
    private ItemStack itemStack;
    private String namePath, lorePath;

    /**
     * Create a new quick buy button
     *
     * @param namePath  Language name path
     * @param lorePath  Language lore path.
     * @param slot      Item slot in inventory
     * @param itemStack Button ItemStack preview
     */
    public QuickBuyButton(int slot, ItemStack itemStack, String namePath, String lorePath) {
        this.slot = slot;
        this.itemStack = itemStack;
        this.namePath = namePath;
        this.lorePath = lorePath;
    }

    /**
     * Get the quick buy button in the player's language
     */
    public ItemStack getItemStack(Player player) {
        ItemStack i = itemStack.clone();
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Language.getMsg(player, namePath));
        im.setLore(Language.getList(player, lorePath));
        i.setItemMeta(im);
        return i;
    }

    /**
     * Get quick buy item slot
     */
    public int getSlot() {
        return slot;
    }
}
