package com.andrei1058.bedwars.support.version.v1_9_R2.entities;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.VersionSupport;
import net.minecraft.server.v1_9_R2.*;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_9_R2.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_9_R2.util.UnsafeList;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;
import java.util.Random;

public class Silverfish extends EntitySilverfish {

    public Silverfish(World world, ITeam team, VersionSupport vs) {
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
        this.targetSelector.a(2, new AttackEnemies<>(this, EntityHuman.class, true, team, vs));
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

    public static LivingEntity spawn(VersionSupport vs, Location loc, ITeam team, int speed, int health, int despawn, int damage) {
        WorldServer mcWorld = ((CraftWorld)loc.getWorld()).getHandle();
        Silverfish customEnt = new Silverfish(mcWorld, team, vs);
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        customEnt.getAttributeInstance(GenericAttributes.maxHealth).setValue(health);
        customEnt.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(speed);
        customEnt.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(damage);
        ((CraftLivingEntity)customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        customEnt.setCustomName(Language.getDefaultLanguage().m(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME)
                .replace("{despawn}", String.valueOf(speed)
                        .replace("{health}", StringUtils.repeat(Language.getDefaultLanguage().m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH)+" ", 10))
                        .replace("{TeamColor}", TeamColor.getChatColor(team.getColor()).toString())));
        customEnt.setCustomNameVisible(true);
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (LivingEntity) customEnt.getBukkitEntity();
    }
}
