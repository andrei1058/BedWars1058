package com.andrei1058.bedwars.support.leaderheads;

import com.andrei1058.bedwars.Main;
import me.robin.leaderheads.datacollectors.OnlineDataCollector;
import me.robin.leaderheads.objects.BoardType;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BedWarsKills extends OnlineDataCollector {

    public BedWarsKills() {
        super("bw-kills", Main.plugin.getName(), BoardType.DEFAULT, "&8BedWars Kills", "openbwkills", Arrays.asList(null, null, "&e{amount} kills", null));
    }

    @Override
    public Double getScore(Player player) {
        return Double.valueOf(Main.database.getKills(player));
    }
}
