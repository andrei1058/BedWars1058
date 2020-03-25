package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkLoad implements Listener {

    @EventHandler
    public void onChunkLoadEvent(ChunkLoadEvent e){
        if (e == null) return;
        if (e.getChunk() == null) return;
        if (e.getChunk().getEntities() == null) return;
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, ()-> {
            for (Entity entity : e.getChunk().getEntities()){
                if (entity instanceof ArmorStand){
                    if (entity.hasMetadata("bw1058-setup")){
                        Bukkit.getScheduler().runTask(BedWars.plugin, entity::remove);
                        continue;
                    }
                    if (!((ArmorStand)entity).isVisible()){
                        if (((ArmorStand)entity).isMarker()){
                            //if (!entity.hasGravity()){
                            if (entity.isCustomNameVisible()){
                                if (ChatColor.stripColor(entity.getCustomName()).contains(" SET") || ChatColor.stripColor(entity.getCustomName()).contains(" set")){
                                    Bukkit.getScheduler().runTask(BedWars.plugin, entity::remove);
                                }
                            }
                            //}
                        }
                    }
                }
            }
        });
    }
}
