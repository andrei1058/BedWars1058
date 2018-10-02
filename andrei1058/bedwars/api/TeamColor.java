package com.andrei1058.bedwars.api;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum TeamColor {

    RED,
    BLUE,
    GREEN,
    YELLOW,
    AQUA,
    WHITE,
    PINK,
    GRAY,
    DARK_GREEN;

    public static ChatColor getChatColor(String culoare) {
        ChatColor color;
        TeamColor teamColor = TeamColor.valueOf(culoare.toUpperCase());
        switch (teamColor) {
            case PINK:
                color = ChatColor.LIGHT_PURPLE;
                break;
            default:
                color = ChatColor.valueOf(teamColor.toString());
                break;
        }
        return color;
    }

    public static ChatColor getChatColor(TeamColor teamColor) {
        ChatColor color;
        switch (teamColor) {
            case PINK:
                color = ChatColor.LIGHT_PURPLE;
                break;
            default:
                color = ChatColor.valueOf(teamColor.toString());
                break;
        }
        return color;
    }

    public static DyeColor getDyeColor(String culoare) {
        TeamColor teamColor = TeamColor.valueOf(culoare.toUpperCase());
        DyeColor color;
        switch (teamColor) {
            case GREEN:
                color = DyeColor.LIME;
                break;
            case AQUA:
                color = DyeColor.LIGHT_BLUE;
                break;
            case DARK_GREEN:
                color = DyeColor.GREEN;
                break;
            default:
                color = DyeColor.valueOf(teamColor.toString());
                break;
        }
        return color;
    }

    @Contract(pure = true)
    @Deprecated
    /**
     * Get the byte color for Minecraft versions until 1.12 included
     *
     */
    public static byte itemColor(@NotNull TeamColor teamColor) {
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

    /**
     * Get the english for byte as color name
     *
     * @since api 6
     */
    public static String enName(Byte b) {
        String name = "";
        switch (b) {
            case 6:
                name = "Pink";
                break;
            case 14:
                name = "Red";
                break;
            case 9:
                name = "Aqua";
                break;
            case 5:
                name = "Green";
                break;
            case 4:
                name = "Yellow";
                break;
            case 11:
                name = "Blue";
                break;
            case 0:
                name = "White";
                break;
            case 7:
                name = "Gray";
                break;

        }
        return name;
    }

    public static Color getColor(TeamColor teamColor) {
        Color color = Color.WHITE;
        switch (teamColor) {
            case PINK:
                color = Color.FUCHSIA;
                break;
            case GRAY:
                color = Color.GRAY;
                break;
            case BLUE:
                color = Color.BLUE;
                break;
            case WHITE:
                color = Color.WHITE;
                break;
            case DARK_GREEN:
                color = Color.GREEN;
                break;
            case AQUA:
                color = Color.AQUA;
                break;
            case RED:
                color = Color.RED;
                break;
            case GREEN:
                color = Color.LIME;
                break;
            case YELLOW:
                color = Color.YELLOW;
                break;
        }
        return color;
    }

    /**
     * Get bed with color
     *
     * @return 1.13+ material
     * @since API 11
     */
    public static Material getBedBlock(TeamColor teamColor) {
        String color = "RED_BED";
        switch (teamColor) {
            case PINK:
                color = "PINK_BED";
                break;
            case GRAY:
                color = "GRAY_BED";
                break;
            case BLUE:
                color = "BLUE_BED";
                break;
            case WHITE:
                color = "WHITE_BED";
                break;
            case DARK_GREEN:
                color = "GREEN_BED";
                break;
            case AQUA:
                color = "LIGHT_BLUE_BED";
                break;
            case GREEN:
                color = "LIME_BED";
                break;
            case YELLOW:
                color = "YELLOW_BED";
                break;
        }
        return Material.valueOf(color);
    }

    /**
     * Get glass with team color
     *
     * @return 1.13+ material
     * @since API 11
     */
    public static Material getGlass(TeamColor teamColor) {
        String color = "GLASS";
        switch (teamColor) {
            case PINK:
                color = "PINK_STAINED_GLASS";
                break;
            case GRAY:
                color = "GRAY_STAINED_GLASS";
                break;
            case BLUE:
                color = "BLUE_STAINED_GLASS";
                break;
            case WHITE:
                color = "WHITE_STAINED_GLASS";
                break;
            case DARK_GREEN:
                color = "GREEN_STAINED_GLASS";
                break;
            case AQUA:
                color = "LIGHT_BLUE_STAINED_GLASS";
                break;
            case GREEN:
                color = "LIME_STAINED_GLASS";
                break;
            case YELLOW:
                color = "YELLOW_STAINED_GLASS";
                break;
        }
        return Material.valueOf(color);
    }

    /**
     * Get glazed terracotta with team color
     *
     * @return 1.13+ material
     * @since API 11
     */
    public static Material getGlazedTerracotta(TeamColor teamColor) {
        String color = "ORANGE_TERRACOTTA";
        switch (teamColor) {
            case PINK:
                color = "PINK_TERRACOTTA";
                break;
            case GRAY:
                color = "GRAY_TERRACOTTA";
                break;
            case BLUE:
                color = "BLUE_TERRACOTTA";
                break;
            case WHITE:
                color = "WHITE_TERRACOTTA";
                break;
            case DARK_GREEN:
                color = "GREEN_TERRACOTTA";
                break;
            case AQUA:
                color = "LIGHT_BLUE_TERRACOTTA";
                break;
            case GREEN:
                color = "LIME_TERRACOTTA";
                break;
            case YELLOW:
                color = "YELLOW_TERRACOTTA";
                break;
        }
        return Material.valueOf(color);
    }

    /**
     * Get wool with team color
     *
     * @return 1.13+ material
     * @since API 11
     */
    public static Material getWool(TeamColor teamColor) {
        String color = "WHITE_WOOL";
        switch (teamColor) {
            case PINK:
                color = "PINK_WOOL";
                break;
            case GRAY:
                color = "GRAY_WOOL";
                break;
            case BLUE:
                color = "BLUE_WOOL";
                break;
            case WHITE:
                color = "WHITE_WOOL";
                break;
            case DARK_GREEN:
                color = "GREEN_WOOL";
                break;
            case AQUA:
                color = "LIGHT_BLUE_WOOL";
                break;
            case GREEN:
                color = "LIME_WOOL";
                break;
            case YELLOW:
                color = "YELLOW_WOOL";
                break;
        }
        return Material.valueOf(color);
    }
}
