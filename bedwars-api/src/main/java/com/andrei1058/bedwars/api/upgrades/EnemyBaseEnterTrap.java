package com.andrei1058.bedwars.api.upgrades;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface EnemyBaseEnterTrap {

    /**
     * Trap name msg path.
     */
    String getNameMsgPath();

    /**
     * Trap lore msg path.
     */
    String getLoreMsgPath();

    /**
     * Trap display item for man gui.
     */
    ItemStack getItemStack();

    /**
     * What to do on trigger.
     */
    void trigger(ITeam trapTeam, Player player);
}
