package dev.andrei1058.bedwars.platform.paper;

import com.andrei1058.bedwars.api.server.VersionSupport;
import dev.andrei1058.bedwars.platform.common.ServerPlatform;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public abstract class PaperPlatform implements ServerPlatform {
    @Override
    public void onLoad(JavaPlugin plugin) {

    }

    @Override
    public void onEnable(JavaPlugin plugin) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (Arrays.stream(this.getClass().getAnnotations()).anyMatch(
                    annotation -> annotation instanceof Deprecated)
            ) {
                plugin.getLogger().warning("Support for " + this.getName() + " is scheduled for removal. " +
                        "Please consider upgrading your server software to a newer Minecraft version.");
            }
        });
    }

    @Override
    public void onDisable(JavaPlugin plugin) {

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
