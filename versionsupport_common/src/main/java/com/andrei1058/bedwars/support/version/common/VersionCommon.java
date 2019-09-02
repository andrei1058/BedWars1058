package com.andrei1058.bedwars.support.version.common;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.server.VersionSupport;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class VersionCommon {

    private static BedWars api;

    public VersionCommon(VersionSupport versionSupport){
        api = Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(BedWars.class)).getProvider();
        if (versionSupport.getVersion() > 0){
            registerListeners(versionSupport.getPlugin(), new SwapItem_1_9Plus());
        }
    }

    private void registerListeners(Plugin plugin, Listener... listener){
        for (Listener l : listener){
            Bukkit.getPluginManager().registerEvents(l, plugin);
        }
    }


    private static class SwapItem_1_9Plus implements Listener {
        @EventHandler
        public void itemSwap(PlayerSwapHandItemsEvent e) {
            if (e.isCancelled()) return;
            if (api.getArenaUtil().isPlaying(e.getPlayer())) {
                if (api.getArenaUtil().getArenaByPlayer(e.getPlayer()).getStatus() != GameState.playing) e.setCancelled(true);
            } else if (api.getArenaUtil().isSpectating(e.getPlayer())){
                e.setCancelled(true);
            }
        }
    }
}
