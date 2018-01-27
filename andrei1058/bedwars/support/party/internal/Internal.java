package com.andrei1058.bedwars.support.party.internal;

import com.andrei1058.bedwars.support.party.Party;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Internal implements Party {
    private static List<Party> parites = new ArrayList<>();

    @Override
    public boolean hasParty(Player p) {
        for (Party party : parites){
            if (party.members.contains(p)) return true;
        }
        return false;
    }

    @Override
    public int partySize(Player p) {
        for (Party party : parites){
            if (party.members.contains(p)){
                return party.members.size();
            }
        }
        return 0;
    }

    @Override
    public boolean isOwner(Player p) {
        for (Party party : parites){
            if (party.members.contains(p)){
                if (party.owner==p) return true;
            }
        }
        return false;
    }

    @Override
    public List<Player> getMembers(Player owner) {
        for (Party party : parites){
            if (party.members.contains(owner)){
                return party.members;
            }
        }
        return null;
    }

    class Party {

        private List<Player> members = new ArrayList<>();
        private Player owner;
        public Party(Player p){
            owner = p;
            Internal.parites.add(this);
        }
    }
}
