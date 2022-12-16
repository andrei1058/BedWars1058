package com.andrei1058.bedwars.support.version.v1_19_R2;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.VersionSupport;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
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
import org.bukkit.entity.IronGolem;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TeamIronGolem extends DespawnableProvider<IronGolem> {

    @Override
    public DespawnableType getType() {
        return DespawnableType.IRON_GOLEM;
    }

    public @NotNull IronGolem spawn(@NotNull DespawnableAttributes attr, @NotNull Location location, @NotNull ITeam team, VersionSupport api) {
        Language lang = Language.getDefaultLanguage();
        String name = lang.m(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME).replace("{despawn}", String.valueOf(attr.despawnSeconds())
                .replace("{health}", StringUtils.repeat(lang.m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH) + " ", 10))
                .replace("{TeamColor}", team.getColor().chat().toString())
        );

        var bukkitEntity = (IronGolem) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.IRON_GOLEM);
        bukkitEntity.setRemoveWhenFarAway(false);
        bukkitEntity.setPersistent(true);
        bukkitEntity.setCustomNameVisible(true);
        bukkitEntity.setCustomName(name);

        var entity = (EntityIronGolem) ((CraftEntity) bukkitEntity).getHandle();
        Objects.requireNonNull(entity.a(GenericAttributes.a)).a(attr.health());
        Objects.requireNonNull(entity.a(GenericAttributes.d)).a(attr.speed());

        clearSelectors(entity);
        var goalSelector = getGoalSelector(entity);
        var targetSelector = getTargetSelector(entity);
        // clear existing
//        goalSelector.a();
        goalSelector.a(1, new PathfinderGoalFloat(entity));
        goalSelector.a(2, new PathfinderGoalMeleeAttack(entity, 1.5D, false));
        goalSelector.a(3, new PathfinderGoalRandomStroll(entity, 1D));
        goalSelector.a(4, new PathfinderGoalRandomLookaround(entity));
//        targetSelector.a();
        targetSelector.a(1, new PathfinderGoalHurtByTarget(entity));
        targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(
                entity, EntityHuman.class, 20, true, false,
                player -> !((EntityHuman) player).getBukkitEntity().isDead() &&
                        !team.wasMember(((EntityHuman) player).getBukkitEntity().getUniqueId()) &&
                        !team.getArena().isReSpawning(((EntityHuman) player).getBukkitEntity().getUniqueId())
                        && !team.getArena().isSpectator(((EntityHuman) player).getBukkitEntity().getUniqueId()))
        );
        //todo not working
        targetSelector.a(3, new PathfinderGoalNearestAttackableTarget<>(
                entity, EntityIronGolem.class, 20, true, false,
                entityLiving -> notSameTeam(entityLiving, team, api))
        );
        targetSelector.a(4, new PathfinderGoalNearestAttackableTarget<>(
                entity, EntitySilverfish.class, 20, true, false,
                entityLiving -> notSameTeam(entityLiving, team, api))
        );

        return bukkitEntity;
    }
}
