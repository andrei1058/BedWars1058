package com.andrei1058.bedwars.z_myadditions;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;

import java.util.LinkedList;
import java.util.Queue;

public class SaveLava_DeleteWater {

    private static final int MAX_DISTANCE = 3;

    public static void deleteWater(int[] pos, World world) {
        Queue<int[]> queue = new LinkedList<int[]>();
        boolean isFirst = true;

        queue.add(pos);

        while (!queue.isEmpty()) {
            int[] pos_target = queue.poll();

            if (Math.abs(pos_target[0] - pos[0]) <= MAX_DISTANCE
            && Math.abs(pos_target[1] - pos[1]) <= MAX_DISTANCE
            && Math.abs(pos_target[2] - pos[2]) <= MAX_DISTANCE
            && removeBlockWater(pos_target, world)
            || isFirst) {

                int[][] directions = {
                    {1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}
                };

                for (int[] dir : directions) {
                    queue.add(new int[]{pos_target[0] + dir[0], pos_target[1] + dir[1], pos_target[2] + dir[2]});
                }
            }

            isFirst = false;
        }
    }

    public static boolean removeBlockWater(int[] pos, World world) {
        boolean isWater = false;

        Block block = world.getBlockAt(pos[0], pos[1], pos[2]);


        if (block.getType() == Material.WATER) {
            block.setType(Material.AIR);
            isWater = true;
        }
        else if (block.getBlockData() instanceof Waterlogged) {
            Waterlogged waterlogged = (Waterlogged) block.getBlockData();

            if (waterlogged.isWaterlogged()) {
                waterlogged.setWaterlogged(false);
                block.setBlockData(waterlogged);
                isWater = true;
            }
        }

        if (isWater) {
//            world.spawnParticle(Particle.CLOUD, block.getX() + 0.5, block.getY() + 0.3, block.getZ() + 0.5, 1);
            world.playSound(block.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.2F, 1.0F);
        }

        return isWater;
    }

}
