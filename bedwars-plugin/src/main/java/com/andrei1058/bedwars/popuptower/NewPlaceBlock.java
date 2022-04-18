package com.andrei1058.bedwars.popuptower;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.region.Region;
import com.andrei1058.bedwars.api.server.VersionSupport;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class NewPlaceBlock {
    public NewPlaceBlock(Block b, String xyz, TeamColor color, Player p, boolean ladder, int ladderdata) {
        int x = Integer.parseInt(xyz.split(", ")[0]);
        int y = Integer.parseInt(xyz.split(", ")[1]);
        int z = Integer.parseInt(xyz.split(", ")[2]);
        if (b.getRelative(x, y, z).getType().equals(Material.AIR)) {
            Iterator var10 = Arena.getArenaByPlayer(p).getRegionsList().iterator();
            while(var10.hasNext()) {
                Region r = (Region)var10.next();
                if (r.isInRegion(b.getRelative(x, y, z).getLocation())) {
                    return;
                }
            }
            if (!ladder) {
                if (BedWars.getAPI().getVersionSupport().getVersion() >= 7) {
                    b.getRelative(x, y, z).setType(color.woolMaterial());
                    Arena.getArenaByPlayer(p).addPlacedBlock(b.getRelative(x, y, z));
                } else {
                    b.getRelative(x, y, z).setType(Material.WOOL);
                    BedWars.getAPI().getVersionSupport().setBlockTeamColor(b.getRelative(x, y, z), color);
                    Arena.getArenaByPlayer(p).addPlacedBlock(b.getRelative(x, y, z));
                }
            } else {
                b.getRelative(x, y, z).setType(Material.LADDER);
                if (BedWars.getAPI().getVersionSupport().getVersion() < 5) //1.12.2 and lower
                    b.getRelative(x, y, z).setData((byte)ladderdata);
                Arena.getArenaByPlayer(p).addPlacedBlock(b.getRelative(x, y, z));
            }
        }

    }
}
