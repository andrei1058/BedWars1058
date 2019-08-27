package com.andrei1058.bedwars.support.version.v1_13_R1.entities;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.language.Messages;
import com.google.common.collect.Sets;
import net.minecraft.server.v1_13_R1.*;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;

@SuppressWarnings("unchecked")
public class IGolem extends EntityIronGolem {

    @SuppressWarnings("WeakerAccess")
    protected IGolem(World world, BedWarsTeam bedWarsTeam) {
        super(world);

        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set(this.goalSelector, Sets.newLinkedHashSet());
            bField.set(this.targetSelector, Sets.newLinkedHashSet());
            cField.set(this.goalSelector, Sets.newLinkedHashSet());
            cField.set(this.targetSelector, Sets.newLinkedHashSet());
        } catch (IllegalAccessException | NoSuchFieldException e1) {
            e1.printStackTrace();
        }
        this.setSize(1.4F, 2.9F);
        ((Navigation)this.getNavigation()).a(true);
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this,1.0D, false));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(2, new AttackEnemies(this, EntityHuman.class, true, bedWarsTeam));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.6D));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    }

    public IGolem(World world){
        super(world);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(Main.shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_HEALTH));
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(Main.shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_SPEED));
    }

    public static LivingEntity spawn(Location loc, BedWarsTeam bedWarsTeam) {
        WorldServer mcWorld = ((CraftWorld)loc.getWorld()).getHandle();
        IGolem customEnt = new IGolem(mcWorld, bedWarsTeam);
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity)customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        customEnt.setCustomNameVisible(true);
        (customEnt.getBukkitEntity()).setCustomName(Main.lang.m(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME)
                .replace("{despawn}", String.valueOf(Main.shop.getYml().getInt(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_DESPAWN))).replace("{health}",
                StringUtils.repeat(Main.lang.m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH), 10)).replace("{TeamColor}",
                        TeamColor.getChatColor(bedWarsTeam.getColor()).toString()));
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (LivingEntity) customEnt.getBukkitEntity();
    }
}
