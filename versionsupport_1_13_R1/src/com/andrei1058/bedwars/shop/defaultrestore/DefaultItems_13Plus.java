package com.andrei1058.bedwars.shop.defaultrestore;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

// Used to restore default swords and bows if they are removed from the inventory and you remain with a less powerful weapon of the same kind. 1.13+.
public class DefaultItems_13Plus implements Listener {


    /**
     * Remove the default swords/ bow if the picked item is more powerful.
     */
    @EventHandler
    public void onPickUp(EntityPickupItemEvent e) {
        if (e.isCancelled()) return;
        if (!(e.getEntity() instanceof Player)) return;
        if (Arena.getArenaByPlayer((Player) e.getEntity()) == null) return;
        if (e.getItem() == null) return;
        if (e.getItem().getItemStack() == null) return;
        if (Arena.getArenaByPlayer((Player) e.getEntity()).getStatus() != GameState.playing) return;
        if (!Arena.getArenaByPlayer((Player) e.getEntity()).isPlayer((Player) e.getEntity())) return;

        if (Main.nms.isSword(e.getItem().getItemStack())) {
            for (ItemStack is : ((Player) e.getEntity()).getInventory()) {
                if (is == null) continue;
                if (is.getType() == Material.AIR) continue;
                if (!Main.nms.isCustomBedWarsItem(is)) continue;
                if (!Main.nms.getCustomData(is).equalsIgnoreCase("DEFAULT_ITEM")) continue;

                if (Main.nms.isSword(e.getItem().getItemStack())) {
                    if (Main.nms.getDamage(e.getItem().getItemStack()) >= Main.nms.getDamage(is)) {
                        ((Player) e.getEntity()).getInventory().remove(is);
                        break;
                    }
                }
            }
            e.setCancelled(true);
            e.getItem().remove();
        }
    }

    /**
     * If the dropped sword/ bow is a default item and is more powerful
     * than the others in the inventory give it back.
     * <p>
     * If the player remains without a sword give it the swords from the default items.
     * If the player remains without a bow give it bows from the default items.
     */
    @EventHandler
    public void onDrop(EntityDropItemEvent e) {
        if (e.isCancelled()) return;
        if (e.getItemDrop() == null) return;
        if (!(e.getEntity() instanceof Player)) return;
        if (Arena.getArenaByPlayer((Player) e.getEntity()) == null) return;
        Arena a = Arena.getArenaByPlayer((Player) e.getEntity());
        if (a.getStatus() != GameState.playing) return;
        if (!a.isPlayer((Player) e.getEntity())) return;
        if (e.getItemDrop().getItemStack() == null) return;
        if (Main.nms.isCustomBedWarsItem(e.getItemDrop().getItemStack())
                && Main.nms.getCustomData(e.getItemDrop().getItemStack()).equalsIgnoreCase("DEFAULT_ITEM")
                && Main.nms.isSword(e.getItemDrop().getItemStack())) {
            boolean hasSword = false;
            for (ItemStack is : ((Player) e.getEntity()).getInventory()) {
                if (is == null) continue;
                if (Main.nms.isSword(is)) {
                    if (Main.nms.getDamage(is) >= Main.nms.getDamage(e.getItemDrop().getItemStack())) {
                        hasSword = true;
                        break;
                    }
                }
            }

            if (!hasSword) {
                e.setCancelled(true);
            }
        } else {
            boolean sword = false;
            for (ItemStack is : ((Player) e.getEntity()).getInventory()) {
                if (is == null) continue;
                if (Main.nms.isSword(is)) {
                    sword = true;
                    break;
                }
            }
            if (!sword) a.getTeam(((Player) e.getEntity())).defaultSword((Player) e.getEntity(), true);
        }

    }

    /**
     * If the player moves a default sword or bow into another inventory
     * and he remains with a less powerful weapon restore the lost one.
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getType() == InventoryType.PLAYER) return;
        if (Arena.getArenaByPlayer((Player) e.getPlayer()) == null) return;
        Arena a = Arena.getArenaByPlayer((Player) e.getPlayer());
        if (a.getStatus() != GameState.playing) return;
        if (!a.isPlayer((Player) e.getPlayer())) return;

        boolean sword = false;
        for (ItemStack is : e.getPlayer().getInventory()) {
            if (is == null) continue;
            if (is.getType() == Material.AIR) continue;
            if (Main.nms.isSword(is)) sword = true;
        }

        if (!sword) a.getTeam((Player) e.getPlayer()).defaultSword((Player) e.getPlayer(), true);
    }
}
