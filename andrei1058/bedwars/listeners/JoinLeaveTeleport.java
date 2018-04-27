package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SBoard;
import com.andrei1058.bedwars.configuration.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.configuration.Language.getList;

public class JoinLeaveTeleport implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (!lang.getIso().equalsIgnoreCase(getLangSupport().getLang(p))){
            Language.getLangByPlayer().put(p, Language.getLang(getLangSupport().getLang(p)));
        }
        Bukkit.getScheduler().runTaskLater(plugin, ()-> {
            for (Player on : Bukkit.getOnlinePlayers()){
                if (Arena.getArenaByPlayer(on) == null){
                    on.showPlayer(p);
                    p.showPlayer(on);
                } else {
                    p.hidePlayer(on);
                    on.hidePlayer(p);
                }
            }
            if (debug) {
                p.sendMessage("§9This server is running BedWars1058 §av" + plugin.getDescription().getVersion() + "\n§9The latest published version is §c" + Misc.getNewVersion());
            }
            if (p.isOp()){
                if (Misc.isUpdateAvailable()){
                    p.sendMessage("§a▃▃▃▃▃▃▃▃▃§2[§6BedWars1058§2]§a▃▃▃▃▃▃▃▃▃");
                    p.sendMessage("");
                    p.sendMessage("§2New version available: §a"+Misc.getNewVersion());
                    p.sendMessage("§cYou're running: §c"+plugin.getDescription().getVersion());
                    p.sendMessage("");
                }
            }
            if (p.getName().equalsIgnoreCase("andrei1058") || p.getName().equalsIgnoreCase("andreea1058") || p.getName().equalsIgnoreCase("Dani3l_FTW")){
                p.sendMessage("§a▃▃▃▃▃▃▃▃▃§2[§6BedWars1058§2]§a▃▃▃▃▃▃▃▃▃");
                p.sendMessage("");
                p.sendMessage("§aUser ID: §2%%__USER__%%");
                p.sendMessage("§aDownload ID: §2%%__NONCE__%%");
                p.sendMessage("");
            }
        }, 10L);
        if (getServerType() == ServerType.SHARED) return;
        e.setJoinMessage(null);
        p.getInventory().setArmorContents(null);
        if (getServerType() == ServerType.BUNGEE){
            Arena.getArenas().get(0).addPlayer(p, false);
            return;
        } else {
            p.teleport(config.getConfigLoc("lobbyLoc"));
            Arena.sendMultiarenaLobbyItems(p);
            Misc.giveLobbySb(p);
        }
        p.setHealthScale(20);
        p.setFoodLevel(20);
        p.setExp(0);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if (Arena.getArenaByPlayer(p) != null){
            Arena.getArenaByPlayer(p).removePlayer(p);
        }
        Language.getLangByPlayer().remove(p);
        if (getServerType() != ServerType.SHARED){
            e.setQuitMessage(null);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        Arena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a != null){
            if (a.isSpectator(e.getPlayer())){
                if (e.getFrom().getWorld() != e.getTo().getWorld()){
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e){
        if (Arena.isInArena(e.getPlayer())){
            Arena a = Arena.getArenaByPlayer(e.getPlayer());
            if (a.isPlayer(e.getPlayer())){
                if (a.getStatus() == GameState.waiting || a.getStatus() == GameState.starting) return;
                if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(a.getWorldName())) {
                    a.removePlayer(e.getPlayer());
                    debug(e.getPlayer().getName() + " was removed from " + a.getDisplayName() + " because he was teleported outside the arena.");
                }
            }
        }
    }
}
