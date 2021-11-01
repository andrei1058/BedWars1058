package com.andrei1058.bedwars.redis;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerJoinArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.andrei1058.bedwars.api.events.server.ArenaEnableEvent;
import com.andrei1058.bedwars.lobbysocket.ArenaSocket;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RedisArenaListeners implements Listener {

    private final RedisConnection redisConnection;

    public RedisArenaListeners(RedisConnection connection) {
        this.redisConnection = connection;
    }

    @EventHandler
    public void onPlayerJoinArena(PlayerJoinArenaEvent e) {
        if (e == null) return;
        final IArena a = e.getArena();
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, ()-> redisConnection.sendMessage(ArenaSocket.formatUpdateMessage(a)));
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent e){
        if (e == null) return;
        final IArena a = e.getArena();
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, ()-> redisConnection.sendMessage(ArenaSocket.formatUpdateMessage(a)));
    }

    @EventHandler
    public void onArenaStatusChange(GameStateChangeEvent e){
        if (e == null) return;
        final IArena a = e.getArena();
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, ()-> redisConnection.sendMessage(ArenaSocket.formatUpdateMessage(a)));
    }

    @EventHandler
    public void onArenaLoad(ArenaEnableEvent e){
        if (e == null) return;
        final IArena a = e.getArena();
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, ()-> redisConnection.sendMessage(ArenaSocket.formatUpdateMessage(a)));
    }
}
