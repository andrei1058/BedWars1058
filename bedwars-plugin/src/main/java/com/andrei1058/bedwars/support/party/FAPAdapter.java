package com.andrei1058.bedwars.support.party;

import com.andrei1058.bedwars.api.party.Party;
import me.sk8ingduck.friendsystem.SpigotAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FAPAdapter implements Party {

	private me.sk8ingduck.friendsystem.util.Party getParty(Player player) {
		return SpigotAPI.getInstance().getMysql().getParty(player.getUniqueId());
	}

	@Override
	public boolean hasParty(Player p) {
		return getParty(p) != null;
	}

	@Override
	public int partySize(Player p) {
		me.sk8ingduck.friendsystem.util.Party party = getParty(p);
		return party == null ? 0 : party.getAllMembers().size();
	}

	@Override
	public boolean isOwner(Player p) {
		me.sk8ingduck.friendsystem.util.Party party = getParty(p);
		return party != null && party.getLeaderUUID().equals(p.getUniqueId());
	}

	public List<Player> getMembers(Player owner) {
		me.sk8ingduck.friendsystem.util.Party party = getParty(owner);
		return party == null ? Collections.emptyList() :
				party.getAllMembers().stream()
				.map(Bukkit::getPlayer)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
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
		me.sk8ingduck.friendsystem.util.Party party = getParty(owner);
		if (party == null) return false;

		return party.getAllMembers().contains(check.getUniqueId());
	}

	@Override
	public void removePlayer(Player owner, Player target) {

	}

	@Override
	public Player getOwner(Player member) {
		me.sk8ingduck.friendsystem.util.Party party = getParty(member);
		return party == null ? member : Bukkit.getPlayer(party.getLeaderUUID());
	}

	@Override
	public void promote(@NotNull Player owner, @NotNull Player target) {
	}

	@Override
	public boolean isInternal() {
		return false;
	}
}
