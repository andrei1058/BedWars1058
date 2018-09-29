package com.andrei1058.bedwars.arena;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class LastHit {

    private Player victim;
    private Entity damager;
    private long time;
    private static HashMap<Player, LastHit> lastHit = new HashMap<>();

    public LastHit (Player victim, Entity damager, long time){
        this.victim = victim;
        this.damager = damager;
        this.time = time;
        lastHit.put(victim, this);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setDamager(Entity damager) {
        this.damager = damager;
    }

    public Entity getDamager() {
        return damager;
    }

    public Player getVictim() {
        return victim;
    }

    public long getTime() {
        return time;
    }

    public static HashMap<Player, LastHit> getLastHit() {
        return lastHit;
    }
}
