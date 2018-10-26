package com.andrei1058.bedwars.api.events;

import com.andrei1058.bedwars.shop.BuyItemsAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShopBuyEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private BuyItemsAction buyItemsAction;
    private Player buyer;

    /**
     * Triggered when someone buys from the shop
     *
     * @since API v8
     */
    public ShopBuyEvent(BuyItemsAction buyItemsAction, Player buyer) {
        this.buyItemsAction = buyItemsAction;
        this.buyer = buyer;
    }


    /**
     * Get some stuff about purchase
     *
     * @since API v8
     */
    public BuyItemsAction getBuyItemsAction() {
        return buyItemsAction;
    }

    /**
     * Get the buyer
     *
     * @since API v8
     */
    public Player getBuyer() {
        return buyer;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
