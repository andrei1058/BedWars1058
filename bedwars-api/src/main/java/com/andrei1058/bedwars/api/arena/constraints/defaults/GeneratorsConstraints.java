package com.andrei1058.bedwars.api.arena.constraints.defaults;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.constraints.ArenaConstraint;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class GeneratorsConstraints extends ArenaConstraint {
    @Override
    public Optional<List<String>> validate(BedWars api, @NotNull ConfigManager template) {

        if (template.getYml().get("generator.Diamond") == null) {
            addMessage("No Diamonds generators were set.");
        }

        if (template.getYml().get("generator.Emerald") == null) {
            addMessage("No Emerald generators were set.");
        }

        return getMessages();
    }
}
