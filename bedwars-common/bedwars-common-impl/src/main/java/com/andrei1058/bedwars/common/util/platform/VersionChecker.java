package com.andrei1058.bedwars.common.util.platform;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Consumer;

public final class VersionChecker {

    private VersionChecker(){}

    /**
     * @param supportedVersions collection of supported versions.
     * @return true if server version is not supported.
     */
    public static boolean performVersionCheck(Collection<ServerVersion> supportedVersions) {
        return inCollection(getPackageVersion(), supportedVersions);
    }

    /**
     * @param supportedVersions collection of supported versions.
     * @param callback          logic applied when server version is not supported.
     *                          It returns the version package name. E.g. v1_8_R1.
     * @return true if server version is not supported.
     */
    public static boolean performVersionCheck(
            @NotNull Collection<ServerVersion> supportedVersions,
            Consumer<String> callback
    ) {
        String packageVersion = getPackageVersion();

        if (inCollection(packageVersion, supportedVersions)) {
            return false;
        }

        callback.accept(packageVersion);
        return true;
    }

    private static boolean inCollection(String packageName, @NotNull Collection<ServerVersion> supportedVersions) {
        return supportedVersions.stream().anyMatch(version -> version.getPackageName().equals(packageName));
    }

    private static String getPackageVersion() {
        return Bukkit.getServer().getClass().getName().split("\\.")[3];
    }
}
