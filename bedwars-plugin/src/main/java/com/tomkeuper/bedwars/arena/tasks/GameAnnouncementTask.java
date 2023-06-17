package com.tomkeuper.bedwars.arena.tasks;

import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.configuration.ConfigPath;
import com.tomkeuper.bedwars.api.language.Language;
import com.tomkeuper.bedwars.api.language.Messages;
import com.tomkeuper.bedwars.api.tasks.AnnouncementTask;
import com.tomkeuper.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

import static com.tomkeuper.bedwars.BedWars.config;

public class GameAnnouncementTask implements Runnable, AnnouncementTask {

    BukkitTask task;

    private Arena arena;

    LinkedHashMap<Player, List<String>> messages;

    int index = 0;

    public GameAnnouncementTask(Arena arena) {
        this.arena = arena;
        messages = new LinkedHashMap<>();
        for (Player player: arena.getPlayers()) {
            loadMessagesForPlayer(player, Messages.ARENA_IN_GAME_ANNOUNCEMENT);
        }
        for (Player player: arena.getSpectators()) {
            loadMessagesForPlayer(player, Messages.ARENA_IN_GAME_ANNOUNCEMENT);
        }
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(BedWars.plugin, this, config.getInt(ConfigPath.GENERAL_CONFIGURATION_IN_GAME_ANNOUNCEMENT_COOLDOW) * 20L, config.getInt(ConfigPath.GENERAL_CONFIGURATION_IN_GAME_ANNOUNCEMENT_COOLDOW) *20L);
    }

    @Override
    public void loadMessagesForPlayer(Player p, String path) {
        if (this.messages.containsKey(p)) this.messages.get(p).addAll(Language.getList(p, path));
        this.messages.put(p, Language.getList(p, path));
    }

    @Override
    public void addMessageForPlayer(Player p, String message){
        if (this.messages.containsKey(p))  this.messages.get(p).add(message);
        this.messages.put(p, Collections.singletonList(message));
    }

    @Override
    public void addMessagesForPlayer(Player p, List<String> messages){
        if (this.messages.containsKey(p))  this.messages.get(p).addAll(messages);
        this.messages.put(p, messages);
    }
    public void run() {
//                if (this.index >= GameAnnouncementTask.this.messages.size()) {
//                    this.index = 0;
//                }
        if (arena == null) {
            cancel();
        }
        for (Player player : arena.getPlayers()) {
            if (arena.getStatus() == GameState.playing) {
                player.sendMessage(messages.get(player).get(index % messages.get(player).size()));
            }
        }
        ++this.index;
    }

    @Override
    public IArena getArena() {
        return arena;
    }

    @Override
    public BukkitTask getBukkitTask() {
        return task;
    }

    @Override
    public int getTask() {
        return task.getTaskId();
    }

    @Override
    public void cancel() {
        task.cancel();
    }
}
