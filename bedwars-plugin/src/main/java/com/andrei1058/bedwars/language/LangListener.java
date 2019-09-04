package com.andrei1058.bedwars.language;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.events.player.PlayerLangChangeEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SBoard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class LangListener implements Listener {

    @EventHandler
    public void onLanguageChangeEvent(PlayerLangChangeEvent e){
        if (BedWars.config.getLobbyWorldName().equalsIgnoreCase(e.getPlayer().getWorld().getName())){
            Arena.sendLobbyCommandItems(e.getPlayer());
            for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
                if (sb.getP().getUniqueId().equals(e.getPlayer().getUniqueId())) {
                    sb.remove();
                }
            }
            if (e.getPlayer().getScoreboard() != null){
                Misc.giveLobbySb(e.getPlayer());
            }
        }
    }
}
