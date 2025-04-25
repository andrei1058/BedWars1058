package com.andrei1058.bedwars.z_listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.water.WaterUtils;
import com.andrei1058.bedwars.z_my_classes.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static com.andrei1058.bedwars.BedWars.plugin;

public class WaterSource implements Listener {

    private final HashMap<Block, Integer> spillingWater = new HashMap<>();
    private static JavaPlugin thisPlugin;
    private static final List<Block> notSpillingFlow = new ArrayList<>();
    private static final Map<Block, List<Block>> flows = new HashMap<>();


    /**
     * Registers the listener with the provided plugin.
     *
     * @param plugin The JavaPlugin instance to register the listener.
     */
    public static void onEnable(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new WaterSource(), plugin);
        thisPlugin = plugin;
    }

    /**
     * Handles the event when a player empties a bucket, managing water placement and cleanup.
     *
     * @param event The PlayerBucketEmptyEvent triggered when a player empties a bucket.
     */
    @EventHandler
    public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();
        IArena arena = Arena.getArenaByPlayer(event.getPlayer());

        // Check the block below the placed water
        location.setY(location.getY() - 1);
        Material type = location.getBlock().getType();

        arena.getWaterSources().put(block, 0);

//        processWaterSource(block, false, Arena.getArenaByPlayer(event.getPlayer()));

//        if (!type.isSolid() || type == Material.FIRE || type == Material.AIR) {
//            spillingWater.put(block, 3);
//            new BukkitRunnable() {
//                @Override
//                public void run() {
//                    if (!WaterUtils.isWater(block)) {
//                        spillingWater.remove(block);
//                        cancel();
//                        return;
//                    } else if (spillingWater.get(block) == 0) {
//                        if (WaterUtils.isWater(block)) {
//                            WaterUtils.deleteWater(block);
//                        }
//                        spillingWater.remove(block);
//                        cancel();
//                        return;
//                    }
//                    spillingWater.put(block, spillingWater.get(block) - 1);
//                }
//            }.runTaskTimer(plugin, 10L, 10L);
//        }
    }

    /**
     * Handles water flow events, controlling water spreading behavior.
     *
     * @param event The BlockFromToEvent triggered when a block (e.g., water) flows to another block.
     */
    @EventHandler
    public void onBlockFromToEvent(BlockFromToEvent event) {
//        Block block = event.getBlock();
//
//        // Prevent flow for blocks marked as not spilling
//        if (notSpillingFlow.contains(block)) {
//            event.setCancelled(true);
//            return;
//        }
//
//        // Cancel flow for certain water types or waterlogged blocks
//        if ((WaterUtils.isWaterType(block) && !spillingWater.containsKey(block) && ((Levelled) block.getBlockData()).getLevel() == 0)
//                || (WaterUtils.isWaterlogged(block) && !spillingWater.containsKey(block))) {
//            event.setCancelled(true);
//            return;
//        }
//
//        Block toBlock = event.getToBlock();
//
//        // Initialize flow tracking
//        flows.computeIfAbsent(block, k -> new ArrayList<>());
//        flows.computeIfAbsent(toBlock, k -> new ArrayList<>());
//        flows.get(block).add(toBlock);
//
//        // Schedule water flow cleanup
//        new BukkitRunnable() {
//            int counter = 6;
//
//            @Override
//            public void run() {
//                if (!WaterUtils.isWaterFlow(toBlock)) {
//                    cancel();
//                    return;
//                }
//                counter--;
//                if (counter == 0) {
//                    for (Block flow : flows.get(toBlock)) {
//                        notSpillingFlow.add(flow);
//                        new BukkitRunnable() {
//                            @Override
//                            public void run() {
//                                if (!WaterUtils.isWaterFlow(flow)) {
//                                    notSpillingFlow.remove(flow);
//                                    cancel();
//                                }
//                            }
//                        }.runTaskTimer(plugin, 0L, 5L);
//                    }
//                    flows.remove(block);
//                    WaterUtils.deleteWater(toBlock);
//                    cancel();
//                }
//            }
//        }.runTaskTimer(plugin, 10L, 10L);
    }

