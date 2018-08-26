package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.api.GeneratorCollectEvent;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static com.andrei1058.bedwars.Main.config;

public class EntityDropPick implements Listener{
    @EventHandler
    public void onPickup(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if (Main.getServerType() != ServerType.BUNGEE){
            if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(Main.getLobbyWorld())) {
                e.setCancelled(true);
                return;
            }
        }
        if (Arena.isInArena(p)) {
            Arena a = Arena.getArenaByPlayer(p);
            if (a.getStatus() == GameState.restarting || a.getStatus() == GameState.waiting) {
                e.setCancelled(true);
                return;
            }
            if (a.isSpectator(p)) {
                e.setCancelled(true);
            } else {
                if (Arena.respawn.containsKey(p)){
                    e.setCancelled(true);
                    return;
                }
                if (e.getItem().getItemStack().getType() == Material.BED) {
                    e.setCancelled(true);
                    e.getItem().remove();
                    return;
                }  else if (e.getItem().getItemStack().hasItemMeta()){
                    if (e.getItem().getItemStack().getItemMeta().hasDisplayName()){
                        if (e.getItem().getItemStack().getItemMeta().getDisplayName().contains("custom")){
                            Material material = e.getItem().getItemStack().getType();
                            //e.setCancelled(true);
                            //e.getItem().remove();
                            //e.getPlayer().getInventory().addItem(new ItemStack(material));
                            ItemMeta itemMeta = new ItemStack(material).getItemMeta();
                            e.getItem().getItemStack().setItemMeta(itemMeta);

                            //Call ore pick up event
                            Bukkit.getPluginManager().callEvent(new GeneratorCollectEvent((Player) e.getEntity(), e.getItem().getItemStack()));
                            //
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (Main.getServerType() != ServerType.BUNGEE){
            if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase(Main.getLobbyWorld())) {
                e.setCancelled(true);
                return;
            }
        }
        if (Arena.isInArena(p)) {
            Arena a = Arena.getArenaByPlayer(p);
            if (a.isSpectator(p)) {
                e.setCancelled(true);
            } else {
                if (Arena.respawn.containsKey(p)){
                    e.setCancelled(true);
                    return;
                }
                if (a.getStatus() != GameState.playing){
                    e.setCancelled(true);
                } else {
                    if (e.getItemDrop().getItemStack().getType() == Material.COMPASS) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }
}
