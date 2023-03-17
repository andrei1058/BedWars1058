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

package com.andrei1058.bedwars.database;

import com.andrei1058.bedwars.shop.quickbuy.QuickBuyElement;
import com.andrei1058.bedwars.stats.PlayerStats;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface Database {

    /**
     * Initialize database.
     */
    void init();

    /**
     * Check if player has remote stats.
     */
    boolean hasStats(UUID uuid);

    /**
     * Create or replace stats for a player.
     */
    void saveStats(PlayerStats stats);

    PlayerStats fetchStats(UUID uuid);

    /**
     * Set quick buy slot value.
     */
    @Deprecated
    @SuppressWarnings("unused")
    void setQuickBuySlot(UUID uuid, String shopPath, int slot);

    /**
     * Get quick buy slot value.
     */
    String getQuickBuySlots(UUID uuid, int slot);

    /**
     * Get quick buy.
     * slot - identifier string
     */
    HashMap<Integer, String> getQuickBuySlots(UUID uuid, int[] slot);

    /**
     * Check if has quick buy.
     */
    boolean hasQuickBuy(UUID player);

    /**
     * Get a stats value for the given player.
     */
    @SuppressWarnings("unused")
    int getColumn(UUID player, String column);

    /**
     * Get a player level and xp.
     * <p>
     * args 0 is level.
     * args 1 is xp.
     * args 2 is display name.
     * args 3 next level cost.
     */
    Object[] getLevelData(UUID player);

    /**
     * Set a player level data.
     */
    void setLevelData(UUID player, int level, int xp, String displayName, int nextCost);

    /**
     * Set a player language.
     */
    void setLanguage(UUID player, String iso);

    /**
     * Get a player language.
     */
    String getLanguage(UUID player);

    /**
     * @param updateSlots key is slot id and value is the element.
     */
    void pushQuickBuyChanges(HashMap<Integer, String> updateSlots, UUID uuid, List<QuickBuyElement> elementList);
}
