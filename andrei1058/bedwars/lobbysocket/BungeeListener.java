package com.andrei1058.bedwars.lobbysocket;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.ArrayList;
import java.util.List;

public class BungeeListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if (s.equals("bedwars:proxy")){
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subChannel = in.readUTF();
            if (subChannel.equals("GetLobbies")){
                String[] servers = in.readUTF().split(",");

                List<String> configServers = Main.config.getYml().getStringList(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_LOBBY_SERVERS);
                for (String server : servers){
                    if (server == null) continue;
                    if (server.isEmpty()) continue;
                    if (server.equals("null")) continue;
                    if (!new ArrayList<>(ArenaSocket.lobbies).contains(server)) ArenaSocket.lobbies.add(server);
                    Main.plugin.getLogger().severe(server);
                    if (!configServers.contains(server)) configServers.add(server);
                }

                Main.config.set(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_LOBBY_SERVERS, configServers);
            } else if (subChannel.equals("GetName")){
                ArenaSocket.serverName = in.readUTF();
                //save new server name in config
                if (!Main.config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_NAME).equals(ArenaSocket.serverName))
                    Main.config.set(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_NAME, ArenaSocket.serverName);
            }
        }
    }
}
