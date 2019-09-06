package com.andrei1058.bedwars.shop.listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.quickbuy.PlayerQuickBuyCache;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ShopOpenListener implements Listener {

    @EventHandler
    public void onShopOpen(PlayerInteractAtEntityEvent e){
        IArena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a == null) return;
        Location l = e.getRightClicked().getLocation();
        for (ITeam t : a.getTeams()) {
            Location l2 = t.getShop();
            if (l.getBlockX() == l2.getBlockX() && l.getBlockY() == l2.getBlockY() && l.getBlockZ() == l2.getBlockZ()) {
                e.setCancelled(true);
                if (a.isPlayer(e.getPlayer())) {
                    ShopManager.shop.open(e.getPlayer(), PlayerQuickBuyCache.getQuickBuyCache(e.getPlayer().getUniqueId()),true);
                }
            }
        }
    }
}
