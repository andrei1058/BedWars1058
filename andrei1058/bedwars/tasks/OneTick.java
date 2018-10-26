package com.andrei1058.bedwars.tasks;

import com.andrei1058.bedwars.arena.OreGenerator;
import org.bukkit.scheduler.BukkitRunnable;

public class OneTick extends BukkitRunnable {
    @Override
    public void run() {

        //OneTick generators
        for (OreGenerator h : OreGenerator.getRotation()) {
            h.rotate();
        }
    }
}
