package com.andrei1058.bedwars.sidebar;

public class SidebarPlaceholderRefresh implements Runnable {
    @Override
    public void run() {
        for (BedWarsScoreboard sb : BedWarsScoreboard.getScoreboards().values()){
            sb.getHandle().refreshPlaceholders();
            //TODO: move to their own task
            //sb.getHandle().refreshHealthAnimation();
        }
    }
}
