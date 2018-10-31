package com.andrei1058.bedwars.shop2.main;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CategoryContent {

    private int slot;
    private boolean loaded = false;
    private List<ContentTier> contentTiers = new ArrayList<>();
    private ContentTier nextTier = null;
    private String contentName;

    /**
     * Load a new category
     */
    public CategoryContent(String path, String name, YamlConfiguration yml) {
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
            ctt = new ContentTier(path + "." + s, s, contentName, yml);
            if (ctt.isLoaded()) contentTiers.add(ctt);
        }

        for (ContentTier ct : contentTiers) {
            if (ct.getValue() == 1) nextTier = ct;
        }

        if (nextTier == null) {
            Main.plugin.getLogger().severe("An error occurred with tiers at " + path);
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
        return nextTier.getItemStack(player);
    }

    /**
     * Check if category content was loaded
     */
    public boolean isLoaded() {
        return loaded;
    }
}
