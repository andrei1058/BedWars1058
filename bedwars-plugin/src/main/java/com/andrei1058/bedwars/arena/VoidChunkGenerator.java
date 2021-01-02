package com.andrei1058.bedwars.arena;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class VoidChunkGenerator extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid b) {
        return this.createChunkData(world);
    }

    @Override
    public final Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0.0D, 64.0D, 0.0D);
    }
}
