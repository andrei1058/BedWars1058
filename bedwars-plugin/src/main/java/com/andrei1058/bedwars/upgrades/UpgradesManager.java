package com.andrei1058.bedwars.upgrades;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.configuration.UpgradesConfig;
import com.andrei1058.bedwars.upgrades.listeners.InventoryListener;
import com.andrei1058.bedwars.upgrades.menu.MenuContent;
import com.andrei1058.bedwars.upgrades.menu.UpgradesMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
    private static HashMap<String, UpgradesMenu> menuByName = new HashMap<>();

    private static HashMap<IArena, UpgradesMenu> customMenuForArena = new HashMap<>();

    private static UpgradesConfig upgrades;

    private UpgradesManager() {
    }

    public static void init() {
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
        //todo
        return false;
    }

    /**
     * Load a category with given name from the shop file.
     *
     * @param name category name.
     * @return false if cannot be loaded.
     */
    public static boolean loadCategory(String name) {
        //todo
        return false;
    }

    /**
     * Load an upgrade element with given name.
     *
     * @param name upgrade name.
     * @return false if can't be loaded.
     */
    public static boolean loadUpgrade(String name) {
        //todo
        return false;
    }

    /**
     * Load a separator with given name.
     *
     * @param name name.
     * @return false if cannot be loaded.
     */
    public static boolean loadSeparator(String name) {
        //todo
        return false;
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
     * Set a custom menu that will override the group menu for an arena.
     *
     * @param arena target arena.
     * @param menu  custom menu.
     */
    public static void setCustomMenuForArena(IArena arena, UpgradesMenu menu) {
        if (!customMenuForArena.containsKey(arena)) customMenuForArena.put(arena, menu);
    }

    /**
     * Get upgrade menu index for an arena.
     *
     * @param arena target arena.
     * @return the default menu if the arena doesn't have an arena group menu set or a custom menu by an addon.
     */
    public static UpgradesMenu getMenuForArena(IArena arena) {
        if (customMenuForArena.containsKey(arena)) return customMenuForArena.get(arena);
        return menuByName.getOrDefault(arena.getGroup().toLowerCase(), menuByName.get("default"));
    }
}
