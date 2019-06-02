package com.andrei1058.bedwars.shop.quickbuy;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.language.Messages;
import com.andrei1058.bedwars.shop.ShopCache;
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.main.CategoryContent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerQuickBuyCache {

    private List<QuickBuyElement> elements = new ArrayList<>();
    private String emptyItemNamePath, emptyItemLorePath;
    private ItemStack emptyItem;
    private Player player;
    private QuickBuyTask task;

    public static int[] quickSlots = new int[]{19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
    private static List<PlayerQuickBuyCache> quickBuyCaches = new ArrayList<>();

    public PlayerQuickBuyCache(Player player) {
        if (player == null) return;
        this.player = player;
        this.emptyItem = Main.nms.createItemStack(Main.shop.getYml().getString(ConfigPath.SHOP_SETTINGS_QUICK_BUY_EMPTY_MATERIAL),
                Main.shop.getYml().getInt(ConfigPath.SHOP_SETTINGS_QUICK_BUY_EMPTY_AMOUNT),
                (short) Main.shop.getYml().getInt(ConfigPath.SHOP_SETTINGS_QUICK_BUY_EMPTY_DATA));
        if (Main.shop.getYml().getBoolean(ConfigPath.SHOP_SETTINGS_QUICK_BUY_EMPTY_ENCHANTED)) {
            this.emptyItem = ShopManager.enchantItem(emptyItem);
        }
        this.emptyItemNamePath = Messages.SHOP_QUICK_EMPTY_NAME;
        this.emptyItemLorePath = Messages.SHOP_QUICK_EMPTY_LORE;
        task = new QuickBuyTask(player.getUniqueId());
        quickBuyCaches.add(this);
    }


    /**
     * Add the player's preferences to the given inventory.
     * This will also add the red empty item.
     */
    public void addInInventory(Inventory inv, ShopCache shopCache) {

        for (QuickBuyElement qbe : elements) {
            inv.setItem(qbe.getSlot(), qbe.getCategoryContent().getItemStack(player, shopCache));
        }

        if (elements.size() == 21) return;

        ItemStack i = getEmptyItem(player);
        for (int x : quickSlots) {
            if (inv.getItem(x) == null) {
                inv.setItem(x, i);
            }
        }
        /*for (int x = 28; x < 35; x++) {
            if (inv.getItem(x) == null) {
                inv.setItem(x, i);
            }
        }
        for (int x = 37; x < 44; x++) {
            if (inv.getItem(x) == null) {
                inv.setItem(x, i);
            }
        }*/
    }

    public void destroy() {
        elements.clear();
        if (task != null) {
            task.cancel();
        }
        quickBuyCaches.remove(this);
    }

    public void setElement(int slot, CategoryContent cc) {
        for (QuickBuyElement q : new ArrayList<>(elements)) {
            if (q.getSlot() == slot) {
                elements.remove(q);
            }
        }
        String element;
        if (cc == null){
            element = " ";
        } else {
            addQuickElement(new QuickBuyElement(cc.getIdentifier(), slot));
            element = cc.getIdentifier();
        }
        Main.getRemoteDatabase().setQuickBuySlot(player.getUniqueId(), element, slot);
    }

    private ItemStack getEmptyItem(Player player) {
        ItemStack i = emptyItem.clone();
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Language.getMsg(player, emptyItemNamePath));
        im.setLore(Language.getList(player, emptyItemLorePath));
        i.setItemMeta(im);
        return i;
    }

    /**
     * Check if as category content at quick buy
     */
    public boolean hasCategoryContent(CategoryContent cc) {
        for (QuickBuyElement q : getElements()) {
            if (q.getCategoryContent() == cc) return true;
        }
        return false;
    }

    /**
     * Get a Player Quick buy cache
     */
    public static PlayerQuickBuyCache getQuickBuyCache(UUID uuid) {
        for (PlayerQuickBuyCache pqbc : new ArrayList<>(quickBuyCaches)) {
            if (pqbc.player.getUniqueId() == uuid) return pqbc;
        }
        return null;
    }

    public List<QuickBuyElement> getElements() {
        return elements;
    }

    /**
     * Add a quick buy element
     */
    public void addQuickElement(QuickBuyElement e) {
        this.elements.add(e);
    }
}
