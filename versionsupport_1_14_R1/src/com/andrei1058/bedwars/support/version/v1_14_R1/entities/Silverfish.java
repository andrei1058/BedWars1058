package com.andrei1058.bedwars.support.version.v1_14_R1.entities;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.configuration.ConfigPath;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import static com.andrei1058.bedwars.Main.shop;

public class Silverfish extends EntitySilverfish {

    private BedWarsTeam bedWarsTeam;

    private Silverfish(EntityTypes<? extends EntitySilverfish> entitytypes, World world, BedWarsTeam bedWarsTeam) {
        super(entitytypes, world);
        this.bedWarsTeam = bedWarsTeam;
    }

    @SuppressWarnings("unchecked")
    public Silverfish(EntityTypes entityTypes, World world) {
        super(entityTypes, world);
    }

    @Override
    protected void initPathfinder() {
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this,1.0D, false));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this));
        if (bedWarsTeam != null) this.targetSelector.a(2, new AttackEnemies(this, true, bedWarsTeam));
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_HEALTH));
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_SPEED));
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_DAMAGE));
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(150D);
    }

    @SuppressWarnings("ConstantConditions")
    public static org.bukkit.entity.LivingEntity spawn(Location loc, BedWarsTeam bedWarsTeam) {
        WorldServer mcWorld = ((CraftWorld)loc.getWorld()).getHandle();
        Silverfish customEnt = new Silverfish(EntityTypes.SILVERFISH, mcWorld, bedWarsTeam);
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity)customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        customEnt.setCustomNameVisible(true);
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (LivingEntity) customEnt.getBukkitEntity();
    }
}
