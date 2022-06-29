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

package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import static com.andrei1058.bedwars.BedWars.*;

public class HungerWeatherSpawn implements Listener {

    private final boolean hungerWaiting;
    private final boolean hungerIngame;

    public HungerWeatherSpawn() {
        hungerWaiting = config.getYml().getBoolean(ConfigPath.GENERAL_CONFIGURATION_HUNGER_WAITING);
        hungerIngame = config.getYml().getBoolean(ConfigPath.GENERAL_CONFIGURATION_HUNGER_INGAME);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        if(e.isCancelled()) return;
        Player player = (Player) e.getEntity();
        IArena arena = Arena.getArenaByPlayer(player);

        // Dont cancel hunger for shared mode outside of arena
        if(arena == null && getServerType() == ServerType.SHARED) return;

        // Cancel hunger in MULTIARENA lobby
        if(arena == null) {
            e.setCancelled(true);
            return;
        }

        // Cancel hunger for spectators
        if(arena.isSpectator(player)) {
            e.setCancelled(true);
            return;
        }

        switch(arena.getStatus()) {
            case waiting:
            case starting:
            case restarting:
                e.setCancelled(!hungerWaiting);
                break;
            case playing:
                e.setCancelled(!hungerIngame);
                break;
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (e.toWeatherState()) {
            if (getServerType() == ServerType.SHARED) {
                if (Arena.getArenaByIdentifier(e.getWorld().getName()) != null) {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    //Used to prevent creature spawn
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
            if (getServerType() != ServerType.BUNGEE) {
                if (Arena.getArenaByIdentifier(e.getEntity().getWorld().getName()) != null) {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent e) {
        IArena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a == null) return;
        /* remove empty bottle */
        switch (e.getItem().getType()) {
            case GLASS_BOTTLE:
                nms.minusAmount(e.getPlayer(), e.getItem(), 1);
                break;
            case MILK_BUCKET:
                e.setCancelled(true);
                nms.minusAmount(e.getPlayer(), e.getItem(), 1);
                int task = Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    Arena.magicMilk.remove(e.getPlayer().getUniqueId());
                    debug("PlayerItemConsumeEvent player " + e.getPlayer() + " was removed from magicMilk");
                }, 20 * 30L).getTaskId();
                Arena.magicMilk.put(e.getPlayer().getUniqueId(), task);
                break;
        }
    }

    @EventHandler
    //Prevent item spawning, issue #60
    public void onItemSpawn(ItemSpawnEvent e) {
        Location l = e.getEntity().getLocation();
        IArena a = Arena.getArenaByIdentifier(l.getWorld().getName());
        if (a == null) return;
        if (a.getStatus() != GameState.playing) {
            e.setCancelled(true);
        }
    }
}
