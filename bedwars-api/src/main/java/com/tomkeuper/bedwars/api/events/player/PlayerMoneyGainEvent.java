package com.tomkeuper.bedwars.api.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerMoneyGainEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private int amount;
    private MoneySource moneySource;

    /**
     * Called when a player receives money.
     * This only works when an economy system is found.
     *
     * @param player   - target player.
     * @param amount   - amount of xp.
     * @param moneySource - where did the player receive money from.
     */
    public PlayerMoneyGainEvent(Player player, int amount, MoneySource moneySource) {
        this.player = player;
        this.amount = amount;
        this.moneySource = moneySource;
    }

    /**
     * Get the player that have received money.
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

    /**
     * Set a custom amount on the money received
     * This can be used for boosters
     * @param amount - amount of xp
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Get xp source
     */
    public MoneySource getMoneySource() {
        return moneySource;
    }

    /**
     * Lets you know why did the player received money.
     */
    public enum MoneySource {
        PER_MINUTE, PER_TEAMMATE, GAME_WIN, BED_DESTROYED, FINAL_KILL, REGULAR_KILL, OTHER
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
