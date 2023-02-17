package com.andrei1058.bedwars.api.events.sidebar;

import com.andrei1058.bedwars.api.sidebar.ISidebar;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSidebarInitEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;

    private Player player;
    private ISidebar sidebar;

    public PlayerSidebarInitEvent(Player player, ISidebar sidebar) {
        this.player = player;
        this.sidebar = sidebar;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ISidebar getSidebar() {
        return sidebar;
    }

    public void setSidebar(ISidebar sidebar) {
        this.sidebar = sidebar;
    }
}
