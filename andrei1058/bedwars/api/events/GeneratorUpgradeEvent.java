package com.andrei1058.bedwars.api.events;

import com.andrei1058.bedwars.api.GeneratorType;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;

public class GeneratorUpgradeEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private GeneratorType generatorType;
    private Location location;

    public GeneratorUpgradeEvent(GeneratorType generatorType, Location location){
        this.generatorType = generatorType;
        this.location = location;
    }

    public GeneratorType getGeneratorType() {
        return generatorType;
    }

    public Location getLocation() {
        return location;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Contract(pure = true)
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
