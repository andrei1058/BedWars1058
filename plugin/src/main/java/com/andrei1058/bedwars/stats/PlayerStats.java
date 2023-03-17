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

package com.andrei1058.bedwars.stats;

import java.time.Instant;
import java.util.UUID;

public class PlayerStats {

    private final UUID uuid;

    private String name;
    private Instant firstPlay;
    private Instant lastPlay;
    private int wins;
    private int kills;
    private int finalKills;
    private int totalKills;
    private int losses;
    private int deaths;
    private int finalDeaths;
    private int bedsDestroyed;
    private int gamesPlayed;

    public PlayerStats(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Instant getFirstPlay() {
        return firstPlay;
    }

    public void setFirstPlay(Instant firstPlay) {
        this.firstPlay = firstPlay;
    }

    public Instant getLastPlay() {
        return lastPlay;
    }

    public void setLastPlay(Instant lastPlay) {
        this.lastPlay = lastPlay;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
        this.totalKills = kills + finalKills;
    }

    public int getFinalKills() {
        return finalKills;
    }

    public void setFinalKills(int finalKills) {
        this.finalKills = finalKills;
        this.totalKills = kills + finalKills;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getFinalDeaths() {
        return finalDeaths;
    }

    public void setFinalDeaths(int finalDeaths) {
        this.finalDeaths = finalDeaths;
    }

    public int getBedsDestroyed() {
        return bedsDestroyed;
    }

    public void setBedsDestroyed(int bedsDestroyed) {
        this.bedsDestroyed = bedsDestroyed;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamePlayed) {
        this.gamesPlayed = gamePlayed;
    }

    public int getTotalKills() {
        return totalKills;
    }
}
