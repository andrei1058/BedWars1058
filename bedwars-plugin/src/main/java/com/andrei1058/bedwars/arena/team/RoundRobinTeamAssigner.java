package com.andrei1058.bedwars.arena.team;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.ITeamAssigner;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RoundRobinTeamAssigner implements ITeamAssigner {
    @Override
    public void assignTeams(IArena arena) {
        List<ITeam> teams = arena.getTeams();
        List<Player> unassignedPlayers = getUnassignedPlayers(arena);

        while (!unassignedPlayers.isEmpty()) {
            for (ITeam team : teams) {
                if (unassignedPlayers.isEmpty()) {
                    break; // No more players to assign
                }
                int randomIndex = ThreadLocalRandom.current().nextInt(unassignedPlayers.size());
                Player playerToAssign = unassignedPlayers.remove(randomIndex);
                team.addPlayers(playerToAssign);
            }
        }
    }


    private List<Player> getUnassignedPlayers(IArena arena) {
        List<ITeam> teams = arena.getTeams();
        return arena.getPlayers().stream()
                .filter(p -> teams.stream().noneMatch(team -> team.getMembers().contains(p)))
                .collect(Collectors.toList());
    }

}
