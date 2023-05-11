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

package com.tomkeuper.bedwars.shop.listeners;

import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.api.configuration.ConfigPath;
import com.tomkeuper.bedwars.api.events.player.PlayerBedBugSpawnEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerDreamDefenderSpawnEvent;
import com.tomkeuper.bedwars.arena.Arena;
import com.tomkeuper.bedwars.arena.Misc;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

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
        if (a.getRespawnSessions().containsKey(e.getPlayer())) return;
        if (!a.isPlayer(p)) return;
        if (BedWars.shop.getYml().getBoolean(ConfigPath.SHOP_SPECIAL_SILVERFISH_ENABLE)) {
            if (!Misc.isProjectile(Material.valueOf(BedWars.shop.getYml().getString(ConfigPath.SHOP_SPECIAL_SILVERFISH_MATERIAL)))) {
                if (i.getType() == Material.valueOf(BedWars.shop.getYml().getString(ConfigPath.SHOP_SPECIAL_SILVERFISH_MATERIAL))
                        && BedWars.nms.itemStackDataCompare(i, (short) BedWars.shop.getYml().getInt(ConfigPath.SHOP_SPECIAL_SILVERFISH_DATA))) {
                    e.setCancelled(true);
                    ITeam playerTeam = a.getTeam(p);
                    PlayerBedBugSpawnEvent event = new PlayerBedBugSpawnEvent(p, playerTeam, a);
                    BedWars.nms.spawnSilverfish(p.getTargetBlock((Set<Material>) null, 5).getLocation().add(0, 1, 0), playerTeam, BedWars.shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_SPEED),
                            BedWars.shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_HEALTH), BedWars.shop.getInt(ConfigPath.SHOP_SPECIAL_SILVERFISH_DESPAWN),
                            BedWars.shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_DAMAGE));
                    Bukkit.getPluginManager().callEvent(event);
                    if (!BedWars.nms.isProjectile(i)) {
                        BedWars.nms.minusAmount(p, i, 1);
                        p.updateInventory();
                    }
                }
            }
        }
        if (BedWars.shop.getYml().getBoolean(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_ENABLE)) {
            if (!Misc.isProjectile(Material.valueOf(BedWars.shop.getYml().getString(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_MATERIAL)))) {
                if (i.getType() == Material.valueOf(BedWars.shop.getYml().getString(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_MATERIAL))
                        && BedWars.nms.itemStackDataCompare(i, (short) BedWars.shop.getYml().getInt(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_DATA))) {
                    e.setCancelled(true);
                    ITeam playerTeam = a.getTeam(p);
                    PlayerDreamDefenderSpawnEvent event = new PlayerDreamDefenderSpawnEvent(p, playerTeam, a);
                    BedWars.nms.spawnIronGolem(p.getTargetBlock((Set<Material>) null, 5).getLocation().add(0, 1, 0), playerTeam, BedWars.shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_SPEED),
                            BedWars.shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_HEALTH), BedWars.shop.getInt(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_DESPAWN));
                    Bukkit.getPluginManager().callEvent(event);
                    if (!BedWars.nms.isProjectile(i)) {
                        BedWars.nms.minusAmount(p, i, 1);
                        p.updateInventory();
                    }
                }
            }
        }
    }
}
