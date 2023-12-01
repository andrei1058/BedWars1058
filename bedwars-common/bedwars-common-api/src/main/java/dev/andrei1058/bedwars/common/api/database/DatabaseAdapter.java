/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2023 Andrei Dascălu
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
 */

package dev.andrei1058.bedwars.common.api.database;

import com.google.gson.JsonArray;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface DatabaseAdapter {

    /**
     * Called when this adapter needs to be disabled because the server is stopping
     * or because it is being replaced with a new adapter.
     */
    void disable();

    /**
     * Create stats table if not exists.
     */
    void initStatsTable();

    /**
     * Create player language preference table if not exists.
     */
    void initLocaleTable();

    /**
     * Create private games table.
     */
    void initPrivateGamesTable();

    /**
     * Get player's stats.
     * @param player target user.
     * @return stats object.
     */
    @Nullable
    PlayerStats getPlayerStats(UUID player);

    /**
     * Save player stats to your data source.
     * @param playerStats cached stats from the server.
     */
    void savePlayerStats(PlayerStats playerStats);

    /**
     * Retrieve player language.
     * @param player given user.
     * @return iso code.
     */
    @Nullable
    String getPlayerLocale(UUID player);

    /**
     * Save language selection to db.
     * @param player target.
     * @param iso language code.
     */
    void savePlayerLocale(UUID player, @Nullable String iso);

    /**
     * Check if the given player has an active private games session.
     * Works in combination with parties.
     */
    boolean hasActivePrivateGames(UUID player);

    /**
     * Toggle private games for a player.
     */
    void setActivePrivateGames(UUID player, boolean toggle);

    /**
     * Get private games settings for the given player.
     * Newly added settings will be added to the list in the manager.
     * <p>
     * Can be null. List (that can be empty) if active;
     */
    @Nullable JsonArray getPrivateGamesSettings(UUID player) throws IllegalAccessException;

    /**
     * Save replace current settings to db.
     */
    void setPrivateGamesSettings(UUID player, JsonArray settingList);
}
