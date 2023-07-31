package com.andrei1058.bedwars.support.party;

import com.andrei1058.bedwars.api.party.Party;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.api.party.PartyManager;
import de.simonsator.partyandfriends.spigot.api.party.PlayerParty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PAFBungeecordRedisApi implements Party {
    //Party and Friends Extended for BungeeCord Support by JT122406

    private PlayerParty getPAFParty(Player p) {
        if (PAFPlayerManager.getInstance() == null) return null;
        return PartyManager.getInstance().getParty(PAFPlayerManager.getInstance().getPlayer(p.getUniqueId()));
    }

    @Override
    public boolean hasParty(Player p) {
        return getPAFParty(p) != null;
    }

    @Override
    public int partySize(Player p) {
        return getMembers(p).size();
    }

    @Override
    public boolean isOwner(Player p) {
        PAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(p.getUniqueId());
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
        for (PAFPlayer players : party.getAllPlayers()) {
            Player bukkitPlayer = Bukkit.getPlayer(players.getUniqueId());
            if (bukkitPlayer != null)
                playerList.add(bukkitPlayer);
        }
        return playerList;
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
        PlayerParty party = getPAFParty(owner);
        if (party == null)
            return false;
        return party.isInParty(PAFPlayerManager.getInstance().getPlayer(check.getUniqueId()));
    }

    @Override
    public void removePlayer(Player owner, Player target) {
    }

    @Override
    public Player getOwner(Player member) {
        return Bukkit.getPlayer(getPAFParty(member).getLeader().getUniqueId());
    }

    @Override
    public void promote(@NotNull Player owner, @NotNull Player target) {
    }

    @Override
    public boolean isInternal() {
        return false;
    }
}
