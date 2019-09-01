package com.andrei1058.bedwars.api.events.spectator;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpectatorTeleportToPlayerEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player spectator, target;
    private IArena arena;
    private boolean cancelled = false;

    /**
     * Called when the spectator uses the player selector to teleport and spectate the selected player.
     */
    public SpectatorTeleportToPlayerEvent(Player spectator, Player target, IArena arena) {
        this.spectator = spectator;
        this.target = target;
        this.arena = arena;
    }

    /**
     * Get the spectator
     */
    public Player getSpectator() {
        return spectator;
    }

    /**
     * Get the arena
     */
    public IArena getArena() {
        return arena;
    }

    /**
     * Get the target player
     */
    public Player getTarget() {
        return target;
    }

    /**
     * Check if it is cancelled
     */
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
}
