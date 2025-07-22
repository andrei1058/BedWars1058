package com.andrei1058.bedwars.popuptower.impl;

import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.popuptower.AbstractTower;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class TowerEast extends AbstractTower {

    public TowerEast(Location location, Block chest, TeamColor color, Player player) {
        super(location, chest, color, player);
    }

    @Override
    protected List<String> getTowerCoordinates() {
        return Arrays.asList(
                // zero layer
                "2, 0, -1", "1, 0, -2", "0, 0, -2", "-1, 0, -1", "-1, 0, 0",
                "-1, 0, 1", "0, 0, 2", "1, 0, 2", "2, 0, 1", "0, 0, 0, ladder5",

                // first layer
                "2, 1, -1", "1, 1, -2", "0, 1, -2", "-1, 1, -1", "-1, 1, 0",
                "-1, 1, 1", "0, 1, 2", "1, 1, 2", "2, 1, 1", "0, 1, 0, ladder5",

                // second layer
                "2, 2, -1", "1, 2, -2", "0, 2, -2", "-1, 2, -1", "-1, 2, 0",
                "-1, 2, 1", "0, 2, 2", "1, 2, 2", "2, 2, 1", "0, 2, 0, ladder5",

                // third layer
                "2, 3, 0", "2, 3, -1", "1, 3, -2", "0, 3, -2", "-1, 3, -1",
                "-1, 3, 0", "-1, 3, 1", "0, 3, 2", "1, 3, 2", "2, 3, 1", "0, 3, 0, ladder5",

                // fourth layer
                "2, 4, 0", "2, 4, -1", "1, 4, -2", "0, 4, -2", "-1, 4, -1",
                "-1, 4, 0", "-1, 4, 1", "0, 4, 2", "1, 4, 2", "2, 4, 1", "0, 4, 0, ladder5",

                // platform layer
                "-1, 5, -2", "0, 5, -2", "1, 5, -2", "2, 5, -2",
                "-1, 5, -1", "0, 5, -1", "1, 5, -1", "2, 5, -1",
                "-1, 5, 0", "1, 5, 0", "2, 5, 0",
                "-1, 5, 1", "0, 5, 0, ladder5", "0, 5, 1", "1, 5, 1", "2, 5, 1",
                "-1, 5, 2", "0, 5, 2", "1, 5, 2", "2, 5, 2",


                "2, 5, -3", "2, 6, -3", "2, 7, -3",
                "1, 6, -3", "0, 6, -3",
                "-1, 5, -3", "-1, 6, -3", "-1, 7, -3",
                "-2, 5, -2", "-2, 6, -2", "-2, 7, -2", "-2, 6, -1",
                "-2, 5, 0", "-2, 6, 0", "-2, 7, 0", "-2, 6, 1",
                "-2, 5, 2", "-2, 6, 2", "-2, 7, 2",
                "2, 5, 3", "2, 6, 3", "2, 7, 3",
                "1, 6, 3", "0, 6, 3",
                "-1, 5, 3", "-1, 6, 3", "-1, 7, 3",
                "3, 5, -2", "3, 6, -2", "3, 7, -2", "3, 6, -1",
                "3, 5, 0", "3, 6, 0", "3, 7, 0", "3, 6, 1",
                "3, 5, 2", "3, 6, 2", "3, 7, 2"
        );
    }
}