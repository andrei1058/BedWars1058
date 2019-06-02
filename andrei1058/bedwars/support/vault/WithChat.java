package com.andrei1058.bedwars.support.vault;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WithChat implements Chat {

    private static net.milkbowl.vault.chat.Chat chat;

    @Override
    public String getPrefix(Player p) {
        return ChatColor.translateAlternateColorCodes('&', chat.getPlayerPrefix(p));
    }

    @Override
    public String getSuffix(Player p) {
        return ChatColor.translateAlternateColorCodes('&', chat.getPlayerSuffix(p));
    }

    public static void setChat(net.milkbowl.vault.chat.Chat chat) {
        WithChat.chat = chat;
    }
}
