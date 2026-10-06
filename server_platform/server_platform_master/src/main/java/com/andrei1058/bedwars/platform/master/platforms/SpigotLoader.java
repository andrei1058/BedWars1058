package com.andrei1058.bedwars.platform.master.platforms;

import com.andrei1058.bedwars.platform.common.ServerPlatform;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class SpigotLoader implements IPlatformLoader {

    private final HashMap<BuildInfo, Object> buildInfo = getBuildInfo();

    @Override
    public boolean isPlatformSupported() {
        var brandId = buildInfo.get(BuildInfo.BRAND_ID).toString();
        var brandName = buildInfo.get(BuildInfo.BRAND_NAME).toString();
        return brandId.equals("spigotmc:spigotmc") && brandName.equals("Spigot");
    }

    @Override
    public boolean isServerVersionSupported() {
        var mcVersionId = buildInfo.get(BuildInfo.MINECRAFT_VERSION_ID).toString();
        var packageName = "com.andrei1058.bedwars.platform.spigot."+mcVersionId;
        try {
            Class.forName(packageName);
            return true;
        } catch (ClassNotFoundException ignored) {
        }
        return false;
    }

    @Override
    public ServerPlatform getSupport() {
        var packageName = "com.andrei1058.bedwars.platform.spigot.";
        packageName+=buildInfo.get(BuildInfo.MINECRAFT_VERSION_ID).toString();

        try {
            return (ServerPlatform)Class.forName(packageName).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException ignored) {
        }
        return null;
    }

    private HashMap<BuildInfo, Object> getBuildInfo() {
        HashMap<BuildInfo, Object> buildInfo = new HashMap<>();
        for (BuildInfo build : BuildInfo.values()) {
            buildInfo.put(build, "");
        }

        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (ClassNotFoundException e) {
            return buildInfo;
        }

        var packageVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];
        if (!(packageVersion.startsWith("v") && packageVersion.contains("R"))) {
            return buildInfo;
        }

        buildInfo.put(BuildInfo.BRAND_ID, "spigotmc:spigotmc");
        buildInfo.put(BuildInfo.BRAND_NAME, "Spigot");
        buildInfo.put(BuildInfo.MINECRAFT_VERSION_ID, packageVersion);
        buildInfo.put(BuildInfo.MINECRAFT_VERSION_NAME, packageVersion);

        return buildInfo;
    }
}
