package com.andrei1058.bedwars.support.version.v1_19_R3.despawnable;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.VersionSupport;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TeamIronGolem extends DespawnableProvider<IronGolem> {

    @Override
    public DespawnableType getType() {
        return DespawnableType.IRON_GOLEM;
    }

    @Override
    String getDisplayName(@NotNull DespawnableAttributes attr, @NotNull ITeam team) {
        Language lang = Language.getDefaultLanguage();
        return lang.m(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME).replace("{despawn}", String.valueOf(attr.despawnSeconds())
                .replace("{health}", StringUtils.repeat(lang.m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH) + " ", 10))
                .replace("{TeamColor}", team.getColor().chat().toString())
        );
    }

    public @NotNull IronGolem spawn(@NotNull DespawnableAttributes attr, @NotNull Location location, @NotNull ITeam team, VersionSupport api) {

        var bukkitEntity = (IronGolem) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.IRON_GOLEM);
        applyDefaultSettings(bukkitEntity, attr, team);

        var entity = (EntityIronGolem) ((CraftEntity) bukkitEntity).getHandle();

        clearSelectors(entity);
        var goalSelector = getGoalSelector(entity);
        var targetSelector = getTargetSelector(entity);

        goalSelector.a(1, new PathfinderGoalFloat(entity));
        goalSelector.a(2, new PathfinderGoalMeleeAttack(entity, 1.5D, false));
        goalSelector.a(3, new PathfinderGoalRandomStroll(entity, 1D));
        goalSelector.a(4, new PathfinderGoalRandomLookaround(entity));
        targetSelector.a(1, new PathfinderGoalHurtByTarget(entity));
        targetSelector.a(2, getTargetGoal(entity, team, api));

        return bukkitEntity;
    }
}
