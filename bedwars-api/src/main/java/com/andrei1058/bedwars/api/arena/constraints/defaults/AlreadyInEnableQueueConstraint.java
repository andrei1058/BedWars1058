package com.andrei1058.bedwars.api.arena.constraints.defaults;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.constraints.ArenaConstraint;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class AlreadyInEnableQueueConstraint extends ArenaConstraint {

    @Override
    public Optional<List<String>> validate(@NotNull BedWars api, ConfigManager template) {
        if (!api.isAutoScale()) {
            for (IArena inQueue : api.getArenaUtil().getEnableQueue()) {
                if (inQueue.getArenaName().equalsIgnoreCase(template.getName())) {
                    return addMessage("Arena is already in the enabling queue.");
                }
            }
        }
        return getMessages();
    }
}
