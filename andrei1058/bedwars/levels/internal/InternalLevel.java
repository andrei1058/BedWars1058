package com.andrei1058.bedwars.levels.internal;

import com.andrei1058.bedwars.levels.Level;
import org.bukkit.entity.Player;

public class InternalLevel implements Level {
    @Override
    public String getLevel(Player p) {
        return PlayerLevel.getLevelByPlayer(p.getUniqueId()).getLevelName();
    }

    @Override
    public String getProgress(Player p) {
        return null;
    }

    @Override
    public String getProgressBar(Player p) {
        return null;
    }

    @Override
    public int getXp(Player p) {
        return 0;
    }
}
