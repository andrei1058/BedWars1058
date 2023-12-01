package com.andrei1058.bedwars.common.util.platform;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Getter
public enum ServerVersion {
    V1_8_8("v1_8_R3", "1.8.9", 8),
    V1_12_2("v1_12_R2", "1.12.2", 12),
    V1_13("v1_13_R1", "1.13", 13);


    private final String packageName;
    private final String displayName;
    private final int numeric;

    ServerVersion(String packageName, String displayName, int numeric) {
        this.packageName = packageName;
        this.displayName = displayName;
        this.numeric = numeric;
    }

    @Contract(pure = true)
    public boolean greaterThan(@NotNull ServerVersion serverVersion) {
        return this.numeric > serverVersion.numeric;
    }

    @Contract(pure = true)
    public boolean smallerThan(@NotNull ServerVersion serverVersion) {
        return this.numeric < serverVersion.numeric;
    }

}