package com.andrei1058.bedwars.popuptower;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.region.Region;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class NewPlaceBlock {
    public NewPlaceBlock(Block b, String xyz, TeamColor color, Player p, boolean ladder, int ladderdata) {
        int x = Integer.parseInt(xyz.split(", ")[0]);
        int y = Integer.parseInt(xyz.split(", ")[1]);
        int z = Integer.parseInt(xyz.split(", ")[2]);
        if (b.getRelative(x, y, z).getType().equals(Material.AIR)) {
            for (Region r : Arena.getArenaByPlayer(p).getRegionsList())
                if (r.isInRegion(b.getRelative(x, y, z).getLocation()))
                    return;

            if (!ladder)
                BedWars.nms.placeTowerBlocks(b, Arena.getArenaByPlayer(p), color, x, y, z);
            else
                BedWars.nms.placeLadder(b, x, y, z, Arena.getArenaByPlayer(p), ladderdata);
        }

    }
}
