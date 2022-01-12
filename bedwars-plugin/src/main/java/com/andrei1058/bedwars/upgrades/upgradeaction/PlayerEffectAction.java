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

package com.andrei1058.bedwars.upgrades.upgradeaction;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.upgrades.UpgradeAction;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class PlayerEffectAction implements UpgradeAction {

    private final PotionEffectType potionEffectType;
    private final int amplifier;
    private int duration;
    private final ApplyType type;

    public PlayerEffectAction(PotionEffectType potionEffectType, int amplifier, int duration, ApplyType type){
        this.potionEffectType = potionEffectType;
        this.amplifier = amplifier;
        this.type = type;
        this.duration = duration;
        if (duration < 0 ) this.duration *= -1;
        /*if (type == ApplyType.ENEMY_BASE_ENTER && duration <= 0){
            this.duration = 20;
        }*/
        if (duration == 0){
            this.duration = Integer.MAX_VALUE;
        } else {
            this.duration *= 20;
        }
    }


    @Override
    public void onBuy(Player player, ITeam bwt) {
        if (type == ApplyType.BASE){
            bwt.addBaseEffect(potionEffectType, amplifier, duration);
        } else if (type == ApplyType.TEAM){
            bwt.addTeamEffect(potionEffectType, amplifier, duration);
        }/* else if (type == ApplyType.ENEMY_BASE_ENTER){
            bwt.addEnemyBaseEnterEffect(potionEffectType, amplifier, duration);
        }*/
    }

    public enum ApplyType {
        TEAM, BASE
    }
}
