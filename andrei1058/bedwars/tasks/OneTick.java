package com.andrei1058.bedwars.tasks;

import com.andrei1058.bedwars.api.events.EggBridgeBuildEvent;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.OreGenerator;
import com.andrei1058.bedwars.listeners.EggBridge;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

import static com.andrei1058.bedwars.Main.debug;
import static com.andrei1058.bedwars.Main.nms;

public class OneTick extends BukkitRunnable {
    @Override
    public void run() {

        //OneTick generators
        for (OreGenerator h : OreGenerator.getRotation()) {
            h.rotate();
        }

        //Egg bridges
        for (Map.Entry<Egg, TeamColor> e : EggBridge.getBridges().entrySet()) {
            Player pl = (Player) e.getKey().getShooter();
            Location loc = e.getKey().getLocation().clone();
            loc.getWorld().playEffect(loc, nms.eggBridge(), 5);

            if (e.getKey().isDead()) {
                EggBridge.removeEgg(e.getKey());
                continue;
            }

            Arena a = Arena.getArenaByName(e.getKey().getWorld().getName());
            if (a == null) continue;
            if (pl.getLocation().distance(e.getKey().getLocation()) > 27) continue;
            if (pl.getLocation().getY()-e.getKey().getLocation().getY() > 9) continue;

            if (pl.getLocation().distance(loc) > 4.0D) {

                Block b2 = loc.clone().subtract(0.0D, 2.0D, 0.0D).getBlock();
                if (!Misc.isBuildProtected(b2.getLocation(), a)) {
                    if (b2.getType() == Material.AIR) {
                        b2.setType(Material.WOOL);
                        nms.setBlockTeamColor(b2, e.getValue());
                        a.getPlaced().add(b2);
                        Bukkit.getPluginManager().callEvent(new EggBridgeBuildEvent(e.getValue(), a, b2));
                    }
                }

                Block b3 = loc.clone().subtract(1.0D, 2.0D, 0.0D).getBlock();
                if (!Misc.isBuildProtected(b3.getLocation(), a)) {
                    if (b3.getType() == Material.AIR) {
                        b3.setType(Material.WOOL);
                        nms.setBlockTeamColor(b3, e.getValue());
                        a.getPlaced().add(b3);
                        Bukkit.getPluginManager().callEvent(new EggBridgeBuildEvent(e.getValue(), a, b3));
                    }
                }

                Block b4 = loc.clone().subtract(0.0D, 2.0D, 1.0D).getBlock();
                if (!Misc.isBuildProtected(b4.getLocation(), a)) {
                    if (b4.getType() == Material.AIR) {
                        b4.setType(Material.WOOL);
                        nms.setBlockTeamColor(b4, e.getValue());
                        a.getPlaced().add(b4);
                        Bukkit.getPluginManager().callEvent(new EggBridgeBuildEvent(e.getValue(), a, b4));
                    }
                }
            }
        }
    }
}
