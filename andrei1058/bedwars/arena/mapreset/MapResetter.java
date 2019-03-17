package com.andrei1058.bedwars.arena.mapreset;

import org.bukkit.Location;

public interface MapResetter {

    void backupLobby(Location loc1, Location loc2);

    void restoreLobby(MapManager map);

    void removeLobby(MapManager mapManager);

    void resetMap(MapManager mapManager);
}
