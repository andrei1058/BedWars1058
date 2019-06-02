package com.andrei1058.bedwars.support.vault;

import org.bukkit.entity.Player;

public interface Chat {

    String getPrefix(Player p);
    String getSuffix(Player p);
}
