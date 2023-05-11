package com.tomkeuper.bedwars.api.events.gameplay;

import com.tomkeuper.bedwars.api.arena.generator.IGenerator;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GeneratorDropEvent extends Event  implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private final IGenerator generator;
    private boolean cancelled = false;

    /**
     * Called when a generator drops an item.
     * @param generator the ore generator.
     */

    public GeneratorDropEvent(IGenerator generator) {
        this.generator = generator;
    }


    public IGenerator getGenerator() {return generator;}

    public HandlerList getHandlers() {return HANDLERS;}

    public static HandlerList getHandlerList() {return HANDLERS;}

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
