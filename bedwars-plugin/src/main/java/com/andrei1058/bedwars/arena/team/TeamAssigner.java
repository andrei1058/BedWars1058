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

package com.andrei1058.bedwars.arena.team;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.ITeamAssigner;
import com.andrei1058.bedwars.api.events.gameplay.TeamAssignEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class TeamAssigner implements ITeamAssigner {

    private final LinkedList<Player> skip = new LinkedList<>();

    public void assignTeams(IArena arena) {

        // team up parties first
        if (arena.getPlayers().size() > arena.getMaxInTeam() && arena.getMaxInTeam() > 1) {
            LinkedList<List<Player>> teams = new LinkedList<>();

            List<Player> members;
            for (Player player : arena.getPlayers()) {
                members = BedWars.getParty().getMembers(player);
                if (members == null) continue;
                members = new ArrayList<>(members);
                if (members.isEmpty()) continue;
                members.removeIf(member -> !arena.isPlayer(member));
                if (members.isEmpty()) continue;
                teams.add(members);
            }
            // prioritize bigger teams

            if (!teams.isEmpty()) {
                for (ITeam team : arena.getTeams()) {
                    // sort
                    teams.sort(Comparator.comparingInt(List::size));
                    if (teams.get(0).isEmpty()) break;
                    for (int i = 0; i < arena.getMaxInTeam() && team.getMembers().size() < arena.getMaxInTeam(); i++) {
                        if (teams.get(0).size() > i) {
                            Player toAdd = teams.get(0).remove(0);
                            TeamAssignEvent e = new TeamAssignEvent(toAdd, team, arena);
                            Bukkit.getPluginManager().callEvent(e);
                            if (!e.isCancelled()) {
                                toAdd.closeInventory();
                                team.addPlayers(toAdd);
                                skip.add(toAdd);
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        for (Player remaining : arena.getPlayers()) {
            if (skip.contains(remaining)) continue;
            for (ITeam team : arena.getTeams()) {
                if (team.getMembers().size() < arena.getMaxInTeam()) {
                    TeamAssignEvent e = new TeamAssignEvent(remaining, team, arena);
                    Bukkit.getPluginManager().callEvent(e);
                    if (!e.isCancelled()) {
                        remaining.closeInventory();
                        team.addPlayers(remaining);
                    }
                    break;
                }
            }
        }
    }
}
