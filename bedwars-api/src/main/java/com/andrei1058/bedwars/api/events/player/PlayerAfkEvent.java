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

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerAfkEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private AFKType afkType;

    /**
     * Called when a Player goes AFK or comes back from AFK.
     * Check AFKType for more.
     */
    public PlayerAfkEvent(Player player, AFKType afkType) {
        this.afkType = afkType;
        this.player = player;
    }

    /**
     * Get player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Check if player target went AFK or came back
     */
    public AFKType getAfkType() {
        return afkType;
    }

    public enum AFKType {
        /**
         * When a player goes AFK
         */
        START,

        /**
         * When a player comes back from AFK
         */
        END
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
