package dev.andrei1058.bedwars.platform.common;

import com.andrei1058.bedwars.api.server.VersionSupport;
import org.bukkit.plugin.java.JavaPlugin;

public interface ServerPlatform {

    void onLoad();
    void onEnable();
    void onDisable();

    VersionSupport getOldWrapper(JavaPlugin plugin);
}
