package com.andrei1058.bedwars.api;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

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

    public static byte itemColor(TeamColor teamColor) {
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

    public static Color getColor(TeamColor teamColor){
        Color color = Color.WHITE;
        switch (teamColor){
            case PINK:
                color = Color.FUCHSIA;
                break;
            case GRAY:
                color = Color.GRAY;
                break;
            case BLUE:
                color =Color.BLUE;
                break;
            case WHITE:
                color=Color.WHITE;
                break;
            case DARK_GREEN:
                color =Color.GREEN;
                break;
            case AQUA:
                color = Color.AQUA;
                break;
            case RED:
                color = Color.RED;
                break;
            case GREEN:
                color =Color.LIME;
                break;
            case YELLOW:
                color = Color.YELLOW;
                break;
        }
        return color;
    }
}
