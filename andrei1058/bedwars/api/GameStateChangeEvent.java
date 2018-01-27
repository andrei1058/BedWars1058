package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStateChangeEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Arena arena;
    private GameState state;

    public GameStateChangeEvent(Arena a, GameState status){
        this.arena = a;
        this.state = status;
    }


    public Arena getArena() {
        return arena;
    }

    public GameState getState() {
        return state;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
