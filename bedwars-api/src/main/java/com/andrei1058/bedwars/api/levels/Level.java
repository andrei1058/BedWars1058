package com.andrei1058.bedwars.api.levels;

import org.bukkit.entity.Player;

public interface Level {

    /**
     * Get current player level formatted as string.
     */
    String getLevel(Player p);


    /**
     * Get current player level as number.
     */
    int getPlayerLevel(Player p);

    /**
     * Get required xp as string.
     * 2000 - 2k
     */
    String getRequiredXpFormatted(Player p);

    /**
     * Get current progress bar.
     */
    String getProgressBar(Player p);

    /**
     * Get current xp.
     */
    int getCurrentXp(Player p);

    /**
     * Get current xp formatted.
     */
    String getCurrentXpFormatted(Player p);

    /**
     * Get required xp
     */
    int getRequiredXp(Player p);
}
