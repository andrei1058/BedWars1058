package com.andrei1058.bedwars.api.events.server;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaEnableEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private IArena arena;

    /**
     * Called when an arena is enabled successfully. It's called after a restart as well.
     */
    public ArenaEnableEvent(IArena a) {
        this.arena = a;
    }

    /**
     * Get the arena
     */
    public IArena getArena() {
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
