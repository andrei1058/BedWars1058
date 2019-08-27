package com.andrei1058.bedwars.shop.main;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.shop.ShopManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class ContentTier {

    private int value, price;
    private ItemStack itemStack;
    private Material currency;
    private List<BuyItem> buyItemsList = new ArrayList<>();
    private boolean loaded = false;

    /**
     * Create a content tier for a category content
     */
    public ContentTier(String path, String tierName, String identifier, YamlConfiguration yml) {
        Main.debug("Loading content tier" + path);

        if (yml.get(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_MATERIAL) == null) {
            Main.plugin.getLogger().severe("tier-item material not set at " + path);
            return;
        }

        try {
            value = Integer.parseInt(tierName.replace("tier", ""));
        } catch (Exception e) {
            Main.plugin.getLogger().severe(path + " doesn't end with a number. It's not recognized as a tier!");
            return;
        }

        if (yml.get(path + ConfigPath.SHOP_CONTENT_TIER_SETTINGS_COST) == null) {
            Main.plugin.getLogger().severe("Cost not set for " + path);
            return;
        }
        price = yml.getInt(path + ConfigPath.SHOP_CONTENT_TIER_SETTINGS_COST);

        if (yml.get(path + ConfigPath.SHOP_CONTENT_TIER_SETTINGS_CURRENCY) == null) {
            Main.plugin.getLogger().severe("Currency not set for " + path);
            return;
        }

        if (yml.getString(path + ConfigPath.SHOP_CONTENT_TIER_SETTINGS_CURRENCY).toLowerCase().isEmpty()) {
            Main.plugin.getLogger().severe("Invalid currency at " + path);
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
        }

        itemStack = Main.nms.createItemStack(yml.getString(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_MATERIAL),
                yml.get(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_AMOUNT) == null ? 1 : yml.getInt(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_AMOUNT),
                (short) (yml.get(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_DATA) == null ? 0 : yml.getInt(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_DATA)));


        if (yml.get(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_ENCHANTED) != null) {
            if (yml.getBoolean(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_ENCHANTED)) {
                itemStack = ShopManager.enchantItem(itemStack);
            }
        }

        itemStack.setItemMeta(ShopManager.hideItemStuff(itemStack.getItemMeta()));

        BuyItem bi;
        for (String s : yml.getConfigurationSection(path + "." + ConfigPath.SHOP_CONTENT_BUY_ITEMS_PATH).getKeys(false)) {
            bi = new BuyItem(path + "." + ConfigPath.SHOP_CONTENT_BUY_ITEMS_PATH + "." + s, yml, identifier);
            if (bi.isLoaded()) buyItemsList.add(bi);
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
    public void setBuyItemsList(List<BuyItem> buyItemsList) {
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
    public List<BuyItem> getBuyItemsList() {
        return buyItemsList;
    }
}
