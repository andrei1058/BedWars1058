package com.andrei1058.bedwars.popuptower.impl;

import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.popuptower.AbstractTower;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class TowerNorth extends AbstractTower {

    public TowerNorth(Location location, Block chest, TeamColor color, Player player) {
        super(location, chest, color, player);
    }

    @Override
    protected List<String> getTowerCoordinates() {
        return Arrays.asList(
                // zero layer
                "-1, 0, -2", "-2, 0, -1", "-2, 0, 0", "-1, 0, 1", "0, 0, 1",
                "1, 0, 1", "2, 0, 0", "2, 0, -1", "1, 0, -2", "0, 0, 0, ladder2",

                // first layer
                "-1, 1, -2", "-2, 1, -1", "-2, 1, 0", "-1, 1, 1", "0, 1, 1",
                "1, 1, 1", "2, 1, 0", "2, 1, -1", "1, 1, -2", "0, 1, 0, ladder2",

                // second layer
                "-1, 2, -2", "-2, 2, -1", "-2, 2, 0", "-1, 2, 1", "0, 2, 1",
                "1, 2, 1", "2, 2, 0", "2, 2, -1", "1, 2, -2", "0, 2, 0, ladder2",

                // third layer
                "0, 3, -2", "-1, 3, -2", "-2, 3, -1", "-2, 3, 0", "-1, 3, 1",
                "0, 3, 1", "1, 3, 1", "2, 3, 0", "2, 3, -1", "1, 3, -2", "0, 3, 0, ladder2",

                // fourth layer
                "0, 4, -2", "-1, 4, -2", "-2, 4, -1", "-2, 4, 0", "-1, 4, 1",
                "0, 4, 1", "1, 4, 1", "2, 4, 0", "2, 4, -1", "1, 4, -2", "0, 4, 0, ladder2",

                // platform layer
                "-2, 5, 1", "-2, 5, 0", "-2, 5, -1", "-2, 5, -2",
                "-1, 5, 1", "-1, 5, 0", "-1, 5, -1", "-1, 5, -2",
                "0, 5, 1", "0, 5, -1", "0, 5, -2", "1, 5, 1",
                "0, 5, 0, ladder2", "1, 5, 0", "1, 5, -1", "1, 5, -2",
                "2, 5, 1", "2, 5, 0", "2, 5, -1", "2, 5, -2",

                "-3, 5, -2", "-3, 6, -2", "-3, 7, -2", "-3, 6, -1", "-3, 6, 0",
                "-3, 5, 1", "-3, 6, 1", "-3, 7, 1",
                "-2, 5, 2", "-2, 6, 2", "-2, 7, 2", "-1, 6, 2",
                "0, 5, 2", "0, 6, 2", "0, 7, 2", "1, 6, 2",
                "2, 5, 2", "2, 6, 2", "2, 7, 2",
                "3, 5, -2", "3, 6, -2", "3, 7, -2", "3, 6, -1", "3, 6, 0",
                "3, 5, 1", "3, 6, 1", "3, 7, 1",
                "-2, 5, -3", "-2, 6, -3", "-2, 7, -3", "-1, 6, -3",
                "0, 5, -3", "0, 6, -3", "0, 7, -3", "1, 6, -3",
                "2, 5, -3", "2, 6, -3", "2, 7, -3"
        );
    }
}