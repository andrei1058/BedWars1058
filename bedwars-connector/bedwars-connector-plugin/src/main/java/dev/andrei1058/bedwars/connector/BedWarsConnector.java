package dev.andrei1058.bedwars.connector;

import com.andrei1058.bedwars.common.util.platform.ServerVersion;
import com.andrei1058.bedwars.common.util.platform.VersionChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class BedWarsConnector extends JavaPlugin {

    private static final Collection<ServerVersion> SUPPORTED_VERSIONS = List.of(
            ServerVersion.V1_8_8,
            ServerVersion.V1_12_2,
            ServerVersion.V1_13
    );

    public void onLoad() {
        // define logger
        var out = Bukkit.getLogger();

        if (versionNotSupportedCheck(out)) {
            return;
        }

        // todo init stuff
    }

    public void onEnable() {
        // define logger
        var out = Bukkit.getLogger();

        if (versionNotSupportedCheck(out)) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // todo init stuff
    }

    /**
     * @return true when the platform is not supported.
     */
    private static boolean versionNotSupportedCheck(Logger out) {
        return VersionChecker.performVersionCheck(SUPPORTED_VERSIONS,
                (pack) -> out.severe("Your server version is not supported: "+pack)
        );
    }
}
