package com.andrei1058.bedwars.sidebar;

import org.bukkit.entity.Player;

public class SidebarLifeRefresh implements Runnable {
    @Override
    public void run() {
        for (BedWarsScoreboard scoreboard : BedWarsScoreboard.getScoreboards().values()){
            if (scoreboard.getArena() != null){
                scoreboard.getHandle().refreshHealthAnimation();
                for (Player player : scoreboard.getArena().getPlayers()){
                    scoreboard.getHandle().refreshHealth(player, (int) Math.ceil(player.getHealth()));
                }
            }
        }
    }
}
