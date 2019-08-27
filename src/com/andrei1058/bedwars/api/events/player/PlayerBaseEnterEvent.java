package com.andrei1058.bedwars.api.events.player;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerBaseEnterEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private BedWarsTeam team;
    private Player p;

    /**
     * Called when a player enters a team base.
     * Called when the distance between the player and a team bed is greater than islandRadius
     */
    public PlayerBaseEnterEvent(Player p, BedWarsTeam team) {
        this.p = p;
        this.team = team;
    }


    /**
     * Get the team owing the entered base
     */
    public BedWarsTeam getTeam() {
        return team;
    }

    /**
     * Get the player that entered the base
     */
    public Player getPlayer() {
        return p;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
