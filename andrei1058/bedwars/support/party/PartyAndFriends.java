package com.andrei1058.bedwars.support.party;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PartyAndFriends implements Party {

    @Override
    public boolean hasParty(Player p) {
        PlayerParty party = PartyManager.getInstance().getParty(p.getUniqueId());
        if (party == null) return false;
        return true;
    }

    @Override
    public int partySize(Player p) {
        PlayerParty party = PartyManager.getInstance().getParty(p.getUniqueId());
        if (party == null) return 0;
        return party.getPlayers().stream().filter(o -> Bukkit.getPlayer(o.getUniqueId()) != null).filter(o -> Bukkit.getPlayer(o.getUniqueId()).isOnline()).toArray().length;
    }

    @Override
    public boolean isOwner(Player p) {
        PlayerParty party = PartyManager.getInstance().getParty(p.getUniqueId());
        if (party == null) return false;
        return party.getLeader().getUniqueId().equals(p.getUniqueId());
    }

    @Override
    public List<Player> getMembers(Player p) {
        ArrayList<Player> players = new ArrayList<>();
        PlayerParty party = PartyManager.getInstance().getParty(p.getUniqueId());
        for (OnlinePAFPlayer pl : party.getPlayers()) {
            Player on = Bukkit.getPlayer(pl.getUniqueId());
            if (on == null) continue;
            if (!on.isOnline()) continue;
            players.add(on);
        }
        return players;
    }

    @Override
    public void createParty(Player owner, Player... members) {
        PlayerParty pp = PartyManager.getInstance().createParty((OnlinePAFPlayer) PAFPlayerManager.getInstance().getPlayer(owner.getUniqueId()));
        if (pp == null) return;
        for (Player p : members) {
            pp.addPlayer((OnlinePAFPlayer) PAFPlayerManager.getInstance().getPlayer(p.getUniqueId()));
        }
    }

    @Override
    public void addMember(Player owner, Player member) {
        PlayerParty pp = PartyManager.getInstance().getParty(owner.getUniqueId());
        if (pp == null) return;
        pp.addPlayer((OnlinePAFPlayer) PAFPlayerManager.getInstance().getPlayer(member.getUniqueId()));
    }

    @Override
    public void removeFromParty(Player member) {
        PlayerParty pp = PartyManager.getInstance().getParty(member.getUniqueId());
        if (pp == null) return;
        pp.leaveParty((OnlinePAFPlayer) PAFPlayerManager.getInstance().getPlayer(member.getUniqueId()));
    }

    @Override
    public void disband(Player owner) {
        PlayerParty pp = PartyManager.getInstance().getParty(owner.getUniqueId());
        if (pp == null) return;
        for (PAFPlayer p : pp.getAllPlayers()) {
            pp.leaveParty(p);
        }
    }

    @Override
    public boolean isMember(Player owner, Player check) {
        PlayerParty pp = PartyManager.getInstance().getParty(owner.getUniqueId());
        if (pp == null) return false;
        return pp.isInParty((OnlinePAFPlayer) PAFPlayerManager.getInstance().getPlayer(check.getUniqueId()));
    }

    @Override
    public void removePlayer(Player owner, Player target) {
        PlayerParty pp = PartyManager.getInstance().getParty(owner.getUniqueId());
        if (pp == null) return;
        pp.leaveParty(PAFPlayerManager.getInstance().getPlayer(target.getUniqueId()));
    }

    @Override
    public boolean isInternal() {
        return false;
    }
}
