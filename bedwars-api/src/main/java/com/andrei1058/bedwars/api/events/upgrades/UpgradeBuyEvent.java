package com.andrei1058.bedwars.api.events.upgrades;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.upgrades.TeamUpgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpgradeBuyEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private TeamUpgrade teamUpgrade;
    private Player player;
    private ITeam team;

    /**
     * Called when a Team Upgrade is bought
     */
    public UpgradeBuyEvent(TeamUpgrade teamUpgrade, Player player, ITeam team) {
        this.teamUpgrade = teamUpgrade;
        this.player = player;
        this.team = team;
    }

    /**
     * Get who bought the upgrade
     */
    public Player getPlayer() {
        return player;
    }

    public ITeam getTeam() {
        return team;
    }

    public TeamUpgrade getTeamUpgrade() {
        return teamUpgrade;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
