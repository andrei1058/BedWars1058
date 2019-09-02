package com.andrei1058.bedwars.support.version.v1_12_R1;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.VersionSupport;
import com.google.common.collect.Sets;
import net.minecraft.server.v1_12_R1.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_12_R1.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;
import java.util.Random;

public class Silverfish extends EntitySilverfish {

    @SuppressWarnings("WeakerAccess")
    public Silverfish(World world, ITeam team, VersionSupport vs) {
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
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this,1.0D, false));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(2, new AttackEnemies<>(this, EntityHuman.class, true, team, vs));
        this.goalSelector.a(5, new PathfinderGoalSilverfishHideInBlock(this));
    }

    @SuppressWarnings("WeakerAccess")
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
            } else if (!this.a.getNavigation().o()) {
                return false;
            } else {
                Random random = this.a.getRandom();
                if (this.a.world.getGameRules().getBoolean("mobGriefing") && random.nextInt(10) == 0) {
                    this.h = EnumDirection.a(random);
                    BlockPosition blockposition = (new BlockPosition(this.a.locX, this.a.locY + 0.5D, this.a.locZ)).shift(this.h);
                    IBlockData iblockdata = this.a.world.getType(blockposition);
                    if (BlockMonsterEggs.x(iblockdata)) {
                        this.i = true;
                        return true;
                    }
                }

                this.i = false;
                return super.a();
            }
        }

        public boolean b() {
            return !this.i && super.b();
        }

        public void c() {
            if (!this.i) {
                super.c();
            } else {
                World world = this.a.world;
                BlockPosition blockposition = (new BlockPosition(this.a.locX, this.a.locY + 0.5D, this.a.locZ)).shift(this.h);
                IBlockData iblockdata = world.getType(blockposition);
                if (BlockMonsterEggs.x(iblockdata)) {
                    if (CraftEventFactory.callEntityChangeBlockEvent(this.a, blockposition, Blocks.MONSTER_EGG, Block.getId(BlockMonsterEggs.getById(iblockdata.getBlock().toLegacyData(iblockdata)))).isCancelled()) {
                        return;
                    }

                    world.setTypeAndData(blockposition, Blocks.MONSTER_EGG.getBlockData().set(BlockMonsterEggs.VARIANT, BlockMonsterEggs.EnumMonsterEggVarient.a(iblockdata)), 3);
                    this.a.doSpawnEffect();
                    this.a.die();
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
                .replace("{despawn}", String.valueOf(despawn)
                        .replace("{health}", StringUtils.repeat(Language.getDefaultLanguage().m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH)+" ", 10))
                        .replace("{TeamColor}", TeamColor.getChatColor(team.getColor()).toString())));
        customEnt.setCustomNameVisible(true);
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (LivingEntity) customEnt.getBukkitEntity();
    }
}
