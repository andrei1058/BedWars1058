package com.andrei1058.bedwars.api.events.shop;

import com.andrei1058.bedwars.api.arena.shop.ICategoryContent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShopBuyEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player buyer;
    private final ICategoryContent categoryContent;
    private boolean cancelled = false;

    /**
     * Triggered when a player buys from the shop
     */
    public ShopBuyEvent(Player buyer, ICategoryContent categoryContent) {
        this.categoryContent = categoryContent;
        this.buyer = buyer;
    }

    /**
     * Get the buyer
     */
    public Player getBuyer() {
        return buyer;
    }

    /**
     * Get the shop category content bought.
     */
    public ICategoryContent getCategoryContent() {
        return categoryContent;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
