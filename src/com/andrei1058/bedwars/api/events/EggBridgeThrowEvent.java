package com.andrei1058.bedwars.api.events;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;

public class EggBridgeThrowEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private Arena arena;
    private boolean cancelled = false;

    /**
     * Called when a player throw an egg bridge and it starts building
     *
     * @since API 10
     */
    public EggBridgeThrowEvent(Player player, Arena arena) {
        this.player = player;
        this.arena = arena;
    }

    /**
     * Get player
     *
     * @since API 10
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get arena
     *
     * @since API 10
     */
    public Arena getArena() {
        return arena;
    }

    /** Check if event is cancelled
     *
     * @since API 10*/
    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Contract(pure = true)
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
