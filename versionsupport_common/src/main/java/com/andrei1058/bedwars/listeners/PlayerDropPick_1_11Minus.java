package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.player.PlayerGeneratorCollectEvent;
import com.andrei1058.bedwars.api.server.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class PlayerDropPick_1_11Minus {

    private static BedWars api;

    public PlayerDropPick_1_11Minus(BedWars bedWars){
        api = bedWars;
    }

    /* This Class is used for versions from 1.8.8 to 1.11 included */

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if (api.getServerType() != ServerType.BUNGEE) {
            //noinspection ConstantConditions
            if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase(api.getLobbyWorld())) {
                e.setCancelled(true);
                return;
            }
        }

        IArena a = api.getArenaUtil().getArenaByPlayer(e.getPlayer());
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
            e.getItem().setItemStack(api.getVersionSupport().createItemStack(e.getItem().getItemStack().getType().toString(), e.getItem().getItemStack().getAmount(), (short) 0));
            return;
        }

        if (e.getItem().getItemStack().getType().toString().equals("BED")) {
            e.setCancelled(true);
            e.getItem().remove();
        } else if (e.getItem().getItemStack().hasItemMeta()) {
            //noinspection ConstantConditions
            if (e.getItem().getItemStack().getItemMeta().hasDisplayName()) {
                if (e.getItem().getItemStack().getItemMeta().getDisplayName().contains("custom")) {
                    Material material = e.getItem().getItemStack().getType();
                    ItemMeta itemMeta = new ItemStack(material).getItemMeta();

                    //Call ore pick up event
                    PlayerGeneratorCollectEvent event = new PlayerGeneratorCollectEvent(e.getPlayer(), e.getItem().getItemStack(), a);
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
        if (api.getServerType() != ServerType.BUNGEE) {
            //noinspection ConstantConditions
            if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase(api.getLobbyWorld())) {
                e.setCancelled(true);
                return;
            }
        }
        IArena a = api.getArenaUtil().getArenaByPlayer(e.getPlayer());
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
        }
    }

    @EventHandler
    //Prevent AFK players from picking items
    public void onCollect(PlayerGeneratorCollectEvent e){
        if (api.getAFKUtil().isPlayerAFK(e.getPlayer())){
            e.setCancelled(true);
        }
    }
}
