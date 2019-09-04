package com.andrei1058.bedwars.api.events.server;

import com.andrei1058.bedwars.api.server.ISetupSession;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SetupSessionCloseEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private ISetupSession setupSession;

    /**
     * Called when the owner closed the arena setup.
     */
    public SetupSessionCloseEvent(ISetupSession setupSession) {
        this.setupSession = setupSession;
    }

    /**
     * Get setup session.
     */
    public ISetupSession getSetupSession() {
        return setupSession;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
