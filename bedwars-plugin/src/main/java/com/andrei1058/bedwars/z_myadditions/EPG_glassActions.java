package com.andrei1058.bedwars.z_myadditions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class EPG_glassActions {

    final JavaPlugin plugin;

    public EPG_glassActions(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static boolean glassExplode (JavaPlugin plugin, Block block) {
        switch (block.getType()) {
            case LIME_STAINED_GLASS:
                transBlock(plugin, block);
                return true;
            case YELLOW_STAINED_GLASS:
                return true;
            default:
                return false;
        }
    }

    private static void transBlock(JavaPlugin plugin, Block block) {
        block.getLocation().getBlock().setType(Material.YELLOW_STAINED_GLASS);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (block.getType() == Material.YELLOW_STAINED_GLASS) {
                    block.setType(Material.RED_STAINED_GLASS);
                }
            }
        }.runTaskLater(plugin, 60L);
    }

}
