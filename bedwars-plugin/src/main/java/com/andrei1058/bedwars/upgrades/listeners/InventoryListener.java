package com.andrei1058.bedwars.upgrades.listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import com.andrei1058.bedwars.api.upgrades.MenuContent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.isCancelled()) return;
        if (!(e.getWhoClicked() instanceof Player)) return;
        IArena a = Arena.getArenaByPlayer((Player) e.getWhoClicked());
        if (a == null) return;
        if (a.isSpectator((Player) e.getWhoClicked())) return;
        if (!UpgradesManager.isWatchingUpgrades(e.getWhoClicked().getUniqueId())) return;
        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getType() == Material.AIR) return;

        MenuContent mc = UpgradesManager.getMenuContent(e.getCurrentItem());
        if (mc == null) return;
        mc.onClick((Player) e.getWhoClicked(), e.getClick(), a.getTeam((Player) e.getWhoClicked()));
    }

    @EventHandler
    public void onUpgradesClose(InventoryCloseEvent e) {
        UpgradesManager.removeWatchingUpgrades(e.getPlayer().getUniqueId());
    }
}
