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

package com.andrei1058.bedwars.api.events.upgrades;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.upgrades.TeamUpgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpgradeBuyEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private TeamUpgrade teamUpgrade;
    private Player player;
    private final IArena arena;
    private ITeam team;
    private boolean cancelled = false;

    /**
     * Called when a Team Upgrade is bought
     */
    public UpgradeBuyEvent(TeamUpgrade teamUpgrade, Player player, ITeam team) {
        this.teamUpgrade = teamUpgrade;
        this.player = player;
        this.team = team;
        this.arena = team.getArena();
    }

    public IArena getArena() {
        return arena;
    }

    /**
     * Get who bought the upgrade
     */
    public Player getPlayer() {
        return player;
    }

    public ITeam getTeam() {
        return team;
    }

    public TeamUpgrade getTeamUpgrade() {
        return teamUpgrade;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Cancel event. Purchase will be cancelled.
     */
    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

}
