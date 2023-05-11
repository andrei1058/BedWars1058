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

package com.tomkeuper.bedwars.listeners.arenaselector;

import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.language.Language;
import com.tomkeuper.bedwars.api.language.Messages;
import com.tomkeuper.bedwars.arena.Arena;
import com.tomkeuper.bedwars.arena.ArenaGUI;
import com.tomkeuper.bedwars.configuration.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ArenaSelectorListener implements Listener {

    public static final String ARENA_SELECTOR_GUI_IDENTIFIER = "arena=";

    @EventHandler
    public void onArenaSelectorClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!(player.getOpenInventory().getTopInventory().getHolder() instanceof ArenaGUI.ArenaSelectorHolder)) return;

        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (item.getType() == Material.AIR) return;
        if (!BedWars.nms.isCustomBedWarsItem(item)) return;

        String data = BedWars.nms.getCustomData(item);
        if (data.startsWith("RUNCOMMAND")) {
            Bukkit.dispatchCommand(player, data.split("_")[1]);
        }

        if (!data.contains(ARENA_SELECTOR_GUI_IDENTIFIER)) return;

        String arenaName = data.split("=")[1];
        IArena arena = Arena.getArenaByName(arenaName);
        if (arena == null) return;

        if (event.getClick() == ClickType.LEFT) {
            if ((arena.getStatus() == GameState.waiting || arena.getStatus() == GameState.starting) && arena.addPlayer(player, false)) {
                Sounds.playSound("join-allowed", player);
            } else {
                Sounds.playSound("join-denied", player);
                player.sendMessage(Language.getMsg(player, Messages.ARENA_JOIN_DENIED_SELECTOR));
            }
        } else if (event.getClick() == ClickType.RIGHT) {
            if (arena.getStatus() == GameState.playing && arena.addSpectator(player, false, null)) {
                Sounds.playSound("spectate-allowed", player);
            } else {
                player.sendMessage(Language.getMsg(player, Messages.ARENA_SPECTATE_DENIED_SELECTOR));
                Sounds.playSound("spectate-denied", player);
            }
        }

        player.closeInventory();
    }
}
