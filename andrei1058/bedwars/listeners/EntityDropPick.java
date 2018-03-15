package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.arena.Arena;
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
    public void p(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if (p.getWorld().getName().equalsIgnoreCase(config.getLobbyWorldName())) {
            e.setCancelled(true);
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
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void d(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (e.getPlayer().getWorld().getName().equalsIgnoreCase(config.getLobbyWorldName())) {
            e.setCancelled(true);
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
