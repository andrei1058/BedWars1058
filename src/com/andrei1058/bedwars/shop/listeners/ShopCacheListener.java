package com.andrei1058.bedwars.shop.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.api.events.PlayerJoinArenaEvent;
import com.andrei1058.bedwars.api.events.PlayerLeaveArenaEvent;
import com.andrei1058.bedwars.shop.ShopCache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ShopCacheListener implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void onArenaJoin(PlayerJoinArenaEvent e) {
        if (e.isSpectator()) return;
        if (ShopCache.getShopCache(e.getPlayer()) != null) {
            ShopCache.getShopCache(e.getPlayer()).destroy();
        }
        new ShopCache(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onArenaLeave(PlayerLeaveArenaEvent e) {
        if (ShopCache.getShopCache(e.getPlayer()) != null) {
            ShopCache.getShopCache(e.getPlayer()).destroy();
        }
    }

    @EventHandler
    public void onServerLeave(PlayerQuitEvent e) {
        if (Main.getServerType() == ServerType.BUNGEE) return;
        //don't remove immediately in case of /rejoin
        if (ShopCache.getShopCache(e.getPlayer()) != null) {
            ShopCache.getShopCache(e.getPlayer()).destroy();
        }
    }

}
