package com.andrei1058.bedwars.api.arena.generator;

import org.bukkit.entity.Player;

public interface IGenHolo {

    /**
     * Set timer hologram display text.
     */
    void setTimerName(String timer);

    /**
     * Set tier hologram display text.
     */
    void setTierName(String tier);

    /**
     * Get language iso associated with this hologram.
     */
    String getIso();

    /**
     * Hide hologram for target player if is using a different language.
     * Add your generator to an arena and it will automatically call this when required.
     *
     * @param p    The player who should not see this hologram.
     * @param lang Player's language.
     */
    void updateForPlayer(Player p, String lang);

    /**
     * Hide hologram for all players using a different language than this hologram.
     */
    void updateForAll();

    /**
     * This must be called when disabling the generator {@link IGenerator#disable()}
     */
    void destroy();
}
