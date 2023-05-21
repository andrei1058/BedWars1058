package com.tomkeuper.bedwars.arena;

import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.configuration.ConfigPath;
import com.tomkeuper.bedwars.api.language.Language;
import com.tomkeuper.bedwars.api.language.Messages;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.LinkedList;

import static com.tomkeuper.bedwars.BedWars.config;

public class Announcement {

    BukkitTask task;

    private Arena arena;

    LinkedList<String> messages;

    public Announcement(Arena arena) {
        this.arena = arena;
        messages = new LinkedList<>();
    }

    public void loadMessages(Player p) {
        for (String announcement : Language.getList(p, Messages.ARENA_IN_GAME_ANNOUNCEMENT)) {
            this.messages.add(announcement);
        }
        if (this.task != null) {
            this.task.cancel();
        }
        if (!this.messages.isEmpty()) {
            this.start();
        }
    }

    public void start() {
        this.task = new BukkitRunnable() {
            int index = 0;

            public void run() {
                if (this.index >= Announcement.this.messages.size()) {
                    this.index = 0;
                }
                String announcement = Announcement.this.messages.get(this.index);
                if (arena == null) {
                    return;
                }
                for (Player player : arena.getPlayers()) {
                    if (arena.getStatus() == GameState.playing) {
                        player.sendMessage(announcement);
                    }
                }
                ++this.index;
            }
        }.runTaskTimerAsynchronously(BedWars.plugin, config.getInt(ConfigPath.GENERAL_CONFIGURATION_IN_GAME_ANNOUNCEMENT_COOLDOW) * 20L, config.getInt(ConfigPath.GENERAL_CONFIGURATION_IN_GAME_ANNOUNCEMENT_COOLDOW) *20L);
    }
}
