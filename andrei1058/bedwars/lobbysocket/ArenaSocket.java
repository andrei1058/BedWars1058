package com.andrei1058.bedwars.lobbysocket;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArenaSocket {

    public static List<String> lobbies = new ArrayList<>();
    public static HashMap<String, RemoteLobby> sockets = new HashMap<>();
    public static String serverIdentifier;

    /**
     * Send arena data to the lobbies.
     */
    public static void sendMessage(Arena a) {
        for (String lobby : new ArrayList<>(lobbies)) {

            String[] l = lobby.split(":");

            if (l.length != 2) continue;
            if (!Misc.isNumber(l[1])) continue;

            if (sockets.containsKey(lobby)) {
                sockets.get(lobby).sendMessage(formatMessage(serverIdentifier, a.getDisplayName(), a.getStatus().toString(), a.getPlayers().size(), a.getMaxPlayers(), a.getGroup(), a.getMaxInTeam()));
            } else {
                try {
                    Socket socket = new Socket(l[0], Integer.valueOf(l[1]));
                    RemoteLobby rl = new RemoteLobby(socket, lobby);
                    sockets.put(lobby, rl);
                    rl.sendMessage(formatMessage(serverIdentifier, a.getDisplayName(), a.getStatus().toString(), a.getPlayers().size(), a.getMaxPlayers(), a.getGroup(), a.getMaxInTeam()));
                } catch (IOException e) {
                    Main.debug("Could not send data to lobby: " + lobby);
                }
            }
        }
    }

    /**
     * Format message before sending it to lobbies.
     */
    public static String formatMessage(String serverName, String arenaName, String status, int currentPlayers, int maxPlayers, String arenaGroup, int maxInTeam) {
        return serverName.replace(".", "-") + "." + arenaName + "." + status + "." + currentPlayers + "." + maxPlayers + "." + arenaGroup + "." + maxInTeam;
    }

    private static class RemoteLobby {
        private Socket socket;
        private PrintWriter out;
        private String lobby;

        public RemoteLobby(Socket socket, String lobby) {
            this.socket = socket;
            this.lobby = lobby;
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
            }
        }

        /**
         * Send a message to the given host with target port.
         *
         * @return true if message was sent successfully.
         */
        private boolean sendMessage(String message) {
            if (socket == null) {
                sockets.remove(lobby);
                return false;
            }
            if (socket.isClosed()) {
                sockets.remove(lobby);
                return false;
            }
            if (out == null) {
                sockets.remove(lobby);
                return false;
            }
            if (out.checkError()){
                sockets.remove(lobby);
            }
            out.println(message);
            return true;
        }
    }

    /**
     * Close active sockets.
     */
    public static void disable() {
        for (RemoteLobby rl : sockets.values()){
            rl.sendMessage("stop");
            rl.out.close();
            try {
                rl.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
