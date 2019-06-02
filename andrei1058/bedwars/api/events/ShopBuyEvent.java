package com.andrei1058.bedwars.api.events;

import com.andrei1058.bedwars.shop.main.CategoryContent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShopBuyEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player buyer;
    private CategoryContent categoryContent;

    /**
     * Triggered when a player buys from the shop
     *
     * @since API 12
     */
    public ShopBuyEvent(Player buyer, CategoryContent categoryContent) {
        this.categoryContent = categoryContent;
        this.buyer = buyer;
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
     * Get the shop category content bought
     *
     * @since API 12
     */
    public CategoryContent getCategoryContent() {
        return categoryContent;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
