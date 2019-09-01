package com.andrei1058.bedwars.configuration;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.configuration.ConfigManager;

public class LevelsConfig extends ConfigManager {

    public static LevelsConfig levels;

    private LevelsConfig() {
        super(Main.plugin,"levels", Main.plugin.getDataFolder().toString());
    }

    /**
     * Initialize levels config.
     */
    public static void init() {
        levels = new LevelsConfig();
        levels.getYml().options().copyDefaults(true);
        if (levels.isFirstTime()){

            levels.getYml().addDefault("levels.1.name", "&7[{number}✩] ");
            levels.getYml().addDefault("levels.1.rankup-cost", 1000);

            levels.getYml().addDefault("levels.2.name", "&7[{number}✩] ");
            levels.getYml().addDefault("levels.2.rankup-cost", 2000);

            levels.getYml().addDefault("levels.3.name", "&7[{number}✩] ");
            levels.getYml().addDefault("levels.3.rankup-cost", 3000);

            levels.getYml().addDefault("levels.4.name", "&7[{number}✩] ");
            levels.getYml().addDefault("levels.4.rankup-cost", 3500);

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
}
