package dev.andrei1058.bedwars.platform.master.platforms;

import dev.andrei1058.bedwars.platform.common.LoaderPriority;
import dev.andrei1058.bedwars.platform.common.ServerPlatform;

import java.lang.reflect.InvocationTargetException;

@LoaderPriority(order = 100)
public class PaperLoader implements IPlatformLoader {

    @Override
    public boolean isPlatformSupported() {

        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
        } catch (ClassNotFoundException e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isServerVersionSupported() {
        return true;
    }

    @Override
    public ServerPlatform getSupport() {
        try {
            return (ServerPlatform)Class.forName("dev.andrei1058.bedwars.platform.paper.v1_21_R1").getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException ignored) {
        }

        return null;
    }
}
