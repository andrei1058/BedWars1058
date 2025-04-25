package com.andrei1058.bedwars.water;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Waterlogged;

public class WaterUtils {
    public static boolean isWaterType(Block block) {
        return block.getType() == Material.WATER;
    }

    public static boolean isWaterlogged(Block block) {
        return block.getBlockData() instanceof Waterlogged
               && ((Waterlogged) block.getBlockData()).isWaterlogged();
    }

    public static boolean isWater(Block block) {
        return isWaterType(block) || isWaterlogged(block);
    }

    public static boolean isWater(Block block, boolean waterlogged) {
        return isWaterType(block) || (isWaterlogged(block) && waterlogged);
    }

    public static boolean isWaterFlow(Block block) {
        return isWaterType(block)
                && ((Levelled) block.getBlockData()).getLevel() != 0;
    }

    public static void changeWaterLevel(Block block, int level) {
        if (isWater(block) && !isWaterlogged(block)) {
            Levelled levelled = (Levelled) block.getBlockData();
            levelled.setLevel(level);
            block.setBlockData(levelled);
        }
    }

    public static boolean isWaterSource(Block block) {
        return isWater(block) && !isWaterFlow(block);
    }

    public static boolean isWaterSource(Block block, boolean waterlogged) {
        return isWater(block, waterlogged) && !isWaterFlow(block);
    }

    public static void deleteWater(Block block) {
        if (isWaterType(block)) {
            block.setType(Material.AIR);
        } else if (isWaterlogged(block)) {
            Waterlogged waterlogged = (Waterlogged) block.getBlockData();
            waterlogged.setWaterlogged(false);
            block.setBlockData(waterlogged);
        }
    }
}
