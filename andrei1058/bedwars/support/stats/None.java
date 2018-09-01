package com.andrei1058.bedwars.support.stats;

import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

public class None implements Database {
    @Override
    public boolean isStats() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public void setupGeneralTables() {

    }

    @Override
    public void setupGroupTable(String group) {

    }

    @Override
    public void saveStats(Player p, Timestamp last_play, int wins, int kills, int final_kills, int looses, int deaths, int final_deaths, int beds_destroyed, int games_played) {

    }

    @Override
    public Timestamp getFirstPlay(Player p) {
        return null;
    }

    @Override
    public Timestamp getLastPlay(Player p) {
        return null;
    }

    @Override
    public int getKills(Player p) {
        return 0;
    }

    @Override
    public int getWins(Player p) {
        return 0;
    }

    @Override
    public int getFinalKills(Player p) {
        return 0;
    }

    @Override
    public int getLooses(Player p) {
        return 0;
    }

    @Override
    public int getFinalDeaths(Player p) {
        return 0;
    }

    @Override
    public int getDeaths(Player p) {
        return 0;
    }

    @Override
    public int getBedsDestroyed(Player p) {
        return 0;
    }

    @Override
    public int getGamesPlayed(Player p) {
        return 0;
    }

    @Override
    public HashMap<UUID, Integer> getTopWins(int x) {
        return new HashMap<>();
    }

    @Override
    public HashMap<UUID, Integer> getTopKills(int x) {
        return new HashMap<>();
    }

    @Override
    public HashMap<UUID, Integer> getTopFinalKills(int x) {
        return new HashMap<>();
    }

    @Override
    public HashMap<UUID, Integer> getTopLooses(int x) {
        return new HashMap<>();
    }

    @Override
    public HashMap<UUID, Integer> getTopDeaths(int x) {
        return new HashMap<>();
    }

    @Override
    public HashMap<UUID, Integer> getTopFinalDeaths(int x) {
        return new HashMap<>();
    }

    @Override
    public HashMap<UUID, Integer> getTopBedsDestroyed(int x) {
        return new HashMap<>();
    }

    @Override
    public HashMap<UUID, Integer> getTopGamesPlayed(int x) {
        return new HashMap<>();
    }
}
