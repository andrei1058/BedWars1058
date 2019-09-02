package com.andrei1058.bedwars.support.leaderheads;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class LeaderHeadsSupport {

    /** Initialize stats */
    public static void initLeaderHeads(){
        Plugin leaderheads = Bukkit.getPluginManager().getPlugin("LeaderHeads");
        if(leaderheads != null) {
            BedWars.plugin.getLogger().info("Hook into LeaderHeads support!");
            new BedWarsKills();
            new BedWarsFinalKills();
            new BedWarsWins();
            new BedWarsLosses();
            new BedWarsDeaths();
            new BedWarsFinalDeaths();
            new BedWarsBedsDestroyed();
            new BedWarsGamesPlayed();
        }
    }
}
