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

package com.andrei1058.bedwars.commands;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.support.paper.TeleportManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Misc {

    /**
     * This is used to spawn armorStands during the setup
     * so the player knows what he set
     *
     * @since api v6
     */
    public static void createArmorStand(String name, @NotNull Location location, String configLoc) {
        ArmorStand a = (ArmorStand) location.getWorld().spawnEntity(location.getBlock().getLocation().add(0.5, 2, 0.5), EntityType.ARMOR_STAND);
        a.setVisible(false);
        a.setMarker(true);
        a.setGravity(false);
        a.setCustomNameVisible(true);
        a.setCustomName(name);
        a.setMetadata("bw1058-setup", new FixedMetadataValue(BedWars.plugin, "hologram"));
        if (configLoc != null) {
            a.setMetadata("bw1058-loc", new FixedMetadataValue(BedWars.plugin, configLoc));
        }
    }

    /**
     * Remove an armor stand
     */
    public static void removeArmorStand(String contains, @NotNull Location location, String configLoc) {
        for (Entity e : location.getWorld().getNearbyEntities(location, 1, 3, 1)) {
            if (e.hasMetadata("bw1058-setup")) {
                if (e.hasMetadata("bw1058-loc")) {
                    if (e.getMetadata("bw1058-loc").get(0).asString().equalsIgnoreCase(configLoc)) {
                        if (contains != null){
                            if (!contains.isEmpty()){
                                if (ChatColor.stripColor(e.getCustomName()).contains(contains)){
                                    e.remove();
                                    return;
                                }
                            }
                        }
                        e.remove();
                    }
                } else {
                    e.remove();
                }
                continue;
            }
            if (e.getType() == EntityType.ARMOR_STAND) {
                if (!((ArmorStand) e).isVisible()) {
                    if (contains != null && e.getCustomName().contains(contains)) {
                        e.remove();
                    }
                }
            }
        }
    }

    /**
     * Find and set generators
     */
    public static void autoSetGen(Player p, String command, SetupSession setupSession, Material type) {
        if (type == Material.EMERALD_BLOCK) {
            if (setupSession.isAutoCreatedEmerald()) return;
            setupSession.setAutoCreatedEmerald(true);
        } else {
            if (setupSession.isAutoCreatedDiamond()) return;
            setupSession.setAutoCreatedDiamond(true);
        }
        detectGenerators(p.getLocation().add(0, -1, 0).getBlock().getLocation(), setupSession);
        Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
            for (Location l : setupSession.getSkipAutoCreateGen()) {
                Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
                    TeleportManager.teleport(p, l);
                    Bukkit.dispatchCommand(p, command + (l.add(0, -1, 0).getBlock().getType() == Material.EMERALD_BLOCK ? "emerald" : "diamond"));
                }, 20);
            }

        }, 20);
    }

    /**
     * @param origin block location under player
     */
    private static void detectGenerators(Location origin, SetupSession setupSession) {
        origin = origin.getBlock().getLocation();
        setupSession.addSkipAutoCreateGen(origin);
        Material target = origin.getBlock().getType();
        Material layout_z_minus = origin.clone().add(0, 1, -1).getBlock().getType();
        Material layout_z_plus = origin.clone().add(0, 1, 1).getBlock().getType();
        Material layout_x_minus = origin.clone().add(-1, 1, 0).getBlock().getType();
        Material layout_x_plus = origin.clone().add(1, 1, 0).getBlock().getType();
        Material layout_x_plus_z_plus = origin.clone().add(1, 1, 1).getBlock().getType();
        Material layout_x_plus_z_minus = origin.clone().add(1, 1, -1).getBlock().getType();
        Material layout_x_minus_z_plus = origin.clone().add(-1, 1, 1).getBlock().getType();
        Material layout_x_minus_z_minus = origin.clone().add(-1, 1, -1).getBlock().getType();

        String path = "generator." + (target == Material.DIAMOND_BLOCK ? "Diamond" : "Emerald");
        if (layout_z_minus == Material.AIR || layout_z_plus == Material.AIR || layout_x_minus == Material.AIR || layout_x_plus == Material.AIR ||
                layout_x_plus_z_plus == Material.AIR || layout_x_plus_z_minus == Material.AIR || layout_x_minus_z_plus == Material.AIR || layout_x_minus_z_minus == Material.AIR) {
            //It's better not to use it
            return;
        }
        List<Location> locations = setupSession.getConfig().getArenaLocations(path);
        for (int x = -150; x < 150; x++) {
            for (int z = -150; z < 150; z++) {
                Block b = origin.clone().add(x, 0, z).getBlock();
                if (b.getX() == origin.getBlockX() && b.getY() == origin.getBlockY() && b.getZ() == origin.getBlockZ())
                    continue;
                Location l = b.getLocation().clone().add(0, 1, 0);
                for (Location location : locations)
                    setupSession.getConfig().compareArenaLoc(location, b.getLocation().add(0, 1, 0));

                if (b.getType() == target) {
                    if (layout_z_minus == l.clone().add(0, 0, -1).getBlock().getType() &&
                            layout_z_plus == l.clone().add(0, 0, 1).getBlock().getType() &&
                            layout_x_minus == l.clone().add(-1, 0, 0).getBlock().getType() &&
                            layout_x_plus == l.clone().add(1, 0, 0).getBlock().getType() &&
                            layout_x_plus_z_minus == l.clone().add(1, 0, -1).getBlock().getType() &&
                            layout_x_plus_z_plus == l.clone().add(1, 0, 1).getBlock().getType() &&
                            layout_x_minus_z_plus == l.clone().add(-1, 0, 1).getBlock().getType() &&
                            layout_x_minus_z_minus == l.clone().add(-1, 0, -1).getBlock().getType()) {
                        if (!setupSession.getSkipAutoCreateGen().contains(l)) {
                            setupSession.addSkipAutoCreateGen(l);
                            detectGenerators(b.getLocation(), setupSession);
                        }
                    }
                }
            }
        }
    }
}
