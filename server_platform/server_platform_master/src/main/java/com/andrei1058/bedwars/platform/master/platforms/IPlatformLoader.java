package com.andrei1058.bedwars.platform.master.platforms;

import com.andrei1058.bedwars.platform.common.ServerPlatform;

public interface IPlatformLoader {

    /**
     * Check if server platform is supported.
     * Server version will be checked with another version.
     *
     * @return true if the server platform is supported, no matter what server version.
     */
    boolean isPlatformSupported();

    /**
     * Check if the server version is supported for current platform.
     *
     * @return true if server version is supported.
     */
    boolean isServerVersionSupported();

    /**
     * Get bed-wars platform wrapper implementation for current version.
     *
     * @return server platform and version support implementation.
     */
    ServerPlatform getSupport();
}
