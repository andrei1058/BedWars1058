package com.andrei1058.bedwars.api.arena.upgrades;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.entity.Player;

import java.util.List;

public interface ITeamUpgrade {

    @Deprecated
    int getSlot();

    @Deprecated
    List<IUpgradeTier> getTiers();

    @Deprecated
    String getName();

    @Deprecated
    void doAction(Player p, ITeam bwt);
}
