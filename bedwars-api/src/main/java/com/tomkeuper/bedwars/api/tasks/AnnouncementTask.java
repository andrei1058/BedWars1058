package com.tomkeuper.bedwars.api.tasks;

import com.tomkeuper.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public interface AnnouncementTask {
    IArena getArena();

    BukkitTask getBukkitTask();

    /**
     * Get bukkit task id.
     */
    int getTask();

    void cancel();
    void loadMessagesForPlayer(Player p, String path);
    void addMessageForPlayer(Player p, String message);
    void addMessagesForPlayer(Player p, List<String> messages);
}
