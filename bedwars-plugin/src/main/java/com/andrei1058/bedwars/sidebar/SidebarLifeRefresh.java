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

package com.andrei1058.bedwars.sidebar;

import org.bukkit.entity.Player;

public class SidebarLifeRefresh implements Runnable {
    @Override
    public void run() {
        for (BedWarsScoreboard scoreboard : BedWarsScoreboard.getScoreboards().values()){
            if (scoreboard.getArena() != null){
                scoreboard.getHandle().refreshHealthAnimation();
                for (Player player : scoreboard.getArena().getPlayers()){
                    scoreboard.getHandle().refreshHealth(player, (int) Math.ceil(player.getHealth()));
                }
            }
        }
    }
}
