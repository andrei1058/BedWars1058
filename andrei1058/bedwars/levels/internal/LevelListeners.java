package com.andrei1058.bedwars.levels.internal;

import com.andrei1058.bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class LevelListeners implements Listener {

    public static LevelListeners instance;

    public LevelListeners(){
        instance = this;
    }

    //create new level data on player join
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final UUID u = e.getPlayer().getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            if (PlayerLevel.getLevelByPlayer(e.getPlayer().getUniqueId()) != null) return;
            int[] levelData = Main.getRemoteDatabase().getLevelData(u);
            new PlayerLevel(e.getPlayer().getUniqueId(), levelData[0], levelData[1]);
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        final UUID u = e.getPlayer().getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            PlayerLevel pl = PlayerLevel.getLevelByPlayer(u);
            pl.destroy();
        });
    }
}
