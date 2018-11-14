package com.andrei1058.bedwars.shop;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigManager;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.language.Messages;
import com.andrei1058.bedwars.shop.listeners.*;
import com.andrei1058.bedwars.shop.main.QuickBuyButton;
import com.andrei1058.bedwars.shop.main.ShopCategory;
import com.andrei1058.bedwars.shop.main.ShopIndex;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;

public class ShopManager {

    public static ShopIndex shop;

    private ConfigManager configManager;
    private YamlConfiguration yml;

    public ShopManager() {
        this.configManager = new ConfigManager("shop", "plugins/" + Main.plugin.getDescription().getName(), false);
        this.yml = configManager.getYml();
        if (yml.get("main") != null) {
            configManager.setFirstTime(true);
        }
        saveDefaults();
        loadShop();
        registerListeners();
    }

    private void saveDefaults() {
        yml.options().header("Shop with quick buy and tiers");

        //quick buy
        yml.addDefault(ConfigPath.SHOP_SETTINGS_QUICK_BUY_BUTTON_MATERIAL, Main.getForCurrentVersion("NETHER_STAR", "NETHER_STAR", "NETHER_STAR"));
        yml.addDefault(ConfigPath.SHOP_SETTINGS_QUICK_BUY_BUTTON_AMOUNT, 1);
        yml.addDefault(ConfigPath.SHOP_SETTINGS_QUICK_BUY_BUTTON_DATA, 0);
        yml.addDefault(ConfigPath.SHOP_SETTINGS_QUICK_BUY_BUTTON_ENCHANTED, false);

        yml.addDefault(ConfigPath.SHOP_SETTINGS_QUICK_BUY_EMPTY_MATERIAL, Main.getForCurrentVersion("STAINED_GLASS_PANE", "STAINED_GLASS_PANE", "red_STAINED_GLASS_PANE"));
        yml.addDefault(ConfigPath.SHOP_SETTINGS_QUICK_BUY_EMPTY_AMOUNT, 1);
        yml.addDefault(ConfigPath.SHOP_SETTINGS_QUICK_BUY_EMPTY_DATA, 4);
        yml.addDefault(ConfigPath.SHOP_SETTINGS_QUICK_BUY_EMPTY_ENCHANTED, false);

        //separator
        yml.addDefault(ConfigPath.SHOP_SETTINGS_SEPARATOR_REGULAR_MATERIAL, Main.getForCurrentVersion("STAINED_GLASS_PANE", "STAINED_GLASS_PANE", "GRAY_STAINED_GLASS_PANE"));
        yml.addDefault(ConfigPath.SHOP_SETTINGS_SEPARATOR_REGULAR_AMOUNT, 1);
        yml.addDefault(ConfigPath.SHOP_SETTINGS_SEPARATOR_REGULAR_DATA, 7);
        yml.addDefault(ConfigPath.SHOP_SETTINGS_SEPARATOR_REGULAR_ENCHANTED, false);

        yml.addDefault(ConfigPath.SHOP_SETTINGS_SEPARATOR_SELECTED_MATERIAL, Main.getForCurrentVersion("STAINED_GLASS_PANE", "STAINED_GLASS_PANE", "GREEN_STAINED_GLASS_PANE"));
        yml.addDefault(ConfigPath.SHOP_SETTINGS_SEPARATOR_SELECTED_AMOUNT, 1);
        yml.addDefault(ConfigPath.SHOP_SETTINGS_SEPARATOR_SELECTED_DATA, 13);
        yml.addDefault(ConfigPath.SHOP_SETTINGS_SEPARATOR_SELECTED_ENCHANTED, false);

        //specials
        yml.addDefault(ConfigPath.SHOP_SPECIAL_SILVERFISH_ENABLE, true);
        yml.addDefault(ConfigPath.SHOP_SPECIAL_SILVERFISH_MATERIAL, Main.getForCurrentVersion("SNOW_BALL", "SNOW_BALL", "SNOWBALL"));
        yml.addDefault(ConfigPath.SHOP_SPECIAL_SILVERFISH_DATA, 0);

        yml.addDefault(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_ENABLE, true);
        yml.addDefault(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_MATERIAL, Main.getForCurrentVersion("MONSTER_EGG", "MONSTER_EGG", "MONSTER_EGG"));
        yml.addDefault(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_DATA, 0);

        //save default shop categories if the file was just generated
        //so the user can remove categories or add new ones
        if (configManager.isFirstTime()) {
            //BLOCKS CATEGORY
            addDefaultShopCategory(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, 1, Main.getForCurrentVersion("STAINED_CLAY", "STAINED_CLAY", "ORANGE_TERRACOTTA"), 1, 1, false, false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "wool", 19, "tier1",
                    Main.getForCurrentVersion("WOOL", "WOOL", "WHITE_WOOL"), 0, 16, false, 4, "iron", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "wool", "tier1", "wool", Main.getForCurrentVersion("WOOL", "WOOL", "WHITE_WOOL"),
                    0, 16, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "clay", 20, "tier1",
                    Main.getForCurrentVersion("STAINED_CLAY", "STAINED_CLAY", "ORANGE_TERRACOTTA"), 1, 16, false, 12, "iron", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "clay", "tier1", "clay", Main.getForCurrentVersion("STAINED_CLAY", "STAINED_CLAY", "ORANGE_TERRACOTTA"),
                    1, 16, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "glass", 21, "tier1",
                    Main.getForCurrentVersion("GLASS", "GLASS", "GLASS"), 0, 4, false, 12, "iron", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "glass", "tier1", "glass", Main.getForCurrentVersion("GLASS", "GLASS", "GLASS"),
                    0, 4, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "stone", 22, "tier1",
                    Main.getForCurrentVersion("ENDER_STONE", "ENDER_STONE", "END_STONE"), 0, 16, false, 24, "iron", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "stone", "tier1", "stone", Main.getForCurrentVersion("ENDER_STONE", "ENDER_STONE", "END_STONE"),
                    0, 16, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "ladder", 23, "tier1",
                    Main.getForCurrentVersion("LADDER", "LADDER", "LADDER"), 0, 16, false, 4, "iron", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "ladder", "tier1", "ladder", Main.getForCurrentVersion("LADDER", "LADDER", "LADDER"),
                    0, 16, "", "", "",  false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "wood", 24, "tier1",
                    Main.getForCurrentVersion("WOOD", "WOOD", "OAK_WOOD"), 0, 16, false, 4, "gold", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "wood", "tier1", "wood", Main.getForCurrentVersion("WOOD", "WOOD", "OAK_WOOD"),
                    0, 16, "", "", "",  false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "obsidian", 25, "tier1",
                    Main.getForCurrentVersion("OBSIDIAN", "OBSIDIAN", "OBSIDIAN"), 0, 4, false, 4, "emerald", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "obsidian", "tier1", "obsidian", Main.getForCurrentVersion("OBSIDIAN", "OBSIDIAN", "OBSIDIAN"),
                    0, 4, "", "", "",  false);
            //

            //MELEE CATEGORY

        }


        yml.options().copyDefaults(true);
        configManager.save();
    }

