package com.andrei1058.bedwars.z_my_classes;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.event.block.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;

@Getter
public class EventsQueues {
    static Map<BlockFace, int[]> SHIFTS = Map.of(
            BlockFace.UP, new int[]{0, 1, 0},
            BlockFace.DOWN, new int[]{0, -1, 0},
            BlockFace.NORTH, new int[]{0, 0, -1},
            BlockFace.EAST, new int[]{1, 0, 0},
            BlockFace.SOUTH, new int[]{0, 0, 1},
            BlockFace.WEST, new int[]{-1, 0, 0}
    );

    private static ConcurrentLinkedQueue<Object[]> queueBlockBreakEvent;
    private static ConcurrentLinkedQueue<Object[]> queueBlockPlaceEvent;
    private static ConcurrentLinkedQueue<Object[]> queueBlockBurnEvent;
    private static ConcurrentLinkedQueue<Object[]> queueBlockExplodeEvent;
    private static ConcurrentLinkedQueue<Object[]> queueBlockFadeEvent;
    private static ConcurrentLinkedQueue<Object[]> queueBlockFertilizeEvent;
    private static ConcurrentLinkedQueue<Object[]> queueBlockFormEvent;
    private static ConcurrentLinkedQueue<Object[]> queueBlockFromToEvent;
    private static ConcurrentLinkedQueue<Object[]> queueBlockGrowEvent;
    private static ConcurrentLinkedQueue<Object[]> queueBlockPistonExtendEvent;
    private static ConcurrentLinkedQueue<Object[]> queueBlockPistonRetractEvent;
    private static ConcurrentLinkedQueue<Object[]> queueBlockRedstoneEvent;
    private static ConcurrentLinkedQueue<Object[]> queueBlockSpreadEvent;
    private static ConcurrentLinkedQueue<Object[]> queueEntityBlockFormEvent;
    private static ConcurrentLinkedQueue<Object[]> queueFluidLevelChangeEvent;
    private static ConcurrentLinkedQueue<Object[]> queueLeavesDecayEvent;
    private static ConcurrentLinkedQueue<Object[]> queueMoistureChangeEvent;
    private static ConcurrentLinkedQueue<Object[]> queueSpongeAbsorbEvent;
    private static ConcurrentLinkedQueue<Object[]> queueTNTPrimeEvent;

    public static void initQueues() {
        queueBlockBreakEvent = new ConcurrentLinkedQueue<>();
        queueBlockPlaceEvent = new ConcurrentLinkedQueue<>();
        queueBlockBurnEvent = new ConcurrentLinkedQueue<>();
        queueBlockExplodeEvent = new ConcurrentLinkedQueue<>();
        queueBlockFadeEvent = new ConcurrentLinkedQueue<>();
        queueBlockFertilizeEvent = new ConcurrentLinkedQueue<>();
        queueBlockFormEvent = new ConcurrentLinkedQueue<>();
        queueBlockFromToEvent = new ConcurrentLinkedQueue<>();
        queueBlockGrowEvent = new ConcurrentLinkedQueue<>();
        queueBlockPistonExtendEvent = new ConcurrentLinkedQueue<>();
        queueBlockPistonRetractEvent = new ConcurrentLinkedQueue<>();
        queueBlockRedstoneEvent = new ConcurrentLinkedQueue<>();
        queueBlockSpreadEvent = new ConcurrentLinkedQueue<>();
        queueEntityBlockFormEvent = new ConcurrentLinkedQueue<>();
        queueFluidLevelChangeEvent = new ConcurrentLinkedQueue<>();
        queueLeavesDecayEvent = new ConcurrentLinkedQueue<>();
        queueMoistureChangeEvent = new ConcurrentLinkedQueue<>();
        queueSpongeAbsorbEvent = new ConcurrentLinkedQueue<>();
        queueTNTPrimeEvent = new ConcurrentLinkedQueue<>();
    }

    public static void removeQueues() {
        queueBlockBreakEvent = null;
        queueBlockPlaceEvent = null;
        queueBlockBurnEvent = null;
        queueBlockExplodeEvent = null;
        queueBlockFadeEvent = null;
        queueBlockFertilizeEvent = null;
        queueBlockFormEvent = null;
        queueBlockFromToEvent = null;
        queueBlockGrowEvent = null;
        queueBlockPistonExtendEvent = null;
        queueBlockPistonRetractEvent = null;
        queueBlockRedstoneEvent = null;
        queueBlockSpreadEvent = null;
        queueEntityBlockFormEvent = null;
        queueFluidLevelChangeEvent = null;
        queueLeavesDecayEvent = null;
        queueMoistureChangeEvent = null;
        queueSpongeAbsorbEvent = null;
        queueTNTPrimeEvent = null;
    }

