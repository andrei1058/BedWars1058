package com.andrei1058.bedwars.patch;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.BlockIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Fix <a href="https://github.com/andrei1058/BedWars1058/issues/292">#292</a>
 */
public class TntGlassFix implements Listener {

    @EventHandler
    public void onBlow(@NotNull EntityExplodeEvent e) {
        if (e.getEntity().getType() == EntityType.PRIMED_TNT ||
                e.getEntity().getType() == EntityType.FIREBALL ||
                e.getEntity().getType() == EntityType.SMALL_FIREBALL) {
            List<Block> destroyed = new ArrayList<>(e.blockList());

            for (Block block :
                    destroyed) {
                boolean glass = false;
                for (Block iterated : getBlocksFromLocToLoc(e.getEntity().getLocation(), block.getLocation())) {
                    if (BedWars.nms.isGlass(iterated.getType())) {
                        glass = true;
                        break;
                    }
                }
                if (glass) {
                    e.blockList().remove(block);
                }
            }
        }
    }

    private @NotNull @Unmodifiable List<Block> getBlocksFromLocToLoc(@NotNull Location start, @NotNull Location end) {
        int distance = (int) start.distance(end);

        if (distance > 8) {
            return Collections.emptyList();
        }

        List<Block> blocks = new ArrayList<>();
        BlockIterator blockIterator = new BlockIterator(end.getWorld(), start.toVector(), end.toVector(), 0, distance);

        int x = 0;

        while (blockIterator.hasNext()) {
            x++;
            if (x > 7) {
                break;
            }
            Block current = blockIterator.next();
            if (current.getType() != Material.AIR) {
                blocks.add(current);
            }
        }

        return blocks;
    }
}
