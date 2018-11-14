package com.andrei1058.bedwars.shop.listeners;

import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.ShopCache;
import com.andrei1058.bedwars.shop.main.CategoryContent;
import com.andrei1058.bedwars.shop.main.ShopCategory;
import com.andrei1058.bedwars.shop.main.ShopIndex;
import com.andrei1058.bedwars.shop.quickbuy.PlayerQuickBuyCache;
import com.andrei1058.bedwars.shop.quickbuy.QuickBuyAdd;
import com.andrei1058.bedwars.shop.quickbuy.QuickBuyElement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (e.isCancelled()) return;
        ShopCache shopCache = ShopCache.getShopCache((Player) e.getWhoClicked());
        PlayerQuickBuyCache cache = PlayerQuickBuyCache.getQuickBuyCache(e.getWhoClicked().getUniqueId());
        if (cache == null) return;
        if (shopCache == null) return;
        if (ShopIndex.getIndexViewers().contains(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
            for (ShopCategory sc : ShopManager.getShop().getCategoryList()) {
                if (e.getSlot() == sc.getSlot()) {
                    sc.open((Player) e.getWhoClicked(), ShopManager.getShop(), shopCache);
                    return;
                }
            }
            for (QuickBuyElement element : cache.getElements()){
                if (element.getSlot() == e.getSlot()){
                    element.getCategoryContent().execute((Player) e.getWhoClicked(), ShopCache.getShopCache((Player) e.getWhoClicked()));
                    return;
                }
            }
        } else if (ShopCategory.getCategoryViewers().contains(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
            for (ShopCategory sc : ShopManager.getShop().getCategoryList()) {
                if (ShopManager.getShop().getQuickBuyButton().getSlot() == e.getSlot()){
                    ShopManager.getShop().open((Player) e.getWhoClicked(), cache, false);
                    return;
                }
                if (e.getSlot() == sc.getSlot()) {
                    sc.open((Player) e.getWhoClicked(), ShopManager.getShop(), shopCache);
                    return;
                }
                if (sc.getSlot() != shopCache.getSelectedCategory()) continue;
                for (CategoryContent cc : sc.getCategoryContentList()) {
                    if (cc.getSlot() == e.getSlot()) {
                        if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY){
                            new QuickBuyAdd((Player) e.getWhoClicked(), cc);
                            return;
                        }
                        cc.execute((Player) e.getWhoClicked(), ShopCache.getShopCache((Player) e.getWhoClicked()));
                        return;
                    }
                }
            }
        } else if (QuickBuyAdd.getQuickBuyAdds().containsKey(e.getWhoClicked().getUniqueId())){
            e.setCancelled(true);
            boolean add = false;
            for (int i : PlayerQuickBuyCache.quickSlots){
                if (i == e.getSlot()){
                    add = true;
                }
            }
            if (!add) return;
            CategoryContent cc = QuickBuyAdd.getQuickBuyAdds().get(e.getWhoClicked().getUniqueId());
            if (cc != null){
                cache.setElement(e.getSlot(), cc);
            }
            e.getWhoClicked().closeInventory();
        }
    }

    @EventHandler
    public void onShopClose(InventoryCloseEvent e){
        ShopIndex.indexViewers.remove(e.getPlayer().getUniqueId());
        ShopCategory.categoryViewers.remove(e.getPlayer().getUniqueId());
        QuickBuyAdd.quickBuyAdds.remove(e.getPlayer().getUniqueId());
    }
}
