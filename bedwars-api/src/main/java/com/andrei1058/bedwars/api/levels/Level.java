package com.andrei1058.bedwars.api.levels;

import com.andrei1058.bedwars.api.events.player.PlayerXpGainEvent;
import org.bukkit.entity.Player;

public interface Level {

    /**
     * @return current player level formatted as string.
     */
    String getLevel(Player p);


    /**
     * @return current player level as number.
     */
    int getPlayerLevel(Player p);

    /**
     * Get required xp as string.
     * 2000 - 2k
     *
     * @return required xp for next level.
     */
    String getRequiredXpFormatted(Player p);

    /**
     * @return current progress bar.
     */
    String getProgressBar(Player p);

    /**
     * @return current xp.
     */
    int getCurrentXp(Player p);

    /**
     * @return current xp formatted.
     */
    String getCurrentXpFormatted(Player p);

    /**
     * @return required xp
     */
    int getRequiredXp(Player p);

    /**
     * Add some xp to target player.
     */
    void addXp(Player player, int xp, PlayerXpGainEvent.XpSource source);

    /**
     * Set player xp.
     */
    void setXp(Player player, int currentXp);

    /**
     * Set player level.
     */
    void setLevel(Player player, int level);
}
