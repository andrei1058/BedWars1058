package com.andrei1058.bedwars.support.party;

import com.andrei1058.bedwars.api.party.Party;
import de.simonsator.*;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriendsgui.api.PartyFriendsAPI;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


//Party and friends Support
public class PAF implements Party {

    //PartyFriendsAPI api = (PartyFriendsAPI) de.simonsator.partyandfriends.api.party.PartyAPI;

    @Override
    public boolean hasParty(Player p) {

        OnlinePAFPlayer p1 = PAFPlayerManager.getInstance().getPlayer(p);

        return p1.getParty().isInParty(p1);
    }

    @Override
    public int partySize(Player p) {

        OnlinePAFPlayer p1 = PAFPlayerManager.getInstance().getPlayer(p);

        if (p1.getParty().isInParty(p1) == false) return 0;

        return p1.getParty().getAllPlayers().size();
    }

    @Override
    public boolean isOwner(Player p) {

        OnlinePAFPlayer p1 = PAFPlayerManager.getInstance().getPlayer(p);

        if (p1.getParty() == null) return false;

        return p1.getParty().isLeader(p1);
    }

    @Override
    public List<Player> getMembers(Player p) {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<OnlinePAFPlayer> playersPAF = new ArrayList<>();


        OnlinePAFPlayer p1 = PAFPlayerManager.getInstance().getPlayer(p);
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

        OnlinePAFPlayer p1 = PAFPlayerManager.getInstance().getPlayer(owner);
        OnlinePAFPlayer targetPAF = PAFPlayerManager.getInstance().getPlayer(check);

        return p1.getParty().isInParty(targetPAF);
    }

    @Override
    public void removePlayer(Player owner, Player target) {

    }

    @Override
    public boolean isInternal() {
        return false;
    }


}
