package com.andrei1058.bedwars.support.party;

import com.andrei1058.bedwars.api.party.Party;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PAF implements Party {
    //Party and Friends for Spigot Support by JT122406
    private PlayerParty getPAFParty(Player p) {
        OnlinePAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(p);
        return PartyManager.getInstance().getParty(pafPlayer);
    }

    @Override
    public boolean hasParty(Player p) {
        return getPAFParty(p) != null;
    }

    @Override
    public int partySize(Player p) {
        PlayerParty party = getPAFParty(p);
        if (party == null)
            return 0;
        return party.getAllPlayers().size();
    }

    @Override
    public boolean isOwner(Player p) {
        OnlinePAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(p);
        PlayerParty party = PartyManager.getInstance().getParty(pafPlayer);
        if (party == null)
            return false;
        return party.isLeader(pafPlayer);
    }

    @Override
    public List<Player> getMembers(Player owner) {
        ArrayList<Player> playerList = new ArrayList<>();
        PlayerParty party = getPAFParty(owner);
        if (party == null)
            return playerList;
        for (OnlinePAFPlayer players : party.getAllPlayers()) {
            playerList.add(players.getPlayer());
        }
        return playerList;
    }

    @Override
    public void createParty(Player owner, Player... members) {
        OnlinePAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(owner);
        PlayerParty party = PartyManager.getInstance().createParty(pafPlayer);
        party.setPrivateState(false);
        for (Player p1 : members){
            party.addPlayer(PAFPlayerManager.getInstance().getPlayer(p1));
        }
        party.setPrivateState(true);
    }

    @Override
    public void addMember(Player owner, Player member) {
        OnlinePAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(owner);
        PlayerParty party = pafPlayer.getParty();
        party.setPrivateState(false);
        party.addPlayer(PAFPlayerManager.getInstance().getPlayer(member));
        party.setPrivateState(true);
    }

    @Override
    public void removeFromParty(Player member) {
        PlayerParty p = PAFPlayerManager.getInstance().getPlayer(member).getParty();
        p.leaveParty(PAFPlayerManager.getInstance().getPlayer(member));
    }

    @Override
    public void disband(Player owner) {
        PartyManager.getInstance().deleteParty(PartyManager.getInstance().getParty(PAFPlayerManager.getInstance().getPlayer(owner)));
    }

    @Override
    public boolean isMember(Player owner, Player check) {
        PlayerParty party = getPAFParty(owner);
        if (party == null)
            return false;
        return party.isInParty(PAFPlayerManager.getInstance().getPlayer(check));
    }

    @Override
    public void removePlayer(Player owner, Player target) {
        PlayerParty p = getPAFParty(owner);
        p.leaveParty(PAFPlayerManager.getInstance().getPlayer(target));
    }

    @Override
    public boolean isInternal() {
        return false;
    }
}
