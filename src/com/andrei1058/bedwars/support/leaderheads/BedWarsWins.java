package com.andrei1058.bedwars.support.leaderheads;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.stats.StatsManager;
import me.robin.leaderheads.datacollectors.OnlineDataCollector;
import me.robin.leaderheads.objects.BoardType;
import org.bukkit.entity.Player;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class BedWarsWins extends OnlineDataCollector {

    public BedWarsWins() {
        super("bw-wins", Main.plugin.getName(), BoardType.DEFAULT, "&8BedWars Wins", "openbwwins", Arrays.asList(null, null, "&e{amount} wins", null));
    }

    @Override
    public Double getScore(Player player) {
        return (double) StatsManager.getStatsCache().getWins(player.getUniqueId());
    }
}
