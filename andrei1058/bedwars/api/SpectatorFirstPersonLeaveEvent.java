package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpectatorFirstPersonLeaveEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player spectator;
    private Arena arena;
    private String title, subtitle;

    public SpectatorFirstPersonLeaveEvent(Player spectator, Arena arena, String title, String subtitle) {
        this.spectator = spectator;
        this.arena = arena;
        if (SpectatorFirstPersonEnterEvent.getSpectatingInFirstPerson().contains(spectator))
            SpectatorFirstPersonEnterEvent.getSpectatingInFirstPerson().remove(spectator);
    }

    /**
     * Get the spectator
     *
     * @since API 9
     */
    public Player getSpectator() {
        return spectator;
    }

    /**
     * Get the arena
     *
     * @since API 9
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Get first person leave subtitle
     *
     * @since API 9
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * Get first person leave title
     *
     * @since API 9
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
