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

package com.andrei1058.bedwars.api.language;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.*;

@Deprecated
public class LanguageOld extends ConfigManager {

    private final String iso;
    private String prefix = "";

    public LanguageOld(Plugin plugin, String iso) {
        super(plugin, "messages_" + iso, plugin.getDataFolder().getPath() + "/Languages");
        this.iso = iso;
    }

    /**
     * Set chat prefix.
     * Comes up in every message containing {prefix}.
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    /**
     * Get language display name.
     * @return language display name.
     */
    public String getDisplayName() {
        return getYml().getString("name");
    }

    /**
     * Check if a message was set.
     */
    public boolean exists(String path) {
        return getYml().get(path) != null;
    }

    /**
     * Get a color translated message.
     * @return color translated message.
     */
    public String m(String path) {
        String message = getYml().getString(path);
        if (message == null) {
            System.err.println("Missing message key " + path + " in language " + getIso());
            message = "MISSING_LANG";
        }
        return ChatColor.translateAlternateColorCodes('&', message.replace("{prefix}", prefix));
    }

    /**
     * Get a color translated list.
     */
    public List<String> l(String path) {
        List<String> result = new ArrayList<>();
        List<String> lines = getYml().getStringList(path);
        if (lines == null) {
            System.err.println("Missing message list key " + path + " in language " + getIso());
            lines = Collections.emptyList();
        }
        for (String line : lines) {
            result.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return result;
    }

    /**
     * Get language iso code.
     */
    public String getIso() {
        return iso;
    }

    @SuppressWarnings("WeakerAccess")
    public void addDefaultStatsMsg(YamlConfiguration yml, String path, String name, String... lore) {
        if (yml.getDefaults() == null || !yml.getDefaults().contains(Messages.PLAYER_STATS_GUI_PATH + "-" + path + "-name"))
            yml.addDefault(Messages.PLAYER_STATS_GUI_PATH + "-" + path + "-name", name);
        if (yml.getDefaults() == null || !yml.getDefaults().contains(Messages.PLAYER_STATS_GUI_PATH + "-" + path + "-lore"))
            yml.addDefault(Messages.PLAYER_STATS_GUI_PATH + "-" + path + "-lore", lore);
    }

    /**
     * Create missing name/ lore for items: multi arena lobby, waiting, spectating
     */
    public static void addDefaultMessagesCommandItems(LanguageOld language) {
        if (language == null) return;
        YamlConfiguration yml = language.getYml();
        if (yml == null) return;
        BedWars api = Bukkit.getServer().getServicesManager().getRegistration(BedWars.class).getProvider();
        if (api.getConfigs().getMainConfig().getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH) != null) {
            for (String item : api.getConfigs().getMainConfig().getYml().getConfigurationSection(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH).getKeys(false)) {
                if (item.isEmpty()) continue;
                String p1 = Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", item);
                String p2 = Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", item);
                if (yml.getDefaults() == null || !yml.getDefaults().contains(p1))
                    yml.addDefault(p1, "&cName not set at: &f" + p1);
                if (yml.getDefaults() == null || !yml.getDefaults().contains(p1))
                    yml.addDefault(p2, Arrays.asList("&cLore not set at:", " &f" + p2));
            }
        }
        if (api.getConfigs().getMainConfig().getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH) != null) {
            for (String item : api.getConfigs().getMainConfig().getYml().getConfigurationSection(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH).getKeys(false)) {
                if (item.isEmpty()) continue;
                String p1 = Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", item);
                String p2 = Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", item);
                if (yml.getDefaults() == null || !yml.getDefaults().contains(p1))
                    yml.addDefault(p1, "&cName not set at: &f" + p1);
                if (yml.getDefaults() == null || !yml.getDefaults().contains(p1))
                    yml.addDefault(p2, Arrays.asList("&cLore not set at:", " &f" + p2));
            }
        }
        if (api.getConfigs().getMainConfig().getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH) != null) {
            for (String item : api.getConfigs().getMainConfig().getYml().getConfigurationSection(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH).getKeys(false)) {
                if (item.isEmpty()) continue;
                String p1 = Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", item);
                String p2 = Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", item);
                if (yml.getDefaults() == null || !yml.getDefaults().contains(p1))
                    yml.addDefault(p1, "&cName not set at: &f" + p1);
                if (yml.getDefaults() == null || !yml.getDefaults().contains(p1))
                    yml.addDefault(p2, Arrays.asList("&cLore not set at:", " &f" + p2));
            }
        }
        yml.options().copyDefaults(true);
        language.save();
    }

    /**
     * Create messages paths for new shop categories
     */
    @SuppressWarnings("DuplicateExpressions")
    public void setupUnSetCategories() {
        BedWars api = Bukkit.getServer().getServicesManager().getRegistration(BedWars.class).getProvider();
        for (String s : api.getConfigs().getShopConfig().getYml().getConfigurationSection("").getKeys(false)) {
            if (s.equalsIgnoreCase(ConfigPath.SHOP_SETTINGS_PATH)) continue;
            if (s.equalsIgnoreCase(ConfigPath.SHOP_SPECIALS_PATH)) continue;
            if (s.equals(ConfigPath.SHOP_QUICK_DEFAULTS_PATH)) continue;
            if (!exists(Messages.SHOP_CATEGORY_INVENTORY_NAME.replace("%category%", s))) {
                set(Messages.SHOP_CATEGORY_INVENTORY_NAME.replace("%category%", s), "&8Name not set");
            }
            if (!exists(Messages.SHOP_CATEGORY_ITEM_NAME.replace("%category%", s))) {
                set(Messages.SHOP_CATEGORY_ITEM_NAME.replace("%category%", s), "&8Name not set");
            }
            if (!exists(Messages.SHOP_CATEGORY_ITEM_LORE.replace("%category%", s))) {
                set(Messages.SHOP_CATEGORY_ITEM_LORE.replace("%category%", s), Collections.singletonList("&8Lore not set"));
            }
            if (api.getConfigs().getShopConfig().getYml().get(s + ConfigPath.SHOP_CATEGORY_CONTENT_PATH) == null)
                continue;
            for (String c : api.getConfigs().getShopConfig().getYml().getConfigurationSection(s + ConfigPath.SHOP_CATEGORY_CONTENT_PATH).getKeys(false)) {
                if (!exists(Messages.SHOP_CONTENT_TIER_ITEM_NAME.replace("%category%", s).replace("%content%", c))) {
                    set(Messages.SHOP_CONTENT_TIER_ITEM_NAME.replace("%category%", s).replace("%content%", c), "&8Name not set");
                }
                if (!exists(Messages.SHOP_CONTENT_TIER_ITEM_LORE.replace("%category%", s).replace("%content%", c))) {
                    set(Messages.SHOP_CONTENT_TIER_ITEM_LORE.replace("%category%", s).replace("%content%", c), Collections.singletonList("&8Lore not set"));
                }
            }
        }
    }

    /**
     * Add required messages for a shop category to the given yml
     */
    public static void addCategoryMessages(YamlConfiguration yml, String categoryName, String invName, String itemName, List<String> itemLore) {
        if (yml.getDefaults() == null || !yml.getDefaults().contains(Messages.SHOP_CATEGORY_INVENTORY_NAME.replace("%category%", categoryName)))
            yml.addDefault(Messages.SHOP_CATEGORY_INVENTORY_NAME.replace("%category%", categoryName), invName);
        if (yml.getDefaults() == null || !yml.getDefaults().contains(Messages.SHOP_CATEGORY_ITEM_NAME.replace("%category%", categoryName)))
            yml.addDefault(Messages.SHOP_CATEGORY_ITEM_NAME.replace("%category%", categoryName), itemName);
        if (yml.getDefaults() == null || !yml.getDefaults().contains(Messages.SHOP_CATEGORY_ITEM_LORE.replace("%category%", categoryName)))
            yml.addDefault(Messages.SHOP_CATEGORY_ITEM_LORE.replace("%category%", categoryName), itemLore);
    }

    /**
     * Add required messages for a shop category to the given yml
     */
    public static void addContentMessages(YamlConfiguration yml, String contentName, String categoryName, String itemName, List<String> itemLore) {
        final String path1 = Messages.SHOP_CONTENT_TIER_ITEM_NAME.replace("%category%", categoryName).replace("%content%", contentName),
                path2 = Messages.SHOP_CONTENT_TIER_ITEM_LORE.replace("%category%", categoryName).replace("%content%", contentName);
        if (yml.getDefaults() == null || !yml.getDefaults().contains(path1)) yml.addDefault(path1, itemName);
        if (yml.getDefaults() == null || !yml.getDefaults().contains(path2)) yml.addDefault(path2, itemLore);
    }

    public static String[] getCountDownTitle(LanguageOld playerLang, int second) {
        String[] result = new String[2];
        result[0] = ChatColor.translateAlternateColorCodes('&', playerLang.getYml().get(Messages.ARENA_STATUS_START_COUNTDOWN_TITLE + "-" + second, playerLang.getString(Messages.ARENA_STATUS_START_COUNTDOWN_TITLE)).toString().replace("{second}", String.valueOf(second)));
        if (result[0].isEmpty()) {
            result[0] = " ";
        }
        result[1] = ChatColor.translateAlternateColorCodes('&', playerLang.getYml().get(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-" + second, playerLang.getString(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE)).toString().replace("{second}", String.valueOf(second)));
        if (result[1].isEmpty()) {
            result[1] = " ";
        }
        return result;
    }
}
