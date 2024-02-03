package com.andrei1058.bedwars.api.arena.constraints.defaults;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.constraints.ArenaConstraint;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TeamConstraint extends ArenaConstraint {

    @Override
    public Optional<List<String>> validate(BedWars api, @NotNull ConfigManager template) {
        if (null == template.getYml().get("Team")) {
            addMessage("No teams were found in config.");
        } else {
            if (template.getYml().getConfigurationSection("Team").getKeys(false).size() < 2) {
                addMessage("You must set at least 2 teams.");
            }
            for (String team : template.getYml().getConfigurationSection("Team").getKeys(false)) {
                String colorS = template.getYml().getString("Team." + team + ".Color");
                if (colorS == null) continue;
                colorS = colorS.toUpperCase();
                try {
                    TeamColor.valueOf(colorS);
                } catch (Exception e) {
                    addMessage("Invalid color for team: "+team);
                }
                for (String stuff : Arrays.asList("Color", "Spawn", "Bed", "Shop", "Upgrade", "Iron", "Gold")) {
                    if (template.getYml().get("Team." + team + "." + stuff) == null) {
                        addMessage(stuff+" not set for team: "+team);
                    }
                }
            }
        }

        return getMessages();
    }
}
