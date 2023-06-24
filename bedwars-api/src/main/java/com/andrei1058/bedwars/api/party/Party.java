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

package com.andrei1058.bedwars.api.party;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Party {

    boolean hasParty(Player p);

    int partySize(Player p);

    boolean isOwner(Player p);

    List<Player> getMembers(Player owner);

    void createParty(Player owner, Player... members);

    void addMember(Player owner, Player member);

    void removeFromParty(Player member);

    void disband(Player owner);

    boolean isMember(Player owner, Player check);

    void removePlayer(Player owner, Player target);

    default Player getOwner(Player member) {
        for (Player m: this.getMembers(member)) {
            if (isOwner(m)) {
                return m;
            }
        }
        return null;
    }

    default void promote(@NotNull Player owner, @NotNull Player target) {
        String msg = ChatColor.RED+"Not implemented! Contact an administrator";
        owner.sendMessage(msg);
        target.sendMessage(msg);
    }

    boolean isInternal();
}
