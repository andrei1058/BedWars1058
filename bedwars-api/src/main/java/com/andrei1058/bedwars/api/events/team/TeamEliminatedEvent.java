package com.andrei1058.bedwars.api.events.team;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeamEliminatedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final IArena arena;
    private final ITeam eliminated;

    /**
     * Called when all Team gets killed and Bed is broken during the game.
     *
     *
     */

    public TeamEliminatedEvent(IArena arena, ITeam eliminated) {
        this.arena = arena;
        this.eliminated = eliminated;
    }

    public IArena getArena() {
        return arena;
    }

    public ITeam getTeam() {return eliminated;}

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}