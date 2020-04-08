package com.andrei1058.bedwars.sidebar;

public class ScoreboardRefresh implements Runnable {
    @Override
    public void run() {
        for (BedWarsScoreboard sb : BedWarsScoreboard.getScoreboards().values()){
            sb.getHandle().refreshPlaceholders();
            sb.getHandle().playerListRefreshAnimation();
            sb.getHandle().refreshHealthAnimation();
        }
    }
}
