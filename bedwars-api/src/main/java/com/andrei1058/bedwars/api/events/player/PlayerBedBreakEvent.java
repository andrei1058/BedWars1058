package com.andrei1058.bedwars.api.events.player;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerBedBreakEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private IArena arena;
    private ITeam playerTeam, victimTeam;

    /**
     * Called when a bed gets destroyed.
     */
    public PlayerBedBreakEvent(Player p, ITeam playerTeam, ITeam victimTeam, IArena arena) {
        this.player = p;
        this.playerTeam = playerTeam;
        this.victimTeam = victimTeam;
        this.arena = arena;
    }

    /**
     * Get the player team.
     */
    public ITeam getPlayerTeam() {
        return playerTeam;
    }

    /**
     * Get the team who got the bed destroyed.
     */
    public ITeam getVictimTeam() {
        return victimTeam;
    }

    public IArena getArena() {
        return arena;
    }

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
