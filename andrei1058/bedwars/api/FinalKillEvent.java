package com.andrei1058.bedwars.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FinalKillEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private String arenaName;
    private Player killer, killed;

    public FinalKillEvent(String arenaName, Player killed, Player killer){
       this.arenaName = arenaName;
       this.killed = killer;
       this.killed = killed;
    }

    public String getArenaName() {
        return arenaName;
    }

    public Player getKilled() {
        return killed;
    }

    public Player getKiller() {
        return killer;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
