package com.andrei1058.mc.bedwars.despawnable;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.server.VersionSupport;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface IDespawnableProvider<T> {

    DespawnableType getType();

    String getDisplayName(DespawnableAttributes attr, ITeam team);

    T spawn(@NotNull DespawnableAttributes attr, @NotNull Location location, @NotNull ITeam team, VersionSupport api);

    PathfinderGoalSelector getTargetSelector(@NotNull EntityCreature entityLiving);

    PathfinderGoalSelector getGoalSelector(@NotNull EntityCreature entityLiving);
}