    private void loadShop() {
        //Quick Buy Button
        ItemStack button = Main.nms.createItemStack(yml.getString(ConfigPath.SHOP_SETTINGS_QUICK_BUY_BUTTON_MATERIAL),
                yml.getInt(ConfigPath.SHOP_SETTINGS_QUICK_BUY_BUTTON_AMOUNT), (short) yml.getInt(ConfigPath.SHOP_SETTINGS_QUICK_BUY_BUTTON_DATA));
        if (yml.getBoolean(ConfigPath.SHOP_SETTINGS_QUICK_BUY_BUTTON_ENCHANTED)) button = enchantItem(button);
        QuickBuyButton qbb = new QuickBuyButton(0, button, Messages.SHOP_QUICK_BUY_NAME, Messages.SHOP_QUICK_BUY_LORE);

        //Separator
        ItemStack separatorStandard = Main.nms.createItemStack(yml.getString(ConfigPath.SHOP_SETTINGS_SEPARATOR_REGULAR_MATERIAL),
                yml.getInt(ConfigPath.SHOP_SETTINGS_SEPARATOR_REGULAR_AMOUNT), (short) yml.getInt(ConfigPath.SHOP_SETTINGS_SEPARATOR_REGULAR_DATA));
        if (yml.getBoolean(ConfigPath.SHOP_SETTINGS_SEPARATOR_REGULAR_ENCHANTED))
            separatorStandard = enchantItem(separatorStandard);
        ItemStack separatorSelected = Main.nms.createItemStack(yml.getString(ConfigPath.SHOP_SETTINGS_SEPARATOR_SELECTED_MATERIAL),
                yml.getInt(ConfigPath.SHOP_SETTINGS_SEPARATOR_SELECTED_AMOUNT), (short) yml.getInt(ConfigPath.SHOP_SETTINGS_SEPARATOR_SELECTED_DATA));
        if (yml.getBoolean(ConfigPath.SHOP_SETTINGS_SEPARATOR_SELECTED_ENCHANTED))
            separatorSelected = enchantItem(separatorSelected);

        shop = new ShopIndex(Messages.SHOP_INDEX_NAME, qbb, Messages.SHOP_SEPARATOR_NAME, Messages.SHOP_SEPARATOR_LORE, separatorSelected, separatorStandard);

        for (String s : yml.getConfigurationSection("").getKeys(false)) {
            if (s.equalsIgnoreCase(ConfigPath.SHOP_SETTINGS_PATH)) continue;
            if (s.equalsIgnoreCase(ConfigPath.SHOP_SPECIALS_PATH)) continue;
            shop.addShopCategory(new ShopCategory(s, yml));
        }
    }

