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

package com.andrei1058.bedwars.levels.internal;

import com.andrei1058.bedwars.api.events.player.PlayerXpGainEvent;
import com.andrei1058.bedwars.api.levels.Level;
import org.bukkit.entity.Player;

public class InternalLevel implements Level {

    private PlayerLevel getLevelOrNull(Player p) {
        return PlayerLevel.getOrNull(p.getUniqueId());
    }

    @Override
    public String getLevel(Player p) {
        PlayerLevel level = getLevelOrNull(p);
        return null == level ? "1" : level.getLevelName();
    }

    @Override
    public int getPlayerLevel(Player p) {
        PlayerLevel level = getLevelOrNull(p);
        return null == level ? 1 : level.getPlayerLevel();
    }

    @Override
    public String getRequiredXpFormatted(Player p) {
        PlayerLevel level = getLevelOrNull(p);
        return null == level ? "0" : level.getFormattedRequiredXp();
    }

    @Override
    public String getProgressBar(Player p) {
        PlayerLevel level = getLevelOrNull(p);
        return null == level ? "" : level.getProgress();
    }

    @Override
    public int getCurrentXp(Player p) {
        PlayerLevel level = getLevelOrNull(p);
        return null == level ? 0 : level.getCurrentXp();
    }

    @Override
    public String getCurrentXpFormatted(Player p) {
        PlayerLevel level = getLevelOrNull(p);
        return null == level ? "0" : level.getFormattedCurrentXp();
    }

    @Override
    public int getRequiredXp(Player p) {
        PlayerLevel level = getLevelOrNull(p);
        return null == level ? 0 : level.getNextLevelCost();
    }

    @Override
    public void addXp(Player player, int xp, PlayerXpGainEvent.XpSource source) {
        PlayerLevel level = getLevelOrNull(player);
        if (null != level) {
            level.addXp(xp, source);
        }
    }

    @Override
    public void setXp(Player player, int currentXp) {
        PlayerLevel level = getLevelOrNull(player);
        if (null != level) {
            level.setXp(currentXp);
        }
    }

    @Override
    public void setLevel(Player player, int level) {
        PlayerLevel playerLevel = getLevelOrNull(player);
        if (null != playerLevel) {
            playerLevel.setLevel(level);
        }
    }
}
