package com.andrei1058.bedwars.api.events.team;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeamEliminatedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final IArena arena;
    private final ITeam team;

    /**
     * Called when all player on a team get killed and Bed is broken.
     * @param arena the arena.
     * @param team the eliminated team.
     */

    public TeamEliminatedEvent(IArena arena, ITeam team) {
        this.arena = arena;
        this.team = team;
    }

    public IArena getArena() {return arena;}

    public ITeam getTeam() {return team;}

    public HandlerList getHandlers() {return HANDLERS;}

    public static HandlerList getHandlerList() {return HANDLERS;}
}