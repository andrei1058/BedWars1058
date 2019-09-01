package com.andrei1058.bedwars.shop.quickbuy;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.main.CategoryContent;
import com.andrei1058.bedwars.shop.main.ShopCategory;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

@SuppressWarnings("WeakerAccess")
public class QuickBuyTask extends BukkitRunnable {

    private UUID uuid;


    public QuickBuyTask(UUID uuid){
        this.uuid = uuid;
        this.runTaskLaterAsynchronously(Main.plugin, 20*7);
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

            if (!Main.getRemoteDatabase().hasQuickBuy(uuid)){
                if (Main.shop.getYml().get(ConfigPath.SHOP_QUICK_DEFAULTS_PATH) != null){
                    for (String s : Main.shop.getYml().getConfigurationSection(ConfigPath.SHOP_QUICK_DEFAULTS_PATH).getKeys(false)) {
                        if (Main.shop.getYml().get(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + "." + s + ".path") != null) {
                            if (Main.shop.getYml().get(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + "." + s + ".slot") == null){
                                continue;
                            }

                            try {
                                Integer.valueOf(Main.shop.getYml().getString(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + "." + s + ".slot"));
                            } catch (Exception ex){
                                Main.debug(Main.shop.getYml().getString(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + "." + s + ".slot") + " must be an integer!");
                                continue;
                            }

                            for (ShopCategory sc : ShopManager.getShop().getCategoryList()) {
                                for (CategoryContent cc : sc.getCategoryContentList()) {
                                    if (cc.getIdentifier().equals(Main.shop.getYml().getString(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + "." + s + ".path"))) {
                                        cache.setElement(Integer.valueOf(Main.shop.getYml().getString(ConfigPath.SHOP_QUICK_DEFAULTS_PATH + "." + s + ".slot")), cc);
                                    }
                                }
                            }

                        }
                    }
                }
            } else {
                for (int x : PlayerQuickBuyCache.quickSlots) {
                    String identifier = Main.getRemoteDatabase().getQuickBuySlots(uuid, x);
                    if (identifier.isEmpty()) continue;
                    if (identifier.equals(" ")) continue;
                    QuickBuyElement e = new QuickBuyElement(identifier, x);
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
