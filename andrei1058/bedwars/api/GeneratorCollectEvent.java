package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.arena.OreGenerator;
import com.boydti.fawe.jnbt.anvil.generator.OreGen;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class GeneratorCollectEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private ItemStack itemStack;

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

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
