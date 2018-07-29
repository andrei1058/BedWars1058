package com.andrei1058.bedwars.tasks;

import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.OreGenerator;
import com.andrei1058.bedwars.listeners.EggBridge;
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
            if (e.getKey().isDead()) {
                //debug("OneTick> EGG is dead! Removed from task.");
                EggBridge.removeEgg(e.getKey());
                continue;
            }
            Arena a = Arena.getArenaByName(e.getKey().getWorld().getName());
            if (a == null) continue;
            //debug("OneTick> Egg's arena is not null.");
            //if (pl.getLocation().distance(e.getKey().getLocation()) > 11) continue;
            //debug("OneTick> Distance < 11. OK.");
            if (pl.getLocation().getY()-e.getKey().getLocation().getY() > 5) continue;
            if (e.getKey().getLocation().getBlockZ() - e.getKey().getWorld().getWorldBorder().getSize() > e.getKey().getWorld().getWorldBorder().getCenter().getBlockZ()) continue;
            if (e.getKey().getLocation().getBlockX() - e.getKey().getWorld().getWorldBorder().getSize() > e.getKey().getWorld().getWorldBorder().getCenter().getBlockX()) continue;
            //debug("OneTick> y < 5. OK.");
            if (pl.getLocation().distance(loc) > 4.0D) {
                Block b1 = loc.clone().subtract(0.0D, 2.0D, 0.0D).getBlock();
                if (b1.getType() == Material.AIR) {
                    b1.setType(Material.WOOL);
                    nms.setBlockTeamColor(b1, e.getValue());
                    a.getPlaced().add(b1);
                    pl.playEffect(b1.getLocation(), nms.eggBridge(), 5);
                }

                Block b2 = loc.clone().subtract(0.0D, 2.0D, 0.0D).subtract(0.0D, 0.0D, 1.0D).getBlock();
                if (b2.getType() == Material.AIR) {
                    b2.setType(Material.WOOL);
                    nms.setBlockTeamColor(b2, e.getValue());
                    a.getPlaced().add(b2);
                }
                Block b3 = loc.clone().subtract(0.0D, 2.0D, 0.0D).subtract(1.0D, 0.0D, 0.0D).getBlock();
                if (b3.getType() == Material.AIR) {
                    b3.setType(Material.WOOL);
                    nms.setBlockTeamColor(b3, e.getValue());
                    a.getPlaced().add(b3);
                }
                Block b4 = loc.clone().subtract(0.0D, 2.0D, 0.0D).add(0.0D, 0.0D, 1.0D).getBlock();
                if (b4.getType() == Material.AIR) {
                    b4.setType(Material.WOOL);
                    nms.setBlockTeamColor(b4, e.getValue());
                    a.getPlaced().add(b4);
                }
                Block b5 = loc.clone().subtract(0.0D, 2.0D, 0.0D).add(1.0D, 0.0D, 0.0D).getBlock();
                if (b5.getType() == Material.AIR) {
                    b5.setType(Material.WOOL);
                    nms.setBlockTeamColor(b5, e.getValue());
                    a.getPlaced().add(b5);
                }
            }
        }
    }
}
