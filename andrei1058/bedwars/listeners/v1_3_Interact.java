package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.andrei1058.bedwars.Main.config;

public class v1_3_Interact implements Listener {

    @EventHandler
    //Check if player is opening an inventory
    public void onInventoryInteract(PlayerInteractEvent e){
        if (e.isCancelled()) return;
        Block b = e.getClickedBlock();
        if (b == null) return;
        if (b.getWorld().getName().equals(Main.getLobbyWorld()) || Arena.getArenaByPlayer(e.getPlayer()) != null){
            switch (b.getType().toString()){
                case "CHIPPED_ANVIL":
                case "DAMAGED_ANVIL":
                    if (config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_DISABLE_ANVIL)){
                        e.setCancelled(true);
                    }
                    break;
            }
        }
    }
}
