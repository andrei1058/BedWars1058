package com.andrei1058.bedwars.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLevelUpEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private int newXpTarget;
    private int newLevel;

    /**
     * Called when a player levels up.
     * This only works when the internal Level System is used.
     * Developers can "inject" their own level system.
     *
     * @param player    - target player.
     * @param newLevel  - gained level.
     * @param levelUpXp - new xp target to level up.
     */
    public PlayerLevelUpEvent(Player player, int newLevel, int levelUpXp) {
        this.player = player;
        this.newLevel = newLevel;
        this.newXpTarget = levelUpXp;
    }

    /**
     * Get player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get new player level.
     */
    public int getNewLevel() {
        return newLevel;
    }

    /**
     * Get new xp target to level up.
     */
    public int getNewXpTarget() {
        return newXpTarget;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
