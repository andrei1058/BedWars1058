package com.andrei1058.bedwars.upgrades;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.configuration.UpgradesConfig;
import com.andrei1058.bedwars.upgrades.listeners.InventoryListener;
import com.andrei1058.bedwars.upgrades.menu.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import java.util.logging.Level;

import static com.andrei1058.bedwars.BedWars.nms;

public class UpgradesManager {

    private static LinkedList<UUID> upgradeViewers = new LinkedList<>();
    //store lower case names
    private static HashMap<String, MenuContent> menuContentByName = new HashMap<>();
    //store lower case names
    private static HashMap<String, UpgradesIndex> menuByName = new HashMap<>();

    private static HashMap<IArena, UpgradesIndex> customMenuForArena = new HashMap<>();

    private static UpgradesConfig upgrades;

    private UpgradesManager() {
    }

    public static void init() {
        File oldFile = new File("plugins/" + BedWars.plugin.getName() + "/upgrades.yml");
        oldFile.delete();

        upgrades = new UpgradesConfig("upgrades2", "plugins/" + BedWars.plugin.getName());
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), BedWars.plugin);
        String name = "";
        for (String index : BedWars.upgrades.getYml().getConfigurationSection("").getKeys(false)) {
            if (index.startsWith("upgrade-")) {
                name = index.replace("upgrade-", "");
                if (!name.isEmpty()) {
                    if (!loadUpgrade(name)) {
                        Bukkit.getLogger().log(Level.WARNING, "Could not load upgrade: " + name);
                    }
                }
            } else if (index.startsWith("separator-")) {
                name = index.replace("separator-", "");
                if (!name.isEmpty()) {
                    if (!loadSeparator(name)) {
                        Bukkit.getLogger().log(Level.WARNING, "Could not load separator: " + name);
                    }
                }
            } else if (index.startsWith("category-")) {
                name = index.replace("category-", "");
                if (!name.isEmpty()) {
                    if (!loadCategory(name)) {
                        Bukkit.getLogger().log(Level.WARNING, "Could not load category: " + name);
                    }
                }
            } else if (index.endsWith("-upgrades-menu")) {
                name = index.replace("-upgrades-menu", "");
                if (!name.isEmpty()) {
                    if (!loadMenu(name)) {
                        Bukkit.getLogger().log(Level.WARNING, "Could not load menu: " + name);
                    }
                }
            }
        }
    }

    /**
     * @return true if has the upgrades GUI opened.
     */
    public static boolean isWatchingUpgrades(UUID uuid) {
        return upgradeViewers.contains(uuid);
    }

    /**
     * Set watching upgrades GUI.
     */
    public static void setWatchingUpgrades(UUID uuid) {
        if (!upgradeViewers.contains(uuid)) upgradeViewers.add(uuid);
    }

    /**
     * Remove from upgrades GUI.
     */
    public static void removeWatchingUpgrades(UUID uuid) {
        upgradeViewers.remove(uuid);
    }

    /**
     * Load a menu for a group.
     *
     * @param groupName arena group name.
     * @return false if cannot be loaded.
     */
    public static boolean loadMenu(String groupName) {
        if (!upgrades.getYml().isSet(groupName + "-upgrades-menu")) return false;
        InternalMenu um = new InternalMenu(groupName);
        for (String component : upgrades.getYml().getStringList(groupName + "-upgrades-menu")) {
            String[] data = component.split(",");
            if (data.length <= 1) continue;

            MenuContent mc = null;
            if (data[0].startsWith("category-")) {
                mc = getMenuContent(data[0]);
                if (mc == null && loadCategory(data[0])) {
                    mc = getMenuContent(data[0]);
                }
            } else if (data[0].startsWith("upgrade-")) {
                mc = getMenuContent(data[0]);
                if (mc == null && loadUpgrade(data[0])) {
                    mc = getMenuContent(data[0]);
                }
            } else if (data[0].startsWith("trap-slot-")) {
                mc = getMenuContent(data[0]);
                if (mc == null && loadTrapSlot(data[0])) {
                    mc = getMenuContent(data[0]);
                }
            } else if (data[0].startsWith("separator-")) {
                mc = getMenuContent(data[0]);
                if (mc == null && loadSeparator(data[0])) {
                    mc = getMenuContent(data[0]);
                }
            }
            if (mc == null) continue;
            for (int i = 1; i < data.length; i++) {
                if (Misc.isNumber(data[i])) um.addContent(mc, Integer.parseInt(data[i]));
            }
        }
        return true;
    }

    /**
     * Load a category with given name from the shop file.
     *
     * @param name category name.
     * @return false if cannot be loaded.
     */
    private static boolean loadCategory(String name) {
        if (!upgrades.getYml().isSet(name)) return false;
        if (getMenuContent(name) != null) return false;
        MenuCategory uc = new MenuCategory(name, createDisplayItem(name));
        for (String component : upgrades.getYml().getStringList(name + ".category-content")) {
            String[] data = component.split(",");
            if (data.length <= 1) continue;

            MenuContent mc = null;
            if (data[0].startsWith("category-")) {
                mc = getMenuContent(data[0]);
                if (mc == null && loadCategory(data[0])) {
                    mc = getMenuContent(data[0]);
                }
            } else if (data[0].startsWith("upgrade-")) {
                mc = getMenuContent(data[0]);
                if (mc == null && loadUpgrade(data[0])) {
                    mc = getMenuContent(data[0]);
                }
            } else if (data[0].startsWith("trap-slot-")) {
                mc = getMenuContent(data[0]);
                if (mc == null && loadTrapSlot(data[0])) {
                    mc = getMenuContent(data[0]);
                }
            } else if (data[0].startsWith("separator-")) {
                mc = getMenuContent(data[0]);
                if (mc == null && loadSeparator(data[0])) {
                    mc = getMenuContent(data[0]);
                }
            }
            if (mc == null) continue;
            for (int i = 1; i < data.length; i++) {
                if (Misc.isNumber(data[i])) uc.addContent(mc, Integer.parseInt(data[i]));
            }
        }
        return true;
    }

    /**
     * Load an upgrade element with given name.
     *
     * @param name upgrade name.
     * @return false if can't be loaded.
     */
    private static boolean loadUpgrade(String name) {
        if (!upgrades.getYml().isSet(name)) return false;
        if (!upgrades.getYml().isSet(name + ".tier-1")) return false;
        if (getMenuContent(name) != null) return false;
        MenuUpgrade mu = new MenuUpgrade(name);

        for (String s : upgrades.getYml().getConfigurationSection(name).getKeys(false)) {
            if (!s.startsWith("tier-")) continue;
            if (!upgrades.getYml().isSet(name + "." + s + ".receive")) continue;
            if (!upgrades.getYml().isSet(name + "." + s + ".display-item")) continue;
            if (!upgrades.getYml().isSet(name + "." + s + ".cost")) continue;
            if (!upgrades.getYml().isSet(name + "." + s + ".currency")) continue;
            UpgradeTier ut = new UpgradeTier(name + "." + s, createDisplayItem(name + "." + s), upgrades.getYml().getInt(name + "." + s + ".cost"), getCurrency(upgrades.getYml().getString(name + "." + s + ".currency")));
            mu.addTier(ut);
        }
        return true;
    }

    /**
     * Load a separator with given name.
     *
     * @param name name.
     * @return false if cannot be loaded.
     */
    private static boolean loadSeparator(String name) {
        if (!upgrades.getYml().isSet(name)) return false;
        if (getMenuContent(name) != null) return false;
        new MenuSeparator(name, createDisplayItem(name));
        return true;
    }

    /**
     * Load a trap slot with given name.
     *
     * @param name name.
     * @return false if cannot be loaded.
     */
    private static boolean loadTrapSlot(String name) {
        if (!upgrades.getYml().isSet(name)) return false;
        if (getMenuContent(name) != null) return false;
        new MenuTrapSlot(name, createDisplayItem(name));
        return true;
    }

    /**
     * Check the money amount in a given currency of a player.
     *
     * @param player   target player.
     * @param currency {@link org.bukkit.Material#AIR} is used for vault, {@link org.bukkit.Material#IRON_INGOT} for iron, {@link org.bukkit.Material#GOLD_INGOT} for gold, {@link org.bukkit.Material#DIAMOND} for diamond, {@link org.bukkit.Material#EMERALD} for emerald.
     * @return the amount of money.
     */
    public static int getMoney(Player player, ItemStack currency) {
        //todo
        return 0;
    }

    /**
     * @param name the string to be converted.
     * @return NULL if not a currency. {@link org.bukkit.Material#AIR} is used for vault, {@link org.bukkit.Material#IRON_INGOT} for iron, {@link org.bukkit.Material#GOLD_INGOT} for gold, {@link org.bukkit.Material#DIAMOND} for diamond, {@link org.bukkit.Material#EMERALD} for emerald.
     */
    public static ItemStack getCurrency(String name) {
        //todo
        return null;
    }

    /**
     * Check if is upgradable item.
     * Used in inventory click.
     *
     * @param item item to be checked.
     * @retrun {@link com.andrei1058.bedwars.upgrades.menu.MenuContent} NULL if isn't an element.
     */
    public static MenuContent getMenuContent(ItemStack item) {
        if (item == null) return null;

        String identifier = nms.getShopUpgradeIdentifier(item);
        if (identifier == null) return null;
        if (identifier.equals("null")) return null;
        if (!identifier.startsWith("MCONT_")) return null;
        identifier = identifier.replaceFirst("MCONT_", "");
        if (identifier.isEmpty()) return null;
        return menuContentByName.getOrDefault(identifier.toLowerCase(), null);
    }

    /**
     * Get menu content by identifier.
     *
     * @retrun null if not found.
     */
    public static MenuContent getMenuContent(String identifier) {
        return menuContentByName.getOrDefault(identifier.toLowerCase(), null);
    }

    /**
     * Set a custom menu that will override the group menu for an arena.
     *
     * @param arena target arena.
     * @param menu  custom menu.
     */
    public static void setCustomMenuForArena(IArena arena, UpgradesIndex menu) {
        if (!customMenuForArena.containsKey(arena)) customMenuForArena.put(arena, menu);
    }

    /**
     * Get upgrade menu index for an arena.
     *
     * @param arena target arena.
     * @return the default menu if the arena doesn't have an arena group menu set or a custom menu by an addon.
     */
    public static UpgradesIndex getMenuForArena(IArena arena) {
        if (customMenuForArena.containsKey(arena)) return customMenuForArena.get(arena);
        return menuByName.getOrDefault(arena.getGroup().toLowerCase(), menuByName.get("default"));
    }

    /**
     * Create a display item.
     *
     * @param path config path.
     * @return bedrock if given material is null
     */
    private static ItemStack createDisplayItem(String path) {
        Material m;
        try {
            m = Material.valueOf(upgrades.getYml().getString(path + ".display-item.material"));
        } catch (Exception e) {
            m = Material.BEDROCK;
        }
        ItemStack i = new ItemStack(m, Integer.parseInt(upgrades.getYml().getString(path + ".display-item.amount")), (short) upgrades.getYml().getInt(path + ".display-item.data"));
        if (upgrades.getYml().getBoolean(path + ".display-item.enchanted")) {
            i.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            ItemMeta im = i.getItemMeta();
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            i.setItemMeta(im);
        }
        return i;
    }
}
