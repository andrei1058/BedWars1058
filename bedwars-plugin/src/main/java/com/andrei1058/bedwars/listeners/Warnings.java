package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Warnings implements Listener {
    private final BedWars plugin;

    public Warnings(BedWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(!player.isOp()) return;

        if (Bukkit.getPluginManager().isPluginEnabled("Multiverse-Core")) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.sendMessage(ChatColor.RED + "[BedWars1058] Multiverse-Core detected! Please remove it or make sure it won't touch BedWars maps!");
            }, 5); // run after 5 ticks to make sure its after any update spam on join
        }

        if(Bukkit.getServer().getSpawnRadius() > 0) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.sendMessage(ChatColor.RED + "[BedWars1058] Your spawn-protection in server.properties is enabled. "+ChatColor.YELLOW+"This might mess with BedWars arenas!"+ChatColor.GRAY+" It is highly reccomend setting it to 0.");
            }, 5);
        }
    }
}
