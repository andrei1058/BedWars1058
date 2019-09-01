package com.andrei1058.bedwars.listeners.blockstatus;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.server.ArenaEnableEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockStatusListener implements Listener {

    @EventHandler
    public void onArenaEnable(ArenaEnableEvent e) {
        updateBlock((Arena) e.getArena());
    }

    @EventHandler
    public void onStatusChange(GameStateChangeEvent e) {
        updateBlock((Arena) e.getArena());
    }

    /**
     * Update sign block
     */
    public static void updateBlock(Arena a) {
        for (BlockState s : a.getSigns()) {
            if (!(s instanceof Sign)) continue;
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
                case restarting:
                case starting:
                    path = ConfigPath.SIGNS_STATUS_BLOCK_PLAYING_MATERIAL;
                    data = ConfigPath.SIGNS_STATUS_BLOCK_PLAYING_DATA;
                    break;
            }
            Main.nms.setJoinSignBackground(s, Material.valueOf(Main.signs.getString(path)));
            Main.nms.setJoinSignBackgroundBlockData(s, (byte) Main.signs.getInt(data));
        }
    }
}
