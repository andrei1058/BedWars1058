package dev.andrei1058.bedwars.platform.paper;

import com.andrei1058.bedwars.api.server.VersionSupport;
import dev.andrei1058.bedwars.platform.common.ServerPlatform;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PaperPlatform implements ServerPlatform {
    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public VersionSupport getOldWrapper(JavaPlugin plugin) {
        return null;
    }

    @Override
    public String getName() {
        return "Paper "+getVersion();
    }

    public abstract String getVersion();
}
