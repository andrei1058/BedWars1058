package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.LastHit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.*;

import static com.andrei1058.bedwars.BedWars.config;
import static com.andrei1058.bedwars.BedWars.getAPI;

public class FireballListener implements Listener {

    private final double fireballExplosionSize;
    private final boolean fireballMakeFire;
    private final double fireballHorizontal;
    private final double fireballVertical;

    private final double damageSelf;
    private final double damageEnemy;
    private final double damageTeammates;
    private final boolean fireballDestroyBlocks;
    private final double fireballBlockFailureRadius;
    private final List<String> fireballBlocksThatAllowDestruction;
    private final boolean fireballDestroyOriginalBlocks;
    private final boolean fireballGenerateExplosiveParticles;
    private final boolean fireballReverseWhitelist;
    private final boolean fireballSpawnProtection;
    private final boolean fireballShopProtection;
    private final boolean fireballUpgradesProtection;
    private final boolean fireballGeneratorProtection;
    private final Vector locSubtractedDifference = new Vector(0.5, 1.3, 0.5);

    public FireballListener() {
        this.fireballExplosionSize = config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_EXPLOSION_SIZE);
        this.fireballMakeFire = config.getYml().getBoolean(ConfigPath.GENERAL_FIREBALL_MAKE_FIRE);
        this.fireballHorizontal = config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_KNOCKBACK_HORIZONTAL) * -1;
        this.fireballVertical = config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_KNOCKBACK_VERTICAL);
        this.fireballDestroyBlocks = config.getYml().getBoolean(ConfigPath.GENERAL_FIREBALL_DESTORY_BLOCKS_STATE);
        this.fireballBlockFailureRadius = config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_DESTORY_BLOCKS_RADIUS);
        this.fireballReverseWhitelist = config.getYml().getBoolean(ConfigPath.GENERAL_FIREBALL_DESTORY_BLOCKS_REVERSE_WHITELIST);
        this.fireballBlocksThatAllowDestruction = config.getYml().getStringList(ConfigPath.GENERAL_FIREBALL_DESTORY_BLOCKS_ALLOW_DESTRUCTION);
        this.fireballDestroyOriginalBlocks = config.getYml().getBoolean(ConfigPath.GENERAL_FIREBALL_DESTORY_BLOCKS_DESTORY_ORIGINAL_BLOCKS);
        this.fireballSpawnProtection = config.getYml().getBoolean(ConfigPath.GENERAL_FIREBALL_DESTORY_BLOCKS_SPAWN_PROTECTION);
        this.fireballShopProtection = config.getYml().getBoolean(ConfigPath.GENERAL_FIREBALL_DESTORY_BLOCKS_SHOP_PROTECTION);
        this.fireballUpgradesProtection = config.getYml().getBoolean(ConfigPath.GENERAL_FIREBALL_DESTORY_BLOCKS_UPGRADES_PROTECTION);
        this.fireballGeneratorProtection = config.getYml().getBoolean(ConfigPath.GENERAL_FIREBALL_DESTORY_BLOCKS_GENERATOR_PROTECTION);
        this.fireballGenerateExplosiveParticles = config.getYml().getBoolean(ConfigPath.GENERAL_FIREBALL_DESTORY_BLOCKS_GENERATE_EXPLOSIVE_PARTICLES);

        this.damageSelf = config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_DAMAGE_SELF);
        this.damageEnemy = config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_DAMAGE_ENEMY);
        this.damageTeammates = config.getYml().getDouble(ConfigPath.GENERAL_FIREBALL_DAMAGE_TEAMMATES);
    }

    @EventHandler
    public void fireballHit(ProjectileHitEvent e) {
        if(!(e.getEntity() instanceof Fireball)) return;
        Location location = e.getEntity().getLocation();

        ProjectileSource projectileSource = e.getEntity().getShooter();
        if(!(projectileSource instanceof Player)) return;
        Player source = (Player) projectileSource;

        IArena arena = Arena.getArenaByPlayer(source);

        Vector vector = location.toVector();

        World world = location.getWorld();

        assert world != null;
        Collection<Entity> nearbyEntities = world
                .getNearbyEntities(location, fireballExplosionSize, fireballExplosionSize, fireballExplosionSize);
        for(Entity entity : nearbyEntities) {
            if(!(entity instanceof Player)) continue;
            Player player = (Player) entity;
            if(!getAPI().getArenaUtil().isPlaying(player)) continue;


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

            if(player.equals(source)) {
                if(damageSelf > 0) {
                    player.damage(damageSelf); // damage shooter
                }
            } else if(arena.getTeam(player).equals(arena.getTeam(source))) {
                if(damageTeammates > 0) {
                    player.damage(damageTeammates); // damage teammates
                }
            } else {
                if(damageEnemy > 0) {
                    player.damage(damageEnemy); // damage enemies
                }
            }
        }
    }


    @EventHandler
    public void fireballDirectHit(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Fireball)) return;
        if(!(e.getEntity() instanceof Player)) return;

        if(Arena.getArenaByPlayer((Player) e.getEntity()) == null) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void fireballPrime(ExplosionPrimeEvent e) {
        if(!(e.getEntity() instanceof Fireball)) return;
        ProjectileSource shooter = ((Fireball)e.getEntity()).getShooter();
        if(!(shooter instanceof Player)) return;
        Player player = (Player) shooter;
        IArena currentArena = Arena.getArenaByPlayer(player);
        Fireball fireball = (Fireball) e.getEntity();
        Location location = fireball.getLocation();

        if(currentArena == null) return;

        e.setFire(fireballMakeFire);

        if (fireballGenerateExplosiveParticles)
        {
            BedWars.nms.playExplosiveParticles(player, location);
        }

        if (fireballDestroyBlocks)
        {
            for (int x = (int) -fireballBlockFailureRadius; x <= fireballBlockFailureRadius; x++) {
                for (int y = (int) -fireballBlockFailureRadius; y <= fireballBlockFailureRadius; y++) {
                    for (int z = (int) -fireballBlockFailureRadius; z <= fireballBlockFailureRadius; z++) {
                        Location blockLoc = new Location(location.getWorld(),
                                location.getBlockX() + x, location.getBlockY() + y, location.getBlockZ() + z);

                        if (blockLoc.distance(location) <= fireballBlockFailureRadius + 1)
                        {
                            Block currentBlock = blockLoc.getBlock();
                            boolean currentBlockCanDestroyed = !(!currentArena.isBlockPlaced(currentBlock) && !fireballDestroyOriginalBlocks);

                            if (currentBlockCanDestroyed)
                            {
                                for (ITeam team : currentArena.getTeams()) {
                                    if (fireballSpawnProtection && blockLoc.distance(team.getSpawn().clone().subtract(locSubtractedDifference)) <= currentArena.getConfig().getInt(ConfigPath.ARENA_SPAWN_PROTECTION))
                                    {
                                        currentBlockCanDestroyed = false;
                                        break;
                                    }
                                    if (fireballShopProtection && blockLoc.distance(team.getShop().clone().subtract(locSubtractedDifference)) <= currentArena.getConfig().getInt(ConfigPath.ARENA_SHOP_PROTECTION))
                                    {
                                        currentBlockCanDestroyed = false;
                                        break;
                                    }
                                    if (fireballUpgradesProtection && blockLoc.distance(team.getTeamUpgrades().clone().subtract(locSubtractedDifference)) <= currentArena.getConfig().getInt(ConfigPath.ARENA_UPGRADES_PROTECTION))
                                    {
                                        currentBlockCanDestroyed = false;
                                        break;
                                    }
                                    if (fireballGeneratorProtection)
                                    {
                                        for (IGenerator generator : team.getGenerators()) {
                                            if (blockLoc.distance(generator.getLocation().clone().subtract(locSubtractedDifference)) <= currentArena.getConfig().getInt(ConfigPath.ARENA_GENERATOR_PROTECTION))
                                            {
                                                currentBlockCanDestroyed = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }

                            if (fireballGeneratorProtection && currentBlockCanDestroyed)
                            {
                                for (IGenerator generator : currentArena.getOreGenerators())
                                {
                                    if (blockLoc.distance(generator.getLocation().clone().subtract(locSubtractedDifference)) <= currentArena.getConfig().getInt(ConfigPath.ARENA_GENERATOR_PROTECTION))
                                    {
                                        currentBlockCanDestroyed = false;
                                    }
                                }
                            }

                            boolean blockInProcessingList = fireballBlocksThatAllowDestruction.contains(currentBlock.getType().name());

                            if ((!fireballReverseWhitelist && blockInProcessingList) || (fireballReverseWhitelist && !blockInProcessingList))
                            {
                                if (currentBlockCanDestroyed)
                                {
                                    currentBlock.breakNaturally();
                                }
                            }
                        }

                    }
                }
            }
        }

    }

}
