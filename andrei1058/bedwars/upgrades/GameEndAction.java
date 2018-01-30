package com.andrei1058.bedwars.upgrades;

import com.andrei1058.bedwars.arena.BedWarsTeam;

import static com.andrei1058.bedwars.Main.plugin;

public class GameEndAction extends UpgradeAction {

    private String name;
    private int bonusDragons;

    public GameEndAction(String name, int bonusDragons){
        this.name = name;
        this.bonusDragons = bonusDragons;
        plugin.debug("loading new GameEndAction: "+getName());
    }

    @Override
    public void execute(BedWarsTeam bwt, int i) {

    }

    public String getName() {
        return name;
    }

    public int getBonusDragons() {
        return bonusDragons;
    }
}
