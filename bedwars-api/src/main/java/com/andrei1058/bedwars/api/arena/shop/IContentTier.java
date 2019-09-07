package com.andrei1058.bedwars.api.arena.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IContentTier {

    /**
     * Get tier price
     */
    int getPrice();

    /**
     * Get tier currency.
     *
     * {@link Material#AIR} for vault.
     */
    Material getCurrency();


    /**
     * Set tier currency.
     * {@link Material#AIR} for vault.
     */
    void setCurrency(Material currency);

    /**
     * Set tier price.
     */
    void setPrice(int price);

    /**
     * Set tier preview item.
     */
    void setItemStack(ItemStack itemStack);

    /**
     * Set list of items that you receive on buy.
     */
    void setBuyItemsList(List<IBuyItem> buyItemsList);

    /**
     * Get item stack with name and lore in player's language
     */
    ItemStack getItemStack();
    /**
     * Get tier level
     */
    int getValue();

    /**
     * Get items
     */
    List<IBuyItem> getBuyItemsList();
}
