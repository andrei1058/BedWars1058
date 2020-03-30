package com.andrei1058.bedwars.support.preloadedparty;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PreLoadedParty {

    private String owner;
    private List<Player> members = new ArrayList<>();

    private static ConcurrentHashMap<String, PreLoadedParty> preLoadedParties = new ConcurrentHashMap<>();

    public PreLoadedParty(String owner){
        PreLoadedParty plp = getPartyByOwner(owner);
        if (plp != null){
            plp.clean();
        }
        this.owner = owner;
        preLoadedParties.put(owner, this);
    }

    public static PreLoadedParty getPartyByOwner(String owner){
        return preLoadedParties.getOrDefault(owner, null);
    }

    public void addMember(Player player){
        members.add(player);
    }

    public void teamUp(){
        if (this.owner == null) return;
        Player owner = Bukkit.getPlayer(this.owner);
        if (owner == null) return;
        if (!owner.isOnline()) return;
        for (Player player : members){
            if (!player.getName().equalsIgnoreCase(this.owner)){
                BedWars.getParty().addMember(owner, player);
            }
        }
        preLoadedParties.remove(this.owner);
    }

    public static ConcurrentHashMap<String, PreLoadedParty> getPreLoadedParties() {
        return preLoadedParties;
    }
    public void clean(){
        preLoadedParties.remove(this.owner);
    }
}
