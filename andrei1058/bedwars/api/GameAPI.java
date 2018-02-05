package com.andrei1058.bedwars.api;

public interface GameAPI {

    //BedWars api = (BedWars) Bukkit.getServicesManager().getRegistration(GameAPI.class).getProvider();
    ServerType getServerType();

    int getApiVersion();
}
