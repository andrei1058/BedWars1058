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

package com.andrei1058.bedwars.halloween;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerJoinArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.events.player.PlayerXpGainEvent;
import com.andrei1058.bedwars.api.events.server.ArenaDisableEvent;
import com.andrei1058.bedwars.api.events.server.ArenaEnableEvent;
import com.andrei1058.bedwars.api.events.server.ArenaRestartEvent;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.levels.internal.PlayerLevel;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class HalloweenListener implements Listener {

    private final Sound ambienceSound;
    private final Sound ghastSound;

    public HalloweenListener() {
        ambienceSound = Sound.valueOf(BedWars.getForCurrentVersion("AMBIENCE_CAVE", "AMBIENT_CAVE", "AMBIENT_CAVE"));
        ghastSound = Sound.valueOf(BedWars.getForCurrentVersion("GHAST_SCREAM2", "ENTITY_GHAST_SCREAM", "ENTITY_GHAST_SCREAM"));
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.isCancelled()) return;
        LivingEntity entity = e.getEntity();
        if (entity.getType() == EntityType.ARMOR_STAND) return;
        entity.getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldLoad(WorldLoadEvent e) {
        // check if it is time to disable this special
        if (HalloweenSpecial.getINSTANCE() != null) {
            if (!HalloweenSpecial.checkAvailabilityDate()) {
                CreatureSpawnEvent.getHandlerList().unregister(this);
            }
        }
    }

    @EventHandler
    public void onPlayerDie(PlayerKillEvent e) {
        if (e.getKiller() != null) {
            Location location = e.getVictim().getLocation().add(0, 1, 0);
            if (location.getBlock().getType() == Material.AIR) {
                location.getWorld().playSound(location, ghastSound, 2f, 1f);
                if (!Misc.isBuildProtected(location, e.getArena())) {
                    location.getBlock().setType(Material.valueOf(BedWars.getForCurrentVersion("WEB", "WEB", "COBWEB")));
                    e.getArena().addPlacedBlock(location.getBlock());
                    location.getBlock().setMetadata("give-bw-exp", new FixedMetadataValue(BedWars.plugin, "ok"));
                    CobWebRemover remover = CobWebRemover.getByArena(e.getArena());
                    if (remover != null) {
                        remover.addCobWeb(location.getBlock());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        if (e.getBlock().hasMetadata("give-bw-exp")) {
            PlayerLevel level = PlayerLevel.getLevelByPlayer(e.getPlayer().getUniqueId());
            if (level != null) {
                e.getBlock().getDrops().clear();
                level.addXp(5, PlayerXpGainEvent.XpSource.OTHER);
                e.getPlayer().sendMessage(ChatColor.GOLD + "+5 xp!");
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinArenaEvent e) {
        if (!e.isSpectator()) {
            Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), ambienceSound, 3f, 1f), 20L);
        }
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent e) {
        if (e.getNewState() == GameState.restarting) {
            CobWebRemover remover = CobWebRemover.getByArena(e.getArena());
            if (remover != null) {
                remover.destroy();
            }
        }
    }

    @EventHandler
    public void onRestart(ArenaRestartEvent e) {
        CobWebRemover remover = CobWebRemover.getByArenaWorld(e.getWorldName());
        if (remover != null) {
            remover.destroy();
        }
    }

    @EventHandler
    public void onDisable(ArenaDisableEvent e) {
        CobWebRemover remover = CobWebRemover.getByArenaWorld(e.getWorldName());
        if (remover != null) {
            remover.destroy();
        }
    }

    @EventHandler
    public void onEnable(ArenaEnableEvent e){
        new CobWebRemover(e.getArena());
    }
}
