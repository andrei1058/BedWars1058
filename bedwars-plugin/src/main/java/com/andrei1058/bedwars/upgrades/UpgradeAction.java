package com.andrei1058.bedwars.upgrades;

import com.andrei1058.bedwars.api.arena.team.ITeam;

public abstract class UpgradeAction {

    public abstract void execute(ITeam bwt, int slot);
}
