package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.arena.SBoard;

public class ScoreboardRefresh implements Runnable {
    @Override
    public void run() {
        for (SBoard sb : SBoard.getScoreboards().values()){
            sb.getHandle().refreshPlaceholders();
        }
    }
}
