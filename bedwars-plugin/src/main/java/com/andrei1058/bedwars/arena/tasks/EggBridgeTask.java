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

package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.events.gameplay.EggBridgeBuildEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.configuration.Sounds;
import com.andrei1058.bedwars.listeners.EggBridge;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import static com.andrei1058.bedwars.BedWars.nms;

@SuppressWarnings("WeakerAccess")
public class EggBridgeTask implements Runnable {

    private Egg projectile;
    private TeamColor teamColor;
    private Player player;
    private IArena arena;
    private BukkitTask task;

    public EggBridgeTask(Player player, Egg projectile, TeamColor teamColor) {
        IArena a = Arena.getArenaByPlayer(player);
        if (a == null) return;
        this.arena = a;
        this.projectile = projectile;
        this.teamColor = teamColor;
        this.player = player;
        task = Bukkit.getScheduler().runTaskTimer(BedWars.plugin, this, 0, 1);
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public Egg getProjectile() {
        return projectile;
    }

    public Player getPlayer() {
        return player;
    }

    public IArena getArena() {
        return arena;
    }

    @Override
    public void run() {

        Location loc = getProjectile().getLocation();

        if (getProjectile().isDead()
                || !arena.isPlayer(getPlayer())
                || getPlayer().getLocation().distance(getProjectile().getLocation()) > 27
                || getPlayer().getLocation().getY() - getProjectile().getLocation().getY() > 9) {
            EggBridge.removeEgg(projectile);
            return;
        }

        if (getPlayer().getLocation().distance(loc) > 4.0D) {

            Block b2 = loc.clone().subtract(0.0D, 2.0D, 0.0D).getBlock();
            if (!Misc.isBuildProtected(b2.getLocation(), getArena())) {
                if (b2.getType() == Material.AIR) {
                    b2.setType(nms.woolMaterial());
                    nms.setBlockTeamColor(b2, getTeamColor());
                    getArena().addPlacedBlock(b2);
                    Bukkit.getPluginManager().callEvent(new EggBridgeBuildEvent(getTeamColor(), getArena(), b2));
                    loc.getWorld().playEffect(b2.getLocation(), nms.eggBridge(), 3);
                    Sounds.playSound("egg-bridge-block", getPlayer());
                }
            }

            Block b3 = loc.clone().subtract(1.0D, 2.0D, 0.0D).getBlock();
            if (!Misc.isBuildProtected(b3.getLocation(), getArena())) {
                if (b3.getType() == Material.AIR) {
                    b3.setType(nms.woolMaterial());
                    nms.setBlockTeamColor(b3, getTeamColor());
                    getArena().addPlacedBlock(b3);
                    Bukkit.getPluginManager().callEvent(new EggBridgeBuildEvent(getTeamColor(), getArena(), b3));
                    loc.getWorld().playEffect(b3.getLocation(), nms.eggBridge(), 3);
                    Sounds.playSound("egg-bridge-block", getPlayer());
                }
            }

            Block b4 = loc.clone().subtract(0.0D, 2.0D, 1.0D).getBlock();
            if (!Misc.isBuildProtected(b4.getLocation(), getArena())) {
                if (b4.getType() == Material.AIR) {
                    b4.setType(nms.woolMaterial());
                    nms.setBlockTeamColor(b4, getTeamColor());
                    getArena().addPlacedBlock(b4);
                    Bukkit.getPluginManager().callEvent(new EggBridgeBuildEvent(getTeamColor(), getArena(), b4));
                    loc.getWorld().playEffect(b4.getLocation(), nms.eggBridge(), 3);
                    Sounds.playSound("egg-bridge-block", getPlayer());
                }
            }
        }
    }

    public void cancel(){
        task.cancel();
    }
}
