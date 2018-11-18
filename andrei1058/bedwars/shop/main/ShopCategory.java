package com.andrei1058.bedwars.shop.main;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.language.Messages;
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.ShopCache;
import com.andrei1058.bedwars.shop.quickbuy.PlayerQuickBuyCache;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopCategory {

    private int slot;
    private ItemStack itemStack;
    private String itemNamePath, itemLorePath, invNamePath;
    private boolean loaded = false;
    private List<CategoryContent> categoryContentList = new ArrayList<>();
    public static List<UUID> categoryViewers = new ArrayList<>();

    /**
     * Load a shop category from the given path
     */
    public ShopCategory(String path, YamlConfiguration yml) {
        Main.debug("Loading shop category: " + path);

        if (yml.get(path + ConfigPath.SHOP_CATEGORY_ITEM_MATERIAL) == null) {
            Main.plugin.getLogger().severe("Category material not set at: " + path);
            return;
        }

        if (yml.get(path + ConfigPath.SHOP_CATEGORY_SLOT) == null) {
            Main.plugin.getLogger().severe("Category slot not set at: " + path);
            return;
        }
        slot = yml.getInt(path + ConfigPath.SHOP_CATEGORY_SLOT);

        if (slot < 1 || slot > 7) {
            Main.plugin.getLogger().severe("Slot must be n > 1 and n < 8 at: " + path);
            return;
        }

        for (ShopCategory sc : ShopManager.shop.getCategoryList()){
            if (sc.getSlot() == slot){
                Main.plugin.getLogger().severe("Slot is already in use at: " + path);
                return;
            }
        }

        itemStack = Main.nms.createItemStack(yml.getString(path + ConfigPath.SHOP_CATEGORY_ITEM_MATERIAL),
                yml.get(path + ConfigPath.SHOP_CATEGORY_ITEM_AMOUNT) == null ? 1 : yml.getInt(path + ConfigPath.SHOP_CATEGORY_ITEM_AMOUNT),
                (short) (yml.get(path + ConfigPath.SHOP_CATEGORY_ITEM_DATA) == null ? 0 : yml.getInt(path + ConfigPath.SHOP_CATEGORY_ITEM_DATA)));


        if (yml.get(path + ConfigPath.SHOP_CATEGORY_ITEM_ENCHANTED) != null) {
            if (yml.getBoolean(path + ConfigPath.SHOP_CATEGORY_ITEM_ENCHANTED)) {
                itemStack = ShopManager.enchantItem(itemStack);
            }
        }

        itemStack.setItemMeta(ShopManager.hideItemStuff(itemStack.getItemMeta()));

        itemNamePath = Messages.SHOP_CATEGORY_ITEM_NAME.replace("%category%", path);
        itemLorePath = Messages.SHOP_CATEGORY_ITEM_LORE.replace("%category%", path);
        invNamePath = Messages.SHOP_CATEGORY_INVENTORY_NAME.replace("%category%", path);

        CategoryContent cc;
        for (String s : yml.getConfigurationSection(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_PATH).getKeys(false)) {
            cc = new CategoryContent(path + ConfigPath.SHOP_CATEGORY_CONTENT_PATH + "." + s, s, path, yml);
            if (cc.isLoaded()) {
                categoryContentList.add(cc);
                Main.debug("Adding CategoryContent: " + s + " to Shop Category: " + path);
            }
        }

        loaded = true;
    }

    public void open(Player player, ShopIndex index, ShopCache shopCache){
        if (player.getOpenInventory().getTopInventory() == null) return;
        ShopIndex.indexViewers.remove(player.getUniqueId());

        Inventory inv = Bukkit.createInventory(null, index.getInvSize(), Language.getMsg(player, invNamePath));

        inv.setItem(index.getQuickBuyButton().getSlot(), index.getQuickBuyButton().getItemStack(player));

        for (ShopCategory sc : index.getCategoryList()) {
            inv.setItem(sc.getSlot(), sc.getItemStack(player));
        }

        index.addSeparator(player, inv);

        inv.setItem(getSlot() + 9, index.getSelectedItem(player));

        shopCache.setSelectedCategory(getSlot());

        for (CategoryContent cc : getCategoryContentList()) {
            inv.setItem(cc.getSlot(), cc.getItemStack(player, shopCache));
        }

        player.openInventory(inv);
        if (!categoryViewers.contains(player.getUniqueId())){
            categoryViewers.add(player.getUniqueId());
        }
    }

    /**
     * Get the category preview item in player's language
     */
    public ItemStack getItemStack(Player player) {
        ItemStack i = itemStack.clone();
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Language.getMsg(player, itemNamePath));
        im.setLore(Language.getList(player, itemLorePath));
        i.setItemMeta(im);
        return i;
    }

    /**
     * Check if category was loaded
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Get category slot in shop index
     */
    public int getSlot() {
        return slot;
    }

    public List<CategoryContent> getCategoryContentList() {
        return categoryContentList;
    }

    /**Get a category content by identifier*/
    public static CategoryContent getCategoryContent(String identifier, ShopIndex shopIndex){
        for (ShopCategory sc : shopIndex.getCategoryList()){
            for (CategoryContent cc : sc.getCategoryContentList()){
                if (cc.getIdentifier().equals(identifier)) return cc;
            }
        }
        return null;
    }

    public static List<UUID> getCategoryViewers() {
        return new ArrayList<>(categoryViewers);
    }
}
