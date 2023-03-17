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
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.upgrades.UpgradeBuyEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.upgrades.MenuContent;
import com.andrei1058.bedwars.api.upgrades.TeamUpgrade;
import com.andrei1058.bedwars.api.upgrades.UpgradeAction;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.configuration.Sounds;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class MenuUpgrade implements MenuContent, TeamUpgrade {

    private String name;
    private List<UpgradeTier> tiers = new LinkedList<>();

    /**
     * Create a new upgrade element.
     *
     * @param name identifier.
     */
    public MenuUpgrade(String name) {
        this.name = name;
    }

    @Override
    public ItemStack getDisplayItem(Player player, ITeam team) {
        if (tiers.isEmpty()) return new ItemStack(Material.BEDROCK);

        int tier = -1;
        if (team.getTeamUpgradeTiers().containsKey(getName())) {
            tier = team.getTeamUpgradeTiers().get(getName());
        }

        boolean highest = getTiers().size() == tier + 1 && team.getTeamUpgradeTiers().containsKey(getName());
        if (!highest) tier += 1;
        UpgradeTier ut = getTiers().get(tier);
        boolean afford = UpgradesManager.getMoney(player, ut.getCurrency()) >= ut.getCost();

        ItemStack i = new ItemStack(tiers.get(tier).getDisplayItem());
        ItemMeta im = i.getItemMeta();
        if (im == null) return i;
        String color;
        if (!highest){
            if (afford){
                color = Language.getMsg(player, Messages.FORMAT_UPGRADE_COLOR_CAN_AFFORD);
            } else {
                color = Language.getMsg(player, Messages.FORMAT_UPGRADE_COLOR_CANT_AFFORD);
            }
        } else {
            color = Language.getMsg(player, Messages.FORMAT_UPGRADE_COLOR_UNLOCKED);
        }

        im.setDisplayName(Language.getMsg(player, Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", this.getName().replace("upgrade-", "")).replace("{tier}", ut.getName())).replace("{color}", color));

        List<String> lore = new ArrayList<>();
        String currencyMsg = UpgradesManager.getCurrencyMsg(player, ut);
        for (String s : Language.getList(player, Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", this.getName().replace("upgrade-", "")))){
            if (s.contains("{tier_")){
                // Get tier number from placeholder
                String result = s.replaceAll(".*_([0-9]+)_.*", "$1");

                String tierColor = Messages.FORMAT_UPGRADE_TIER_LOCKED;
                if (Integer.valueOf(result)-1 <= team.getTeamUpgradeTiers().getOrDefault(getName(), -1)) {
                    tierColor = Messages.FORMAT_UPGRADE_TIER_UNLOCKED;
                }

                //get current tier. Note: placeholder number doesnt match array index.
                UpgradeTier upgradeTier = tiers.get(Integer.valueOf(result)-1);

                lore.add(s.replace("{tier_" + result + "_cost}", String.valueOf(upgradeTier.getCost()))
                        .replace("{tier_" + result + "_currency}", currencyMsg)
                        .replace("{tier_" + result + "_color}", Language.getMsg(player, tierColor)));

            } else {
                lore.add(s.replace("{color}", color));
            }
        }
        if (highest){
            lore.add(Language.getMsg(player, Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED).replace("{color}", color));
        } else if (afford){
            lore.add(Language.getMsg(player, Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY).replace("{color}", color));
        } else {
            lore.add(Language.getMsg(player, Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY).replace("{currency}", currencyMsg).replace("{color}", color));
        }
        im.setLore(lore);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        i.setItemMeta(im);
        return i;
    }

    @Override
    public void onClick(Player player, ClickType clickType, ITeam team) {
        int tier = -1;
        if (team.getTeamUpgradeTiers().containsKey(getName())) {
            tier = team.getTeamUpgradeTiers().get(getName());
        }
        UpgradeTier ut;
        if (getTiers().size() - 1 > tier) {
            ut = getTiers().get(tier + 1);

            int money = UpgradesManager.getMoney(player, ut.getCurrency());
            if (money < ut.getCost()) {
                Sounds.playSound(ConfigPath.SOUNDS_INSUFF_MONEY, player);
                player.sendMessage(Language.getMsg(player, Messages.SHOP_INSUFFICIENT_MONEY)
                        .replace("{currency}", UpgradesManager.getCurrencyMsg(player, ut))
                        .replace("{amount}", String.valueOf(ut.getCost() - money)));
                player.closeInventory();
                return;
            }

            final UpgradeBuyEvent event;
            Bukkit.getPluginManager().callEvent(event = new UpgradeBuyEvent(this, player, team));
            if(event.isCancelled()) return;
            
            if (ut.getCurrency() == Material.AIR) {
                BedWars.getEconomy().buyAction(player, ut.getCost());
            } else {
                BedWars.getAPI().getShopUtil().takeMoney(player, ut.getCurrency(), ut.getCost());
            }

            if (team.getTeamUpgradeTiers().containsKey(getName())) {
                team.getTeamUpgradeTiers().replace(getName(), team.getTeamUpgradeTiers().get(getName()) + 1);
            } else {
                team.getTeamUpgradeTiers().put(getName(), 0);
            }
            Sounds.playSound(ConfigPath.SOUNDS_BOUGHT, player);
            for (UpgradeAction a : ut.getUpgradeActions()) {
                a.onBuy(player, team);
            }

            for (Player p1 : team.getMembers()) {
                p1.sendMessage(Language.getMsg(p1, Messages.UPGRADES_UPGRADE_BOUGHT_CHAT).replace("{playername}", player.getName()).replace("{player}", player.getDisplayName()).replace("{upgradeName}",
                        ChatColor.stripColor(Language.getMsg(p1, Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", getName()
                                .replace("upgrade-", "")).replace("{tier}", ut.getName())))).replace("{color}", ""));
            }

            ImmutableMap<Integer, MenuContent> menuContentBySlot = UpgradesManager.getMenuForArena(Arena.getArenaByPlayer(player)).getMenuContentBySlot();
            Inventory inv = player.getOpenInventory().getTopInventory();
            for (Map.Entry<Integer, MenuContent> entry : menuContentBySlot.entrySet()) {
                inv.setItem(entry.getKey(), entry.getValue().getDisplayItem(player, team));
            }

        }
    }

    /**
     * Load a upgrade element tiers.
     *
     * @param upgradeTier tier.
     * @return false if something went wrong.
     */
    public boolean addTier(UpgradeTier upgradeTier) {
        for (UpgradeTier ut : tiers) {
            if (ut.getName().equalsIgnoreCase(upgradeTier.getName())) return false;
        }
        tiers.add(upgradeTier);
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getTierCount() {
        return tiers.size();
    }

    /**
     * @return tiers list.
     */
    public List<UpgradeTier> getTiers() {
        return Collections.unmodifiableList(tiers);
    }
}
