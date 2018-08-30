package com.andrei1058.bedwars.support.bukkit.utils;

import com.andrei1058.bedwars.api.TeamColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Misc {

    /**
     * Get the byte color for Minecraft versions until 1.12 included
     *
     * @since API 10
     */
    @Contract(pure = true)
    public static byte getOldItemColor(@NotNull TeamColor teamColor) {
        int i = 0;
        switch (teamColor) {
            case PINK:
                i = 6;
                break;
            case RED:
                i = 14;
                break;
            case AQUA:
                i = 9;
                break;
            case GREEN:
                i = 5;
                break;
            case DARK_GREEN:
                i = 13;
                break;
            case YELLOW:
                i = 4;
                break;
            case BLUE:
                i = 11;
                break;
            case GRAY:
                i = 7;
                break;
            case WHITE:
                i = 0;
                break;
        }

        return (byte) i;
    }
}
