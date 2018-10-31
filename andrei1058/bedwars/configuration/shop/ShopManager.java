package com.andrei1058.bedwars.configuration.shop;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigManager;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.language.Messages;
import com.andrei1058.bedwars.shop2.main.QuickBuyButton;
import com.andrei1058.bedwars.shop2.main.ShopCategory;
import com.andrei1058.bedwars.shop2.main.ShopIndex;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopManager {

    public static ShopIndex shop;

    private ConfigManager configManager;
    private YamlConfiguration yml;

    public ShopManager() {
        this.configManager = new ConfigManager("shop2", "plugins/" + Main.plugin.getDescription().getName(), false);
        this.yml = configManager.getYml();
        saveDefaults();
        loadShop();
    }

    private void saveDefaults() {
        yml.options().header("Shop with quick buy and tiers");

        yml.addDefault("", "");

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

        ShopCategory sc;
        for (String s : yml.getConfigurationSection("").getKeys(false)) {
            if (s.equalsIgnoreCase(ConfigPath.SHOP_SETTINGS_PATH)) continue;
            sc = new ShopCategory(s, yml);
            if (sc.isLoaded()) {
                shop.addShopCategory(sc);
                Main.debug("Adding shop category: " + s + " to the main shop.");
            }
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
}
