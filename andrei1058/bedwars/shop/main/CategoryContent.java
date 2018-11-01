package com.andrei1058.bedwars.shop.main;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.language.Messages;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CategoryContent {

    private int slot;
    private boolean loaded = false;
    private List<ContentTier> contentTiers = new ArrayList<>();
    private ContentTier nextTier = null;
    private String contentName;
    private String itemNamePath, itemLorePath;

    /**
     * Load a new category
     */
    public CategoryContent(String path, String name, String categoryName, YamlConfiguration yml) {
        Main.debug("Loading CategoryContent " + path);
        this.contentName = name;

        if (yml.get(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_CONTENT_SLOT) == null) {
            Main.plugin.getLogger().severe("Content slot not set at " + path);
            return;
        }

        if (yml.get(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_CONTENT_TIERS) == null) {
            Main.plugin.getLogger().severe("No tiers set for " + path);
            return;
        }

        if (yml.getConfigurationSection(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_CONTENT_TIERS).getKeys(false).isEmpty()) {
            Main.plugin.getLogger().severe("No tiers set for " + path);
            return;
        }

        if (yml.get(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_CONTENT_TIERS + ".tier1") == null) {
            Main.plugin.getLogger().severe("tier1 not found for " + path);
            return;
        }

        this.slot = yml.getInt(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_CONTENT_SLOT);

        ContentTier ctt;
        for (String s : yml.getConfigurationSection(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_CONTENT_TIERS).getKeys(false)) {
            ctt = new ContentTier(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_CONTENT_TIERS + "." + s, s, contentName, categoryName, yml);
            if (ctt.isLoaded()) contentTiers.add(ctt);
        }

        for (ContentTier ct : contentTiers) {
            if (ct.getValue() == 1) nextTier = ct;
        }

        if (nextTier == null) {
            Main.plugin.getLogger().severe("An error occurred with tiers at " + path);
        }

        itemNamePath = Messages.SHOP_CONTENT_TIER_ITEM_NAME.replace("%category%", categoryName).replace("%content%", contentName);
        if (!Main.lang.exists(itemNamePath)) {
            for (Language lang : Language.getLanguages()) {
                if (!lang.exists(itemNamePath)) {
                    lang.set(itemNamePath, "&cName not set");
                }
            }
        }
        itemLorePath = Messages.SHOP_CONTENT_TIER_ITEM_LORE.replace("%category%", categoryName).replace("%content%", contentName);
        if (!Main.lang.exists(itemLorePath)) {
            for (Language lang : Language.getLanguages()) {
                if (!lang.exists(itemLorePath)) {
                    lang.set(itemLorePath, "&cLore not set");
                }
            }
        }

        loaded = true;

    }

    /**
     * Get content slot in category
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Get content preview item in player's language
     */
    public ItemStack getItemStack(Player player) {
        ItemStack i = nextTier.getItemStack().clone();
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Language.getMsg(player, itemNamePath));//todo replace tier placeholders n shit
        im.setLore(Language.getList(player, itemLorePath));
        i.setItemMeta(im);
        return i;
    }

    /**
     * Check if category content was loaded
     */
    public boolean isLoaded() {
        return loaded;
    }
}
