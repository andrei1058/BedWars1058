package com.andrei1058.bedwars.support.party;

import com.andrei1058.bedwars.api.party.Party;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PAF implements Party {
    //Party and Friends Support by JT122406
    @Override
    public boolean hasParty(Player p) {
        OnlinePAFPlayer p1 = PAFPlayerManager.getInstance().getPlayer(p);
        return p1.getParty().isInParty(p1);
    }

    @Override
    public int partySize(Player p) {
        if (hasParty(p) == false) return 0;
        else {
            return PAFPlayerManager.getInstance().getPlayer(p).getParty().getAllPlayers().size();
        }
    }

    @Override
    public boolean isOwner(Player p) {
        if (hasParty(p) == false) return  false;
        else {
            return PAFPlayerManager.getInstance().getPlayer(p).getParty().isLeader(PAFPlayerManager.getInstance().getPlayer(p));
        }
    }

    @Override
    public List<Player> getMembers(Player owner) {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<OnlinePAFPlayer> playersPAF = new ArrayList<>();
        OnlinePAFPlayer p1 = PAFPlayerManager.getInstance().getPlayer(owner);
        if (p1.getParty() == null) return players;
        playersPAF.addAll(p1.getParty().getAllPlayers());
        for (int i = 0; i < playersPAF.size(); i++) {
            players.add(playersPAF.get(i).getPlayer());
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
    }

    @Override
    public void disband(Player owner) {
    }

    @Override
    public boolean isMember(Player owner, Player check) {
        return PAFPlayerManager.getInstance().getPlayer(owner).getParty().isInParty(PAFPlayerManager.getInstance().getPlayer(check));
    }

    @Override
    public void removePlayer(Player owner, Player target) {
    }

    @Override
    public boolean isInternal() {
        return false;
    }
}
