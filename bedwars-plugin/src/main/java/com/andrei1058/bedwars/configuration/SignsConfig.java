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

import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

import static com.andrei1058.bedwars.BedWars.getForCurrentVersion;

public class SignsConfig extends ConfigManager {

    public SignsConfig(Plugin plugin, String name, String dir) {
        super(plugin, name, dir);
        YamlConfiguration yml = getYml();
        yml.addDefault("format", Arrays.asList("&a[arena]", "", "&2[on]&9/&2[max] &7([type])", "[status]"));
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_WAITING_MATERIAL, getForCurrentVersion("STAINED_CLAY", "STAINED_CLAY", "GREEN_CONCRETE"));
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_WAITING_DATA, 5);
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_STARTING_MATERIAL, getForCurrentVersion("STAINED_CLAY", "STAINED_CLAY", "YELLOW_CONCRETE"));
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_STARTING_DATA, 14);
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_PLAYING_MATERIAL, getForCurrentVersion("STAINED_CLAY", "STAINED_CLAY", "RED_CONCRETE"));
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_PLAYING_DATA, 4);
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_RESTARTING_MATERIAL, getForCurrentVersion("STAINED_CLAY", "STAINED_CLAY", "RED_CONCRETE"));
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_RESTARTING_DATA, 4);
        yml.options().copyDefaults(true);
        save();
        if (yml.getStringList("format").size() < 4) {
            set("format", yml.getStringList("format").subList(0, 3));
        }
    }
}
