package com.andrei1058.bedwars.lobbysocket;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.server.ArenaEnableEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerJoinArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArenaListeners implements Listener {

    @EventHandler
    public void onPlayerJoinArena(PlayerJoinArenaEvent e) {
        IArena a = Arena.getArenaByPlayer(e.getPlayer());
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, ()-> ArenaSocket.sendMessage(a));
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent e){
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, ()-> ArenaSocket.sendMessage(e.getArena()));
    }

    @EventHandler
    public void onArenaStatusChange(GameStateChangeEvent e){
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, ()-> ArenaSocket.sendMessage(e.getArena()));
    }

    @EventHandler
    public void onArenaLoad(ArenaEnableEvent e){
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, ()-> ArenaSocket.sendMessage(e.getArena()));
    }
}
