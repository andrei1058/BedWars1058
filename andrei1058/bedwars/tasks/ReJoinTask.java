package com.andrei1058.bedwars.tasks;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ReJoinTask extends BukkitRunnable {

    private static List<ReJoinTask> reJoinTasks = new ArrayList<>();

    private Arena arena;
    private BedWarsTeam bedWarsTeam;

    public ReJoinTask(Arena arena, BedWarsTeam bedWarsTeam){
        this.arena = arena;
        this.bedWarsTeam = bedWarsTeam;
        runTaskLater(Main.plugin, Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_REJOIN_TIME)*20);
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
        cancel();
    }

    /** Get tasks list */
    public static List<ReJoinTask> getReJoinTasks() {
        return new ArrayList<>(reJoinTasks);
    }
}
