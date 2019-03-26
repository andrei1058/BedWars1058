package com.andrei1058.bedwars.levels;

import org.bukkit.entity.Player;

public interface Level {

    /**
     * Get current level number and sign.
     */
    String getLevel(Player p);

    /**
     * Get current progress in numbers.
     * Ex: 12/15k
     */
    String getProgress(Player p);

    /**
     * Get current progress bar.
     */
    String getProgressBar(Player p);

    /**
     * Get current xp.
     */
    int getXp(Player p);
}
