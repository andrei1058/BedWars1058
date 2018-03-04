package com.andrei1058.bedwars.support.bukkit.v1_8_R2;

import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import net.minecraft.server.v1_8_R2.*;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;

import static com.andrei1058.bedwars.Main.lang;
import static com.andrei1058.bedwars.Main.shop;

public class IGolem extends EntityIronGolem {

    public IGolem(World world, BedWarsTeam team) {
        super(world);
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
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this,1.0D, false));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
        this.targetSelector.a(2, new AttackEnemies<>(this, EntityHuman.class, true, team.getMembers()));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.6D));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(shop.getYml().getDouble("utilities.ironGolem.health"));
        this.getAttributeInstance(GenericAttributes.d).setValue(shop.getYml().getDouble("utilities.ironGolem.speed"));
    }

    public static IGolem spawn(Location loc, BedWarsTeam bedWarsTeam) {
        WorldServer mcWorld = ((CraftWorld)loc.getWorld()).getHandle();
        IGolem customEnt = new IGolem(mcWorld, bedWarsTeam);
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity)customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        customEnt.setCustomNameVisible(true);
        customEnt.setCustomName(lang.m(lang.iGolemName).replace("{despawn}", String.valueOf(shop.getInt("utilities.ironGolem.despawn"))).replace("{health}",
                StringUtils.repeat(lang.m(lang.iGolemHealthFormat)+" ", 10)).replace("{TeamColor}", TeamColor.getChatColor(bedWarsTeam.getColor()).toString()));
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return customEnt;
    }
}
