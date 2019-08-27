package com.andrei1058.bedwars.support.version.v1_9_R2.listeners;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class v1_9_R2_SwapItem implements Listener {

    @EventHandler
    public void itemSwap(PlayerSwapHandItemsEvent e) {
        if (e.isCancelled()) return;
        if (Arena.isInArena(e.getPlayer())) {
            if (Arena.getArenaByPlayer(e.getPlayer()).getStatus() != GameState.playing) e.setCancelled(true);
            if (Arena.getArenaByPlayer(e.getPlayer()).isSpectator(e.getPlayer())) e.setCancelled(true);
        }
    }
}
