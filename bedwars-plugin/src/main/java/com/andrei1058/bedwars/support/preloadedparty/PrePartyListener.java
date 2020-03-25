package com.andrei1058.bedwars.support.preloadedparty;

import com.andrei1058.bedwars.api.events.server.ArenaDisableEvent;
import com.andrei1058.bedwars.api.events.server.ArenaRestartEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PrePartyListener implements Listener {

    @EventHandler
    public void onDisable(ArenaDisableEvent e){
        PreLoadedParty plp = PreLoadedParty.getPartyByOwner(e.getWorldName());
        if (plp != null){
            PreLoadedParty.getPreLoadedParties().remove(plp);
        }
    }

    @EventHandler
    public void onRestart(ArenaRestartEvent e){
        PreLoadedParty plp = PreLoadedParty.getPartyByOwner(e.getWorldName());
        if (plp != null){
            PreLoadedParty.getPreLoadedParties().remove(plp);
        }
    }
}
