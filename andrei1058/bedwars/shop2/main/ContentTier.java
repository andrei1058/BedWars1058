package com.andrei1058.bedwars.shop2.main;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.language.Messages;
import com.andrei1058.bedwars.configuration.shop.ShopManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ContentTier {

    private int value, price;
    private ItemStack itemStack;
    private String itemNamePath, itemLorePath, currency = "";
    private List<BuyItem> buyItemsList = new ArrayList<>();
    private boolean loaded = false;

    /**
     * Create a content tier for a category content
     */
    public ContentTier(String path, String tierName, String contentName, YamlConfiguration yml) {
        Main.debug("Loading " + path);

        if (yml.get(path + ".tier-item.material") == null) {
            Main.plugin.getLogger().severe("tier-item material not set at " + path);
            return;
        }

        try {
            value = Integer.valueOf(tierName.replace("tier", ""));
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

        switch (yml.getString(path + ConfigPath.SHOP_CONTENT_TIER_SETTINGS_CURRENCY).toLowerCase()) {
            case "iron":
            case "gold":
            case "diamond":
            case "vault":
            case "emerald":
                currency = yml.getString(path + ConfigPath.SHOP_CONTENT_TIER_SETTINGS_CURRENCY).toLowerCase();
                break;
        }

        if (currency.isEmpty()) {
            Main.plugin.getLogger().severe("Invalid currency at " + path);
            return;
        }

        itemNamePath = Messages.SHOP_CONTENT_TIER_ITEM_NAME.replace("%category%", path.split(".")[0]).replace("%tier%", tierName).replace("%content%", contentName);
        if (!Main.lang.exists(itemNamePath)) {
            for (Language lang : Language.getLanguages()) {
                if (!lang.exists(itemNamePath)) {
                    lang.set(itemNamePath, "&cName not set");
                }
            }
        }
        itemLorePath = Messages.SHOP_CONTENT_TIER_ITEM_LORE.replace("%category%", path.split(".")[0]).replace("%tier%", tierName).replace("%content%", contentName);
        if (!Main.lang.exists(itemLorePath)) {
            for (Language lang : Language.getLanguages()) {
                if (!lang.exists(itemLorePath)) {
                    lang.set(itemLorePath, "&cLore not set");
                }
            }
        }

        itemStack = Main.nms.createItemStack(yml.getString(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_MATERIAL),
                yml.get(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_AMOUNT) == null ? 1 : yml.getInt(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_AMOUNT),
                (short) (yml.get(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_DATA) == null ? 0 : yml.getInt(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_DATA)));


        if (yml.get(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_ENCHANTED) != null) {
            if (yml.getBoolean(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_ENCHANTED)) {
                itemStack = ShopManager.enchantItem(itemStack);
            }
        }

        BuyItem bi;
        for (String s : yml.getConfigurationSection(path + "." + ConfigPath.SHOP_CONTENT_BUY_ITEMS_PATH).getKeys(false)) {
            bi = new BuyItem(path + "." + s, yml, path.replace("." + tierName, ""), getValue());
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
    public String getCurrency() {
        return currency;
    }

    /**
     * Get item stack with name and lore in player's language
     */
    public ItemStack getItemStack(Player player) {
        ItemStack i = itemStack.clone();
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Language.getMsg(player, itemNamePath));
        im.setLore(Language.getList(player, itemLorePath));
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
}
