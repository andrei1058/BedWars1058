package com.andrei1058.bedwars.configuration;

import com.andrei1058.bedwars.upgrades.*;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.andrei1058.bedwars.Main.plugin;
import static com.andrei1058.bedwars.configuration.Language.saveIfNotExists;

public class UpgradesManager {

    private YamlConfiguration yml;
    private File file;

    public UpgradesManager(String name, String dir) {
        File d = new File(dir);
        if (!d.exists()) {
            d.mkdir();
        }
        file = new File(dir + "/" + name + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                yml = YamlConfiguration.loadConfiguration(file);
                yml.options().copyDefaults(true);
                plugin.getLogger().info("Creating " + dir + "/" + name + ".yml");


            } catch (IOException e) {
                e.printStackTrace();
            }

            /** Generators Upgrade **/
            yml.set("default.generators.slot", 11);

            yml.set("default.generators.tier1.displayItem.material", "FURNACE");
            yml.set("default.generators.tier1.displayItem.data", 0);
            yml.set("default.generators.tier1.displayItem.amount", 1);
            yml.set("default.generators.tier1.displayItem.enchanted", false);
            yml.set("default.generators.tier1.currency", "diamond");
            yml.set("default.generators.tier1.cost", 4);
            yml.set("default.generators.tier1.receive.teamGenerator.iron.delay", 2);
            yml.set("default.generators.tier1.receive.teamGenerator.iron.amount", 2);
            yml.set("default.generators.tier1.receive.teamGenerator.gold.delay", 3);
            yml.set("default.generators.tier1.receive.teamGenerator.gold.amount", 1);

            yml.set("default.generators.tier2.displayItem.material", "FURNACE");
            yml.set("default.generators.tier2.displayItem.data", 0);
            yml.set("default.generators.tier2.displayItem.amount", 1);
            yml.set("default.generators.tier2.displayItem.enchanted", false);
            yml.set("default.generators.tier2.currency", "diamond");
            yml.set("default.generators.tier2.cost", 8);
            yml.set("default.generators.tier2.receive.teamGenerator.iron.delay", 1);
            yml.set("default.generators.tier2.receive.teamGenerator.iron.amount", 2);
            yml.set("default.generators.tier2.receive.teamGenerator.gold.delay", 3);
            yml.set("default.generators.tier2.receive.teamGenerator.gold.amount", 2);

            yml.set("default.generators.tier3.displayItem.material", "FURNACE");
            yml.set("default.generators.tier3.displayItem.data", 0);
            yml.set("default.generators.tier3.displayItem.amount", 1);
            yml.set("default.generators.tier3.displayItem.enchanted", false);
            yml.set("default.generators.tier3.currency", "diamond");
            yml.set("default.generators.tier3.cost", 12);
            yml.set("default.generators.tier3.receive.teamGenerator.iron.delay", 1);
            yml.set("default.generators.tier3.receive.teamGenerator.iron.amount", 2);
            yml.set("default.generators.tier3.receive.teamGenerator.gold.delay", 2);
            yml.set("default.generators.tier3.receive.teamGenerator.gold.amount", 2);
            yml.set("default.generators.tier3.receive.teamGenerator.emerald.delay", 10);
            yml.set("default.generators.tier3.receive.teamGenerator.emerald.amount", 1);

            /** Maniac Miner Upgrades*/
            yml.set("default.maniacMiner.slot", 12);
            yml.set("default.maniacMiner.tier1.displayItem.material", "GOLD_AXE");
            yml.set("default.maniacMiner.tier1.displayItem.data", 0);
            yml.set("default.maniacMiner.tier1.displayItem.amount", 1);
            yml.set("default.maniacMiner.tier1.displayItem.enchanted", false);
            yml.set("default.maniacMiner.tier1.currency", "diamond");
            yml.set("default.maniacMiner.tier1.cost", 4);
            yml.set("default.maniacMiner.tier1.receive.playerEffect.haste1.effect", "FAST_DIGGING"); //todo asta s-ar putea sa nu fie compatibila cu 1.9+
            yml.set("default.maniacMiner.tier1.receive.playerEffect.haste1.amplifier", 0);
            yml.set("default.maniacMiner.tier1.receive.playerEffect.haste1.apply", "members");

            yml.set("settings.startValues.solo.ironGeneratorDelay", 2);
            yml.set("settings.startValues.solo.ironGeneratorAmount", 1);
            yml.set("settings.startValues.solo.goldGeneratorDelay", 3);
            yml.set("settings.startValues.solo.goldGeneratorAmount", 1);

            /** Sharpened Swords Upgrade*/
            yml.set("default.sharpSword.slot", 13);
            yml.set("default.sharpSword.tier1.displayItem.material", "IRON_SWORD");
            yml.set("default.sharpSword.tier1.displayItem.data", 0);
            yml.set("default.sharpSword.tier1.displayItem.amount", 1);
            yml.set("default.sharpSword.tier1.displayItem.enchanted", false);
            yml.set("default.sharpSword.tier1.currency", "diamond");
            yml.set("default.sharpSword.tier1.cost", 4);
            yml.set("default.sharpSword.tier1.receive.itemEnchantment.sharp.enchantment", "DAMAGE_ALL");
            yml.set("default.sharpSword.tier1.receive.itemEnchantment.sharp.amplifier", 1);
            yml.set("default.sharpSword.tier1.receive.itemEnchantment.sharp.apply", "sword"); //sword, bow, armor

            /** Reinforced Armor Upgrade*/
            yml.set("default.reinforced.slot", 14);
            yml.set("default.reinforced.tier1.displayItem.material", "IRON_CHESTPLATE");
            yml.set("default.reinforced.tier1.displayItem.data", 0);
            yml.set("default.reinforced.tier1.displayItem.amount", 1);
            yml.set("default.reinforced.tier1.displayItem.enchanted", false);
            yml.set("default.reinforced.tier1.currency", "diamond");
            yml.set("default.reinforced.tier1.cost", 2);
            yml.set("default.reinforced.tier1.receive.itemEnchantment.sharp.enchantment", "PROTECTION_ENVIRONMENTAL");
            yml.set("default.reinforced.tier1.receive.itemEnchantment.sharp.amplifier", 1);
            yml.set("default.reinforced.tier1.receive.itemEnchantment.sharp.apply", "armor"); //sword, bow, armor

            /** It's a trap Upgrade*/
            yml.set("default.trap.slot", 15);
            yml.set("default.trap.tier1.displayItem.material", "TRIPWIRE_HOOK");
            yml.set("default.trap.tier1.displayItem.data", 0);
            yml.set("default.trap.tier1.displayItem.amount", 1);
            yml.set("default.trap.tier1.displayItem.enchanted", false);
            yml.set("default.trap.tier1.currency", "diamond");
            yml.set("default.trap.tier1.cost", 1);
            yml.set("default.trap.tier1.receive.enemyBaseEnter.announce", "title, subtitle, action, chat");
            yml.set("default.trap.tier1.receive.playerEffect.blindness.effect", "BLINDNESS");
            yml.set("default.trap.tier1.receive.playerEffect.blindness.amplifier", 1);
            yml.set("default.trap.tier1.receive.playerEffect.blindness.apply", "enemyBaseEnter");
            yml.set("default.trap.tier1.receive.playerEffect.slowness.effect", "SLOW");
            yml.set("default.trap.tier1.receive.playerEffect.slowness.amplifier", 0);
            yml.set("default.trap.tier1.receive.playerEffect.slowness.apply", "enemyBaseEnter");

            /** Mining fatigue trap Upgrade */
            yml.set("default.miningFatigue.slot", 20);
            yml.set("default.miningFatigue.tier1.displayItem.material", "IRON_PICKAXE");
            yml.set("default.miningFatigue.tier1.displayItem.data", 0);
            yml.set("default.miningFatigue.tier1.displayItem.amount", 1);
            yml.set("default.miningFatigue.tier1.displayItem.enchanted", false);
            yml.set("default.miningFatigue.tier1.currency", "diamond");
            yml.set("default.miningFatigue.tier1.cost", 2);
            yml.set("default.miningFatigue.tier1.receive.enemyBaseEnter.announce", "title, subtitle");
            yml.set("default.miningFatigue.tier1.receive.playerEffect.fatigue.effect", "SLOW_DIGGING");
            yml.set("default.miningFatigue.tier1.receive.playerEffect.fatigue.amplifier", 0);
            yml.set("default.miningFatigue.tier1.receive.playerEffect.fatigue.apply", "enemyBaseEnter");
            yml.set("default.miningFatigue.tier1.receive.playerEffect.fatigue.duration", 10);

            /** Health pool Upgrade */
            yml.set("default.healPool.slot", 21);
            yml.set("default.healPool.tier1.displayItem.material", "BEACON");
            yml.set("default.healPool.tier1.displayItem.data", 0);
            yml.set("default.healPool.tier1.displayItem.amount", 1);
            yml.set("default.healPool.tier1.displayItem.enchanted", false);
            yml.set("default.healPool.tier1.currency", "diamond");
            yml.set("default.healPool.tier1.cost", 1);
            yml.set("default.healPool.tier1.receive.playerEffect.fatigue.effect", "HEALTH_BOOST");
            yml.set("default.healPool.tier1.receive.playerEffect.fatigue.amplifier", 0);
            yml.set("default.healPool.tier1.receive.playerEffect.fatigue.apply", "base");
        }
        if (yml == null) {
            yml = YamlConfiguration.loadConfiguration(file);
            yml.options().copyDefaults(true);
            yml.addDefault("settings.invSize", 36);
            yml.addDefault("settings.startValues.default.ironGeneratorDelay", 2);
            yml.addDefault("settings.startValues.default.ironGeneratorAmount", 2);
            yml.addDefault("settings.startValues.default.goldGeneratorDelay", 6);
            yml.addDefault("settings.startValues.default.goldGeneratorAmount", 2);
            yml.addDefault("settings.stackItems", false);
        } else {
            yml.options().copyDefaults(true);
            yml.addDefault("settings.invSize", 36);
        }
        save();
        if (yml != null){
            for (String s : yml.getConfigurationSection("").getKeys(false)){
                if (s.equalsIgnoreCase("settings")) continue;
                loadTeamUpgrades(s);
            }
        }
    }

    public void loadTeamUpgrades(String path){
        saveIfNotExists("upgrades."+path+".name", "&8Team Upgrades");
        List<TeamUpgrade> teamUpgrades = new ArrayList<>();
        for (String tu : yml.getConfigurationSection(path).getKeys(false)){
            if (tu.equalsIgnoreCase("settings")) continue;
            if (yml.get(path.isEmpty() ? tu+".slot" : path+"."+tu+".slot") == null){
                logger("slot not set for "+tu);
                continue;
            }
            if (yml.getConfigurationSection((path.isEmpty() ? tu : path+"."+tu)).getKeys(false).isEmpty()){
                logger("no tier defined for "+tu);
                continue;
            }
            List<UpgradeTier> tiers = new ArrayList<>();
            List<UpgradeAction> actions;
            for (String tier : yml.getConfigurationSection(path+"."+tu).getKeys(false)){
                String tp = path.isEmpty() ? tu+"."+tier+"." : path+"."+tu+"."+tier+".";
                if (tier.equalsIgnoreCase("slot")) continue;
                saveIfNotExists("upgrades."+tp+"name", "&cA nice name");
                saveIfNotExists("upgrades."+tp+"lore", Arrays.asList("&7A nice description here", "", "&7Cost:&b {cost} {currency}", "", "{loreFooter}"));
                if (yml.isSet(tp+"displayItem")){
                    if (yml.isSet(tp+"displayItem.material")){
                        try {
                            Material.valueOf(yml.getString(tp+"displayItem.material"));
                        } catch (Exception ex){
                            logger("Invalid material at "+tp+"displayItem.material");
                            continue;
                        }
                    } else {
                        yml.set(tp+".displayItem.material", "STONE");
                        save();
                        logger("material not set at "+tp+"displayItem");
                        continue;
                    }
                } else {
                    logger("displayItem not set for tier "+tier+" at "+tp);
                    continue;
                }
                if (yml.isSet(tp+"currency")){
                    List<String> currency = Arrays.asList("diamond", "emerald", "vault", "iron", "gold");
                    if (!currency.contains(yml.getString(tp+"currency").toLowerCase())){
                        logger("invalid currency type at "+tp);
                        logger("available currencies: diamond, emerald, vault, iron, gold");
                        continue;
                    }
                } else {
                    yml.set("currency", "vault");
                    save();
                    continue;
                }
                if (yml.isSet(tp+"cost")){
                    try {
                        yml.getInt(tp+"cost");
                    } catch (Exception ex){
                        logger("cost must be an integer at "+tp);
                        continue;
                    }
                } else {
                    logger("cost not set for "+tp);
                    continue;
                }
                if (yml.get(tp+"receive") == null) continue;
                if (yml.getConfigurationSection(tp+"receive").getKeys(false).isEmpty()){
                    logger("receive is empty at "+tp);
                    continue;
                }
                actions = new ArrayList<>();
                for (String re : yml.getConfigurationSection(tp+"receive").getKeys(false)){
                    switch (re.toLowerCase()){
                        case "playereffect":
                            for (String pe : yml.getConfigurationSection(tp+"receive."+re).getKeys(false)){
                                if (yml.isSet(tp+"receive."+re+"."+pe+".effect")){
                                    try {
                                        PotionEffectType.getByName(yml.getString(tp+"receive."+re+"."+pe+".effect"));
                                    } catch (Exception ex){
                                        logger("wrong potion at: "+tp+"receive"+re+"."+pe+".effect");
                                        continue;
                                    }
                                } else {
                                    logger("potion not set at: "+tp+"receive"+re+"."+pe+".effect");
                                    yml.set(tp+"receive"+re+"."+pe+".effect", "JUMP");
                                    save();
                                    continue;
                                }
                                if (yml.isSet(tp+"receive"+re+"."+pe+".apply")){
                                    if (!Arrays.asList("members", "base", "enemyBaseEnter").contains(yml.getString(tp+"receive"+re+"."+pe+".apply").toLowerCase())){
                                        logger("wrong apply at: "+tp+"receive"+re+"."+pe+".apply");
                                        continue;
                                    }
                                }
                                actions.add(new EffectAction(pe, PotionEffectType.getByName(yml.getString(tp+"receive."+re+"."+pe+".effect")),
                                        yml.isSet(tp+"receive."+re+"."+pe+".amplifier") ? yml.getInt(tp+"receive."+re+"."+pe+".amplifier")
                                        : 1, yml.getString(tp+"receive."+re+"."+pe+".apply"), yml.isSet(tp+"receive."+re+"."+pe+".duration") ?
                                yml.getInt(tp+"receive."+re+"."+pe+".duration")*20 : -1));
                            }
                            break;
                        case "itemenchantment":
                            for (String ie : yml.getConfigurationSection(tp+"receive."+re).getKeys(false)){
                                if (yml.isSet(tp+"receive."+re+"."+ie+".enchantment")){
                                    try {
                                        Enchantment.getByName(yml.getString(tp+"receive."+re+"."+ie+".enchantment"));
                                    } catch (Exception ex){
                                        logger("wrong enchantment at: "+tp+"receive"+re+"."+ie+".enchantment");
                                        continue;
                                    }
                                } else {
                                    logger("enchantment not set at: "+tp+"receive."+re+"."+ie+".enchantment");
                                    yml.set(tp+"receive."+re+"."+ie+".enchantment", "DIG_SPEED");
                                    save();
                                    continue;
                                }
                                if (yml.isSet(tp+"receive"+re+"."+ie+".apply")){
                                    if (!Arrays.asList("sword", "bow", "armor").contains(yml.getString(tp+"receive"+re+"."+ie+".apply").toLowerCase())){
                                        logger("wrong apply at: "+tp+"receive"+re+"."+ie+".apply");
                                        continue;
                                    }
                                }
                                actions.add(new EnchantmentAction(ie, Enchantment.getByName(yml.getString(tp+"receive."+re+"."+ie+".enchantment")),
                                        yml.isSet(tp+"receive."+re+"."+ie+".amplifier") ? yml.getInt(tp+"receive."+re+"."+ie+".amplifier")
                                        : 1, yml.getString(tp+"receive."+re+"."+ie+".apply")));
                            }
                            break;
                        case "teamgenerator":
                            GeneratorAction generatorAction;
                            if (!yml.getConfigurationSection(tp+"receive."+re).getKeys(false).isEmpty()){
                                generatorAction = new GeneratorAction(re);
                            } else {
                                continue;
                            }
                            for (String tg : yml.getConfigurationSection(tp+"receive."+re).getKeys(false)){
                                if (tg.equalsIgnoreCase("gold") || tg.equalsIgnoreCase("iron") || tg.equalsIgnoreCase("emerald")){
                                    int x = 0;
                                    if (yml.isSet(tp+"receive."+re+"."+tg+".delay")){
                                        try {
                                            x = yml.getInt(tp+"receive."+re+"."+tg+".delay");
                                            generatorAction.setDelay(tg,x);
                                        } catch (Exception ex){
                                            logger("delay must be an int at: "+tp+"receive."+re+"."+tg+".delay");
                                            continue;
                                        }
                                    } else {
                                        logger("delay is not set at: "+tp+"receive."+re+"."+tg+".delay");
                                        yml.set(tp+"receive."+re+"."+tg+".delay", 3);
                                        save();
                                        continue;
                                    }
                                    if (yml.isSet(tp+"receive."+re+"."+tg+".amount")){
                                        try {
                                            x = yml.getInt(tp+"receive."+re+"."+tg+".amount");
                                            generatorAction.setAmount(tg,x);
                                        } catch (Exception ex){
                                            logger("amount must be an int at: "+tp+"receive."+re+"."+tg+".amount");
                                            continue;
                                        }
                                    } else {
                                        logger("amount is not set at: "+tp+"receive."+re+"."+tg+".amount");
                                        yml.set(tp+"receive."+re+"."+tg+".amount", 1);
                                        save();
                                        continue;
                                    }
                                    if (generatorAction != null){
                                        if (generatorAction.isSomethingLoaded()){
                                            actions.add(generatorAction);
                                        }
                                    }
                                }
                            }
                            break;
                        case "enemybaseenter":
                            if (yml.isSet(tp+"receive."+re+".announce")){
                                String[] options = yml.getString(tp+"receive."+re+".announce").replace(" ", "").split(",");
                                EnemyBaseEnterAction enemyBaseEnterAction = null;
                                if (options.length != 0){
                                    enemyBaseEnterAction = new EnemyBaseEnterAction(re);
                                }
                                for (String o : options){
                                    if (o.isEmpty()) continue;
                                    if (o.equalsIgnoreCase("chat")){
                                        enemyBaseEnterAction.setChat(true);
                                    } else if (o.equalsIgnoreCase("title")){
                                        enemyBaseEnterAction.setTitle(true);
                                    } else if (o.equalsIgnoreCase("subtitle")){
                                        enemyBaseEnterAction.setSubtitle(true);
                                    } else if (o.equalsIgnoreCase("action")){
                                        enemyBaseEnterAction.setAction(true);
                                    } else {
                                        logger(o+" is not an option at: "+tp+"receive."+re+".announce");
                                    }
                                }
                                if (enemyBaseEnterAction != null){
                                    if (enemyBaseEnterAction.isSomethingTrue()){
                                        actions.add(enemyBaseEnterAction);
                                    } else {
                                        logger("ignoring announce at: "+tp+"receive."+re+" because there is no correct option");
                                    }
                                }
                            } else {
                                logger("announce not set at: "+tp+"receive."+re);
                                yml.set(tp+"receive."+re, "chat");
                                save();
                                continue;
                            }
                            break;
                        case "gameend":
                            if (yml.isSet(tp+"receive."+re+".bonusDragon")){
                                try {
                                    yml.getInt(tp+"receive."+re+".bonusDragon");
                                } catch (Exception e){
                                    logger("bonusDragon must be an int at: "+tp+"receive."+re);
                                    continue;
                                }
                                actions.add(new GameEndAction(re, yml.getInt(tp+"receive."+re+".bonusDragon")));
                            } else {
                                logger("bonusDragon not set at: "+tp+"receive."+re);
                                yml.set(tp+"receive."+re+".bonusDragon", 1);
                                save();
                                continue;
                            }
                            break;
                    }
                }
                if (!actions.isEmpty()){
                    ItemStack i = new ItemStack(Material.valueOf(yml.getString(tp+"displayItem.material")), yml.isSet(tp+"displayItem.amount") ? yml.getInt(tp+"displayItem.amount") : 1,
                            (short) (yml.isSet(tp+"displayItem.data") ? yml.getInt(tp+"displayItem.data") : 0));
                    if (yml.isSet(tp+"displayItem.enchanted")){
                        if (yml.getBoolean(tp+"displayItem.enchanted")){
                            ItemMeta im = i.getItemMeta();
                            im.addEnchant(Enchantment.LURE, 1, true);
                            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            i.setItemMeta(im);
                        }
                    }
                    tiers.add(new UpgradeTier(tier, actions, yml.getInt(tp+"cost"), yml.getString(tp+"currency"),
                            i));
                }
            }
            if (!tiers.isEmpty()){
                teamUpgrades.add(new TeamUpgrade(tu, yml.getInt(path.isEmpty() ? tu+".slot" : path+"."+tu+".slot"), tiers));
            }
        }
        if (!teamUpgrades.isEmpty()){
            new UpgradeGroup(path, teamUpgrades);
        }
    }


    public void set(String path, Object value) {
        yml.set(path, value);
        save();
    }

    public void save() {
        try {
            yml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getYml() {
        return yml;
    }

    private void logger(String message){
        plugin.getLogger().severe("Team Upgrades: "+message);
    }

    public List<String> l(String path) {
        return yml.getStringList(path).stream().map(s -> s.replace("&", "§")).collect(Collectors.toList());
    }

    public boolean getBoolean(String path) {
        return yml.getBoolean(path);
    }

    public int getInt(String path) {
        return yml.getInt(path);
    }
}