    public static void addBlockBreakEvent(BlockBreakEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getBlock());

        if (((MyBlockState) data[2]).getType() != Material.AIR) {
            System.getLogger("BlockBreakEvent").log(System.Logger.Level.WARNING, "ПРИ РАЗРУШЕНИИ НЕ ВОЗДУХ!!!");
        }

        queueBlockBreakEvent.add(data);
    }

    public static void addBlockPlaceEvent(BlockPlaceEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getBlock());

        queueBlockPlaceEvent.add(data);
    }

    public static void addBlockBurnEvent(BlockBurnEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getBlock());

        queueBlockBurnEvent.add(data);
    }

    public static void addBlockExplodeEvent(BlockExplodeEvent event) {
        Object[] data = new Object[3];
        
        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = MyBlockState.getBlockStates(event.blockList());

        queueBlockExplodeEvent.add(data);
    }

    public static void addBlockFadeEvent(BlockFadeEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getBlock());

        queueBlockFadeEvent.add(data);
    }

    public static void addBlockFertilizeEvent(BlockFertilizeEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getBlock());

        queueBlockFertilizeEvent.add(data);
    }

    public static void addBlockFormEvent(BlockFormEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getBlock());

        queueBlockFormEvent.add(data);
    }

    public static void addBlockFromToEvent(BlockFromToEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getToBlock());

        queueBlockFromToEvent.add(data);
    }

    public static void addBlockGrowEvent(BlockGrowEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getBlock());

        queueBlockGrowEvent.add(data);
    }

    private static List<Block> getShiftBlocks(BlockFace blockFace, List<Block> blockList, int vector) {

        int[] shift = SHIFTS.get(blockFace);
        Location loc;

        Function<Location, Boolean> checkShiftLocation = (Location loc2) -> {
            boolean res = true;

            for (Block block : blockList) {
                if (block.getLocation().equals(loc2)) { res = false; };
            }

            return res;
        };

        for (Block block : blockList) {
            loc = block.getLocation();
            loc.add(shift[0] * vector, shift[1] * vector, shift[2] * vector);

            if (checkShiftLocation.apply(loc)) {
                blockList.add(block.getWorld().getBlockAt(loc));
            }

        }

        return blockList;
    }

    public static void addBlockPistonExtendEvent(BlockPistonExtendEvent event) {

        BlockFace blockFace = event.getDirection();
        List<Block> blockList = getShiftBlocks(blockFace, event.getBlocks(), 1);

        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = MyBlockState.getBlockStates(blockList);

        queueBlockPistonExtendEvent.add(data);
    }

    public static void addBlockPistonRetractEvent(BlockPistonRetractEvent event) {

        BlockFace blockFace = event.getDirection();
        List<Block> blockList = getShiftBlocks(blockFace, event.getBlocks(), -1);

        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = MyBlockState.getBlockStates(blockList);

        queueBlockPistonRetractEvent.add(data);
    }

    public static void addBlockRedstoneEvent(BlockRedstoneEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getBlock());

        queueBlockRedstoneEvent.add(data);
    }

    public static void addBlockSpreadEvent(BlockSpreadEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getBlock());

        queueBlockRedstoneEvent.add(data);
    }

    public static void addEntityBlockFormEvent(EntityBlockFormEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getBlock());

        queueEntityBlockFormEvent.add(data);
    }

    public static void addFluidLevelChangeEvent(FluidLevelChangeEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getBlock());

        queueFluidLevelChangeEvent.add(data);
    }

    public static void addLeavesDecayEvent(LeavesDecayEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getBlock());

        queueLeavesDecayEvent.add(data);
    }

    public static void addMoistureChangeEvent(MoistureChangeEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getBlock());

        queueMoistureChangeEvent.add(data);
    }

    public static void addSpongeAbsorbEvent(SpongeAbsorbEvent event) {
        Object[] data = new Object[3];

        List<Block> blockList = new ArrayList<>();

        for (BlockState blockState : event.getBlocks()) {
            blockList.add(blockState.getBlock());
        }

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = MyBlockState.getBlockStates(blockList);

        queueSpongeAbsorbEvent.add(data);
    }

    public static void addTNTPrimeEvent(TNTPrimeEvent event) {
        Object[] data = new Object[3];

        data[0] = System.nanoTime();
        data[1] = event;
        data[2] = new MyBlockState(event.getBlock());

        queueTNTPrimeEvent.add(data);
    }
}
