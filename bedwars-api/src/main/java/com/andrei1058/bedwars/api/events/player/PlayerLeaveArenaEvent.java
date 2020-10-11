package com.andrei1058.bedwars.api.events.player;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Nullable;

public class PlayerLeaveArenaEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private boolean spectator;
    private IArena arena;
    @Nullable
    private Player lastDamager;

    /**
     * Called when a player leaves the arena.
     * This is called if the arena has ended as well.
     */
    public PlayerLeaveArenaEvent(Player p, IArena arena, @Nullable Player lastDamager) {
        this.player = p;
        this.spectator = arena.isSpectator(p);
        this.arena = arena;
        this.lastDamager = lastDamager;
    }

    @Deprecated
    public PlayerLeaveArenaEvent(Player p, IArena arena) {
        this(p, arena, null);
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

    /**
     * Get the player's last damager, might be null
     */
    @Nullable
    public Player getLastDamager() {
        return lastDamager;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
