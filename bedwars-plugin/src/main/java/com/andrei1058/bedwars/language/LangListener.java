package com.andrei1058.bedwars.language;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.events.player.PlayerLangChangeEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.sidebar.BedWarsScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LangListener implements Listener {

    @EventHandler
    public void onLanguageChangeEvent(PlayerLangChangeEvent e) {
        if (e == null) return;
        if (!e.isCancelled()) return;
        if (BedWars.config.getLobbyWorldName().equalsIgnoreCase(e.getPlayer().getWorld().getName())) {
            BedWarsScoreboard sb = BedWarsScoreboard.getSBoard(e.getPlayer().getUniqueId());
            if (sb != null){
                sb.remove();
                Bukkit.getLogger().info(ChatColor.GREEN + "aaaaaaa");
            }

            Bukkit.getScheduler().runTaskLater(BedWars.plugin, ()-> {
                Bukkit.getLogger().info(ChatColor.GREEN + "bbbbbbbbbb");
                Arena.sendLobbyCommandItems(e.getPlayer());
                Bukkit.getScheduler().runTaskLater(BedWars.plugin, ()-> {
                    BedWarsScoreboard.giveScoreboard(e.getPlayer(), Arena.getArenaByPlayer(e.getPlayer()), true);
                    Bukkit.getLogger().info(ChatColor.GREEN + "ccccccccccc");
                }, 40L);
            }, 30L);
        }
    }
}
