package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GameEnd implements Listener {

    @EventHandler
    public void stopPickUP(GameEndEvent event){
        if (!event.getAliveWinners().isEmpty()){
            for (UUID p : event.getAliveWinners()) {
                Bukkit.getPlayer(p).getInventory().clear();
            }
        }
        else if (event.getArena().getPlayers().isEmpty())
            return;  //no one in arena no need to do anything

        World game = event.getArena().getWorld();
        for (Entity item : game.getEntities()) {
            if ((item instanceof Item) ||(item instanceof ItemStack))
                item.remove();
        }
    }
}
