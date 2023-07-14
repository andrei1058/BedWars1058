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

package com.andrei1058.bedwars.configuration;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.andrei1058.bedwars.BedWars.getForCurrentVersion;
import static com.andrei1058.bedwars.BedWars.plugin;

public class UpgradesConfig extends ConfigManager {

    public UpgradesConfig(String name, String dir) {
        super(plugin, name, dir);
        YamlConfiguration yml = this.getYml();
        List<String> elements = Arrays.asList("upgrade-swords,10", "upgrade-armor,11", "upgrade-miner,12", "upgrade-forge,13",
                "upgrade-heal-pool,14", "upgrade-dragon,15", "category-traps,16", "separator-glass,18,19,20,21,22,23,24,25,26",
                "trap-slot-first,30", "trap-slot-second,31", "trap-slot-third,32");
        yml.addDefault("default-upgrades-settings.menu-content", elements);
        yml.addDefault("default-upgrades-settings.trap-start-price", 1);
        yml.addDefault("default-upgrades-settings.trap-increment-price", 1);
        yml.addDefault("default-upgrades-settings.trap-currency", "diamond");
        yml.addDefault("default-upgrades-settings.trap-queue-limit", 3);

        if (isFirstTime()) {
            yml.addDefault("upgrade-swords.tier-1.cost", 4);
            yml.addDefault("upgrade-swords.tier-1.currency", "diamond");
            addDefaultDisplayItem("upgrade-swords.tier-1", "IRON_SWORD", 0, 1, false);
            //noinspection ArraysAsListWithZeroOrOneArgument
            yml.addDefault("upgrade-swords.tier-1.receive", Arrays.asList("enchant-item: DAMAGE_ALL,1,sword"));

            yml.addDefault("upgrade-armor.tier-1.currency", "diamond");
            yml.addDefault("upgrade-armor.tier-1.cost", 2);
            addDefaultDisplayItem("upgrade-armor.tier-1", "IRON_CHESTPLATE", 0, 1, false);
            yml.addDefault("upgrade-armor.tier-1.receive", Collections.singletonList("enchant-item: PROTECTION_ENVIRONMENTAL,1,armor"));

            yml.addDefault("upgrade-armor.tier-2.currency", "diamond");
            yml.addDefault("upgrade-armor.tier-2.cost", 4);
            addDefaultDisplayItem("upgrade-armor.tier-2", "IRON_CHESTPLATE", 0, 2, false);
            yml.addDefault("upgrade-armor.tier-2.receive", Collections.singletonList("enchant-item: PROTECTION_ENVIRONMENTAL,2,armor"));

            yml.addDefault("upgrade-armor.tier-3.currency", "diamond");
            yml.addDefault("upgrade-armor.tier-3.cost", 8);
            addDefaultDisplayItem("upgrade-armor.tier-3", "IRON_CHESTPLATE", 0, 3, false);
            yml.addDefault("upgrade-armor.tier-3.receive", Collections.singletonList("enchant-item: PROTECTION_ENVIRONMENTAL,3,armor"));

            yml.addDefault("upgrade-armor.tier-4.currency", "diamond");
            yml.addDefault("upgrade-armor.tier-4.cost", 16);
            addDefaultDisplayItem("upgrade-armor.tier-4", "IRON_CHESTPLATE", 0, 4, false);
            yml.addDefault("upgrade-armor.tier-4.receive", Collections.singletonList("enchant-item: PROTECTION_ENVIRONMENTAL,4,armor"));

            yml.addDefault("upgrade-miner.tier-1.currency", "diamond");
            yml.addDefault("upgrade-miner.tier-1.cost", 2);
            addDefaultDisplayItem("upgrade-miner.tier-1", getForCurrentVersion("GOLD_PICKAXE", "GOLD_PICKAXE", "GOLDEN_PICKAXE"), 0, 1, false);
            yml.addDefault("upgrade-miner.tier-1.receive", Collections.singletonList("player-effect: FAST_DIGGING,0,0,team"));

            yml.addDefault("upgrade-miner.tier-2.currency", "diamond");
            yml.addDefault("upgrade-miner.tier-2.cost", 4);
            addDefaultDisplayItem("upgrade-miner.tier-2", getForCurrentVersion("GOLD_PICKAXE", "GOLD_PICKAXE", "GOLDEN_PICKAXE"), 0, 2, false);            yml.addDefault("upgrade-miner.tier-2.receive", Collections.singletonList("player-effect: FAST_DIGGING,1,0,team"));

            yml.addDefault("upgrade-forge.tier-1.currency", "diamond");
            yml.addDefault("upgrade-forge.tier-1.cost", 2);
            addDefaultDisplayItem("upgrade-forge.tier-1", "FURNACE", 0, 1, false);
            yml.addDefault("upgrade-forge.tier-1.receive", Arrays.asList("generator-edit: iron,2,2,41", "generator-edit: gold,3,1,14"));

            yml.addDefault("upgrade-forge.tier-2.currency", "diamond");
            yml.addDefault("upgrade-forge.tier-2.cost", 4);
            addDefaultDisplayItem("upgrade-forge.tier-2", "FURNACE", 0, 2, false);
            yml.addDefault("upgrade-forge.tier-2.receive", Arrays.asList("generator-edit: iron,1,2,48", "generator-edit: gold,3,2,21"));

            yml.addDefault("upgrade-forge.tier-3.currency", "diamond");
            yml.addDefault("upgrade-forge.tier-3.cost", 6);
            addDefaultDisplayItem("upgrade-forge.tier-3", "FURNACE", 0, 3, false);
            yml.addDefault("upgrade-forge.tier-3.receive", Arrays.asList("generator-edit: iron,1,2,64", "generator-edit: gold,3,2,29",
                    "generator-edit: emerald,10,1,10"));

            yml.addDefault("upgrade-forge.tier-4.currency", "diamond");
            yml.addDefault("upgrade-forge.tier-4.cost", 8);
            addDefaultDisplayItem("upgrade-forge.tier-4", "FURNACE", 0, 4, false);
            yml.addDefault("upgrade-forge.tier-4.receive", Arrays.asList("generator-edit: iron,1,4,120", "generator-edit: gold,2,4,80",
                    "generator-edit: emerald,10,2,20"));

            yml.addDefault("upgrade-heal-pool.tier-1.currency", "diamond");
            yml.addDefault("upgrade-heal-pool.tier-1.cost", 1);
            addDefaultDisplayItem("upgrade-heal-pool.tier-1", "BEACON", 0, 1, false);
            yml.addDefault("upgrade-heal-pool.tier-1.receive", Collections.singletonList("player-effect: REGENERATION,1,0,base"));

            yml.addDefault("upgrade-dragon.tier-1.currency", "diamond");
            yml.addDefault("upgrade-dragon.tier-1.cost", 5);
            addDefaultDisplayItem("upgrade-dragon.tier-1", "DRAGON_EGG", 0, 1, false);
            yml.addDefault("upgrade-dragon.tier-1.receive", Collections.singletonList("dragon: 1"));

            addDefaultDisplayItem("category-traps", "LEATHER", 0, 1, false);
            yml.addDefault("category-traps.category-content", Arrays.asList("base-trap-1,10", "base-trap-2,11",
                    "base-trap-3,12", "base-trap-4,13", "separator-back,31"));

            yml.addDefault("separator-glass.on-click", "");
            addDefaultDisplayItem("separator-glass", getForCurrentVersion("STAINED_GLASS_PANE", "STAINED_GLASS_PANE",
                    "GRAY_STAINED_GLASS_PANE"), 7, 1, false);

            yml.addDefault("trap-slot-first.trap", 1);
            addDefaultDisplayItem("trap-slot-first", getForCurrentVersion("STAINED_GLASS", "STAINED_GLASS",
                    "GRAY_STAINED_GLASS"), 8, 1, false);
            yml.addDefault("trap-slot-second.trap", 2);
            addDefaultDisplayItem("trap-slot-second", getForCurrentVersion("STAINED_GLASS", "STAINED_GLASS",
                    "GRAY_STAINED_GLASS"), 8, 2, false);
            yml.addDefault("trap-slot-third.trap", 3);
            addDefaultDisplayItem("trap-slot-third", getForCurrentVersion("STAINED_GLASS", "STAINED_GLASS",
                    "GRAY_STAINED_GLASS"), 8, 3, false);

            addDefaultDisplayItem("base-trap-1", "TRIPWIRE_HOOK", 0, 1, false);
            yml.addDefault("base-trap-1.receive", Arrays.asList("player-effect: BLINDNESS,1,5,enemy", "player-effect: SLOW,1,5,enemy"));

            addDefaultDisplayItem("base-trap-2", "FEATHER", 0, 1, false);
            yml.addDefault("base-trap-2.receive", Collections.singletonList("player-effect: SPEED,1,15,base"));

            addDefaultDisplayItem("base-trap-3", getForCurrentVersion("REDSTONE_TORCH_ON", "REDSTONE_TORCH", "REDSTONE_TORCH"), 0, 1, false);
            yml.addDefault("base-trap-3.custom-announce", true);
            yml.addDefault("base-trap-3.receive", Collections.singletonList("remove-effect: INVISIBILITY,enemy"));

            addDefaultDisplayItem("base-trap-4", "IRON_PICKAXE", 0, 1, false);
            yml.addDefault("base-trap-4.receive", Collections.singletonList("player-effect: SLOW_DIGGING,1,15,enemy"));

            //noinspection ArraysAsListWithZeroOrOneArgument
            yml.addDefault("separator-back.on-click.player", Arrays.asList("bw upgradesmenu"));
            //noinspection ArraysAsListWithZeroOrOneArgument
            yml.addDefault("separator-back.on-click.console", Arrays.asList(""));
            addDefaultDisplayItem("separator-back", "ARROW", 0, 1, false);
        }
        yml.options().copyDefaults(true);
        save();
    }

