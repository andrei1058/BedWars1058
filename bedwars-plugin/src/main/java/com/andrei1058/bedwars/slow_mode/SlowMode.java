package com.andrei1058.bedwars.slow_mode;

import com.andrei1058.bedwars.API;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.z_my_classes.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.andrei1058.bedwars.BedWars.plugin;

public class SlowMode {
    private final static boolean isTurnedOn = new API().getConfigs()
                                                      .getMainConfig()
                                                      .getBoolean(
                                                              ConfigPath.GENERAL_CONFIG_SLOW_MODE
                                                      );

    private static Map<Block, Integer> fireBlocks = new HashMap<Block, Integer>();

    public static boolean isSlowMode() {
        return isTurnedOn;
    }

    public static boolean checkNeighborBlocks(Location loc) {
        ArrayList<Location> locsNeighbor =  BlockUtils.getNeighborsBlocks(loc);

        for (Location locNeighbor : locsNeighbor) {
            Block neighbor = loc.getWorld().getBlockAt(locNeighbor);
            if (neighbor.getType().isFlammable() && BlockUtils.checkBlockPlacedByPlayer(neighbor)) { return true; }
        }

        return false;
    }

    public static void setFire(Location loc) {
        if ((loc.getBlock().getType() == Material.AIR
            || (loc.getBlock().getType() == Material.FIRE
            && !fireBlocks.containsKey(loc.getBlock())))
            && checkNeighborBlocks(loc)) {

            loc.getBlock().setType(Material.FIRE);
            fireBlocks.put(loc.getBlock(), 0);
            spreadFireActivate(loc.getBlock());

        }
    }

    public static void spreadFireActivate(Block block) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (block.getType() != Material.FIRE
                || !checkNeighborBlocks(block.getLocation())) {
                    if (block.getType() == Material.FIRE) {
                        block.setType(Material.AIR);
                    }
                    fireBlocks.remove(block);
                    this.cancel();
                    return;
                } else if (fireBlocks.get(block) == 2 * 4) {
                    spreadFire(block);
                    fireBlocks.put(block, -1);
                }
                fireBlocks.put(block, fireBlocks.get(block) + 1);
            }
        }.runTaskTimer(plugin, 5L, 5L);
    }

    public static void spreadFire(Block block) {
        Location loc = block.getLocation();

        for (int x = -3; x < 4; x++) {
            for (int z = -3; z < 4; z++) {
                for (int y = -3; y < 4; y++) {
                    setFire(new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z));
                }
            }
        }
    }
}
