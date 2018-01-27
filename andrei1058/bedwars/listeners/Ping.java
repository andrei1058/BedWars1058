package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class Ping implements Listener {
    @EventHandler
    public void p(ServerListPingEvent e){
        Arena a = Arena.getArenas().get(0);
        if (a != null){
            e.setMaxPlayers(a.getMaxPlayers());
            e.setMotd(a.getDisplayStatus());
        }
    }
}
