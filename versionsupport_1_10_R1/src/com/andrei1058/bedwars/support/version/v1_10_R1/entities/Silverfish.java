package com.andrei1058.bedwars.support.version.v1_10_R1.entities;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.configuration.ConfigPath;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_10_R1.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_10_R1.util.UnsafeList;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;
import java.util.Random;

import static com.andrei1058.bedwars.Main.shop;

public class Silverfish extends EntitySilverfish {

    @SuppressWarnings("WeakerAccess")
    public Silverfish(World world, BedWarsTeam bedWarsTeam) {
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
        } catch (IllegalAccessException | NoSuchFieldException e1) {
            e1.printStackTrace();
        }
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this,1.0D, false));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(2, new AttackEnemies<>(this, EntityHuman.class, true, bedWarsTeam));
        this.goalSelector.a(5, new PathfinderGoalSilverfishHideInBlock(this));
    }

    static class PathfinderGoalSilverfishHideInBlock extends PathfinderGoalRandomStroll {
        private final EntitySilverfish silverfish;
        private EnumDirection b;
        private boolean c;

        @SuppressWarnings("WeakerAccess")
        public PathfinderGoalSilverfishHideInBlock(EntitySilverfish entitysilverfish) {
            super(entitysilverfish, 1.0D, 10);
            this.silverfish = entitysilverfish;
            this.a(1);
        }

        public boolean a() {
            if (!this.silverfish.world.getGameRules().getBoolean("mobGriefing")) {
                return false;
            } else if (this.silverfish.getGoalTarget() != null) {
                return false;
            } else if (!this.silverfish.getNavigation().n()) {
                return false;
            } else {
                Random random = this.silverfish.getRandom();
                if (random.nextInt(10) == 0) {
                    this.b = EnumDirection.a(random);
                    BlockPosition blockposition = (new BlockPosition(this.silverfish.locX, this.silverfish.locY + 0.5D, this.silverfish.locZ)).shift(this.b);
                    IBlockData iblockdata = this.silverfish.world.getType(blockposition);
                    if (BlockMonsterEggs.i(iblockdata)) {
                        this.c = true;
                        return true;
                    }
                }

                this.c = false;
                return super.a();
            }
        }

        public boolean b() {
            return !this.c && super.b();
        }

        public void c() {
            if (!this.c) {
                super.c();
            } else {
                World world = this.silverfish.world;
                BlockPosition blockposition = (new BlockPosition(this.silverfish.locX, this.silverfish.locY + 0.5D, this.silverfish.locZ)).shift(this.b);
                IBlockData iblockdata = world.getType(blockposition);
                if (BlockMonsterEggs.i(iblockdata)) {
                    if (CraftEventFactory.callEntityChangeBlockEvent(this.silverfish, blockposition.getX(), blockposition.getY(), blockposition.getZ(), Blocks.MONSTER_EGG, Block.getId(BlockMonsterEggs.getById(iblockdata.getBlock().toLegacyData(iblockdata)))).isCancelled()) {
                        return;
                    }

                    world.setTypeAndData(blockposition, Blocks.MONSTER_EGG.getBlockData().set(BlockMonsterEggs.VARIANT, BlockMonsterEggs.EnumMonsterEggVarient.a(iblockdata)), 3);
                    this.silverfish.doSpawnEffect();
                    this.silverfish.die();
                }
            }

        }
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_HEALTH));
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_SPEED));
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(shop.getYml().getDouble(ConfigPath.SHOP_SPECIAL_SILVERFISH_DAMAGE));
    }

    public static LivingEntity spawn(Location loc, BedWarsTeam team) {
        WorldServer mcWorld = ((CraftWorld)loc.getWorld()).getHandle();
        Silverfish customEnt = new Silverfish(mcWorld, team);
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity)customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        customEnt.setCustomNameVisible(true);
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (LivingEntity) customEnt.getBukkitEntity();
    }
}
