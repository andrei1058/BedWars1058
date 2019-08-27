package com.andrei1058.bedwars.api.events.player;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerReJoinEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private Arena arena;
    private boolean cancelled = false;

    /**
     * Called when a player re-joins the arena.
     * PlayerJoinArenaEvent won't be called
     */
    public PlayerReJoinEvent(Player player, Arena arena) {
        this.player = player;
        this.arena = arena;
    }

    /**
     * Get arena
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Get player
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
