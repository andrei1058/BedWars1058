package com.andrei1058.bedwars.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;

public class PlayerAfkEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private AFKType afkType;

    /**
     * Called when a Player goes AFK or comes back from AFK
     *
     * @since API 10
     */
    public PlayerAfkEvent(Player player, AFKType afkType) {
        this.afkType = afkType;
        this.player = player;
    }

    /**
     * Get player
     *
     * @since API 10
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Check if player target went AFK or come back
     *
     * @since API 10
     */
    public AFKType getAfkType() {
        return afkType;
    }

    public enum AFKType {
        /**
         * When a player goes AFK
         *
         * @since API 10
         */
        START,

        /**
         * When a player comes back from AFK
         */
        END
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Contract(pure = true)
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
