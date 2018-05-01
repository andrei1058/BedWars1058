package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKillEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private Arena a;
    private Player victim, killer;

    /** killer can be null */
    public PlayerKillEvent(Arena a, Player victim, Player killer){
        this.a = a;
        this.victim = victim;
        this.killer = killer;
    }

    public Player getKiller() {
        return killer;
    }

    public Arena getArena() {
        return a;
    }

    public Player getVictim() {
        return victim;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
