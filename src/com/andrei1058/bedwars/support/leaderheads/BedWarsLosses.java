package com.andrei1058.bedwars.support.leaderheads;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.stats.StatsManager;
import me.robin.leaderheads.datacollectors.OnlineDataCollector;
import me.robin.leaderheads.objects.BoardType;
import org.bukkit.entity.Player;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class BedWarsLosses extends OnlineDataCollector {

    public BedWarsLosses() {
        super("bw-losses", Main.plugin.getName(), BoardType.DEFAULT, "&8BedWars Losses", "openbwlosses", Arrays.asList(null, null, "&e{amount} losses", null));
    }

    @Override
    public Double getScore(Player player) {
        return (double) StatsManager.getStatsCache().getLosses(player.getUniqueId());
    }
}
