package com.andrei1058.bedwars.tasks;

import com.andrei1058.bedwars.arena.OreGenerator;

public class OneTick implements Runnable {
    @Override
    public void run() {

        //OneTick generators
        for (OreGenerator h : OreGenerator.getRotation()) {
            h.rotate();
        }
    }
}
