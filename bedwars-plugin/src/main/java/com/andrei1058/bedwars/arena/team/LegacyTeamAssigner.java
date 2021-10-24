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

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.TeamAssignEvent;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.andrei1058.bedwars.BedWars.getParty;

public class LegacyTeamAssigner {

    private LegacyTeamAssigner(){}

    public static void assignTeams(IArena arena){
        //Check who is having parties
        List<Player> skip = new ArrayList<>(), owners = new ArrayList<>();
        for (Player p : arena.getPlayers()) {
            if (getParty().hasParty(p) && getParty().isOwner(p)) {
                owners.add(p);
            }
        }

        //Mix teams order
        Collections.shuffle(arena.getTeams());

        //Team-up parties
        for (Player p : arena.getPlayers()) {
            if (owners.contains(p)) {
                for (ITeam t : arena.getTeams()) {
                    if (skip.contains(p)) continue;
                    if (t.getSize() + getParty().partySize(p) <= arena.getMaxInTeam()) {
                        skip.add(p);
                        p.closeInventory();
                        TeamAssignEvent e = new TeamAssignEvent(p, t, arena);
                        Bukkit.getPluginManager().callEvent(e);
                        if (!e.isCancelled()) {
                            t.addPlayers(p);
                        }
                        for (Player mem : getParty().getMembers(p)) {
                            if (mem != p) {
                                IArena ia = Arena.getArenaByPlayer(mem);
                                if (ia == null) {
                                    continue;
                                } else if (!ia.equals(arena)) {
                                    continue;
                                }
                                TeamAssignEvent ee = new TeamAssignEvent(p, t, arena);
                                Bukkit.getPluginManager().callEvent(ee);
                                if (!e.isCancelled()) {
                                    t.addPlayers(mem);
                                }
                                skip.add(mem);
                                mem.closeInventory();
                            }
                        }
                    }
                }
            }
        }

        //Give a team to players without a party
        for (Player p : arena.getPlayers()) {
            if (skip.contains(p)) continue;
            ITeam addhere = arena.getTeams().get(0);
            for (ITeam t : arena.getTeams()) {
                if (t.getMembers().size() < arena.getMaxInTeam() && t.getMembers().size() < addhere.getMembers().size()) {
                    addhere = t;
                }
            }
            TeamAssignEvent e = new TeamAssignEvent(p, addhere, arena);
            Bukkit.getPluginManager().callEvent(e);
            if (!e.isCancelled()) {
                addhere.addPlayers(p);
            }
            p.closeInventory();
        }
    }
}
