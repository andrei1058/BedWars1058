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
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.upgrades.UpgradeAction;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import com.andrei1058.bedwars.upgrades.upgradeaction.*;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpgradeTier {

    private ItemStack displayItem;
    private String name;
    private List<UpgradeAction> upgradeActions = new ArrayList<>();
    private int cost;
    private Material currency;

    /**
     * @param parentName is the parent name.
     * @param name       is the upgrade identifier.
     */
    public UpgradeTier(String parentName, String name, ItemStack displayItem, int cost, Material currency) {
        this.displayItem = BedWars.nms.addCustomData(displayItem, "MCONT_" + parentName);
        this.name = name;
        Language.saveIfNotExists(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", parentName.replace("upgrade-", "")).replace("{tier}", name), "&cName not set");
        Language.saveIfNotExists(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", parentName.replace("upgrade-", "")).replace("{tier}", name), Collections.singletonList("&cLore not set"));
        this.cost = cost;
        this.currency = currency;

        for (String action : UpgradesManager.getConfiguration().getYml().getStringList(parentName + "." + name + ".receive")) {
            String[] type = action.trim().split(":");
            if (type.length < 2) continue;
            String[] data = type[1].trim().toLowerCase().split(",");
            UpgradeAction ua = null;

            switch (type[0].trim().toLowerCase()) {
                case "enchant-item":
                    if (data.length < 3) {
                        BedWars.plugin.getLogger().warning("Invalid " + type[0] + " at upgrades2: " + parentName + "." + name);
                        continue;
                    }
                    Enchantment e = Enchantment.getByName(data[0].toUpperCase());
                    if (e == null) {
                        BedWars.plugin.getLogger().warning("Invalid enchantment " + data[0].toUpperCase() + " at upgrades2: " + parentName + "." + name);
                        continue;
                    }
                    EnchantItemAction.ApplyType apply = null;
                    switch (data[2].toLowerCase()) {
                        case "sword":
                            apply = EnchantItemAction.ApplyType.SWORD;
                            break;
                        case "armor":
                            apply = EnchantItemAction.ApplyType.ARMOR;
                            break;
                        case "bow":
                            apply = EnchantItemAction.ApplyType.BOW;
                            break;
                    }
                    if (apply == null) {
                        BedWars.plugin.getLogger().warning("Invalid apply type " + data[2] + " at upgrades2: " + parentName + "." + name);
                        continue;
                    }
                    int amplifier = 1;
                    try {
                        amplifier = Integer.parseInt(data[1]);
                    } catch (Exception ignored) {
                    }
                    ua = new EnchantItemAction(e, amplifier, apply);
                    break;
                case "player-effect":
                    if (data.length < 4) {
                        BedWars.plugin.getLogger().warning("Invalid " + type[0] + " at upgrades2: " + parentName + "." + name);
                        continue;
                    }
                    PotionEffectType pe = PotionEffectType.getByName(data[0].toUpperCase());
                    if (pe == null) {
                        BedWars.plugin.getLogger().warning("Invalid potion effect " + data[0] + " at upgrades2: " + parentName + "." + name);
                        continue;
                    }
                    PlayerEffectAction.ApplyType applyType = null;
                    switch (data[3].toLowerCase()) {
                        case "team":
                            applyType = PlayerEffectAction.ApplyType.TEAM;
                            break;
                        case "base":
                            applyType = PlayerEffectAction.ApplyType.BASE;
                            break;
                    }
                    if (applyType == null) {
                        BedWars.plugin.getLogger().warning("Invalid apply type " + data[3] + " at upgrades2: " + parentName + "." + name);
                        continue;
                    }
                    int amp = 1, time = 0;
                    try {
                        amp = Integer.parseInt(data[1]);
                        time = Integer.parseInt(data[2]);
                    } catch (Exception ignored) {
                    }
                    ua = new PlayerEffectAction(pe, amp, time, applyType);
                    break;
                case "generator-edit":
                    if (data.length < 4) {
                        BedWars.plugin.getLogger().warning("Invalid " + type[0] + " at upgrades2: " + parentName + "." + name);
                        continue;
                    }
                    GeneratorEditAction.ApplyType genType = null;
                    switch (data[0].toLowerCase()) {
                        case "gold":
                        case "g":
                            genType = GeneratorEditAction.ApplyType.GOLD;
                            break;
                        case "iron":
                        case "i":
                            genType = GeneratorEditAction.ApplyType.IRON;
                            break;
                        case "emerald":
                        case "e":
                            genType = GeneratorEditAction.ApplyType.EMERALD;
                            break;
                    }
                    if (genType == null) {
                        BedWars.plugin.getLogger().warning("Invalid generator type " + data[0] + " at upgrades2: " + parentName + "." + name);
                    }
                    int spawn, amount, limit;
                    try {
                        spawn = Integer.parseInt(data[1]);
                        amount = Integer.parseInt(data[2]);
                        limit = Integer.parseInt(data[3]);
                    } catch (Exception ex) {
                        BedWars.plugin.getLogger().warning("Invalid generator configuration " + data[0] + " at upgrades2: " + parentName + "." + name);
                        continue;
                    }
                    ua = new GeneratorEditAction(genType, amount, spawn, limit);
                    break;
                case "dragon":
                    if (data.length < 1) {
                        BedWars.plugin.getLogger().warning("Invalid " + type[0] + " at upgrades2: " + parentName + "." + name);
                        continue;
                    }
                    int dragons;
                    try {
                        dragons = Integer.parseInt(data[0]);
                    } catch (Exception exc) {
                        BedWars.plugin.getLogger().warning("Invalid dragon amount at upgrades2: " + parentName + "." + name);
                        continue;
                    }
                    ua = new DragonAction(dragons);
                    break;
                case "command":
                    // once-as-console,command
                    if (data.length < 2) {
                        BedWars.plugin.getLogger().warning("Invalid " + type[0] + " at upgrades2: " + parentName + "." + name);
                        continue;
                    }
                    DispatchCommand.CommandType cmdType;
                    try {
                        cmdType = DispatchCommand.CommandType.valueOf(data[0].toUpperCase());
                    } catch (Exception exception) {
                        BedWars.plugin.getLogger().warning("Invalid command type " + data[0] + " at upgrades2: " + parentName + "." + name);
                        continue;
                    }
                    // re-do here because the first one does a trim on data
                    // we need data with spaces
                    data = type[1].split(",");
                    ua = new DispatchCommand(cmdType, data[1]);
                    break;
            }
            if (ua != null) upgradeActions.add(ua);
        }
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public Material getCurrency() {
        return currency;
    }

    public List<UpgradeAction> getUpgradeActions() {
        return upgradeActions;
    }
}
