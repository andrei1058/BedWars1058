package com.andrei1058.bedwars.api.events;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EnemyBaseLeaveEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player enemy;
    private BedWarsTeam enemyTeam, team;

    public EnemyBaseLeaveEvent(Player p, BedWarsTeam pTeam, BedWarsTeam enter) {
        this.enemy = p;
        this.enemyTeam = pTeam;
        this.team = enter;
    }

    public BedWarsTeam getEnemyTeam() {
        return enemyTeam;
    }

    public Player getEnemy() {
        return enemy;
    }

    public BedWarsTeam getTeam() {
        return team;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
