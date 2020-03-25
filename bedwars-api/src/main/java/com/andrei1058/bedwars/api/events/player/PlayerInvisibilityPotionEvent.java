package com.andrei1058.bedwars.api.events.player;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerInvisibilityPotionEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Type type;
    private Player player;
    private IArena arena;
    private ITeam team;

    /**
     * This event is called when invisibility potions are managed by Bed-Wars.
     */
    public PlayerInvisibilityPotionEvent(Type type, ITeam team, Player player, IArena arena) {
        this.type = type;
        this.player = player;
        this.arena = arena;
        this.team = team;
    }


    public static enum Type {
        ADDED, REMOVED
    }

    public Type getType() {
        return type;
    }

    public IArena getArena() {
        return arena;
    }

    public Player getPlayer() {
        return player;
    }

    public ITeam getTeam() {
        return team;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
