package com.andrei1058.bedwars.sidebar;

public class SidebarListRefresh implements Runnable {
    @Override
    public void run() {
        for (BedWarsScoreboard scoreboard : BedWarsScoreboard.getScoreboards().values()){
            scoreboard.getHandle().playerListRefreshAnimation();
        }
    }
}
