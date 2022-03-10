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

            levels.getYml().addDefault("levels.1-99.name", "&7[{number}✫] ");
            levels.getYml().addDefault("levels.1-99.rankup-cost", 1000);

            levels.getYml().addDefault("levels.100-199.name", "&7[{number}✫] ");
            levels.getYml().addDefault("levels.100-199.rankup-cost", 1000);

            levels.getYml().addDefault("levels.200-299.name", "&6[{number}✫] ");
            levels.getYml().addDefault("levels.200-299.rankup-cost", 1000);

            levels.getYml().addDefault("levels.300-399.name", "&b[{number}✫] ");
            levels.getYml().addDefault("levels.300-399.rankup-cost", 1000);
            levels.getYml().addDefault("levels.400-499.name", "&2[{number}✫] ");
            levels.getYml().addDefault("levels.400-499.rankup-cost", 1000);
            levels.getYml().addDefault("levels.500-599.name", "&3[{number}✫] ");
            levels.getYml().addDefault("levels.500-599.rankup-cost", 1000);
            levels.getYml().addDefault("levels.600-699.name", "&4[{number}✫] ");
            levels.getYml().addDefault("levels.600-699.rankup-cost", 1000);
            levels.getYml().addDefault("levels.700-799.name", "&d[{number}✫] ");
            levels.getYml().addDefault("levels.700-799.rankup-cost", 1000);
            levels.getYml().addDefault("levels.800-899.name", "&9[{number}✫] ");
            levels.getYml().addDefault("levels.800-899.rankup-cost", 1000);
            levels.getYml().addDefault("levels.900-999.name", "&5[{number}✫] ");
            levels.getYml().addDefault("levels.900-999.rankup-cost", 1000);
            levels.getYml().addDefault("levels.1000-1099.name", "&1[{number}✫] ");
            levels.getYml().addDefault("levels.1000-1099.rankup-cost", 1500);
            levels.getYml().addDefault("levels.1100-1199.name", "&c[{number}✫] ");
            levels.getYml().addDefault("levels.1100-1199.rankup-cost", 2000);
            levels.getYml().addDefault("levels.1200-1299.name", "&e[{number}✫] ");
            levels.getYml().addDefault("levels.1200-1299.rankup-cost", 2500);
            levels.getYml().addDefault("levels.1300-1399.name", "&a[{number}✫] ");
            levels.getYml().addDefault("levels.1300-1399.rankup-cost", 3000);
            levels.getYml().addDefault("levels.1400-1499.name", "&8[{number}✫] ");
            levels.getYml().addDefault("levels.1400-1499.rankup-cost", 3500);
            levels.getYml().addDefault("levels.1500-1599.name", "&0[{number}✫] ");
            levels.getYml().addDefault("levels.1500-1599.rankup-cost", 4000);
            levels.getYml().addDefault("levels.1600-1699.name", "&7[{number}✫] ");
            levels.getYml().addDefault("levels.1600-1699.rankup-cost", 4500);
            levels.getYml().addDefault("levels.1700-1799.name", "&f[{number}✫] ");
            levels.getYml().addDefault("levels.1700-1799.rankup-cost", 5000);
            levels.getYml().addDefault("levels.1800-1899.name", "&6[{number}✫] ");
            levels.getYml().addDefault("levels.1800-1899.rankup-cost", 5000);
            levels.getYml().addDefault("levels.1900-1999.name", "&b[{number}✫] ");
            levels.getYml().addDefault("levels.1900-1999.rankup-cost", 5000);
            levels.getYml().addDefault("levels.2000-2099.name", "&2[{number}✫] ");
            levels.getYml().addDefault("levels.2000-2099.rankup-cost", 5000);
            levels.getYml().addDefault("levels.2100-1199.name", "&3[{number}✫] ");
            levels.getYml().addDefault("levels.2100-2199.rankup-cost", 5000);
            levels.getYml().addDefault("levels.2200-2299.name", "&4[{number}✫] ");
            levels.getYml().addDefault("levels.2200-2299.rankup-cost", 5000);
            levels.getYml().addDefault("levels.2300-2399.name", "&d[{number}✫] ");
            levels.getYml().addDefault("levels.2300-2399.rankup-cost", 5000);
            levels.getYml().addDefault("levels.2400-2499.name", "&9[{number}✫] ");
            levels.getYml().addDefault("levels.2400-2499.rankup-cost", 5000);
            levels.getYml().addDefault("levels.2500-2599.name", "&5[{number}✫] ");
            levels.getYml().addDefault("levels.2500-2599.rankup-cost", 5000);
            levels.getYml().addDefault("levels.2600-2699.name", "&1[{number}✫] ");
            levels.getYml().addDefault("levels.2600-2699.rankup-cost", 5000);
            levels.getYml().addDefault("levels.2700-2799.name", "&c[{number}✫] ");
            levels.getYml().addDefault("levels.2700-2799.rankup-cost", 5000);
            levels.getYml().addDefault("levels.2800-2899.name", "&e[{number}✫] ");
            levels.getYml().addDefault("levels.2800-2899.rankup-cost", 5000);
            levels.getYml().addDefault("levels.2900-2999.name", "&a[{number}✫] ");
            levels.getYml().addDefault("levels.2900-2999.rankup-cost", 5000);
            levels.getYml().addDefault("levels.3000-3099.name", "&8[{number}✫] ");
            levels.getYml().addDefault("levels.3000-3099.rankup-cost", 5000);
            levels.getYml().addDefault("levels.3100-3199.name", "&c[{number}✫] ");
            levels.getYml().addDefault("levels.3100-3199.rankup-cost", 2000);
            levels.getYml().addDefault("levels.3200-3299.name", "&e[{number}✫] ");
            levels.getYml().addDefault("levels.3200-3299.rankup-cost", 2500);
            levels.getYml().addDefault("levels.3300-3399.name", "&a[{number}✫] ");
            levels.getYml().addDefault("levels.3300-3399.rankup-cost", 3000);
            levels.getYml().addDefault("levels.3400-3499.name", "&8[{number}✫] ");
            levels.getYml().addDefault("levels.3400-3499.rankup-cost", 3500);
            levels.getYml().addDefault("levels.3500-3599.name", "&0[{number}✫] ");
            levels.getYml().addDefault("levels.3500-3599.rankup-cost", 4000);
            levels.getYml().addDefault("levels.3600-3699.name", "&7[{number}✫] ");
            levels.getYml().addDefault("levels.3600-3699.rankup-cost", 4500);
            levels.getYml().addDefault("levels.3700-3799.name", "&f[{number}✫] ");
            levels.getYml().addDefault("levels.3700-3799.rankup-cost", 5000);
            levels.getYml().addDefault("levels.3800-3899.name", "&6[{number}✫] ");
            levels.getYml().addDefault("levels.3800-3899.rankup-cost", 5000);
            levels.getYml().addDefault("levels.3900-3999.name", "&b[{number}✫] ");
            levels.getYml().addDefault("levels.3900-3999.rankup-cost", 5000);
            levels.getYml().addDefault("levels.4000-4099.name", "&2[{number}✫] ");
            levels.getYml().addDefault("levels.4000-4099.rankup-cost", 5000);

            levels.getYml().addDefault("levels.others.name", "&7[{number}✩] ");
            levels.getYml().addDefault("levels.others.rankup-cost", 5000);
        }

        levels.getYml().addDefault("xp-rewards.per-minute", 10);
        levels.getYml().addDefault("xp-rewards.per-teammate", 5);
        levels.getYml().addDefault("xp-rewards.game-win", 100);

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
