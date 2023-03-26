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

package com.andrei1058.bedwars.support.party;

import com.andrei1058.bedwars.api.party.Party;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NoParty implements Party {
    @Override
    public boolean hasParty(Player p) {
        return false;
    }

    @Override
    public int partySize(Player p) {
        return 0;
    }

    @Override
    public boolean isOwner(Player p) {
        return false;
    }

    @Override
    public List<Player> getMembers(Player owner) {
        return new ArrayList<>();
    }

    @Override
    public void createParty(Player owner, Player... members) {

    }

    @Override
    public void addMember(Player owner, Player member) {

    }

    @Override
    public void removeFromParty(Player member) {

    }

    @Override
    public void disband(Player owner) {

    }

    @Override
    public boolean isMember(Player owner, Player check) {
        return false;
    }

    @Override
    public void removePlayer(Player owner, Player target) {

    }

    @Override
    public Player getOwner(Player member) {
        return null;
    }

    @Override
    public void promote(@NotNull Player owner, @NotNull Player target) {

    }

    @Override
    public boolean isInternal() {
        return false;
    }
}
