package dev.andrei1058.bedwars.platform.master.platforms;


import dev.andrei1058.bedwars.platform.common.ServerPlatform;

public class SpigotLoader implements IPlatformLoader {
    @Override
    public boolean isPlatformSupported() {

        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (ClassNotFoundException e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isServerVersionSupported() {
        return false;
    }

    @Override
    public ServerPlatform getSupport() {
        return null;
    }
}
