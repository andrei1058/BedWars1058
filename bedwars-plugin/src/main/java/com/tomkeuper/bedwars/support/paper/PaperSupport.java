package com.tomkeuper.bedwars.support.paper;

import com.tomkeuper.bedwars.api.configuration.ConfigPath;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;

import static com.tomkeuper.bedwars.BedWars.config;
import static com.tomkeuper.bedwars.BedWars.isPaper;

public final class PaperSupport {

    public static void teleport(Entity entity, Location location){
        if (isPaper && config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_PERFORMANCE_PAPER_FEATURES)){
            PaperLib.teleportAsync(entity, location);
        }
        else
        {
        entity.teleport(location);
        }
    }

    public static void teleportC(Entity entity, Location location, PlayerTeleportEvent.TeleportCause cause){
        if (isPaper){
            PaperLib.teleportAsync(entity, location, cause);
        }
        else
        {
            entity.teleport(location, cause);
        }
    }

}
