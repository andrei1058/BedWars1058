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
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.function.Function;

public class SpectatorFirstPersonLeaveEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player spectator;
    private IArena arena;
    private Function<Player, String> title;
    private Function<Player, String> subTitle;
    private int fadeIn = 0;
    private int stay = 40;
    private int fadeOut = 10;

    public SpectatorFirstPersonLeaveEvent(Player spectator, IArena arena, Function<Player, String> title, Function<Player, String> subtitle) {
        this.spectator = spectator;
        this.arena = arena;
        this.title = title;
        this.subTitle = subtitle;
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
     * Get first person leave subtitle
     */
    public Function<Player, String> getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(Function<Player, String> subTitle) {
        this.subTitle = subTitle;
    }

    /**
     * Get first person leave title
     */
    public Function<Player, String> getTitle() {
        return title;
    }

    public void setTitle(Function<Player, String> title) {
        this.title = title;
    }

    public int getFadeIn() {
        return fadeIn;
    }

    public int getFadeOut() {
        return fadeOut;
    }

    public int getStay() {
        return stay;
    }

    public void setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
    }

    public void setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
    }

    public void setStay(int stay) {
        this.stay = stay;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
