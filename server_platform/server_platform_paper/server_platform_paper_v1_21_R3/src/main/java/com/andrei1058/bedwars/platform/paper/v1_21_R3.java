package com.andrei1058.bedwars.platform.paper;

import com.andrei1058.bedwars.api.server.VersionSupport;
import com.andrei1058.spigot.sidebar.PAPIAdapter;
import com.andrei1058.spigot.sidebar.PAPISupport;
import com.andrei1058.spigot.sidebar.SidebarManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class v1_21_R3 extends PaperPlatform {

    @SuppressWarnings("unused")
    public v1_21_R3() {

        // PAPI hook
        var hasPAPI = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;

        SidebarManager.setInstance(new SidebarManager(
                new com.andrei1058.spigot.sidebar.v1_21_R3.ProviderImpl(),
                hasPAPI ? new PAPIAdapter() : new PAPISupport() {
                    @Override
                    public String replacePlaceholders(Player player, String s) {
                        return s;
                    }

                    @Override
                    public boolean hasPlaceholders(String s) {
                        return false;
                    }
                }
                ));
    }

    @Override
    public VersionSupport getOldWrapper(JavaPlugin plugin) {
        return new v1_21_R3_NMS(plugin, "v1_21_R3");
    }

    @Override
    public String getVersion() {
        return "1.21.4";
    }
}
