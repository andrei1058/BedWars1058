package com.andrei1058.bedwars.api.server;

import com.andrei1058.bedwars.api.configuration.ConfigManager;
import org.bukkit.entity.Player;

public interface ISetupSession {

    /**
     * Get used world name.
     */
    String getWorldName();

    /**
     * Get player doing the setup.
     */
    Player getPlayer();

    /**
     * Get setup type.
     */
    SetupType getSetupType();

    /**
     * Get arena config.
     */
    ConfigManager getConfig();
}
