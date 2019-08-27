package com.andrei1058.bedwars.api.events.player;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerFirstSpawnEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private Arena arena;
    private BedWarsTeam team;

    /**
     * Called when a member is spawned for the first time on his island
     */
    public PlayerFirstSpawnEvent(Player player, Arena arena, BedWarsTeam team) {
        this.player = player;
        this.arena = arena;
        this.team = team;
    }

    /**
     * Get the arena
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Get the player's team
     */
    public BedWarsTeam getTeam() {
        return team;
    }

    /**
     * Get the player
     */
    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
