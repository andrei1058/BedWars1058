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

package com.andrei1058.bedwars.api.events.player;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.function.Function;

public class PlayerBedBreakEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final IArena arena;
    private final ITeam playerTeam;
    private final ITeam victimTeam;
    private Function<Player, String> message;
    private Function<Player, String> title;
    private Function<Player, String> subTitle;

    /**
     * Called when a bed gets destroyed.
     */
    public PlayerBedBreakEvent(Player p, ITeam playerTeam, ITeam victimTeam, IArena arena, Function<Player, String> message, Function<Player, String> title, Function<Player, String> subTitle) {
        this.player = p;
        this.playerTeam = playerTeam;
        this.victimTeam = victimTeam;
        this.arena = arena;
        this.message = message;
        this.title = title;
        this.subTitle = subTitle;
    }

    /**
     * Get the player team.
     */
    public ITeam getPlayerTeam() {
        return playerTeam;
    }

    /**
     * Get the team who got the bed destroyed.
     */
    public ITeam getVictimTeam() {
        return victimTeam;
    }

    public IArena getArena() {
        return arena;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Get chat message.
     */
    public Function<Player, String> getMessage() {
        return message;
    }

    /**
     * Set chat message.
     */
    public void setMessage(Function<Player, String> message) {
        this.message = message;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Function<Player, String> getTitle() {
        return title;
    }

    public void setTitle(Function<Player, String> title) {
        this.title = title;
    }

    public Function<Player, String> getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(Function<Player, String> subTitle) {
        this.subTitle = subTitle;
    }
}
