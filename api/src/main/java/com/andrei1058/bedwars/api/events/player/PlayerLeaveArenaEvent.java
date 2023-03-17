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
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Nullable;

public class PlayerLeaveArenaEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private boolean spectator;
    private IArena arena;
    @Nullable
    private Player lastDamager;

    /**
     * Called when a player leaves the arena.
     * This is called if the arena has ended as well.
     */
    public PlayerLeaveArenaEvent(Player p, IArena arena, @Nullable Player lastDamager) {
        this.player = p;
        this.spectator = arena.isSpectator(p);
        this.arena = arena;
        this.lastDamager = lastDamager;
    }

    @Deprecated
    public PlayerLeaveArenaEvent(Player p, IArena arena) {
        this(p, arena, null);
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Get the arena
     */
    public IArena getArena() {
        return arena;
    }

    /**
     * Check if the player was spectating.
     */
    public boolean isSpectator() {
        return spectator;
    }

    /**
     * Get the player's last damager, might be null
     */
    @Nullable
    public Player getLastDamager() {
        return lastDamager;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
