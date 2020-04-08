package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.arena.*;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.commands.bedwars.subcmds.regular.CmdStats;
import com.andrei1058.bedwars.configuration.Permissions;
import com.andrei1058.bedwars.configuration.Sounds;
import com.andrei1058.bedwars.language.PreLoadedLanguage;
import com.andrei1058.bedwars.lobbysocket.LoadedUser;
import com.andrei1058.bedwars.sidebar.BedWarsScoreboard;
import com.andrei1058.bedwars.support.preloadedparty.PreLoadedParty;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.UUID;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class JoinLeaveTeleport implements Listener {

    @SuppressWarnings("ControlFlowStatementWithoutBraces")
    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        final UUID u = p.getUniqueId();

        if (autoscale) {
            if (LoadedUser.isPreLoaded(u)) {
                debug("PlayerLoginEvent is pre loaded");
                LoadedUser lu = LoadedUser.getPreLoaded(u);
                Language l = lu.getLanguage() == null ? Language.getDefaultLanguage() : lu.getLanguage();

                if (e.getPlayer().hasPermission(Permissions.PERMISSION_REJOIN)) {
                    ReJoin rj = ReJoin.getPlayer(p);
                    if (rj != null) {
                        if (rj.canReJoin()) {
                            return;
                        } else {
                            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, l.m(Messages.REJOIN_DENIED));
                            return;
                        }
                    }
                }
                IArena a = Arena.getArenaByIdentifier(lu.getArenaIdentifier());
                if (a == null || lu.getRequestTime() > System.currentTimeMillis() + 5000 || a.getStatus() == GameState.restarting) {
                    debug("PlayerLoginEvent is pre loaded but time out");
                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, l.m(Messages.ARENA_STATUS_RESTARTING_NAME));
                    lu.destroy();
                    return;
                }
                return;
            } else {
                if (!e.getPlayer().hasPermission("bw.setup")) {
                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "You must be op to join directly. Use the arena selector otherwise.");
                }
            }
        }

        if (!autoscale) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                String iso = BedWars.getRemoteDatabase().getLanguage(u);
                if (Language.isLanguageExist(iso)) {
                    if (!BedWars.config.getYml().getStringList(ConfigPath.GENERAL_CONFIGURATION_DISABLED_LANGUAGES).contains(iso)) {
                        new PreLoadedLanguage(e.getPlayer().getUniqueId(), iso);
                    }
                }
            });
        }

        if (getServerType() == ServerType.BUNGEE) {
            if (Arena.getArenas().isEmpty()) {
                if (!Arena.getEnableQueue().isEmpty()) {
                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, getMsg(e.getPlayer(), Messages.ARENA_STATUS_RESTARTING_NAME));
                } else return;
            }
            IArena a = Arena.getArenas().get(0);
            if (a.getStatus() == GameState.waiting || (a.getStatus() == GameState.starting && a.getStartingTask().getCountdown() > 2)) {
                if (a.getPlayers().size() >= a.getMaxPlayers() && !Arena.isVip(e.getPlayer())) {
                    e.disallow(PlayerLoginEvent.Result.KICK_FULL, getMsg(e.getPlayer(), Messages.COMMAND_JOIN_DENIED_IS_FULL));
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
                        e.disallow(PlayerLoginEvent.Result.KICK_FULL, getMsg(e.getPlayer(), Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS));
                    }
                }
            } else if (a.getStatus() == GameState.playing) {
                if (!a.isAllowSpectate()) {
                    if (e.getPlayer().hasPermission(Permissions.PERMISSION_REJOIN)) {
                        ReJoin rj = ReJoin.getPlayer(p);
                        if (rj != null) {
                            if (rj.canReJoin()) return;
                        }
                    }
                    e.disallow(PlayerLoginEvent.Result.KICK_OTHER, getMsg(e.getPlayer(), Messages.REJOIN_DENIED));
                }

            } else {
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, getMsg(e.getPlayer(), Messages.ARENA_STATUS_RESTARTING_NAME));
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();

        if (autoscale) {
            e.setJoinMessage(null);
            if (LoadedUser.isPreLoaded(p.getUniqueId())) {
                LoadedUser lu = LoadedUser.getPreLoaded(p.getUniqueId());
                IArena a = Arena.getArenaByIdentifier(lu.getArenaIdentifier());

                if (ReJoin.exists(p)) {
                    if (!ReJoin.getPlayer(p).canReJoin()) {
                        p.kickPlayer(Language.getMsg(p, Messages.REJOIN_DENIED));
                        return;
                    }
                    p.sendMessage(Language.getMsg(p, Messages.REJOIN_ALLOWED).replace("{arena}", ReJoin.getPlayer(p).getArena().getDisplayName()));
                    ReJoin.getPlayer(p).reJoin(p);
                    return;
                }

                if (a == null || lu.getRequestTime() > System.currentTimeMillis() + 5000 || a.getStatus() == GameState.restarting) {
                    Language l = lu.getLanguage() == null ? Language.getDefaultLanguage() : lu.getLanguage();
                    p.kickPlayer(l.m(Messages.ARENA_STATUS_RESTARTING_NAME));
                    return;
                }
                if (lu.getLanguage() != null)
                    Language.setPlayerLanguage(e.getPlayer(), lu.getLanguage().getIso(), true);
                if (a.getStatus() == GameState.starting || a.getStatus() == GameState.waiting) {
                    Sounds.playSound("join-allowed", p);
                    if (lu.getPartyOwnerOrSpectateTarget() != null) {
                        Player po = Bukkit.getPlayer(lu.getPartyOwnerOrSpectateTarget());
                        if (po != null && po.isOnline()) {
                            if (po.equals(e.getPlayer())) {
                                BedWars.getParty().createParty(e.getPlayer());
                                PreLoadedParty plp = PreLoadedParty.getPartyByOwner(e.getPlayer().getName());
                                if (plp != null) {
                                    plp.teamUp();
                                }
                            } else {
                                BedWars.getParty().addMember(po, e.getPlayer());
                            }
                        } else {
                            PreLoadedParty plp = PreLoadedParty.getPartyByOwner(lu.getPartyOwnerOrSpectateTarget());
                            if (plp == null) {
                                plp = new PreLoadedParty(lu.getPartyOwnerOrSpectateTarget());
                            }
                            plp.addMember(e.getPlayer());
                        }
                    }
                    a.addPlayer(p, true);
                } else {
                    a.addSpectator(p, false, lu.getPartyOwnerOrSpectateTarget() == null ? null : Bukkit.getPlayer(lu.getPartyOwnerOrSpectateTarget()) == null ? null : Bukkit.getPlayer(lu.getPartyOwnerOrSpectateTarget()).getLocation());
                    Sounds.playSound("spectate-allowed", p);
                }
                lu.destroy();
                return;
            } else {
                if (p.hasPermission("bw.setup")) {
                    Bukkit.dispatchCommand(p, "/bw");
                    p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                } else {
                    p.kickPlayer(Language.getMsg(p, Messages.ARENA_STATUS_RESTARTING_NAME));
                    return;
                }
            }
        }

        PreLoadedLanguage preLoadedLanguage = PreLoadedLanguage.getByUUID(e.getPlayer().getUniqueId());
        if (preLoadedLanguage != null) {
            Language.setPlayerLanguage(e.getPlayer(), preLoadedLanguage.getIso(), true);
            PreLoadedLanguage.clear(e.getPlayer().getUniqueId());
        }

        if (getServerType() != ServerType.BUNGEE) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                for (Player on : Bukkit.getOnlinePlayers()) {
                    if (Arena.isInArena(e.getPlayer())) break;
                    if (Arena.getArenaByPlayer(on) != null) {
                        p.hidePlayer(on);
                        on.hidePlayer(p);
                    }
                }

            }, 14L);
        }

        if (p.isOp()) {
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
            p.getInventory().setArmorContents(null);
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

        if (BedWars.getServerType() == ServerType.SHARED) {
            if (e.getPlayer().getWorld().getName().equalsIgnoreCase(BedWars.getLobbyWorld())) {
                BedWarsScoreboard.giveScoreboard(e.getPlayer(), null, true);
            }
        }

        if (getServerType() == ServerType.BUNGEE && !autoscale) {
            if (!Arena.getArenas().isEmpty()) {
                IArena a = Arena.getArenas().get(0);
                if (a.getStatus() == GameState.waiting || a.getStatus() == GameState.starting) {
                    if (a.addPlayer(p, false)) {
                        Sounds.playSound("join-allowed", p);
                    } else {
                        a.addSpectator(p, false, null);
                        Sounds.playSound("join-denied", p);
                    }
                } else {
                    a.addSpectator(p, false, null);
                    Sounds.playSound("spectate-allowed", p);
                }
            }
        } else if (getServerType() == ServerType.MULTIARENA) {
            if (config.getConfigLoc("lobbyLoc") != null) {
                Location loc = config.getConfigLoc("lobbyLoc");
                if (loc.getWorld() != null) p.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
            BedWarsScoreboard.giveScoreboard(p, null, true);
            Arena.sendLobbyCommandItems(p);
            p.setHealthScale(20);
            p.setFoodLevel(20);
            p.setExp(0);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        /* Remove from arena */
        IArena a = Arena.getArenaByPlayer(p);
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
                    if (BedWars.config.getYml().getStringList(ConfigPath.GENERAL_CONFIGURATION_DISABLED_LANGUAGES).contains(iso))
                        iso = Language.getDefaultLanguage().getIso();
                    BedWars.getRemoteDatabase().setLanguage(u, iso);
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
        SetupSession ss = SetupSession.getSession(p.getUniqueId());
        if (ss != null) {
            ss.cancel();
        }

        BedWarsScoreboard sb = BedWarsScoreboard.getSBoard(e.getPlayer().getUniqueId());
        if (sb != null) {
            sb.remove();
        }

        BedWarsTeam.antiFallDamageAtRespawn.remove(e.getPlayer().getUniqueId());

        LastHit lh = LastHit.getLastHit(p);
        if (lh != null) {
            lh.remove();
        }

        CmdStats.getStatsCoolDown().remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (e == null) return;
        if (e.isCancelled()) return;
        if (e.getTo() == null) return;
        if (e.getTo().getWorld() == null) return;
        IArena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a != null) {
            IArena a1 = Arena.getArenaByIdentifier(e.getTo().getWorld().getName());
            if (a1 != null) {
                if (!a1.equals(a)) {
                    if (a.isSpectator(e.getPlayer())) a.removeSpectator(e.getPlayer(), false);
                    if (a.isPlayer(e.getPlayer())) a.removePlayer(e.getPlayer(), false);
                    e.getPlayer().sendMessage("PlayerTeleportEvent something went wrong. You have joined an arena world while playing on a different map.");
                }
            }
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if (BedWars.getServerType() == ServerType.SHARED) {
            if (BedWars.config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_LOBBY_SCOREBOARD)) {
                //Bukkit.getScheduler().runTaskLater(plugin, ()-> {
                if (e.getPlayer().getWorld().getName().equalsIgnoreCase(BedWars.getLobbyWorld())) {
                    BedWarsScoreboard.giveScoreboard(e.getPlayer(), null, true);
                } else {
                    BedWarsScoreboard sb = BedWarsScoreboard.getSBoard(e.getPlayer().getUniqueId());
                    if (sb != null) {
                        sb.remove();
                    }
                }
                //}, 2L);
            }
        }
        if (Arena.isInArena(e.getPlayer())) {
            IArena a = Arena.getArenaByPlayer(e.getPlayer());
            if (a.isPlayer(e.getPlayer())) {
                if (a.getStatus() == GameState.waiting || a.getStatus() == GameState.starting) return;
                if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(a.getWorld().getName())) {
                    a.removePlayer(e.getPlayer(), BedWars.getServerType() == ServerType.BUNGEE);
                    debug(e.getPlayer().getName() + " was removed from " + a.getDisplayName() + " because he was teleported outside the arena.");
                }
            }
        }
    }
}
