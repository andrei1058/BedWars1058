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

package com.andrei1058.bedwars.halloween;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

public class CobWebRemover {

    private static final LinkedHashMap<IArena, CobWebRemover> taskByArena = new LinkedHashMap<>();

    private int taskId;
    private IArena arena;
    private LinkedHashMap<Block, Long> cobWebs = new LinkedHashMap<>();

    protected CobWebRemover(IArena arena) {
        taskByArena.remove(arena);
        taskByArena.put(arena, this);
        this.arena = arena;
        taskId = Bukkit.getScheduler().runTaskTimer(BedWars.plugin, new RemovalTask(), 20L, 20L).getTaskId();
    }

    public void addCobWeb(Block block) {
        cobWebs.put(block, System.currentTimeMillis() + 7500L);
    }

    public int getTaskId() {
        return taskId;
    }

    public IArena getArena() {
        return arena;
    }

    public static CobWebRemover getByArena(IArena arena){
        return taskByArena.get(arena);
    }

    public static CobWebRemover getByArenaWorld(String world){
        Optional<Map.Entry<IArena, CobWebRemover>> entry = taskByArena.entrySet().stream().filter(arena -> arena.getKey().getWorldName().equals(world)).findFirst();
        return entry.map(Map.Entry::getValue).orElse(null);
    }

    public void destroy() {
        Bukkit.getScheduler().cancelTask(getTaskId());
        taskByArena.remove(arena);
    }

    private class RemovalTask implements Runnable {

        private final LinkedList<Block> toBeRemoved = new LinkedList<>();

        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            cobWebs.forEach((key, value) -> {
                if (value <= currentTime) {
                    toBeRemoved.add(key);
                    if (key.getType().toString().contains("WEB")) {
                        key.breakNaturally();
                    }
                }
            });
            toBeRemoved.forEach(block -> cobWebs.remove(block));
            toBeRemoved.clear();
        }
    }
}
