package com.andrei1058.bedwars.support.party;

import com.alessiodp.parties.api.interfaces.PartiesAPI;
import com.alessiodp.parties.api.interfaces.PartyPlayer;
import com.andrei1058.bedwars.api.party.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Parties implements Party {

    //Support for Parties by AlessioDP
    private final PartiesAPI api = com.alessiodp.parties.api.Parties.getApi();

    @Override
    public boolean hasParty(Player p) {
        PartyPlayer pp = api.getPartyPlayer(p.getUniqueId());
        return pp == null || (api.getParty(pp.getPartyName()) != null);
    }

    @Override
    public int partySize(Player p) {
        return getMembers(p).size();
    }

    @Override
    public boolean isOwner(Player p) {
        PartyPlayer pp = api.getPartyPlayer(p.getUniqueId());
        if (pp == null) return false;
        com.alessiodp.parties.api.interfaces.Party party = api.getParty(pp.getPartyName());
        if (party == null) return false;
        if (party.getLeader() == null) return false;
        return party.getLeader().equals(p.getUniqueId());
    }

    @Override
    public List<Player> getMembers(Player p) {
        ArrayList<Player> players = new ArrayList<>();
        PartyPlayer pp = api.getPartyPlayer(p.getUniqueId());
        if (pp == null) return players;
        com.alessiodp.parties.api.interfaces.Party party = api.getParty(pp.getPartyName());
        if (party == null) return players;

        for (UUID pl : party.getMembers()) {
            Player on = Bukkit.getPlayer(pl);
            if (on == null) continue;
            if (!on.isOnline()) continue;
            players.add(on);
        }
        return players;
    }

    @Override
    public void createParty(Player owner, Player... members) {
    }

    @Override
    public void addMember(Player owner, Player member) {
    }

    @Override
    public void removeFromParty(Player member) {
        /*PartyPlayer pp = api.getPartyPlayer(member.getUniqueId());
        if (pp == null) return;
        com.alessiodp.parties.api.interfaces.Party party = api.getParty(pp.getPartyName());
        if (party == null) return;
        if (party.getLeader() == member.getUniqueId()) {
            disband(member);
        } else {
            party.removeMember(pp);
            for (UUID mem : party.getMembers()) {
                Player p = Bukkit.getPlayer(mem);
                if (p == null) continue;
                if (!p.isOnline()) continue;
                p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_LEAVE_SUCCESS).replace("{player}", member.getDisplayName()));
            }
        }*/
    }

    @Override
    public void disband(Player owner) {
        /*PartyPlayer pp = api.getPartyPlayer(owner.getUniqueId());
        if (pp == null) return;
        com.alessiodp.parties.api.interfaces.Party party = api.getParty(pp.getPartyName());
        if (party == null) return;
        for (UUID mem : party.getMembers()) {
            Player p = Bukkit.getPlayer(mem);
            if (p == null) continue;
            if (!p.isOnline()) continue;
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_DISBAND_SUCCESS));
        }
        party.delete();*/
    }

    @Override
    public boolean isMember(Player owner, Player check) {
        PartyPlayer pp = api.getPartyPlayer(owner.getUniqueId());
        if (pp == null) return false;
        com.alessiodp.parties.api.interfaces.Party party = api.getParty(pp.getPartyName());
        if (party == null) return false;
        return party.getMembers().contains(check.getUniqueId());
    }

    @Override
    public void removePlayer(Player owner, Player target) {
        /*PartyPlayer pp = api.getPartyPlayer(target.getUniqueId());
        if (pp == null) return;
        com.alessiodp.parties.api.interfaces.Party party = api.getParty(pp.getPartyName());
        if (party == null) return;
        party.removeMember(pp);
        for (UUID mem : party.getMembers()) {
            Player p = Bukkit.getPlayer(mem);
            if (p == null) continue;
            if (!p.isOnline()) continue;
            p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_REMOVE_SUCCESS));
        }*/
    }

    @Override
    public boolean isInternal() {
        return false;
    }
}