//    public static void checkWaterSource(Block block) {
//        if (!WaterUtils.isWaterSource(block)) {
//            waterSources.add(block);
//            return;
//        }
//
//        List<Block> neighborsBlocks = BlockUtils.getNeighborsBlocks(block, EnumSet.of(BlockUtils.Direction.UP), true);
//        boolean isStatic = true;
//
//        for (Block neighborBlock : neighborsBlocks) {
//            Material type = neighborBlock.getType();
//            if (!(!type.isSolid() || type == Material.FIRE || type == Material.AIR || type == Material.WATER)) {
//                isStatic = false;
//                break;
//            }
//        }
//
//        if (staticWaterSources.contains(block) && !isStatic) {
//
//        } else if (!staticWaterSources.contains(block) && isStatic) {
//            staticWaterSources.add(block);
//
//        }
//    }

    public static Integer processWaterSource(Map.Entry<Block, Integer> blockEntry, Map<Block, Integer> toAddToWaterSources, Set<Block> toRemoveFromWaterSources) {

        Block block = blockEntry.getKey();
        Integer timer = blockEntry.getValue();

        if (!WaterUtils.isWaterSource(block)) {
            toRemoveFromWaterSources.add(block);
            return 0;
        }

        List<Block> neighborsBlocks = BlockUtils.getNeighborsBlocks(block, EnumSet.of(BlockUtils.Direction.UP), true);
        boolean isStatic = true;

        for (Block neighborBlock : neighborsBlocks) {
            Material type = neighborBlock.getType();
            if (WaterUtils.isWaterSource(neighborBlock)) {
                toAddToWaterSources.put(neighborBlock, 0);
                continue;
            }
            if (!(type.isSolid())) {
                isStatic = false;
            }
        }

        if (!isStatic) {
            timer += 1;
            if (timer == 6) {
                if (WaterUtils.isWaterlogged(block)) {
                    WaterUtils.deleteWater(block);
                    toRemoveFromWaterSources.add(block);
                } else {
                    deleteWater(block, toRemoveFromWaterSources);
                    for (Block neighborBlock : BlockUtils.getNeighborsBlocks(
                            block,
                            EnumSet.of(
                                    BlockUtils.Direction.UP,
                                    BlockUtils.Direction.DOWN
                            ),
                            true)
                    ) {
                        deleteWater(neighborBlock, toRemoveFromWaterSources);
                    }
                }
            }
        } else {
            timer = 0;
        }

        return timer;
    }

    private static void deleteWater(Block block, Set<Block> setToRemove) {
        if (WaterUtils.isWaterSource(block)) {
            Block iterationBlock = block;
            while (WaterUtils.isWaterSource(iterationBlock, false)) {
                WaterUtils.changeWaterLevel(block, 15);
                setToRemove.add(iterationBlock);
                iterationBlock = BlockUtils.getNeighborsBlocks(iterationBlock, EnumSet.of(BlockUtils.Direction.UP)).get(0);
            }
        }
    }

    /**
     * Prints the current state of the flows map for debugging purposes.
     *
     * @param map The map containing block-to-flow mappings.
     */
    private static void printMap(Map<Block, List<Block>> map) {
        System.out.println("\nMapFlow:");
        for (Block block : map.keySet()) {
            System.out.printf("Block: x=%s y=%s z=%s%n",
                    block.getLocation().getX(),
                    block.getLocation().getY(),
                    block.getLocation().getZ());
            for (Block flow : map.get(block)) {
                System.out.printf("     Flow: x=%s y=%s z=%s%n",
                        flow.getLocation().getX(),
                        flow.getLocation().getY(),
                        flow.getLocation().getZ());
            }
        }
    }
}