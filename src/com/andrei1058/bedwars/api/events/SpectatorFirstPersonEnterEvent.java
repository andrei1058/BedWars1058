package com.andrei1058.bedwars.api.events;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;

public class SpectatorFirstPersonEnterEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player spectator, target;
    private Arena arena;
    private boolean cancelled = false;
    private String title, subtitle;

    /* A list of all players spectating in first person */
    private static List<Player> spectatingInFirstPerson = new ArrayList<>();

    public SpectatorFirstPersonEnterEvent(Player spectator, Player target, Arena arena, String title, String subtitle) {
        this.spectator = spectator;
        this.target = target;
        this.arena = arena;
        this.title = title;
        this.subtitle = subtitle;
        if (!spectatingInFirstPerson.contains(spectator)) spectatingInFirstPerson.add(spectator);
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
     * Get the target player
     *
     * @since API 9
     */
    public Player getTarget() {
        return target;
    }

    /**
     * Check if it is cancelled
     *
     * @since API 9
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Change value
     *
     * @since PAI 9
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Get a list of spectators in first person
     *
     * @since API 9
     */
    public static List<Player> getSpectatingInFirstPerson() {
        return spectatingInFirstPerson;
    }

    /**
     * Get first person enter subtitle
     *
     * @since API 9
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * Get first person enter title
     *
     * @since API 9
     */
    public String getTitle() {
        return title;
    }

    /** Set first person enter title and subtitle. Leave "" for empty msg */
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
