package com.andrei1058.bedwars.api.events.player;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerBedBreakEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private BedWarsTeam playerTeam, victimTeam;

    /**
     * Called when a bed gets destroyed.
     */
    public PlayerBedBreakEvent(Player p, BedWarsTeam playerTeam, BedWarsTeam victimTeam) {
        this.player = p;
        this.playerTeam = playerTeam;
        this.victimTeam = victimTeam;
    }

    /**
     * Get the player team.
     */
    public BedWarsTeam getPlayerTeam() {
        return playerTeam;
    }

    /**
     * Get the team who got the bed destroyed.
     */
    public BedWarsTeam getVictimTeam() {
        return victimTeam;
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
