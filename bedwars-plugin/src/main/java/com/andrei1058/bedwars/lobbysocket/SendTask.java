package com.andrei1058.bedwars.lobbysocket;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;

public class SendTask {

    /**
     * This is used to send data to new lobby servers to improve data sync
     */
    public SendTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(BedWars.plugin, () -> {
            if (!Arena.getArenas().isEmpty()) {
                ArenaSocket.sendMessage(Arena.getArenas().get(0));
            }
        }, 0, 100L);
    }
}
