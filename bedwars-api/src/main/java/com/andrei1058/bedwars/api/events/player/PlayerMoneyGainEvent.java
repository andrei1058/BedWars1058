package com.andrei1058.bedwars.api.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerMoneyGainEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final int amount;

    /**
     * Called when a player receives money in-game.
     *
     * @param player   - target player.
     * @param amount   - amount of money.
     */
    public PlayerMoneyGainEvent(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    /**
     * Get the player that have received the money.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the amount of money received.
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
}