package com.andrei1058.bedwars.api.language;

import org.bukkit.entity.Player;

public interface SupportPAPI {


    /**
     * @return message with papi placeholders
     */
    String replace(Player p, String s);
}
