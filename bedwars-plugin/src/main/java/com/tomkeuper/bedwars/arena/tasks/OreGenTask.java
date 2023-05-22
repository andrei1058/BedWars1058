package com.tomkeuper.bedwars.arena.tasks;

import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.arena.generator.IGenerator;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;


public class OreGenTask implements Runnable{
    Arena arena;
    private BukkitTask task;
    public OreGenTask(Arena arena) {
        this.arena = arena;
        this.task = Bukkit.getScheduler().runTaskTimer(BedWars.plugin, this, 0, 5L);
    }

    @Override
    public void run() {
        for (ITeam t : getArena().getTeams()) {
            // spawn items
            for (IGenerator o : t.getGenerators()) {
                o.spawn();
            }
        }

        /* SPAWN ITEMS */
        for (IGenerator o : getArena().getOreGenerators()) {
            o.spawn();
        }
    }

    public void cancel() {
        task.cancel();
    }
    public Arena getArena() {
        return arena;
    }

}
