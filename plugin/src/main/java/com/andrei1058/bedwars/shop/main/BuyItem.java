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

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.shop.IBuyItem;
import com.andrei1058.bedwars.api.arena.team.TeamEnchant;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.andrei1058.bedwars.BedWars.nms;
import static com.andrei1058.bedwars.BedWars.plugin;

@SuppressWarnings("WeakerAccess")
public class BuyItem implements IBuyItem {

    private ItemStack itemStack;
    private boolean autoEquip = false;
    private boolean permanent = false;
    private boolean unbreakable = false;
    private boolean loaded = false;
    private final String upgradeIdentifier;

    /**
     * Create a shop item
     */
    public BuyItem(String path, YamlConfiguration yml, String upgradeIdentifier, ContentTier parent) {
        BedWars.debug("Loading BuyItems: " + path);
        this.upgradeIdentifier = upgradeIdentifier;

        if (yml.get(path + ".material") == null) {
            BedWars.plugin.getLogger().severe("BuyItem: Material not set at " + path);
            return;
        }

        itemStack = nms.createItemStack(yml.getString(path + ".material"),
                yml.get(path + ".amount") == null ? 1 : yml.getInt(path + ".amount"),
                (short) (yml.get(path + ".data") == null ? 1 : yml.getInt(path + ".data")));

        if (yml.get(path + ".name") != null) {
            ItemMeta im = itemStack.getItemMeta();
            if (im != null) {
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&r"+yml.getString(path + ".name")));
                itemStack.setItemMeta(im);
            }
        }

        if (yml.get(path + ".enchants") != null && itemStack.getItemMeta() != null) {
            ItemMeta imm = itemStack.getItemMeta();
            String[] enchant = yml.getString(path + ".enchants").split(",");
            for (String enc : enchant) {
                String[] stuff = enc.split(" ");
                try {
                    Enchantment.getByName(stuff[0]);
                } catch (Exception ex) {
                    plugin.getLogger().severe("BuyItem: Invalid enchants " + stuff[0] + " at: " + path + ".enchants");
                    continue;
                }
                int ieee = 1;
                if (stuff.length >= 2) {
                    try {
                        ieee = Integer.parseInt(stuff[1]);
                    } catch (Exception exx) {
                        plugin.getLogger().severe("BuyItem: Invalid int " + stuff[1] + " at: " + path + ".enchants");
                        continue;
                    }
                }
                imm.addEnchant(Enchantment.getByName(stuff[0]), ieee, true);
            }
            itemStack.setItemMeta(imm);
        }

        if (yml.get(path + ".potion") != null && (itemStack.getType() == Material.POTION)) {
            // 1.16+ custom color
            if (yml.getString(path + ".potion-color") != null && !yml.getString(path + ".potion-color").isEmpty()) {
                itemStack = nms.setTag(itemStack, "CustomPotionColor", yml.getString(path + ".potion-color"));
            }
            PotionMeta imm = (PotionMeta) itemStack.getItemMeta();
            if (imm != null) {
                String[] enchant = yml.getString(path + ".potion").split(",");
                for (String enc : enchant) {
                    String[] stuff = enc.split(" ");
                    try {
                        PotionEffectType.getByName(stuff[0].toUpperCase());
                    } catch (Exception ex) {
                        plugin.getLogger().severe("BuyItem: Invalid potion effect " + stuff[0] + " at: " + path + ".potion");
                        continue;
                    }
                    int duration = 50, amplifier = 1;
                    if (stuff.length >= 3) {
                        try {
                            duration = Integer.parseInt(stuff[1]);
                        } catch (Exception exx) {
                            plugin.getLogger().severe("BuyItem: Invalid int (duration) " + stuff[1] + " at: " + path + ".potion");
                            continue;
                        }
                        try {
                            amplifier = Integer.parseInt(stuff[2]);
                        } catch (Exception exx) {
                            plugin.getLogger().severe("BuyItem: Invalid int (amplifier) " + stuff[2] + " at: " + path + ".potion");
                            continue;
                        }
                    }
                    imm.addCustomEffect(new PotionEffect(PotionEffectType.getByName(stuff[0].toUpperCase()), duration * 20, amplifier), true);
                }
                itemStack.setItemMeta(imm);
            }

            itemStack = nms.setTag(itemStack, "Potion", "minecraft:water");
            if (parent.getItemStack().getType() == Material.POTION && imm != null && !imm.getCustomEffects().isEmpty()) {
                ItemStack parentItemStack = parent.getItemStack();
                if (parentItemStack.getItemMeta() != null) {
                    PotionMeta potionMeta = (PotionMeta) parentItemStack.getItemMeta();
                    for (PotionEffect potionEffect : imm.getCustomEffects()) {
                        potionMeta.addCustomEffect(potionEffect, true);
                    }
                    parentItemStack.setItemMeta(potionMeta);
                }
                parentItemStack = nms.setTag(parentItemStack, "Potion", "minecraft:water");
                parent.setItemStack(parentItemStack);
            }
        }

        if (yml.get(path + ".auto-equip") != null) {
            autoEquip = yml.getBoolean(path + ".auto-equip");
        }
        if (yml.get(upgradeIdentifier + "." + ConfigPath.SHOP_CATEGORY_CONTENT_IS_PERMANENT) != null) {
            permanent = yml.getBoolean(upgradeIdentifier + "." + ConfigPath.SHOP_CATEGORY_CONTENT_IS_PERMANENT);
        }
        if (yml.get(upgradeIdentifier + "." + ConfigPath.SHOP_CATEGORY_CONTENT_IS_UNBREAKABLE) != null) {
            unbreakable = yml.getBoolean(upgradeIdentifier + "." + ConfigPath.SHOP_CATEGORY_CONTENT_IS_UNBREAKABLE);
        }

        loaded = true;
    }

