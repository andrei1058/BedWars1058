package com.andrei1058.bedwars.shop.listeners;

import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.ShopCache;
import com.andrei1058.bedwars.shop.main.CategoryContent;
import com.andrei1058.bedwars.shop.main.ShopCategory;
import com.andrei1058.bedwars.shop.main.ShopIndex;
import com.andrei1058.bedwars.shop.quickbuy.PlayerQuickBuyCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (e.isCancelled()) return;
        if (ShopIndex.indexViewers.contains(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
            for (ShopCategory sc : ShopManager.getShop().getCategoryList()) {
                if (e.getSlot() == sc.getSlot()) {
                    sc.open((Player) e.getWhoClicked(), ShopManager.getShop(), ShopCache.getShopCache((Player) e.getWhoClicked()));
                    return;
                }
            }
        } else if (ShopCategory.categoryViewers.contains(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
            for (ShopCategory sc : ShopManager.getShop().getCategoryList()) {
                if (ShopManager.getShop().getQuickBuyButton().getSlot() == e.getSlot()){
                    ShopManager.getShop().open((Player) e.getWhoClicked(), PlayerQuickBuyCache.getQyickBuyCache(e.getWhoClicked().getUniqueId()), false);
                    return;
                }
                for (CategoryContent cc : sc.getCategoryContentList()) {
                    if (cc.getSlot() == e.getSlot()) {
                        cc.execute((Player) e.getWhoClicked(), ShopCache.getShopCache((Player) e.getWhoClicked()));
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onShopClose(InventoryCloseEvent e){
        ShopIndex.indexViewers.remove(e.getPlayer().getUniqueId());
        ShopCategory.categoryViewers.remove(e.getPlayer().getUniqueId());
    }
}
