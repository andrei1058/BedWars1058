package com.andrei1058.bedwars.support.leaderheads;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.stats.StatsManager;
import me.robin.leaderheads.datacollectors.OnlineDataCollector;
import me.robin.leaderheads.objects.BoardType;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BedWarsGamesPlayed extends OnlineDataCollector {

    public BedWarsGamesPlayed() {
        super("bw-games-played", Main.plugin.getName(), BoardType.DEFAULT, "&8BedWars Games Played", "openbwgames", Arrays.asList(null, null, "&e{amount} games played", null));
    }

    @Override
    public Double getScore(Player player) {
        return (double) StatsManager.getStatsCache().getGamesPlayed(player.getUniqueId());
    }
}
