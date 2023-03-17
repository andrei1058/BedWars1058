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
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.main.CategoryContent;
import com.andrei1058.bedwars.shop.main.ShopCategory;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("WeakerAccess")
public class QuickBuyTask extends BukkitRunnable {

    private UUID uuid;


    public QuickBuyTask(UUID uuid){
        this.uuid = uuid;
        this.runTaskLaterAsynchronously(BedWars.plugin, 20*7);
    }

    @Override
    public void run() {
        if (Bukkit.getPlayer(uuid) == null){
            cancel();
            return;
        }
        if (Bukkit.getPlayer(uuid).isOnline()){
            PlayerQuickBuyCache cache = PlayerQuickBuyCache.getQuickBuyCache(uuid);
            if (cache == null){
                cancel();
                return;
            }

            if (!BedWars.getRemoteDatabase().hasQuickBuy(uuid)){
                if (BedWars.shop.getYml().get(ConfigPath.SHOP_QUICK_DEFAULTS_PATH) != null){
                    for (String s : BedWars.shop.getYml().getConfigurationSection(ConfigPath.SHOP_QUICK_DEFAULTS_PATH).getKeys(false)) {
                        if (BedWars.shop.getYml().get(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + "." + s + ".path") != null) {
                            if (BedWars.shop.getYml().get(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + "." + s + ".slot") == null){
                                continue;
                            }

                            try {
                                Integer.valueOf(BedWars.shop.getYml().getString(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + "." + s + ".slot"));
                            } catch (Exception ex){
                                BedWars.debug(BedWars.shop.getYml().getString(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + "." + s + ".slot") + " must be an integer!");
                                continue;
                            }

                            for (ShopCategory sc : ShopManager.getShop().getCategoryList()) {
                                for (CategoryContent cc : sc.getCategoryContentList()) {
                                    if (cc.getIdentifier().equals(BedWars.shop.getYml().getString(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + "." + s + ".path"))) {
                                        cache.setElement(Integer.parseInt(BedWars.shop.getYml().getString(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + "." + s + ".slot")), cc);
                                    }
                                }
                            }

                        }
                    }
                }
            } else {
                // slot, identifier
                HashMap<Integer, String> items = BedWars.getRemoteDatabase().getQuickBuySlots(uuid, PlayerQuickBuyCache.quickSlots);
                if (items == null) return;
                if (items.isEmpty()) return;
                for (Map.Entry<Integer, String> entry : items.entrySet()) {
                    if (entry.getValue().isEmpty()) continue;
                    if (entry.getValue().equals(" ")) continue;
                    QuickBuyElement e = new QuickBuyElement(entry.getValue(), entry.getKey());
                    if (e.isLoaded()) {
                        cache.addQuickElement(e);
                    }
                }
            }
        }
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
    }
}
