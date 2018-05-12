package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.configuration.Language;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class JoinLeaveTeleport implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if (getServerType() == ServerType.BUNGEE) {
            Arena a = Arena.getArenas().get(0);
            if (a.getStatus() == GameState.waiting || (a.getStatus() == GameState.starting && a.getCountdownS() > 2)) {
                if (a.getPlayers().size() >= a.getMaxPlayers() && !Arena.isVip(e.getPlayer())) {
                    e.setKickMessage(getMsg(e.getPlayer(), Language.fullArena));
                    e.setResult(PlayerLoginEvent.Result.KICK_FULL);
                    return;
                } else if (a.getPlayers().size() >= a.getMaxPlayers() && Arena.isVip(e.getPlayer())) {
                    boolean canJoin = false;
                    for (Player on : a.getPlayers()) {
                        if (!Arena.isVip(on)) {
                            canJoin = true;
                            a.removePlayer(on);
                            on.kickPlayer(getMsg(on, Language.vipJoinedSlot));
                        }
                    }
                    if (!canJoin) {
                        e.setKickMessage(getMsg(e.getPlayer(), Language.vipNoJoin));
                        e.setResult(PlayerLoginEvent.Result.KICK_FULL);
                        return;
                    }
                }
            } else if (a.getStatus() == GameState.playing) {
                if (!a.allowSpectate) {
                    e.setResult(PlayerLoginEvent.Result.KICK_FULL);
                }
            } else {
                e.setResult(PlayerLoginEvent.Result.KICK_FULL);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!lang.getIso().equalsIgnoreCase(getLangSupport().getLang(p))) {
            Language.getLangByPlayer().put(p, Language.getLang(getLangSupport().getLang(p)));
        }
        if (plugin.getServerType() != ServerType.BUNGEE) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                for (Player on : Bukkit.getOnlinePlayers()) {
                    if (Arena.getArenaByPlayer(on) == null) {
                        on.showPlayer(p);
                        p.showPlayer(on);
                    } else {
                        p.hidePlayer(on);
                        on.hidePlayer(p);
                    }
                }

            }, 5L);
        }
        if (debug) {
            p.sendMessage("");
            p.sendMessage("");
            p.sendMessage("§7§m----------------------------------------\n" +
                    "§eThis server is running BedWars1058 §cv" + plugin.getDescription().getVersion()
                    + "\n§eThe latest published version is §a" + Misc.getNewVersion()+
                    "\n§7§m----------------------------------------");
            p.sendMessage("");
            p.sendMessage("");
        }
        if (p.isOp()) {
            if (Misc.isUpdateAvailable()) {
                p.sendMessage("§8[§f"+plugin.getName()+"§8]§7§m---------------------------");
                p.sendMessage("");
                TextComponent tc = new TextComponent("§eUpdate available: §6" + Misc.getNewVersion()+" §7§o(click)");
                tc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
                p.spigot().sendMessage(tc);
                p.sendMessage("");
                p.sendMessage("§8[§f"+plugin.getName()+"§8]§7§m---------------------------");
            }
        }
        if (p.getName().equalsIgnoreCase("andrei1058") || p.getName().equalsIgnoreCase("andreea1058") || p.getName().equalsIgnoreCase("Dani3l_FTW")) {
            p.sendMessage("§8[§f"+plugin.getName()+"§8]§7§m---------------------------");
            p.sendMessage("");
            p.sendMessage("§7User ID: §f%%__USER__%%");
            p.sendMessage("§7Download ID: §f%%__NONCE__%%");
            p.sendMessage("");
            p.sendMessage("§8[§f"+plugin.getName()+"§8]§7§m---------------------------");
        }
        if (getServerType() == ServerType.SHARED) return;
        e.setJoinMessage(null);
        p.getInventory().setArmorContents(null);
        if (getServerType() == ServerType.BUNGEE) {
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
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (Arena.getArenaByPlayer(p) != null) {
            Arena.getArenaByPlayer(p).removePlayer(p);
        }
        Language.getLangByPlayer().remove(p);
        if (getServerType() != ServerType.SHARED) {
            e.setQuitMessage(null);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Arena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a != null) {
            if (a.isSpectator(e.getPlayer())) {
                if (e.getFrom().getWorld() != e.getTo().getWorld()) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if (Arena.isInArena(e.getPlayer())) {
            Arena a = Arena.getArenaByPlayer(e.getPlayer());
            if (a.isPlayer(e.getPlayer())) {
                if (a.getStatus() == GameState.waiting || a.getStatus() == GameState.starting) return;
                if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(a.getWorldName())) {
                    a.removePlayer(e.getPlayer());
                    debug(e.getPlayer().getName() + " was removed from " + a.getDisplayName() + " because he was teleported outside the arena.");
                }
            }
        }
    }
}
