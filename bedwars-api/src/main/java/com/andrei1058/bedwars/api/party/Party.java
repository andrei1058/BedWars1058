package com.andrei1058.bedwars.api.party;

import org.bukkit.entity.Player;

import java.util.List;

public interface Party {

    boolean hasParty(Player p);

    int partySize(Player p);

    boolean isOwner(Player p);

    List<Player> getMembers(Player owner);

    void createParty(Player owner, Player... members);

    void addMember(Player owner, Player member);

    void removeFromParty(Player member);

    void disband(Player owner);

    boolean isMember(Player owner, Player check);

    void removePlayer(Player owner, Player target);

    boolean isInternal();
}
