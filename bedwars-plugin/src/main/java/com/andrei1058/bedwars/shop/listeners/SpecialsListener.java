package com.andrei1058.bedwars.shop.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static com.andrei1058.bedwars.BedWars.nms;

public class SpecialsListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpecialInteract(PlayerInteractEvent e) {
        if (e.isCancelled()) return;
        Player p = e.getPlayer();
        ItemStack i = e.getItem();
        if (i == null) return;
        if (i.getType() == Material.AIR) return;
        IArena a = Arena.getArenaByPlayer(p);
        if (a == null) return;
        if (a.getRespawn().containsKey(e.getPlayer())) return;
        if (!a.isPlayer(p)) return;
        if (BedWars.shop.getYml().getBoolean(ConfigPath.SHOP_SPECIAL_SILVERFISH_ENABLE)) {
            if (!Misc.isProjectile(Material.valueOf(BedWars.shop.getYml().getString(ConfigPath.SHOP_SPECIAL_SILVERFISH_MATERIAL)))) {
                if (i.getType() == Material.valueOf(BedWars.shop.getYml().getString(ConfigPath.SHOP_SPECIAL_SILVERFISH_MATERIAL))
                        && nms.itemStackDataCompare(i, (short) BedWars.shop.getYml().getInt(ConfigPath.SHOP_SPECIAL_SILVERFISH_DATA))) {
                    e.setCancelled(true);
                    nms.spawnSilverfish(p.getLocation().add(0, 1, 0), a.getTeam(p), BedWars.shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_SPEED),
                            BedWars.shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_HEALTH), BedWars.shop.getInt(ConfigPath.SHOP_SPECIAL_SILVERFISH_DESPAWN),
                            BedWars.shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_DAMAGE));
                    if (!nms.isProjectile(i)) {
                        nms.minusAmount(p, i, 1);
                        p.updateInventory();
                    }
                }
            }
        }
        if (BedWars.shop.getYml().getBoolean(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_ENABLE)) {
            if (!Misc.isProjectile(Material.valueOf(BedWars.shop.getYml().getString(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_MATERIAL)))) {
                if (i.getType() == Material.valueOf(BedWars.shop.getYml().getString(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_MATERIAL))
                        && nms.itemStackDataCompare(i, (short) BedWars.shop.getYml().getInt(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_DATA))) {
                    e.setCancelled(true);
                    ITeam team = a.getTeam(p);
                    if (team== null){
                        Bukkit.broadcastMessage("TEAM IS NULL");
                        team = a.getPlayerTeam(p.getName());
                    }
                    if (team== null){
                        team = a.getExTeam(p.getUniqueId());
                        Bukkit.broadcastMessage("TEAM EX");
                    }
                    else Bukkit.broadcastMessage("PLAYER TEAM: " + team.getName());

                    nms.spawnIronGolem(p.getLocation().add(0, 1, 0), team, BedWars.shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_SPEED),
                            BedWars.shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_HEALTH), BedWars.shop.getInt(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_DESPAWN));
                    if (!nms.isProjectile(i)) {
                        nms.minusAmount(p, i, 1);
                        p.updateInventory();
                    }
                }
            }
        }
    }
}
