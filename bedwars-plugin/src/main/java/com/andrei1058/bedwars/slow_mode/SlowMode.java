package com.andrei1058.bedwars.slow_mode;

import com.andrei1058.bedwars.API;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.z_my_classes.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.andrei1058.bedwars.BedWars.plugin;

public class SlowMode {
    private final static boolean isTurnedOn = new API().getConfigs()
                                                      .getMainConfig()
                                                      .getBoolean(
                                                              ConfigPath.GENERAL_SLOW_MODE
                                                      );

    private static Map<Block, Integer> fireBlocks = new HashMap<Block, Integer>();

    public static boolean isSlowMode() {
        return isTurnedOn;
    }

    public static boolean checkNeighborBlocks(Location loc) {
        List<Location> locsNeighbor =  BlockUtils.getNeighborsBlocks(loc);

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

            spawnFireBlock(loc);
            fireBlocks.put(loc.getBlock(), 0);
            spreadFireActivate(loc.getBlock());

        }
    }

    public static void spawnFireBlock(Location loc) {

        if (loc.getBlock().getType() == Material.FIRE) {
            return;
        }

        BlockFace[] blockFaces = new BlockFace[] {
                BlockFace.EAST,
                BlockFace.WEST,
                BlockFace.UP,
                null,
                BlockFace.SOUTH,
                BlockFace.NORTH
        };

        Block block = loc.getBlock();

        ArrayList<Block> blocksNeighbor = BlockUtils.getNeighborsBlocks(loc.getBlock());

//        int treadId = ThreadLocalRandom.current().nextInt(1000, 10000);
        block.setType(Material.FIRE);
//        System.out.printf("[%s]     Block : %s %s %s %s\n", treadId, block.getType(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());

        MultipleFacing fireData = (MultipleFacing) block.getBlockData();

        if (!blocksNeighbor.get(3).getType().isFlammable()) {
            for (int i = 0; i < 6; i++) {
                if (i == 3) { continue; };
//                System.out.printf("[%s] Block Neighbor : %s %s %s %s\n", treadId, blocksNeighbor.get(i).getType(), blocksNeighbor.get(i).getLocation().getBlockX(), blocksNeighbor.get(i).getLocation().getBlockY(), blocksNeighbor.get(i).getLocation().getBlockZ());
                if (blocksNeighbor.get(i).getType().isFlammable()) {
//                    System.out.printf("[%s] Set Face: %s %s\n", treadId, blockFaces[i], true);
                    fireData.setFace(blockFaces[i], true);
                }
                else {
//                    System.out.printf("[%s] Set Face: %s %s\n", treadId, blockFaces[i], false);
                    fireData.setFace(blockFaces[i], false);
                }
            }
        } else {
//            System.out.printf("[%s] Block Neighbor Dowm : %s %s %s %s\n", treadId, blocksNeighbor.get(5).getType(), blocksNeighbor.get(5).getLocation().getBlockX(), blocksNeighbor.get(5).getLocation().getBlockY(), blocksNeighbor.get(5).getLocation().getBlockZ());
        }

//        String fireDataString = null;
//        BlockData readFireData = block.getBlockData();
//        try {
//            fireDataString = new ObjectMapper().writeValueAsString(readFireData);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.printf("[%s] Data Block: %s", treadId, fireDataString);


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
