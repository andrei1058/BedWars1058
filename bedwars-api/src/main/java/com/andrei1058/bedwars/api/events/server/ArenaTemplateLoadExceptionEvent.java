package com.andrei1058.bedwars.api.events.server;

import com.andrei1058.bedwars.api.arena.constraints.ArenaConstraintViolation;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Collection;

@SuppressWarnings("unused")
public class ArenaTemplateLoadExceptionEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final String template;
    private final Collection<ArenaConstraintViolation> violationList;

    public ArenaTemplateLoadExceptionEvent(String template, Collection<ArenaConstraintViolation> violations) {
        this.template = template;
        this.violationList = violations;
    }

    public String getTemplate() {
        return template;
    }

    public Collection<ArenaConstraintViolation> getViolationList() {
        return violationList;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
