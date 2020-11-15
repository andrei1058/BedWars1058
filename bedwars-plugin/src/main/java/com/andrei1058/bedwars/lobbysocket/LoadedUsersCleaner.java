package com.andrei1058.bedwars.lobbysocket;

import com.andrei1058.bedwars.support.preloadedparty.PreLoadedParty;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.LinkedList;
import java.util.List;

public class LoadedUsersCleaner implements Runnable {

    private final List<LoadedUser> toRemove = new LinkedList<>();

    @Override
    public void run() {
        for (LoadedUser lu : LoadedUser.getLoaded().values()) {
            if (lu.getRequestTime() + 6000 > System.currentTimeMillis()) {
                toRemove.add(lu);
            }
        }
        if (!toRemove.isEmpty()) {
            toRemove.forEach(c -> {
                OfflinePlayer op = Bukkit.getOfflinePlayer(c.getUuid());
                if (op != null && op.getName() != null) {
                    PreLoadedParty plp = PreLoadedParty.getPartyByOwner(op.getName());
                    if (plp != null) {
                        plp.clean();
                    }
                }
                c.destroy("Removed by cleaner task.");
            });
            toRemove.clear();
        }
    }
}
