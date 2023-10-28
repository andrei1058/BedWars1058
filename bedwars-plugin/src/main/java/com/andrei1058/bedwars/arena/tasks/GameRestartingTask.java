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
import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.api.arena.shop.ShopHolo;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.tasks.RestartingTask;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.configuration.Sounds;
import com.andrei1058.bedwars.support.paper.TeleportManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class GameRestartingTask implements Runnable, RestartingTask {

    private Arena arena;
    private int restarting = BedWars.config.getInt(ConfigPath.GENERAL_CONFIGURATION_RESTART) + 5;
    private final BukkitTask task;

    public GameRestartingTask(@NotNull Arena arena) {
        this.arena = arena;
        task = Bukkit.getScheduler().runTaskTimer(BedWars.plugin, this, 0, 20L);
        Sounds.playSound("game-end", arena.getPlayers());
        Sounds.playSound("game-end", arena.getSpectators());

        // teleport to alive players
        if (arena.getConfig().getGameOverridableBoolean(ConfigPath.GENERAL_GAME_END_TELEPORT_ELIMINATED)) {
            if (!arena.getPlayers().isEmpty()) {
                Random r = new Random();
                for (Player spectator : arena.getSpectators()) {
                    Player target = arena.getPlayers().get(r.nextInt(arena.getPlayers().size()));
                    Location loc = target.getLocation().clone();
                    loc.setDirection(target.getLocation().getDirection().multiply(-1));
                    loc.add(0,2,0);

                    TeleportManager.teleportC(spectator, loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
            }
        }

        // show eliminated players
        if (arena.getConfig().getGameOverridableBoolean(ConfigPath.GENERAL_GAME_END_SHOW_ELIMINATED)) {
            for (Player spectator : arena.getSpectators()) {
                ITeam exTeam = arena.getExTeam(spectator.getUniqueId());
                if (null == exTeam) {
                    continue;
                }
                spectator.removePotionEffect(PotionEffectType.INVISIBILITY);
                for (Player player : arena.getPlayers()) {
                    BedWars.nms.spigotShowPlayer(player, spectator);
                    BedWars.nms.spigotShowPlayer(spectator, player);
                }
            }
        }
    }

    /**
     * Get task ID
     */
    public int getTask() {
        return task.getTaskId();
    }

    @Override
    public int getRestarting() {
        return restarting;
    }

    public Arena getArena() {
        return arena;
    }

    @Override
    public BukkitTask getBukkitTask() {
        return task;
    }

    @Override
    public void run() {

        restarting--;

        if (getArena().getPlayers().isEmpty() && restarting > 9) restarting = 9;
        if (restarting == 7) {
            for (Player on : new ArrayList<>(getArena().getPlayers())) {
                getArena().removePlayer(on, BedWars.getServerType() == ServerType.BUNGEE);
            }
            for (Player on : new ArrayList<>(getArena().getSpectators())) {
                getArena().removeSpectator(on, BedWars.getServerType() == ServerType.BUNGEE);
            }
        } else if (restarting == 4) {
            ShopHolo.clearForArena(getArena());
            for (Entity e : getArena().getWorld().getEntities()) {
                if (e.getType() == EntityType.PLAYER) {
                    Player p = (Player) e;
                    Misc.moveToLobbyOrKick(p, getArena(), true);
                    if (getArena().isSpectator(p)) getArena().removeSpectator(p, false);
                    if (getArena().isPlayer(p)) getArena().removePlayer(p, false);
                }
            }
            for (IGenerator eg : getArena().getOreGenerators()) {
                eg.disable();
            }
            for (ITeam t : getArena().getTeams()) {
                for (IGenerator eg : t.getGenerators()) {
                    eg.disable();
                }
            }
        } else if (restarting == 0) {
            getArena().restart();
            task.cancel();
            arena = null;
        }
    }

    public void cancel() {
        task.cancel();
    }

}
