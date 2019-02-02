package com.andrei1058.bedwars.lobbysocket;

import com.andrei1058.bedwars.Main;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TempListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if (!ArenaSocket.lobbies.isEmpty()) return;
        Bukkit.getScheduler().runTaskLater(Main.plugin, ()-> {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("PostArena");
            e.getPlayer().sendPluginMessage(Main.plugin, "bedwars:proxy", out.toByteArray());
        }, 15L);
    }
}
