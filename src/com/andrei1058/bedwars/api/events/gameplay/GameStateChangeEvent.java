package com.andrei1058.bedwars.api.events.gameplay;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStateChangeEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Arena arena;
    private GameState state;

    /**
     * Called when the status of the game is changed. Waiting, Starting etc.
     */
    public GameStateChangeEvent(Arena a, GameState status) {
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
