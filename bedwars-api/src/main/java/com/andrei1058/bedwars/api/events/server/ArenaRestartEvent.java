package com.andrei1058.bedwars.api.events.server;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaRestartEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private String arena;

    /**
     * Called when an arena is restarting.
     * After the world was unloaded.
     */
    public ArenaRestartEvent(String arena) {
        this.arena = arena;
    }

    /**
     * Get the arena name.
     */
    public String getArenaName() {
        return arena;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
