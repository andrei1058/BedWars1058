package com.andrei1058.bedwars.listeners.blockstatus;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.server.ArenaEnableEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockStatusListener implements Listener {

    @EventHandler
    public void onArenaEnable(ArenaEnableEvent e) {
        if (e == null) return;
        updateBlock((Arena) e.getArena());
    }

    @EventHandler
    public void onStatusChange(GameStateChangeEvent e) {
        if (e == null) return;
        updateBlock((Arena) e.getArena());
    }

    /**
     * Update sign block
     */
    public static void updateBlock(Arena a) {
        if (a == null) return;
        for (Block s : a.getSigns()) {
            if (!(s.getState() instanceof Sign)) continue;
            String path = "", data = "";
            switch (a.getStatus()) {
                case waiting:
                    path = ConfigPath.SIGNS_STATUS_BLOCK_WAITING_MATERIAL;
                    data = ConfigPath.SIGNS_STATUS_BLOCK_WAITING_DATA;
                    break;
                case playing:
                    path = ConfigPath.SIGNS_STATUS_BLOCK_PLAYING_MATERIAL;
                    data = ConfigPath.SIGNS_STATUS_BLOCK_STARTING_DATA;
                    break;
                case starting:
                    path = ConfigPath.SIGNS_STATUS_BLOCK_PLAYING_MATERIAL;
                    data = ConfigPath.SIGNS_STATUS_BLOCK_PLAYING_DATA;
                    break;
                case restarting:
                    path = ConfigPath.SIGNS_STATUS_BLOCK_RESTARTING_MATERIAL;
                    data = ConfigPath.SIGNS_STATUS_BLOCK_RESTARTING_DATA;
                    break;
            }
            BedWars.nms.setJoinSignBackground(s.getState(), Material.valueOf(BedWars.signs.getString(path)));
            BedWars.nms.setJoinSignBackgroundBlockData(s.getState(), (byte) BedWars.signs.getInt(data));
        }
    }
}
