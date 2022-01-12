/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.shop.main;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.shop.IBuyItem;
import com.andrei1058.bedwars.api.arena.shop.IContentTier;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.shop.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.BedWars.nms;

@SuppressWarnings("WeakerAccess")
public class ContentTier implements IContentTier {

    private int value, price;
    private ItemStack itemStack;
    private Material currency;
    private List<IBuyItem> buyItemsList = new ArrayList<>();
    private boolean loaded = false;

    /**
     * Create a content tier for a category content
     */
    public ContentTier(String path, String tierName, String identifier, YamlConfiguration yml) {
        BedWars.debug("Loading content tier" + path);

        if (yml.get(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_MATERIAL) == null) {
            BedWars.plugin.getLogger().severe("tier-item material not set at " + path);
            return;
        }

        try {
            value = Integer.parseInt(tierName.replace("tier", ""));
        } catch (Exception e) {
            BedWars.plugin.getLogger().severe(path + " doesn't end with a number. It's not recognized as a tier!");
            return;
        }

        if (yml.get(path + ConfigPath.SHOP_CONTENT_TIER_SETTINGS_COST) == null) {
            BedWars.plugin.getLogger().severe("Cost not set for " + path);
            return;
        }
        price = yml.getInt(path + ConfigPath.SHOP_CONTENT_TIER_SETTINGS_COST);

        if (yml.get(path + ConfigPath.SHOP_CONTENT_TIER_SETTINGS_CURRENCY) == null) {
            BedWars.plugin.getLogger().severe("Currency not set for " + path);
            return;
        }

        if (yml.getString(path + ConfigPath.SHOP_CONTENT_TIER_SETTINGS_CURRENCY).isEmpty()) {
            BedWars.plugin.getLogger().severe("Invalid currency at " + path);
            return;
        }

        switch (yml.getString(path + ConfigPath.SHOP_CONTENT_TIER_SETTINGS_CURRENCY).toLowerCase()) {
            case "iron":
            case "gold":
            case "diamond":
            case "vault":
            case "emerald":
                currency = CategoryContent.getCurrency(yml.getString(path + ConfigPath.SHOP_CONTENT_TIER_SETTINGS_CURRENCY).toLowerCase());
                break;
            default:
                BedWars.plugin.getLogger().severe("Invalid currency at " + path);
                currency = Material.IRON_INGOT;
                break;
        }

        itemStack = BedWars.nms.createItemStack(yml.getString(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_MATERIAL),
                yml.get(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_AMOUNT) == null ? 1 : yml.getInt(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_AMOUNT),
                (short) (yml.get(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_DATA) == null ? 0 : yml.getInt(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_DATA)));


        if (yml.get(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_ENCHANTED) != null) {
            if (yml.getBoolean(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_ENCHANTED)) {
                itemStack = ShopManager.enchantItem(itemStack);
            }
        }

        // potion display color based on NBT tag
        if (yml.getString(path + ".tier-item.potion-display") != null && !yml.getString(path + ".tier-item.potion-display").isEmpty()) {
            itemStack = nms.setTag(itemStack, "Potion", yml.getString(path + ".tier-item.potion-display"));
        }
        // 1.16+ custom color
        if (yml.getString(path + ".tier-item.potion-color") != null && !yml.getString(path + ".tier-item.potion-color").isEmpty()) {
            itemStack = nms.setTag(itemStack, "CustomPotionColor", yml.getString(path + ".tier-item.potion-color"));
        }

        if (itemStack != null) {
            itemStack.setItemMeta(ShopManager.hideItemStuff(itemStack.getItemMeta()));
        }

        IBuyItem bi;
        if (yml.get(path + "." + ConfigPath.SHOP_CONTENT_BUY_ITEMS_PATH) != null) {
            for (String s : yml.getConfigurationSection(path + "." + ConfigPath.SHOP_CONTENT_BUY_ITEMS_PATH).getKeys(false)) {
                bi = new BuyItem(path + "." + ConfigPath.SHOP_CONTENT_BUY_ITEMS_PATH + "." + s, yml, identifier, this);
                if (bi.isLoaded()) buyItemsList.add(bi);
            }
        }
        if (yml.get(path + "." + ConfigPath.SHOP_CONTENT_BUY_CMDS_PATH) != null) {
            bi = new BuyCommand(path + "." + ConfigPath.SHOP_CONTENT_BUY_CMDS_PATH, yml, identifier);
            if (bi.isLoaded()) buyItemsList.add(bi);
        }

        if (buyItemsList.isEmpty()) {
            Bukkit.getLogger().warning("Loaded 0 buy content for: " + path);
        }

        loaded = true;
    }

    /**
     * Get tier price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Get tier currency
     */
    public Material getCurrency() {
        return currency;
    }

    /**
     * Set tier currency.
     */
    public void setCurrency(Material currency) {
        this.currency = currency;
    }

    /**
     * Set tier price.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Set tier preview item.
     */
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Set list of items that you receive on buy.
     */
    public void setBuyItemsList(List<IBuyItem> buyItemsList) {
        this.buyItemsList = buyItemsList;
    }

    /**
     * Get item stack with name and lore in player's language
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Get tier level
     */
    public int getValue() {
        return value;
    }

    /**
     * Check if tier is loaded
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Get items
     */
    public List<IBuyItem> getBuyItemsList() {
        return buyItemsList;
    }
}
