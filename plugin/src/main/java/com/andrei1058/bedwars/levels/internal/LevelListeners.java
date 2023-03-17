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

package com.andrei1058.bedwars.levels.internal;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.player.PlayerBedBreakEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerXpGainEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.configuration.LevelsConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class LevelListeners implements Listener {

    public static LevelListeners instance;

    public LevelListeners() {
        instance = this;
    }

    //create new level data on player join
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent e) {
        final UUID u = e.getPlayer().getUniqueId();
        // create empty level first
        new PlayerLevel(u, 1, 0);
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, () -> {
            //if (PlayerLevel.getLevelByPlayer(e.getPlayer().getUniqueId()) != null) return;
            Object[] levelData = BedWars.getRemoteDatabase().getLevelData(u);
            PlayerLevel.getLevelByPlayer(u).lazyLoad((Integer) levelData[0], (Integer) levelData[1]);
            //new PlayerLevel(e.getPlayer().getUniqueId(), (Integer)levelData[0], (Integer)levelData[1]);
            //Bukkit.broadcastMessage("LAZY LOAD");
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent e) {
        final UUID u = e.getPlayer().getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, () -> {
            PlayerLevel pl = PlayerLevel.getLevelByPlayer(u);
            pl.destroy();
        });
    }

    @EventHandler
    public void onGameEnd(GameEndEvent e) {
        for (UUID p : e.getWinners()) {
            if (PlayerLevel.getLevelByPlayer(p) != null) {
                Player p1 = Bukkit.getPlayer(p);
                if (p1 == null) continue;
                int xpAmount = LevelsConfig.levels.getInt("xp-rewards.game-win");
                if (xpAmount > 0){
                    PlayerLevel.getLevelByPlayer(p).addXp(xpAmount, PlayerXpGainEvent.XpSource.GAME_WIN);
                    p1.sendMessage(Language.getMsg(p1, Messages.XP_REWARD_WIN).replace("{xp}", String.valueOf(xpAmount)));
                }
                ITeam bwt = e.getArena().getExTeam(p1.getUniqueId());
                if (bwt != null) {
                    //noinspection deprecation
                    if (bwt.getMembersCache().size() > 1) {
                        int xpAmountPerTmt = LevelsConfig.levels.getInt("xp-rewards.per-teammate");
                        if (xpAmountPerTmt > 0) {
                            //noinspection deprecation
                            int tr = xpAmountPerTmt * bwt.getMembersCache().size();
                            PlayerLevel.getLevelByPlayer(p).addXp(tr, PlayerXpGainEvent.XpSource.PER_TEAMMATE);
                            p1.sendMessage(Language.getMsg(p1, "xp-reward-per-teammate").replace("{xp}", String.valueOf(tr)));
                        }
                    }
                }
            }
        }
        for (UUID p : e.getLosers()) {
            if (PlayerLevel.getLevelByPlayer(p) != null) {
                Player p1 = Bukkit.getPlayer(p);
                if (p1 == null) continue;
                ITeam bwt = e.getArena().getExTeam(p1.getUniqueId());
                if (bwt != null) {
                    //noinspection deprecation
                    if (bwt.getMembersCache().size() > 1) {
                        int xpAmountPerTmt = LevelsConfig.levels.getInt("xp-rewards.per-teammate");
                        if (xpAmountPerTmt > 0) {
                            //noinspection deprecation
                            int tr = LevelsConfig.levels.getInt("xp-rewards.per-teammate") * bwt.getMembersCache().size();
                            PlayerLevel.getLevelByPlayer(p).addXp(tr, PlayerXpGainEvent.XpSource.PER_TEAMMATE);
                            p1.sendMessage(Language.getMsg(p1, Messages.XP_REWARD_PER_TEAMMATE).replace("{xp}", String.valueOf(tr)));
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onArenaLeave(PlayerLeaveArenaEvent e) {
        final UUID u = e.getPlayer().getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, () -> {
            PlayerLevel pl = PlayerLevel.getLevelByPlayer(u);
            if (pl != null) pl.updateDatabase();
        });
    }

    @EventHandler
    public void onBreakBed(PlayerBedBreakEvent e) {
        Player player = e.getPlayer ();
        if (player == null) {
            return;
        }
        int bedDestroy = LevelsConfig.levels.getInt("xp-rewards.bed-destroyed");
        if (bedDestroy > 0) {
            PlayerLevel.getLevelByPlayer(player.getUniqueId()).addXp(bedDestroy, PlayerXpGainEvent.XpSource.BED_DESTROYED);
            player.sendMessage(Language.getMsg(player, Messages.XP_REWARD_BED_DESTROY).replace("{xp}", String.valueOf(bedDestroy)));
        }
    }

    @EventHandler
    public void onKill(PlayerKillEvent e) {
        Player player = e.getKiller ();
        Player victim = e.getVictim ();
        if (player == null || victim.equals(player)) {
            return;
        }
        int finalKill = LevelsConfig.levels.getInt("xp-rewards.final-kill");
        int regularKill = LevelsConfig.levels.getInt("xp-rewards.regular-kill");
        if (e.getCause ().isFinalKill ()) {
            if (finalKill > 0) {
                PlayerLevel.getLevelByPlayer(player.getUniqueId()).addXp(finalKill, PlayerXpGainEvent.XpSource.FINAL_KILL);
                player.sendMessage(Language.getMsg(player, Messages.XP_REWARD_FINAL_KILL).replace("{xp}", String.valueOf(finalKill)));
            }
        } else {
            if (regularKill > 0) {
                PlayerLevel.getLevelByPlayer(player.getUniqueId()).addXp(regularKill, PlayerXpGainEvent.XpSource.REGULAR_KILL);
                player.sendMessage(Language.getMsg(player, Messages.XP_REWARD_REGULAR_KILL).replace("{xp}", String.valueOf(regularKill)));
            }
        }
    }
}
