package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.arena.SBoard;
import com.andrei1058.bedwars.api.entity.Despawnable;

import static com.andrei1058.bedwars.BedWars.nms;

public class Refresh implements Runnable {

    @Override
    public void run() {
        for (SBoard sb : SBoard.getScoreboards()){
            sb.refresh();
        }

        for (Despawnable d : nms.getDespawnablesList().values()){
            d.refresh();
        }
    }
}
