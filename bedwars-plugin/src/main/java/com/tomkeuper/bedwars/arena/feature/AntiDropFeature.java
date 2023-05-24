package com.tomkeuper.bedwars.arena.feature;

import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.configuration.ConfigPath;
import com.tomkeuper.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.ArrayList;
import java.util.List;

public class AntiDropFeature implements Listener {
    private static AntiDropFeature instance;
    public AntiDropFeature() {
        Bukkit.getPluginManager().registerEvents(this, BedWars.plugin);
    }

    public static void init() {
        if (BedWars.config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_ENABLE_ANTI_DROP)) {
            if (instance == null) {
                instance = new AntiDropFeature();
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if (!BedWars.getAPI().getArenaUtil().isPlaying(player)) {
            return;
        }
        if (!Arena.getArenaByPlayer(player).getStatus().equals(GameState.playing)) {
            return;
        }
        List<Block> blocks = new ArrayList<>();
        blocks.add(player.getLocation().clone().subtract(0.0D, 0.1D, 0.0D).getBlock());
        for (int i = 1; i <= 4; i++) {
            blocks.add(player.getLocation().clone().subtract(0.0D, i, 0.0D).getBlock());
        }
        for (Block block : blocks) {
            if (block.getType().equals(Material.AIR)) {
                continue;
            }
            return;
        }
        e.setCancelled(true);
    }
}
