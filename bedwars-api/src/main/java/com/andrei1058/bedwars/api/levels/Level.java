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

package com.andrei1058.bedwars.api.levels;

import com.andrei1058.bedwars.api.events.player.PlayerXpGainEvent;
import org.bukkit.entity.Player;

public interface Level {

    /**
     * @return current player level formatted as string.
     */
    String getLevel(Player p);


    /**
     * @return current player level as number.
     */
    int getPlayerLevel(Player p);

    /**
     * Get required xp as string.
     * 2000 - 2k
     *
     * @return required xp for next level.
     */
    String getRequiredXpFormatted(Player p);

    /**
     * @return current progress bar.
     */
    String getProgressBar(Player p);

    /**
     * @return current xp.
     */
    int getCurrentXp(Player p);

    /**
     * @return current xp formatted.
     */
    String getCurrentXpFormatted(Player p);

    /**
     * @return required xp
     */
    int getRequiredXp(Player p);

    /**
     * Add some xp to target player.
     */
    void addXp(Player player, int xp, PlayerXpGainEvent.XpSource source);

    /**
     * Set player xp.
     */
    void setXp(Player player, int currentXp);

    /**
     * Set player level.
     */
    void setLevel(Player player, int level);
}
