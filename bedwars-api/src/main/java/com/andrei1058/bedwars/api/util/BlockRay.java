package com.andrei1058.bedwars.api.util;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
    Used for tracing blocks between two vectors.
    Note: this could return the same blocks multiple times.
*/
public class BlockRay implements Iterator<Block> {
    private final World world;

    private final Vector delta;

    private final double multiple;
    private final int parts;
    private int consumed;

    private final double xOffset;
    private final double yOffset;
    private final double zOffset;

    private double lcx;
    private double lcy;
    private double lcz;

    private int currentBlock;

    private final Block[] blockQueue;

    /**
     * Constructs a BlockRay instance.
     *
     * @param world bukkit world to get the blocks from.
     * @param src   the source vector (where we shoot the ray).
     * @param dst   the destination vector.
     * @param step  how frequent to check the ray (0.25 - 0.5 recommended).
     */
    public BlockRay(World world, Vector src, Vector dst, double step) {

        // How much `pov` needs to change in order to reach the target
        this.delta = new Vector(
                dst.getBlockX() - src.getBlockX(),
                dst.getBlockY() - src.getBlockY(),
                dst.getBlockZ() - src.getBlockZ()
        );

        if (delta.lengthSquared() == 0) {
            throw new IllegalArgumentException("The source vector is the same as the destination vector");
        }

        this.world = world;

        // Coordinates for where we should shoot our ray
        // Exactly from the center.
        this.xOffset = src.getBlockX() + 0.5;
        this.yOffset = src.getBlockY() + 0.5;
        this.zOffset = src.getBlockZ() + 0.5;

        // The last vector components (last offset)
        this.lcx = 0;
        this.lcy = 0;
        this.lcz = 0;

        // How much one part is worth
        this.multiple = (1 / (delta.length() / step));

        // How many sections `delta` could be split up to
        this.parts = NumberConversions.ceil(delta.length() / step);

        // Where we hold our components per step
        // 0 -> X component
        // 1 -> Y component
        // 2 -> Z component
        // 3 -> XY component
        // 4 -> XZ component
        // 5 -> ZY component
        // 6 -> Resultant
        this.blockQueue = new Block[7];

        // The current component (gets decremented whenever next() is called)
        this.currentBlock = 6;

        scan();
    }

    @Override
    public boolean hasNext() {
        return consumed <= parts || currentBlock >= 0;
    }

    /**
     * This could return similar blocks multiple times.
     *
     * @return Current block at the ray's position.
     */
    @Override
    public Block next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more blocks");
        }

        if (currentBlock < 0) {
            scan();
            currentBlock = 6;
        }

        return blockQueue[currentBlock--];

    }

    private void scan() {
        double cx = (multiple * delta.getX() * consumed);
        double cy = (multiple * delta.getY() * consumed);
        double cz = (multiple * delta.getZ() * consumed);

        int lastXFloor = NumberConversions.floor(xOffset + lcx);
        int lastYFloor = NumberConversions.floor(yOffset + lcy);
        int lastZFloor = NumberConversions.floor(zOffset + lcz);

        int currentXFloor = NumberConversions.floor(xOffset + cx);
        int currentYFloor = NumberConversions.floor(yOffset + cy);
        int currentZFloor = NumberConversions.floor(zOffset + cz);


        // Get the X component separately
        blockQueue[0] = world.getBlockAt(
                currentXFloor,
                lastYFloor,
                lastZFloor
        );

        // Get the Y component separately
        blockQueue[1] = world.getBlockAt(
                lastXFloor,
                currentYFloor,
                lastZFloor
        );

        // Get the Z component separately
        blockQueue[2] = world.getBlockAt(
                lastXFloor,
                lastYFloor,
                currentZFloor
        );

        // Get the XY separately
        blockQueue[3] = world.getBlockAt(
                currentXFloor,
                currentYFloor,
                lastZFloor
        );

        // Get the XZ separately
        blockQueue[4] = world.getBlockAt(
                currentXFloor,
                lastYFloor,
                currentZFloor
        );

        // Get the ZY separately
        blockQueue[5] = world.getBlockAt(
                lastXFloor,
                currentYFloor,
                currentZFloor
        );

        // Get the resultant (cx, cy, cz) together
        blockQueue[6] =  world.getBlockAt(
                currentXFloor,
                currentYFloor,
                currentZFloor
        );

        lcx = cx;
        lcy = cy;
        lcz = cz;

        consumed += 1;
    }

}
