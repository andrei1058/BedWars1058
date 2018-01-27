package com.andrei1058.bedwars.support.levels;

import org.bukkit.entity.Player;

public class NoLevel implements Level {
    @Override
    public String getLevel(Player p) {
        return "";
    }
}
