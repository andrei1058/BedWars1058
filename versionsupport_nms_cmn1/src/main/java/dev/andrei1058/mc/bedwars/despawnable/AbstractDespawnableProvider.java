package dev.andrei1058.mc.bedwars.despawnable;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.server.VersionSupport;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class AbstractDespawnableProvider<T> implements IDespawnableProvider<T> {

    protected boolean notSameTeam(@NotNull Entity entity, ITeam team, @NotNull VersionSupport api) {
        var despawnable = api.getDespawnablesList().getOrDefault(entity.getBukkitEntity().getUniqueId(), null);
        return null == despawnable || despawnable.getTeam() != team;
    }

    public PathfinderGoalSelector getTargetSelector(@NotNull EntityCreature entityLiving) {
        return entityLiving.bW;
    }

    public PathfinderGoalSelector getGoalSelector(@NotNull EntityCreature entityLiving) {
        return entityLiving.bX;
    }

    protected void clearSelectors(@NotNull EntityCreature entityLiving) {
        getTargetSelector(entityLiving).b().clear();
        getGoalSelector(entityLiving).b().clear();
    }

    protected PathfinderGoal getTargetGoal(EntityInsentient entity, ITeam team, VersionSupport api) {
        return new PathfinderGoalNearestAttackableTarget<>(entity, EntityLiving.class, 20, true, false,
                entityLiving -> {
                    if (entityLiving instanceof EntityHuman) {
                        return !((EntityHuman) entityLiving).getBukkitEntity().isDead() &&
                                !team.wasMember(((EntityHuman) entityLiving).getBukkitEntity().getUniqueId()) &&
                                !team.getArena().isReSpawning(((EntityHuman) entityLiving).getBukkitEntity().getUniqueId())
                                && !team.getArena().isSpectator(((EntityHuman) entityLiving).getBukkitEntity().getUniqueId());
                    }
                    return notSameTeam(entityLiving, team, api);
                });
    }

    protected void applyDefaultSettings(org.bukkit.entity.@NotNull LivingEntity bukkitEntity, DespawnableAttributes attr,
                                        ITeam team) {
        bukkitEntity.setRemoveWhenFarAway(false);
        bukkitEntity.setPersistent(true);
        bukkitEntity.setCustomNameVisible(true);
        bukkitEntity.setCustomName(getDisplayName(attr, team));

        // todo
        var entity = ((EntityInsentient)((CraftEntity)bukkitEntity).getHandle());
        Objects.requireNonNull(entity.craftAttributes.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(attr.health());
        Objects.requireNonNull(entity.craftAttributes.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(attr.speed());
        Objects.requireNonNull(entity.craftAttributes.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(attr.damage());
    }
}
