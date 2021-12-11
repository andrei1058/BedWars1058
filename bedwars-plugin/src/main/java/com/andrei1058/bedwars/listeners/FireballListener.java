package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.LastHit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.andrei1058.bedwars.BedWars.config;
import static com.andrei1058.bedwars.BedWars.getAPI;

public class FireballListener implements Listener {

    private final double fireballExplosionSize;
    private final boolean fireballMakeFire;
    private final double fireballHorizontal;
    private final double fireballVertical;

    public FireballListener() {
        this.fireballExplosionSize = config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_EXPLOSION_SIZE);
        this.fireballMakeFire = config.getYml().getBoolean(ConfigPath.GENERAL_FIREBALL_MAKE_FIRE);
        this.fireballHorizontal = config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_KNOCKBACK_HORIZONTAL) * -1;
        this.fireballVertical = config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_KNOCKBACK_VERTICAL);
    }

    Map<Location, Player> explosionSources = new HashMap<>();

    @EventHandler
    public void fireballPrime(ExplosionPrimeEvent e) {
        if(!(e.getEntity() instanceof Fireball)) return;
        ProjectileSource shooter = ((Fireball) e.getEntity()).getShooter();
        if(!(shooter instanceof Player)) return;
        IArena a = Arena.getArenaByPlayer((Player) shooter);
        if(a == null) return;
        e.setRadius((float) fireballExplosionSize);
        e.setFire(fireballMakeFire);
        explosionSources.put(e.getEntity().getLocation(), (Player) shooter);
    }

    @EventHandler
    public void fireballDirectHit(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Fireball)) return;
        if(!(e.getEntity() instanceof Player)) return;

        if(Arena.getArenaByPlayer((Player) e.getEntity()) == null) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void fireballExplode(EntityExplodeEvent e) {
        if(!(e.getEntity() instanceof Fireball)) return;
        Location location = e.getLocation();

        Player source = null;
        for(Location sourceLocation : explosionSources.keySet()) {
            if(sourceLocation == null) continue;
            if(sourceLocation.distance(location) < 0.05) {
                source = explosionSources.get(sourceLocation);
                explosionSources.remove(sourceLocation);
            }
        }

        if(source == null) return;

        Vector vector = location.toVector();

        World world = location.getWorld();

        assert world != null;
        Collection<Entity> nearbyEntities = world
                .getNearbyEntities(location, fireballExplosionSize, fireballExplosionSize, fireballExplosionSize);
        for(Entity entity : nearbyEntities) {
            if(!(entity instanceof Player)) continue;
            Player player = (Player) entity;
            if(!getAPI().getArenaUtil().isPlaying(player)) continue;
            IArena arena = Arena.getArenaByPlayer(player);


            Vector playerVector = player.getLocation().toVector();
            Vector normalizedVector = vector.subtract(playerVector).normalize();
            Vector horizontalVector = normalizedVector.multiply(fireballHorizontal);
            double y = normalizedVector.getY();
            if(y < 0 ) y += 1.5;
            if(y <= 0.5) {
                y = fireballVertical*1.5; // kb for not jumping
            } else {
                y = y*fireballVertical*1.5; // kb for jumping
            }
            player.setVelocity(horizontalVector.setY(y));

            LastHit lh = LastHit.getLastHit(player);
            if (lh != null) {
                lh.setDamager(source);
                lh.setTime(System.currentTimeMillis());
            } else {
                new LastHit(player, source, System.currentTimeMillis());
            }


            if(!player.equals(source) && arena.getTeam(player).equals(arena.getTeam(source))) continue;

            player.damage(2.0);
        }

    }
}
