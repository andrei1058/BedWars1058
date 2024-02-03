package com.andrei1058.bedwars.api.arena.constraints.defaults;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.constraints.ArenaConstraint;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class WaitingSpawnConstraint extends ArenaConstraint {
    @Override
    public Optional<List<String>> validate(BedWars api, @NotNull ConfigManager template) {

        if (template.getYml().get("waiting.Loc") == null) {
            addMessage("Waiting spawn position was not set.");
        }

        return getMessages();
    }
}
