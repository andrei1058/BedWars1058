package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class ReJoinTask implements Runnable {

    private static List<ReJoinTask> reJoinTasks = new ArrayList<>();

    private Arena arena;
    private BedWarsTeam bedWarsTeam;
    private BukkitTask task;

    public ReJoinTask(Arena arena, BedWarsTeam bedWarsTeam){
        this.arena = arena;
        this.bedWarsTeam = bedWarsTeam;
        task = Bukkit.getScheduler().runTaskLater(Main.plugin, this, Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_REJOIN_TIME) * 20);
    }

    @Override
    public void run() {
        if (arena == null){
            destroy();
            return;
        }
        if (bedWarsTeam == null){
            destroy();
            return;
        }
        if (bedWarsTeam.getMembers().isEmpty()){
            bedWarsTeam.setBedDestroyed(true);
            destroy();
        }
    }

    /** Get arena */
    public Arena getArena() {
        return arena;
    }

    /** Destroy task */
    public void destroy(){
        reJoinTasks.remove(this);
        task.cancel();
    }

    /** Get tasks list */
    public static List<ReJoinTask> getReJoinTasks() {
        return new ArrayList<>(reJoinTasks);
    }

    public void cancel(){
        task.cancel();
    }
}
