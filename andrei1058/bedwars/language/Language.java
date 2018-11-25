package com.andrei1058.bedwars.language;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.andrei1058.bedwars.Main.lang;
import static com.andrei1058.bedwars.Main.plugin;

public class Language {

    private YamlConfiguration yml;
    private File config;
    private String iso, prefixColor = "", langName = "English";
    private static HashMap<Player, Language> langByPlayer = new HashMap<>();
    private static List<Language> languages = new ArrayList<>();

    public Language(String iso) {
        this.iso = iso;
        File d = new File("plugins/" + plugin.getDescription().getName() + "/Languages");
        if (!d.exists()) d.mkdir();
        config = new File(d.toPath() + "/messages_" + iso + ".yml");
        if (!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            plugin.getLogger().info("Creating " + d.toPath() + "/messages_" + iso + ".yml");
        }
        yml = YamlConfiguration.loadConfiguration(config);
        languages.add(this);
    }

    public static void setupLang(Language lbj) {
        YamlConfiguration yml = lbj.yml;
        yml.options().copyDefaults(true);
        switch (lbj.iso) {
            default:
                new English(lbj, yml);
                break;
            case "ro":
                new Romanian(lbj, yml);
                break;
            case "it":
                new Italian(lbj, yml);
                break;
            case "pl":
                new Polish(lbj, yml);
                break;
            case "es":
                new Spanish(lbj, yml);
                break;
            case "ru":
                new Russian(lbj, yml);
                break;
        }

        lbj.save();
        lbj.langName = lbj.m("name");
        lbj.prefixColor = ChatColor.translateAlternateColorCodes('&', yml.getString(com.andrei1058.bedwars.language.Messages.PREFIX));
    }

    public static List<String> getScoreboard(Player p, String path, String alternative) {
        Language language = getPlayerLanguage(p);
        if (language.exists(path)) {
            return language.l(path);
        }
        return language.l(alternative);
    }

    public void set(String path, Object value) {
        yml.set(path, value);
        save();
    }

    public String getLangName() {
        return langName;
    }

