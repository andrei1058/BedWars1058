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

package com.andrei1058.bedwars.api.upgrades;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface TrapAction {

    /**
     * @return trap identifier.
     */
    String getName();

    /**
     * Manage what happens on {@param player} enters {@param targetTeam}.
     *
     * @param player     The player who enters the targetTeam.
     * @param playerTeam Enemy player team.
     * @param targetTeam The affected team.
     */
    void onTrigger(@NotNull Player player, ITeam playerTeam, ITeam targetTeam);
}
