package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.arena.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import static com.andrei1058.bedwars.support.version.common.VersionCommon.api;

// Prevent item swap.
public class SwapItem implements Listener {

    @EventHandler
    public void itemSwap(PlayerSwapHandItemsEvent e) {
        if (e.isCancelled()) return;
        if (api.getArenaUtil().isPlaying(e.getPlayer())) {
            if (api.getArenaUtil().getArenaByPlayer(e.getPlayer()).getStatus() != GameState.playing)
                e.setCancelled(true);
        } else if (api.getArenaUtil().isSpectating(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
