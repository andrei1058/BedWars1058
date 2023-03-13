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

package com.andrei1058.bedwars.support.preloadedparty;

import com.andrei1058.bedwars.api.events.server.ArenaDisableEvent;
import com.andrei1058.bedwars.api.events.server.ArenaRestartEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Deprecated
        (forRemoval = true, since = "23.1.1")
public class PrePartyListener implements Listener {

    @EventHandler
    public void onDisable(ArenaDisableEvent e){
        PreLoadedParty plp = PreLoadedParty.getPartyByOwner(e.getWorldName());
        if (plp != null){
            //todo what was I doing here lmao. no sense
            PreLoadedParty.getPreLoadedParties().remove(plp);
        }
    }

    @EventHandler
    public void onRestart(ArenaRestartEvent e){
        PreLoadedParty plp = PreLoadedParty.getPartyByOwner(e.getWorldName());
        if (plp != null){
            //todo what was I doing here lmao. no sense
            PreLoadedParty.getPreLoadedParties().remove(plp);
        }
    }
}
