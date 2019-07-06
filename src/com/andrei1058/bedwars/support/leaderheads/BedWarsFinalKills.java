package com.andrei1058.bedwars.support.leaderheads;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.stats.StatsManager;
import me.robin.leaderheads.datacollectors.OnlineDataCollector;
import me.robin.leaderheads.objects.BoardType;
import org.bukkit.entity.Player;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class BedWarsFinalKills extends OnlineDataCollector {

    public BedWarsFinalKills() {
        super("bw-final-kills", Main.plugin.getName(), BoardType.DEFAULT, "&8BedWars Final Kills", "openbwfinalkills", Arrays.asList(null, null, "&e{amount} final kills", null));
    }

    @Override
    public Double getScore(Player player) {
        return (double) StatsManager.getStatsCache().getFinalKills(player.getUniqueId());
    }
}
