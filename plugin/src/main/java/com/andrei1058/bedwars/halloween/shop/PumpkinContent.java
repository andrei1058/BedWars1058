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

package com.andrei1058.bedwars.halloween.shop;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.shop.IBuyItem;
import com.andrei1058.bedwars.api.arena.shop.IContentTier;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.shop.ShopCache;
import com.andrei1058.bedwars.shop.main.CategoryContent;
import com.andrei1058.bedwars.shop.main.ShopCategory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class PumpkinContent extends CategoryContent {

    private final int slot;

    public PumpkinContent(ShopCategory father) {
        super(null, null, null, null, father);

        int foundSlot = -1;
        for (int i = 19; i < 26; i++){
            int finalI = i;
            if (father.getCategoryContentList().stream().noneMatch(categoryContent -> categoryContent.getSlot() == finalI)){
                foundSlot = i;
                break;
            }
        }
        if (foundSlot == -1) {
            for (int i = 28; i < 35; i++) {
                int finalI = i;
                if (father.getCategoryContentList().stream().noneMatch(categoryContent -> categoryContent.getSlot() == finalI)) {
                    foundSlot = i;
                    break;
                }
            }
        }
        if (foundSlot == -1) {
            for (int i = 37; i < 44; i++) {
                int finalI = i;
                if (father.getCategoryContentList().stream().noneMatch(categoryContent -> categoryContent.getSlot() == finalI)) {
                    foundSlot = i;
                    break;
                }
            }
        }

        this.slot = foundSlot;
        setLoaded(slot != -1);
        if (!isLoaded()) return;
        OneTier pumpkinTier = new OneTier();
        getContentTiers().add(pumpkinTier);
    }

    @Override
    public String getIdentifier() {
        return "halloween-special-pumpkin";
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public boolean isPermanent() {
        return false;
    }


    @Override
    public ItemStack getItemStack(Player player) {
        IContentTier tier = getContentTiers().get(0);
        ItemStack pumpkin = tier.getItemStack();

        boolean canAfford = calculateMoney(player, tier.getCurrency()) >= tier.getPrice();
        String translatedCurrency = getMsg(player, getCurrencyMsgPath(tier));

        String buyStatus;

        if (!canAfford) {
            buyStatus = getMsg(player, Messages.SHOP_LORE_STATUS_CANT_AFFORD).replace("{currency}", translatedCurrency);
        } else {
            buyStatus = getMsg(player, Messages.SHOP_LORE_STATUS_CAN_BUY);
        }
        ChatColor cColor = getCurrencyColor(tier.getCurrency());

        pumpkin.setAmount(12);
        ItemMeta itemMeta = pumpkin.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Happy Halloween!");
        itemMeta.setLore(Arrays.asList("", cColor + String.valueOf(tier.getPrice()) + " " + cColor + translatedCurrency, " ", buyStatus));
        pumpkin.setItemMeta(itemMeta);
        return pumpkin;
    }

    @Override
    public ItemStack getItemStack(Player player, ShopCache shopCache) {
        return getItemStack(player);
    }

    private static class OneTier implements IContentTier {

        @Override
        public int getPrice() {
            return 4;
        }

        @Override
        public Material getCurrency() {
            return Material.IRON_INGOT;
        }

        @Override
        public void setCurrency(Material currency) {

        }

        @Override
        public void setPrice(int price) {

        }

        @Override
        public void setItemStack(ItemStack itemStack) {

        }

        @Override
        public void setBuyItemsList(List<IBuyItem> buyItemsList) {

        }

        @Override
        public ItemStack getItemStack() {
            return new ItemStack(Material.PUMPKIN, 12);
        }

        @Override
        public int getValue() {
            return 4;
        }

        @Override
        public List<IBuyItem> getBuyItemsList() {
            return Collections.singletonList(new FinalItem());
        }
    }

    private static class FinalItem implements IBuyItem {

        @Override
        public boolean isLoaded() {
            return true;
        }

        @Override
        public void give(Player player, IArena arena) {
            player.getInventory().addItem(new ItemStack(Material.PUMPKIN, 12));
        }

        @Override
        public String getUpgradeIdentifier() {
            return null;
        }

        @Override
        public ItemStack getItemStack() {
            return null;
        }

        @Override
        public void setItemStack(ItemStack itemStack) {

        }

        @Override
        public boolean isAutoEquip() {
            return false;
        }

        @Override
        public void setAutoEquip(boolean autoEquip) {

        }

        @Override
        public boolean isPermanent() {
            return false;
        }

        @Override
        public void setPermanent(boolean permanent) {

        }

        @Override
        public boolean isUnbreakable() {
            return false;
        }

        @Override
        public void setUnbreakable(boolean unbreakable) {

        }
    }
}
