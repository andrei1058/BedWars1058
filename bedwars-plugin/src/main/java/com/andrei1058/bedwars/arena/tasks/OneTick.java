package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.arena.OreGenerator;

public class OneTick implements Runnable {
    @Override
    public void run() {

        //OneTick generators
        for (IGenerator h : OreGenerator.getRotation()) {
            h.rotate();
        }
    }
}
