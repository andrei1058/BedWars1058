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
