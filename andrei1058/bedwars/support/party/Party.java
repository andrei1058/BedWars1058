package com.andrei1058.bedwars.support.party;

import org.bukkit.entity.Player;

import java.util.List;

public interface Party {

    boolean hasParty(Player p);

    int partySize(Player p);

    boolean isOwner(Player p);

    List<Player> getMembers(Player owner);
}
