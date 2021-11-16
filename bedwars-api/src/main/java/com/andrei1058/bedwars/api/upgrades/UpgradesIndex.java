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

package com.andrei1058.bedwars.api.upgrades;

import com.google.common.collect.ImmutableMap;
import org.bukkit.entity.Player;

public interface UpgradesIndex {

    /**
     * Get menu name.
     */
    String getName();

    /**
     * Open this menu to a player.
     * Make sure to use {@link com.andrei1058.bedwars.api.BedWars.TeamUpgradesUtil#setWatchingGUI(Player)}
     *
     * @param player target player.
     */
    void open(Player player);

    /**
     * Add content to a menu.
     *
     * @param content content instance.
     * @param slot    where to put the content in the menu.
     * @return false if te given slot is in use.
     */
    boolean addContent(MenuContent content, int slot);

    /**
     *
     * @return total amount of tiers in upgrades
     */
    int countTiers();

    ImmutableMap<Integer, MenuContent> getMenuContentBySlot();
}
