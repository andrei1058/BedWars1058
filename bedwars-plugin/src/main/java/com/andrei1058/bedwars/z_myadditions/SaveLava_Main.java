package com.andrei1058.bedwars.z_myadditions;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public class SaveLava_Main {

    JavaPlugin plugin;

    public SaveLava_Main(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        Logger.getLogger("BW Addition \"SaveLava\" is Enable!").info("BW Addition \"SaveLava\" is Enable!");
        plugin.getServer().getPluginManager().registerEvents(new SaveLava_Listener(plugin), plugin);
    }

}
