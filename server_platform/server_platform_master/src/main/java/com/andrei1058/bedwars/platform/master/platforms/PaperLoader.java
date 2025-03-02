package com.andrei1058.bedwars.platform.master.platforms;

import com.andrei1058.bedwars.platform.common.LoaderPriority;
import com.andrei1058.bedwars.platform.common.ServerPlatform;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

@LoaderPriority(order = 100)
public class PaperLoader implements IPlatformLoader {

    private final HashMap<BuildInfo, Object> buildInfos = getBuildInfo();
    private final HashMap<String, String> versionMappings = getVersionMappings();

    private HashMap<String, String> getVersionMappings() {
        HashMap<String, String> versionMappings = new HashMap<>();

        versionMappings.put("1.21", "v1_21_R1");
        versionMappings.put("1.21.1", "v1_21_R1");
        versionMappings.put("1.21.3", "v1_21_R2");
        versionMappings.put("1.21.4", "v1_21_R3");

        return versionMappings;
    }

    @Override
    public boolean isPlatformSupported() {
        var brandId = buildInfos.get(BuildInfo.BRAND_ID).toString();
        var brandName = buildInfos.get(BuildInfo.BRAND_NAME).toString();
        return brandId.equals("papermc:paper") && brandName.equals("Paper");
    }

    @Override
    public boolean isServerVersionSupported() {
        var mcVersionId = buildInfos.get(BuildInfo.MINECRAFT_VERSION_ID).toString();
        var mcVersionName = buildInfos.get(BuildInfo.MINECRAFT_VERSION_NAME).toString();

        return versionMappings.containsKey(mcVersionId) && versionMappings.containsKey(mcVersionName);
    }

    @Override
    public ServerPlatform getSupport() {
        String classPath = "com.andrei1058.bedwars.platform.paper."+versionMappings.get(
                buildInfos.get(BuildInfo.MINECRAFT_VERSION_ID).toString()
        );

        try {
            return (ServerPlatform)Class.forName(classPath).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException ignored) {
        }

        return null;
    }

    private @NotNull HashMap<BuildInfo, Object> getBuildInfo() {
        HashMap<BuildInfo, Object> buildInfo = new HashMap<>();
        for (BuildInfo build : BuildInfo.values()) {
            buildInfo.put(build, "");
        }

        try {
            var buildInfoHolder = Class.forName("io.papermc.paper.ServerBuildInfo");
            var buildMethod = buildInfoHolder.getMethod("buildInfo");
            var build = buildMethod.invoke(null);

            var buildIdMethod = build.getClass().getMethod("brandId");
            var brandId = buildIdMethod.invoke(build);
            buildInfo.put(BuildInfo.BRAND_ID, brandId);

            var brandNameMethod = build.getClass().getMethod("brandName");
            var brandName = brandNameMethod.invoke(build);
            buildInfo.put(BuildInfo.BRAND_NAME, brandName);

            var minecraftVersionMethod = build.getClass().getMethod("minecraftVersionId");
            var minecraftVersion = minecraftVersionMethod.invoke(build);
            buildInfo.put(BuildInfo.MINECRAFT_VERSION_ID, minecraftVersion);

            var minecraftVersionNameMethod = build.getClass().getMethod("minecraftVersionName");
            var minecraftVersionName = minecraftVersionNameMethod.invoke(build);
            buildInfo.put(BuildInfo.MINECRAFT_VERSION_NAME, minecraftVersionName);

        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
        }

        return buildInfo;
    }
}
