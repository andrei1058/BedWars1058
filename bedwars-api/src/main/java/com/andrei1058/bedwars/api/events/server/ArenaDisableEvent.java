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

package com.andrei1058.bedwars.api.events.server;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaDisableEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private String arenaName;
    private String worldName;

    /**
     * Called when an arena is disabled.
     * This is not called when you restart the server.
     */
    public ArenaDisableEvent(String arenaName, String worldName) {
        this.arenaName = arenaName;
        this.worldName = worldName;
    }

    /**
     * Get the arena name
     */
    public String getArenaName() {
        return arenaName;
    }

    public String getWorldName() {
        return worldName;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