    /**
     * Check if object created properly
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Give to a player
     */
    public void give(Player player, IArena arena) {

        ItemStack i = itemStack.clone();
        BedWars.debug("Giving BuyItem: " + getUpgradeIdentifier() + " to: " + player.getName());

        if (autoEquip && nms.isArmor(itemStack)) {
            Material m = i.getType();

            ItemMeta im = i.getItemMeta();
            // idk dadea erori
            if (arena.getTeam(player) == null) {
                BedWars.debug("Could not give BuyItem to " + player.getName() + " - TEAM IS NULL");
                return;
            }
            if (im != null) {
                for (TeamEnchant e : arena.getTeam(player).getArmorsEnchantments()) {
                    im.addEnchant(e.getEnchantment(), e.getAmplifier(), true);
                }
                if (permanent) nms.setUnbreakable(im);
                i.setItemMeta(im);
            }

            if (m == Material.LEATHER_HELMET || m == Material.CHAINMAIL_HELMET || m == Material.IRON_HELMET || m == Material.DIAMOND_HELMET || m == nms.materialGoldenHelmet() || m == nms.materialNetheriteHelmet()) {
                if (permanent) i = nms.setShopUpgradeIdentifier(i, upgradeIdentifier);
                player.getInventory().setHelmet(i);
            } else if (m == Material.LEATHER_CHESTPLATE || m == Material.CHAINMAIL_CHESTPLATE || m == Material.IRON_CHESTPLATE || m == Material.DIAMOND_CHESTPLATE || m == nms.materialGoldenChestPlate() || m == nms.materialNetheriteChestPlate() || m == nms.materialElytra()) {
                if (permanent) i = nms.setShopUpgradeIdentifier(i, upgradeIdentifier);
                player.getInventory().setChestplate(i);
            } else if (m == Material.LEATHER_LEGGINGS || m == Material.CHAINMAIL_LEGGINGS || m == Material.IRON_LEGGINGS  || m == Material.DIAMOND_LEGGINGS || m == nms.materialGoldenLeggings()|| m == nms.materialNetheriteLeggings()) {
                if (permanent) i = nms.setShopUpgradeIdentifier(i, upgradeIdentifier);
                player.getInventory().setLeggings(i);
            } else {
                if (permanent) i = nms.setShopUpgradeIdentifier(i, upgradeIdentifier);
                player.getInventory().setBoots(i);
            }
            player.updateInventory();
            Sounds.playSound("shop-auto-equip", player);

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                // #274
                if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    for (Player p : arena.getPlayers()) {
                        BedWars.nms.hideArmor(player, p);
                    }
                }
                //
            }, 20L);
            return;
        } else {

            ItemMeta im = i.getItemMeta();
            i = nms.colourItem(i, arena.getTeam(player));
            if (im != null) {
                if (permanent) nms.setUnbreakable(im);
                if (unbreakable) nms.setUnbreakable(im);
                if (i.getType() == Material.BOW) {
                    if (permanent) nms.setUnbreakable(im);
                    for (TeamEnchant e : arena.getTeam(player).getBowsEnchantments()) {
                        im.addEnchant(e.getEnchantment(), e.getAmplifier(), true);
                    }
                } else if (nms.isSword(i) || nms.isAxe(i)) {
                    for (TeamEnchant e : arena.getTeam(player).getSwordsEnchantments()) {
                        im.addEnchant(e.getEnchantment(), e.getAmplifier(), true);
                    }
                }
                i.setItemMeta(im);
            }

            if (permanent) {
                i = nms.setShopUpgradeIdentifier(i, upgradeIdentifier);
            }
        }

        //Remove swords with lower damage
        if (BedWars.nms.isSword(i)) {
            for (ItemStack itm : player.getInventory().getContents()) {
                if (itm == null) continue;
                if (itm.getType() == Material.AIR) continue;
                if (!BedWars.nms.isSword(itm)) continue;
                if (itm == i) continue;
                if (nms.isCustomBedWarsItem(itm) && nms.getCustomData(itm).equals("DEFAULT_ITEM")) {
                    if (BedWars.nms.getDamage(itm) <= BedWars.nms.getDamage(i)) {
                        player.getInventory().remove(itm);
                    }
                }
            }
        }
        //
        player.getInventory().addItem(i);
        player.updateInventory();
    }


    /**
     * Get upgrade identifier.
     * Used to remove old tier items.
     */
    public String getUpgradeIdentifier() {
        return upgradeIdentifier;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public boolean isAutoEquip() {
        return autoEquip;
    }

    public void setAutoEquip(boolean autoEquip) {
        this.autoEquip = autoEquip;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }
}
