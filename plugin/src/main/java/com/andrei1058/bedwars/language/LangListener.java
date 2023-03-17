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

package com.andrei1058.bedwars.language;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.events.player.PlayerLangChangeEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.sidebar.SidebarService;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class LangListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLanguageChangeEvent(PlayerLangChangeEvent e) {
        if (e == null) return;
        if (e.isCancelled()) return;
        if (BedWars.config.getLobbyWorldName().equalsIgnoreCase(e.getPlayer().getWorld().getName())) {
            Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
                Arena.sendLobbyCommandItems(e.getPlayer());
                SidebarService.getInstance().giveSidebar(e.getPlayer(), Arena.getArenaByPlayer(e.getPlayer()), false);

                // save to db
                Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, ()-> BedWars.getRemoteDatabase().setLanguage(e.getPlayer().getUniqueId(), e.getNewLang()));
            }, 10L);
        }
    }
}
