package com.andrei1058.bedwars.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FinalKillEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private String arenaName;
    private Player killer, killed;

    @Deprecated
    public FinalKillEvent(String arenaName, Player killed, Player killer){
       this.arenaName = arenaName;
       this.killer = killer;
       this.killed = killed;
    }

    @Deprecated
    public String getArenaName() {
        return arenaName;
    }

    @Deprecated
    public Player getKilled() {
        return killed;
    }

    @Deprecated
    public Player getKiller() {
        return killer;
    }

    @Deprecated
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Deprecated
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