    private void addDefaultDisplayItem(String path, String material, int data, int amount, boolean enchanted) {
        getYml().addDefault(path + ".display-item.material", material);
        getYml().addDefault(path + ".display-item.data", data);
        getYml().addDefault(path + ".display-item.amount", amount);
        getYml().addDefault(path + ".display-item.enchanted", enchanted);
    }

    //private YamlConfiguration yml;

    /*public UpgradesConfig(String name, String dir) {
        super(plugin, name, dir);
        YamlConfiguration yml = getYml();
        yml.addDefault("Default.generators.slot", 11);

        yml.addDefault("Default.generators.tier1.displayItem.material", "FURNACE");
        yml.addDefault("Default.generators.tier1.displayItem.data", 0);
        yml.addDefault("Default.generators.tier1.displayItem.amount", 1);
        yml.addDefault("Default.generators.tier1.displayItem.enchanted", false);
        yml.addDefault("Default.generators.tier1.currency", "diamond");
        yml.addDefault("Default.generators.tier1.cost", 4);
        yml.addDefault("Default.generators.tier1.receive.teamGenerator.iron.delay", 2);
        yml.addDefault("Default.generators.tier1.receive.teamGenerator.iron.amount", 2);
        yml.addDefault("Default.generators.tier1." + ConfigPath.UPGRADES_TEAM_UPGRADE_RECEIVE_TEAM_GENERATOR_SPAWN_LIMIT.replace("%g%", "iron").replace("%r%", "teamGenerator"), 41);
        yml.addDefault("Default.generators.tier1.receive.teamGenerator.gold.delay", 3);
        yml.addDefault("Default.generators.tier1.receive.teamGenerator.gold.amount", 1);
        yml.addDefault("Default.generators.tier1." + ConfigPath.UPGRADES_TEAM_UPGRADE_RECEIVE_TEAM_GENERATOR_SPAWN_LIMIT.replace("%g%", "gold").replace("%r%", "teamGenerator"), 14);

        yml.addDefault("Default.generators.tier2.displayItem.material", "FURNACE");
        yml.addDefault("Default.generators.tier2.displayItem.data", 0);
        yml.addDefault("Default.generators.tier2.displayItem.amount", 1);
        yml.addDefault("Default.generators.tier2.displayItem.enchanted", false);
        yml.addDefault("Default.generators.tier2.currency", "diamond");
        yml.addDefault("Default.generators.tier2.cost", 8);
        yml.addDefault("Default.generators.tier2.receive.teamGenerator.iron.delay", 1);
        yml.addDefault("Default.generators.tier2.receive.teamGenerator.iron.amount", 2);
        yml.addDefault("Default.generators.tier2." + ConfigPath.UPGRADES_TEAM_UPGRADE_RECEIVE_TEAM_GENERATOR_SPAWN_LIMIT.replace("%g%", "iron").replace("%r%", "teamGenerator"), 48);
        yml.addDefault("Default.generators.tier2.receive.teamGenerator.gold.delay", 3);
        yml.addDefault("Default.generators.tier2.receive.teamGenerator.gold.amount", 2);
        yml.addDefault("Default.generators.tier2." + ConfigPath.UPGRADES_TEAM_UPGRADE_RECEIVE_TEAM_GENERATOR_SPAWN_LIMIT.replace("%g%", "gold").replace("%r%", "teamGenerator"), 21);

        yml.addDefault("Default.generators.tier3.displayItem.material", "FURNACE");
        yml.addDefault("Default.generators.tier3.displayItem.data", 0);
        yml.addDefault("Default.generators.tier3.displayItem.amount", 1);
        yml.addDefault("Default.generators.tier3.displayItem.enchanted", false);
        yml.addDefault("Default.generators.tier3.currency", "diamond");
        yml.addDefault("Default.generators.tier3.cost", 12);
        yml.addDefault("Default.generators.tier3.receive.teamGenerator.iron.delay", 1);
        yml.addDefault("Default.generators.tier3.receive.teamGenerator.iron.amount", 2);
        yml.addDefault("Default.generators.tier3." + ConfigPath.UPGRADES_TEAM_UPGRADE_RECEIVE_TEAM_GENERATOR_SPAWN_LIMIT.replace("%g%", "iron").replace("%r%", "teamGenerator"), 64);
        yml.addDefault("Default.generators.tier3.receive.teamGenerator.gold.delay", 2);
        yml.addDefault("Default.generators.tier3.receive.teamGenerator.gold.amount", 2);
        yml.addDefault("Default.generators.tier3." + ConfigPath.UPGRADES_TEAM_UPGRADE_RECEIVE_TEAM_GENERATOR_SPAWN_LIMIT.replace("%g%", "gold").replace("%r%", "teamGenerator"), 29);
        yml.addDefault("Default.generators.tier3.receive.teamGenerator.emerald.delay", 10);
        yml.addDefault("Default.generators.tier3.receive.teamGenerator.emerald.amount", 1);

        yml.addDefault("Default.maniacMiner.slot", 12);
        yml.addDefault("Default.maniacMiner.tier1.displayItem.material", BedWars.getForCurrentVersion("GOLD_AXE", "GOLD_AXE", "GOLDEN_AXE"));
        yml.addDefault("Default.maniacMiner.tier1.displayItem.data", 0);
        yml.addDefault("Default.maniacMiner.tier1.displayItem.amount", 1);
        yml.addDefault("Default.maniacMiner.tier1.displayItem.enchanted", false);
        yml.addDefault("Default.maniacMiner.tier1.currency", "diamond");
        yml.addDefault("Default.maniacMiner.tier1.cost", 4);
        yml.addDefault("Default.maniacMiner.tier1.receive.playerEffect.haste1.effect", "FAST_DIGGING");
        yml.addDefault("Default.maniacMiner.tier1.receive.playerEffect.haste1.amplifier", 0);
        yml.addDefault("Default.maniacMiner.tier1.receive.playerEffect.haste1.apply", "members");


        yml.addDefault("Default.sharpSword.slot", 13);
        yml.addDefault("Default.sharpSword.tier1.displayItem.material", "IRON_SWORD");
        yml.addDefault("Default.sharpSword.tier1.displayItem.data", 0);
        yml.addDefault("Default.sharpSword.tier1.displayItem.amount", 1);
        yml.addDefault("Default.sharpSword.tier1.displayItem.enchanted", false);
        yml.addDefault("Default.sharpSword.tier1.currency", "diamond");
        yml.addDefault("Default.sharpSword.tier1.cost", 4);
        yml.addDefault("Default.sharpSword.tier1.receive.itemEnchantment.sharp.enchantment", "DAMAGE_ALL");
        yml.addDefault("Default.sharpSword.tier1.receive.itemEnchantment.sharp.amplifier", 1);
        yml.addDefault("Default.sharpSword.tier1.receive.itemEnchantment.sharp.apply", "sword"); //sword, bow, armor


        yml.addDefault("Default.reinforced.slot", 14);
        yml.addDefault("Default.reinforced.tier1.displayItem.material", "IRON_CHESTPLATE");
        yml.addDefault("Default.reinforced.tier1.displayItem.data", 0);
        yml.addDefault("Default.reinforced.tier1.displayItem.amount", 1);
        yml.addDefault("Default.reinforced.tier1.displayItem.enchanted", false);
        yml.addDefault("Default.reinforced.tier1.currency", "diamond");
        yml.addDefault("Default.reinforced.tier1.cost", 2);
        yml.addDefault("Default.reinforced.tier1.receive.itemEnchantment.sharp.enchantment", "PROTECTION_ENVIRONMENTAL");
        yml.addDefault("Default.reinforced.tier1.receive.itemEnchantment.sharp.amplifier", 1);
        yml.addDefault("Default.reinforced.tier1.receive.itemEnchantment.sharp.apply", "armor"); //sword, bow, armor


        yml.addDefault("Default.trap.slot", 15);
        yml.addDefault("Default.trap.tier1.displayItem.material", "TRIPWIRE_HOOK");
        yml.addDefault("Default.trap.tier1.displayItem.data", 0);
        yml.addDefault("Default.trap.tier1.displayItem.amount", 1);
        yml.addDefault("Default.trap.tier1.displayItem.enchanted", false);
        yml.addDefault("Default.trap.tier1.currency", "diamond");
        yml.addDefault("Default.trap.tier1.cost", 1);
        yml.addDefault("Default.trap.tier1.receive.enemyBaseEnter.announce", "title, subtitle, action, chat");
        yml.addDefault("Default.trap.tier1.receive.playerEffect.blindness.effect", "BLINDNESS");
        yml.addDefault("Default.trap.tier1.receive.playerEffect.blindness.amplifier", 1);
        yml.addDefault("Default.trap.tier1.receive.playerEffect.blindness.apply", "enemyBaseEnter");
        yml.addDefault("Default.trap.tier1.receive.playerEffect.slowness.effect", "SLOW");
        yml.addDefault("Default.trap.tier1.receive.playerEffect.blindness.duration", 20);
        yml.addDefault("Default.trap.tier1.receive.playerEffect.slowness.amplifier", 0);
        yml.addDefault("Default.trap.tier1.receive.playerEffect.slowness.apply", "enemyBaseEnter");
        yml.addDefault("Default.trap.tier1.receive.playerEffect.slowness.duration", 20);

        yml.addDefault("Default.miningFatigue.slot", 20);
        yml.addDefault("Default.miningFatigue.tier1.displayItem.material", "IRON_PICKAXE");
        yml.addDefault("Default.miningFatigue.tier1.displayItem.data", 0);
        yml.addDefault("Default.miningFatigue.tier1.displayItem.amount", 1);
        yml.addDefault("Default.miningFatigue.tier1.displayItem.enchanted", false);
        yml.addDefault("Default.miningFatigue.tier1.currency", "diamond");
        yml.addDefault("Default.miningFatigue.tier1.cost", 2);
        yml.addDefault("Default.miningFatigue.tier1.receive.enemyBaseEnter.announce", "title, subtitle");
        yml.addDefault("Default.miningFatigue.tier1.receive.playerEffect.fatigue.effect", "SLOW_DIGGING");
        yml.addDefault("Default.miningFatigue.tier1.receive.playerEffect.fatigue.amplifier", 0);
        yml.addDefault("Default.miningFatigue.tier1.receive.playerEffect.fatigue.apply", "enemyBaseEnter");
        yml.addDefault("Default.miningFatigue.tier1.receive.playerEffect.fatigue.duration", 30);

        yml.addDefault("Default.healPool.slot", 21);
        yml.addDefault("Default.healPool.tier1.displayItem.material", "BEACON");
        yml.addDefault("Default.healPool.tier1.displayItem.data", 0);
        yml.addDefault("Default.healPool.tier1.displayItem.amount", 1);
        yml.addDefault("Default.healPool.tier1.displayItem.enchanted", false);
        yml.addDefault("Default.healPool.tier1.currency", "diamond");
        yml.addDefault("Default.healPool.tier1.cost", 1);
        yml.addDefault("Default.healPool.tier1.receive.playerEffect.fatigue.effect", "REGENERATION");
        yml.addDefault("Default.healPool.tier1.receive.playerEffect.fatigue.amplifier", 0);
        yml.addDefault("Default.healPool.tier1.receive.playerEffect.fatigue.apply", "base");
        yml.options().copyDefaults(true);
        yml.addDefault("settings.invSize", 36);
        save();
        this.set("settings.startValues", null);
        this.set("settings.stackItems", null);

        if (yml != null) {
            for (String s : yml.getConfigurationSection("").getKeys(false)) {
                if (s.equalsIgnoreCase("settings")) continue;
                loadTeamUpgrades(s);
            }
        }

    /*
    public void loadTeamUpgrades(String path) {
        saveIfNotExists("upgrades." + path + ".name", "&8Team Upgrades");
        List<TeamUpgrade> teamUpgrades = new ArrayList<>();
        for (String tu : yml.getConfigurationSection(path).getKeys(false)) {
            if (tu.equalsIgnoreCase("settings")) continue;
            if (yml.get(path.isEmpty() ? tu + ".slot" : path + "." + tu + ".slot") == null) {
                logger("slot not set for " + tu);
                continue;
            }
            if (yml.getConfigurationSection((path.isEmpty() ? tu : path + "." + tu)).getKeys(false).isEmpty()) {
                logger("no tier defined for " + tu);
                continue;
            }
            List<UpgradeTier> tiers = new ArrayList<>();
            List<UpgradeAction> actions;
            for (String tier : yml.getConfigurationSection(path + "." + tu).getKeys(false)) {
                String tp = path.isEmpty() ? tu + "." + tier + "." : path + "." + tu + "." + tier + ".";
                if (tier.equalsIgnoreCase("slot")) continue;
                saveIfNotExists("upgrades." + tp + "name", "&cA nice name");
                saveIfNotExists("upgrades." + tp + "lore", Arrays.asList("&7A nice description here", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));
                if (yml.isSet(tp + "displayItem")) {
                    if (yml.isSet(tp + "displayItem.material")) {
                        try {
                            Material.valueOf(yml.getString(tp + "displayItem.material"));
                        } catch (Exception ex) {
                            logger("Invalid material at " + tp + "displayItem.material");
                            continue;
                        }
                    } else {
                        yml.set(tp + ".displayItem.material", "STONE");
                        save();
                        logger("material not set at " + tp + "displayItem");
                        continue;
                    }
                } else {
                    logger("displayItem not set for tier " + tier + " at " + tp);
                    continue;
                }
                if (yml.isSet(tp + "currency")) {
                    List<String> currency = Arrays.asList("diamond", "emerald", "vault", "iron", "gold");
                    if (!currency.contains(yml.getString(tp + "currency").toLowerCase())) {
                        logger("invalid currency type at " + tp);
                        logger("available currencies: diamond, emerald, vault, iron, gold");
                        continue;
                    }
                } else {
                    yml.set("currency", "vault");
                    save();
                    continue;
                }
                if (yml.isSet(tp + "cost")) {
                    try {
                        yml.getInt(tp + "cost");
                    } catch (Exception ex) {
                        logger("cost must be an integer at " + tp);
                        continue;
                    }
                } else {
                    logger("cost not set for " + tp);
                    continue;
                }
                if (yml.get(tp + "receive") == null) continue;
                if (yml.getConfigurationSection(tp + "receive").getKeys(false).isEmpty()) {
                    logger("receive is empty at " + tp);
                    continue;
                }
                actions = new ArrayList<>();
                for (String re : yml.getConfigurationSection(tp + "receive").getKeys(false)) {
                    switch (re.toLowerCase()) {
                        case "playereffect":
                            for (String pe : yml.getConfigurationSection(tp + "receive." + re).getKeys(false)) {
                                if (yml.isSet(tp + "receive." + re + "." + pe + ".effect")) {
                                    try {
                                        PotionEffectType.getByName(yml.getString(tp + "receive." + re + "." + pe + ".effect"));
                                    } catch (Exception ex) {
                                        logger("wrong potion at: " + tp + "receive" + re + "." + pe + ".effect");
                                        continue;
                                    }
                                } else {
                                    logger("potion not set at: " + tp + "receive" + re + "." + pe + ".effect");
                                    yml.set(tp + "receive" + re + "." + pe + ".effect", "JUMP");
                                    save();
                                    continue;
                                }
                                if (yml.isSet(tp + "receive" + re + "." + pe + ".apply")) {
                                    if (!Arrays.asList("members", "base", "enemyBaseEnter").contains(yml.getString(tp + "receive" + re + "." + pe + ".apply").toLowerCase())) {
                                        logger("wrong apply at: " + tp + "receive" + re + "." + pe + ".apply");
                                        continue;
                                    }
                                }
                                actions.add(new EffectAction(pe, PotionEffectType.getByName(yml.getString(tp + "receive." + re + "." + pe + ".effect")),
                                        yml.isSet(tp + "receive." + re + "." + pe + ".amplifier") ? yml.getInt(tp + "receive." + re + "." + pe + ".amplifier")
                                                : 1, yml.getString(tp + "receive." + re + "." + pe + ".apply"), yml.isSet(tp + "receive." + re + "." + pe + ".duration") ?
                                        yml.getInt(tp + "receive." + re + "." + pe + ".duration") * 20 : -1));
                            }
                            break;
                        case "itemenchantment":
                            for (String ie : yml.getConfigurationSection(tp + "receive." + re).getKeys(false)) {
                                if (yml.isSet(tp + "receive." + re + "." + ie + ".enchantment")) {
                                    try {
                                        Enchantment.getByName(yml.getString(tp + "receive." + re + "." + ie + ".enchantment"));
                                    } catch (Exception ex) {
                                        logger("wrong enchantment at: " + tp + "receive" + re + "." + ie + ".enchantment");
                                        continue;
                                    }
                                } else {
                                    logger("enchantment not set at: " + tp + "receive." + re + "." + ie + ".enchantment");
                                    yml.set(tp + "receive." + re + "." + ie + ".enchantment", "DIG_SPEED");
                                    save();
                                    continue;
                                }
                                if (yml.isSet(tp + "receive" + re + "." + ie + ".apply")) {
                                    if (!Arrays.asList("sword", "bow", "armor").contains(yml.getString(tp + "receive" + re + "." + ie + ".apply").toLowerCase())) {
                                        logger("wrong apply at: " + tp + "receive" + re + "." + ie + ".apply");
                                        continue;
                                    }
                                }
                                actions.add(new EnchantmentAction(ie, Enchantment.getByName(yml.getString(tp + "receive." + re + "." + ie + ".enchantment")),
                                        yml.isSet(tp + "receive." + re + "." + ie + ".amplifier") ? yml.getInt(tp + "receive." + re + "." + ie + ".amplifier")
                                                : 1, yml.getString(tp + "receive." + re + "." + ie + ".apply")));
                            }
                            break;
                        case "teamgenerator":
                            GeneratorAction generatorAction;
                            if (!yml.getConfigurationSection(tp + "receive." + re).getKeys(false).isEmpty()) {
                                generatorAction = new GeneratorAction(re);
                            } else {
                                continue;
                            }
                            //re = receive, tg = team generator
                            for (String tg : yml.getConfigurationSection(tp + "receive." + re).getKeys(false)) {
                                if (tg.equalsIgnoreCase("gold") || tg.equalsIgnoreCase("iron") || tg.equalsIgnoreCase("emerald")) {
                                    int x;
                                    if (yml.isSet(tp + "receive." + re + "." + tg + ".delay")) {
                                        try {
                                            x = yml.getInt(tp + "receive." + re + "." + tg + ".delay");
                                            generatorAction.setDelay(tg, x);
                                        } catch (Exception ex) {
                                            logger("delay must be an int at: " + tp + "receive." + re + "." + tg + ".delay");
                                            continue;
                                        }
                                    } else {
                                        logger("delay is not set at: " + tp + "receive." + re + "." + tg + ".delay");
                                        yml.set(tp + "receive." + re + "." + tg + ".delay", 3);
                                        save();
                                        continue;
                                    }
                                    if (yml.isSet(tp + ConfigPath.UPGRADES_TEAM_UPGRADE_RECEIVE_TEAM_GENERATOR_SPAWN_LIMIT.replace("%g%", tg).replace("%r%", re))) {
                                        generatorAction.setLimit(tg, yml.getInt(tp + ConfigPath.UPGRADES_TEAM_UPGRADE_RECEIVE_TEAM_GENERATOR_SPAWN_LIMIT.replace("%g%", tg).replace("%r%", re)));
                                    }
                                    if (yml.isSet(tp + "receive." + re + "." + tg + ".amount")) {
                                        try {
                                            x = yml.getInt(tp + "receive." + re + "." + tg + ".amount");
                                            generatorAction.setAmount(tg, x);
                                        } catch (Exception ex) {
                                            logger("amount must be an int at: " + tp + "receive." + re + "." + tg + ".amount");
                                            continue;
                                        }
                                    } else {
                                        logger("amount is not set at: " + tp + "receive." + re + "." + tg + ".amount");
                                        yml.set(tp + "receive." + re + "." + tg + ".amount", 1);
                                        save();
                                        continue;
                                    }
                                    if (generatorAction != null) {
                                        if (generatorAction.isSomethingLoaded()) {
                                            actions.add(generatorAction);
                                        }
                                    }
                                }
                            }
                            break;
                        case "enemybaseenter":
                            if (yml.isSet(tp + "receive." + re + ".announce")) {
                                String[] options = yml.getString(tp + "receive." + re + ".announce").replace(" ", "").split(",");
                                EnemyBaseEnterAction enemyBaseEnterAction = null;
                                if (options.length != 0) {
                                    enemyBaseEnterAction = new EnemyBaseEnterAction(re);
                                }
                                for (String o : options) {
                                    if (o.isEmpty()) continue;
                                    if (o.equalsIgnoreCase("chat")) {
                                        enemyBaseEnterAction.setChat(true);
                                    } else if (o.equalsIgnoreCase("title")) {
                                        enemyBaseEnterAction.setTitle(true);
                                    } else if (o.equalsIgnoreCase("subtitle")) {
                                        enemyBaseEnterAction.setSubtitle(true);
                                    } else if (o.equalsIgnoreCase("action")) {
                                        enemyBaseEnterAction.setAction(true);
                                    } else {
                                        logger(o + " is not an option at: " + tp + "receive." + re + ".announce");
                                    }
                                }
                                if (enemyBaseEnterAction != null) {
                                    if (enemyBaseEnterAction.isSomethingTrue()) {
                                        actions.add(enemyBaseEnterAction);
                                    } else {
                                        logger("ignoring announce at: " + tp + "receive." + re + " because there is no correct option");
                                    }
                                }
                            } else {
                                logger("announce not set at: " + tp + "receive." + re);
                                yml.set(tp + "receive." + re, "chat");
                                save();
                                continue;
                            }
                            break;
                        case "gameend":
                            if (yml.isSet(tp + "receive." + re + ".bonusDragon")) {
                                try {
                                    yml.getInt(tp + "receive." + re + ".bonusDragon");
                                } catch (Exception e) {
                                    logger("bonusDragon must be an int at: " + tp + "receive." + re);
                                    continue;
                                }
                                actions.add(new GameEndAction(re, yml.getInt(tp + "receive." + re + ".bonusDragon")));
                            } else {
                                logger("bonusDragon not set at: " + tp + "receive." + re);
                                yml.set(tp + "receive." + re + ".bonusDragon", 1);
                                save();
                                continue;
                            }
                            break;
                    }
                }
                if (!actions.isEmpty()) {
                    ItemStack i = nms.createItemStack(yml.getString(tp + "displayItem.material"), yml.isSet(tp + "displayItem.amount") ? yml.getInt(tp + "displayItem.amount") : 1,
                            (short) (yml.isSet(tp + "displayItem.data") ? yml.getInt(tp + "displayItem.data") : 0));
                    if (yml.isSet(tp + "displayItem.enchanted")) {
                        if (yml.getBoolean(tp + "displayItem.enchanted")) {
                            ItemMeta im = i.getItemMeta();
                            im.addEnchant(Enchantment.LURE, 1, true);
                            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            i.setItemMeta(im);
                        }
                    }
                    tiers.add(new UpgradeTier(tier, actions, yml.getInt(tp + "cost"), yml.getString(tp + "currency"),
                            i));
                }
            }
            if (!tiers.isEmpty()) {
                teamUpgrades.add(new TeamUpgrade(tu, yml.getInt(path.isEmpty() ? tu + ".slot" : path + "." + tu + ".slot"), tiers));
            }
        }
        if (!teamUpgrades.isEmpty()) {
            new UpgradeGroup(path, teamUpgrades);
        }
    }

    private void logger(String message) {
        plugin.getLogger().severe("Team Upgrades: " + message);
    }

    public List<String> l(String path) {
        return yml.getStringList(path).stream().map(s -> s.replace("&", "§")).collect(Collectors.toList());
    }*/
}
