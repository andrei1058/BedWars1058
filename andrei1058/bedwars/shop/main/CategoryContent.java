package com.andrei1058.bedwars.shop.main;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.events.ShopBuyEvent;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.language.Messages;
import com.andrei1058.bedwars.shop.ShopCache;
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.quickbuy.PlayerQuickBuyCache;
import com.andrei1058.bedwars.shop.quickbuy.QuickBuyAdd;
import com.andrei1058.bedwars.shop.quickbuy.QuickBuyElement;
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
            /*if (ctt.isLoaded())*/
            contentTiers.add(ctt);
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
                player.playSound(player.getLocation(), nms.playerKill(), 1f, 1f);
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
            player.playSound(player.getLocation(), nms.playerKill(), 1f, 1f);
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
        player.sendMessage(getMsg(player, Messages.SHOP_NEW_PURCHASE).replace("{item}", ChatColor.stripColor(getMsg(player, itemNamePath))).replace("{color}", "").replace("{tier}", ""));

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
        ContentTier ct;
        if (shopCache.getContentTier(identifier) == contentTiers.size()) {
            ct = contentTiers.get(contentTiers.size() - 1);
        } else {
            if (shopCache.hasCachedItem(this)) {
                ct = contentTiers.get(shopCache.getContentTier(identifier));
            } else {
                ct = contentTiers.get(shopCache.getContentTier(identifier) - 1);
            }
        }

        ItemStack i = ct.getItemStack();
        ItemMeta im = i.getItemMeta().clone();

        boolean canAfford = calculateMoney(player, ct.getCurrency()) >= ct.getPrice();
        boolean hasQuick = hasQuick(PlayerQuickBuyCache.getQuickBuyCache(player.getUniqueId()));

        String color = getMsg(player, canAfford ? Messages.SHOP_CAN_BUY_COLOR : Messages.SHOP_CANT_BUY_COLOR);
        String translatedCurrency = getMsg(player, getCurrencyMsgPath(ct));
        ChatColor cColor = getCurrencyColor(ct.getCurrency());

        String tier = getRomanNumber(shopCache.getContentTier(this.getIdentifier()));
        String buyStatus;
        if (!canAfford){
            buyStatus = getMsg(player, Messages.SHOP_LORE_STATUS_CANT_AFFORD).replace("{currency}", translatedCurrency);
        } else {
            if (isPermanent() && getContentTiers().size() == ct.getValue()){
                buyStatus = getMsg(player, Messages.SHOP_LORE_STATUS_MAXED);
            } else {
                buyStatus = getMsg(player, Messages.SHOP_LORE_STATUS_CAN_BUY);
            }
        }

        im.setDisplayName(getMsg(player, itemNamePath).replace("{color}", color).replace("{tier}", tier));

        List<String> lore = new ArrayList<>();
        for (String s : Language.getList(player, itemLorePath)) {
            if (s.contains("{quick_buy}")) {
                if (hasQuick) {
                    if (ShopIndex.getIndexViewers().contains(player.getUniqueId())) {
                        s = getMsg(player, Messages.SHOP_LORE_QUICK_REMOVE);
                    } else {
                        continue;
                    }
                } else {
                    s = getMsg(player, Messages.SHOP_LORE_QUICK_ADD);
                }
            }
            s = s.replace("{tier}", tier).replace("{color}", color).replace("{cost}", cColor + String.valueOf(ct.getPrice()))
                    .replace("{currency}", cColor + translatedCurrency).replace("{buy_status}", buyStatus);
            lore.add(s);
        }

        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    /**
     * Check if a player has this cc to quick buy
     */
    public boolean hasQuick(PlayerQuickBuyCache c) {
        for (QuickBuyElement q : c.getElements()) {
            if (q.getCategoryContent() == this) return true;
        }
        return false;
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

    public static ChatColor getCurrencyColor(String currency) {
        ChatColor c = ChatColor.DARK_GREEN;
        switch (currency.toLowerCase()) {
            case "diamond":
                c = ChatColor.AQUA;
                break;
            case "gold":
                c = ChatColor.GOLD;
                break;
            case "iron":
                c = ChatColor.WHITE;
                break;
        }
        return c;
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
     * Get the roman number for an integer
     */
    public static String getRomanNumber(int n) {
        String s;
        switch (n) {
            default:
                s = String.valueOf(n);
                break;
            case 1:
                s = "I";
                break;
            case 2:
                s = "II";
                break;
            case 3:
                s = "III";
                break;
            case 4:
                s = "IV";
                break;
            case 5:
                s = "V";
                break;
            case 6:
                s = "VI";
                break;
            case 7:
                s = "VII";
                break;
            case 8:
                s = "VIII";
                break;
            case 9:
                s = "IX";
                break;
            case 10:
                s = "X";
                break;
        }
        return s;
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
