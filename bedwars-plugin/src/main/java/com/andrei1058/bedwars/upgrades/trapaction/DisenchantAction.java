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

package com.andrei1058.bedwars.upgrades.trapaction;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.upgrades.TrapAction;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DisenchantAction implements TrapAction {

    private Enchantment enchantment;
    private ApplyType type;

    public DisenchantAction(Enchantment enchantment, DisenchantAction.ApplyType type){
        this.enchantment = enchantment;
        this.type = type;
    }

    @Override
    public String getName() {
        return "disenchant-item";
    }

    @Override
    public void onTrigger(@NotNull Player player, ITeam playerTeam, ITeam targetTeam) {
        if (type == ApplyType.SWORD){
            for (ItemStack i : player.getInventory()){
                if (BedWars.nms.isSword(i)){
                    i.removeEnchantment(enchantment);
                }
                player.updateInventory();
            }
        } else if (type == ApplyType.ARMOR){
            for (ItemStack i : player.getInventory()){
                if (BedWars.nms.isArmor(i)){
                    i.removeEnchantment(enchantment);
                }
                player.updateInventory();
            }
            for (ItemStack i : player.getInventory().getArmorContents()){
                if (BedWars.nms.isArmor(i)){
                    i.removeEnchantment(enchantment);
                }
                player.updateInventory();
            }
        } else if (type == ApplyType.BOW){
            for (ItemStack i : player.getInventory()){
                if (BedWars.nms.isBow(i)){
                    i.removeEnchantment(enchantment);
                }
                player.updateInventory();
            }
        }
    }

    public enum ApplyType {
        SWORD,
        ARMOR,
        BOW,
    }
}
