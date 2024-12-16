package dev.andrei1058.bedwars.platform.master;

import dev.andrei1058.bedwars.platform.common.LoaderPriority;
import dev.andrei1058.bedwars.platform.common.ServerPlatform;
import dev.andrei1058.bedwars.platform.master.platforms.IPlatformLoader;
import dev.andrei1058.bedwars.platform.master.platforms.PaperLoader;
import dev.andrei1058.bedwars.platform.master.platforms.SpigotLoader;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.*;

// Loads the server platform logic based on server jar at runtime
public class ServerPlatformManager {

    /**
     * Default supported platforms.
     * */
    private static @NotNull List<IPlatformLoader> getSupportedPlatforms() {
        return Arrays.asList(
                new PaperLoader(),
                new SpigotLoader()
        );
    }

    /**
     * Loads platform support on plugin load.
     * @return empty in case of not supported at all.
     */
    public static Optional<ServerPlatform> loadPlatformSupport() {

        List<IPlatformLoader> platforms = getSupportedPlatforms();
        platforms.sort((a,b) -> {
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

        return Optional.empty();
    }

    @Deprecated
    private static String version;

    @Deprecated
    public static String getServerVersion() {
        if (null == version) {
            version = Bukkit.getServer().getClass().getName().split("\\.")[3];
            if (version.equals("CraftServer")) {
                // it is probably PAPER
                String bukkitVersion = Bukkit.getServer().getBukkitVersion();
                Bukkit.getLogger().warning("ver: "+bukkitVersion);
                Bukkit.getLogger().warning("ver: "+Bukkit.getServer().getVersion());
                if (bukkitVersion.startsWith("1.21")) {
                    // todo test me
                }
            }
        }
        return version;
    }
}
