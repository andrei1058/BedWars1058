package com.andrei1058.bedwars.upgrades.upgradeaction;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.upgrades.UpgradeAction;

public class DragonAction implements UpgradeAction {

    private int amount;

    public DragonAction(int amount){
        this.amount = amount;
    }
    @Override
    public void onBuy(ITeam bwt) {
        bwt.setDragons(amount);
    }
}
