package com.andrei1058.bedwars.shop.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static com.andrei1058.bedwars.Main.nms;

public class SpecialsListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpecialInteract(PlayerInteractEvent e) {
        if (e.isCancelled()) return;
        Player p = e.getPlayer();

        ItemStack i = nms.getItemInHand(p);
        if (i == null) return;
        if (i.getType() == Material.AIR) return;

        Arena a = Arena.getArenaByPlayer(p);
        if (a == null) return;
        if (a.getRespawn().containsKey(e.getPlayer())) return;
        if (!a.isPlayer(p)) return;

        if (Main.shop2.getYml().getBoolean(ConfigPath.SHOP_SPECIAL_SILVERFISH_ENABLE)) {
            if (!Misc.isProjectile(Material.valueOf(Main.shop2.getYml().getString(ConfigPath.SHOP_SPECIAL_SILVERFISH_MATERIAL)))) {
                if (i.getType() == Material.valueOf(Main.shop2.getYml().getString(ConfigPath.SHOP_SPECIAL_SILVERFISH_MATERIAL))
                        && nms.itemStackDataCompare(i, (short) Main.shop2.getYml().getInt(ConfigPath.SHOP_SPECIAL_SILVERFISH_DATA))) {
                    e.setCancelled(true);
                    nms.spawnSilverfish(p.getLocation().add(0, 1, 0), a.getTeam(p));
                    if (!nms.isProjectile(i)) {
                        nms.minusAmount(p, i, 1);
                        p.updateInventory();
                    }
                }
            }
        }
        if (Main.shop2.getYml().getBoolean(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_ENABLE)) {
            if (!Misc.isProjectile(Material.valueOf(Main.shop2.getYml().getString(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_MATERIAL)))) {
                if (i.getType() == Material.valueOf(Main.shop2.getYml().getString(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_MATERIAL))
                        && nms.itemStackDataCompare(i, (short) Main.shop2.getYml().getInt(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_DATA))) {
                    e.setCancelled(true);
                    nms.spawnSilverfish(p.getLocation().add(0, 1, 0), a.getTeam(p));
                    if (!nms.isProjectile(i)) {
                        nms.minusAmount(p, i, 1);
                        p.updateInventory();
                    }
                }
            }
        }
    }
}
