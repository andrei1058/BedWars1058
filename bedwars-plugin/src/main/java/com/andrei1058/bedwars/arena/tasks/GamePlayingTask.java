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
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.player.PlayerInvisibilityPotionEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.tasks.PlayingTask;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.andrei1058.bedwars.BedWars.nms;
import static com.andrei1058.bedwars.BedWars.plugin;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class GamePlayingTask implements Runnable, PlayingTask {

    private Arena arena;
    private BukkitTask task;
    private int beds_destroy_countdown, dragon_spawn_countdown, game_end_countdown;
    private final boolean temporaryWallMod;
    private int temporaryWallModCountdown = -1;
    private boolean shootersEnabled;
    private int shootersCooldwan = 0;
    private int shootersRad = 0;

    public GamePlayingTask(Arena arena) {
        this.arena = arena;
        this.beds_destroy_countdown = BedWars.config.getInt(ConfigPath.GENERAL_CONFIGURATION_BEDS_DESTROY_COUNTDOWN);
        this.dragon_spawn_countdown = BedWars.config.getInt(ConfigPath.GENERAL_CONFIGURATION_DRAGON_SPAWN_COUNTDOWN);
        this.game_end_countdown = BedWars.config.getInt(ConfigPath.GENERAL_CONFIGURATION_GAME_END_COUNTDOWN);
        this.temporaryWallMod = arena.getConfig().getBoolean(ConfigPath.ARENA_TEMPORARY_WALL_MOD);
        if (temporaryWallMod) {
            temporaryWallModCountdown = arena.getConfig().getInt(ConfigPath.ARENA_TEMPORARY_WALL_TIME_TO_DELETE);
        }
        this.shootersEnabled = arena.getConfig().getBoolean(ConfigPath.ARENA_SHOOTERS_ENABLED);
//        if (shootersEnabled) {
//            shootersCooldwan = arena.getConfig().getInt(ConfigPath.ARENA_SHOOTERS_COOLDAWN);
//        }

        this.task = Bukkit.getScheduler().runTaskTimer(BedWars.plugin, this, 0, 20L);
    }

    public Arena getArena() {
        return arena;
    }

    @Override
    public BukkitTask getBukkitTask() {
        return task;
    }

    /**
     * Get task ID
     */
    public int getTask() {
        return task.getTaskId();
    }

    public int getBedsDestroyCountdown() {
        return beds_destroy_countdown;
    }

    public int getDragonSpawnCountdown() {
        return dragon_spawn_countdown;
    }

    public int getTemporaryWallModCountdown() {
        return temporaryWallModCountdown;
    }

    public int getGameEndCountdown() {
        return game_end_countdown;
    }

    @Override
    public void run() {

        if (temporaryWallMod) {
            if (temporaryWallModCountdown > 0) {
                temporaryWallModCountdown--;
                if (temporaryWallModCountdown == 0) {
                    System.out.println("Cтена рушится! (По крайней мере должна)");
                    arena.getWallBlocksPos().forEach(blockPos -> {
                        arena.getWorld().getBlockAt(
                                blockPos.get(0),
                                blockPos.get(1),
                                blockPos.get(2)
                        )
                        .setType(Material.AIR);
                    });
                    if (arena.getRegionWallStartEnd()[1] >= arena.getRegionWallStartEnd()[0]) {
                        arena.getRegionsList().subList(arena.getRegionWallStartEnd()[0], arena.getRegionWallStartEnd()[1]).clear();
                    }
                    arena.getPlayers().forEach(
                            player -> {player.sendTitle("", "§aСТЕНА ПАЛА", 10, 60, 20);}
                    );
                }
            }
        }

        if (shootersEnabled) {
            if (shootersCooldwan == 0) {
                shooterFunc();
                shootersCooldwan = arena.getConfig().getInt(ConfigPath.ARENA_SHOOTERS_COOLDAWN);
            }
            else {
                shootersCooldwan--;
            }
        }

        World world = arena.getWorld();

        arena.getPlayers().forEach(player -> {
            Location playerLocation = player.getLocation();
            playerLocation.add(0, -1, 0);
            Block block = world.getBlockAt(playerLocation);
            if (block.getType() != Material.AIR) {
                arena.setLastStandBlock(player.getName(), block.getLocation().add(0.5, 1, 0.5));
//                System.out.println("X: " + (block.getLocation().getX()+0.5) + " Y: " + (block.getLocation().getY()+1) + " Z: " + (block.getLocation().getZ()+0.5));
            }
        });

        switch (getArena().getNextEvent()) {
            case EMERALD_GENERATOR_TIER_II:
            case EMERALD_GENERATOR_TIER_III:
            case DIAMOND_GENERATOR_TIER_II:
            case DIAMOND_GENERATOR_TIER_III:
                if (getArena().upgradeDiamondsCount > 0) {
                    getArena().upgradeDiamondsCount--;
                    if (getArena().upgradeDiamondsCount == 0) {
                        getArena().updateNextEvent();
                    }
                }
                if (getArena().upgradeEmeraldsCount > 0) {
                    getArena().upgradeEmeraldsCount--;
                    if (getArena().upgradeEmeraldsCount == 0) {
                        getArena().updateNextEvent();
                    }
                }
                break;
            case BEDS_DESTROY:
                beds_destroy_countdown--;
                if (getBedsDestroyCountdown() == 0) {
                    for (Player p : getArena().getPlayers()) {
                        nms.sendTitle(p, getMsg(p, Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED), getMsg(p, Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED), 0, 40, 10);
                        p.sendMessage(getMsg(p, Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED));
                    }
                    for (Player p : getArena().getSpectators()) {
                        nms.sendTitle(p, getMsg(p, Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED), getMsg(p, Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED), 0, 40, 10);
                        p.sendMessage(getMsg(p, Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED));
                    }
                    for (ITeam t : getArena().getTeams()) {
                        t.setBedDestroyed(true);
                    }
                    getArena().updateNextEvent();
                }
                break;
            case ENDER_DRAGON:
                dragon_spawn_countdown--;
                if (getDragonSpawnCountdown() == 0) {
                    for (Player p : getArena().getPlayers()) {
                        nms.sendTitle(p, getMsg(p, Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH), getMsg(p, Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH), 0, 40, 10);
                        for (ITeam t : getArena().getTeams()) {
                            if (t.getMembers().isEmpty()) continue;
                            p.sendMessage(getMsg(p, Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH).replace("{TeamDragons}", String.valueOf(t.getDragons()))
                                    .replace("{TeamColor}", t.getColor().chat().toString()).replace("{TeamName}", t.getDisplayName(Language.getPlayerLanguage(p))));
                        }
                    }
                    for (Player p : getArena().getSpectators()) {
                        nms.sendTitle(p, getMsg(p, Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH), getMsg(p, Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH), 0, 40, 10);
                        for (ITeam t : getArena().getTeams()) {
                            if (t.getMembers().isEmpty()) continue;
                            p.sendMessage(getMsg(p, Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH).replace("{TeamDragons}", String.valueOf(t.getDragons()))
                                    .replace("{TeamColor}", t.getColor().chat().toString()).replace("{TeamName}", t.getDisplayName(Language.getPlayerLanguage(p))));
                        }
                    }
                    getArena().updateNextEvent();
                    for (ITeam team : arena.getTeams()){
                        for (IGenerator o : team.getGenerators()) {
                            Location l = o.getLocation();
                            for (int y = 0; y < 20; y++) {
                                l.clone().subtract(0, y, 0).getBlock().setType(Material.AIR);
                            }
                        }
                    }
                    for (ITeam t : getArena().getTeams()) {
                        if (t.getMembers().isEmpty()) continue;
                        for (int x = 0; x < t.getDragons(); x++) {
                            nms.spawnDragon(getArena().getConfig().getArenaLoc("waiting.Loc").add(0, 10, 0), t);
                        }
                    }
                }
                break;
            case GAME_END:
                game_end_countdown--;
                if (getGameEndCountdown() == 0) {
                    getArena().checkWinner();
                    getArena().changeStatus(GameState.restarting);
                }
                break;
        }
        int distance = 0;
        for (ITeam t : getArena().getTeams()) {
            if (t.getSize() > 1) {
                for (Player p : t.getMembers()) {
                    for (Player p2 : t.getMembers()) {
                        if (p2 == p) continue;
                        if (distance == 0) {
                            distance = (int) p.getLocation().distance(p2.getLocation());
                        } else if ((int) p.getLocation().distance(p2.getLocation()) < distance) {
                            distance = (int) p.getLocation().distance(p2.getLocation());
                        }
                    }
                    nms.playAction(p, getMsg(p, Messages.FORMATTING_ACTION_BAR_TRACKING).replace("{team}", t.getColor().chat() + t.getDisplayName(Language.getPlayerLanguage(p)))
                            .replace("{distance}", t.getColor().chat().toString() + distance).replace("&", "§"));
                }
            }

            // spawn items
            for (IGenerator o : t.getGenerators()) {
                o.spawn();
            }
        }

        /* AFK SYSTEM FOR PLAYERS */
        int current = 0;
        for (Player p : getArena().getPlayers()) {
            if (Arena.afkCheck.get(p.getUniqueId()) == null) {
                Arena.afkCheck.put(p.getUniqueId(), current);
            } else {
                current = Arena.afkCheck.get(p.getUniqueId());
                current++;
                Arena.afkCheck.replace(p.getUniqueId(), current);
                if (current == 45) {
                    BedWars.getAPI().getAFKUtil().setPlayerAFK(p, true);
                }
            }
        }

        /* RESPAWN SESSION */
        if (!getArena().getRespawnSessions().isEmpty()) {
            for (Map.Entry<Player, Integer> e : getArena().getRespawnSessions().entrySet()) {
                if (e.getValue() <= 0) {
                    IArena a = Arena.getArenaByPlayer(e.getKey());
                    if (a == null) {
                        getArena().getRespawnSessions().remove(e.getKey());
                        continue;
                    }
                    ITeam t = a.getTeam(e.getKey());
                    if (t == null){
                        a.addSpectator(e.getKey(), true, null);
                    } else {
                        t.respawnMember(e.getKey());
                        e.getKey().setAllowFlight(false);
                        e.getKey().setFlying(false);
                    }
                } else {
                    nms.sendTitle(e.getKey(), getMsg(e.getKey(), Messages.PLAYER_DIE_RESPAWN_TITLE).replace("{time}",
                            String.valueOf(e.getValue())), getMsg(e.getKey(), Messages.PLAYER_DIE_RESPAWN_SUBTITLE).replace("{time}",
                            String.valueOf(e.getValue())), 0, 30, 10);
                    e.getKey().sendMessage(getMsg(e.getKey(), Messages.PLAYER_DIE_RESPAWN_CHAT).replace("{time}", String.valueOf(e.getValue())));
                    getArena().getRespawnSessions().replace(e.getKey(), e.getValue() - 1);
                }
            }
        }

        /* INVISIBILITY FOR ARMOR */
        if (!getArena().getShowTime().isEmpty()) {
            for (Map.Entry<Player, Integer> e : getArena().getShowTime().entrySet()) {
                if (e.getValue() <= 0) {
                    for (Player p : e.getKey().getWorld().getPlayers()) {
                        nms.showArmor(e.getKey(), p);
                        //nms.showPlayer(e.getKey(), p);
                    }
                    e.getKey().removePotionEffect(PotionEffectType.INVISIBILITY);
                    getArena().getShowTime().remove(e.getKey());
                    Bukkit.getPluginManager().callEvent(new PlayerInvisibilityPotionEvent(PlayerInvisibilityPotionEvent.Type.REMOVED, getArena().getTeam(e.getKey()), e.getKey(), getArena()));
                } else {
                    getArena().getShowTime().replace(e.getKey(), e.getValue() - 1);
                }
            }
        }

        /* SPAWN ITEMS */
        for (IGenerator o : getArena().getOreGenerators()) {
            o.spawn();
        }
    }

    private void shooterFunc() {
        arena.getPlayers().forEach(
                player -> {
           Location playerLocation = player.getLocation();
           arena.getShootersPos().forEach(
               shooterPos -> {
                   shooterPos = shooterPos.clone().add(0.5, 0, 0.5);
                   Vector vectorSP = playerLocation.toVector().subtract(shooterPos.toVector());
                   System.out.println(vectorSP.length());
                   if (vectorSP.length() <= arena.getConfig().getInt(ConfigPath.ARENA_SHOOTERS_RAD)) {
//                       spawnArrowShooter(vectorSP, shooterPos);
                       Vector arrowVector = vectorSP.normalize();
                       double arrowSpeed = 2;
                       Location spawnArrowLocation = shooterPos.clone().add(arrowVector.clone().multiply(1.1));
                       System.out.println(spawnArrowLocation);
                       Arrow arrow = (Arrow) arena.getWorld().spawnEntity(spawnArrowLocation, EntityType.ARROW);
                       arrow.setGravity(false);
                       arrow.setVelocity(arrowVector.clone().multiply(arrowSpeed));
                       removeArrowFunc(arrow, shooterPos);
                   }
               }
           );
        });
    };

    private void removeArrowFunc(Arrow arrow, Location startPos) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (
                    arrow.isDead()
                    || arrow.getLocation().toVector().subtract(startPos.toVector()).length() > arena.getConfig().getInt(ConfigPath.ARENA_SHOOTERS_RAD)) {
                    arrow.remove();
                    this.cancel();
                    System.out.println("Стрела сдохла");
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

//    private void spawnArrowShooter(Vector arrowVector, Location shooterPos) {
//        arrowVector = arrowVector.normalize();
//        double arrowSpeed = 2;
//        Location spawnArrowLocation = shooterPos.add(arrowVector.clone().multiply(1.1));
//        Arrow arrow = (Arrow) arena.getWorld().spawnEntity(spawnArrowLocation, EntityType.ARROW);
//        arrow.setVelocity(arrowVector.clone().multiply(arrowSpeed));
//    }

    public void cancel() {
        task.cancel();
    }
}


