package com.andrei1058.bedwars.support.leaderheads;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.stats.StatsManager;
import me.robin.leaderheads.datacollectors.OnlineDataCollector;
import me.robin.leaderheads.objects.BoardType;
import org.bukkit.entity.Player;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class BedWarsFinalDeaths extends OnlineDataCollector {

    public BedWarsFinalDeaths() {
        super("bw-final-deaths", Main.plugin.getName(), BoardType.DEFAULT, "&8BedWars Final Deaths", "openbwfinaldeaths", Arrays.asList(null, null, "&e{amount} final deaths", null));
    }

    @Override
    public Double getScore(Player player) {
        return (double) StatsManager.getStatsCache().getPlayerFinalDeaths(player.getUniqueId());
    }
}
