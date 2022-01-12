/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.api.events.shop;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.shop.ICategoryContent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShopBuyEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player buyer;
    private final IArena arena;
    private final ICategoryContent categoryContent;
    private boolean cancelled = false;

    /**
     * Triggered when a player buys from the shop
     * 
     * @deprecated Use {@link #ShopBuyEvent(Player, IArena, ICategoryContent)}
     */
    @Deprecated
    public ShopBuyEvent(Player buyer, ICategoryContent categoryContent) {
        this.categoryContent = categoryContent;
        this.buyer = buyer;
        this.arena = null;
    }

    /**
     * Triggered when a player buys from the shop
     */
    public ShopBuyEvent(Player buyer, IArena arena, ICategoryContent categoryContent) {
        this.categoryContent = categoryContent;
        this.buyer = buyer;
        this.arena = arena;
    }

    public IArena getArena() {
        return arena;
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
