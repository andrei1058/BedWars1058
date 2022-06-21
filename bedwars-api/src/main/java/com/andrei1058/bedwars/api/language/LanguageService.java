/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2022 Andrei Dascălu
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

package com.andrei1058.bedwars.api.language;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface LanguageService {

    Collection<LanguageOld> getRegisteredLanguages();

    /**
     * Save a new message in all registered languages if it does not exist.
     */
    void saveIfNotExists(String path, Object data);

    /**
     * Check if a language is active on the server.
     *
     * @param iso - language code.
     * @return true if language is registered.
     */
    boolean isLanguageExist(String iso);

    /**
     * Get language by iso code.
     *
     * @param iso - given code.
     * @return null if not found.
     */
    @Nullable
    LanguageOld getLang(String iso);

    /**
     * Get language by iso code or server default.
     *
     * @param iso - given code.
     * @return server default if not found.
     */
    LanguageOld getLangOrDefault(String iso);

    /**
     * Change a player language and refresh
     * scoreboard and custom join items.
     * Changes will not apply if given iso is not a registered language.
     */
    boolean setPlayerLanguage(UUID uuid, String iso);

    /**
     * Register a new language.
     *
     * @param lang - given language.
     */
    boolean register(LanguageOld lang);

    /**
     * Save messages for stats gui items if custom items added, for each language
     */
    void setupCustomStatsMessages();

    /**
     * Get sidebar strings at given path or alternative path.
     * @param p receiver.
     * @param path strings path.
     * @param alternative alternative path.
     * @return strings.
     */
    List<String> getScoreboard(Player p, String path, String alternative);

    /**
     * Retrieve what language is a player using.
     * @param p target.
     * @return player language.
     */
    LanguageOld getPlayerLanguage(Player p);

    /**
     * Retrieve what language is a player using.
     * @param p target.
     * @return player language.
     */
    LanguageOld getPlayerLanguage(UUID p);

    /**
     * Get a string list in player's language.
     */
    List<String> getList(Player p, String path);

    /**
     * Get message in player's language.
     */
    String getMsg(Player p, String path);

    /**
     * Trigger language preference saving.
     * @param player leaving player.
     */
    void onPlayerLeave(Player player);

    /**
     * Get server default language.
     * @return server default language.
     */
    LanguageOld getDefaultLanguage();

    /**
     * Set server default language.
     * It must be a registered language.
     * @param defaultLanguage new server default.
     */
    void setDefaultLanguage(LanguageOld defaultLanguage);

    /**
     * Unregister language.
     * @param language language to be unregistered.
     */
    void unregister(LanguageOld language);
}
