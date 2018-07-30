package com.andrei1058.bedwars.api;

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

    /**
     * Triggered when a Team Upgrade is bought
     *
     * @since API v8
     */
    public UpgradeBuyEvent(TeamUpgrade teamUpgrade, Player buyer, UpgradeTier tierBought) {
        this.teamUpgrade = teamUpgrade;
        this.buyer = buyer;
        this.tierBought = tierBought;
    }

    /**
     * Get stuff about team upgrade
     *
     * @since API v8
     */
    public TeamUpgrade getTeamUpgrade() {
        return teamUpgrade;
    }

    /**
     * Get the buyer
     *
     * @since API v8
     */
    public Player getBuyer() {
        return buyer;
    }

    /**
     * Get the bought tier
     *
     * @since API v8
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
