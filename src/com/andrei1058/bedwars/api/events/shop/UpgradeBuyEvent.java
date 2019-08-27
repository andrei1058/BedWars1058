package com.andrei1058.bedwars.api.events.shop;

import com.andrei1058.bedwars.upgrades.TeamUpgrade;
import com.andrei1058.bedwars.upgrades.UpgradeTier;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpgradeBuyEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private TeamUpgrade teamUpgrade;
    private Player buyer;
    private UpgradeTier tierBought;
    private int tierLevel;

    /**
     * Called when a Team Upgrade is bought
     */
    public UpgradeBuyEvent(TeamUpgrade teamUpgrade, Player buyer, UpgradeTier tierBought, int tierLevel) {
        this.teamUpgrade = teamUpgrade;
        this.buyer = buyer;
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
    public TeamUpgrade getTeamUpgrade() {
        return teamUpgrade;
    }

    /**
     * Get the buyer
     */
    public Player getBuyer() {
        return buyer;
    }

    /**
     * Get the bought tier
     */
    public UpgradeTier getTierBought() {
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
