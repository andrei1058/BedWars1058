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

package com.tomkeuper.bedwars.stats;

import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.api.events.gameplay.GameEndEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerBedBreakEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerKillEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerStatChangeEvent;
import com.tomkeuper.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Instant;
import java.util.UUID;

public class StatsListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            // Do nothing if login fails
            return;
        }
        PlayerStats stats = BedWars.getRemoteDatabase().fetchStats(event.getUniqueId());
        stats.setName(event.getName());
        BedWars.getStatsManager().put(event.getUniqueId(), stats);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            // Prevent memory leak if login fails
            BedWars.getStatsManager().remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onBedBreak(PlayerBedBreakEvent event) {
        PlayerStats stats = BedWars.getStatsManager().get(event.getPlayer().getUniqueId());
        //store beds destroyed
        PlayerStatChangeEvent ev = new PlayerStatChangeEvent(event.getPlayer(), event.getArena());
        Bukkit.getPluginManager().callEvent(ev); //call player stat change event for bed destroyer
        if(ev.isCancelled()) return;
        stats.setBedsDestroyed(stats.getBedsDestroyed() + 1);
    }

    @EventHandler
    public void onPlayerKill(PlayerKillEvent event) {
        PlayerStats victimStats = BedWars.getStatsManager().get(event.getVictim().getUniqueId());
        // If killer is not null and not equal to victim
        PlayerStats killerStats = !event.getVictim().equals(event.getKiller()) ?
                (event.getKiller() == null ? null : BedWars.getStatsManager().getUnsafe(event.getKiller().getUniqueId())) : null;

        PlayerStatChangeEvent ev = new PlayerStatChangeEvent(event.getVictim(), event.getArena());
        PlayerStatChangeEvent ev1 = new PlayerStatChangeEvent(event.getKiller(), event.getArena());
        if (event.getCause().isFinalKill()) {
            Bukkit.getPluginManager().callEvent(ev); //call player stat change event for victim
            if(ev.isCancelled()) return;
            //store final deaths
            victimStats.setFinalDeaths(victimStats.getFinalDeaths() + 1);
            //store losses
            victimStats.setLosses(victimStats.getLosses() + 1);
            //store games played
            victimStats.setGamesPlayed(victimStats.getGamesPlayed() + 1);
            Bukkit.getPluginManager().callEvent(ev1); //call player stat change event for killer
            if(ev1.isCancelled()) return;
            //store final kills
            if (killerStats != null) killerStats.setFinalKills(killerStats.getFinalKills() + 1);
        } else {
            Bukkit.getPluginManager().callEvent(ev); //call player stat change event for victim
            if(ev.isCancelled()) return;
            //store deaths
            victimStats.setDeaths(victimStats.getDeaths() + 1);
            //store kills
            Bukkit.getPluginManager().callEvent(ev1); //call player stat change event for killer
            if(ev1.isCancelled()) return;
            if (killerStats != null) killerStats.setKills(killerStats.getKills() + 1);
        }
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        for (UUID uuid : event.getWinners()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            if (!player.isOnline()) continue;

            PlayerStats stats = BedWars.getStatsManager().get(uuid);

            PlayerStatChangeEvent ev = new PlayerStatChangeEvent(player, event.getArena());
            Bukkit.getPluginManager().callEvent(ev);
            if(ev.isCancelled()) return;
            // store wins even if is in another game because he assisted this team
            // the ones who abandoned are already removed from the winners list
            stats.setWins(stats.getWins() + 1);

            // store games played
            // give if he remained in this arena till the end even if was eliminated
            // for those who left games played are updated in arena leave listener
            IArena playerArena = Arena.getArenaByPlayer(player);
            if (playerArena != null && playerArena.equals(event.getArena())) {
                Bukkit.getPluginManager().callEvent(ev);
                if(ev.isCancelled()) return;
                stats.setGamesPlayed(stats.getGamesPlayed() + 1);
            }
        }
    }

    @EventHandler
    public void onArenaLeave(PlayerLeaveArenaEvent event) {
        final Player player = event.getPlayer();

        ITeam team = event.getArena().getExTeam(player.getUniqueId());
        if (team == null) {
            return; // The player didn't play this game
        }

        if (event.getArena().getStatus() == GameState.starting || event.getArena().getStatus() == GameState.waiting) {
            return; // Game didn't start
        }

        PlayerStats playerStats = BedWars.getStatsManager().get(player.getUniqueId());
        // sometimes can be null due to scheduling delays
        if (playerStats == null) return;

        PlayerStatChangeEvent ev = new PlayerStatChangeEvent(player, event.getArena());
        Bukkit.getPluginManager().callEvent(ev); //call player stat change event for player left arena
        if(ev.isCancelled()) return;
        // Update last play and first play (if required)
        Instant now = Instant.now();
        playerStats.setLastPlay(now);
        if (playerStats.getFirstPlay() == null) {
            Bukkit.getPluginManager().callEvent(ev); //call player stat change event for player that left
            if(ev.isCancelled()) return;
            playerStats.setFirstPlay(now);
        }

        // Check quit abuse
        if (event.getArena().getStatus() == GameState.playing) {
            // Only if the player left the arena while the game was running
            if (team.isBedDestroyed()) {
                // Only if the team had the bed destroyed

                // Punish player if bed is destroyed and he disconnects without getting killed
                // if he is not in the spectators list it means he did not pass trough player kill event and he did not receive
                // the penalty bellow.
                if (event.getArena().isPlayer(player)) {
                    Bukkit.getPluginManager().callEvent(ev); //call player stat change event for player that left
                    if(ev.isCancelled()) return;
                    playerStats.setFinalDeaths(playerStats.getFinalDeaths() + 1);
                    playerStats.setLosses(playerStats.getLosses() + 1);
                }

                // Reward attacker
                // if attacker is not null it means the victim did pvp log out
                Player damager = event.getLastDamager();
                ITeam killerTeam = event.getArena().getTeam(damager);
                PlayerStatChangeEvent ev1 = new PlayerStatChangeEvent(damager, event.getArena());
                if (damager != null && event.getArena().isPlayer(damager) && killerTeam != null) {
                    PlayerStats damagerStats = BedWars.getStatsManager().get(damager.getUniqueId());
                    Bukkit.getPluginManager().callEvent(ev1); //call player stat change event for damager
                    if(ev1.isCancelled()) return;
                    damagerStats.setFinalKills(damagerStats.getFinalKills() + 1);
                    event.getArena().addPlayerKill(damager, true, player);
                }
            } else {
                // Prevent pvp log out abuse
                Player damager = event.getLastDamager();
                ITeam killerTeam = event.getArena().getTeam(damager);
                PlayerStatChangeEvent ev1 = new PlayerStatChangeEvent(damager, event.getArena());
                // killer is null if if he already received kill point.
                // LastHit damager is set to null at PlayerDeathEvent so this part is not duplicated for sure.
                // damager is not null if the victim disconnected during pvp only.
                if (event.getLastDamager() != null && event.getArena().isPlayer(damager) && killerTeam != null) {
                    // Punish player
                    Bukkit.getPluginManager().callEvent(ev); //call player stat change event for player that died
                    if(ev.isCancelled()) return;
                    playerStats.setDeaths(playerStats.getDeaths() + 1);
                    event.getArena().addPlayerDeath(player);

                    // Reward attacker
                    event.getArena().addPlayerKill(damager, false, player);
                    PlayerStats damagerStats = BedWars.getStatsManager().get(damager.getUniqueId());
                    Bukkit.getPluginManager().callEvent(ev1); //call player stat change event for damager
                    if(ev1.isCancelled()) return;
                    damagerStats.setKills(damagerStats.getKills() + 1);
                }
            }
        }

        //save or replace stats for player
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, () -> BedWars.getRemoteDatabase().saveStats(playerStats));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        BedWars.getStatsManager().remove(event.getPlayer().getUniqueId());
    }
}
