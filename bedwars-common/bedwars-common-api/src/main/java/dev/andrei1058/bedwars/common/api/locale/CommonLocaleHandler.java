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


package dev.andrei1058.bedwars.common.api.locale;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

public interface CommonLocaleHandler {
    /**
     * Get enabled languages by iso.
     */
    List<CommonLocale> getEnabledCommonLocales();

    /**
     * Get a translated message.
     *
     * @param path   message path.
     * @param player player language.
     */
    String getMsg(Player player, CommonMessage path);

    /**
     * Get a translated message.
     *
     * @param path   message path.
     * @param sender sender language.
     */
    String getMsg(CommandSender sender, CommonMessage path);

    /**
     * Get player's language.
     *
     * @param player target player.
     */
    CommonLocale getLocale(Player player);

    /**
     * Get sender's language.
     *
     * @param sender target.
     */
    CommonLocale getLocale(CommandSender sender);

    /**
     * Get a player language.
     *
     * @param player target player.
     * @return player language of if he does not have any, return default language.
     */
    @NotNull
    CommonLocale getLocale(UUID player);

    /**
     * Get server default language.
     */
    CommonLocale getDefaultLocale();

    /**
     * Get language by iso code.
     */
    CommonLocale getLocale(String langIso);

    /**
     * Get language folder.
     * This is where language files are retrieved from.
     */
    File getLocalesFolder();

    /**
     * Add a new language to the enabled languages list.
     *
     * @param translation language to be registered.
     * @return true if successfully added.
     * Will return false if could not validate messages paths.
     * It may return false if adding this language or languages in general is not allowed.
     * Will return false if language is already loaded for example.
     * Will return false if language is disabled from config.
     */
    @SuppressWarnings({"unused", "UnusedReturnValue"})
    boolean addLocale(CommonLocale translation);

    /**
     * Remove a language from the languages list.
     *
     * @param translation language to be removed.
     * @return true if removed successfully.
     * Will return false if given language is the default language.
     */
    @SuppressWarnings({"unused", "UnusedReturnValue"})
    boolean removeLocale(CommonLocale translation);

    /**
     * Change a player language.
     * It is not recommended to do it during the game.
     * The player must be online. If you want to "pre-load" player languages use
     * your own system and add the player here only when he is online.
     * WILL SAVE THE LANGUAGE TO THE DATABASE AS WELL (ASYNC).
     *
     * @param uuid         player uuid. He must be online.
     * @param translation  language. Null to restore to default server language. If the language is not registered will return false.
     * @param triggerEvent true if you want to trigger language change event. Usually false at join.
     * @return true if switched successfully. False if language is not registered,
     * if uuid is not on the server, if switch not allowed at this moment or other.
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean setPlayerLocale(UUID uuid, @Nullable CommonLocale translation, boolean triggerEvent);

    /**
     * Set server default language.
     *
     * @param translation language.
     * @return true if set successfully.
     * Will return false if the language is not registered,
     * if cannot be validated or other.
     */
    @SuppressWarnings("unused")
    boolean setDefaultLocale(CommonLocale translation);

    /**
     * Check if a language exists (is enabled and loaded) by its iso code.
     *
     * @param isoCode iso code to be checked.
     * @return true if translation exists.
     */
    @SuppressWarnings("unused")
    boolean isLocaleExist(@Nullable String isoCode);

    /**
     * Format a date using player's time-zone.
     *
     * @param player player used to retrieve time-zone.
     * @param date   date to be formatted.
     */
    @SuppressWarnings("unused")
    String formatDate(Player player, Date date);

    /**
     * Clear cached locale on player leave.
     */
    void clearOnLeave(Player player);
}
