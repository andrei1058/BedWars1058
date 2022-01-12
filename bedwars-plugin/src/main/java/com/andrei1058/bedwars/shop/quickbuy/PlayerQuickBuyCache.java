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

package com.andrei1058.bedwars.shop.quickbuy;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.shop.ShopCache;
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.main.CategoryContent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerQuickBuyCache {

    private final List<QuickBuyElement> elements = new ArrayList<>();
    private String emptyItemNamePath, emptyItemLorePath;
    private ItemStack emptyItem;
    private UUID player;
    private QuickBuyTask task;

    public static int[] quickSlots = new int[]{19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
    private static final ConcurrentHashMap<UUID, PlayerQuickBuyCache> quickBuyCaches = new ConcurrentHashMap<>();
    private final HashMap<Integer, String> updateSlots = new HashMap<>();

    public PlayerQuickBuyCache(Player player) {
        if (player == null) return;
        this.player = player.getUniqueId();
        this.emptyItem = BedWars.nms.createItemStack(BedWars.shop.getYml().getString(ConfigPath.SHOP_SETTINGS_QUICK_BUY_EMPTY_MATERIAL),
                BedWars.shop.getYml().getInt(ConfigPath.SHOP_SETTINGS_QUICK_BUY_EMPTY_AMOUNT),
                (short) BedWars.shop.getYml().getInt(ConfigPath.SHOP_SETTINGS_QUICK_BUY_EMPTY_DATA));
        if (BedWars.shop.getYml().getBoolean(ConfigPath.SHOP_SETTINGS_QUICK_BUY_EMPTY_ENCHANTED)) {
            this.emptyItem = ShopManager.enchantItem(emptyItem);
        }
        this.emptyItemNamePath = Messages.SHOP_QUICK_EMPTY_NAME;
        this.emptyItemLorePath = Messages.SHOP_QUICK_EMPTY_LORE;
        task = new QuickBuyTask(player.getUniqueId());
        quickBuyCaches.put(this.player, this);
    }


    /**
     * Add the player's preferences to the given inventory.
     * This will also add the red empty item.
     */
    public void addInInventory(Inventory inv, ShopCache shopCache) {

        Player p = Bukkit.getPlayer(player);

        for (QuickBuyElement qbe : elements) {
            inv.setItem(qbe.getSlot(), qbe.getCategoryContent().getItemStack(p, shopCache));
        }

        if (elements.size() == 21) return;

        ItemStack i = getEmptyItem(p);
        for (int x : quickSlots) {
            if (inv.getItem(x) == null) {
                inv.setItem(x, i);
            }
        }
    }

    public void destroy() {
        elements.clear();
        if (task != null) {
            task.cancel();
        }
        quickBuyCaches.remove(player);
        this.pushChangesToDB();
    }

    public void setElement(int slot, CategoryContent cc) {
        elements.removeIf(q -> q.getSlot() == slot);
        String element;
        if (cc == null){
            element = " ";
        } else {
            addQuickElement(new QuickBuyElement(cc.getIdentifier(), slot));
            element = cc.getIdentifier();
        }
        if (updateSlots.containsKey(slot)){
            updateSlots.replace(slot, element);
        } else {
            updateSlots.put(slot, element);
        }
    }

    @NotNull
    private ItemStack getEmptyItem(Player player) {
        ItemStack i = emptyItem.clone();
        ItemMeta im = i.getItemMeta();
        if (im != null) {
            im.setDisplayName(Language.getMsg(player, emptyItemNamePath));
            im.setLore(Language.getList(player, emptyItemLorePath));
            i.setItemMeta(im);
        }
        return i;
    }

    /**
     * Check if as category content at quick buy
     */
    public boolean hasCategoryContent(CategoryContent cc) {
        for (QuickBuyElement q : getElements()) {
            if (q.getCategoryContent() == cc) return true;
        }
        return false;
    }

    /**
     * Get a Player Quick buy cache
     */
    @Nullable
    public static PlayerQuickBuyCache getQuickBuyCache(UUID uuid) {
        return quickBuyCaches.getOrDefault(uuid, null);
    }

    public List<QuickBuyElement> getElements() {
        return elements;
    }

    /**
     * Add a quick buy element
     */
    public void addQuickElement(QuickBuyElement e) {
        this.elements.add(e);
    }

    public void pushChangesToDB() {
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin,
                () -> BedWars.getRemoteDatabase().pushQuickBuyChanges(updateSlots, this.player, elements));
    }
}
