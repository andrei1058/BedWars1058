package com.andrei1058.bedwars.shop.main;

import com.andrei1058.bedwars.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.andrei1058.bedwars.Main.plugin;

public class BuyItem {

    private ItemStack itemStack;
    private boolean autoEquip = false;
    private boolean permanent = false;
    private boolean loaded = false;
    private String upgradeIdentifier;

    /**
     * Create a shop item
     */
    public BuyItem(String path, YamlConfiguration yml, String upgradeIdentifier, int tier) {
        Main.debug("Loading BuyItems: " + path);
        this.upgradeIdentifier = upgradeIdentifier;

        if (yml.get(path + ".material") == null) {
            Main.plugin.getLogger().severe("BuyItem: Material not set at " + path);
            return;
        }

        itemStack = Main.nms.createItemStack(yml.getString(path + ".material"),
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

        itemStack = Main.nms.addUpgradeTracker(itemStack, upgradeIdentifier, String.valueOf(tier));

        if (yml.get(path + ".auto-equip") != null) {
            autoEquip = yml.getBoolean(path + ".auto-equip");
        }
        if (yml.get(path + ".permanent") != null) {
            permanent = yml.getBoolean(path + ".permanent");
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
     * Get upgrade identifier.
     * Used to remove old tier items.
     */
    public String getUpgradeIdentifier() {
        return upgradeIdentifier;
    }
}
