package com.andrei1058.bedwars.api.events;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpectatorTeleportToPlayerEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player spectator, target;
    private Arena arena;
    private boolean cancelled = false;

    public SpectatorTeleportToPlayerEvent(Player spectator, Player target, Arena arena) {
        this.spectator = spectator;
        this.target = target;
        this.arena = arena;
    }

    /**
     * Get the spectator
     *
     * @since API 9
     */
    public Player getSpectator() {
        return spectator;
    }

    /**
     * Get the arena
     *
     * @since API 9
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Get the target player
     *
     * @since API 9
     */
    public Player getTarget() {
        return target;
    }

    /**
     * Check if it is cancelled
     *
     * @since API 9
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Change value
     *
     * @since PAI 9
     */
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
