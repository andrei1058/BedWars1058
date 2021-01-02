package com.andrei1058.bedwars.language;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.events.player.PlayerLangChangeEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.sidebar.BedWarsScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class LangListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLanguageChangeEvent(PlayerLangChangeEvent e) {
        if (e == null) return;
        if (e.isCancelled()) return;
        if (BedWars.config.getLobbyWorldName().equalsIgnoreCase(e.getPlayer().getWorld().getName())) {
            Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
                Arena.sendLobbyCommandItems(e.getPlayer());
                BedWarsScoreboard.giveScoreboard(e.getPlayer(), Arena.getArenaByPlayer(e.getPlayer()), false);

                // save to db
                Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, ()-> BedWars.getRemoteDatabase().setLanguage(e.getPlayer().getUniqueId(), e.getNewLang()));
            }, 10L);
        }
    }
}
