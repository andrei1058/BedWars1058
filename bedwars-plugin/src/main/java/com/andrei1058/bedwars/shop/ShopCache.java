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

package com.andrei1058.bedwars.shop;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.shop.main.CategoryContent;
import com.andrei1058.bedwars.shop.main.ShopCategory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ShopCache {

    private UUID player;
    private List<CachedItem> cachedItems = new LinkedList<>();
    private int selectedCategory;
    private HashMap<ShopCategory, Byte> categoryWeight = new HashMap<>();

    private static List<ShopCache> shopCaches = new ArrayList<>();

    public ShopCache(UUID player) {
        this.player = player;
        this.selectedCategory = ShopManager.getShop().getQuickBuyButton().getSlot();
        shopCaches.add(this);
    }

    public UUID getPlayer() {
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

    public static ShopCache getShopCache(UUID player) {
        for (ShopCache sc : new ArrayList<>(shopCaches)) {
            if (sc.player.equals(player)) return sc;
        }
        return null;
    }

    /**
     * Destroy data
     */
    public void destroy() {
        shopCaches.remove(this);
        cachedItems.clear();
        cachedItems = null;
        categoryWeight = null;
    }

    /**
     * Used to give items on player respawn
     */
    public void managePermanentsAndDowngradables(Arena arena) {
        BedWars.debug("Restore permanents on death for: " + player);
        for (CachedItem ci : cachedItems){
            ci.manageDeath(arena);
        }
    }

    /**
     * Keep trace of shop items and player's tiers
     */
    @SuppressWarnings("WeakerAccess")
    public class CachedItem {
        private CategoryContent cc;
        private int tier = 1;

        public CachedItem(CategoryContent cc) {
            this.cc = cc;
            cachedItems.add(this);
            BedWars.debug("New Cached item " + cc.getIdentifier() + " for player " + player);
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
        public void manageDeath(Arena arena) {
            if (!cc.isPermanent()) return;
            if (cc.isDowngradable() && tier > 1) tier--;
            BedWars.debug("ShopCache Item Restore: " + cc.getIdentifier() + " for " + player);
            //noinspection ConstantConditions
            cc.giveItems(Bukkit.getPlayer(player), getShopCache(player), arena);
        }

        public void upgrade(int slot) {
            tier++;
            Player p = Bukkit.getPlayer(player);
            for (ItemStack i : p.getInventory().getContents()) {
                if (i == null) continue;
                if (i.getType() == Material.AIR) continue;
                if (BedWars.nms.getShopUpgradeIdentifier(i).equals(cc.getIdentifier())) {
                    p.getInventory().remove(i);
                }
            }
            updateItem(slot, p);
            p.updateInventory();
        }

        public void updateItem(int slot, Player p) {
            if (p.getOpenInventory() != null) {
                if (p.getOpenInventory().getTopInventory() != null) {
                    p.getOpenInventory().getTopInventory().setItem(slot, cc.getItemStack(Bukkit.getPlayer(player), getShopCache(player)));
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
    public void upgradeCachedItem(CategoryContent cc, int slot) {
        CachedItem ci = getCachedItem(cc.getIdentifier());
        if (ci == null) {
            ci = new CachedItem(cc);
            ci.updateItem(slot, Bukkit.getPlayer(player));
        } else {
            if (cc.getContentTiers().size() > ci.getTier()) {
                BedWars.debug("Cached item upgrade for " + cc.getIdentifier() + " player " + player);
                ci.upgrade(slot);
            }
        }
    }

    /**
     * Used for categories where you can't buy lower items
     * Ex. if you have bought diamond iron from it, you can't buy stone iron
     */
    public void setCategoryWeight(ShopCategory sc, byte weight) {
        if (categoryWeight.containsKey(sc)) {
            categoryWeight.replace(sc, weight);
        } else {
            categoryWeight.put(sc, weight);
        }
    }

    public byte getCategoryWeight(ShopCategory sc) {
        return categoryWeight.getOrDefault(sc, (byte) 0);
    }

    /**
     * Get permanent and non downgradable shop items.
     */
    public List<CachedItem> getCachedPermanents() {
        List<CachedItem> ci = new ArrayList<>();
        for (CachedItem c : cachedItems){
            if (c.getCc().isPermanent()){
                ci.add(c);
            }
        }
        return ci;
    }

    public List<CachedItem> getCachedItems() {
        return cachedItems;
    }
}
