package com.andrei1058.bedwars.api.arena.upgrades;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IUpgradeTier {

    @Deprecated
    String getName();

    @Deprecated
    ItemStack getItemStack(Player p, String path, ITeamUpgrade tu, ITeam bwt);

    @Deprecated
    boolean buy(Player p, ITeam bwt, int slot);

    @Deprecated
    boolean hasEnoughMoney(Player p);

    @Deprecated
    boolean isHighest(ITeam tm, ITeamUpgrade tu);

    @Deprecated
    String getCurrency();

    @Deprecated
    String getCurrencyMsg(Player p);

    @Deprecated
    int getCost();
}
