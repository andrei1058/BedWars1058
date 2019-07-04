package com.andrei1058.bedwars.arena.spectator;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class v1_9PlusListener implements Listener {

    @EventHandler
    public void itemSwap(PlayerSwapHandItemsEvent e){
        if (e.isCancelled()) return;
        if (Arena.isInArena(e.getPlayer())){
            if (Arena.getArenaByPlayer(e.getPlayer()).isSpectator(e.getPlayer())) e.setCancelled(true);
        }
    }
}
