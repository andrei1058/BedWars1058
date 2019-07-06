package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.api.events.GeneratorCollectEvent;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("ALL")
public class PlayerDropPickListener implements Listener {

    /* This Class is used for versions from 1.8.8 to 1.11 included */

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if (Main.getServerType() != ServerType.BUNGEE) {
            if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase(Main.getLobbyWorld())) {
                e.setCancelled(true);
                return;
            }
        }

        Arena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a == null) return;

        if (!a.isPlayer(e.getPlayer())) {
            e.setCancelled(true);
            return;
        }

        if (a.getStatus() != GameState.playing) {
            e.setCancelled(true);
            return;
        }

        if (a.getRespawn().containsKey(e.getPlayer())) {
            e.setCancelled(true);
            return;
        }

        if (e.getItem().getItemStack().getType() == Material.ARROW){
            e.getItem().setItemStack(Main.nms.createItemStack(e.getItem().getItemStack().getType().toString(), e.getItem().getItemStack().getAmount(), (short) 0));
            return;
        }

        if (e.getItem().getItemStack().getType().toString().equals("BED")) {
            e.setCancelled(true);
            e.getItem().remove();
            return;
        } else if (e.getItem().getItemStack().hasItemMeta()) {
            if (e.getItem().getItemStack().getItemMeta().hasDisplayName()) {
                if (e.getItem().getItemStack().getItemMeta().getDisplayName().contains("custom")) {
                    Material material = e.getItem().getItemStack().getType();
                    ItemMeta itemMeta = new ItemStack(material).getItemMeta();

                    //Call ore pick up event
                    GeneratorCollectEvent event = new GeneratorCollectEvent(e.getPlayer(), e.getItem().getItemStack());
                    Bukkit.getPluginManager().callEvent(event);
                    if (event.isCancelled()){
                        e.setCancelled(true);
                    } else {
                        e.getItem().getItemStack().setItemMeta(itemMeta);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (Main.getServerType() != ServerType.BUNGEE) {
            if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase(Main.getLobbyWorld())) {
                e.setCancelled(true);
                return;
            }
        }
        Arena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a == null) return;

        if (!a.isPlayer(e.getPlayer())) {
            e.setCancelled(true);
            return;
        }

        if (a.getStatus() != GameState.playing) {
            e.setCancelled(true);
        } else {
            ItemStack i = e.getItemDrop().getItemStack();
            if (i.getType() == Material.COMPASS) {
                e.setCancelled(true);
                return;
            }
        }

        if (a.getRespawn().containsKey(e.getPlayer())) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    //Prevent AFK players from picking items
    public void onCollect(GeneratorCollectEvent e){
        if (Main.api.isPlayerAFK(e.getPlayer())){
            e.setCancelled(true);
            return;
        }
    }
}
