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

package com.andrei1058.bedwars.shop.main;

import com.andrei1058.bedwars.api.language.Language;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QuickBuyButton {

    private int slot;
    private ItemStack itemStack;
    private String namePath, lorePath;

    /**
     * Create a new quick buy button
     *
     * @param namePath  Language name path
     * @param lorePath  Language lore path.
     * @param slot      Item slot in inventory
     * @param itemStack Button ItemStack preview
     */
    public QuickBuyButton(int slot, ItemStack itemStack, String namePath, String lorePath) {
        this.slot = slot;
        this.itemStack = itemStack;
        this.namePath = namePath;
        this.lorePath = lorePath;
    }

    /**
     * Get the quick buy button in the player's language
     */
    public ItemStack getItemStack(Player player) {
        ItemStack i = itemStack.clone();
        ItemMeta im = i.getItemMeta();
        if (im != null) {
            im.setDisplayName(Language.getMsg(player, namePath));
            im.setLore(Language.getList(player, lorePath));
            i.setItemMeta(im);
        }
        return i;
    }

    /**
     * Get quick buy item slot
     */
    public int getSlot() {
        return slot;
    }
}
