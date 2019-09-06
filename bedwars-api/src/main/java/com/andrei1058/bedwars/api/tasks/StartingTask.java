package com.andrei1058.bedwars.api.tasks;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.scheduler.BukkitTask;

public interface StartingTask {

    int getCountdown();

    void setCountdown(int countdown);

    IArena getArena();

    int getTask();

    BukkitTask getBukkitTask();

    void cancel();
}
