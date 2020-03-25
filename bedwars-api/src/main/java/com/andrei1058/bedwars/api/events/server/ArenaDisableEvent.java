package com.andrei1058.bedwars.api.events.server;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaDisableEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private String arenaName;
    private String worldName;

    /**
     * Called when an arena is disabled.
     * This is not called when you restart the server.
     */
    public ArenaDisableEvent(String arenaName, String worldName) {
        this.arenaName = arenaName;
        this.worldName = worldName;
    }

    /**
     * Get the arena name
     */
    public String getArenaName() {
        return arenaName;
    }

    public String getWorldName() {
        return worldName;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
