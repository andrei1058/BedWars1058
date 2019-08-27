package com.andrei1058.bedwars.api.events.player;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class PlayerXpGainEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private UUID player;
    private int amount;
    private XpSource xpSource;

    /**
     * Called when a player receives new xp.
     * This only works when the internal Level System is used.
     * Developers can "inject" their own level system.
     *
     * @param player   - target player.
     * @param amount   - amount of xp.
     * @param xpSource - where did the player receive xp from.
     */
    public PlayerXpGainEvent(UUID player, int amount, XpSource xpSource) {
        this.player = player;
        this.amount = amount;
        this.xpSource = xpSource;
    }

    /**
     * Get the player that have received new xp.
     */
    public UUID getPlayer() {
        return player;
    }

    /**
     * Get the amount of xp received.
     */
    public int getAmount() {
        return amount;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Lets you know why did the player received new xp.
     */
    public enum XpSource {
        PER_MINUTE, PER_TEAMMATE, GAME_WIN, OTHER
    }
}
