package com.andrei1058.bedwars.api.arena.constraints.defaults;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.constraints.ArenaConstraint;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class WorldContainerConstraint extends ArenaConstraint {
    @Override
    public Optional<List<String>> validate(@NotNull BedWars api, @NotNull ConfigManager template) {
        if (!api.getRestoreAdapter().isWorld(template.getName())) {
            addMessage("Could not find the map in " + api.getRestoreAdapter().getDisplayName());
        }
        return getMessages();
    }
}
