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

import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerBaseLeaveEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private ITeam team;
    private Player p;

    /**
     * Called when a player leaves a team base.
     * Called when the distance between the player and a team's bed is greater than islandRadius
     */
    public PlayerBaseLeaveEvent(Player p, ITeam team) {
        this.p = p;
        this.team = team;
    }


    /**
     * Get the team owing the exited base
     */
    public ITeam getTeam() {
        return team;
    }

    /**
     * Get the player that leaved the base
     */
    public Player getPlayer() {
        return p;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