    /**
     * Hide an item details
     */
    private static void hideItemStuff(ItemMeta im) {
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON);
    }

    /**
     * Enchant item stack and hide details
     */
    public static ItemStack enchantItem(ItemStack itemStack) {
        ItemStack i = new ItemStack(itemStack);
        ItemMeta im = i.getItemMeta();
        im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        hideItemStuff(im);
        i.setItemMeta(im);
        return i;
    }

    /**
     * Initialize a shop category to config
     */
    private void addDefaultShopCategory(String path, int slot, String material, int data, int amount, boolean enchant, boolean isPermanent) {
        yml.addDefault(path + ConfigPath.SHOP_CATEGORY_SLOT, slot);
        yml.addDefault(path + ConfigPath.SHOP_CATEGORY_ITEM_MATERIAL, material);
        yml.addDefault(path + ConfigPath.SHOP_CATEGORY_ITEM_DATA, data);
        yml.addDefault(path + ConfigPath.SHOP_CATEGORY_ITEM_AMOUNT, amount);
        yml.addDefault(path + ConfigPath.SHOP_CATEGORY_ITEM_ENCHANTED, enchant);
    }

    /**
     * Create a tier for a shop content
     */
    public void adCategoryContentTier(String path, String contentName, int contentSlot, String tierName, String tierMaterial, int tierData, int amount, boolean enchant, int tierCost, String tierCurrency, boolean permanent,
                                      boolean downgradable) {
        path += ConfigPath.SHOP_CATEGORY_CONTENT_PATH + "." + contentName + ".";
        yml.addDefault(path + ConfigPath.SHOP_CATEGORY_CONTENT_CONTENT_SLOT, contentSlot);
        yml.addDefault(path + ConfigPath.SHOP_CATEGORY_CONTENT_IS_PERMANENT, permanent);
        yml.addDefault(path + ConfigPath.SHOP_CATEGORY_CONTENT_IS_DOWNGRADABLE, downgradable);
        path += ConfigPath.SHOP_CATEGORY_CONTENT_CONTENT_TIERS + "." + tierName;
        yml.addDefault(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_MATERIAL, tierMaterial);
        yml.addDefault(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_DATA, tierData);
        yml.addDefault(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_AMOUNT, amount);
        yml.addDefault(path + ConfigPath.SHOP_CONTENT_TIER_ITEM_ENCHANTED, enchant);
        yml.addDefault(path + ConfigPath.SHOP_CONTENT_TIER_SETTINGS_COST, tierCost);
        yml.addDefault(path + ConfigPath.SHOP_CONTENT_TIER_SETTINGS_CURRENCY, tierCurrency);
    }

    /**
     * Add buy items to a content tier
     */
    public void addBuyItem(String path, String contentName, String tierName, String item, String material, int data, int amount, String enchant, String potion, String itemName, boolean autoEquip) {
        path += ConfigPath.SHOP_CATEGORY_CONTENT_PATH + "." + contentName + "." + ConfigPath.SHOP_CATEGORY_CONTENT_CONTENT_TIERS + "." + tierName + "." + ConfigPath.SHOP_CONTENT_BUY_ITEMS_PATH + "." + item + ".";
        yml.addDefault(path + "material", material);
        yml.addDefault(path + "data", data);
        yml.addDefault(path + "amount", amount);
        if (!enchant.isEmpty()) {
            yml.addDefault(path + "enchants", enchant);
        }
        if (!potion.isEmpty()) {
            yml.addDefault(path + "potion", potion);
        }
        if (autoEquip) {
            yml.addDefault(path + "auto-equip", true);
        }
        if (!itemName.isEmpty()) {
            yml.addDefault(path + "name", itemName);
        }
    }

    public static ShopIndex getShop() {
        return shop;
    }

    public YamlConfiguration getYml() {
        return yml;
    }

    /**
     * Register shop related listeners
     */
    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryListener(), Main.plugin);
        pm.registerEvents(new ShopCacheListener(), Main.plugin);
        pm.registerEvents(new QuickBuyListener(), Main.plugin);
        pm.registerEvents(new ShopOpenListener(), Main.plugin);
        pm.registerEvents(new PlayerDropListener(), Main.plugin);
        pm.registerEvents(new SpecialsListener(), Main.plugin);
    }
}
