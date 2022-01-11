package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ArenaTeamSorter implements Comparator<ITeam> {
List<TeamColor> comparisonTeams = Arrays.asList(TeamColor.values());
    @Override
    public int compare(ITeam t1, ITeam t2) {
        Integer t1Index = comparisonTeams.indexOf(t1.getColor());
        Integer t2Index = comparisonTeams.indexOf(t2.getColor());
        return t1Index.compareTo(t2Index);
    }
}
