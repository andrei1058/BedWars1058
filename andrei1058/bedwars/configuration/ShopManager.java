package com.andrei1058.bedwars.configuration;

import com.andrei1058.bedwars.shop.*;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.andrei1058.bedwars.Main.plugin;

public class ShopManager {

    private YamlConfiguration yml;
    private File file;

    public ShopManager(String name, String dir) {
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

                setMainStuff("main.invContents.armor", 11, "CHAINMAIL_BOOTS", 0, 1, false);
                yml.addDefault("main.invContents.armor.invSize", 36);
                //armor
                setCategoryWithBuy("main.invContents.armor.invContents.chainmail", 10, "CHAINMAIL_BOOTS", 0, 1, 40, "iron", false);
                addBuyItem("main.invContents.armor.invContents.chainmail.buyItems.boots", "CHAINMAIL_BOOTS", 0, 1, "", true, true);
                addBuyItem("main.invContents.armor.invContents.chainmail.buyItems.leggings", "CHAINMAIL_LEGGINGS", 0, 1, "", true, true);

                setCategoryWithBuy("main.invContents.armor.invContents.iron", 11, "IRON_BOOTS", 0, 1, 12, "gold", false);
                addBuyItem("main.invContents.armor.invContents.iron.buyItems.boots", "IRON_BOOTS", 0, 1, "", true, true);
                addBuyItem("main.invContents.armor.invContents.iron.buyItems.leggings", "IRON_LEGGINGS", 0, 1, "", true, true);

                setCategoryWithBuy("main.invContents.armor.invContents.diamond", 12, "DIAMOND_BOOTS", 0, 1, 6, "emerald", false);
                addBuyItem("main.invContents.armor.invContents.diamond.buyItems.boots", "DIAMOND_BOOTS", 0, 1, "", true, true);
                addBuyItem("main.invContents.armor.invContents.diamond.buyItems.leggings", "DIAMOND_LEGGINGS", 0, 1, "", true, true);

                setCategoryWithBuy("main.invContents.armor.invContents.back", 31, "ARROW", 0, 1, 0, "iron", false);
                set("main.invContents.armor.invContents.back.buyItems", new ArrayList<>());

                //melee
                setMainStuff("main.invContents.melee", 12, "GOLD_SWORD", 0, 1, false);
                yml.addDefault("main.invContents.melee.invSize", 36);

                setCategoryWithBuy("main.invContents.melee.invContents.stone", 10, "STONE_SWORD", 0, 1, 10, "iron", false);
                addBuyItem("main.invContents.melee.invContents.stone.buyItems.sword", "STONE_SWORD", 0, 1, "", false, false);

                setCategoryWithBuy("main.invContents.melee.invContents.iron", 11, "IRON_SWORD", 0, 1, 7, "gold", false);
                addBuyItem("main.invContents.melee.invContents.iron.buyItems.sword", "IRON_SWORD", 0, 1, "", false, false);

                setCategoryWithBuy("main.invContents.melee.invContents.diamond", 12, "DIAMOND_SWORD", 0, 1, 4, "emerald", false);
                addBuyItem("main.invContents.melee.invContents.diamond.buyItems.sword", "DIAMOND_SWORD", 0, 1, "", false, false);

                setCategoryWithBuy("main.invContents.melee.invContents.stick", 13, "STICK", 0, 1, 10, "gold", true);
                addBuyItem("main.invContents.melee.invContents.stick.buyItems.stick", "STICK", 0, 1, "KNOCKBACK 1", false, false);

                setCategoryWithBuy("main.invContents.melee.invContents.back", 31, "ARROW", 0, 1, 0, "iron", false);
                set("main.invContents.melee.invContents.back.buyItems", new ArrayList<>());

                //blocks
                setMainStuff("main.invContents.blocks", 13, "STAINED_CLAY", 1, 1, false);
                yml.addDefault("main.invContents.blocks.invSize", 36);

                setCategoryWithBuy("main.invContents.blocks.invContents.wool", 10, "WOOL", 0, 16, 4, "iron", false);
                addBuyItem("main.invContents.blocks.invContents.wool.buyItems.wool", "WOOL", 0, 16, "", false, false);

                setCategoryWithBuy("main.invContents.blocks.invContents.clay", 11, "STAINED_CLAY", 1, 16, 12, "iron", false);
                addBuyItem("main.invContents.blocks.invContents.clay.buyItems.clay", "STAINED_CLAY", 1, 16, "", false, false);

                setCategoryWithBuy("main.invContents.blocks.invContents.glass", 12, "GLASS", 0, 4, 12, "iron", false);
                addBuyItem("main.invContents.blocks.invContents.glass.buyItems.glass", "STAINED_GLASS", 0, 4, "", false, false);

                setCategoryWithBuy("main.invContents.blocks.invContents.stone", 13, "ENDER_STONE", 0, 16, 24, "iron", false);
                addBuyItem("main.invContents.blocks.invContents.stone.buyItems.stone", "ENDER_STONE", 0, 16, "", false, false);

                setCategoryWithBuy("main.invContents.blocks.invContents.ladder", 14, "LADDER", 0, 16, 4, "iron", false);
                addBuyItem("main.invContents.blocks.invContents.ladder.buyItems.ladder", "LADDER", 0, 16, "", false, false);

                setCategoryWithBuy("main.invContents.blocks.invContents.wood", 15, "WOOD", 0, 16, 4, "gold", false);
                addBuyItem("main.invContents.blocks.invContents.wood.buyItems.wood", "WOOD", 0, 16, "", false, false);

                setCategoryWithBuy("main.invContents.blocks.invContents.obsidian", 16, "OBSIDIAN", 0, 4, 4, "emerald", false);
                addBuyItem("main.invContents.blocks.invContents.obsidian.buyItems.obsidian", "OBSIDIAN", 0, 4, "", false, false);

                setCategoryWithBuy("main.invContents.blocks.invContents.back", 31, "ARROW", 0, 1, 0, "iron", false);
                set("main.invContents.blocks.invContents.back.buyItems", new ArrayList<>());

                //ranged
                setMainStuff("main.invContents.ranged", 14, "BOW", 0, 1, false);
                yml.addDefault("main.invContents.ranged.invSize", 36);

                setCategoryWithBuy("main.invContents.ranged.invContents.arrow", 10, "ARROW", 0, 8, 2, "gold", false);
                addBuyItem("main.invContents.ranged.invContents.arrow.buyItems.arrows", "ARROW", 0, 8, "", false, false);

                setCategoryWithBuy("main.invContents.ranged.invContents.bow", 11, "BOW", 0, 1, 12, "gold", false);
                addBuyItem("main.invContents.ranged.invContents.bow.buyItems.bow", "BOW", 0, 1, "", false, false);

                setCategoryWithBuy("main.invContents.ranged.invContents.bow2", 12, "BOW", 0, 1, 24, "gold", true);
                addBuyItem("main.invContents.ranged.invContents.bow2.buyItems.bow", "BOW", 0, 1, "ARROW_DAMAGE 1", false, false);

                setCategoryWithBuy("main.invContents.ranged.invContents.bow3", 13, "BOW", 0, 1, 6, "emerald", true);
                addBuyItem("main.invContents.ranged.invContents.bow3.buyItems.bow", "BOW", 0, 1, "ARROW_DAMAGE 1,ARROW_KNOCKBACK 1", false, false);

                setCategoryWithBuy("main.invContents.ranged.invContents.back", 31, "ARROW", 0, 1, 0, "iron", false);
                set("main.invContents.ranged.invContents.back.buyItems", new ArrayList<>());

                //tools
                setMainStuff("main.invContents.tools", 20, "STONE_PICKAXE", 0, 1, false);
                yml.addDefault("main.invContents.tools.invSize", 36);

                setCategoryWithBuy("main.invContents.tools.invContents.shears", 10, "SHEARS", 0, 1, 30, "iron", false);
                addBuyItem("main.invContents.tools.invContents.shears.buyItems.shears", "SHEARS", 0, 1, "", false, false);

                setCategoryWithBuy("main.invContents.tools.invContents.pick", 11, "WOOD_PICKAXE", 0, 1, 10, "iron", false);
                addBuyItem("main.invContents.tools.invContents.pick.buyItems.pick", "WOOD_PICKAXE", 0, 1, "DIG_SPEED 1", false, false);

                setCategoryWithBuy("main.invContents.tools.invContents.axe", 12, "WOOD_AXE", 0, 1, 10, "iron", false);
                addBuyItem("main.invContents.tools.invContents.axe.buyItems.axe", "WOOD_AXE", 0, 1, "DIG_SPEED 1", false, false);

                setCategoryWithBuy("main.invContents.tools.invContents.back", 31, "ARROW", 0, 1, 0, "iron", false);
                set("main.invContents.tools.invContents.back.buyItems", new ArrayList<>());

                //potions
                setMainStuff("main.invContents.potions", 21, "BREWING_STAND_ITEM", 0, 1, false);
                yml.addDefault("main.invContents.potions.invSize", 36);

                setCategoryWithBuy("main.invContents.potions.invContents.potion1", 10, "POTION", 0, 1, 1, "emerald", false);
                addBuyItem("main.invContents.potions.invContents.potion1.buyItems.potion", "POTION", 0, 1, "", false, false);
                set("main.invContents.potions.invContents.potion1.buyItems.potion.potionEffect", "SPEED 30 2");

                setCategoryWithBuy("main.invContents.potions.invContents.potion2", 11, "POTION", 0, 1, 1, "emerald", false);
                addBuyItem("main.invContents.potions.invContents.potion2.buyItems.potion", "POTION", 0, 1, "", false, false);
                set("main.invContents.potions.invContents.potion2.buyItems.potion.potionEffect", "JUMP 30 5");

                setCategoryWithBuy("main.invContents.potions.invContents.potion3", 12, "POTION", 0, 1, 1, "emerald", false);
                addBuyItem("main.invContents.potions.invContents.potion3.buyItems.potion", "POTION", 0, 1, "", false, false);
                set("main.invContents.potions.invContents.potion3.buyItems.potion.potionEffect", "INVISIBILITY 45 1");

                setCategoryWithBuy("main.invContents.potions.invContents.back", 31, "ARROW", 0, 1, 0, "iron", false);
                set("main.invContents.potions.invContents.back.buyItems", new ArrayList<>());

                //utility
                setMainStuff("main.invContents.utility", 22, "TNT", 0, 1, false);
                yml.addDefault("main.invContents.utility.invSize", 36);

                setCategoryWithBuy("main.invContents.utility.invContents.apple", 10, "GOLDEN_APPLE", 0, 1, 3, "gold", false);
                addBuyItem("main.invContents.utility.invContents.apple.buyItems.item", "GOLDEN_APPLE", 0, 1, "", false, false);

                setCategoryWithBuy("main.invContents.utility.invContents.sball", 11, "SNOW_BALL", 0, 1, 50, "iron", false);
                addBuyItem("main.invContents.utility.invContents.sball.buyItems.item", "SNOW_BALL", 0, 1, "", false, false);

                setCategoryWithBuy("main.invContents.utility.invContents.monsteregg", 12, "MONSTER_EGG", 0, 1, 120, "iron", false);
                addBuyItem("main.invContents.utility.invContents.monsteregg.buyItems.item", "MONSTER_EGG", 0, 1, "", false, false);

                setCategoryWithBuy("main.invContents.utility.invContents.fireball", 13, "FIREBALL", 0, 1, 50, "iron", false);
                addBuyItem("main.invContents.utility.invContents.fireball.buyItems.item", "FIREBALL", 0, 1, "", false, false);

                setCategoryWithBuy("main.invContents.utility.invContents.tnt", 14, "TNT", 0, 1, 8, "gold", false);
                addBuyItem("main.invContents.utility.invContents.tnt.buyItems.item", "TNT", 0, 1, "", false, false);

                setCategoryWithBuy("main.invContents.utility.invContents.enderpearl", 15, "ENDER_PEARL", 0, 1, 4, "emerald", false);
                addBuyItem("main.invContents.utility.invContents.enderpearl.buyItems.item", "ENDER_PEARL", 0, 1, "", false, false);

                setCategoryWithBuy("main.invContents.utility.invContents.bucket", 16, "WATER_BUCKET", 0, 1, 1, "emerald", false);
                addBuyItem("main.invContents.utility.invContents.bucket.buyItems.item", "WATER_BUCKET", 0, 1, "", false, false);

                /*setCategoryWithBuy("main.invContents.utility.invContents.egg", 19, "EGG", 0, 1, 4, "emerald", false);
                addBuyItem("main.invContents.utility.invContents.egg.buyItems.item", "EGG", 0, 1, "", false, false);
                */

                setCategoryWithBuy("main.invContents.utility.invContents.back", 31, "ARROW", 0, 1, 0, "iron", false);
                set("main.invContents.utility.invContents.back.buyItems", new ArrayList<>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (yml == null) {
            yml = YamlConfiguration.loadConfiguration(file);
            yml.options().copyDefaults(true);
        }
        yml.addDefault("main.invSize", 36);
        //yml.addDefault("main.invContents.favourites.enable", true);
        //setMainStuff("main.invContents.favourites", 16, "NETHER_STAR", 0, 1, false);
        //yml.addDefault("main.invContents.recommended.enable", true);
        //setMainStuff("main.invContents.recommended", 26, "BOOK", 0, 1, true);

        yml.addDefault("utilities.silverfish.material", "SNOW_BALL");
        yml.addDefault("utilities.silverfish.data", 0);
        yml.addDefault("utilities.silverfish.enable", true);
        yml.addDefault("utilities.silverfish.health", 8.0);
        yml.addDefault("utilities.silverfish.damage", 4.0);
        yml.addDefault("utilities.silverfish.speed", 0.25);

        yml.addDefault("utilities.ironGolem.material", "MONSTER_EGG");
        yml.addDefault("utilities.ironGolem.data", 0);
        yml.addDefault("utilities.ironGolem.enable", true);
        yml.addDefault("utilities.ironGolem.health", 100.0);
        yml.addDefault("utilities.ironGolem.speed", 0.25);
        yml.addDefault("utilities.ironGolem.despawn", 225);

        /*yml.addDefault("utilities.bridge.material", "EGG");
        yml.addDefault("utilities.bridge.data", 0);
        yml.addDefault("utilities.bridge.enable", true);*/

        yml.options().copyDefaults(true);
        save();
        if (yml.getBoolean("utilities.silverfish.enable")){
            try {
                Material.valueOf(yml.getString("utilities.silverfish.material"));
            } catch (Exception ecx){
                plugin.getLogger().severe("Invalid material at utilities.silverfish.material.. Changing it..");
                yml.set("utilities.silverfish.material", "SNOW_BALL");
                save();
            }
        }
        if (yml.getBoolean("utilities.ironGolem.enable")){
            try {
                Material.valueOf(yml.getString("utilities.ironGolem.material"));
            } catch (Exception ecx){
                plugin.getLogger().severe("Invalid material at utilities.ironGolem.material.. Changing it..");
                yml.set("utilities.ironGolem.material", "MONSTER_EGG");
                save();
            }
        }
       /* if (yml.getBoolean("utilities.bridge.enable")){
            try {
                Material.valueOf(yml.getString("utilities.bridge.material"));
            } catch (Exception ecx){
                plugin.getLogger().severe("Invalid material at utilities.bridge.material.. Changing it..");
                yml.set("utilities.bridge.material", "EGG");
                save();
            }
        }*/
    }

    public void setCategoryWithBuy(String name, int slot, String material, int data, int amount, int price, String currency, boolean enchant) {
        String path = name + ".";
        set(path + "slot", slot);
        set(path + "material", material);
        set(path + "data", data);
        set(path + "amount", amount);
        set(path + "enchanted", enchant);
        set(path + "cost", price);
        set(path + "currency", currency);
    }

    public void addBuyItem(String name, String material, int data, int amount, String enchant, boolean autoequip, boolean permanent) {
        String path = name + ".";
        set(path + "material", material);
        set(path + "data", data);
        set(path + "amount", amount);
        if (!enchant.isEmpty()) {
            set(path + "enchantments", enchant);
        }
        if (autoequip) {
            set(path + "auto-equip", true);
        }
        if (permanent) {
            set(path + "permanent", true);
        }
    }

    public void setMainStuff(String name, int slot, String material, int data, int amount, boolean enchant) {
        String path = name + ".";
        set(path + "slot", slot);
        set(path + "material", material);
        set(path + "data", data);
        set(path + "amount", amount);
        set(path + "enchanted", enchant);
    }

    public void loadShop() {
        loadMainShop();
    }

    public void loadShopCategory(String path) {
        for (String category : yml.getConfigurationSection(path).getKeys(false)) {
            if (category.equalsIgnoreCase("invSize")) continue;
            if (path.equalsIgnoreCase("main.invContents") && (category.equalsIgnoreCase("recommended") || category.equalsIgnoreCase("favourites")))
                continue; //todo de facut
            if ((yml.get(path + "." + category + ".buyItems") != null && yml.get(path + "." + category + ".invContents") != null) ||
                    (yml.get(path + "." + category + ".buyItems") == null && yml.get(path + "." + category + ".invContents") == null)) {
                plugin.getLogger().severe("Could not understand if category or buyItems: " + path + "." + category);
                continue;
            }
            if (yml.get(path + "." + category + ".buyItems") != null) {
                for (String par : Arrays.asList("material", "data", "slot", "cost", "currency", "enchanted", "amount")) {
                    if (yml.get(path + "." + category + "." + par) == null) {
                        plugin.getLogger().severe(par + " not set for: " + path + "." + category);
                        return;
                    }
                }
                try {
                    Material.valueOf(yml.getString(path + "." + category + ".material"));
                } catch (Exception ex) {
                    plugin.getLogger().severe("Invalid material at: " + path + "." + category);
                    continue;
                }
                ShopCategory sc = ShopCategory.getByName(path);
                if (sc != null) {
                    if (yml.getInt(path + "." + category + ".slot") > sc.getInvSize() - 1) {
                        plugin.getLogger().severe(path + "." + category + ".slot is bigger than the inventory size!");
                        continue;
                    }
                    ItemStack i;
                    try {
                        i = new ItemStack(Material.valueOf(yml.getString(path + "." + category + ".material")), yml.getInt(path + "." + category + ".amount"), (byte) yml.getInt(path + "." + category + ".data"));
                    } catch (Exception ex) {
                        plugin.getLogger().severe("There was an error while loading ItemStack for: " + path + "." + category);
                        continue;
                    }
                    if (!Arrays.asList("iron", "gold", "emerald", "diamond", "vault").contains(yml.getString(path + "." + category + ".currency"))) {
                        plugin.getLogger().severe("Invalid currency at: " + path + "." + category);
                        continue;
                    }
                    ItemMeta im = i.getItemMeta();
                    im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    if (yml.get(path + "." + category + ".enchanted") != null) {
                        if (yml.getBoolean(path + "." + category + ".enchanted")) {
                            im.addEnchant(Enchantment.DURABILITY, 1, true);
                            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        }
                    }
                    i.setItemMeta(im);
                    CategoryContent cc = new CategoryContent(i, yml.getInt(path + "." + category + ".slot"), category);
                    cc.setShopCategory(sc);
                    BuyItemsAction buy = new BuyItemsAction(yml.getInt(path + "." + category + ".cost"), yml.getString(path + "." + category + ".currency"), cc);
                    cc.setContentAction(buy);
                    sc.addContent(cc);
                    if (!yml.getString(path + "." + category + ".buyItems").equalsIgnoreCase("[]")) {
                        for (String s : yml.getConfigurationSection(path + "." + category + ".buyItems").getKeys(false)) {
                            String path2 = path + "." + category + ".buyItems." + s + ".";
                            for (String s2 : Arrays.asList("material", "data", "amount")) {
                                if (yml.get(path2 + s2) == null) {
                                    plugin.getLogger().severe(path2 + s2 + " not set.");
                                    return;
                                }
                            }
                            try {
                                Material.valueOf(yml.getString(path2 + "material"));
                            } catch (Exception exx) {
                                plugin.getLogger().severe("Invalid material at: " + path2);
                                continue;
                            }
                            ItemStack itemStack = new ItemStack(Material.valueOf(yml.getString(path2 + "material")), yml.getInt(path2 + "amount", (byte) yml.getInt(path2 + "data")));
                            if (yml.get(path2 + "enchantments") != null) {
                                ItemMeta imm = itemStack.getItemMeta();
                                String[] enchant = yml.getString(path2 + "enchantments").split(",");
                                for (String enc : enchant) {
                                    String[] stuff = enc.split(" ");
                                    try {
                                        Enchantment.getByName(stuff[0]);
                                    } catch (Exception eccc) {
                                        plugin.getLogger().severe("Invalid enchantment " + stuff[0] + " at: " + path2 + "enchantments");
                                        continue;
                                    }
                                    int ieee = 1;
                                    if (stuff.length >= 2) {
                                        try {
                                            ieee = Integer.parseInt(stuff[1]);
                                        } catch (Exception exx) {
                                            plugin.getLogger().severe("Invalid int " + stuff[1] + " at: " + path2 + "enchantments");
                                            continue;
                                        }
                                    }
                                    imm.addEnchant(Enchantment.getByName(stuff[0]), ieee, true);
                                }
                                itemStack.setItemMeta(imm);
                            }
                            if (yml.get(path2 + "potionEffect") != null && (i.getType() == Material.POTATO_ITEM || i.getType() == Material.POTION)) {
                                PotionMeta imm = (PotionMeta) itemStack.getItemMeta();
                                String[] enchant = yml.getString(path2 + "potionEffect").split(",");
                                for (String enc : enchant) {
                                    String[] stuff = enc.split(" ");
                                    try {
                                        PotionEffectType.getByName(stuff[0]);
                                    } catch (Exception eccc) {
                                        plugin.getLogger().severe("Invalid potion effect " + stuff[0] + " at: " + path2 + "potionEffect");
                                        continue;
                                    }
                                    int duration = 50, amplifier = 1;
                                    if (stuff.length >= 3) {
                                        try {
                                            duration = Integer.parseInt(stuff[1]);
                                        } catch (Exception exx) {
                                            plugin.getLogger().severe("Invalid int (duration) " + stuff[1] + " at: " + path2 + "potionEffect");
                                            continue;
                                        }
                                        try {
                                            amplifier = Integer.parseInt(stuff[2]);
                                        } catch (Exception exx) {
                                            plugin.getLogger().severe("Invalid int (amplifier) " + stuff[2] + " at: " + path2 + "potionEffect");
                                            continue;
                                        }
                                    }
                                    imm.addCustomEffect(new PotionEffect(PotionEffectType.getByName(stuff[0]), duration*20, amplifier), false);
                                }
                                itemStack.setItemMeta(imm);
                            }
                            ShopItem si = new ShopItem(itemStack, yml.get(path2 + "permanent") == null ? false : yml.getBoolean(path2 + "permanent"), yml.get(path2 + "auto-equip") == null ? false : yml.getBoolean(path2 + "auto-equip"));
                            buy.addItem(si);
                        }
                    }
                } else {
                    plugin.getLogger().severe("Invalid category " + path);
                }
            } else if (yml.get(path + "." + category + ".invContents") != null) {
                for (String par : Arrays.asList("material", "data", "slot", "invSize", "enchanted")) {
                    if (yml.get(path + "." + category + "." + par) == null) {
                        plugin.getLogger().severe(par + " not set for: " + path + "." + category);
                        return;
                    }
                }
                try {
                    Material.valueOf(yml.getString(path + "." + category + ".material"));
                } catch (Exception ex) {
                    plugin.getLogger().severe("Invalid material at: " + path + "." + category);
                }
                ShopCategory sc = ShopCategory.getByName(path);
                if (sc != null) {
                    if (yml.getInt(path + "." + category + ".slot") > sc.getInvSize() - 1) {
                        plugin.getLogger().severe(path + "." + category + ".slot is bigger than the inventory size!");
                        continue;
                    }
                    ItemStack i;
                    try {
                        i = new ItemStack(Material.valueOf(yml.getString(path + "." + category + ".material")), yml.getInt(path + "." + category + ".amount"), (byte) yml.getInt(path + "." + category + ".data"));
                    } catch (Exception ex) {
                        plugin.getLogger().severe("There was an error while loading ItemStack for: " + path + "." + category);
                        continue;
                    }
                    ItemMeta im = i.getItemMeta();
                    im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    if (yml.get(path + "." + category + ".enchanted") != null) {
                        if (yml.getBoolean(path + "." + category + ".enchanted")) {
                            im.addEnchant(Enchantment.DURABILITY, 1, true);
                            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        }
                    }
                    i.setItemMeta(im);
                    ShopCategory categorie = new ShopCategory(yml.getInt(path + "." + category + ".invSize"), path + "." + category + ".invContents");
                    categorie.setParent(sc);
                    CategoryContent cc = new CategoryContent(i, yml.getInt(path + "." + category + ".slot"), category);
                    cc.setContentAction(new OpenCategoryAction(categorie));
                    sc.addContent(cc);
                    loadShopCategory(path + "." + category + ".invContents");
                } else {
                    plugin.getLogger().severe("Invalid category " + path);
                }

            } else {
                plugin.getLogger().severe("Could not understand if category or buyItems: " + path + "." + category);
                continue;
            }
        }

    }

    private void loadMainShop() {
        String category = "main";
        if (yml.get(category + ".invContents") != null) {
            if (yml.get(category + ".invSize") == null) {
                plugin.getLogger().severe("invSize not set for: " + category);
                return;
            }
            if (yml.getInt(category + ".invSize") % 9 != 0) {
                plugin.getLogger().severe("Invalid invSize at: " + category);
                return;
            }
            new ShopCategory(yml.getInt(category + ".invSize"), category + ".invContents");
            loadShopCategory("main.invContents");
        } else {
            plugin.getLogger().severe("Could not load main shop.");
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

    public List<String> l(String path) {
        return yml.getStringList(path).stream().map(s -> s.replace("&", "§")).collect(Collectors.toList());
    }

    public boolean getBoolean(String path) {
        return yml.getBoolean(path);
    }

    public int getInt(String path) {
        return yml.getInt(path);
    }

    public YamlConfiguration getYml() {
        return yml;
    }
}
