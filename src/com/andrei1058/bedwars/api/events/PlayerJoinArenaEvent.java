package com.andrei1058.bedwars.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerJoinArenaEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private boolean spectator;
    private boolean cancelled = false;
    private boolean rejoin;

    /**
     * This event is called when a spectator is added to the arena.
     * Even if he has played before and was eliminated and then added as spectator.
     */
    public PlayerJoinArenaEvent(Player p, boolean spectator, boolean rejoin) {
        this.player = p;
        this.spectator = spectator;
        this.rejoin = rejoin;
    }

    public boolean isRejoin() {
        return rejoin;
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
