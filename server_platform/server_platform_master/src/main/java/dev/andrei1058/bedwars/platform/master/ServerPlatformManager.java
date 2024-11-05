package dev.andrei1058.bedwars.platform.master;

import org.bukkit.Bukkit;

// Loads the server platform logic based on server jar at runtime
public class ServerPlatformManager {

    private static String version;

    public static String getServerVersion() {
        if (null == version) {
            version = Bukkit.getServer().getClass().getName().split("\\.")[3];
            if (version.equals("CraftServer")) {
                // it is probably PAPER
                String bukkitVersion = Bukkit.getServer().getBukkitVersion();
                if (bukkitVersion.startsWith("1.21")) {
                    // todo test me
                }
            }
        }
        return version;
    }
}
