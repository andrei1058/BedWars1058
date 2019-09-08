package com.andrei1058.bedwars.support.vipfeatures;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.events.player.PlayerJoinArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.vipfeatures.api.IVipFeatures;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class VipListeners implements Listener {

    private IVipFeatures api;

    public VipListeners(IVipFeatures api){
        this.api = api;
    }

    @EventHandler
    public void onServerJoin(PlayerJoinEvent e) {
        if (BedWars.getServerType() == ServerType.MULTIARENA) {
            Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
                api.givePlayerItemStack(e.getPlayer());
            }, 10L);
        }
    }

    @EventHandler
    public void onArenaJoin(PlayerJoinArenaEvent e) {
        Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
            api.givePlayerItemStack(e.getPlayer());
        }, 10L);
    }
    @EventHandler
    public void onServerJoin(PlayerLeaveArenaEvent e) {
        if (BedWars.getServerType() == ServerType.MULTIARENA) {
            Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
                if (e.getPlayer().isOnline()) api.givePlayerItemStack(e.getPlayer());
            }, 10L);
        }
    }
}
