package com.andrei1058.bedwars.api.events.spectator;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpectatorFirstPersonEnterEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private UUID spectator, target;
    private IArena arena;
    private boolean cancelled = false;
    private String title, subtitle;

    // A list of all players spectating in first person
    private static List<UUID> spectatingInFirstPerson = new ArrayList<>();

    /**
     * Called when a spectator enters the first person spectating system.
     */
    public SpectatorFirstPersonEnterEvent(@NotNull Player spectator, @NotNull Player target, IArena arena, String title, String subtitle) {
        this.spectator = spectator.getUniqueId();
        this.target = target.getUniqueId();
        this.arena = arena;
        this.title = title;
        this.subtitle = subtitle;
        if (!spectatingInFirstPerson.contains(spectator.getUniqueId())) spectatingInFirstPerson.add(spectator.getUniqueId());
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
     * Get the target player
     */
    public Player getTarget() {
        return Bukkit.getPlayer(target);
    }

    /**
     * Check if it is cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Change value
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Get first person enter subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * Get first person enter title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set first person enter title and subtitle. Leave "" for empty msg
     */
    public void setTitle(String title, String subtitle) {
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
