package com.andrei1058.bedwars.lobbysocket;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.configuration.ConfigPath;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ArenaSocket {

    public static List<String> lobbies = new ArrayList<>();
    public static String serverName = Main.config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_NAME);

    /**
     * Send arena data to the lobbies.
     */
    public static void sendMessage(Arena a) {
        for (String lobby : new ArrayList<>(lobbies)) {

            String[] l = lobby.split(":");

            if (l.length != 2) continue;
            if (!Misc.isNumber(l[1])) continue;

            sendMessage(formatMessage(serverName, a.getDisplayName(), a.getStatus().toString(), a.getPlayers().size(), a.getMaxPlayers(), a.getSpectators().size()), l[0], Integer.valueOf(l[1]));
        }
    }

    /**
     * Send a message to the given host with target port.
     *
     * @return true if message was sent successfully.
     */
    private static boolean sendMessage(String message, String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(message);
            out.close();
            socket.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Format message before sending it to lobbies.
     */
    public static String formatMessage(String serverName, String arenaName, String status, int currentPlayers, int maxPlayers, int spectators) {
        return serverName + "." + arenaName + "." + status + "." + currentPlayers + "." + maxPlayers + "." + spectators;
    }
}
