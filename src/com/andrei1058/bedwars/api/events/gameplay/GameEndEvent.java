package com.andrei1058.bedwars.api.events.gameplay;

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
    private List<UUID> losers;
    private List<UUID> aliveWinners;
    private BedWarsTeam teamWinner;
    private Arena arena;

    /**
     * Triggered when the game ends.
     */
    public GameEndEvent(Arena arena, List<UUID> winners, List<UUID> losers, BedWarsTeam teamWinner, List<UUID> aliveWinners) {
        this.winners = new ArrayList<>(winners);
        this.arena = arena;
        this.losers = new ArrayList<>(losers);
        this.teamWinner = teamWinner;
        this.aliveWinners = new ArrayList<>(aliveWinners);
    }

    /**
     * Get a list of winners including eliminated teammates
     */
    public List<UUID> getWinners() {
        return winners;
    }

    /**
     * Get the winner team
     */
    public BedWarsTeam getTeamWinner() {
        return teamWinner;
    }

    /**
     * Get a list with people who played and didn't win.
     * This includes people who leaved the game etc.
     */
    public List<UUID> getLosers() {
        return losers;
    }

    /**
     * Get the arena
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Get a list of winners.
     * Teammates killed by final kill excluded.
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
