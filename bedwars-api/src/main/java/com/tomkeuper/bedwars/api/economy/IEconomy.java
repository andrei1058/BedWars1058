package com.tomkeuper.bedwars.api.economy;

import org.bukkit.entity.Player;

/**
 * Get Economy Methods
 */
public interface IEconomy {

    /**
     * Check if economy is enabled
     */
    boolean isEconomy();

    /**
     * Get player money balance
     * @param p player from which to get the economy balance
     */
    double getMoney(Player p);

    /**
     * give to player money
     * @param p player from which to get the economy balance
     * @param money money amount to give
     */
    void giveMoney(Player p, double money);

    /**
     * Get player money from balance to buy an item shop
     * @param p player from which to get the money
     * @param cost money amount to take
     */
    void buyAction(Player p, double cost);

}
