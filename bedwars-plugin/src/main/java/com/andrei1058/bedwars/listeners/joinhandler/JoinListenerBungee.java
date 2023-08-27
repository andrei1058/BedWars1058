/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.listeners.joinhandler;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.ReJoin;
import com.andrei1058.bedwars.configuration.Permissions;
import com.andrei1058.bedwars.configuration.Sounds;
import com.andrei1058.bedwars.lobbysocket.LoadedUser;
import com.andrei1058.bedwars.support.paper.TeleportManager;
import com.andrei1058.bedwars.support.preloadedparty.PreLoadedParty;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class JoinListenerBungee implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        final Player p = e.getPlayer();

        LoadedUser proxyUser = LoadedUser.getPreLoaded(p.getUniqueId());

        // If is NOT logging in trough BedWarsProxy
        if (proxyUser == null) {
            if (!e.getPlayer().hasPermission("bw.setup")) {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Language.getMsg(p, Messages.ARENA_JOIN_DENIED_NO_PROXY));
            }
        } else {
            // If is logging in trough BedWarsProxy
            Language playerLang = proxyUser.getLanguage() == null ? Language.getDefaultLanguage() : proxyUser.getLanguage();

            // Check if there is an arena to rejoin
            ReJoin reJoin = ReJoin.getPlayer(p);
            if (reJoin != null) {
                // If is not allowed to rejoin
                if (!(p.hasPermission(Permissions.PERMISSION_REJOIN) || reJoin.canReJoin())) {
                    e.disallow(PlayerLoginEvent.Result.KICK_OTHER, playerLang.m(Messages.REJOIN_DENIED));
                    reJoin.destroy(true);
                }
                // Stop here, rejoin handled. More will be handled at PlayerJoinEvent
                return;
            }

            IArena arena = Arena.getArenaByIdentifier(proxyUser.getArenaIdentifier());
            GameState status = arena != null ? arena.getStatus() : null;

            // check if arena is not available, time out etc.
            if (arena == null || proxyUser.isTimedOut() || status == GameState.restarting) {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, playerLang.m(Messages.ARENA_STATUS_RESTARTING_NAME));
                proxyUser.destroy("Time out or game unavailable at PlayerLoginEvent");
                return;
            }

            // Player logic
            switch (status) {
                case starting:
                case waiting:
                    // Vip join/ kick feature
                    if (arena.getPlayers().size() >= arena.getMaxPlayers() && Arena.isVip(p)) {
                        boolean canJoin = false;
                        for (Player inGame : arena.getPlayers()) {
                            if (!Arena.isVip(inGame)) {
                                canJoin = true;
                                inGame.kickPlayer(getMsg(inGame, Messages.ARENA_JOIN_VIP_KICK));
                                break;
                            }
                        }
                        if (!canJoin) {
                            e.disallow(PlayerLoginEvent.Result.KICK_FULL, playerLang.m(Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS));
                        }
                    }
                    break;
                case playing:
                    // Spectator logic
                    if (!arena.isAllowSpectate()) {
                        e.disallow(PlayerLoginEvent.Result.KICK_OTHER, playerLang.m(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG));
                    }
                    break;
                default:
                    throw new IllegalStateException("Unhandled game status!");
            }

        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer() == null) return;
        e.setJoinMessage(null);
        final Player p = e.getPlayer();

        LoadedUser proxyUser = LoadedUser.getPreLoaded(p.getUniqueId());

        // If didn't join trough BedWarsProxy
        if (proxyUser == null) {
            // If is an admin let him in to do the setup
            if (p.hasPermission("bw.setup")) {
                JoinHandlerCommon.displayCustomerDetails(p);
                Bukkit.dispatchCommand(p, "bw");
                World mainWorld = Bukkit.getWorlds().get(0);
                if (mainWorld != null) {
                    TeleportManager.teleportC(p, mainWorld.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
                // hide admin to in game users
                for (Player inGame : Bukkit.getOnlinePlayers()){
                    if (inGame.equals(p)) continue;
                    if (Arena.isInArena(inGame)) {
                        BedWars.nms.spigotHidePlayer(p, inGame);
                        BedWars.nms.spigotHidePlayer(inGame, p);
                    }
                }
            } else {
                // The player is not an admin and he joined using /server or equivalent
                p.kickPlayer(Language.getMsg(p, Messages.ARENA_JOIN_DENIED_NO_PROXY));
            }
        } else {
            // The player joined using BedWarsProxy
            Language playerLang = proxyUser.getLanguage() == null ? Language.getDefaultLanguage() : proxyUser.getLanguage();

            // Check if has an arena to ReJoin
            ReJoin reJoin = ReJoin.getPlayer(p);
            if (reJoin != null) {
                // Check if can re-join
                if (reJoin.canReJoin()) {
                    JoinHandlerCommon.displayCustomerDetails(p);
                    reJoin.reJoin(p);
                    // Cache player language
                    Language.setPlayerLanguage(p.getUniqueId(), playerLang.getIso());
                } else {
                    p.kickPlayer(playerLang.m(Messages.REJOIN_DENIED));
                }
                // ReJoin handled, stop here
                proxyUser.destroy("Rejoin handled. PreLoaded user no longer needed.");
                return;
            }

            // There's nothing to re-join, so he might want to join an arena
            IArena arena = Arena.getArenaByIdentifier(proxyUser.getArenaIdentifier());
            GameState status = arena != null ? arena.getStatus() : null;

            // Check if the arena is still available or request time-out etc.
            if (arena == null || proxyUser.isTimedOut() || status == GameState.restarting) {
                p.kickPlayer(playerLang.m(Messages.ARENA_STATUS_RESTARTING_NAME));
                proxyUser.destroy("Time out or game unavailable at PlayerLoginEvent");
                return;
            }

            // Join allowed, cache player language
            Language.setPlayerLanguage(p.getUniqueId(), playerLang.getIso());
            JoinHandlerCommon.displayCustomerDetails(p);

            // Join as player
            switch (status) {
                case starting:
                case waiting:
                    Sounds.playSound("join-allowed", p);

                    // If has no party
                    if (proxyUser.getPartyOwnerOrSpectateTarget() == null) {
                        // Add to arena
                        if (!arena.addPlayer(p, true)) {
                            p.kickPlayer(Language.getMsg(p, Messages.ARENA_JOIN_DENIED_NO_PROXY));
                        }
                    } else {
                        // If is member or owner of a remote party

                        Player partyOwner = Bukkit.getPlayer(proxyUser.getPartyOwnerOrSpectateTarget());
                        // If party owner is connected
                        if (partyOwner != null && partyOwner.isOnline()) {
                            // If joiner is the party owner create the party
                            if (partyOwner.equals(p)) {
                                BedWars.getParty().createParty(p);

                                // Handle to-be-teamed-up players. A list used if some party members join before the party owner.
                                PreLoadedParty preLoadedParty = PreLoadedParty.getPartyByOwner(partyOwner.getName());
                                if (preLoadedParty != null) {
                                    preLoadedParty.teamUp();
                                }
                            } else {
                                // Add to a existing party
                                BedWars.getParty().addMember(partyOwner, p);
                            }
                        } else {
                            // If a party member joined before the party owner create a waiting list
                            // to-be-teamed-up players, when the owner will join
                            PreLoadedParty preLoadedParty = PreLoadedParty.getPartyByOwner(proxyUser.getPartyOwnerOrSpectateTarget());
                            if (preLoadedParty == null) {
                                preLoadedParty = new PreLoadedParty(proxyUser.getPartyOwnerOrSpectateTarget());
                            }
                            preLoadedParty.addMember(p);
                        }
                        if (!arena.addPlayer(p, true)) {
                            p.kickPlayer(Language.getMsg(p, Messages.ARENA_JOIN_DENIED_NO_PROXY));
                        }
                    }
                    break;
                case playing:
                    // Join as spectator
                    Sounds.playSound("spectate-allowed", p);
                    Location spectatorTarget = null;
                    if (proxyUser.getPartyOwnerOrSpectateTarget() != null) {
                        Player targetPlayer = Bukkit.getPlayer(proxyUser.getPartyOwnerOrSpectateTarget());
                        if (targetPlayer != null) {
                            spectatorTarget = targetPlayer.getLocation();
                        }
                    }
                    arena.addSpectator(p, false, spectatorTarget);
                    break;
                default:
                    throw new IllegalStateException("Unhandled game status!");
            }
            proxyUser.destroy("Joined as player or spectator. PreLoaded user no longer needed.");
        }
    }
}

