package com.andrei1058.bedwars.api.events;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLeaveArenaEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private boolean spectator;
    private Arena arena;

    @Deprecated
    public PlayerLeaveArenaEvent(Player p, boolean spectator) {
        this.player = p;
        this.spectator = spectator;
    }

    public PlayerLeaveArenaEvent(Player p, Arena arena) {
        this.player = p;
        this.spectator = arena.isSpectator(p);
        this.arena = arena;
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

    /**
     * Get the arena
     *
     * @since API 9
     */
    public Arena getArena() {
        return arena;
    }

    @Deprecated
    public boolean isSpectator() {
        return spectator;
    }
}
