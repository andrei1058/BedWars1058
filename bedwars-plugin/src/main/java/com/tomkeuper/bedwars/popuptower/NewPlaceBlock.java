package com.tomkeuper.bedwars.popuptower;

import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.TeamColor;
import com.tomkeuper.bedwars.api.configuration.ConfigPath;
import com.tomkeuper.bedwars.api.region.Region;
import com.tomkeuper.bedwars.arena.Arena;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class NewPlaceBlock {
    public NewPlaceBlock(Block b, String xyz, TeamColor color, Player p, boolean ladder, int ladderdata) {
        IArena a = Arena.getArenaByPlayer(p);
        if (a == null) {
            return;
        }
        int x = Integer.parseInt(xyz.split(", ")[0]);
        int y = Integer.parseInt(xyz.split(", ")[1]);
        int z = Integer.parseInt(xyz.split(", ")[2]);
        if (b.getRelative(x, y, z).getLocation().getBlockY() >= a.getConfig().getInt(ConfigPath.ARENA_CONFIGURATION_MAX_BUILD_Y)) {
            return;
        }
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
