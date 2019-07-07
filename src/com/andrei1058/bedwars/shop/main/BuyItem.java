package com.andrei1058.bedwars.shop.main;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.configuration.ConfigPath;
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

import static com.andrei1058.bedwars.Main.nms;
import static com.andrei1058.bedwars.Main.plugin;

@SuppressWarnings("WeakerAccess")
public class BuyItem {

    private ItemStack itemStack;
    private boolean autoEquip = false;
    private boolean permanent = false;
    private boolean loaded = false;
    private String upgradeIdentifier;

    /**
     * Create a shop item
     */
    public BuyItem(String path, YamlConfiguration yml, String upgradeIdentifier) {
        Main.debug("Loading BuyItems: " + path);
        this.upgradeIdentifier = upgradeIdentifier;

        if (yml.get(path + ".material") == null) {
            Main.plugin.getLogger().severe("BuyItem: Material not set at " + path);
            return;
        }

        itemStack = nms.createItemStack(yml.getString(path + ".material"),
                yml.get(path + ".amount") == null ? 1 : yml.getInt(path + ".amount"),
                (short) (yml.get(path + ".data") == null ? 1 : yml.getInt(path + ".data")));

        if (yml.get(path + ".name") != null) {
            ItemMeta im = itemStack.getItemMeta();
            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', yml.getString(path + ".name")));
            itemStack.setItemMeta(im);
        }

        if (yml.get(path + ".enchants") != null) {
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
            PotionMeta imm = (PotionMeta) itemStack.getItemMeta();
            String[] enchant = yml.getString(path + ".potion").split(",");
            for (String enc : enchant) {
                String[] stuff = enc.split(" ");
                try {
                    PotionEffectType.getByName(stuff[0]);
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
                imm.addCustomEffect(new PotionEffect(PotionEffectType.getByName(stuff[0]), duration * 20, amplifier), false);
            }
            itemStack.setItemMeta(imm);
        }

        if (yml.get(path + ".auto-equip") != null) {
            autoEquip = yml.getBoolean(path + ".auto-equip");
        }
        if (yml.get(upgradeIdentifier + "." + ConfigPath.SHOP_CATEGORY_CONTENT_IS_PERMANENT) != null) {
            permanent = yml.getBoolean(upgradeIdentifier + "." + ConfigPath.SHOP_CATEGORY_CONTENT_IS_PERMANENT);

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
    public void give(Player player, Arena arena) {

        ItemStack i = itemStack.clone();
        Main.debug("Giving BuyItem: " + getUpgradeIdentifier() + " to: " + player.getName());

        if (autoEquip && nms.isArmor(itemStack)) {
            Material m = i.getType();

            ItemMeta im = i.getItemMeta();
            // idk dadea erori
            if (arena.getTeam(player) == null) {
                Main.debug("Could not give BuyItem to " + player.getName() + " - TEAM IS NULL");
                return;
            }
            for (BedWarsTeam.Enchant e : arena.getTeam(player).getArmorsEnchantemnts()) {
                im.addEnchant(e.getEnchantment(), e.getAmplifier(), true);
            }
            if (permanent) nms.setUnbreakable(im);
            i.setItemMeta(im);

            if (m == Material.LEATHER_HELMET || m == Material.CHAINMAIL_HELMET || m == Material.DIAMOND_HELMET || m == nms.materialGoldenHelmet() || m == Material.IRON_HELMET) {
                if (permanent) i = nms.setShopUpgradeIdentifier(i, upgradeIdentifier);
                player.getInventory().setHelmet(i);
            } else if (m == Material.LEATHER_CHESTPLATE || m == Material.CHAINMAIL_CHESTPLATE || m == nms.materialGoldenChestPlate() || m == Material.DIAMOND_CHESTPLATE || m == Material.IRON_CHESTPLATE) {
                if (permanent) i = nms.setShopUpgradeIdentifier(i, upgradeIdentifier);
                player.getInventory().setChestplate(i);
            } else if (m == Material.LEATHER_LEGGINGS || m == Material.CHAINMAIL_LEGGINGS || m == Material.DIAMOND_LEGGINGS || m == nms.materialGoldenLeggings() || m == Material.IRON_LEGGINGS) {
                if (permanent) i = nms.setShopUpgradeIdentifier(i, upgradeIdentifier);
                player.getInventory().setLeggings(i);
            } else {
                if (permanent) i = nms.setShopUpgradeIdentifier(i, upgradeIdentifier);
                player.getInventory().setBoots(i);
            }
            player.updateInventory();

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                // #274
                if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    for (Player p : arena.getPlayers()) {
                        Main.nms.hideArmor(player, p);
                    }
                }
                //
            }, 20L);

            return;
        } else {

            ItemMeta im = i.getItemMeta();
            i = nms.colourItem(i, arena.getTeam(player));
            if (permanent) nms.setUnbreakable(im);

            if (i.getType() == Material.BOW) {
                if (permanent) nms.setUnbreakable(im);
                for (BedWarsTeam.Enchant e : arena.getTeam(player).getBowsEnchantments()) {
                    im.addEnchant(e.getEnchantment(), e.getAmplifier(), true);
                }
            } else if (nms.isSword(i) || nms.isAxe(i)) {
                for (BedWarsTeam.Enchant e : arena.getTeam(player).getSwordsEnchantemnts()) {
                    im.addEnchant(e.getEnchantment(), e.getAmplifier(), true);
                }
            }
            i.setItemMeta(im);

            if (permanent) {
                i = nms.setShopUpgradeIdentifier(i, upgradeIdentifier);
            }
        }

        //Remove swords with lower damage
        if (Main.nms.isSword(i)) {
            for (ItemStack itm : player.getInventory().getContents()) {
                if (itm == null) continue;
                if (itm.getType() == Material.AIR) continue;
                if (!Main.nms.isSword(itm)) continue;
                if (itm == i) continue;
                if (nms.isCustomBedWarsItem(itm) && nms.getCustomData(itm).equals("DEFAULT_ITEM")) {
                    if (Main.nms.getDamage(itm) <= Main.nms.getDamage(i)) {
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
}
