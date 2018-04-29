package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BedBreakEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private BedWarsTeam playerTeam, victimTeam;

    public BedBreakEvent(Player p, BedWarsTeam playerTeam, BedWarsTeam victimTeam) {
        this.player = p;
        this.playerTeam = playerTeam;
        this.victimTeam = victimTeam;
    }

    public BedWarsTeam getPlayerTeam() {
        return playerTeam;
    }

    public BedWarsTeam getVictimTeam() {
        return victimTeam;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
