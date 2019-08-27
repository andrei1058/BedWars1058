package com.andrei1058.bedwars.api.events.server;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaEnableEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private Arena arena;

    /**
     * Called when an arena is enabled successfully. It's called after a restart as well.
     */
    public ArenaEnableEvent(Arena a) {
        this.arena = a;
    }

    /**
     * Get the arena
     */
    public Arena getArena() {
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
