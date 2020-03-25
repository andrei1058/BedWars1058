package com.andrei1058.bedwars.api.upgrades;

import com.andrei1058.bedwars.api.arena.team.ITeam;

public interface UpgradeAction {

    /**
     * Apply action to team.
     */
    void onBuy(ITeam bwt);
}
