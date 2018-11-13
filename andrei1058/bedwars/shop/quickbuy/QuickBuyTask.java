package com.andrei1058.bedwars.shop.quickbuy;

import com.andrei1058.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class QuickBuyTask extends BukkitRunnable {

    private UUID uuid;


    public QuickBuyTask(UUID uuid){
        this.uuid = uuid;
        this.runTaskLater(Main.plugin, 20*7);
    }

    @Override
    public void run() {
        if (Bukkit.getPlayer(uuid).isOnline()){
            PlayerQuickBuyCache cache = PlayerQuickBuyCache.getQuickBuyCache(uuid);
            if (cache == null){
                cancel();
                return;
            }
            for (int x : PlayerQuickBuyCache.quickSlots){
                String identifier = Main.database.getQuickBuySlots(uuid, x);
                if (identifier.isEmpty()) continue;
                if (identifier.equals(" ")) continue;
                QuickBuyElement e = new QuickBuyElement(identifier, x);
                if (e.isLoaded()){
                    cache.addQuickElement(e);
                }
            }
        }
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
    }
}
