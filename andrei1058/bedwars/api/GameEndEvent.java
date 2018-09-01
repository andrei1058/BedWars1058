package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameEndEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private List<UUID> winners;
    private List<UUID> loosers;
    private List<UUID> aliveWinners;
    private BedWarsTeam teamWinner;
    private Arena arena;

    /**
     * Triggered when the game ends
     *
     * @since API v8
     */
    public GameEndEvent(Arena arena, List<UUID> winners, List<UUID> losers, BedWarsTeam teamWinner, List<UUID> aliveWinners) {
        this.winners = new ArrayList<>(winners);
        this.arena = arena;
        this.loosers = new ArrayList<>(losers);
        this.teamWinner = teamWinner;
        this.aliveWinners = new ArrayList<>(aliveWinners);
    }

    /**
     * Get a list of winners including eliminated teammates
     *
     * @since API v8
     */
    public List<UUID> getWinners() {
        return winners;
    }

    /**
     * Get the winner team
     *
     * @since API v8
     */
    public BedWarsTeam getTeamWinner() {
        return teamWinner;
    }

    /**
     * Get a list with people who played and didn't win.
     * This includes people who leaved the game etc.
     *
     * @since API v8
     */
    public List<UUID> getLoosers() {
        return loosers;
    }

    /**
     * Get the arena
     *
     * @since API v8
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Get a list of winners.
     * Teammates killed by final kill excluded.
     *
     * @since API v8
     */
    public List<UUID> getAliveWinners() {
        return aliveWinners;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
