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

package com.andrei1058.bedwars.upgrades.menu;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.upgrades.EnemyBaseEnterTrap;
import com.andrei1058.bedwars.api.upgrades.MenuContent;
import com.andrei1058.bedwars.api.upgrades.TeamUpgrade;
import com.andrei1058.bedwars.api.upgrades.UpgradesIndex;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class InternalMenu implements UpgradesIndex {

    private String name;
    private HashMap<Integer, MenuContent> menuContentBySlot = new HashMap<>();

    /**
     * Create an upgrade menu for an arena group.
     *
     * @param groupName arena group name.
     */
    public InternalMenu(String groupName) {
        this.name = groupName.toLowerCase();
        Language.saveIfNotExists(Messages.UPGRADES_MENU_GUI_NAME_PATH + groupName.toLowerCase(), "&8Upgrades & Traps");
    }

    @Override
    public void open(Player player) {
        IArena a = Arena.getArenaByPlayer(player);
        if (a == null) return;
        if (!a.isPlayer(player)) return;
        ITeam team = a.getTeam(player);
        if (team == null) return;
        if (!BedWars.getAPI().getArenaUtil().isPlaying(player)) return;
        Inventory inv = Bukkit.createInventory(null, 45, Language.getMsg(player, Messages.UPGRADES_MENU_GUI_NAME_PATH + name));
        for (Map.Entry<Integer, MenuContent> entry : menuContentBySlot.entrySet()) {
            inv.setItem(entry.getKey(), entry.getValue().getDisplayItem(player, team));
        }
        player.openInventory(inv);
        UpgradesManager.setWatchingUpgrades(player.getUniqueId());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean addContent(MenuContent content, int slot) {
        if (menuContentBySlot.get(slot) != null) return false;
        menuContentBySlot.put(slot, content);
        return true;
    }

    public int countTiers() {
        int count = 0;
        for (MenuContent content : menuContentBySlot.values()){
            if (content instanceof TeamUpgrade && !(content instanceof EnemyBaseEnterTrap)){
                TeamUpgrade tu = (TeamUpgrade) content;
                count+=tu.getTierCount();
            }
        }
        return count;
    }

    @Override
    public ImmutableMap<Integer, MenuContent> getMenuContentBySlot() {
        return ImmutableMap.copyOf(menuContentBySlot);
    }
}
