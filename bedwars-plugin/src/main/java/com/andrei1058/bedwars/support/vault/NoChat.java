package com.andrei1058.bedwars.support.vault;

import org.bukkit.entity.Player;

public class NoChat implements Chat {
    @Override
    public String getPrefix(Player p) {
        return "";
    }

    @Override
    public String getSuffix(Player p) {
        return "";
    }
}
