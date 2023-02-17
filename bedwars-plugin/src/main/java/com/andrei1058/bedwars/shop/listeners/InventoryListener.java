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

package com.andrei1058.bedwars.shop.listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.shop.ShopCache;
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.main.CategoryContent;
import com.andrei1058.bedwars.shop.main.ShopCategory;
import com.andrei1058.bedwars.shop.main.ShopIndex;
import com.andrei1058.bedwars.shop.quickbuy.PlayerQuickBuyCache;
import com.andrei1058.bedwars.shop.quickbuy.QuickBuyAdd;
import com.andrei1058.bedwars.shop.quickbuy.QuickBuyElement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

import static com.andrei1058.bedwars.BedWars.nms;
import static org.bukkit.event.inventory.InventoryAction.HOTBAR_SWAP;
import static org.bukkit.event.inventory.InventoryAction.MOVE_TO_OTHER_INVENTORY;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.isCancelled()) return;
        if (!(e.getWhoClicked() instanceof Player)) return;

        Player p = (Player) e.getWhoClicked();

        IArena a = Arena.getArenaByPlayer(p);
        if (a == null) return;
        if (a.isSpectator(p)) return;

        ShopCache shopCache = ShopCache.getShopCache(p.getUniqueId());
        PlayerQuickBuyCache cache = PlayerQuickBuyCache.getQuickBuyCache(p.getUniqueId());

        if (cache == null) return;
        if (shopCache == null) return;

        if(ShopIndex.getIndexViewers().contains(p.getUniqueId()) || ShopCategory.getCategoryViewers().contains(p.getUniqueId())) {
            if (e.getClickedInventory() != null && e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                e.setCancelled(true);
                return;
            }
        }

        if (ShopIndex.getIndexViewers().contains(p.getUniqueId())) {
            e.setCancelled(true);

            for (ShopCategory sc : ShopManager.getShop().getCategoryList()) {
                if (e.getSlot() == sc.getSlot()) {
                    sc.open(p, ShopManager.getShop(), shopCache);
                    return;
                }
            }
            for (QuickBuyElement element : cache.getElements()) {
                if (element.getSlot() == e.getSlot()) {
                    if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                        cache.setElement(element.getSlot(), null);
                        p.closeInventory();
                        return;
                    }
                    element.getCategoryContent().execute(p, shopCache, element.getSlot());
                    return;
                }
            }
        } else if (ShopCategory.getCategoryViewers().contains(p.getUniqueId())) {
            e.setCancelled(true);
            for (ShopCategory sc : ShopManager.getShop().getCategoryList()) {
                if (ShopManager.getShop().getQuickBuyButton().getSlot() == e.getSlot()) {
                    ShopManager.getShop().open(p, cache, false);
                    return;
                }
                if (e.getSlot() == sc.getSlot()) {
                    sc.open(p, ShopManager.getShop(), shopCache);
                    return;
                }
                if (sc.getSlot() != shopCache.getSelectedCategory()) continue;
                for (CategoryContent cc : sc.getCategoryContentList()) {
                    if (cc.getSlot() == e.getSlot()) {
                        if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                            if (cache.hasCategoryContent(cc)) return;
                            new QuickBuyAdd(p, cc);
                            return;
                        }
                        cc.execute(p, shopCache, cc.getSlot());
                        return;
                    }
                }
            }
        } else if (QuickBuyAdd.getQuickBuyAdds().containsKey(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
            boolean add = false;
            for (int i : PlayerQuickBuyCache.quickSlots) {
                if (i == e.getSlot()) {
                    add = true;
                }
            }
            if (!add) return;
            CategoryContent cc = QuickBuyAdd.getQuickBuyAdds().get(e.getWhoClicked().getUniqueId());
            if (cc != null) {
                cache.setElement(e.getSlot(), cc);
            }
            e.getWhoClicked().closeInventory();
        }
    }

    @EventHandler
    public void onUpgradableMove(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        ShopCache sc = ShopCache.getShopCache(p.getUniqueId());
        if (sc == null) return;

        //block moving from hotbar
        if (e.getAction() == HOTBAR_SWAP && e.getClick() == ClickType.NUMBER_KEY) {
            if (e.getHotbarButton() > -1) {
                ItemStack i = e.getWhoClicked().getInventory().getItem(e.getHotbarButton());
                if (i != null) {
                    if (e.getClickedInventory() != e.getWhoClicked().getInventory()) {
                        if (shouldCancelMovement(i, sc)) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }

        //block moving cursor item
        if (e.getCursor() != null) {
            if (e.getCursor().getType() != Material.AIR) {
                if (e.getClickedInventory() == null) {
                    if (shouldCancelMovement(e.getCursor(), sc)) {
                        e.getWhoClicked().closeInventory();
                        e.setCancelled(true);
                    }
                } else if (e.getClickedInventory().getType() != e.getWhoClicked().getInventory().getType()) {
                    if (shouldCancelMovement(e.getCursor(), sc)) {
                        e.getWhoClicked().closeInventory();
                        e.setCancelled(true);
                    }
                }
            }
        }

        //block moving current item
        if (e.getCurrentItem() != null) {
            if (e.getCurrentItem().getType() != Material.AIR) {
                if (e.getClickedInventory() == null) {
                    if (shouldCancelMovement(e.getCursor(), sc)) {
                        e.getWhoClicked().closeInventory();
                        e.setCancelled(true);
                    }
                } else if (e.getClickedInventory().getType() != e.getWhoClicked().getInventory().getType()) {
                    if (shouldCancelMovement(e.getCurrentItem(), sc)) {
                        e.getWhoClicked().closeInventory();
                        e.setCancelled(true);
                    }
                }
            }
        }

        //block moving with shift
        if (e.getAction() == MOVE_TO_OTHER_INVENTORY) {
            if (shouldCancelMovement(e.getCurrentItem(), sc)) {
                if (e.getView().getTopInventory().getHolder() != null && e.getInventory().getHolder() == e.getWhoClicked())
                    return;
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onShopClose(InventoryCloseEvent e) {
        ShopIndex.indexViewers.remove(e.getPlayer().getUniqueId());
        ShopCategory.categoryViewers.remove(e.getPlayer().getUniqueId());
        QuickBuyAdd.quickBuyAdds.remove(e.getPlayer().getUniqueId());
    }

    /**
     * Check can move item outside inventory.
     * Block despawnable, permanent and start items dropping and inventory change.
     */
    public static boolean shouldCancelMovement(ItemStack i, ShopCache sc) {
        if (i == null) return false;
        if (sc == null) return false;

        if (nms.isCustomBedWarsItem(i)){
            if (nms.getCustomData(i).equalsIgnoreCase("DEFAULT_ITEM")){
                return true;
            }
        }

        String identifier = nms.getShopUpgradeIdentifier(i);
        if (identifier == null) return false;
        if (identifier.equals("null")) return false;
        ShopCache.CachedItem cachedItem = sc.getCachedItem(identifier);
        return cachedItem != null;
        // the commented line bellow was blocking movement only if tiers amount > 1
        // return sc.getCachedItem(identifier).getCc().getContentTiers().size() > 1;
    }
}
