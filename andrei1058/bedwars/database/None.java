package com.andrei1058.bedwars.database;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

public class None implements Database {
    @Override
    public void init() {

    }

    @Override
    public void saveStats(UUID uuid, String username, Timestamp firstPlay, Timestamp lastPlay, int wins, int kills, int finalKills, int losses, int deaths, int finalDeaths, int bedsDestroyed, int gamesPlayed) {

    }

    @Override
    public void updateLocalStats(UUID uuid) {

    }

    @Override
    public void close() {

    }

    @Override
    public void setQuickBuySlot(UUID uuid, String shopPath, int slot) {

    }

    @Override
    public String getQuickBuySlots(UUID uuid, int slot) {
        return null;
    }

    @Override
    public boolean hasQuickBuy(UUID uuid) {
        return false;
    }
}
