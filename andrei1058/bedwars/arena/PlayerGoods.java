package com.andrei1058.bedwars.arena;

import org.bukkit.entity.Player;

import java.util.HashMap;

import static com.andrei1058.bedwars.Main.plugin;

/** This is where player stuff are stored so he can have them back after a game*/
public class PlayerGoods {

    private Player player;
    private int level, foodLevel;
    double health, healthscale;
    float exp;

    protected PlayerGoods(Player p ,boolean prepare){
        if (hasGoods(p)){
            plugin.getLogger().severe(p.getName()+" is already having a PlayerGoods vault :|");
            return;
        }
        this.player = p;
        this.level = p.getLevel();
        this.exp = p.getExp();
        this.health = p.getHealth();
        this.healthscale = p.getHealthScale();
        this.foodLevel = p.getFoodLevel();
        playerGoods.put(p, this);

        /** PREGATESTE PENTRU ARENA */
        if (prepare){
            p.setHealthScale(20);
            p.setHealth(20);
            p.setFoodLevel(20);
        }
    }

    /** a list where you can get PlayerGoods by player */
    private static HashMap<Player, PlayerGoods> playerGoods = new HashMap<>();

    /** check if a player has a vault */
    protected static boolean hasGoods(Player p){
        return playerGoods.containsKey(p);
    }

    /** get a player vault */
    protected static PlayerGoods getPlayerGoods(Player p){
        return playerGoods.get(p);
    }

    /** restore player */
    protected void restore(){
        player.setLevel(level);
        player.setExp(exp);
        player.setHealthScale(healthscale);
        player.setHealth(health);
        player.setFoodLevel(foodLevel);
        playerGoods.remove(player);
    }

}
