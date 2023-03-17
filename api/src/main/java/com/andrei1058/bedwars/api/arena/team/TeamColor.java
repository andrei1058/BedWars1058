/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.api.arena.team;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
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
    DARK_GREEN,
    DARK_GRAY;

    /**
     * Get chat color by team color.
     *
     * @param tColor - {@link TeamColor} string.
     * @return {@link ChatColor} for given team.
     */
    public static ChatColor getChatColor(@NotNull String tColor) {
        ChatColor color;
        TeamColor teamColor = TeamColor.valueOf(tColor.toUpperCase());
        if (teamColor == TeamColor.PINK) {
            color = ChatColor.LIGHT_PURPLE;
        } else {
            color = ChatColor.valueOf(teamColor.toString());
        }
        return color;
    }

    /**
     * @return Chat color for this team color.
     */
    public ChatColor chat() {
        ChatColor color;
        TeamColor teamColor = TeamColor.valueOf(this.toString());
        if (teamColor == TeamColor.PINK) {
            color = ChatColor.LIGHT_PURPLE;
        } else {
            color = ChatColor.valueOf(teamColor.toString());
        }
        return color;
    }

    /**
     * @param teamColor team color.
     * @return Chat color for this team color.
     */
    @Deprecated
    public static ChatColor getChatColor(TeamColor teamColor) {
        ChatColor color;
        if (teamColor == TeamColor.PINK) {
            color = ChatColor.LIGHT_PURPLE;
        } else {
            color = ChatColor.valueOf(teamColor.toString());
        }
        return color;
    }

    /**
     * @param teamColor team color.
     * @return DyeColor color for this team color.
     */
    @Deprecated
    public static DyeColor getDyeColor(@NotNull String teamColor) {
        DyeColor color;
        switch (TeamColor.valueOf(teamColor.toUpperCase())) {
            case GREEN:
                color = DyeColor.LIME;
                break;
            case AQUA:
                color = DyeColor.LIGHT_BLUE;
                break;
            case DARK_GREEN:
                color = DyeColor.GREEN;
                break;
            case DARK_GRAY:
                color = DyeColor.GRAY;
                break;
            default:
                color = DyeColor.valueOf(teamColor.toUpperCase());
                break;
        }
        return color;
    }

    /**
     * @return DyeColor color for this team color.
     */
    public DyeColor dye() {
        DyeColor color;
        switch (this) {
            case GREEN:
                color = DyeColor.LIME;
                break;
            case AQUA:
                color = DyeColor.LIGHT_BLUE;
                break;
            case DARK_GREEN:
                color = DyeColor.GREEN;
                break;
            case DARK_GRAY:
                color = DyeColor.GRAY;
                break;
            default:
                color = DyeColor.valueOf(this.toString());
                break;
        }
        return color;
    }

    /**
     * @param teamColor team color.
     * @return byte color for MC versions until 1.12 included
     */
    @Deprecated
    public static byte itemColor(@NotNull TeamColor teamColor) {
        // 0 white
        int i = 0;
        switch (teamColor) {
            case WHITE:
                break;
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
                i = 8;
                break;
            case DARK_GRAY:
                i = 7;
                break;
        }
        return (byte) i;
    }

    /**
     * @return byte color for MC versions until 1.12 included
     */
    public byte itemByte() {
        // 0 white
        int i = 0;
        switch (this) {
            case WHITE:
                break;
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
                i = 8;
                break;
            case DARK_GRAY:
                i = 7;
        }
        return (byte) i;
    }

    /**
     * Get the english for material as color name.
     *
     * @param material material string.
     * @return the english color name for given material. EMPTY if item is not supported.
     */
    public static String enName(@NotNull String material) {
        String name = "";
        switch (material.toUpperCase()) {
            case "PINK_WOOL":
                name = "Pink";
                break;
            case "RED_WOOL":
                name = "Red";
                break;
            case "LIGHT_GRAY_WOOL":
                name = "Gray";
                break;
            case "BLUE_WOOL":
                name = "Blue";
                break;
            case "WHITE_WOOL":
                name = "White";
                break;
            case "LIGHT_BLUE_WOOL":
                name = "Aqua";
                break;
            case "LIME_WOOL":
                name = "Green";
                break;
            case "YELLOW_WOOL":
                name = "Yellow";
                break;
            case "GRAY_WOOL":
                name = "Dark_Gray";
                break;
        }
        return name;
    }

    /**
     * Get the english for byte as color name.
     *
     * @param b color byte. Used for 1.12 and older.
     * @return the english color name for given material. EMPTY if item is not supported.
     */
    public static String enName(byte b) {
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
            case 8:
                name = "Dark_Gray";
                break;
            case 7:
                name = "Gray";
                break;

        }
        return name;
    }

    /**
     * This may be used for leather armor.
     *
     * @param teamColor team color.
     * @return Equivalent color. Returns WHITE if given color has no equivalent.
     */
    @Deprecated
    public static Color getColor(@NotNull TeamColor teamColor) {
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
            case DARK_GRAY:
                color = Color.fromBGR(74, 74, 74);
                break;
        }
        return color;
    }

    /**
     * This is usually used for leather armor.
     *
     * @return Equivalent color. Returns WHITE if given color has no equivalent.
     */
    public Color bukkitColor() {
        Color color = Color.WHITE;
        switch (this) {
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
            case DARK_GRAY:
                color = Color.fromBGR(74, 74, 74);
                break;
        }
        return color;
    }

    /**
     * Get bed with color.
     *
     * @param teamColor team color.
     * @return 1.13+ material
     */
    @Deprecated
    public static Material getBedBlock(@NotNull TeamColor teamColor) {
        String color = "RED_BED";
        switch (teamColor) {
            case PINK:
                color = "PINK_BED";
                break;
            case GRAY:
                color = "LIGHT_GRAY_BED";
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
            case DARK_GRAY:
                color = "GRAY_BED";
                break;
        }
        return Material.valueOf(color);
    }

    /**
     * Get bed with color. Used for 1.13+.
     *
     * @return 1.13+ bed material. Return RED_BED if not found.
     */
    public Material bedMaterial() {
        String color = "RED_BED";
        switch (this) {
            case PINK:
                color = "PINK_BED";
                break;
            case GRAY:
                color = "LIGHT_GRAY_BED";
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
            case DARK_GRAY:
                color = "GRAY_BED";
                break;
        }
        return Material.valueOf(color);
    }

    /**
     * Get glass with team color.
     *
     * @param teamColor team color.
     * @return 1.13+ material
     */
    @Deprecated
    public static Material getGlass(@NotNull TeamColor teamColor) {
        String color = "GLASS";
        switch (teamColor) {
            case PINK:
                color = "PINK_STAINED_GLASS";
                break;
            case GRAY:
                color = "LIGHT_GRAY_STAINED_GLASS";
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
            case RED:
                color = "RED_STAINED_GLASS";
                break;
            case DARK_GRAY:
                color = "GRAY_STAINED_GLASS";
                break;
        }
        return Material.valueOf(color);
    }

    /**
     * Get glass with team color. Used for 1.13+ team glass.
     *
     * @return 1.13+ glass material. Return GLASS if this team does not have a custom glass.
     */
    public Material glassMaterial() {
        String color = "GLASS";
        switch (this) {
            case PINK:
                color = "PINK_STAINED_GLASS";
                break;
            case GRAY:
                color = "LIGHT_GRAY_STAINED_GLASS";
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
            case RED:
                color = "RED_STAINED_GLASS";
                break;
            case DARK_GRAY:
                color = "GRAY_STAINED_GLASS";
                break;
        }
        return Material.valueOf(color);
    }

    /**
     * Retrieve glass pane with team color.
     *
     * @param teamColor team color.
     * @return glass pane material for 1.13+.
     */
    @Deprecated
    public static Material getGlassPane(@NotNull TeamColor teamColor) {
        String color = "GLASS";
        switch (teamColor) {
            case PINK:
                color = "PINK_STAINED_GLASS_PANE";
                break;
            case GRAY:
                color = "LIGHT_GRAY_STAINED_GLASS_PANE";
                break;
            case BLUE:
                color = "BLUE_STAINED_GLASS_PANE";
                break;
            case WHITE:
                color = "WHITE_STAINED_GLASS_PANE";
                break;
            case DARK_GREEN:
                color = "GREEN_STAINED_GLASS_PANE";
                break;
            case AQUA:
                color = "LIGHT_BLUE_STAINED_GLASS_PANE";
                break;
            case GREEN:
                color = "LIME_STAINED_GLASS_PANE";
                break;
            case YELLOW:
                color = "YELLOW_STAINED_GLASS_PANE";
                break;
            case RED:
                color = "RED_STAINED_GLASS_PANE";
                break;
            case DARK_GRAY:
                color = "GRAY_STAINED_PANE";
                break;
        }
        return Material.valueOf(color);
    }

    /**
     * Retrieve glass pane with team color.
     *
     * @return glass pane material for 1.13+.
     */
    public Material glassPaneMaterial() {
        String color = "GLASS";
        switch (this) {
            case PINK:
                color = "PINK_STAINED_GLASS_PANE";
                break;
            case GRAY:
                color = "LIGHT_GRAY_STAINED_GLASS_PANE";
                break;
            case BLUE:
                color = "BLUE_STAINED_GLASS_PANE";
                break;
            case WHITE:
                color = "WHITE_STAINED_GLASS_PANE";
                break;
            case DARK_GREEN:
                color = "GREEN_STAINED_GLASS_PANE";
                break;
            case AQUA:
                color = "LIGHT_BLUE_STAINED_GLASS_PANE";
                break;
            case GREEN:
                color = "LIME_STAINED_GLASS_PANE";
                break;
            case YELLOW:
                color = "YELLOW_STAINED_GLASS_PANE";
                break;
            case RED:
                color = "RED_STAINED_GLASS_PANE";
                break;
            case DARK_GRAY:
                color = "GRAY_STAINED_PANE";
                break;
        }
        return Material.valueOf(color);
    }

    /**
     * Get glazed terracotta with team color.
     *
     * @param teamColor team color.
     * @return 1.13+ material
     */
    @Deprecated
    public static Material getGlazedTerracotta(@NotNull TeamColor teamColor) {
        String color = "ORANGE_TERRACOTTA";
        switch (teamColor) {
            case PINK:
                color = "PINK_TERRACOTTA";
                break;
            case GRAY:
                color = "LIGHT_GRAY_TERRACOTTA";
                break;
            case DARK_GRAY:
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
            case RED:
                color = "RED_TERRACOTTA";
                break;
        }
        return Material.valueOf(color);
    }

    /**
     * Get glazed terracotta with team color.
     *
     * @return 1.13+ material.
     */
    public Material glazedTerracottaMaterial() {
        String color = "ORANGE_TERRACOTTA";
        switch (this) {
            case PINK:
                color = "PINK_TERRACOTTA";
                break;
            case GRAY:
                color = "LIGHT_GRAY_TERRACOTTA";
                break;
            case DARK_GRAY:
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
            case RED:
                color = "RED_TERRACOTTA";
                break;
        }
        return Material.valueOf(color);
    }

    /**
     * Get wool with team color.
     *
     * @param teamColor team color.
     * @return 1.13+ material.
     */
    @Deprecated
    public static Material getWool(@NotNull TeamColor teamColor) {
        String color = "WHITE_WOOL";
        switch (teamColor) {
            case PINK:
                color = "PINK_WOOL";
                break;
            case GRAY:
                color = "LIGHT_GRAY_WOOL";
                break;
            case DARK_GRAY:
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
            case RED:
                color = "RED_WOOL";
                break;
        }
        return Material.valueOf(color);
    }

    /**
     * Get wool with team color.
     *
     * @return 1.13+ material.
     */
    public Material woolMaterial() {
        String color = "WHITE_WOOL";
        switch (this) {
            case PINK:
                color = "PINK_WOOL";
                break;
            case GRAY:
                color = "LIGHT_GRAY_WOOL";
                break;
            case DARK_GRAY:
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
            case RED:
                color = "RED_WOOL";
                break;
        }
        return Material.valueOf(color);
    }
}
