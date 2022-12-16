package com.andrei1058.bedwars.support.version.v1_19_R2;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.server.VersionSupport;
import com.google.common.collect.Sets;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.util.UnsafeList;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public abstract class DespawnableProvider<T> {

    abstract DespawnableType getType();

    abstract T spawn(@NotNull DespawnableAttributes attr, @NotNull Location location, @NotNull ITeam team, VersionSupport api);

    protected boolean notSameTeam(@NotNull Entity entity, ITeam team, @NotNull VersionSupport api) {
        var despawnable = api.getDespawnablesList().getOrDefault(entity.getBukkitEntity().getUniqueId(), null);
        return null == despawnable || despawnable.getTeam() != team;
    }

    protected PathfinderGoalSelector getTargetSelector(@NotNull EntityCreature entityLiving) {
        return entityLiving.bT;
    }

    protected PathfinderGoalSelector getGoalSelector(@NotNull EntityCreature entityLiving) {
        return entityLiving.bS;
    }

    protected void clearSelectors(@NotNull EntityCreature entityLiving) {
        try {
            Field dField = PathfinderGoalSelector.class.getDeclaredField("d");
            dField.setAccessible(true);
//            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
//            cField.setAccessible(true);
            dField.set(getGoalSelector(entityLiving), Sets.newLinkedHashSet());
            dField.set(getGoalSelector(entityLiving),Sets.newLinkedHashSet());
//            cField.set(this.goalSelector, new UnsafeList());
//            cField.set(this.targetSelector, new UnsafeList());
        } catch (IllegalAccessException | NoSuchFieldException e1) {
            e1.printStackTrace();
        }
    }
}
