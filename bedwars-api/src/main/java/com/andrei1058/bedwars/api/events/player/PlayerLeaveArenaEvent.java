package com.andrei1058.bedwars.api.events.player;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLeaveArenaEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private boolean spectator;
    private IArena arena;

    /**
     * Called when a player leaves the arena.
     * This is called if the arena has ended as well.
     */
    public PlayerLeaveArenaEvent(Player p, IArena arena) {
        this.player = p;
        this.spectator = arena.isSpectator(p);
        this.arena = arena;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Get the arena
     */
    public IArena getArena() {
        return arena;
    }

    /**
     * Check if the player was spectating.
     */
    public boolean isSpectator() {
        return spectator;
    }


    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
