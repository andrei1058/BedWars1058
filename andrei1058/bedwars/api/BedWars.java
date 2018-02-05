package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.Main;

public class BedWars implements GameAPI {

    @Override
    public ServerType getServerType() {
        return Main.getServerType();
    }

    @Override
    public int getApiVersion() {
        return 1;
    }


}
