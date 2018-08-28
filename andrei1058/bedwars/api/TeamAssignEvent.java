package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeamAssignEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private BedWarsTeam team;
    private Arena arena;
    private boolean cancelled;

    /**
     * Called for each player when the waiting countdown == 0
     * You can cancel each team assign event in order to manage them yourself
     * but make sure to set BedWarsTeam#setBedDestroyed(false) if non empty teams are marked as eliminated
     * and use BedWarsTeam#firstSpawn(p) to spawn them. But first assign them to a team BedWarsTeam#addPlayers(p)
     *
     * @since API 10
     */
    public TeamAssignEvent(Player player, BedWarsTeam team, Arena arena) {
        this.player = player;
        this.team = team;
        this.arena = arena;
    }

    /**
     * Get the team
     *
     * @return the team assigned to the player
     * @since API 10
     */
    public BedWarsTeam getTeam() {
        return team;
    }

    /**
     * Get the player
     *
     * @return the target player
     * @since API 10
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the arena
     *
     * @return arena
     * @since API 10
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Check if the assign was cancelled
     *
     * @since API 10
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Cancel/ Allow the assign event
     *
     * @since API 10
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
