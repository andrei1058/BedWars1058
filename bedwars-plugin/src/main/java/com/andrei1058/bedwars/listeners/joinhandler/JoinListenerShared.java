package com.andrei1058.bedwars.listeners.joinhandler;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.language.PreLoadedLanguage;
import com.andrei1058.bedwars.sidebar.BedWarsScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.BedWars.*;


public class JoinListenerShared implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e){
        // If login is allowed load language from DB
        if (e.getResult() == PlayerLoginEvent.Result.ALLOWED) {
            final Player p = e.getPlayer();
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                String iso = BedWars.getRemoteDatabase().getLanguage(p.getUniqueId());
                if (Language.isLanguageExist(iso)) {
                    new PreLoadedLanguage(e.getPlayer().getUniqueId(), iso);

                }
            });
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        final Player p = e.getPlayer();

        // Set player language
        PreLoadedLanguage preLoadedLanguage = PreLoadedLanguage.getByUUID(p.getUniqueId());
        if (preLoadedLanguage != null) {
            Language.setPlayerLanguage(p, preLoadedLanguage.getIso(), true);
            PreLoadedLanguage.clear(p.getUniqueId());
        }

        JoinHandlerCommon.displayCustomerDetails(p);

        // Show commands if player is op and there is no set arenas
        if (p.isOp()) {
            if (Arena.getArenas().isEmpty()) {
                p.performCommand(mainCmd);
            }
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            // Hide new player to players and spectators, and vice versa
            for (Player inArena : Arena.getArenaByPlayer().keySet()){
                if (inArena.equals(p)) continue;
                BedWars.nms.spigotHidePlayer(p, inArena);
                BedWars.nms.spigotHidePlayer(inArena, p);
            }
        }, 14L);

        // Give scoreboard
        if (e.getPlayer().getWorld().getName().equalsIgnoreCase(BedWars.getLobbyWorld())) {
            BedWarsScoreboard.giveScoreboard(e.getPlayer(), null, true);
        }
    }
}

