/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.listeners.blockstatus;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.server.ArenaEnableEvent;
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
                default:
                    throw new IllegalStateException("Unhandled game status!");
            }
            BedWars.nms.setJoinSignBackground(s.getState(), Material.valueOf(BedWars.signs.getString(path)));
            BedWars.nms.setJoinSignBackgroundBlockData(s.getState(), (byte) BedWars.signs.getInt(data));
        }
    }
}
