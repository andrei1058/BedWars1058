package com.andrei1058.bedwars.arena.upgrades;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.andrei1058.bedwars.BedWars.config;
import static com.andrei1058.bedwars.BedWars.plugin;

public class HealPoolTask extends BukkitRunnable {

    private ITeam bwt;
    private int maxX, minX, maxY, minY, maxZ, minZ;
    private IArena arena;
    private Random r = new Random();
    private Location l;

    private static List<HealPoolTask> healPoolTasks = new ArrayList<>();

    public HealPoolTask(ITeam bwt){
        this.bwt = bwt;
        if (bwt == null){
            cancel();
            removeForTeam(this.bwt);
            return;
        }
        int radius = bwt.getArena().getConfig().getInt(ConfigPath.ARENA_ISLAND_RADIUS);
        this.maxX = Math.max(bwt.getSpawn().clone().add(radius, 0 , 0).getBlockX(),bwt.getSpawn().clone().subtract(radius, 0, 0).getBlockX());
        this.minX = Math.min(bwt.getSpawn().clone().add(radius, 0, 0).getBlockX(), bwt.getSpawn().clone().subtract(radius, 0, 0).getBlockX());
        this.maxY = Math.max(bwt.getSpawn().clone().add(0, radius, 0).getBlockY(), bwt.getSpawn().clone().subtract(0, radius, 0).getBlockY());
        this.minY = Math.min(bwt.getSpawn().clone().add(0, radius, 0).getBlockY(), bwt.getSpawn().clone().subtract(0, radius, 0).getBlockY());
        this.maxZ = Math.max(bwt.getSpawn().clone().add(0, 0, radius).getBlockZ(), bwt.getSpawn().clone().subtract(0, 0, radius).getBlockZ());
        this.minZ = Math.min(bwt.getSpawn().clone().add(0, 0, radius).getBlockZ(), bwt.getSpawn().clone().subtract(0, 0, radius).getBlockZ());
        this.arena = bwt.getArena();
        this.runTaskTimerAsynchronously(plugin, 0, 80L);
        healPoolTasks.add(this);
    }

    @Override
    public void run(){
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    l = new Location(bwt.getSpawn().getWorld(), x, y, z);
                    if (l.getBlock().getType() != Material.AIR) continue;
                    int chance = r.nextInt(5);
                    if (chance == 0) {
                        if (config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_HEAL_POOL_SEEN_TEAM_ONLY)) {
                            for (Player p : bwt.getMembers()) {
                                BedWars.nms.playEffect(p, l);
                            }
                        }
                        else
                        {
                            for (Player p : arena.getPlayers()) {
                                BedWars.nms.playEffect(p, l);
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean exists(IArena arena, ITeam bwt){
        if (healPoolTasks.isEmpty()) return false;
        for (HealPoolTask hpt : healPoolTasks) {
            if (hpt.getArena() == arena && hpt.getBwt() == bwt) return true;
        }
        return false;
    }
    public static void removeForArena(IArena a){
        if (healPoolTasks.isEmpty()) return;
        for (HealPoolTask hpt: healPoolTasks) {
            if (hpt.getArena().equals(a)){
                healPoolTasks.remove(hpt);
                hpt.cancel();
                break;
            }
        }
    }

    public  static void removeForArena(String a){
        if (healPoolTasks.isEmpty()) return;
        for (HealPoolTask hpt: healPoolTasks) {
            if (hpt.getArena().getWorldName().equals(a)){
                healPoolTasks.remove(hpt);
                hpt.cancel();
                break;
            }
        }
    }

    public  static void removeForTeam(ITeam team){
        if (healPoolTasks.isEmpty()) return;
        for (HealPoolTask hpt: healPoolTasks) {
            if (hpt.getBwt().equals(team)){
                healPoolTasks.remove(hpt);
                hpt.cancel();
                break;
            }
        }
    }

    public ITeam getBwt() {return bwt;}

    public IArena getArena() {return arena;}
}
