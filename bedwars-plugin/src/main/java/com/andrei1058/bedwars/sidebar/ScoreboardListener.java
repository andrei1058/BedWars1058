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

package com.andrei1058.bedwars.sidebar;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.player.*;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class ScoreboardListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDamage(@NotNull EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        final Player player = (Player) e.getEntity();
        final IArena arena = Arena.getArenaByPlayer(player);

        if (arena == null) {
            return;
        }

        int health = (int) Math.ceil((player.getHealth() - e.getFinalDamage()));
        SidebarService.getInstance().refreshHealth(arena, player, health);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onRegain(@NotNull EntityRegainHealthEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        final Player player = (Player) e.getEntity();
        final IArena arena = Arena.getArenaByPlayer(player);
        if (arena == null) {
            return;
        }

        int health = (int) Math.ceil(player.getHealth() + e.getAmount());
        SidebarService.getInstance().refreshHealth(arena, player, health);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onReSpawn(@NotNull PlayerReSpawnEvent e) {
        final IArena arena = e.getArena();

        SidebarService.getInstance().refreshHealth(arena, e.getPlayer(), (int) Math.ceil(e.getPlayer().getHealth()));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void reJoin(@NotNull PlayerReJoinEvent e) {
        // re-add player to scoreboard tab list
        SidebarService.getInstance().handleReJoin(e.getArena(), e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void arenaJoin(@NotNull PlayerJoinArenaEvent e) {
        // add player to scoreboard tab list
        SidebarService.getInstance().handleJoin(e.getArena(), e.getPlayer(), e.isSpectator());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void serverJoin(@NotNull PlayerJoinEvent e) {
        if (BedWars.getServerType() == ServerType.MULTIARENA || BedWars.getServerType() == ServerType.SHARED) {
            // add player to scoreboard tab list
            SidebarService.getInstance().applyLobbyTab(e.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void arenaLeave(@NotNull PlayerLeaveArenaEvent e) {
        if (BedWars.getServerType() == ServerType.MULTIARENA || BedWars.getServerType() == ServerType.SHARED) {
            // add player to scoreboard tab list
            SidebarService.getInstance().applyLobbyTab(e.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBedDestroy(@NotNull PlayerBedBreakEvent e) {
        // refresh placeholders in case placeholders refresh is disabled
        SidebarService.getInstance().refreshPlaceholders(e.getArena());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFinalKill(@NotNull PlayerKillEvent e) {
        if (!e.getCause().isFinalKill()) {
            return;
        }
        // refresh placeholders in case placeholders refresh is disabled
        SidebarService.getInstance().refreshPlaceholders(e.getArena());
    }
}
