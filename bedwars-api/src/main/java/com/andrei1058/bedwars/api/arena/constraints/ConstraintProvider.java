package com.andrei1058.bedwars.api.arena.constraints;

import com.andrei1058.bedwars.api.configuration.ConfigManager;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Optional;

public interface ConstraintProvider {

    /**
     * @return provider owner.
     */
    Plugin getOwner();

    /**
     * @param template game template.
     * @param config configuration file.
     * @return if you are tracking the given template, return a collection with custom validators or empty optional.
     */
    Optional<Collection<ArenaConstraint>> provide(String template, ConfigManager config);
}
