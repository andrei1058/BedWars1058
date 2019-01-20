package com.andrei1058.bedwars.shop;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.shop.main.CategoryContent;
import com.andrei1058.bedwars.shop.main.ShopCategory;
import com.andrei1058.bedwars.shop.quickbuy.PlayerQuickBuyCache;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopCache {

    private Player player;
    private List<CachedItem> cachedItems = new ArrayList<>();
    private ShopCache cache;
    private int selectedCategory;
    private HashMap<ShopCategory, Byte> categoryWeight = new HashMap<>();

    private static List<ShopCache> shopCaches = new ArrayList<>();

    public ShopCache(Player player) {
        this.player = player;
        this.cache = this;
        this.selectedCategory = ShopManager.getShop().getQuickBuyButton().getSlot();
        this.shopCaches.add(this);
    }

    public Player getPlayer() {
        return player;
    }

    public void setSelectedCategory(int slot) {
        this.selectedCategory = slot;
    }

    public int getSelectedCategory() {
        return selectedCategory;
    }

    public int getContentTier(String identifier) {
        CachedItem ci = getCachedItem(identifier);
        return ci == null ? 1 : ci.getTier();
    }

    public static ShopCache getShopCache(Player player) {
        for (ShopCache sc : new ArrayList<>(shopCaches)) {
            if (sc.getPlayer() == player) return sc;
        }
        return null;
    }

    /**
     * Destroy data
     */
    public void destroy() {
        cachedItems.clear();
        shopCaches.remove(this);
    }

    /**
     * Used to give items on player respawn
     */
    public void managePermanentsAndDowngradables() {
        Main.debug("Restore permanents on death for: " + player.getName());
        for (CachedItem ci : cachedItems) {
            ci.manageDeath();
        }
    }

    /**
     * Keep trace of shop items and player's tiers
     */
    public class CachedItem {
        private CategoryContent cc;
        private int tier = 1;

        public CachedItem(CategoryContent cc) {
            this.cc = cc;
            cachedItems.add(this);
            Main.debug("New Cached item " + cc.getIdentifier() + " for player " + player.getName());
        }

        public int getTier() {
            return tier;
        }

        public CategoryContent getCc() {
            return cc;
        }

        /**
         * Give permanents on death
         * and downgrade if necessary
         */
        public void manageDeath() {
            if (!cc.isPermanent()) return;
            if (cc.isDowngradable() && tier > 1) tier--;
            cc.giveItems(player, cache);
        }

        public void upgrade() {
            tier++;
            for (ItemStack i : player.getInventory().getContents()) {
                if (i == null) continue;
                if (i.getType() == Material.AIR) continue;
                if (Main.nms.getShopUpgradeIdentifier(i).equals(cc.getIdentifier())) {
                    player.getInventory().remove(i);
                }
            }
            updateItem();
            player.updateInventory();
        }

        public void updateItem() {
            if (player.getOpenInventory() != null) {
                if (player.getOpenInventory().getTopInventory() != null) {
                    player.getOpenInventory().getTopInventory().setItem(cc.getSlot(), cc.getItemStack(player, cache));
                }
            }
        }
    }

    /**
     * Get a player's cached item
     */
    public CachedItem getCachedItem(String identifier) {
        for (CachedItem ci : cachedItems) {
            if (ci.getCc().getIdentifier().equals(identifier)) return ci;
        }
        return null;
    }

    /**
     * Check if the player has a cached item
     */
    public boolean hasCachedItem(CategoryContent cc) {
        for (CachedItem ci : cachedItems) {
            if (ci.getCc() == cc) return true;
        }
        return false;
    }

    public CachedItem getCachedItem(CategoryContent cc) {
        for (CachedItem ci : cachedItems) {
            if (ci.getCc() == cc) return ci;
        }
        return null;
    }

    /**
     * Upgrade cached item
     * Add it if not found
     */
    public void upgradeCachedItem(CategoryContent cc) {
        CachedItem ci = getCachedItem(cc.getIdentifier());
        if (ci == null) {
            ci = new CachedItem(cc);
            ci.updateItem();
        } else {
            if (cc.getContentTiers().size() > ci.getTier()) {
                Main.debug("Cached item upgrade for " + cc.getIdentifier() + " player " + player.getName());
                ci.upgrade();
            }
        }
    }

    /**
     * Used for categories where you can't buy lower items
     * Ex. if you have bought diamond iron from it, you can't buy stone iron
     */
    public void setCategoryWeight(ShopCategory sc, byte weight) {
        if (categoryWeight.containsKey(sc)){
            categoryWeight.replace(sc, weight);
        } else {
            categoryWeight.put(sc, weight);
        }
    }

    public byte getCategoryWeight(ShopCategory sc){
        return categoryWeight.getOrDefault(sc, (byte)0);
    }
}
