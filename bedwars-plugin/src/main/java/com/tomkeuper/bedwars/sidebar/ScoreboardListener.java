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

package com.tomkeuper.bedwars.sidebar;

import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.configuration.ConfigPath;
import com.tomkeuper.bedwars.api.events.player.PlayerBedBreakEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerKillEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerReJoinEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerReSpawnEvent;
import com.tomkeuper.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class ScoreboardListener implements Listener {
    // Leaving this here if needed for possible health integrations. (27/4/2023)
    // Listener is not registered!

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e == null) return;
        if (e.isCancelled()) return;
        if (!(e.getEntity() instanceof Player)) return;
        final Player player = (Player) e.getEntity();
        final IArena arena = Arena.getArenaByPlayer(player);

        int health = (int) Math.ceil((player.getHealth() - e.getFinalDamage()));
        if (arena == null) return;

//        SidebarService.getInstance().refreshHealth(arena, player, health);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRegain(EntityRegainHealthEvent e) {
        if (e == null) return;
        if (e.isCancelled()) return;
        if (!(e.getEntity() instanceof Player)) return;
        final Player player = (Player) e.getEntity();
        final IArena arena = Arena.getArenaByPlayer(player);
        if (arena == null) return;

        int health = (int) Math.ceil(player.getHealth() + e.getAmount());

//        SidebarService.getInstance().refreshHealth(arena, player, health);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onReSpawn(PlayerReSpawnEvent e) {
        if (e == null) return;
        final IArena arena = e.getArena();

//        SidebarService.getInstance().refreshHealth(arena, e.getPlayer(), (int) Math.ceil(e.getPlayer().getHealth()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void reJoin(PlayerReJoinEvent e) {
        if (e == null) return;
        if (e.isCancelled()) return;
        if (!BedWars.config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_PLAYING)) return;
        final IArena arena = e.getArena();
        final Player player = e.getPlayer();

        // re-add player to scoreboard tab list
//        SidebarService.getInstance().handleReJoin(arena, player);
    }

    @EventHandler
    public void onBedDestroy(PlayerBedBreakEvent e) {
        if (e == null) return;
        final IArena arena = e.getArena();

        // refresh placeholders in case placeholders refresh is disabled
//        SidebarService.getInstance().refreshPlaceholders(arena);
    }

    @EventHandler
    public void onFinalKill(PlayerKillEvent e) {
        if (e == null) return;
        if (!e.getCause().isFinalKill()) return;
        final IArena arena = e.getArena();

        // refresh placeholders in case placeholders refresh is disabled
//        SidebarService.getInstance().refreshPlaceholders(arena);
    }
}
