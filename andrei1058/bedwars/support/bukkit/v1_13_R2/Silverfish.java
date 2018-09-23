package com.andrei1058.bedwars.support.bukkit.v1_13_R2;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.google.common.collect.Sets;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_13_R2.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import static com.andrei1058.bedwars.Main.shop;

public class Silverfish extends EntitySilverfish {

    public Silverfish(World world, List<Player> exclude) {
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
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        }
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this,1.0D, false));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
        this.targetSelector.a(2, new AttackEnemies(this, EntityHuman.class, true, exclude));
        this.goalSelector.a(5, new PathfinderGoalSilverfishHideInBlock(this));
    }

    static class PathfinderGoalSilverfishHideInBlock extends PathfinderGoalRandomStroll {
        private EnumDirection h;
        private boolean i;

        public PathfinderGoalSilverfishHideInBlock(EntitySilverfish entitysilverfish) {
            super(entitysilverfish, 1.0D, 10);
            this.a(1);
        }

        public boolean a() {
            if (this.a.getGoalTarget() != null) {
                return false;
            } else if (!this.a.getNavigation().p()) {
                return false;
            } else {
                Random random = this.a.getRandom();
                if (this.a.world.getGameRules().getBoolean("mobGriefing") && random.nextInt(10) == 0) {
                    this.h = EnumDirection.a(random);
                    BlockPosition blockposition = (new BlockPosition(this.a.locX, this.a.locY + 0.5D, this.a.locZ)).shift(this.h);
                    IBlockData iblockdata = this.a.world.getType(blockposition);
                    if (BlockMonsterEggs.k(iblockdata)) {
                        this.i = true;
                        return true;
                    }
                }

                this.i = false;
                return super.a();
            }
        }

        public boolean b() {
            return this.i ? false : super.b();
        }

        public void c() {
            if (!this.i) {
                super.c();
            } else {
                World world = this.a.world;
                BlockPosition blockposition = (new BlockPosition(this.a.locX, this.a.locY + 0.5D, this.a.locZ)).shift(this.h);
                IBlockData iblockdata = world.getType(blockposition);
                if (BlockMonsterEggs.k(iblockdata)) {
                    if (CraftEventFactory.callEntityChangeBlockEvent(this.a, blockposition, BlockMonsterEggs.f(iblockdata.getBlock())).isCancelled()) {
                        return;
                    }

                    world.setTypeAndData(blockposition, BlockMonsterEggs.f(iblockdata.getBlock()), 3);
                    this.a.doSpawnEffect();
                    this.a.die();
                }
            }

        }
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(shop.getYml().getDouble("utilities.silverfish.health"));
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(shop.getYml().getDouble("utilities.silverfish.speed"));
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(shop.getYml().getDouble("utilities.silverfish.damage"));
    }

    public static Silverfish spawn(Location loc, BedWarsTeam team) {
        WorldServer mcWorld = ((CraftWorld)loc.getWorld()).getHandle();
        Silverfish customEnt = new Silverfish(mcWorld, team.getMembers());
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity)customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        customEnt.setCustomNameVisible(true);
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return customEnt;
    }
}
