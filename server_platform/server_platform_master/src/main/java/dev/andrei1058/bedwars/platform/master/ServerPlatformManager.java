package dev.andrei1058.bedwars.platform.master;

import com.andrei1058.bedwars.api.server.VersionSupport;
import dev.andrei1058.bedwars.platform.common.LoaderPriority;
import dev.andrei1058.bedwars.platform.common.ServerPlatform;
import dev.andrei1058.bedwars.platform.master.platforms.IPlatformLoader;
import dev.andrei1058.bedwars.platform.master.platforms.PaperLoader;
import dev.andrei1058.bedwars.platform.master.platforms.SpigotLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

// Loads the server platform logic based on server jar at runtime
public class ServerPlatformManager {

    /**
     * Default supported platforms.
     */
    private static @NotNull List<IPlatformLoader> getSupportedPlatforms() {
        return Arrays.asList(
                new PaperLoader(),
                new SpigotLoader()
        );
    }

    /**
     * Loads platform support on plugin load.
     *
     * @return empty in case of not supported at all.
     */
    public static Optional<ServerPlatform> loadPlatformSupport() {

        List<IPlatformLoader> platforms = getSupportedPlatforms();
        platforms.sort((a, b) -> {
            if (a instanceof LoaderPriority && b instanceof LoaderPriority) {
                return Math.max(((LoaderPriority) a).order(), ((LoaderPriority) b).order());
            }
            return -1;
        });

        List<IPlatformLoader> supportedPlatforms = new ArrayList<>();
        for (IPlatformLoader platform : platforms) {
            if (platform.isPlatformSupported() && platform.isServerVersionSupported()) {
                supportedPlatforms.add(platform);
            }
        }

        if (supportedPlatforms.size() == 1) {
            return Optional.of(supportedPlatforms.get(0).getSupport());
        }

        // legacy support
        var packageVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];

        try {
            var supp = Class.forName("com.andrei1058.bedwars.support.version." + packageVersion + "." + packageVersion);

            var constructor = supp.getConstructor(Class.forName("org.bukkit.plugin.Plugin"), String.class);
            var nms = constructor.newInstance(Bukkit.getPluginManager().getPlugin("BedWars1058"), packageVersion);
            return Optional.of(new ServerPlatform() {
                @Override
                public void onLoad(JavaPlugin plugin) {

                }

                @Override
                public void onEnable(JavaPlugin plugin) {

                }

                @Override
                public void onDisable(JavaPlugin plugin) {

                }

                @Override
                public VersionSupport getOldWrapper(JavaPlugin plugin) {
                    return (VersionSupport) nms;
                }

                @Override
                public String getName() {
                    return "Spigot/ Paper " + packageVersion;
                }
            });
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | ClassNotFoundException |
                 RuntimeException | NoSuchMethodException ignored) {
        }

        return Optional.empty();
    }
}
