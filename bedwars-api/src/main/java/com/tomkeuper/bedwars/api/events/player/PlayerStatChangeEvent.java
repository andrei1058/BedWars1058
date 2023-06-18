package com.tomkeuper.bedwars.api.events.player;

import com.tomkeuper.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerStatChangeEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private IArena arena;
    private boolean cancelled = false;

    /**
     * Called when player statistics get changes
     * @Param player - target player
     * @param arena  - target arena
     *
     */
    public PlayerStatChangeEvent(Player player, IArena arena) {
        this.player = player;
        this.arena = arena;
    }



    /**
     * Get the arena
     */
    public IArena getArena() {
        return arena;
    }

    /**
     * Get the player
     */
    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Check if event was cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Cancel event
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
