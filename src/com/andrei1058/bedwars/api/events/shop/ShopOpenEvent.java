package com.andrei1058.bedwars.api.events.shop;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShopOpenEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private boolean cancelled = false;

    /**
     * Triggered when the shop NPS is clicked.
     */
    public ShopOpenEvent(Player player) {
        this.player = player;
    }

    /**
     * Get the player who opened the shop.
     */
    public Player getPlayer() {
        return player;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
