package com.andrei1058.bedwars.support.bukkit.v1_8_R3;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;

import static com.andrei1058.bedwars.Main.shop;

public class IGolem extends EntityIronGolem {

    private BedWarsTeam team;
    private int despawn = 225;

    public IGolem(World world, BedWarsTeam team) {
        super(world);
        this.team = team;
        this.despawn = shop.getInt("utilities.ironGolem.despawn");
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set(this.goalSelector, new UnsafeList());
            bField.set(this.targetSelector, new UnsafeList());
            cField.set(this.goalSelector, new UnsafeList());
            cField.set(this.targetSelector, new UnsafeList());
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        }
        this.setSize(1.4F, 2.9F);
        ((Navigation)this.getNavigation()).a(true);
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
        this.targetSelector.a(2, new AttackEnemies<>(this, EntityHuman.class, true, team.getMembers()));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.6D));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(shop.getYml().getDouble("utilities.ironGolem.health"));
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(shop.getYml().getDouble("utilities.ironGolem.speed"));
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(shop.getYml().getDouble("utilities.ironGolem.damage"));
    }


    public static CraftEntity spawn(Location loc, BedWarsTeam bedWarsTeam, String name) {
        WorldServer mcWorld = ((CraftWorld)loc.getWorld()).getHandle();
        IGolem customEnt = new IGolem(mcWorld, bedWarsTeam);
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity)customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        customEnt.setCustomNameVisible(true);
        customEnt.setCustomName(name);
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return customEnt.getBukkitEntity();
    }
}
