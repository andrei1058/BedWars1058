package com.andrei1058.bedwars.z_my_classes;

import com.andrei1058.bedwars.z_myadditions.EPG_Listener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class EventHandlersArenaMatrix implements Listener {

    public static void OnEnable(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new EventHandlersArenaMatrix(), plugin);
    }

    @EventHandler
    public void onBlockBreakEvent(final BlockBreakEvent event) {

    }
}
