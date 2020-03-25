package com.andrei1058.bedwars.lobbysocket;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;

public class SendTask {

    /**
     * This is used to send data to new lobby servers to improve data sync
     */
    public SendTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(BedWars.plugin, () -> {
            for (IArena a : Arena.getArenas()){
                ArenaSocket.sendMessage(ArenaSocket.formatUpdateMessage(a));
            }
        }, 100L, 30L);
    }
}
