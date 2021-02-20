package com.andrei1058.bedwars.stats;

import com.andrei1058.bedwars.api.BedWars;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class StatsAPI implements BedWars.IStats {

    private static StatsAPI instance;

    public static StatsAPI getInstance() {
        if (instance == null) {
            instance = new StatsAPI();
        }
        return instance;
    }

    private StatsAPI() {
    }

    private PlayerStats getData(UUID uuid) {
        PlayerStats stats = com.andrei1058.bedwars.BedWars.getStatsManager().getUnsafe(uuid);
        if (stats == null) {
            stats = com.andrei1058.bedwars.BedWars.getRemoteDatabase().fetchStats(uuid);
        }
        return stats;
    }

    @Override
    public Timestamp getPlayerFirstPlay(UUID p) {
        Instant firstPlay = getData(p).getFirstPlay();
        if (firstPlay == null) {
            return null;
        }
        return Timestamp.from(firstPlay);
    }

    @Override
    public Timestamp getPlayerLastPlay(UUID p) {
        Instant lastPlay = getData(p).getLastPlay();
        if (lastPlay == null) {
            return null;
        }
        return Timestamp.from(lastPlay);
    }

    @Override
    public int getPlayerWins(UUID p) {
        return getData(p).getWins();
    }

    @Override
    public int getPlayerKills(UUID p) {
        return getData(p).getKills();
    }

    @Override
    public int getPlayerTotalKills(UUID p) {
        return getData(p).getTotalKills();
    }

    @Override
    public int getPlayerFinalKills(UUID p) {
        return getData(p).getFinalKills();
    }

    @Override
    public int getPlayerLoses(UUID p) {
        return getData(p).getLosses();
    }

    @Override
    public int getPlayerDeaths(UUID p) {
        return getData(p).getDeaths();
    }

    @Override
    public int getPlayerFinalDeaths(UUID p) {
        return getData(p).getFinalDeaths();
    }

    @Override
    public int getPlayerBedsDestroyed(UUID p) {
        return getData(p).getBedsDestroyed();
    }

    @Override
    public int getPlayerGamesPlayed(UUID p) {
        return getData(p).getGamesPlayed();
    }
}
