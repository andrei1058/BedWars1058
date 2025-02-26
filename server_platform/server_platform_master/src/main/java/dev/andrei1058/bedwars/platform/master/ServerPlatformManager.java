package dev.andrei1058.bedwars.platform.master;

import dev.andrei1058.bedwars.platform.common.LoaderPriority;
import dev.andrei1058.bedwars.platform.common.ServerPlatform;
import dev.andrei1058.bedwars.platform.master.platforms.IPlatformLoader;
import dev.andrei1058.bedwars.platform.master.platforms.PaperLoader;
import dev.andrei1058.bedwars.platform.master.platforms.SpigotLoader;
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
}
