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

package com.andrei1058.bedwars.configuration;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.entity.Player;

public class Permissions {
    public static final String PERMISSION_FORCESTART = BedWars.mainCmd+".forcestart";
    public static final String PERMISSION_ALL = BedWars.mainCmd+".*";
    public static final String PERMISSION_COMMAND_BYPASS = BedWars.mainCmd+".cmd.bypass";
    public static final String PERMISSION_SHOUT_COMMAND = BedWars.mainCmd+".shout";

    public static final String PERMISSION_SETUP_ARENA = BedWars.mainCmd+".setup";
    public static final String PERMISSION_ARENA_GROUP = BedWars.mainCmd+".groups";
    public static final String PERMISSION_BUILD = BedWars.mainCmd+".build";
    public static final String PERMISSION_CLONE = BedWars.mainCmd+".clone";
    public static final String PERMISSION_DEL_ARENA = BedWars.mainCmd+".delete";
    public static final String PERMISSION_ARENA_ENABLE = BedWars.mainCmd+".enableRotation";
    public static final String PERMISSION_ARENA_DISABLE = BedWars.mainCmd+".disable";
    public static final String PERMISSION_NPC = BedWars.mainCmd+".npc";
    public static final String PERMISSION_RELOAD = BedWars.mainCmd+".reload";
    public static final String PERMISSION_REJOIN = BedWars.mainCmd+".rejoin";
    public static final String PERMISSION_LEVEL = BedWars.mainCmd+".level";
    public static final String PERMISSION_CHAT_COLOR = BedWars.mainCmd+".chatcolor";
    public static final String PERMISSION_VIP = BedWars.mainCmd+".vip";

    /**
     * Check if player has one of the given permissions.
     */
    public static boolean hasPermission(Player player, String... permissions){
        for (String permission : permissions){
            if (player.hasPermission(permission)){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if player has all given permissions.
     */
    public static boolean hasPermissions(Player player, String... permissions){
        for (String permission : permissions){
            if (!player.hasPermission(permission)){
                return false;
            }
        }
        return true;
    }
}
