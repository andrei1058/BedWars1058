package com.andrei1058.bedwars.slow_mode;

import com.andrei1058.bedwars.z_my_classes.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SlowMode_Listener implements Listener {

    private final boolean isSlowMode = SlowMode.isSlowMode();

    public static void onEnable(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new SlowMode_Listener(), plugin);
    }

    @EventHandler
    public void onBlockIgniteEvent(BlockIgniteEvent event) {

        Location loc = event.getBlock().getLocation();

        if (event.getCause() == BlockIgniteEvent.IgniteCause.SPREAD
        && !SlowMode.checkNeighborBlocks(event.getIgnitingBlock().getLocation())) {
            event.setCancelled(true);
        }

    }
}
