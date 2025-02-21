package com.andrei1058.bedwars.z_my_classes;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MyBlockState {

    private final Block block;
    private BlockData blockData;
    private Material material;
    private final byte lightLevel;
    private final World world;
    private final int X;
    private final int Y;
    private final int Z;
    private final Chunk chunk;

    public MyBlockState(Block block) {
        this.block = block;
        this.blockData = block.getBlockData();
        this.material = block.getType();
        this.lightLevel = block.getLightLevel();
        this.world = block.getWorld();
        this.X = block.getX();
        this.Y = block.getY();
        this.Z = block.getZ();
        this.chunk = block.getChunk();
    }

    @NotNull
    public Block getBlock() {
        return block;
    };

    @NotNull
    public BlockData getBlockData() {
        return blockData;
    };

    @NotNull
    public Material getType() {
        return material;
    };

    public byte getLightLevel() {
        return lightLevel;
    };

    @NotNull
    public World getWorld() {
        return world;
    };

    public int getX() {
        return X;
    };

    public int getY() {
        return Y;
    };

    public int getZ() {
        return Z;
    };

    @NotNull
    public Location getLocation() {
        return new Location(world, X, Y, Z);
    };

    @Contract("null -> null; !null -> !null")
    @Nullable
    public Location getLocation(@Nullable Location var1) {
        if (var1 == null) return null;

        var1.setX(X);
        var1.setY(Y);
        var1.setZ(Z);
        var1.setWorld(world);

        return var1;
    };

    @NotNull
    public Chunk getChunk() {
        return chunk;
    };

    public void setBlockData(@NotNull BlockData var1) {
        blockData = var1;
    };

    public void setType(@NotNull Material var1) {
        material = var1;
    };

}
