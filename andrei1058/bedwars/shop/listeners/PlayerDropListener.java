package com.andrei1058.bedwars.shop.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDropListener implements Listener {

    @EventHandler
    //Prevent from dropping permanent items
    public void onDrop(PlayerDropItemEvent e) {
        Arena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a == null) return;
        String identifier = Main.nms.getShopUpgradeIdentifier(e.getItemDrop().getItemStack());
        if (identifier.isEmpty() || identifier.equals(" ")) return;
        e.setCancelled(true);
    }

    @EventHandler
    //Prevent from moving items in chests
    public void onClose(InventoryCloseEvent e) {
        if (!(e instanceof Player)) return;
        Arena a = Arena.getArenaByPlayer((Player) e.getPlayer());
        if (a == null) return;
        String identifier;
        for (ItemStack i : e.getInventory()) {
            if (i == null) continue;
            if (i.getType() == Material.AIR) continue;
            identifier = Main.nms.getShopUpgradeIdentifier(i);
            if (identifier.isEmpty() || identifier.equals(" ")) return;
        }
    }
}
