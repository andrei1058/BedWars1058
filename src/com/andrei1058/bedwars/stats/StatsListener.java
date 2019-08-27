package com.andrei1058.bedwars.stats;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.events.player.PlayerBedBreakEvent;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class StatsListener implements Listener {

    @EventHandler
    // Create cache for player if does not exist yet.
    public void onLogin(PlayerLoginEvent e) {
        final Player p = e.getPlayer();
        //create cache row for player
        StatsManager.getStatsCache().createStatsCache(p);
        //update local cache for player
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, ()-> Main.getRemoteDatabase().updateLocalCache(p.getUniqueId()));
    }

    @EventHandler
    public void onBedBreak(PlayerBedBreakEvent e) {
        //store beds destroyed
        StatsManager.getStatsCache().addBedsDestroyed(e.getPlayer().getUniqueId(), 1);
    }

    @EventHandler
    public void onPlayerKill(PlayerKillEvent e) {
        if (e.getCause().toString().contains("FINAL")) {
            //store final deaths
            StatsManager.getStatsCache().addFinalDeaths(e.getVictim().getUniqueId(), 1);
            //store losses
            StatsManager.getStatsCache().addLosses(e.getVictim().getUniqueId(), 1);
            //store games played
            StatsManager.getStatsCache().addGamesPlayed(e.getVictim().getUniqueId(), 1);
            //store final kills
            if (e.getKiller() != null) StatsManager.getStatsCache().addFinalKill(e.getKiller().getUniqueId(), 1);
        } else {
            //store deaths
            StatsManager.getStatsCache().addDeaths(e.getVictim().getUniqueId(), 1);
            //store kills
            if (e.getKiller() != null) StatsManager.getStatsCache().addKill(e.getKiller().getUniqueId(), 1);
        }
    }

    @EventHandler
    public void onGameEnd(GameEndEvent e){
        for (UUID uuid : e.getWinners()){
            Player p = Bukkit.getPlayer(uuid);
            if (p == null) continue;
            if (!p.isOnline()) continue;
            //store wins
            StatsManager.getStatsCache().addWins(uuid, 1);
            //store games played
            StatsManager.getStatsCache().addGamesPlayed(uuid, 1);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        //save or replace stats for player
        final Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, ()-> StatsManager.getStatsCache().updateRemote(p.getUniqueId(), p.getName()));
    }
}
