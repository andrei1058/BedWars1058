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

import com.alessiodp.parties.api.interfaces.PartiesAPI;
import com.alessiodp.parties.api.interfaces.PartyPlayer;
import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.party.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PartiesAdapter implements Party {

    //Support for Parties by AlessioDP
    private final PartiesAPI api = com.alessiodp.parties.api.Parties.getApi();
    private static final int requiredRankToSelect = BedWars.config.getInt(ConfigPath.GENERAL_ALESSIODP_PARTIES_RANK);

    @Override
    public boolean hasParty(Player p) {
        PartyPlayer pp = api.getPartyPlayer(p.getUniqueId());
        return pp != null && pp.isInParty();
    }

    @Override
    public int partySize(Player p) {
        if (hasParty(p)) {
            PartyPlayer partyPlayer = api.getPartyPlayer(p.getUniqueId());
            if (partyPlayer != null) {
                if (partyPlayer.getPartyId() != null) {
                    com.alessiodp.parties.api.interfaces.Party party = api.getParty(partyPlayer.getPartyId());
                    if (null != party) {
                        return party.getOnlineMembers().size();
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public boolean isOwner(Player p) {
        PartyPlayer pp = api.getPartyPlayer(p.getUniqueId());
        if (pp == null || pp.getPartyId() == null) {
            return false;
        }
        return pp.getRank() >= requiredRankToSelect;
    }

    @Override
    public List<Player> getMembers(Player p) {
        ArrayList<Player> players = new ArrayList<>();
        if (hasParty(p)) {
            PartyPlayer pp = api.getPartyPlayer(p.getUniqueId());
            if (null != pp) {
                if (pp.getPartyId() != null) {
                    com.alessiodp.parties.api.interfaces.Party party = api.getParty(pp.getPartyId());
                    for (PartyPlayer member : party.getOnlineMembers()) {
                        players.add(Bukkit.getPlayer(member.getPlayerUUID()));
                    }
                }
            }
        }
        return players;
    }

    @Override
    public void createParty(Player owner, Player... members) {
        //party creation handled on bungee side
        if (!api.isBungeeCordEnabled()) {
            PartyPlayer partyOwner = api.getPartyPlayer(owner.getUniqueId());
            if (null != partyOwner  && !partyOwner.isInParty()) {
                boolean created = api.createParty(null, partyOwner);
                if (created && null != partyOwner.getPartyId()) {
                    com.alessiodp.parties.api.interfaces.Party party = api.getParty(partyOwner.getPartyId());
                    if (null != party) {
                        for (Player player1 : members) {
                            PartyPlayer partyPlayer = api.getPartyPlayer(player1.getUniqueId());
                            if (null != partyPlayer  && !partyPlayer.isInParty()) {
                                party.addMember(partyPlayer);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void addMember(Player owner, Player member) {
        //party operations handled on bungee server side
        if (!api.isBungeeCordEnabled()) {
            PartyPlayer partyPlayer = api.getPartyPlayer(owner.getUniqueId());
            if (null != partyPlayer) {
                if (null != partyPlayer.getPartyId()) {
                    com.alessiodp.parties.api.interfaces.Party party = api.getParty(partyPlayer.getPartyId());
                    if (null != party) {
                        PartyPlayer partyMember = api.getPartyPlayer(member.getUniqueId());
                        if (null != partyMember) {
                            party.addMember(partyMember);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void removeFromParty(Player member) {
        PartyPlayer partyMember = api.getPartyPlayer(member.getUniqueId());
        if (null != partyMember) {
            if (null != partyMember.getPartyId()) {
                com.alessiodp.parties.api.interfaces.Party party = api.getParty(partyMember.getPartyId());
                if (null != party) {
                    party.removeMember(partyMember);
                }
            }
        }
    }

    @Override
    public void disband(Player owner) {
        PartyPlayer partyMember = api.getPartyPlayer(owner.getUniqueId());
        if (null != partyMember) {
            if (null != partyMember.getPartyId()) {
                com.alessiodp.parties.api.interfaces.Party party = api.getParty(partyMember.getPartyId());
                if (null != party) {
                    party.delete();
                }
            }
        }
    }

    @Override
    public boolean isMember(Player owner, Player check) {
        if (!hasParty(owner) || !hasParty(check)) {
            return false;
        } else {
            return api.areInTheSameParty(owner.getUniqueId(), check.getUniqueId());
        }
    }

    @Override
    public void removePlayer(Player owner, Player target) {
        PartyPlayer player = api.getPartyPlayer(owner.getUniqueId());
        if (null != player) {
            if (null != player.getPartyId()) {
                com.alessiodp.parties.api.interfaces.Party party = api.getParty(player.getPartyId());
                if (null != party) {
                    PartyPlayer targetPlayer = api.getPartyPlayer(target.getUniqueId());
                    if (null != targetPlayer) {
                        party.removeMember(targetPlayer);
                    }
                }
            }
        }
    }

    @Override
    public Player getOwner(Player member) {
        PartyPlayer partyPlayer = api.getPartyPlayer(member.getUniqueId());
        return Bukkit.getPlayer(Objects.requireNonNull(api.getParty(Objects.requireNonNull(partyPlayer).getPartyId())).getLeader());
    }

    @Override
    public void promote(@NotNull Player owner, @NotNull Player target) {
    }

    @Override
    public boolean isInternal() {
        return false;
    }
}
