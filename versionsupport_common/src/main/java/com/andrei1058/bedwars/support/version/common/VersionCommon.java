package com.andrei1058.bedwars.support.version.common;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.server.VersionSupport;
import com.andrei1058.bedwars.listeners.Interact_1_13Plus;
import com.andrei1058.bedwars.listeners.ItemDropPickListener;
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
            registerListeners(versionSupport.getPlugin(), new ItemDropPickListener.PlayerDrop(), new ItemDropPickListener.PlayerPickup(),
            new ShopItemRestoreListener.PlayerDrop(), new ShopItemRestoreListener.PlayerPickup());
        }

        // 13 and newer
        if (versionSupport.getVersion() > 5){
            registerListeners(versionSupport.getPlugin(), new ShopItemRestoreListener.EntityDrop(), new Interact_1_13Plus(), new ItemDropPickListener.EntityDrop());
        }

        // 1.12 and newer
        if (versionSupport.getVersion() > 4){
            registerListeners(versionSupport.getPlugin(), new ItemDropPickListener.EntityPickup(), new ShopItemRestoreListener.EntityPickup());
        }
        // common
        registerListeners(versionSupport.getPlugin(), new ItemDropPickListener.GeneratorCollect(), new ShopItemRestoreListener.DefaultRestoreInvClose());
    }

    private void registerListeners(Plugin plugin, Listener... listener) {
        for (Listener l : listener) {
            plugin.getServer().getPluginManager().registerEvents(l, plugin);
        }
    }
}
