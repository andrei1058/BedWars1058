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
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import org.jetbrains.annotations.NotNull;

public class LevelsConfig extends ConfigManager {

    public static LevelsConfig levels;

    private LevelsConfig() {
        super(BedWars.plugin, "levels", BedWars.plugin.getDataFolder().toString());
    }

    /**
     * Initialize levels config.
     */
    public static void init() {
        levels = new LevelsConfig();
        levels.getYml().options().copyDefaults(true);
        if (levels.isFirstTime()) {

            levels.getYml().addDefault("levels.1.name", "&7[{number}✩] ");
            levels.getYml().addDefault("levels.1.rankup-cost", 1000);

            levels.getYml().addDefault("levels.2.name", "&7[{number}✩] ");
            levels.getYml().addDefault("levels.2.rankup-cost", 2000);

            levels.getYml().addDefault("levels.3.name", "&7[{number}✩] ");
            levels.getYml().addDefault("levels.3.rankup-cost", 3000);

            levels.getYml().addDefault("levels.4.name", "&7[{number}✩] ");
            levels.getYml().addDefault("levels.4.rankup-cost", 3500);
            levels.getYml().addDefault("levels.5-10.name", "&e[{number}✩] ");
            levels.getYml().addDefault("levels.5-10.rankup-cost", 5000);

            levels.getYml().addDefault("levels.others.name", "&7[{number}✩] ");
            levels.getYml().addDefault("levels.others.rankup-cost", 5000);
        }

        levels.getYml().addDefault("xp-rewards.per-minute", 10);
        levels.getYml().addDefault("xp-rewards.per-teammate", 5);
        levels.getYml().addDefault("xp-rewards.game-win", 100);
        levels.getYml().addDefault("xp-rewards.bed-destroyed", 15);
        levels.getYml().addDefault("xp-rewards.regular-kill", 10);
        levels.getYml().addDefault("xp-rewards.final-kill", 15);

        levels.getYml().addDefault("progress-bar.symbol", "■");
        levels.getYml().addDefault("progress-bar.unlocked-color", "&b");
        levels.getYml().addDefault("progress-bar.locked-color", "&7");
        levels.getYml().addDefault("progress-bar.format", "&8 [{progress}&8]");

        levels.save();
    }

    @NotNull
    public static String getLevelName(int level) {
        String name = levels.getYml().getString("levels." + level + ".name");
        if (name != null) return name;
        for (String key : levels.getYml().getConfigurationSection("levels").getKeys(false)) {
            if (key.contains("-")) {
                String[] nrs = key.split("-");
                if (nrs.length != 2) continue;
                int nr1, nr2;
                try {
                    nr1 = Integer.parseInt(nrs[0]);
                    nr2 = Integer.parseInt(nrs[1]);
                } catch (Exception ex) {
                    continue;
                }
                if (nr1 <= level && level <= nr2) {
                    return levels.getYml().getString("levels." + key + ".name");
                }
            }
        }
        return levels.getYml().getString("levels.others.name");
    }

    public static int getNextCost(int level) {
        if (levels.getYml().get("levels." + level + ".rankup-cost") != null) return levels.getYml().getInt("levels." + level + ".rankup-cost");
        for (String key : levels.getYml().getConfigurationSection("levels").getKeys(false)) {
            if (key.contains("-")) {
                String[] nrs = key.split("-");
                if (nrs.length != 2) continue;
                int nr1, nr2;
                try {
                    nr1 = Integer.parseInt(nrs[0]);
                    nr2 = Integer.parseInt(nrs[1]);
                } catch (Exception ex) {
                    continue;
                }
                if (nr1 <= level && level <= nr2) {
                    return levels.getYml().getInt("levels." + key + ".rankup-cost");
                }
            }
        }
        return levels.getYml().getInt("levels.others.rankup-cost");
    }
}
