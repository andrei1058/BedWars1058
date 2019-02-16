package com.andrei1058.bedwars.tasks;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.OreGenerator;
import com.andrei1058.bedwars.arena.SBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import static com.andrei1058.bedwars.Main.nms;

public class Refresh extends BukkitRunnable {

    public static HashMap<UUID, Integer> showTime = new HashMap<>();

    @Override
    public void run() {
        for (SBoard sb : SBoard.getScoreboards()){
            sb.refresh();
        }

        for (OreGenerator o  : OreGenerator.getGenerators()) {
            o.spawn();
        }

        if (!showTime.isEmpty()){
            for (Map.Entry<UUID, Integer> e :  new HashMap<>(showTime).entrySet()){
                if (e.getValue() <= 0){
                    Player p1 = Bukkit.getPlayer(e.getKey());
                    for (Player p : Arena.getArenaByPlayer(p1).getWorld().getPlayers()){
                        nms.showArmor(p1, p);
                    }
                    showTime.remove(e.getKey());
                } else {
                    showTime.replace(e.getKey(), e.getValue()-1);
                }
            }
        }

        nms.refreshDespawnables();
    }
}
