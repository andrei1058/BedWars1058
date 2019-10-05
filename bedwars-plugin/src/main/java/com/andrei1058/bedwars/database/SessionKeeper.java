package com.andrei1058.bedwars.database;

import com.andrei1058.bedwars.BedWars;

public class SessionKeeper implements Runnable {

    private MySQL database;

    public SessionKeeper(MySQL database){
        this.database = database;
        BedWars.debug("Starting MySQL SessionKeeper...");
    }

    @Override
    public void run() {
        if (!database.isConnected()) database.connect();

        database.ping();
    }
}
