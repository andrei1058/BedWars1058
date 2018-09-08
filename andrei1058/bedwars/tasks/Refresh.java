package com.andrei1058.bedwars.tasks;

import com.andrei1058.bedwars.arena.OreGenerator;
import com.andrei1058.bedwars.arena.SBoard;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import static com.andrei1058.bedwars.Main.nms;

public class Refresh extends BukkitRunnable {

    public static HashMap<Player, Integer> showTime = new HashMap<>();

    @Override
    public void run() {
        for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
            sb.refresh();
        }

        for (OreGenerator o : OreGenerator.getGenerators()) {
            o.spawn();
        }

        nms.refreshDespawnables();
    }
}
