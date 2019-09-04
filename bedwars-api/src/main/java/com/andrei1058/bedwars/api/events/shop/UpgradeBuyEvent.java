package com.andrei1058.bedwars.api.events.shop;

import com.andrei1058.bedwars.api.arena.upgrades.ITeamUpgrade;
import com.andrei1058.bedwars.api.arena.upgrades.IUpgradeTier;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpgradeBuyEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private ITeamUpgrade teamUpgrade;
    private Player player;
    private IUpgradeTier tierBought;
    private int tierLevel;

    /**
     * Called when a Team Upgrade is bought
     */
    public UpgradeBuyEvent(ITeamUpgrade teamUpgrade, Player player, IUpgradeTier tierBought, int tierLevel) {
        this.teamUpgrade = teamUpgrade;
        this.player = player;
        this.tierBought = tierBought;
        this.tierLevel = tierLevel;
    }


    /**
     * Ge the tier level
     * Ex: Tier 3
     */
    public int getTierLevel() {
        return tierLevel;
    }

    /**
     * Get stuff about team upgrade
     */
    public ITeamUpgrade getTeamUpgrade() {
        return teamUpgrade;
    }

    /**
     * Get who bought the upgrade
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the bought tier
     */
    public IUpgradeTier getTierBought() {
        return tierBought;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
