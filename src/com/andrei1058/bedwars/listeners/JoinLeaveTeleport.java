package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.*;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.language.Messages;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.language.Language.getMsg;

public class JoinLeaveTeleport implements Listener {

    private static HashMap<UUID, String> preLoadedLanguage = new HashMap<>();

    @SuppressWarnings("ControlFlowStatementWithoutBraces")
    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        final UUID u = p.getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String iso = Main.getRemoteDatabase().getLanguage(u);
            if (Language.isLanguageExist(iso)) {
                if (Main.config.getYml().getStringList(ConfigPath.GENERAL_CONFIGURATION_DISABLED_LANGUAGES).contains(iso))
                    iso = lang.getIso();
                if (preLoadedLanguage.containsKey(u)){
                    preLoadedLanguage.replace(u, iso);
                } else {
                    preLoadedLanguage.put(u, iso);
                }
            }
        });

        if (getServerType() == ServerType.BUNGEE) {
            if (Arena.getArenas().isEmpty()) return;
            Arena a = Arena.getArenas().get(0);
            if (a.getStatus() == GameState.waiting || (a.getStatus() == GameState.starting && a.getStartingTask().getCountdown() > 2)) {
                if (a.getPlayers().size() >= a.getMaxPlayers() && !Arena.isVip(e.getPlayer())) {
                    e.setKickMessage(getMsg(e.getPlayer(), Messages.COMMAND_JOIN_DENIED_IS_FULL));
                    e.setResult(PlayerLoginEvent.Result.KICK_FULL);
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
                        e.disallow(PlayerLoginEvent.Result.KICK_FULL, "The arena is full");
                    }
                }
            } else if (a.getStatus() == GameState.playing) {
                if (!a.allowSpectate) {
                    if (e.getPlayer().hasPermission(Permissions.PERMISSION_REJOIN)) {
                        if (ReJoin.exists(e.getPlayer())) {
                            if (ReJoin.getPlayer(e.getPlayer()).canReJoin()) return;
                        }
                    }
                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "Cannot rejoin");
                }

            } else {
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "The arena is restarting");
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (preLoadedLanguage.containsKey(e.getPlayer().getUniqueId())) {
            Language.setPlayerLanguage(e.getPlayer(), preLoadedLanguage.get(e.getPlayer().getUniqueId()), true);
            preLoadedLanguage.remove(e.getPlayer().getUniqueId());
        }

        if (getServerType() != ServerType.BUNGEE) {
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
            if (Arena.getArenas().isEmpty()) {
                p.performCommand(mainCmd);
            }
        }
        if (p.getName().equalsIgnoreCase("andrei1058") || p.getName().equalsIgnoreCase("andreea1058") || p.getName().equalsIgnoreCase("Dani3l_FTW")) {
            p.sendMessage("§8[§f" + plugin.getName() + " v" + plugin.getDescription().getVersion() + "§8]§7§m---------------------------");
            p.sendMessage("");
            p.sendMessage("§7User ID: §f%%__USER__%%");
            p.sendMessage("§7Download ID: §f%%__NONCE__%%");
            p.sendMessage("");
            p.sendMessage("§8[§f" + plugin.getName() + "§8]§7§m---------------------------");
        }

        if (getServerType() != ServerType.SHARED) {
            e.setJoinMessage(null);
        }

        //if (Arena.getArenas().isEmpty()) return;

        if (getServerType() != ServerType.SHARED) {
            //ReJoin system
            if (ReJoin.exists(p)) {
                if (!ReJoin.getPlayer(p).canReJoin()) return;
                p.sendMessage(Language.getMsg(p, Messages.REJOIN_ALLOWED).replace("{arena}", ReJoin.getPlayer(p).getArena().getDisplayName()));
                ReJoin.getPlayer(p).reJoin(p);
                return;
            }
        }

        if (Main.getServerType() == ServerType.SHARED) {
            if (Main.config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_LOBBY_SCOREBOARD)) {
                if (e.getPlayer().getWorld().getName().equalsIgnoreCase(Main.getLobbyWorld()))
                    Misc.giveLobbySb(e.getPlayer());
            }
            return;
        }
        p.getInventory().setArmorContents(null);
        if (getServerType() == ServerType.BUNGEE) {
            if (!Arena.getArenas().isEmpty()) {
                Arena a = Arena.getArenas().get(0);
                if (a.getStatus() == GameState.waiting || a.getStatus() == GameState.starting) {
                    a.addPlayer(p, false);
                } else {
                    a.addSpectator(p, false, null);
                }
            }
            return;
        } else {
            if (config.getConfigLoc("lobbyLoc") != null)
                p.teleport(config.getConfigLoc("lobbyLoc"), PlayerTeleportEvent.TeleportCause.PLUGIN);
            Misc.giveLobbySb(e.getPlayer());
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

        //Save preferred language
        if (Language.getLangByPlayer().containsKey(p)) {
            final UUID u = p.getUniqueId();
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                String iso = Language.getLangByPlayer().get(p).getIso();
                if (Language.isLanguageExist(iso)) {
                    if (Main.config.getYml().getStringList(ConfigPath.GENERAL_CONFIGURATION_DISABLED_LANGUAGES).contains(iso))
                        iso = lang.getIso();
                    Main.getRemoteDatabase().setLanguage(u, iso);
                }
                Language.getLangByPlayer().remove(p);
            });
        }

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
        if (e.isCancelled()) return;
        Arena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a != null) {
            Arena a1 = Arena.getArenaByName(e.getTo().getWorld().getName());
            if (a1 != null) {
                if (!a1.getWorldName().equals(a.getWorldName())) {
                    if (a.isSpectator(e.getPlayer())) a.removeSpectator(e.getPlayer(), false);
                    if (a.isPlayer(e.getPlayer())) a.removePlayer(e.getPlayer(), false);
                    e.getPlayer().sendMessage("PlayerTeleportEvent something went wrong. You have joined an arena world while playing on a different map.");
                }
            }
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if (Main.getServerType() == ServerType.SHARED) {
            if (Main.config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_LOBBY_SCOREBOARD)) {
                //Bukkit.getScheduler().runTaskLater(plugin, ()-> {
                if (e.getPlayer().getWorld().getName().equalsIgnoreCase(Main.getLobbyWorld())) {
                    Misc.giveLobbySb(e.getPlayer());
                } else {
                    for (SBoard sBoard : new ArrayList<>(SBoard.getScoreboards())) {
                        if (sBoard.getP() == e.getPlayer())
                            if (sBoard.getArena() == null) sBoard.remove();
                    }
                }
                //}, 2L);
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
