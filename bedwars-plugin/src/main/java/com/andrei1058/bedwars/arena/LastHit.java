package com.andrei1058.bedwars.arena;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LastHit {

    private UUID victim;
    private Entity damager;
    private long time;
    private static ConcurrentHashMap<UUID, LastHit> lastHit = new ConcurrentHashMap<>();

    public LastHit(@NotNull Player victim, Entity damager, long time) {
        this.victim = victim.getUniqueId();
        this.damager = damager;
        this.time = time;
        lastHit.put(victim.getUniqueId(), this);
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

    public UUID getVictim() {
        return victim;
    }

    public void remove() {
        lastHit.remove(victim);
    }

    public long getTime() {
        return time;
    }

    public static LastHit getLastHit(@NotNull Player player) {
        return lastHit.getOrDefault(player.getUniqueId(), null);
    }
}
