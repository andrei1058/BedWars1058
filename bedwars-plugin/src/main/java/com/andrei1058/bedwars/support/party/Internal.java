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

import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.party.Party;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class Internal implements Party {
    private static List<Internal.Party> parites = new ArrayList<>();

    @Override
    public boolean hasParty(Player p) {
        for (Party party : getParites()) {
            if (party.members.contains(p)) return true;
        }
        return false;
    }

    @Override
    public int partySize(Player p) {
        for (Party party : getParites()) {
            if (party.members.contains(p)) {
                return party.members.size();
            }
        }
        return 0;
    }

    @Override
    public boolean isOwner(Player p) {
        for (Party party : getParites()) {
            if (party.members.contains(p)) {
                if (party.owner == p) return true;
            }
        }
        return false;
    }

    @Override
    public List<Player> getMembers(Player owner) {
        for (Party party : getParites()) {
            if (party.members.contains(owner)) {
                return party.members;
            }
        }
        return null;
    }

    @Override
    public void createParty(Player owner, Player... members) {
        Party p = new Party(owner);
        p.addMember(owner);
        for (Player mem : members) {
            p.addMember(mem);
        }
    }

    @Override
    public void addMember(Player owner, Player member) {
        if (owner == null || member == null) return;
        Internal.Party p = getParty(owner);
        if (p == null) return;
        p.addMember(member);
    }

    @Override
    public void removeFromParty(Player member) {
        for (Party p : new ArrayList<>(getParites())) {
            if (p.owner == member) {
                disband(member);
            } else if (p.members.contains(member)) {
                for (Player mem : p.members) {
                    mem.sendMessage(getMsg(mem, Messages.COMMAND_PARTY_LEAVE_SUCCESS).replace("{playername}", member.getName()).replace("{player}", member.getDisplayName()));
                }
                p.members.remove(member);
                if (p.members.isEmpty() || p.members.size() == 1) {
                    disband(p.owner);
                    parites.remove(p);
                }
                return;
            }
        }
    }

    @Override
    public void disband(Player owner) {
        Internal.Party pa = getParty(owner);
        if (pa == null) return;
        for (Player p : pa.members) {
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_DISBAND_SUCCESS));
        }
        pa.members.clear();
        Internal.parites.remove(pa);
    }

    @Override
    public boolean isMember(Player owner, Player check) {
        for (Party p : parites) {
            if (p.owner == owner) {
                if (p.members.contains(check)) return true;
            }
        }
        return false;
    }

    @Override
    public void removePlayer(Player owner, Player target) {
        Party p = getParty(owner);
        if (p != null) {
            if (p.members.contains(target)) {
                for (Player mem : p.members) {
                    mem.sendMessage(getMsg(mem, Messages.COMMAND_PARTY_REMOVE_SUCCESS).replace("{player}", target.getName()));
                }
                p.members.remove(owner);
                if (p.members.isEmpty() || p.members.size() == 1) {
                    disband(p.owner);
                    parites.remove(p);
                }
            }
        }
    }

    @Override
    public Player getOwner(Player member) {
        for (Internal.Party party: Internal.getParites()) {
            if (party.members.contains(member)){
                return party.owner;
            }
        }
        return null;
    }

    @Override
    public void promote(@NotNull Player owner, @NotNull Player target) {
        Party p = getParty(owner);
        if (p != null) {
            p.owner = target;
        }
    }

    @Override
    public boolean isInternal() {
        return true;
    }

    @Nullable
    private Party getParty(Player owner) {
        for (Party p : getParites()) {
            if (p.getOwner() == owner) return p;
        }
        return null;
    }

    @NotNull
    @Contract(pure = true)
    public static List<Party> getParites() {
        return Collections.unmodifiableList(parites);
    }

    static class Party {

        private List<Player> members = new ArrayList<>();
        private Player owner;

        public Party(Player p) {
            owner = p;
            Internal.parites.add(this);
        }

        public Player getOwner() {
            return owner;
        }

        void addMember(Player p) {
            members.add(p);
        }
    }
}
