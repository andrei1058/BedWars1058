package com.andrei1058.bedwars.support.party;

import com.andrei1058.bedwars.api.party.Party;
import de.simonsator.partyandfriendsgui.api.datarequest.DataRequestPlayerInfo;
import de.simonsator.partyandfriendsgui.api.datarequest.party.PartyDataRequestCallbackAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PAFDataCallBack implements Party {
    public boolean hasParty(Player p) {
        List<Boolean> booleans = new ArrayList<>();
        PartyDataRequestCallbackAPI.getInstance().fetchPartyData(p, (player, partyData, i) -> {
            if (partyData.DOES_EXIST)
                booleans.add(true);
            else
                booleans.add(false);
        });
        return booleans.get(0);
    }

    public int partySize(Player p) {
        List<Integer> ints = new ArrayList<>();
        PartyDataRequestCallbackAPI.getInstance().fetchPartyData(p, (player, partyData, i) -> {
            if (partyData.DOES_EXIST){
                ints.add(partyData.getAllPlayers().size());
            }
        });
        return ints.get(0);
    }

    public boolean isOwner(Player p) {
        List<Boolean> booleans = new ArrayList<>();
        PartyDataRequestCallbackAPI.getInstance().fetchPartyData(p, (player, partyData, i) -> {
            if (partyData.DOES_EXIST  && (partyData.getPartyLeader().PLAYER_UUID.equals(p.getUniqueId())))
                booleans.add(true);
            else
                booleans.add(false);
        });
        return booleans.get(0);
    }

    public List<Player> getMembers(Player owner) {
        List<Player> partymembers = new ArrayList<>();
        PartyDataRequestCallbackAPI.getInstance().fetchPartyData(owner, (player, partyData, i) -> {
            if (partyData.DOES_EXIST){
                for (DataRequestPlayerInfo p: partyData.getAllPlayers()) {
                    partymembers.add(Bukkit.getPlayer(p.PLAYER_UUID));
                }
            }
        });
        return partymembers;
    }

    public void createParty(Player owner, Player... members) {
    }

    public void addMember(Player owner, Player member) {
    }

    public void removeFromParty(Player member) {
    }

    public void disband(Player owner) {
    }

    public boolean isMember(Player owner, Player check) {
        List<Boolean> booleans = new ArrayList<>();
        PartyDataRequestCallbackAPI.getInstance().fetchPartyData(owner, (player, partyData, i) -> {
            if (partyData.DOES_EXIST  && (partyData.getAllPlayers().contains(check)))
                booleans.add(true);
            else
                booleans.add(false);
        });
        return booleans.get(0);
    }

    public void removePlayer(Player owner, Player target) {
    }

    public boolean isInternal() {
        return false;
    }
}
