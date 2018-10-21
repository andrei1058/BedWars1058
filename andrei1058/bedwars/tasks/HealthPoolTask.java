package com.andrei1058.bedwars.tasks;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class HealthPoolTask extends BukkitRunnable {

    private BedWarsTeam bwt;
    private int maxX, minX;
    private int maxY, minY;
    private int maxZ, minZ;
    private int blocks;

    public HealthPoolTask(BedWarsTeam bwt) {
        this.bwt = bwt;
        int radius = bwt.getArena().getCm().getInt(ConfigPath.ARENA_ISLAND_RADIUS);
        this.maxX = bwt.getSpawn().getBlockX() + radius;
        this.minX = bwt.getSpawn().getBlockX() - radius;
        this.maxY = bwt.getSpawn().getBlockY() + radius;
        this.minY = bwt.getSpawn().getBlockY() - radius;
        this.maxZ = bwt.getSpawn().getBlockZ() + radius;
        this.minZ = bwt.getSpawn().getBlockZ() - radius;
        this.runTaskTimer(Main.plugin, 0, 20);
        this.blocks = radius^radius;
    }

    @Override
    public void run() {
        for (int x = blocks; x > 0; x--){
            Random r = new Random();
            Random r2 = new Random();
            Random r3 = new Random();

            bwt.getSpawn().getWorld().playEffect(new Location(bwt.getSpawn().getWorld(), r2.nextInt(maxX)+minX, r3.nextInt(maxY)+minY, r.nextInt(maxZ)+minZ), Main.nms.eggBridge(), 2f);
        }
    }
}
