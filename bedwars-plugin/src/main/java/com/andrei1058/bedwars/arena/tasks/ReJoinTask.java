package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class ReJoinTask implements Runnable {

    private static List<ReJoinTask> reJoinTasks = new ArrayList<>();

    private IArena arena;
    private ITeam bedWarsTeam;
    private BukkitTask task;

    public ReJoinTask(IArena arena, ITeam bedWarsTeam) {
        this.arena = arena;
        this.bedWarsTeam = bedWarsTeam;
        task = Bukkit.getScheduler().runTaskLater(BedWars.plugin, this, BedWars.config.getInt(ConfigPath.GENERAL_CONFIGURATION_REJOIN_TIME) * 20);
    }

    @Override
    public void run() {
        if (arena == null) {
            destroy();
            return;
        }
        if (bedWarsTeam == null) {
            destroy();
            return;
        }
        if (bedWarsTeam.getMembers().isEmpty()) {
            bedWarsTeam.setBedDestroyed(true);
            destroy();
        }
    }

    /**
     * Get arena
     */
    public IArena getArena() {
        return arena;
    }

    /**
     * Destroy task
     */
    public void destroy() {
        reJoinTasks.remove(this);
        task.cancel();
    }

    /**
     * Get tasks list
     */
    public static List<ReJoinTask> getReJoinTasks() {
        return new ArrayList<>(reJoinTasks);
    }

    public void cancel() {
        task.cancel();
    }
}
