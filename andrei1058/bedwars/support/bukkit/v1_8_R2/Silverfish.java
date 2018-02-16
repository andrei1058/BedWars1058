package com.andrei1058.bedwars.support.bukkit.v1_8_R2;

import net.minecraft.server.v1_8_R2.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

public class Silverfish extends EntitySilverfish {
    private final PathfinderGoalSilverfishWakeOthers a;

    public Silverfish(World world, List<Player> exclude) {
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
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(3, this.a = new Silverfish.PathfinderGoalSilverfishWakeOthers(this));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
        this.goalSelector.a(5, new Silverfish.PathfinderGoalSilverfishHideInBlock(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
        this.targetSelector.a(2, new AttackEnemies<>(this, EntityHuman.class, true, exclude));
    }

    @Override
    protected void a(BlockPosition blockposition, Block block) {
        this.makeSound("mob.silverfish.step", 0.15F, 1.0F);
    }

    static class PathfinderGoalSilverfishWakeOthers extends PathfinderGoal {
        private EntitySilverfish silverfish;
        private int b;

        public PathfinderGoalSilverfishWakeOthers(EntitySilverfish entitysilverfish) {
            this.silverfish = entitysilverfish;
        }

        public void f() {
            if (this.b == 0) {
                this.b = 20;
            }

        }

        public boolean a() {
            return this.b > 0;
        }

        public void e() {
            --this.b;
            if (this.b <= 0) {
                World world = this.silverfish.world;
                Random random = this.silverfish.bc();
                BlockPosition blockposition = new BlockPosition(this.silverfish);

                for(int i = 0; i <= 5 && i >= -5; i = i <= 0 ? 1 - i : 0 - i) {
                    for(int j = 0; j <= 10 && j >= -10; j = j <= 0 ? 1 - j : 0 - j) {
                        for(int k = 0; k <= 10 && k >= -10; k = k <= 0 ? 1 - k : 0 - k) {
                            BlockPosition blockposition1 = blockposition.a(j, i, k);
                            IBlockData iblockdata = world.getType(blockposition1);
                            if (iblockdata.getBlock() == Blocks.MONSTER_EGG && !CraftEventFactory.callEntityChangeBlockEvent(this.silverfish, blockposition1.getX(), blockposition1.getY(), blockposition1.getZ(), Blocks.AIR, 0).isCancelled()) {
                                if (world.getGameRules().getBoolean("mobGriefing")) {
                                    world.setAir(blockposition1, true);
                                } else {
                                    world.setTypeAndData(blockposition1, (iblockdata.get(BlockMonsterEggs.VARIANT)).d(), 3);
                                }

                                if (random.nextBoolean()) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    static class PathfinderGoalSilverfishHideInBlock extends PathfinderGoalRandomStroll {
        private final EntitySilverfish silverfish;
        private EnumDirection b;
        private boolean c;

        public PathfinderGoalSilverfishHideInBlock(EntitySilverfish entitysilverfish) {
            super(entitysilverfish, 1.0D, 10);
            this.silverfish = entitysilverfish;
            this.a(1);
        }

        public boolean a() {
            if (this.silverfish.getGoalTarget() != null) {
                return false;
            } else if (!this.silverfish.getNavigation().m()) {
                return false;
            } else {
                Random random = this.silverfish.bc();
                if (random.nextInt(10) == 0) {
                    this.b = EnumDirection.a(random);
                    BlockPosition blockposition = (new BlockPosition(this.silverfish.locX, this.silverfish.locY + 0.5D, this.silverfish.locZ)).shift(this.b);
                    IBlockData iblockdata = this.silverfish.world.getType(blockposition);
                    if (BlockMonsterEggs.d(iblockdata)) {
                        this.c = true;
                        return true;
                    }
                }

                this.c = false;
                return super.a();
            }
        }

        public boolean b() {
            return this.c ? false : super.b();
        }

        public void c() {
            if (!this.c) {
                super.c();
            } else {
                World world = this.silverfish.world;
                BlockPosition blockposition = (new BlockPosition(this.silverfish.locX, this.silverfish.locY + 0.5D, this.silverfish.locZ)).shift(this.b);
                IBlockData iblockdata = world.getType(blockposition);
                if (BlockMonsterEggs.d(iblockdata)) {
                    if (CraftEventFactory.callEntityChangeBlockEvent(this.silverfish, blockposition.getX(), blockposition.getY(), blockposition.getZ(), Blocks.MONSTER_EGG, Block.getId(BlockMonsterEggs.getById(iblockdata.getBlock().toLegacyData(iblockdata)))).isCancelled()) {
                        return;
                    }

                    world.setTypeAndData(blockposition, Blocks.MONSTER_EGG.getBlockData().set(BlockMonsterEggs.VARIANT, BlockMonsterEggs.EnumMonsterEggVarient.a(iblockdata)), 3);
                    this.silverfish.y();
                    this.silverfish.die();
                }
            }

        }
    }

    public static CraftEntity spawnSilverfish(Location loc, List<Player> exclude, String name) {
        WorldServer mcWorld = ((CraftWorld)loc.getWorld()).getHandle();
        Silverfish customEnt = new Silverfish(mcWorld, exclude);
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity)customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        customEnt.setCustomNameVisible(true);
        customEnt.setCustomName(name);
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return customEnt.getBukkitEntity();
    }
}
