package com.andrei1058.bedwars.z_my_classes;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MyBlockState {

    private final Block block;
    private BlockData blockData;
    private Material material;
    private final byte lightLevel;
    private final World world;
    private final int x;
    private final int z;
    private final int y;
    private final Location location;
    private final Chunk chunk;

    public MyBlockState(Block block) {
        this.block = block;
        this.blockData = block.getBlockData().clone();
        this.material = block.getType();
        this.lightLevel = block.getLightLevel();
        this.world = block.getWorld();
        this.x = block.getX();
        this.z = block.getZ();
        this.y = block.getY();
        this.location = new Location(world, x, z, y);
        this.chunk = block.getChunk();
    }

    public Material getType() { return material; }

    public void setBlockData(BlockData blockData) {
        this.blockData = blockData.clone();
    }

    public static ArrayList<MyBlockState> getBlockStates(ArrayList<Block> blocks) {
        ArrayList<MyBlockState> blockStates = new ArrayList<>();

        for (Block block : blocks) {
            blockStates.add(new MyBlockState(block));
        }

        return blockStates;
    }

    public static List<MyBlockState> getBlockStates(List<Block> blocks) {
        List<MyBlockState> blockStates = new ArrayList<>();

        for (Block block : blocks) {
            blockStates.add(new MyBlockState(block));
        }

        return blockStates;
    }
}
