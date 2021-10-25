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

import com.andrei1058.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PreLoadedParty {

    private String owner;
    private List<Player> members = new ArrayList<>();

    private static ConcurrentHashMap<String, PreLoadedParty> preLoadedParties = new ConcurrentHashMap<>();

    public PreLoadedParty(String owner){
        PreLoadedParty plp = getPartyByOwner(owner);
        if (plp != null){
            plp.clean();
        }
        this.owner = owner;
        preLoadedParties.put(owner, this);
    }

    public static PreLoadedParty getPartyByOwner(String owner){
        return preLoadedParties.getOrDefault(owner, null);
    }

    public void addMember(Player player){
        members.add(player);
    }

    public void teamUp(){
        if (this.owner == null) return;
        Player owner = Bukkit.getPlayer(this.owner);
        if (owner == null) return;
        if (!owner.isOnline()) return;
        for (Player player : members){
            if (!player.getName().equalsIgnoreCase(this.owner)){
                BedWars.getParty().addMember(owner, player);
            }
        }
        preLoadedParties.remove(this.owner);
    }

    public static ConcurrentHashMap<String, PreLoadedParty> getPreLoadedParties() {
        return preLoadedParties;
    }
    public void clean(){
        preLoadedParties.remove(this.owner);
    }
}
