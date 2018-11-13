package com.andrei1058.bedwars.shop.main;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.events.ShopBuyEvent;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.language.Messages;
import com.andrei1058.bedwars.shop.ShopCache;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.Main.nms;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class CategoryContent {

    private int slot;
    private boolean loaded = false;
    private List<ContentTier> contentTiers = new ArrayList<>();
    private String contentName;
    private String itemNamePath, itemLorePath;
    private String identifier;
    private boolean permanent = false, downgradable = false;

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

        if (yml.get(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_IS_PERMANENT) != null) {
            permanent = yml.getBoolean(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_IS_PERMANENT);
        }

        if (yml.get(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_IS_DOWNGRADABLE) != null) {
            downgradable = yml.getBoolean(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_IS_DOWNGRADABLE);
        }

        this.slot = yml.getInt(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_CONTENT_SLOT);

        ContentTier ctt;
        for (String s : yml.getConfigurationSection(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_CONTENT_TIERS).getKeys(false)) {
            ctt = new ContentTier(path + "." + ConfigPath.SHOP_CATEGORY_CONTENT_CONTENT_TIERS + "." + s, s, path, yml);
            /*if (ctt.isLoaded())*/ contentTiers.add(ctt);
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

        identifier = path;

        loaded = true;

    }

    public void execute(Player player, ShopCache shopCache) {

        ContentTier ct;

        //check if can re-buy
        if (shopCache.getContentTier(getIdentifier()) == contentTiers.size()) {
            if (isPermanent() && shopCache.hasCachedItem(this)) {
                player.sendMessage(getMsg(player, Messages.SHOP_ALREADY_BOUGHT));
                //todo play cant buy sound
                return;
            }
            //current tier
            ct = contentTiers.get(shopCache.getContentTier(getIdentifier()) - 1);
        } else {
            //next tier
            ct = contentTiers.get(shopCache.getContentTier(getIdentifier()));
        }

        //check money
        int money = calculateMoney(player, ct.getCurrency());
        if (money < ct.getPrice()) {
            player.sendMessage(getMsg(player, Messages.SHOP_INSUFFICIENT_MONEY).replace("{currency}", getMsg(player, getCurrencyMsgPath(ct))).
                    replace("{amount}", String.valueOf(ct.getPrice() - money)));
            //todo play cant buy sound
            return;
        }

        //take money
        takeMoney(player, ct.getCurrency(), ct.getPrice());

        //upgrade if possible
        shopCache.upgradeCachedItem(this);


        //give items
        giveItems(player, shopCache);

        //play sound
        player.playSound(player.getLocation(), nms.bought(), 1f, 1f);

        //send purchase msg
        player.sendMessage(getMsg(player, Messages.SHOP_NEW_PURCHASE).replace("{item}", ChatColor.stripColor(getMsg(player, itemNamePath))));

        //call shop buy event
        Bukkit.getPluginManager().callEvent(new ShopBuyEvent(player, this));
    }

    /**
     * Add tier items to player inventory
     */
    public void giveItems(Player player, ShopCache shopCache) {
        for (BuyItem bi : contentTiers.get(shopCache.getContentTier(getIdentifier()) - 1).getBuyItemsList()) {
            bi.give(player);
        }
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
    public ItemStack getItemStack(Player player, ShopCache shopCache) {
        ItemStack i;
        if (shopCache.getContentTier(identifier) == contentTiers.size()) {
            i = contentTiers.get(contentTiers.size() - 1).getItemStack().clone();
        } else {
            if (shopCache.hasCachedItem(this)){
                i = contentTiers.get(shopCache.getContentTier(identifier)).getItemStack().clone();
            } else {
                i = contentTiers.get(shopCache.getContentTier(identifier) - 1).getItemStack().clone();
            }
        }

        ItemMeta im = i.getItemMeta();
        im.setDisplayName(getMsg(player, itemNamePath));//todo replace tier placeholders n shit
        im.setLore(Language.getList(player, itemLorePath));
        i.setItemMeta(im);
        return i;
    }

    /**
     * Get player's money amount
     */
    public static int calculateMoney(Player player, String currency) {
        if (currency.equals("vault")) {
            return (int) Main.getEconomy().getMoney(player);
        }
        Material material = getCurrency(currency);

        int amount = 0;
        for (ItemStack is : player.getInventory().getContents()) {
            if (is == null) continue;
            if (is.getType() == material) amount += is.getAmount();
        }
        return amount;
    }

    /**
     * Get currency as material
     */
    public static Material getCurrency(String currency) {
        Material material;
        switch (currency) {
            default:
                material = Material.IRON_INGOT;
                break;
            case "gold":
                material = Material.GOLD_INGOT;
                break;
            case "diamond":
                material = Material.DIAMOND;
                break;
            case "emerald":
                material = Material.EMERALD;
                break;
        }
        return material;
    }

    /**
     * Cet currency path
     */
    public static String getCurrencyMsgPath(ContentTier contentTier) {
        String c = "";

        switch (contentTier.getCurrency()) {
            case "iron":
                c = contentTier.getPrice() == 1 ? Messages.MEANING_IRON_SINGULAR : Messages.MEANING_IRON_PLURAL;
                break;
            case "gold":
                c = contentTier.getPrice() == 1 ? Messages.MEANING_GOLD_SINGULAR : Messages.MEANING_GOLD_PLURAL;
                break;
            case "emerald":
                c = contentTier.getPrice() == 1 ? Messages.MEANING_EMERALD_SINGULAR : Messages.MEANING_EMERALD_PLURAL;
                break;
            case "diamond":
                c = contentTier.getPrice() == 1 ? Messages.MEANING_DIAMOND_SINGULAR : Messages.MEANING_DIAMOND_PLURAL;
                break;
            case "vault":
                c = contentTier.getPrice() == 1 ? Messages.MEANING_VAULT_SINGULAR : Messages.MEANING_VAULT_PLURAL;
                break;
        }

        return c;
    }


    /**
     * Take money from player on buy
     */
    public static void takeMoney(Player player, String currency, int amount) {
        if (currency.equals("vault")) {
            if (!Main.getEconomy().isEconomy()) {
                player.sendMessage("§4§lERROR: This requires Vault Support! Please install Vault plugin!");
                return;
            }
            Main.getEconomy().buyAction(player, amount);
            return;
        }

        Material material = getCurrency(currency);
        int cost = amount;
        for (ItemStack i : player.getInventory().getContents()) {
            if (i == null) continue;
            if (i.getType() == material) {
                if (i.getAmount() < cost) {
                    cost -= i.getAmount();
                    nms.minusAmount(player, i, i.getAmount());
                    player.updateInventory();
                } else {
                    nms.minusAmount(player, i, cost);
                    player.updateInventory();
                    break;
                }
            }
        }

    }

    /**
     * Check if category content was loaded
     */
    public boolean isLoaded() {
        return loaded;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public boolean isDowngradable() {
        return downgradable;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<ContentTier> getContentTiers() {
        return contentTiers;
    }
}
