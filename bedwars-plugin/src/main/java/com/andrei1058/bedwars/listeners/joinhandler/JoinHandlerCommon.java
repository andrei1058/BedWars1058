package com.andrei1058.bedwars.listeners.joinhandler;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.entity.Player;

public class JoinHandlerCommon {

    // Used to show some details to andrei1058
    // No sensitive data
    protected static void displayCustomerDetails(Player player){
        if (player == null) return;
        //TODO IMPROVE, ADD MORE DETAILS
        if (player.getName().equalsIgnoreCase("andrei1058") || player.getName().equalsIgnoreCase("andreea1058") || player.getName().equalsIgnoreCase("Dani3l_FTW")) {
            player.sendMessage("§8[§f" + BedWars.plugin.getName() + " v" + BedWars.plugin.getDescription().getVersion() + "§8]§7§m---------------------------");
            player.sendMessage("");
            player.sendMessage("§7User ID: §f%%__USER__%%");
            player.sendMessage("§7Download ID: §f%%__NONCE__%%");
            player.sendMessage("");
            player.sendMessage("§8[§f" + BedWars.plugin.getName() + "§8]§7§m---------------------------");
        }
    }

}
