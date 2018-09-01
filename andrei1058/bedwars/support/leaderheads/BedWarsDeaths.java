package com.andrei1058.bedwars.support.leaderheads;

import com.andrei1058.bedwars.Main;
import me.robin.leaderheads.datacollectors.OnlineDataCollector;
import me.robin.leaderheads.objects.BoardType;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BedWarsDeaths extends OnlineDataCollector {

    public BedWarsDeaths() {
        super("bw-deaths", Main.plugin.getName(), BoardType.DEFAULT, "&8BedWars Deaths", "openbwdeaths", Arrays.asList(null, null, "&e{amount} deaths", null));
    }

    @Override
    public Double getScore(Player player) {
        return Double.valueOf(Main.database.getDeaths(player));
    }
}
