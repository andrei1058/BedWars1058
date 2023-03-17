package com.andrei1058.bedwars.configuration;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigManager;

public class MoneyConfig extends ConfigManager {

    public static MoneyConfig money;

    private MoneyConfig() {
        super ( BedWars.plugin, "rewards", BedWars.plugin.getDataFolder ().toString () );
    }

    /**
     * Initialize money config.
     */
    public static void init() {
        money = new MoneyConfig ();
        money.getYml ().options ().copyDefaults ( true );
        money.getYml ().addDefault ( "money-rewards.per-minute", 5 );
        money.getYml ().addDefault ( "money-rewards.per-teammate", 30 );
        money.getYml ().addDefault ( "money-rewards.game-win", 90 );
        money.getYml ().addDefault ( "money-rewards.bed-destroyed", 60 );
        money.getYml ().addDefault ( "money-rewards.final-kill", 40 );
        money.getYml ().addDefault ( "money-rewards.regular-kill", 10 );
        money.save ();
    }
}