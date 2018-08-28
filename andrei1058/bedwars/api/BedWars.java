package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.configuration.Language;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

import static com.andrei1058.bedwars.Main.database;

public class BedWars implements GameAPI {

    @Override
    public ServerType getServerType() {
        return Main.getServerType();
    }

    @Override
    public int getApiVersion() {
        return 10;
    }

    @Override
    public boolean isStats() {
        return database.isStats();
    }

    @Override
    public Timestamp getPlayerFirstPlay(Player p) {
        return database.getFirstPlay(p);
    }

    @Override
    public Timestamp getPlayerLastPlay(Player p) {
        return database.getLastPlay(p);
    }

    @Override
    public int getPlayerWins(Player p) {
        return database.getWins(p);
    }

    @Override
    public int getPlayerKills(Player p) {
        return database.getKills(p);
    }

    @Override
    public int getPlayerFinalKills(Player p) {
        return database.getFinalKills(p);
    }

    @Override
    public int getPlayerLooses(Player p) {
        return database.getLooses(p);
    }

    @Override
    public int getPlayerDeaths(Player p) {
        return database.getDeaths(p);
    }

    @Override
    public int getPlayerFinalDeaths(Player p) {
        return database.getFinalDeaths(p);
    }

    @Override
    public int getPlayerBedsDestroyed(Player p) {
        return database.getBedsDestroyed(p);
    }

    @Override
    public int getPlayerGamesPlayed(Player p) {
        return database.getGamesPlayed(p);
    }

    @Override
    public HashMap<UUID, Integer> getTopWins(int x) {
        return database.getTopWins(x);
    }

    @Override
    public HashMap<UUID, Integer> getTopKills(int x) {
        return database.getTopKills(x);
    }

    @Override
    public HashMap<UUID, Integer> getTopFinalKills(int x) {
        return database.getTopFinalKills(x);
    }

    @Override
    public HashMap<UUID, Integer> getTopLooses(int x) {
        return database.getTopLooses(x);
    }

    @Override
    public HashMap<UUID, Integer> getTopDeaths(int x) {
        return database.getTopDeaths(x);
    }

    @Override
    public HashMap<UUID, Integer> getTopFinalDeaths(int x) {
        return database.getTopFinalDeaths(x);
    }

    @Override
    public HashMap<UUID, Integer> getTopBedsDestroyed(int x) {
        return database.getTopBedsDestroyed(x);
    }

    @Override
    public HashMap<UUID, Integer> getTopGamesPlayed(int x) {
        return database.getTopGamesPlayed(x);
    }

    @Override
    public boolean isPlaying(Player p) {
        if (Arena.isInArena(p)){
            return Arena.getArenaByPlayer(p).isPlayer(p);
        }
        return false;
    }

    @Override
    public boolean isSpectating(Player p) {
        if (Arena.isInArena(p)){
            return Arena.getArenaByPlayer(p).isSpectator(p);
        }
        return false;
    }

    @Override
    public String getLangIso(Player p) {
        return Language.getPlayerLanguage(p).getIso();
    }


}
