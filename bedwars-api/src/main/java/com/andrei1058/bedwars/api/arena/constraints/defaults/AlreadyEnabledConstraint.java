package com.andrei1058.bedwars.api.arena.constraints.defaults;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.constraints.ArenaConstraint;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class AlreadyEnabledConstraint extends ArenaConstraint {

    @Override
    public Optional<List<String>> validate(@NotNull BedWars api, ConfigManager template) {
        if (!api.isAutoScale()) {
            if (null != api.getArenaUtil().getArenaByName(template.getName())) {
                return addMessage("Arena is already enabled.");
            }
        }
        return this.getMessages();
    }
}
