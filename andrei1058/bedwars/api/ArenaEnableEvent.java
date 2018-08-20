package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaEnableEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private Arena arena;

    /**
     * Called when an arena is enabled successfully
     *
     * @since API 9
     */
    public ArenaEnableEvent(Arena a) {
        this.arena = a;
    }

    /**
     * Get the arena
     *
     * @since API 9
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
