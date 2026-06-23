package com.andrei1058.bedwars.platform.spigot;

import com.andrei1058.bedwars.api.server.VersionSupport;
import com.andrei1058.bedwars.platform.common.ServerPlatform;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public abstract class SpigotPlatform implements ServerPlatform {
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
    public abstract VersionSupport getOldWrapper(JavaPlugin plugin);

    @Override
    public String getName() {
        return "Spigot "+getVersion();
    }

    public abstract String getVersion();
}
