package com.andrei1058.bedwars.language;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.events.player.PlayerLangChangeEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.SBoard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LangListener implements Listener {

    @EventHandler
    public void onLanguageChangeEvent(PlayerLangChangeEvent e){
        if (e == null) return;
        if (BedWars.config.getLobbyWorldName().equalsIgnoreCase(e.getPlayer().getWorld().getName())){
            Arena.sendLobbyCommandItems(e.getPlayer());
            SBoard sb = SBoard.getSBoard(e.getPlayer().getUniqueId());
            if (sb != null) {
                sb.remove();
            }
            if (e.getPlayer().getScoreboard() != null){
                SBoard.giveLobbyScoreboard(e.getPlayer());
            }
        }
    }
}
