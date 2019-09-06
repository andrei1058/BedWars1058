package com.andrei1058.bedwars.api.arena.shop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ICategoryContent {

    /**
     * Get content slot in category
     */
    int getSlot();

    /**
     * Get content preview item in player's language
     */
    ItemStack getItemStack(Player player);

    /**
     * Check if a player has this cc to quick buy
     */
    boolean hasQuick(Player player);

    /**
     * Check if is permanent content.
     */
    boolean isPermanent();

    /**
     * Check if is downgradable.
     */
    boolean isDowngradable();

    /**
     * Get category identifier.
     */
    String getIdentifier();

    /**
     * Get content tiers.
     */
    List<IContentTier> getContentTiers();
}
