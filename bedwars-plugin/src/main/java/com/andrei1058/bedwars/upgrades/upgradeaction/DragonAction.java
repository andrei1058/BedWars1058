package com.andrei1058.bedwars.upgrades.upgradeaction;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.upgrades.UpgradeAction;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class DragonAction implements UpgradeAction {

    private final int amount;

    public DragonAction(int amount){
        this.amount = amount;
    }

    @Override
    public void onBuy(@Nullable Player player, ITeam bwt) {
        bwt.setDragons(amount);
    }
}
