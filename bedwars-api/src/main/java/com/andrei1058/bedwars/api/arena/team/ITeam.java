package com.andrei1058.bedwars.api.arena.team;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;

import java.util.List;

public interface ITeam {

    /**
     * Get team color.
     */
    TeamColor getColor();

    /**
     * Get team name.
     */
    String getName();

    /**
     * Check if is member.
     */
    boolean isMember(Player player);

    IArena getArena();

    /**
     * Get alive team members.
     */
    List<Player> getMembers();
}
