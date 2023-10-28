package com.andrei1058.bedwars.arena.stats;

import com.andrei1058.bedwars.api.arena.stats.*;
import com.andrei1058.bedwars.api.events.player.PlayerBedBreakEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Used to increase default session stats.
 */
public class DefaultStatsHandler implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBedWarsKill(@NotNull PlayerKillEvent event) {

        GameStatsHolder statsHolder = event.getArena().getStatsHolder();
        if (null == statsHolder) {
            return;
        }

        Player killer = event.getKiller();
        Player victim = event.getVictim();

        // INCREMENT KILLER STATS
        if ((killer != null && !event.getVictimTeam().equals(event.getKillerTeam())) && !victim.equals(killer)) {
            Optional<PlayerGameStats> killerStats = statsHolder.get(killer);

            killerStats.flatMap(stats -> stats.getStatistic(DefaultStatistics.KILLS)).ifPresent(gameStatistic -> {
                if (gameStatistic instanceof Incrementable) {
                    ((Incrementable) gameStatistic).increment();
                }
            });

            if (event.getCause().isFinalKill()) {
                killerStats.flatMap(stats -> stats.getStatistic(DefaultStatistics.KILLS_FINAL)).ifPresent(gameStatistic -> {
                    if (gameStatistic instanceof Incrementable) {
                        ((Incrementable) gameStatistic).increment();
                    }
                });
            }
        }

        // INCREMENT VICTIM STATS
        Optional<PlayerGameStats> victimStats = statsHolder.get(victim);

        victimStats.flatMap(stats -> stats.getStatistic(DefaultStatistics.DEATHS)).ifPresent(gameStatistic -> {
            if (gameStatistic instanceof Incrementable) {
                ((Incrementable) gameStatistic).increment();
            }
        });

        if (event.getCause().isFinalKill()) {
            victimStats.flatMap(stats -> stats.getStatistic(DefaultStatistics.DEATHS_FINAL)).ifPresent(gameStatistic -> {
                if (gameStatistic instanceof Incrementable) {
                    ((Incrementable) gameStatistic).increment();
                }
            });
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBedWarsBedBreak(@NotNull PlayerBedBreakEvent event) {
        if (null == event.getArena().getStatsHolder()) {
            return;
        }

        event.getArena().getStatsHolder().get(event.getPlayer()).flatMap(stats ->
                stats.getStatistic(DefaultStatistics.BEDS_DESTROYED)).ifPresent(st -> {
            if (st instanceof Incrementable) {
                ((Incrementable) st).increment();
            }
        });
    }
}
