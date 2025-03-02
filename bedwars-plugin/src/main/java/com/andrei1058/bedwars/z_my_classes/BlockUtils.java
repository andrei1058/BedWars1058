package com.andrei1058.bedwars.z_my_classes;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class BlockUtils {
    public static ArrayList<Block> getNeighborsBlocks(Block block) {
        ArrayList<Block> blocks = new ArrayList<>();
        Location loc = block.getLocation();

        int[][] directions = {
                { 1,  0,  0},
                {-1,  0,  0},
                { 0,  1,  0},
                { 0, -1,  0},
                { 0,  0,  1},
                { 0,  0, -1}
        };

        for (int[] dir : directions) {
            blocks.add(
                block.getWorld().getBlockAt(loc.getBlockX() + dir[0], loc.getBlockY() + dir[1], loc.getBlockZ() + dir[2]
                )
            );
        };

        return blocks;
    }

    public static ArrayList<Location> getNeighborsBlocks(Location loc) {
        ArrayList<Location> locs = new ArrayList<>();

        int[][] directions = {
                { 1,  0,  0},
                {-1,  0,  0},
                { 0,  1,  0},
                { 0, -1,  0},
                { 0,  0,  1},
                { 0,  0, -1}
        };

        for (int[] dir : directions) {
            locs.add(new Location(loc.getWorld(), loc.getBlockX() + dir[0], loc.getBlockY() + dir[1], loc.getBlockZ() + dir[2]));
        };

        return locs;
    }

    public static boolean checkBlockPlacedByPlayer(Block block) {
        Location loc = block.getLocation();
        World world = block.getWorld();

        IArena arena = Arena.getArenaByName(world.getName());

        for (Vector pos : arena.getPlaced()) {
            if (pos.getBlockX() == loc.getBlockX()
                    && pos.getBlockZ() == loc.getBlockZ()
                    && pos.getBlockY() == loc.getBlockY()
            ) { return true; }
        }

        return false;
    }
}
