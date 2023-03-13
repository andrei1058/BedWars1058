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
import com.andrei1058.bedwars.api.upgrades.EnemyBaseEnterTrap;
import com.andrei1058.bedwars.api.upgrades.MenuContent;
import com.andrei1058.bedwars.api.upgrades.TeamUpgrade;
import com.andrei1058.bedwars.api.upgrades.TrapAction;
import com.andrei1058.bedwars.configuration.Sounds;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import com.andrei1058.bedwars.upgrades.trapaction.DisenchantAction;
import com.andrei1058.bedwars.upgrades.trapaction.PlayerEffectAction;
import com.andrei1058.bedwars.upgrades.trapaction.RemoveEffectAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuBaseTrap implements MenuContent, EnemyBaseEnterTrap, TeamUpgrade {

    private ItemStack displayItem;
    private String name;
    private int cost;
    private Material currency;
    private List<TrapAction> trapActions = new ArrayList<>();

    /**
     * @param name        is the trap identifier.
     * @param displayItem display item.
     * @param cost        trap cost.
     * @param currency    currency cost.
     */
    public MenuBaseTrap(String name, ItemStack displayItem, int cost, Material currency) {
        this.displayItem = BedWars.nms.addCustomData(displayItem, "MCONT_" + name);
        this.name = name;
        String nPath = name.replace("base-trap-", "");
        Language.saveIfNotExists(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + nPath, "&cName not set");
        Language.saveIfNotExists(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + nPath, Collections.singletonList("&cLore not set"));
        if (UpgradesManager.getConfiguration().getBoolean(name + ".custom-announce")) {
            Language.saveIfNotExists(Messages.UPGRADES_TRAP_CUSTOM_MSG + nPath, "Edit path: " + Messages.UPGRADES_TRAP_CUSTOM_MSG + nPath);
            Language.saveIfNotExists(Messages.UPGRADES_TRAP_CUSTOM_TITLE + nPath, "Edit path: " + Messages.UPGRADES_TRAP_CUSTOM_TITLE + nPath);
            Language.saveIfNotExists(Messages.UPGRADES_TRAP_CUSTOM_SUBTITLE + nPath, "Edit path: " + Messages.UPGRADES_TRAP_CUSTOM_SUBTITLE + nPath);
        }

        this.cost = cost;
        this.currency = currency;

        for (String action : UpgradesManager.getConfiguration().getYml().getStringList(name + ".receive")) {
            String[] type = action.trim().split(":");
            if (type.length < 2) continue;
            String[] data = type[1].trim().toLowerCase().split(",");
            TrapAction ua = null;

            switch (type[0].trim().toLowerCase()) {
                case "player-effect":
                    if (data.length < 4) {
                        BedWars.plugin.getLogger().warning("Invalid " + type[0] + " at upgrades2: " + name);
                        continue;
                    }
                    PotionEffectType pe = PotionEffectType.getByName(data[0].toUpperCase());
                    if (pe == null) {
                        BedWars.plugin.getLogger().warning("Invalid potion effect " + data[0] + " at upgrades2: " + name);
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
                        case "enemy":
                        case "enemies":
                            applyType = PlayerEffectAction.ApplyType.ENEMY;
                            break;
                    }
                    if (applyType == null) {
                        BedWars.plugin.getLogger().warning("Invalid apply type " + data[3] + " at upgrades2: " + name);
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
                case "disenchant-item":
                    if (data.length < 2) {
                        BedWars.plugin.getLogger().warning("Invalid " + type[0] + " at upgrades2: " + name);
                        continue;
                    }
                    Enchantment re = Enchantment.getByName(data[0].toUpperCase());
                    if (re == null) {
                        BedWars.plugin.getLogger().warning("Invalid enchantment " + data[0] + " at upgrades2: " + name);
                        continue;
                    }
                    DisenchantAction.ApplyType da = null;
                    switch (data[1].toLowerCase()) {
                        case "sword":
                            da = DisenchantAction.ApplyType.SWORD;
                            break;
                        case "armor":
                            da = DisenchantAction.ApplyType.ARMOR;
                            break;
                        case "bow":
                            da = DisenchantAction.ApplyType.BOW;
                            break;
                    }
                    if (da == null) {
                        BedWars.plugin.getLogger().warning("Invalid apply type " + data[3] + " at upgrades2: " + name);
                        continue;
                    }
                    ua = new DisenchantAction(re, da);
                    break;
                case "remove-effect":
                    if (data.length < 1) {
                        BedWars.plugin.getLogger().warning("Invalid " + type[0] + " at upgrades2: " + name);
                        continue;
                    }
                    PotionEffectType pet = PotionEffectType.getByName(data[0].toUpperCase());
                    if (pet == null) {
                        BedWars.plugin.getLogger().warning("Invalid potion effect " + data[0] + " at upgrades2: " + name);
                        continue;
                    }
                    ua = new RemoveEffectAction(pet);
                    break;
            }
            if (ua != null) trapActions.add(ua);
        }
    }

    @Override
    public ItemStack getDisplayItem(Player player, ITeam team) {
        Material currency = this.currency;
        if (this.currency == null) {
            String st = UpgradesManager.getConfiguration().getYml().getString(team.getArena().getGroup().toLowerCase() + "-upgrades-settings.trap-currency");
            if (st == null) {
                st = UpgradesManager.getConfiguration().getYml().getString("default-upgrades-settings.trap-currency");
            }
            currency = Material.valueOf(st.toUpperCase());
        }

        int cost = this.cost;
        if (cost == 0) {
            int multiplier = team.getActiveTraps().size();

            int incrementer = UpgradesManager.getConfiguration().getYml().getInt(team.getArena().getGroup().toLowerCase() + "-upgrades-settings.trap-increment-price");
            if (incrementer == 0) {
                incrementer = UpgradesManager.getConfiguration().getYml().getInt("default-upgrades-settings.trap-increment-price");
            }

            cost = UpgradesManager.getConfiguration().getYml().getInt(team.getArena().getGroup().toLowerCase() + "-upgrades-settings.trap-start-price");
            if (cost == 0) {
                cost = UpgradesManager.getConfiguration().getYml().getInt("default-upgrades-settings.trap-start-price");
            }
            cost = cost + (multiplier * incrementer);
        }

        ItemStack i = displayItem.clone();
        ItemMeta im = i.getItemMeta();
        if (im != null) {
            boolean afford = UpgradesManager.getMoney(player, currency) >= cost;
            String color;
            if (afford) {
                color = Language.getMsg(player, Messages.FORMAT_UPGRADE_COLOR_CAN_AFFORD);
            } else {
                color = Language.getMsg(player, Messages.FORMAT_UPGRADE_COLOR_CANT_AFFORD);
            }
            im.setDisplayName(Language.getMsg(player, Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + name.replace("base-trap-", ""))
                    .replace("{color}", color));

            List<String> lore = Language.getList(player, Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + name.replace("base-trap-", ""));
            String currencyMsg = UpgradesManager.getCurrencyMsg(player, cost, currency);
            lore.add(Language.getMsg(player, Messages.FORMAT_UPGRADE_TRAP_COST).replace("{cost}", String.valueOf(cost)).replace("{currency}", currencyMsg)
                    .replace("{currencyColor}", String.valueOf(UpgradesManager.getCurrencyColor(currency))));
            lore.add("");
            if (afford) {
                lore.add(Language.getMsg(player, Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY).replace("{color}", color));
            } else {
                lore.add(Language.getMsg(player, Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY).replace("{currency}", currencyMsg).replace("{color}", color));
            }
            im.setLore(lore);
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            i.setItemMeta(im);
        }
        return i;
    }

    @Override
    public void onClick(Player player, ClickType clickType, ITeam team) {
        int queueLimit = UpgradesManager.getConfiguration().getInt(team.getArena().getGroup().toLowerCase() + "-upgrades-settings.trap-queue-limit");
        if (queueLimit == 0) {
            queueLimit = UpgradesManager.getConfiguration().getInt("default-upgrades-settings.trap-queue-limit");
        }
        if (queueLimit <= team.getActiveTraps().size()) {
            player.sendMessage(Language.getMsg(player, Messages.UPGRADES_TRAP_QUEUE_LIMIT));
            return;
        }

        Material currency = this.currency;
        if (this.currency == null) {
            String st = UpgradesManager.getConfiguration().getYml().getString(team.getArena().getGroup().toLowerCase() + "-upgrades-settings.trap-currency");
            if (st == null) {
                st = UpgradesManager.getConfiguration().getYml().getString("default-upgrades-settings.trap-currency");
            }
            currency = Material.valueOf(st.toUpperCase());
        }

        int cost = this.cost;
        if (cost == 0) {
            int multiplier = team.getActiveTraps().size();

            int incrementer = UpgradesManager.getConfiguration().getYml().getInt(team.getArena().getGroup().toLowerCase() + "-upgrades-settings.trap-increment-price");
            if (incrementer == 0) {
                incrementer = UpgradesManager.getConfiguration().getYml().getInt("default-upgrades-settings.trap-increment-price");
            }

            cost = UpgradesManager.getConfiguration().getYml().getInt(team.getArena().getGroup().toLowerCase() + "-upgrades-settings.trap-start-price");
            if (cost == 0) {
                cost = UpgradesManager.getConfiguration().getYml().getInt("default-upgrades-settings.trap-start-price");
            }
            cost = cost + (multiplier * incrementer);
        }

        int money = UpgradesManager.getMoney(player, currency);
        if (money < cost) {
            Sounds.playSound(ConfigPath.SOUNDS_INSUFF_MONEY, player);
            player.sendMessage(Language.getMsg(player, Messages.SHOP_INSUFFICIENT_MONEY)
                    .replace("{currency}", UpgradesManager.getCurrencyMsg(player, cost, currency))
                    .replace("{amount}", String.valueOf(cost - money)));
            player.closeInventory();
            return;
        }

        final UpgradeBuyEvent event;
        Bukkit.getPluginManager().callEvent(event = new UpgradeBuyEvent(this, player, team));
        if(event.isCancelled()) return;

        if (currency == Material.AIR) {
            BedWars.getEconomy().buyAction(player, money);
        } else {
            BedWars.getAPI().getShopUtil().takeMoney(player, currency, cost);
        }
        Sounds.playSound(ConfigPath.SOUNDS_BOUGHT, player);
        team.getActiveTraps().add(this);
        // when a new trap is bought check for enemies on the island #646
        for (Player arenaPlayer : team.getArena().getPlayers()) {
            if (team.isMember(arenaPlayer)) continue;
            if (team.getArena().isReSpawning(arenaPlayer)) continue;
            if (arenaPlayer.getLocation().distance(team.getBed()) <= team.getArena().getIslandRadius()) {
                team.getActiveTraps().remove(0).trigger(team, arenaPlayer);
                break;
            }
        }

        for (Player p1 : team.getMembers()) {
            p1.sendMessage(Language.getMsg(p1, Messages.UPGRADES_UPGRADE_BOUGHT_CHAT).replace("{playername}", player.getName()).replace("{player}", player.getDisplayName()).replace("{upgradeName}",
                    ChatColor.stripColor(Language.getMsg(p1, Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + getName().replace("base-trap-", "")).replace("{color}", ""))));
        }
        UpgradesManager.getMenuForArena(team.getArena()).open(player);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getTierCount() {
        return trapActions.size();
    }

    @Override
    public String getNameMsgPath() {
        return Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + name.replace("base-trap-", "");
    }

    @Override
    public String getLoreMsgPath() {
        return Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + name.replace("base-trap-", "");
    }

    @Override
    public ItemStack getItemStack() {
        return displayItem;
    }

    @Override
    public void trigger(ITeam trapTeam, Player player) {

        Sound sound = null;
        if (UpgradesManager.getConfiguration().getYml().get(name + ".sound") != null) {
            try {
                sound = Sound.valueOf(UpgradesManager.getConfiguration().getYml().getString(name + ".sound"));
            } catch (Exception ignored) {
            }
        }
        if (!Sounds.playSound(sound, trapTeam.getMembers())) {
            Sounds.playSound("trap-sound", trapTeam.getMembers());
        }

        final ITeam enemyTeam = trapTeam.getArena().getTeam(player);
        trapActions.forEach(t -> t.onTrigger(player, enemyTeam, trapTeam));

        if (UpgradesManager.getConfiguration().getBoolean(name + ".custom-announce")) {
            String name2 = name.replace("base-trap-", "");
            String color = trapTeam.getArena().getTeam(player) == null ? "" : trapTeam.getArena().getTeam(player).getColor().chat().toString();
            for (Player p : trapTeam.getMembers()) {
                String trapName = ChatColor.stripColor(Language.getMsg(p, getNameMsgPath())).replace("{color}", "");
                String enemy = trapTeam.getArena().getTeam(player) == null ? "NULL" : trapTeam.getArena().getTeam(player).getDisplayName(Language.getPlayerLanguage(p));
                p.sendMessage(Language.getMsg(p, Messages.UPGRADES_TRAP_CUSTOM_MSG + name2).replace("{trap}", trapName)
                        .replace("{player}", player.getName()).replace("{team}", enemy).replace("{color}", color));
                BedWars.nms.sendTitle(p, Language.getMsg(p, Messages.UPGRADES_TRAP_CUSTOM_TITLE + name2)
                                .replace("{trap}", trapName).replace("{player}", player.getName()).replace("{team}", enemy).replace("{color}", color),
                        Language.getMsg(p, Messages.UPGRADES_TRAP_CUSTOM_SUBTITLE + name2).replace("{trap}", trapName).replace("{player}", player.getName())
                                .replace("{team}", enemy).replace("{color}", color), 15, 35, 10);
            }
        } else {
            for (Player p : trapTeam.getMembers()) {
                String trapName = ChatColor.stripColor(Language.getMsg(p, getNameMsgPath())).replace("{color}", "");
                p.sendMessage(Language.getMsg(p, Messages.UPGRADES_TRAP_DEFAULT_MSG).replace("{trap}", trapName));
                BedWars.nms.sendTitle(p, Language.getMsg(p, Messages.UPGRADES_TRAP_DEFAULT_TITLE)
                        .replace("{trap}", trapName), Language.getMsg(p, Messages.UPGRADES_TRAP_DEFAULT_SUBTITLE)
                        .replace("{trap}", trapName), 15, 35, 10);
            }
        }
    }
}
