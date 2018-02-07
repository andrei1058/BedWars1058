package com.andrei1058.bedwars.arena;

import org.bukkit.scheduler.BukkitRunnable;

import static com.andrei1058.bedwars.Main.debug;

public class Rotate extends BukkitRunnable {
    @Override
    public void run() {
        if (!OreGenerator.getGenerators().isEmpty()) {
            for (OreGenerator h : OreGenerator.getRotation()) {
                h.rotate();
            }
        }
    }
}
