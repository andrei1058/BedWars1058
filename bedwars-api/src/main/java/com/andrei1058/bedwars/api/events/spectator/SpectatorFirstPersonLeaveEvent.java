package com.andrei1058.bedwars.api.events.spectator;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class SpectatorFirstPersonLeaveEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private UUID spectator;
    private IArena arena;
    private String title, subtitle;

    public SpectatorFirstPersonLeaveEvent(Player spectator, IArena arena, String title, String subtitle) {
        this.spectator = spectator.getUniqueId();
        this.arena = arena;
        this.title = title;
        this.subtitle = subtitle;
    }

    /**
     * Get the spectator
     */
    public Player getSpectator() {
        return Bukkit.getPlayer(spectator);
    }

    /**
     * Get the arena
     */
    public IArena getArena() {
        return arena;
    }

    /**
     * Get first person leave subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * Get first person leave title
     */
    public String getTitle() {
        return title;
    }

    /** Set first person leave title and subtitle. Leave "" for empty msg */
    public void setTitle(String title, String subtitle){
        this.title = title;
        this.subtitle = subtitle;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
