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
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

import static com.andrei1058.bedwars.BedWars.nms;
import static com.andrei1058.bedwars.BedWars.plugin;

@SuppressWarnings("WeakerAccess")
public class EggBridgeTask implements Runnable {

    private Egg projectile;
    private TeamColor teamColor;
    private Player player;
    private IArena arena;
    private BukkitTask task;

//    private final List<Block> eggBlocks = new ArrayList<>();
    private final HashMap<Block, Integer> eggBlocks = new HashMap<>();

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
            setEggBlock(b2, loc);

            Block b3 = loc.clone().subtract(1.0D, 2.0D, 0.0D).getBlock();
            setEggBlock(b3, loc);

            Block b4 = loc.clone().subtract(0.0D, 2.0D, 1.0D).getBlock();
            setEggBlock(b4, loc);
        }
    }

    private void setEggBlock(Block block, Location loc) {
        if (!Misc.isBuildProtected(block.getLocation(), getArena())) {
            if (block.getType() == Material.AIR) {
                block.setType(Material.SNOW_BLOCK);
                eggBlocks.put(block, 0);
                getArena().addPlacedBlock(block);
                Bukkit.getPluginManager().callEvent(new EggBridgeBuildEvent(getTeamColor(), getArena(), block));
                loc.getWorld().playEffect(block.getLocation(), nms.eggBridge(), 3);
                Sounds.playSound("egg-bridge-block", getPlayer());
                removeBlock(block);
            }
        }
    }

    private void removeBlock(Block block) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (block.getType() != Material.SNOW_BLOCK) {
                    eggBlocks.remove(block);
                    this.cancel();
                    return;
                } else if (eggBlocks.get(block) == 10) {
                    block.setType(Material.AIR);
                    eggBlocks.remove(block);
                    this.cancel();
                    return;
                }
                eggBlocks.put(block, eggBlocks.get(block) + 1);
            }
        }.runTaskTimer(plugin, 0L, 10L);
    }

    public void cancel(){
        task.cancel();
    }
}
