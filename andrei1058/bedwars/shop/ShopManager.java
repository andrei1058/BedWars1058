package com.andrei1058.bedwars.shop;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigManager;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.language.Messages;
import com.andrei1058.bedwars.shop.listeners.*;
import com.andrei1058.bedwars.shop.main.QuickBuyButton;
import com.andrei1058.bedwars.shop.main.ShopCategory;
import com.andrei1058.bedwars.shop.main.ShopIndex;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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

        yml.addDefault(ConfigPath.SHOP_SETTINGS_QUICK_BUY_EMPTY_MATERIAL, Main.getForCurrentVersion("STAINED_GLASS_PANE", "STAINED_GLASS_PANE", "RED_STAINED_GLASS_PANE"));
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
        yml.addDefault(ConfigPath.SHOP_SPECIAL_SILVERFISH_HEALTH, 8.0);
        yml.addDefault(ConfigPath.SHOP_SPECIAL_SILVERFISH_DAMAGE, 4.0);
        yml.addDefault(ConfigPath.SHOP_SPECIAL_SILVERFISH_SPEED, 0.25);
        yml.addDefault(ConfigPath.SHOP_SPECIAL_SILVERFISH_DESPAWN, 15);

        yml.addDefault(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_ENABLE, true);
        yml.addDefault(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_MATERIAL, Main.getForCurrentVersion("MONSTER_EGG", "MONSTER_EGG", "HORSE_SPAWN_EGG"));
        yml.addDefault(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_DATA, 0);
        yml.addDefault(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_HEALTH, 100.0);
        yml.addDefault(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_DESPAWN, 240);
        yml.addDefault(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_SPEED, 0.25);

        if (configManager.isFirstTime()) {
            //quick buy defaults
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element1.path", "blocks-category.category-content.wool");
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element1.slot", 19);
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element2.path", "melee-category.category-content.stone-sword");
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element2.slot", 20);
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element3.path", "armor-category.category-content.chainmail");
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element3.slot", 21);
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element4.path", "ranged-category.category-content.bow1");
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element4.slot", 23);
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element5.path", "potions-category.category-content.speed-potion");
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element5.slot", 24);
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element6.path", "utility-category.category-content.tnt");
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element6.slot", 25);

            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element7.path", "blocks-category.category-content.wood");
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element7.slot", 28);
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element8.path", "melee-category.category-content.iron-sword");
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element8.slot", 29);
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element9.path", "armor-category.category-content.iron-armor");
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element9.slot", 30);
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element10.path", "tools-category.category-content.shears");
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element10.slot", 31);
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element11.path", "ranged-category.category-content.arrow");
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element11.slot", 32);
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element12.path", "potions-category.category-content.jump-potion");
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element12.slot", 33);
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element13.path", "utility-category.category-content.water-bucket");
            yml.addDefault(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + ".element13.slot", 34);
        }

        //save default shop categories if the file was just generated
        //so the user can remove categories or add new ones
        if (configManager.isFirstTime()) {
            //BLOCKS CATEGORY
            addDefaultShopCategory(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, 1, Main.getForCurrentVersion("STAINED_CLAY", "STAINED_CLAY", "ORANGE_TERRACOTTA"), 1, 1, false);

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
                    0, 16, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "wood", 24, "tier1",
                    Main.getForCurrentVersion("WOOD", "WOOD", "OAK_WOOD"), 0, 16, false, 4, "gold", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "wood", "tier1", "wood", Main.getForCurrentVersion("WOOD", "WOOD", "OAK_WOOD"),
                    0, 16, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "obsidian", 25, "tier1",
                    Main.getForCurrentVersion("OBSIDIAN", "OBSIDIAN", "OBSIDIAN"), 0, 4, false, 4, "emerald", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "obsidian", "tier1", "obsidian", Main.getForCurrentVersion("OBSIDIAN", "OBSIDIAN", "OBSIDIAN"),
                    0, 4, "", "", "", false);
            //

            //MELEE CATEGORY
            addDefaultShopCategory(ConfigPath.SHOP_PATH_CATEGORY_MELEE, 2, Main.getForCurrentVersion("GOLD_SWORD", "GOLD_SWORD", "GOLDEN_SWORD"), 0, 1, false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_MELEE, "stone-sword", 19, "tier1",
                    Main.getForCurrentVersion("STONE_SWORD", "STONE_SWORD", "STONE_SWORD"), 0, 1, false, 10, "iron", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_MELEE, "stone-sword", "tier1", "sword", Main.getForCurrentVersion("STONE_SWORD", "STONE_SWORD", "STONE_SWORD"),
                    0, 1, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_MELEE, "iron-sword", 20, "tier1",
                    Main.getForCurrentVersion("IRON_SWORD", "IRON_SWORD", "IRON_SWORD"), 0, 1, false, 7, "gold", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_MELEE, "iron-sword", "tier1", "sword", Main.getForCurrentVersion("IRON_SWORD", "IRON_SWORD", "IRON_SWORD"),
                    0, 1, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_MELEE, "diamond-sword", 21, "tier1",
                    Main.getForCurrentVersion("DIAMOND_SWORD", "DIAMOND_SWORD", "DIAMOND_SWORD"), 0, 1, false, 4, "emerald", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_MELEE, "diamond-sword", "tier1", "sword", Main.getForCurrentVersion("DIAMOND_SWORD", "DIAMOND_SWORD", "DIAMOND_SWORD"),
                    0, 1, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_MELEE, "stick", 22, "tier1",
                    Main.getForCurrentVersion("STICK", "STICK", "STICK"), 0, 1, true, 10, "gold", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_MELEE, "stick", "tier1", "stick", Main.getForCurrentVersion("STICK", "STICK", "STICK"),
                    0, 1, "KNOCKBACK 1", "", "", false);

            //ARMOR CATEGORY
            addDefaultShopCategory(ConfigPath.SHOP_PATH_CATEGORY_ARMOR, 3, Main.getForCurrentVersion("CHAINMAIL_BOOTS", "CHAINMAIL_BOOTS", "CHAINMAIL_BOOTS"), 0, 1, false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "chainmail", 19, "tier1",
                    Main.getForCurrentVersion("CHAINMAIL_BOOTS", "CHAINMAIL_BOOTS", "CHAINMAIL_BOOTS"), 0, 1, false, 40, "iron", true, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "chainmail", "tier1", "boots", Main.getForCurrentVersion("CHAINMAIL_BOOTS", "CHAINMAIL_BOOTS", "CHAINMAIL_BOOTS"),
                    0, 1, "", "", "", true);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "chainmail", "tier1", "leggings", Main.getForCurrentVersion("CHAINMAIL_LEGGINGS", "CHAINMAIL_LEGGINGS", "CHAINMAIL_LEGGINGS"),
                    0, 1, "", "", "", true);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "iron-armor", 20, "tier1",
                    Main.getForCurrentVersion("IRON_BOOTS", "IRON_BOOTS", "IRON_BOOTS"), 0, 1, false, 12, "gold", true, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "iron-armor", "tier1", "boots", Main.getForCurrentVersion("IRON_BOOTS", "IRON_BOOTS", "IRON_BOOTS"),
                    0, 1, "", "", "", true);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "iron-armor", "tier1", "leggings", Main.getForCurrentVersion("IRON_LEGGINGS", "IRON_LEGGINGS", "IRON_LEGGINGS"),
                    0, 1, "", "", "", true);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "diamond-armor", 21, "tier1",
                    Main.getForCurrentVersion("DIAMOND_BOOTS", "DIAMOND_BOOTS", "DIAMOND_BOOTS"), 0, 1, false, 6, "emerald", true, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "diamond-armor", "tier1", "boots", Main.getForCurrentVersion("DIAMOND_BOOTS", "DIAMOND_BOOTS", "DIAMOND_BOOTS"),
                    0, 1, "", "", "", true);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "diamond-armor", "tier1", "leggings", Main.getForCurrentVersion("DIAMOND_LEGGINGS", "DIAMOND_LEGGINGS", "DIAMOND_LEGGINGS"),
                    0, 1, "", "", "", true);

            //TOOLS CATEGORY
            addDefaultShopCategory(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, 4, Main.getForCurrentVersion("STONE_PICKAXE", "STONE_PICKAXE", "STONE_PICKAXE"), 0, 1, false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "shears", 19, "tier1",
                    Main.getForCurrentVersion("SHEARS", "SHEARS", "SHEARS"), 0, 1, false, 20, "iron", true, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "shears", "tier1", "shears", Main.getForCurrentVersion("SHEARS", "SHEARS", "SHEARS"),
                    0, 1, "", "", "", false);

            //pickaxe
            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "pickaxe", 20, "tier1",
                    Main.getForCurrentVersion("WOOD_PICKAXE", "WOOD_PICKAXE", "WOODEN_PICKAXE"), 0, 1, false, 10, "iron", true, true);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "pickaxe", "tier1", "wooden-pickaxe", Main.getForCurrentVersion("WOOD_PICKAXE", "WOOD_PICKAXE", "WOODEN_PICKAXE"),
                    0, 1, "", "", "", false);
            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "pickaxe", 20, "tier2",
                    Main.getForCurrentVersion("IRON_PICKAXE", "IRON_PICKAXE", "IRON_PICKAXE"), 0, 1, true, 10, "iron", true, true);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "pickaxe", "tier2", "iron-pickaxe", Main.getForCurrentVersion("IRON_PICKAXE", "IRON_PICKAXE", "IRON_PICKAXE"),
                    0, 1, "DIG_SPEED 2", "", "", false);
            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "pickaxe", 20, "tier3",
                    Main.getForCurrentVersion("GOLD_PICKAXE", "GOLD_PICKAXE", "GOLDEN_PICKAXE"), 0, 1, true, 3, "gold", true, true);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "pickaxe", "tier3", "gold-pickaxe", Main.getForCurrentVersion("GOLD_PICKAXE", "GOLD_PICKAXE", "GOLDEN_PICKAXE"),
                    0, 1, "DIG_SPEED 3,DAMAGE_ALL 2", "", "", false);
            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "pickaxe", 20, "tier4",
                    Main.getForCurrentVersion("DIAMOND_PICKAXE", "DIAMOND_PICKAXE", "DIAMOND_PICKAXE"), 0, 1, true, 6, "gold", true, true);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "pickaxe", "tier4", "diamond-pickaxe", Main.getForCurrentVersion("DIAMOND_PICKAXE", "DIAMOND_PICKAXE", "DIAMOND_PICKAXE"),
                    0, 1, "DIG_SPEED 3", "", "", false);

            //axe
            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "axe", 21, "tier1",
                    Main.getForCurrentVersion("WOOD_AXE", "WOOD_AXE", "WOODEN_AXE"), 0, 1, false, 10, "iron", true, true);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "axe", "tier1", "wooden-axe", Main.getForCurrentVersion("WOOD_AXE", "WOOD_AXE", "WOODEN_AXE"),
                    0, 1, "DIG_SPEED 1", "", "", false);
            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "axe", 21, "tier2",
                    Main.getForCurrentVersion("IRON_AXE", "IRON_AXE", "IRON_AXE"), 0, 1, true, 10, "iron", true, true);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "axe", "tier2", "iron-axe", Main.getForCurrentVersion("IRON_AXE", "IRON_AXE", "IRON_AXE"),
                    0, 1, "DIG_SPEED 1", "", "", false);
            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "axe", 21, "tier3",
                    Main.getForCurrentVersion("GOLD_AXE", "GOLD_AXE", "GOLDEN_AXE"), 0, 1, true, 3, "gold", true, true);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "axe", "tier3", "gold-axe", Main.getForCurrentVersion("GOLD_AXE", "GOLD_AXE", "GOLDEN_AXE"),
                    0, 1, "DIG_SPEED 2", "", "", false);
            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "axe", 21, "tier4",
                    Main.getForCurrentVersion("DIAMOND_AXE", "DIAMOND_AXE", "DIAMOND_AXE"), 0, 1, true, 6, "gold", true, true);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "axe", "tier4", "diamond-axe", Main.getForCurrentVersion("DIAMOND_AXE", "DIAMOND_AXE", "DIAMOND_AXE"),
                    0, 1, "DIG_SPEED 3", "", "", false);

            //RANGED CATEGORY
            addDefaultShopCategory(ConfigPath.SHOP_PATH_CATEGORY_RANGED, 5, Main.getForCurrentVersion("BOW", "BOW", "BOW"), 0, 1, false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_RANGED, "arrow", 19, "tier1",
                    Main.getForCurrentVersion("ARROW", "ARROW", "ARROW"), 0, 8, false, 2, "gold", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_RANGED, "arrow", "tier1", "arrows", Main.getForCurrentVersion("ARROW", "ARROW", "ARROW"),
                    0, 8, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_RANGED, "bow1", 20, "tier1",
                    Main.getForCurrentVersion("BOW", "BOW", "BOW"), 0, 1, false, 12, "gold", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_RANGED, "bow1", "tier1", "bow", Main.getForCurrentVersion("BOW", "BOW", "BOW"),
                    0, 1, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_RANGED, "bow2", 21, "tier1",
                    Main.getForCurrentVersion("BOW", "BOW", "BOW"), 0, 1, true, 24, "gold", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_RANGED, "bow2", "tier1", "bow", Main.getForCurrentVersion("BOW", "BOW", "BOW"),
                    0, 1, "ARROW_DAMAGE 1", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_RANGED, "bow3", 22, "tier1",
                    Main.getForCurrentVersion("BOW", "BOW", "BOW"), 0, 1, true, 6, "emerald", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_RANGED, "bow3", "tier1", "bow", Main.getForCurrentVersion("BOW", "BOW", "BOW"),
                    0, 1, "ARROW_DAMAGE 1,ARROW_KNOCKBACK 1", "", "", false);

            //POTIONS CATEGORY
            addDefaultShopCategory(ConfigPath.SHOP_PATH_CATEGORY_POTIONS, 6, Main.getForCurrentVersion("BREWING_STAND_ITEM", "BREWING_STAND_ITEM", "BREWING_STAND"), 0, 1, false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "jump-potion", 20, "tier1",
                    Main.getForCurrentVersion("POTION", "POTION", "POTION"), 0, 1, false, 1, "emerald", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "jump-potion", "tier1", "jump", Main.getForCurrentVersion("POTION", "POTION", "POTION"),
                    0, 1, "", "JUMP 45 5", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "speed-potion", 19, "tier1",
                    Main.getForCurrentVersion("POTION", "POTION", "POTION"), 0, 1, false, 1, "emerald", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "speed-potion", "tier1", "speed", Main.getForCurrentVersion("POTION", "POTION", "POTION"),
                    0, 1, "", "SPEED 45 2", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "invisibility", 21, "tier1",
                    Main.getForCurrentVersion("POTION", "POTION", "POTION"), 0, 1, false, 2, "emerald", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "invisibility", "tier1", "invisibility", Main.getForCurrentVersion("POTION", "POTION", "POTION"),
                    0, 1, "", "INVISIBILITY 30 1", "", false);

            //UTILITY CATEGORY
            addDefaultShopCategory(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, 7, Main.getForCurrentVersion("TNT", "TNT", "TNT"), 0, 1, false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "golden-apple", 19, "tier1",
                    Main.getForCurrentVersion("GOLDEN_APPLE", "GOLDEN_APPLE", "GOLDEN_APPLE"), 0, 1, false, 3, "gold", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "golden-apple", "tier1", "apple", Main.getForCurrentVersion("GOLDEN_APPLE", "GOLDEN_APPLE", "GOLDEN_APPLE"),
                    0, 1, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "bedbug", 20, "tier1",
                    Main.getForCurrentVersion("SNOW_BALL", "SNOW_BALL", "SNOWBALL"), 0, 1, false, 40, "iron", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "bedbug", "tier1", "bedbug", Main.getForCurrentVersion("SNOW_BALL", "SNOWBALL", "SNOWBALL"),
                    0, 1, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "dream-defender", 21, "tier1",
                    Main.getForCurrentVersion("MONSTER_EGG", "MONSTER_EGG", "HORSE_SPAWN_EGG"), 0, 1, false, 120, "iron", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "dream-defender", "tier1", "defender", Main.getForCurrentVersion("MONSTER_EGG", "MONSTER_EGG", "HORSE_SPAWN_EGG"),
                    0, 1, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "fireball", 22, "tier1",
                    Main.getForCurrentVersion("FIREBALL", "FIREBALL", "FIRE_CHARGE"), 0, 1, false, 40, "iron", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "fireball", "tier1", "fireball", Main.getForCurrentVersion("FIREBALL", "FIREBALL", "FIRE_CHARGE"),
                    0, 1, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "tnt", 23, "tier1",
                    Main.getForCurrentVersion("TNT", "TNT", "TNT"), 0, 1, false, 4, "gold", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "tnt", "tier1", "tnt", Main.getForCurrentVersion("TNT", "TNT", "TNT"),
                    0, 1, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "ender-pearl", 24, "tier1",
                    Main.getForCurrentVersion("ENDER_PEARL", "ENDER_PEARL", "ENDER_PEARL"), 0, 1, false, 4, "emerald", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "ender-pearl", "tier1", "ender-pearl", Main.getForCurrentVersion("ENDER_PEARL", "ENDER_PEARL", "ENDER_PEARL"),
                    0, 1, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "water-bucket", 25, "tier1",
                    Main.getForCurrentVersion("WATER_BUCKET", "WATER_BUCKET", "WATER_BUCKET"), 0, 1, false, 4, "gold", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "water-bucket", "tier1", "water-bucket", Main.getForCurrentVersion("WATER_BUCKET", "WATER_BUCKET", "WATER_BUCKET"),
                    0, 1, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "bridge-egg", 28, "tier1",
                    Main.getForCurrentVersion("EGG", "EGG", "EGG"), 0, 1, false, 3, "emerald", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "bridge-egg", "tier1", "egg", Main.getForCurrentVersion("EGG", "EGG", "EGG"),
                    0, 1, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "magic-milk", 29, "tier1",
                    Main.getForCurrentVersion("MILK_BUCKET", "MILK_BUCKET", "MILK_BUCKET"), 0, 1, false, 4, "gold", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "magic-milk", "tier1", "milk", Main.getForCurrentVersion("MILK_BUCKET", "MILK_BUCKET", "MILK_BUCKET"),
                    0, 1, "", "", "", false);

            adCategoryContentTier(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "sponge", 29, "tier1",
                    Main.getForCurrentVersion("SPONGE", "SPONGE", "SPONGE"), 0, 1, false, 3, "gold", false, false);
            addBuyItem(ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "sponge", "tier1", "sponge", Main.getForCurrentVersion("SPONGE", "SPONGE", "SPONGE"),
                    0, 1, "", "", "", false);

        }

        //try materials
        try {
            String material = yml.getString(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_MATERIAL);
            Main.debug(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_MATERIAL + " is set to: " + material);
            Material.valueOf(material);
        } catch (Exception ex) {
            Main.plugin.getLogger().severe("Invalid material at " + ConfigPath.SHOP_SPECIAL_IRON_GOLEM_MATERIAL);
        }
        try {
            String material = yml.getString(ConfigPath.SHOP_SPECIAL_SILVERFISH_MATERIAL);
            Main.debug(ConfigPath.SHOP_SPECIAL_SILVERFISH_MATERIAL + " is set to: " + material);
            Material.valueOf(material);
        } catch (Exception ex) {
            Main.plugin.getLogger().severe("Invalid material at " + ConfigPath.SHOP_SPECIAL_SILVERFISH_MATERIAL);
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
            if (s.equals(ConfigPath.SHOP_QUICK_DEFAULTS_PATH)) continue;
            if (s.equalsIgnoreCase(ConfigPath.SHOP_SPECIALS_PATH)) continue;
            shop.addShopCategory(new ShopCategory(s, yml));
        }
    }

    /**
     * Hide an item details
     */
    public static ItemMeta hideItemStuff(ItemMeta im) {
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON);
        return im;
    }

    /**
     * Enchant item stack and hide details
     */
    public static ItemStack enchantItem(ItemStack itemStack) {
        ItemStack i = new ItemStack(itemStack);
        ItemMeta im = i.getItemMeta();
        im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        i.setItemMeta(hideItemStuff(im));
        return i;
    }

    /**
     * Initialize a shop category to config
     */
    private void addDefaultShopCategory(String path, int slot, String material, int data, int amount, boolean enchant) {
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
