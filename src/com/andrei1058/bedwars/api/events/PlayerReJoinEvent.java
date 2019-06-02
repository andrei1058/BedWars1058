package com.andrei1058.bedwars.api.events;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerReJoinEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private Arena arena;

    /**
     * Called when a player re-joins the arena.
     * PlayerJoinArenaEvent won't be called
     *
     * @since API 11
     */
    public PlayerReJoinEvent(Player player, Arena arena) {
        this.player = player;
        this.arena = arena;
    }

    /**
     * Get arena
     *
     * @since API 11
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Get player
     *
     * @since API 11
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
}
