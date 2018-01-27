package com.andrei1058.bedwars.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerJoinArenaEvent extends Event{

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private boolean spectator;

    public PlayerJoinArenaEvent(Player p, boolean spectator){
        this.player = p;
        this.spectator = spectator;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isSpectator() {
        return spectator;
    }
}
