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

package com.andrei1058.bedwars.listeners.joinhandler;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import static com.andrei1058.bedwars.BedWars.plugin;

public class JoinHandlerCommon implements Listener {

    // Used to show some details to andrei1058
    // No sensitive data
    protected static void displayCustomerDetails(Player player) {
        if (player == null) return;
        //TODO IMPROVE, ADD MORE DETAILS
        if (player.getName().equalsIgnoreCase("andrei1058") || player.getName().equalsIgnoreCase("andreea1058") || player.getName().equalsIgnoreCase("Dani3l_FTW")) {
            player.sendMessage("§8[§f" + BedWars.plugin.getName() + " v" + BedWars.plugin.getDescription().getVersion() + "§8]§7§m---------------------------");
            player.sendMessage("");
            player.sendMessage("§7User ID: §f%%__USER__%%");
            player.sendMessage("§7Download ID: §f%%__NONCE__%%");
            player.sendMessage("");
            player.sendMessage("§8[§f" + BedWars.plugin.getName() + "§8]§7§m---------------------------");
        }
    }

    @EventHandler
    public void requestLanguage(AsyncPlayerPreLoginEvent e) {
        String iso = BedWars.getRemoteDatabase().getLanguage(e.getUniqueId());
        Bukkit.getScheduler().runTask(plugin, () -> Language.setPlayerLanguage(e.getUniqueId(), iso));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void removeLanguage(PlayerLoginEvent e) {
        if (e.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            Language.setPlayerLanguage(e.getPlayer().getUniqueId(), null);
        }
    }
}
