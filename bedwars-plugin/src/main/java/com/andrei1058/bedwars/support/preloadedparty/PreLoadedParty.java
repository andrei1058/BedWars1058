package com.andrei1058.bedwars.support.preloadedparty;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PreLoadedParty {

    private String arenaIdentifier;
    private String owner;
    private List<Player> members = new ArrayList<>();

    private static LinkedList<PreLoadedParty> preLoadedParties = new LinkedList<>();

    public PreLoadedParty(String arenaIdentifier, String owner){
        this.arenaIdentifier = arenaIdentifier;
        this.owner = owner;
        preLoadedParties.add(this);
    }

    public static PreLoadedParty getPartyByOwner(String owner){
        for (PreLoadedParty plp : preLoadedParties){
            if (plp.owner.equals(owner)) return plp;
        }
        return null;
    }

    public void addMember(Player player){
        if (player.getName().equals(owner)){
            BedWars.getParty().createParty(player);
            preLoadedParties.remove(this);
            for (Player p : members){
                BedWars.getParty().addMember(player, p);
            }
            return;
        }
        members.add(player);
    }

    public static LinkedList<PreLoadedParty> getPreLoadedParties() {
        return preLoadedParties;
    }
}
