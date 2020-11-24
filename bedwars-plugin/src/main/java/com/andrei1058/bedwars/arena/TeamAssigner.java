package com.andrei1058.bedwars.arena;

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
                members = new ArrayList<>(BedWars.getParty().getMembers(player));
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
                }
            }
        }
    }
}
