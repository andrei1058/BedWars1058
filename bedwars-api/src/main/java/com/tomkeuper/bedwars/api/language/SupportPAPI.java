package com.tomkeuper.bedwars.api.language;

import org.bukkit.entity.Player;

public interface SupportPAPI {


    /**
     * @return message with papi placeholders
     */
    String replace(Player p, String s);
}
