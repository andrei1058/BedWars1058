package com.andrei1058.bedwars.shop.quickbuy;

import com.andrei1058.bedwars.shop.ShopCache;
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.main.CategoryContent;
import com.andrei1058.bedwars.shop.main.ShopCategory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class QuickBuyAdd {

    public static HashMap<UUID, CategoryContent> quickBuyAdds = new HashMap<>();

    public QuickBuyAdd(Player player, CategoryContent cc){
        ShopCategory.categoryViewers.remove(player.getUniqueId());
        open(player, cc);
    }

    public void open(Player player, CategoryContent cc){
        Inventory inv = Bukkit.createInventory(null, ShopManager.getShop().getInvSize());
        PlayerQuickBuyCache cache = PlayerQuickBuyCache.getQuickBuyCache(player.getUniqueId());
        ShopCache sc = ShopCache.getShopCache(player);
        if (sc == null || cache == null){
            player.closeInventory();
        }
        inv.setItem(4, cc.getItemStack(player, sc));

        cache.addInInventory(inv, sc);

        player.openInventory(inv);
        quickBuyAdds.put(player.getUniqueId(), cc);
    }

    public static HashMap<UUID, CategoryContent> getQuickBuyAdds() {
        return new HashMap<>(quickBuyAdds);
    }
}
