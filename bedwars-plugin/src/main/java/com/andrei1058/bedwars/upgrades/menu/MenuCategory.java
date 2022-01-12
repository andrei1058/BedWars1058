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

package com.andrei1058.bedwars.upgrades.menu;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.upgrades.MenuContent;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuCategory implements MenuContent {

    private ItemStack displayItem;
    private String name;

    private HashMap<Integer, MenuContent> menuContentBySlot = new HashMap<>();

    public MenuCategory(String name, ItemStack displayItem) {
        this.name = name;
        this.displayItem = BedWars.nms.addCustomData(displayItem, "MCONT_" + name);
        Language.saveIfNotExists(Messages.UPGRADES_CATEGORY_GUI_NAME_PATH + name.replace("category-", ""), "&8" + name);
        Language.saveIfNotExists(Messages.UPGRADES_CATEGORY_ITEM_NAME_PATH + name.replace("category-", ""), "&cName not set");
        Language.saveIfNotExists(Messages.UPGRADES_CATEGORY_ITEM_LORE_PATH + name.replace("category-", ""), Collections.singletonList("&cLore not set"));
    }

    /**
     * Add content to a menu.
     *
     * @param content content instance.
     * @param slot    where to put the content in the menu.
     * @return false if te given slot is in use.
     */
    public boolean addContent(MenuContent content, int slot) {
        if (menuContentBySlot.get(slot) != null) return false;
        menuContentBySlot.put(slot, content);
        return true;
    }

    @Override
    public ItemStack getDisplayItem(Player player, ITeam team) {
        ItemStack i = new ItemStack(displayItem);
        ItemMeta im = i.getItemMeta();
        if (im != null) {
            im.setDisplayName(Language.getMsg(player, Messages.UPGRADES_CATEGORY_ITEM_NAME_PATH + name.replace("category-", "")));
            List<String> lore = Language.getList(player, Messages.UPGRADES_CATEGORY_ITEM_LORE_PATH + name.replace("category-", ""));

            if (name.equalsIgnoreCase("traps")) {
                int queueLimit = UpgradesManager.getConfiguration().getInt(team.getArena().getGroup().toLowerCase() + "-upgrades-settings.trap-queue-limit");
                if (queueLimit == 0) {
                    queueLimit = UpgradesManager.getConfiguration().getInt("default-upgrades-settings.trap-queue-limit");
                }
                if (queueLimit == team.getActiveTraps().size()) {
                    lore.add("");
                    lore.add(Language.getMsg(player, Messages.UPGRADES_TRAP_QUEUE_LIMIT));
                }
            }
            im.setLore(lore);
            i.setItemMeta(im);
        }
        return i;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void onClick(Player player, ClickType clickType, ITeam team) {
        if (name.equalsIgnoreCase("category-traps")){
            int queueLimit = UpgradesManager.getConfiguration().getInt(team.getArena().getGroup().toLowerCase()+"-upgrades-settings.trap-queue-limit");
            if (queueLimit == 0){
                queueLimit = UpgradesManager.getConfiguration().getInt("default-upgrades-settings.trap-queue-limit");
            }
            if (queueLimit <= team.getActiveTraps().size()){
                player.sendMessage(Language.getMsg(player, Messages.UPGRADES_TRAP_QUEUE_LIMIT));
                return;
            }
        }
        Inventory inv = Bukkit.createInventory(null, 45, Language.getMsg(player, Messages.UPGRADES_CATEGORY_GUI_NAME_PATH + name.replace("category-", "")));
        for (Map.Entry<Integer, MenuContent> entry : menuContentBySlot.entrySet()) {
            inv.setItem(entry.getKey(), entry.getValue().getDisplayItem(player, team));
        }
        player.openInventory(inv);
        UpgradesManager.setWatchingUpgrades(player.getUniqueId());
    }

}
