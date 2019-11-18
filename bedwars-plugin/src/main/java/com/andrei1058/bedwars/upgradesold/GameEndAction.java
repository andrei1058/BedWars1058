package com.andrei1058.bedwars.upgradesold;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;

public class GameEndAction extends UpgradeAction {

    private String name;
    private int bonusDragons;

    public GameEndAction(String name, int bonusDragons) {
        this.name = name;
        this.bonusDragons = bonusDragons;
        BedWars.debug("Loading new GameEndAction: " + getName());
    }

    @Override
    public void execute(ITeam bwt, int i) {

    }

    public String getName() {
        return name;
    }

    public int getBonusDragons() {
        return bonusDragons;
    }
}
