package com.andrei1058.bedwars.language;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.player.PlayerLangChangeEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.sidebar.BedWarsScoreboard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LangListener implements Listener {

    @EventHandler
    public void onLanguageChangeEvent(PlayerLangChangeEvent e){
        if (e == null) return;
        if (BedWars.config.getLobbyWorldName().equalsIgnoreCase(e.getPlayer().getWorld().getName())){
            Arena.sendLobbyCommandItems(e.getPlayer());
            BedWarsScoreboard sb = BedWarsScoreboard.getSBoard(e.getPlayer().getUniqueId());
            if (sb == null) return;
            IArena arena = sb.getArena();
            sb.remove();
            if (e.getPlayer().getScoreboard() != null){
                BedWarsScoreboard.giveScoreboard(e.getPlayer(), arena, false);
            }
        }
    }
}
