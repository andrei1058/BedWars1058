package com.andrei1058.bedwars.configuration;


import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.exceptions.InvalidSoundException;
import org.bukkit.configuration.file.YamlConfiguration;

import static com.andrei1058.bedwars.Main.nms;
import static com.andrei1058.bedwars.Main.plugin;
import static com.andrei1058.bedwars.configuration.ConfigPath.*;

public class Sounds {

    /** Load sounds configuration */
    public Sounds(){
        saveDefaultSounds();
        loadSounds();
    }

    private static ConfigManager sounds = new ConfigManager("sounds", "plugins/" + Main.plugin.getName(), false);

    @SuppressWarnings("WeakerAccess")
    public static void saveDefaultSounds() {
        YamlConfiguration yml = sounds.getYml();
        yml.addDefault(SOUNDS_BED_DESTROY, nms.bedDestroy().toString());
        yml.addDefault(SOUNDS_PLAYER_KILL, nms.playerKill().toString());
        yml.addDefault(SOUNDS_INSUFF_MONEY, nms.insufficientMoney().toString());
        yml.addDefault(SOUNDS_BOUGHT, nms.bought().toString());
        yml.addDefault(SOUNDS_COUNTDOWN_TICK, nms.countdownTick().toString());
        yml.options().copyDefaults(true);
        sounds.save();
    }

    @SuppressWarnings("WeakerAccess")
    public static void loadSounds() {
        try {
            nms.setBedDestroySound(sounds.getString(SOUNDS_BED_DESTROY));
            nms.setPlayerKillsSound(sounds.getString(SOUNDS_PLAYER_KILL));
            nms.setInsuffMoneySound(sounds.getString(SOUNDS_INSUFF_MONEY));
            nms.setBoughtSound(sounds.getString(SOUNDS_BOUGHT));
            nms.setCountdownSound(sounds.getString(SOUNDS_COUNTDOWN_TICK));
        } catch (InvalidSoundException invalidSound) {
            plugin.getLogger().severe(invalidSound.getMessage());
        }
    }
}
