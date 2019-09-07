package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldLoadListener implements Listener {

    @EventHandler
    public void onLoad(WorldLoadEvent e){
        for (IArena a : Arena.getEnableQueue()){
            if (a.getWorldName().equalsIgnoreCase(e.getWorld().getName())) a.init(e.getWorld());
        }
    }
}
