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

package com.andrei1058.bedwars.support.version.common;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.server.VersionSupport;
import com.andrei1058.bedwars.listeners.Interact_1_13Plus;
import com.andrei1058.bedwars.listeners.ItemDropPickListener;
import com.andrei1058.bedwars.listeners.PlayerDropPick_1_11Minus;
import com.andrei1058.bedwars.listeners.SwapItem;
import com.andrei1058.bedwars.shop.defaultrestore.ShopItemRestoreListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class VersionCommon {

    public static BedWars api;

    public VersionCommon(VersionSupport versionSupport) {
            //noinspection ConstantConditions
            api = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
            // 9 and newer
            if (versionSupport.getVersion() > 1) {
                registerListeners(versionSupport.getPlugin(), new SwapItem(), new ItemDropPickListener.ArrowCollect());
            }
            // 11 and older
            if (versionSupport.getVersion() < 5){
                registerListeners(versionSupport.getPlugin() /*, new ItemDropPickListener.PlayerDrop()*/, new ItemDropPickListener.PlayerPickup()/*,
                        new ShopItemRestoreListener.PlayerDrop()*/, new ShopItemRestoreListener.PlayerPickup(), new PlayerDropPick_1_11Minus(api));
            }

            // 13 and newer
            if (versionSupport.getVersion() > 5){
                registerListeners(versionSupport.getPlugin(), new ShopItemRestoreListener.EntityDrop(), new Interact_1_13Plus(), new ItemDropPickListener.EntityDrop());
            }

            // 1.12 and newer
            if (versionSupport.getVersion() > 4){
                registerListeners(versionSupport.getPlugin(), new ItemDropPickListener.EntityPickup(), new ShopItemRestoreListener.EntityPickup());
            }

            // 1.12 drop listeners
            //if (versionSupport.getVersion() == 5){
                // common
                registerListeners(versionSupport.getPlugin(), new ItemDropPickListener.PlayerDrop(), new ShopItemRestoreListener.PlayerDrop());
            //}

            // common
            registerListeners(versionSupport.getPlugin(), new ShopItemRestoreListener.DefaultRestoreInvClose());
    }

    private void registerListeners(Plugin plugin, Listener... listener) {
        for (Listener l : listener) {
            plugin.getServer().getPluginManager().registerEvents(l, plugin);
        }
    }
}
