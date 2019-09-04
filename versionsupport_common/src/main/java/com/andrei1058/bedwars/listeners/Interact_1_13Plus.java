package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.configuration.ConfigPath;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.andrei1058.bedwars.support.version.common.VersionCommon.api;

public class Interact_1_13Plus implements Listener {

    @EventHandler
    //Check if player is opening an inventory
    public void onInventoryInteract(PlayerInteractEvent e) {
        //noinspection deprecation
        if (e.isCancelled()) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block b = e.getClickedBlock();
        if (b == null) return;
        if (b.getWorld().getName().equals(api.getLobbyWorld()) || api.getArenaUtil().getArenaByPlayer(e.getPlayer()) != null) {
            switch (b.getType().toString()) {
                case "CHIPPED_ANVIL":
                case "DAMAGED_ANVIL":
                    if (api.getConfigs().getMainConfig().getBoolean(ConfigPath.GENERAL_CONFIGURATION_DISABLE_ANVIL)) {
                        e.setCancelled(true);
                    } else {
                        if (api.getArenaUtil().isSpectating(e.getPlayer())) e.setCancelled(true);
                    }
                    break;
            }
        }
    }
}
