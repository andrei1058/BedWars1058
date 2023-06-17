package com.tomkeuper.bedwars.api.chat;

import org.bukkit.entity.Player;

/**
 * Get Chat Methods
 */
public interface IChat {

    /**
     * Get Player prefix
     * @param p player from which to take the prefix
     */
    String getPrefix(Player p);

    /**
     * Get Player suffix
     * @param p player from which to take the suffix
     */
    String getSuffix(Player p);

}
