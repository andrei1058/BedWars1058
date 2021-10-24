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

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.ReJoin;
import com.andrei1058.bedwars.configuration.Permissions;
import com.andrei1058.bedwars.configuration.Sounds;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import static com.andrei1058.bedwars.BedWars.mainCmd;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class JoinListenerBungeeLegacy implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onLogin(PlayerLoginEvent e) {
        final Player p = e.getPlayer();

        // Do not allow login if the arena wasn't loaded yet
        if (Arena.getArenas().isEmpty()) {
            if (!Arena.getEnableQueue().isEmpty()) {
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, getMsg(e.getPlayer(), Messages.ARENA_STATUS_RESTARTING_NAME));
                return;
            }
        }

        // Check if there is an arena to rejoin
        ReJoin reJoin = ReJoin.getPlayer(p);
        if (reJoin != null) {
            // If is not allowed to rejoin
            if (!(p.hasPermission(Permissions.PERMISSION_REJOIN) || reJoin.canReJoin())) {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Language.getDefaultLanguage().m(Messages.REJOIN_DENIED));
                reJoin.destroy(true);
            }
            // Stop here, rejoin handled. More will be handled at PlayerJoinEvent
            return;
        }

        IArena arena = Arena.getArenas().get(0);
        if (arena != null) {

            // Player logic
            if (arena.getStatus() == GameState.waiting || (arena.getStatus() == GameState.starting && arena.getStartingTask().getCountdown() > 1)) {
                // If arena is full
                if (arena.getPlayers().size() >= arena.getMaxPlayers()) {
                    // Vip join feature
                    if (Arena.isVip(p)) {
                        boolean canJoin = false;
                        for (Player inGame : arena.getPlayers()) {
                            if (!Arena.isVip(inGame)) {
                                canJoin = true;
                                inGame.kickPlayer(getMsg(inGame, Messages.ARENA_JOIN_VIP_KICK));
                                break;
                            }
                        }
                        if (!canJoin) {
                            e.disallow(PlayerLoginEvent.Result.KICK_FULL, Language.getDefaultLanguage().m(Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS));
                        }
                    } else {
                        e.disallow(PlayerLoginEvent.Result.KICK_OTHER, getMsg(e.getPlayer(), Messages.COMMAND_JOIN_DENIED_IS_FULL));
                    }
                }
            } else if (arena.getStatus() == GameState.playing) {
                // Spectator logic
                if (!arena.isAllowSpectate()) {
                    e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Language.getDefaultLanguage().m(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG));
                }
            } else {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Language.getDefaultLanguage().m(Messages.ARENA_STATUS_RESTARTING_NAME));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        final Player p = e.getPlayer();

        // Do not allow login if the arena wasn't loaded yet
        // I know this code is already in the login event but other plugins may allow login
        if (Arena.getArenas().isEmpty()) {
            if (!Arena.getEnableQueue().isEmpty()) {
                p.kickPlayer(getMsg(e.getPlayer(), Messages.ARENA_STATUS_RESTARTING_NAME));
                return;
            }
        }

        JoinHandlerCommon.displayCustomerDetails(p);

        if (Arena.getArenas().isEmpty()) {
            // Show setup commands if there is no arena available
            if (p.hasPermission("bw.setup")) {
                p.performCommand(mainCmd);
            }
        } else {
            IArena arena = Arena.getArenas().get(0);
            // Add player if the game is in waiting
            if (arena.getStatus() == GameState.waiting || arena.getStatus() == GameState.starting) {
                if (arena.addPlayer(p, false)) {
                    Sounds.playSound("join-allowed", p);
                } else {
                    p.kickPlayer(getMsg(p, Messages.COMMAND_JOIN_DENIED_IS_FULL));
                }
            } else {
                // Check ReJoin
                ReJoin reJoin = ReJoin.getPlayer(p);
                if (reJoin != null) {
                    if (reJoin.canReJoin()) {
                        reJoin.reJoin(p);
                        reJoin.destroy(false);
                        return;
                    } else {
                        p.sendMessage(getMsg(p, Messages.REJOIN_DENIED));
                        reJoin.destroy(true);
                    }
                }

                // Add spectator
                if (arena.addSpectator(p, false, null)) {
                    Sounds.playSound("spectate-allowed", p);
                } else {
                    p.kickPlayer(getMsg(p, Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG));
                }
            }
        }
    }
}

