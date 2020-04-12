package com.andrei1058.bedwars.sidebar;

public class SidebarLifeRefresh implements Runnable {
    @Override
    public void run() {
        for (BedWarsScoreboard scoreboard : BedWarsScoreboard.getScoreboards().values()){
            if (scoreboard.getArena() != null){
                scoreboard.getHandle().refreshHealthAnimation();
            }
        }
    }
}
