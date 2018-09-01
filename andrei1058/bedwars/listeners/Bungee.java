package com.andrei1058.bedwars.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import static com.andrei1058.bedwars.Main.config;
import static com.andrei1058.bedwars.Main.debug;
import static com.andrei1058.bedwars.Main.lobbyServer;

public class Bungee implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        debug("amdjkwe fiun3yvb34yuvb39 n934unv 93n49vvvdddddddddddddddddddddddddd");
        if (s.equals("BungeeCord")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subchannel = in.readUTF();
            if (subchannel.equals("GetServers")) {
                String[] serverList = in.readUTF().split(", ");
                for (String server : serverList) {
                    debug("Received servers: " + serverList);
                    if (server.equalsIgnoreCase(config.getYml().getString("lobbyServer"))) {
                        lobbyServer = true;
                        debug("Lobby server found.");
                    } else {
                        debug("Lobby server not found.");
                    }
                }
            }
        }
    }
}
