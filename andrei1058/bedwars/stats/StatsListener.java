package com.andrei1058.bedwars.stats;

import com.andrei1058.bedwars.api.events.BedBreakEvent;
import com.andrei1058.bedwars.api.events.PlayerKillEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class StatsListener implements Listener {

    @EventHandler
    // Create cache for player if does not exist yet.
    public void onLogin(PlayerLoginEvent e) {
        StatsManager.getStatsCache().createStatsCache(e.getPlayer());
    }

    @EventHandler
    public void onBedBreak(BedBreakEvent e) {
        StatsManager.getStatsCache().addBedsDestroyed(e.getPlayer(), 1);
    }

    @EventHandler
    public void onPlayerKill(PlayerKillEvent e) {
        if (e.getCause().toString().contains("FINAL")) {
            StatsManager.getStatsCache().addFinalDeaths(e.getVictim(), 1);
            if (e.getKiller() != null) StatsManager.getStatsCache().addFinalKill(e.getKiller(), 1);
        } else {
            StatsManager.getStatsCache().addDeaths(e.getVictim(), 1);
            if (e.getKiller() != null) StatsManager.getStatsCache().addKill(e.getKiller(), 1);
        }
    }
}
