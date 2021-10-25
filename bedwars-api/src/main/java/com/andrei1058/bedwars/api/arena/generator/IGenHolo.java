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

package com.andrei1058.bedwars.api.arena.generator;

import org.bukkit.entity.Player;

public interface IGenHolo {

    /**
     * Set timer hologram display text.
     */
    void setTimerName(String timer);

    /**
     * Set tier hologram display text.
     */
    void setTierName(String tier);

    /**
     * Get language iso associated with this hologram.
     */
    String getIso();

    /**
     * Hide hologram for target player if is using a different language.
     * Add your generator to an arena and it will automatically call this when required.
     *
     * @param p    The player who should not see this hologram.
     * @param lang Player's language.
     */
    void updateForPlayer(Player p, String lang);

    /**
     * Hide hologram for all players using a different language than this hologram.
     */
    void updateForAll();

    /**
     * This must be called when disabling the generator {@link IGenerator#disable()}
     */
    void destroy();
}
