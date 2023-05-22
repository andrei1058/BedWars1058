package com.tomkeuper.bedwars.listeners;

import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.configuration.ConfigPath;
import com.tomkeuper.bedwars.api.language.Language;
import com.tomkeuper.bedwars.api.language.Messages;
import com.tomkeuper.bedwars.arena.Arena;
import com.tomkeuper.bedwars.arena.LastHit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.*;

import static com.tomkeuper.bedwars.BedWars.config;
import static com.tomkeuper.bedwars.BedWars.getAPI;

public class FireballListener implements Listener {

    private final double fireballExplosionSize, fireballHorizontal, fireballVertical;
    private final double damageSelf, damageEnemy, damageTeammates;
    private final double fireballSpeedMultiplier, fireballCooldown;
    private final boolean fireballMakeFire;

    public FireballListener() {
        this.fireballExplosionSize = BedWars.config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_EXPLOSION_SIZE);
        this.fireballMakeFire = BedWars.config.getYml().getBoolean(ConfigPath.GENERAL_FIREBALL_MAKE_FIRE);
        this.fireballHorizontal = BedWars.config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_KNOCKBACK_HORIZONTAL) * -1;
        this.fireballVertical = BedWars.config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_KNOCKBACK_VERTICAL);
        this.damageSelf = BedWars.config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_DAMAGE_SELF);
        this.damageEnemy = BedWars.config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_DAMAGE_ENEMY);
        this.damageTeammates = BedWars.config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_DAMAGE_TEAMMATES);
        this.fireballSpeedMultiplier = BedWars.config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_SPEED_MULTIPLIER);
        this.fireballCooldown = BedWars.config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_COOLDOWN);
    }

    @EventHandler
    public void onFireballInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack handItem = e.getItem();
        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)) {
            return;
        }
        if (handItem == null) {
            return;
        }
        IArena arena = Arena.getArenaByPlayer(player);
        if (arena == null) {
            return;
        }
        if (arena.getStatus() != GameState.playing) {
            return;
        }
        if (handItem.getType() != BedWars.nms.materialFireball()) {
            return;
        }
        e.setCancelled(true);
        if (System.currentTimeMillis() - arena.getFireballCooldowns().getOrDefault(player.getUniqueId(), 0L) <= (fireballCooldown * 1000)) {
            if (fireballCooldown >= 1.0) player.sendMessage(Language.getMsg(player, Messages.ARENA_FIREBALL_COOLDOWN).replace("%bw_cooldown%", String.valueOf(fireballCooldown)));
            return;
        }
        arena.getFireballCooldowns().put(player.getUniqueId(), System.currentTimeMillis());
        Fireball fireball = player.launchProjectile(Fireball.class);
        Vector direction = player.getEyeLocation().getDirection();
        fireball = BedWars.nms.setFireballDirection(fireball, direction);
        fireball.setVelocity(fireball.getDirection().multiply(fireballSpeedMultiplier));
        //fireball.setIsIncendiary(false); // apparently this on <12 makes the fireball not explode on hit. wtf bukkit?
        fireball.setYield((float) fireballExplosionSize);
        fireball.setMetadata("bw1058", new FixedMetadataValue(BedWars.plugin, "ceva"));
        BedWars.nms.minusAmount(player, handItem, 1);
    }

    @EventHandler
    public void fireballHit(ProjectileHitEvent e) {
        if (!(e.getEntity() instanceof Fireball)) {
            return;
        }
        Location location = e.getEntity().getLocation();
        ProjectileSource projectileSource = e.getEntity().getShooter();
        if (!(projectileSource instanceof Player)) {
            return;
        }
        Player source = (Player) projectileSource;
        IArena arena = Arena.getArenaByPlayer(source);
        if (arena == null) {
            return;
        }
        if (arena.getStatus() != GameState.playing) {
            return;
        }
        Vector vector = location.toVector();
        World world = location.getWorld();
        if (world == null) {
            return;
        }
        Collection<Entity> nearbyEntities = world.getNearbyEntities(location, fireballExplosionSize, fireballExplosionSize, fireballExplosionSize);
        for (Entity entity : nearbyEntities) {
            if (!(entity instanceof Player)) {
                continue;
            }
            Player player = (Player) entity;
            if (!Arena.isInArena(player)) {
                continue;
            }
            Vector playerVector = player.getLocation().toVector();
            Vector normalizedVector = vector.subtract(playerVector).normalize();
            Vector horizontalVector = normalizedVector.multiply(fireballHorizontal);
            double y = normalizedVector.getY();
            if (y < 0) {
                y += 1.5;
            }
            if (y <= 0.5) {
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
            if (player.equals(source)) {
                if (damageSelf > 0) {
                    player.damage(damageSelf); // damage shooter
                }
            } else if (arena.getTeam(player).equals(arena.getTeam(source))) {
                if (damageTeammates > 0) {
                    player.damage(damageTeammates); // damage teammates
                }
            } else {
                if (damageEnemy > 0) {
                    player.damage(damageEnemy); // damage enemies
                }
            }
        }
    }

    @EventHandler
    public void fireballDirectHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Fireball)) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getEntity();
        if (Arena.isInArena(player)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void fireballPrime(ExplosionPrimeEvent e) {
        if (!(e.getEntity() instanceof Fireball)) {
            return;
        }
        Fireball fireball = (Fireball) e.getEntity();
        ProjectileSource shooter = fireball.getShooter();
        if (!(shooter instanceof Player)) {
            return;
        }
        Player player = (Player) shooter;
        if (Arena.isInArena(player)) {
            return;
        }
        e.setFire(fireballMakeFire);
    }

}