    public void save() {
        try {
            yml.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMsg(Player p, String path) {
        return langByPlayer.getOrDefault(p, lang).m(path);
    }

    public static Language getPlayerLanguage(Player p) {
        return langByPlayer.getOrDefault(p, lang);
    }

    public boolean exists(String path) {
        return yml.get(path) != null;
    }

    public static List<String> getList(Player p, String path) {
        return langByPlayer.getOrDefault(p, lang).l(path);
    }

    public static void saveIfNotExists(String path, Object data) {
        for (Language l : languages) {
            if (l.yml.get(path) == null) {
                l.set(path, data);
            }
        }
    }

    public String m(String path) {
        return ChatColor.translateAlternateColorCodes('&', yml.getString(path).replace("{prefix}", prefixColor));
    }

    public List<String> l(String path) {
        return yml.getStringList(path).stream().map(s -> s.replace("&", "§")).collect(Collectors.toList());
    }

    public static HashMap<Player, Language> getLangByPlayer() {
        return langByPlayer;
    }

    public static boolean isLanguageExist(String iso) {
        for (Language l : languages) {
            if (l.iso.equalsIgnoreCase(iso)) {
                return true;
            }
        }
        return false;
    }

    public static Language getLang(String iso) {
        for (Language l : languages) {
            if (l.iso.equalsIgnoreCase(iso)) {
                return l;
            }
        }
        return lang;
    }

    public void reload() {
        this.yml = YamlConfiguration.loadConfiguration(config);
    }

    public String getIso() {
        return iso;
    }

    public static List<Language> getLanguages() {
        return languages;
    }

    public static void setupCustomStatsMessages() {
        for (Language l : getLanguages()) {
            /* save messages for stats gui items if custom items added */
            for (String item : Main.config.getYml().getConfigurationSection(ConfigPath.GENERAL_CONFIGURATION_STATS_PATH).getKeys(false)) {
                if (ConfigPath.GENERAL_CONFIGURATION_STATS_GUI_SIZE.contains(item)) continue;
                l.yml.addDefault(com.andrei1058.bedwars.language.Messages.PLAYER_STATS_GUI_PATH + "-" + item + "-name", "Name not set");
                l.yml.addDefault(com.andrei1058.bedwars.language.Messages.PLAYER_STATS_GUI_PATH + "-" + item + "-lore", Collections.singletonList("lore not set"));
            }
            l.save();
        }
    }

    public void addDefaultStatsMsg(YamlConfiguration yml, String path, String name, String... lore) {
        yml.addDefault(com.andrei1058.bedwars.language.Messages.PLAYER_STATS_GUI_PATH + "-" + path + "-name", name);
        yml.addDefault(com.andrei1058.bedwars.language.Messages.PLAYER_STATS_GUI_PATH + "-" + path + "-lore", lore);
    }

    /**
     * Create missing name/ lore for items: multi arena lobby, watiting, spectating
     */
    public static void addDefaultMessagesCommandItems(Language language) {
        YamlConfiguration yml = language.yml;
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH) != null) {
            for (String item : Main.config.getYml().getConfigurationSection(ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH).getKeys(false)) {
                String p1 = com.andrei1058.bedwars.language.Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", item);
                String p2 = com.andrei1058.bedwars.language.Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", item);
                yml.addDefault(p1, "&cName not set at: &f" + p1);
                yml.addDefault(p2, Arrays.asList("&cLore not set at:", " &f" + p2));
            }
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH) != null) {
            for (String item : Main.config.getYml().getConfigurationSection(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH).getKeys(false)) {
                String p1 = com.andrei1058.bedwars.language.Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", item);
                String p2 = com.andrei1058.bedwars.language.Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", item);
                yml.addDefault(p1, "&cName not set at: &f" + p1);
                yml.addDefault(p2, Arrays.asList("&cLore not set at:", " &f" + p2));
            }
        }
        if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH) != null) {
            for (String item : Main.config.getYml().getConfigurationSection(ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH).getKeys(false)) {
                String p1 = com.andrei1058.bedwars.language.Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", item);
                String p2 = Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", item);
                yml.addDefault(p1, "&cName not set at: &f" + p1);
                yml.addDefault(p2, Arrays.asList("&cLore not set at:", " &f" + p2));
            }
        }
        yml.options().copyDefaults(true);
        language.save();
    }

    /**
     * Create messages paths for new shop categories
     */
    public void setupUnSetCategories() {
        for (String s : Main.shop.getYml().getConfigurationSection("").getKeys(false)) {
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
                set(Messages.SHOP_CATEGORY_ITEM_LORE.replace("%category%", s), Collections.singleton("&8Lore not set"));
            }
            for (String c : Main.shop.getYml().getConfigurationSection(s + ConfigPath.SHOP_CATEGORY_CONTENT_PATH).getKeys(false)) {
                if (!exists(Messages.SHOP_PATH + Messages.SHOP_CONTENT_TIER_ITEM_NAME.replace("%category%", s).replace("%content%", c))) {
                    set(Messages.SHOP_PATH + Messages.SHOP_CONTENT_TIER_ITEM_NAME.replace("%category%", s).replace("%content%", c), "&8Name not set");
                }
                if (!exists(Messages.SHOP_PATH + Messages.SHOP_CONTENT_TIER_ITEM_LORE.replace("%category%", s).replace("%content%", c))) {
                    set(Messages.SHOP_PATH + Messages.SHOP_CONTENT_TIER_ITEM_LORE.replace("%category%", s).replace("%content%", c), Collections.singleton("&8Lore not set"));
                }
            }
        }
    }

    /**
     * Add required messages for a shop category to the given yml
     */
    public static void addCategoryMessages(YamlConfiguration yml, String categoryName, String invName, String itemName, List<String> itemLore) {
        yml.addDefault(Messages.SHOP_CATEGORY_INVENTORY_NAME.replace("%category%", categoryName), invName);
        yml.addDefault(Messages.SHOP_CATEGORY_ITEM_NAME.replace("%category%", categoryName), itemName);
        yml.addDefault(Messages.SHOP_CATEGORY_ITEM_LORE.replace("%category%", categoryName), itemLore);
    }

    /**
     * Add required messages for a shop category to the given yml
     */
    public static void addContentMessages(YamlConfiguration yml, String contentName, String categoryName, String itemName, List<String> itemLore) {
        yml.addDefault(Messages.SHOP_CONTENT_TIER_ITEM_NAME.replace("%category%", categoryName).replace("%content%", contentName), itemName);
        yml.addDefault(Messages.SHOP_CONTENT_TIER_ITEM_LORE.replace("%category%", categoryName).replace("%content%", contentName), itemLore);
    }
}
