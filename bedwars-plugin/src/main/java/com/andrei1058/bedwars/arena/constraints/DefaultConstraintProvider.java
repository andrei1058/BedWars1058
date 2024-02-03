package com.andrei1058.bedwars.arena.constraints;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.constraints.ArenaConstraint;
import com.andrei1058.bedwars.api.arena.constraints.ConstraintProvider;
import com.andrei1058.bedwars.api.arena.constraints.defaults.*;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DefaultConstraintProvider implements ConstraintProvider {
    @Override
    public Plugin getOwner() {
        return BedWars.plugin;
    }

    @Override
    public Optional<Collection<ArenaConstraint>> provide(String template, ConfigManager config) {
        return Optional.of(getDefaultConstraints());
    }

    public Collection<ArenaConstraint> getDefaultConstraints() {
        return List.of(
                new AlreadyInEnableQueueConstraint(),
                new AlreadyEnabledConstraint(),
                new TeamConstraint(),
                new WorldContainerConstraint(),
                new GeneratorsConstraints(),
                new WaitingSpawnConstraint()
        );
    }
}
