package com.andrei1058.bedwars.configuration;

import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

import static com.andrei1058.bedwars.Main.getForCurrentVersion;

public class SignsConfig extends ConfigManager {

    public SignsConfig(Plugin plugin, String name, String dir) {
        super(plugin, name, dir);
        YamlConfiguration yml = getYml();
        yml.addDefault("format", Arrays.asList("&a[arena]", "", "&2[on]&9/&2[max]", "[status]"));
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_WAITING_MATERIAL, getForCurrentVersion("STAINED_CLAY", "STAINED_CLAY", "GREEN_CONCRETE"));
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_WAITING_DATA, 5);
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_STARTING_MATERIAL, getForCurrentVersion("STAINED_CLAY", "STAINED_CLAY", "YELLOW_CONCRETE"));
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_STARTING_DATA, 14);
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_PLAYING_MATERIAL, getForCurrentVersion("STAINED_CLAY", "STAINED_CLAY", "RED_CONCRETE"));
        yml.addDefault(ConfigPath.SIGNS_STATUS_BLOCK_PLAYING_DATA, 4);
        yml.options().copyDefaults(true);
        save();
        if (yml.getStringList("format").size() < 4) {
            set("format", yml.getStringList("format").subList(0, 3));
        }
    }
}
