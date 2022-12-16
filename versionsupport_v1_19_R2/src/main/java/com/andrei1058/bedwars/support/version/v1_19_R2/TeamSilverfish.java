package com.andrei1058.bedwars.support.version.v1_19_R2;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.VersionSupport;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.monster.EntitySilverfish;
import net.minecraft.world.entity.player.EntityHuman;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Silverfish;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TeamSilverfish extends DespawnableProvider<Silverfish> {
    @Override
    public DespawnableType getType() {
        return DespawnableType.SILVERFISH;
    }

    @Override
    public Silverfish spawn(@NotNull DespawnableAttributes attr, @NotNull Location location, @NotNull ITeam team, VersionSupport api) {
        Language lang = Language.getDefaultLanguage();
        String name = lang.m(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME).replace("{despawn}", String.valueOf(attr.despawnSeconds())
                .replace("{health}", StringUtils.repeat(lang.m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH) + " ", 10))
                .replace("{TeamColor}", team.getColor().chat().toString())
        );

        var bukkitEntity = (Silverfish) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.SILVERFISH);
        bukkitEntity.setRemoveWhenFarAway(false);
        bukkitEntity.setPersistent(true);
        bukkitEntity.setCustomNameVisible(true);
        bukkitEntity.setCustomName(name);

        var entity = (EntitySilverfish) ((CraftEntity) bukkitEntity).getHandle();
        Objects.requireNonNull(entity.a(GenericAttributes.a)).a(attr.health());
        Objects.requireNonNull(entity.a(GenericAttributes.d)).a(attr.speed());
        Objects.requireNonNull(entity.a(GenericAttributes.f)).a(attr.damage());

        var goalSelector = getGoalSelector(entity);
        var targetSelector = getTargetSelector(entity);
        // clear existing
//        goalSelector.a();
//        targetSelector.a();
        clearSelectors(entity);
        goalSelector.a(1, new PathfinderGoalFloat(entity));
        goalSelector.a(2, new PathfinderGoalMeleeAttack(entity, 1.9D, false));
        targetSelector.a(3, new PathfinderGoalHurtByTarget(entity));
        goalSelector.a(4, new PathfinderGoalRandomStroll(entity, 2D));
        targetSelector.a(6, new PathfinderGoalNearestAttackableTarget<>(
                entity, EntityHuman.class, 20, true, false,
                player -> !((EntityHuman) player).getBukkitEntity().isDead() &&
                        !team.wasMember(((EntityHuman) player).getBukkitEntity().getUniqueId()) &&
                        !team.getArena().isReSpawning(((EntityHuman) player).getBukkitEntity().getUniqueId())
                        && !team.getArena().isSpectator(((EntityHuman) player).getBukkitEntity().getUniqueId()))
        );
        targetSelector.a(7, new PathfinderGoalNearestAttackableTarget<>(
                entity, EntityIronGolem.class, 20, true, false,
                entityLiving -> notSameTeam(entityLiving, team, api))
        );
        targetSelector.a(8, new PathfinderGoalNearestAttackableTarget<>(
                entity, EntitySilverfish.class, 20, true, false,
                entityLiving -> notSameTeam(entityLiving, team, api))
        );

        return bukkitEntity;
    }
}
