package com.andrei1058.bedwars.platform.spigot;

import com.andrei1058.bedwars.api.server.VersionSupport;
import org.bukkit.plugin.java.JavaPlugin;

public class v1_21_R2 extends SpigotPlatform {
    @Override
    public VersionSupport getOldWrapper(JavaPlugin plugin) {
        return new com.andrei1058.bedwars.platform.paper.v1_21_R2_NMS(plugin, getVersion());
    }

    @Override
    public String getVersion() {
        return "1.21.3";
    }
}
