package com.andrei1058.bedwars.lobbysocket;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SendTask {

    /**
     * This is used to send data to new lobby servers to improve data sync
     */
    public SendTask() {
        Bukkit.getScheduler().runTaskTimer(BedWars.plugin, () -> {
            List<IArena> arenas = new ArrayList<>(Arena.getArenas());
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (IArena a : arenas){
                        ArenaSocket.sendMessage(ArenaSocket.formatUpdateMessage(a));
                    }
                }
            }.runTaskAsynchronously(BedWars.plugin);
        }, 100L, 30L);
    }
}
