package com.andrei1058.bedwars.api.upgrades;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface TrapAction {

    /**
     * @return trap identifier.
     */
    String getName();

    /**
     * Manage what happens on {@param player} enters {@param targetTeam}.
     *
     * @param player     The player who enters the targetTeam.
     * @param playerTeam Enemy player team.
     * @param targetTeam The affected team.
     */
    void onTrigger(@NotNull Player player, ITeam playerTeam, ITeam targetTeam);
}
