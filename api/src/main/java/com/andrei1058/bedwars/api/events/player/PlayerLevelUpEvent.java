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

public class PlayerLevelUpEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private int newXpTarget;
    private int newLevel;

    /**
     * Called when a player levels up.
     * This only works when the internal Level System is used.
     * Developers can "inject" their own level system.
     *
     * @param player    - target player.
     * @param newLevel  - gained level.
     * @param levelUpXp - new xp target to level up.
     */
    public PlayerLevelUpEvent(Player player, int newLevel, int levelUpXp) {
        this.player = player;
        this.newLevel = newLevel;
        this.newXpTarget = levelUpXp;
    }

    /**
     * Get player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get new player level.
     */
    public int getNewLevel() {
        return newLevel;
    }

    /**
     * Get new xp target to level up.
     */
    public int getNewXpTarget() {
        return newXpTarget;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
