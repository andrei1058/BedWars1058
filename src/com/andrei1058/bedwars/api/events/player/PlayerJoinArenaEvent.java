package com.andrei1058.bedwars.api.events.player;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerJoinArenaEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private boolean spectator;
    private boolean cancelled = false;
    private Arena arena;

    /**
     * This event is called when a player joins the arena as a player or spectator.
     * The event is not triggered for players who died and become spectators. Listen the kill event for this.
     */
    public PlayerJoinArenaEvent(Arena arena, Player p, boolean spectator) {
        this.arena = arena;
        this.player = p;
        this.spectator = spectator;
    }

    public Arena getArena() {
        return arena;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isSpectator() {
        return spectator;
    }
}
