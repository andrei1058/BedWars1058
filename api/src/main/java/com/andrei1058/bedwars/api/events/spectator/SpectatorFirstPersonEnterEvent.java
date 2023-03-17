/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.api.events.spectator;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class SpectatorFirstPersonEnterEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player spectator;
    private final Player target;
    private final IArena arena;
    private boolean cancelled = false;
    private Function<Player, String> title;
    private Function<Player, String> subTitle;
    private int fadeIn = 0;
    private int stay = 40;
    private int fadeOut = 10;

    // A list of all players spectating in first person
    private static List<UUID> spectatingInFirstPerson = new ArrayList<>();

    /**
     * Called when a spectator enters the first person spectating system.
     */
    public SpectatorFirstPersonEnterEvent(@NotNull Player spectator, @NotNull Player target, IArena arena, Function<Player, String> title, Function<Player, String> subtitle) {
        this.spectator = spectator;
        this.target = target;
        this.arena = arena;
        this.title = title;
        this.subTitle = subtitle;
        if (!spectatingInFirstPerson.contains(spectator.getUniqueId()))
            spectatingInFirstPerson.add(spectator.getUniqueId());
    }

    /**
     * Get the spectator
     */
    public Player getSpectator() {
        return spectator;
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
        return target;
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

    public Function<Player, String> getSubTitle() {
        return subTitle;
    }

    /**
     * Get first person enter subtitle
     */


    public Function<Player, String> getTitle() {
        return title;
    }

    /**
     * Get first person enter title
     */
    public void setTitle(Function<Player, String> title) {
        this.title = title;
    }

    /**
     * Set first person enter title and subtitle. Leave "" for empty msg
     */
    public void setSubTitle(Function<Player, String> subTitle) {
        this.subTitle = subTitle;
    }

    public int getStay() {
        return stay;
    }

    public int getFadeOut() {
        return fadeOut;
    }

    public int getFadeIn() {
        return fadeIn;
    }

    public void setStay(int stay) {
        if (stay < 0) return;
        this.stay = stay;
    }

    public void setFadeOut(int fadeOut) {
        if (fadeOut < 0) return;
        this.fadeOut = fadeOut;
    }

    public void setFadeIn(int fadeIn) {
        if (fadeIn < 0) return;
        this.fadeIn = fadeIn;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
