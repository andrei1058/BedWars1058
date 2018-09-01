package com.andrei1058.bedwars.api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaDisableEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private String arenaName;

    /**
     * Called when an arena is disabled.
     * This is not called when you restart the server.
     *
     * @since API 9
     */
    public ArenaDisableEvent(String arenaName) {
        this.arenaName = arenaName;
    }

    /**
     * Get the arena name
     *
     * @since API 9
     */
    public String getArenaName() {
        return arenaName;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
