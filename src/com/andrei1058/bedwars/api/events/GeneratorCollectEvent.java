package com.andrei1058.bedwars.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class GeneratorCollectEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private ItemStack itemStack;
    private boolean cancelled = false;

    /**
     * Triggered when collect from generators
     *
     * @since API 8
     */
    public GeneratorCollectEvent(Player player, ItemStack itemStack) {
        this.player = player;
        this.itemStack = itemStack;
    }

    /**
     * Get the player
     *
     * @since API v8
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the itemStack involved
     *
     * @since API v8
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Cancel this event
     *
     * @since API 10
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * @since API 10
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
