package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.*;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.Messages;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.ArrayList;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class JoinLeaveTeleport implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if (getServerType() == ServerType.BUNGEE) {
            if (Arena.getArenas().isEmpty()) return;
            Arena a = Arena.getArenas().get(0);
            if (a.getStatus() == GameState.waiting || (a.getStatus() == GameState.starting && a.getStartingTask().getCountdown() > 2)) {
                if (a.getPlayers().size() >= a.getMaxPlayers() && !Arena.isVip(e.getPlayer())) {
                    e.setKickMessage(getMsg(e.getPlayer(), Messages.COMMAND_JOIN_DENIED_IS_FULL));
                    e.setResult(PlayerLoginEvent.Result.KICK_FULL);
                    return;
                } else if (a.getPlayers().size() >= a.getMaxPlayers() && Arena.isVip(e.getPlayer())) {
                    boolean canJoin = false;
                    for (Player on : a.getPlayers()) {
                        if (!Arena.isVip(on)) {
                            canJoin = true;
                            a.removePlayer(on, true);
                            on.kickPlayer(getMsg(on, Messages.ARENA_JOIN_VIP_KICK));
                            break;
                        }
                    }
                    if (!canJoin) {
                        e.setKickMessage(getMsg(e.getPlayer(), Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS));
                        e.setResult(PlayerLoginEvent.Result.KICK_FULL);
                        return;
                    }
                }
            } else if (a.getStatus() == GameState.playing) {
                if (!a.allowSpectate) {
                    if (e.getPlayer().hasPermission(Permissions.PERMISSION_REJOIN)) {
                        if (ReJoin.exists(e.getPlayer())) {
                            if (ReJoin.getPlayer(e.getPlayer()).canReJoin()) return;
                        }
                    }
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
                    if (Arena.getArenaByPlayer(on) != null) {
                        p.hidePlayer(on);
                        on.hidePlayer(p);
                    }
                }

            }, 14L);
        }
        if (debug) {
            p.sendMessage("");
            p.sendMessage("");
            p.sendMessage("§7§m----------------------------------------\n" +
                    "§eThis server is running BedWars1058 §cv" + plugin.getDescription().getVersion()
                    + "\n§eThe latest published version is §a" + Misc.getNewVersion() +
                    "\n§7§m----------------------------------------");
            p.sendMessage("");
            p.sendMessage("");
        }
        if (p.isOp()) {
            if (Misc.isUpdateAvailable()) {
                p.sendMessage("§8[§f" + plugin.getName() + "§8]§7§m---------------------------");
                p.sendMessage("");
                TextComponent tc = new TextComponent("§eUpdate available: §6" + Misc.getNewVersion() + " §7§o(click)");
                tc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
                p.spigot().sendMessage(tc);
                p.sendMessage("");
                p.sendMessage("§8[§f" + plugin.getName() + "§8]§7§m---------------------------");
            }
        }
        if (p.getName().equalsIgnoreCase("andrei1058") || p.getName().equalsIgnoreCase("andreea1058") || p.getName().equalsIgnoreCase("Dani3l_FTW")) {
            p.sendMessage("§8[§f" + plugin.getName() + "§8]§7§m---------------------------");
            p.sendMessage("");
            p.sendMessage("§7User ID: §f%%__USER__%%");
            p.sendMessage("§7Download ID: §f%%__NONCE__%%");
            p.sendMessage("");
            p.sendMessage("§8[§f" + plugin.getName() + "§8]§7§m---------------------------");
        }
        if (Arena.getArenas().isEmpty()) return;

        if (getServerType() != ServerType.SHARED) {
            e.setJoinMessage(null);

            //ReJoin system
            if (ReJoin.exists(p)) {
                if (!ReJoin.getPlayer(p).canReJoin()) return;
                p.sendMessage(Language.getMsg(p, Messages.REJOIN_ALLOWED).replace("{arena}", ReJoin.getPlayer(p).getArena().getDisplayName()));
                ReJoin.getPlayer(p).reJoin();
                return;
            }
        }

        if (Main.getServerType() == ServerType.SHARED) {
            if (Main.config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_LOBBY_SCOREBOARD)) {
                Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                    if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase(Main.getLobbyWorld())) {
                        Misc.giveLobbySb(e.getPlayer());
                    }
                }, 20L);
            }
            return;
        }
        p.getInventory().setArmorContents(null);
        if (getServerType() == ServerType.BUNGEE) {
            Arena.getArenas().get(0).addPlayer(p, false);
            return;
        } else {
            p.teleport(config.getConfigLoc("lobbyLoc"));
            Misc.giveLobbySb(p);
            Arena.sendLobbyCommandItems(p);
        }
        p.setHealthScale(20);
        p.setFoodLevel(20);
        p.setExp(0);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        /* Remove from arena */
        Arena a = Arena.getArenaByPlayer(p);
        if (a != null) {
            if (a.isPlayer(p)) {
                a.removePlayer(p, true);
            } else if (a.isSpectator(p)) {
                a.removeSpectator(p, true);
            }
        }
        Language.getLangByPlayer().remove(p);
        if (getServerType() != ServerType.SHARED) {
            e.setQuitMessage(null);
        }
        /* Manage internal parties */
        if (getParty().isInternal()) {
            if (getParty().hasParty(p)) {
                getParty().removeFromParty(p);
            }
        }
        /* Check if was doing a setup and remove the session */
        if (SetupSession.isInSetupSession(p)) {
            SetupSession.getSession(p).cancel();
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Arena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a != null) {
            Arena a1 = Arena.getArenaByName(e.getTo().getWorld().getName());
            Arena a2 = Arena.getArenaByPlayer(e.getPlayer());
            if (a1 != null) {
                if (a2 != null) {
                    if (a1 != a2) {
                        if (a2.isSpectator(e.getPlayer())) a2.removeSpectator(e.getPlayer(), false);
                        if (a2.isPlayer(e.getPlayer())) a2.removePlayer(e.getPlayer(), false);
                        e.getPlayer().sendMessage("PlayerTeleportEvent something went wrong. You were removed from the arena because you were teleported outside the arena somehow.");
                    }
                }
                if (!(a1.isSpectator(e.getPlayer()) || a1.isPlayer(e.getPlayer()))) {
                    a1.addSpectator(e.getPlayer(), false, e.getTo());
                    if (!e.getPlayer().isFlying()) e.getPlayer().setFlying(true);
                }
            }
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if (Main.getServerType() == ServerType.SHARED) {
            if (Main.config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_LOBBY_SCOREBOARD)) {
                if (e.getFrom().getName().equalsIgnoreCase(Main.getLobbyWorld())) {
                    for (SBoard sBoard : new ArrayList<>(SBoard.getScoreboards())) {
                        if (sBoard.getP() == e.getPlayer())
                            if (sBoard.getArena() == null) sBoard.remove();
                    }
                } else {
                    Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                        if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase(Main.getLobbyWorld())) {
                            Misc.giveLobbySb(e.getPlayer());
                        }
                    }, 20L);
                }
            }
        }
        if (Arena.isInArena(e.getPlayer())) {
            Arena a = Arena.getArenaByPlayer(e.getPlayer());
            if (a.isPlayer(e.getPlayer())) {
                if (a.getStatus() == GameState.waiting || a.getStatus() == GameState.starting) return;
                if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(a.getWorldName())) {
                    a.removePlayer(e.getPlayer(), Main.getServerType() == ServerType.BUNGEE);
                    debug(e.getPlayer().getName() + " was removed from " + a.getDisplayName() + " because he was teleported outside the arena.");
                }
            }
        }
    }
}
