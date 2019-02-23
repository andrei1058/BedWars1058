package com.andrei1058.bedwars.lobbysocket;

import com.andrei1058.bedwars.api.events.ArenaEnableEvent;
import com.andrei1058.bedwars.api.events.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.PlayerJoinArenaEvent;
import com.andrei1058.bedwars.api.events.PlayerLeaveArenaEvent;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArenaListeners implements Listener {

    @EventHandler
    public void onPlayerJoinArena(PlayerJoinArenaEvent e) {
        Arena a = Arena.getArenaByPlayer(e.getPlayer());
        ArenaSocket.sendMessage(a);
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent e){
        ArenaSocket.sendMessage(e.getArena());
    }

    @EventHandler
    public void onArenaStatusChange(GameStateChangeEvent e){
        ArenaSocket.sendMessage(e.getArena());
    }

    @EventHandler
    public void onArenaLoad(ArenaEnableEvent e){
        ArenaSocket.sendMessage(e.getArena());
    }
}
