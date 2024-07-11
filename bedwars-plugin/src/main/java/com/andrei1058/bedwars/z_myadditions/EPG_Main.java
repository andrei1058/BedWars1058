package com.andrei1058.bedwars.z_myadditions;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class EPG_Main {

    JavaPlugin plugin;
    public EPG_glassActions glassActions;

    public EPG_Main(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        Logger.getLogger("BW Addition \"ExplosionProofGlass\" is Enable!").info("BW Addition \"ExplosionProofGlass\" is Enable!");
        plugin.getServer().getPluginManager().registerEvents(new EPG_Listener(plugin), plugin);
    }
}
