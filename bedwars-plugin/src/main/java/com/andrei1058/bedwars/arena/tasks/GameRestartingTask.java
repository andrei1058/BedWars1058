package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.api.arena.shop.ShopHolo;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.tasks.RestartingTask;
import com.andrei1058.bedwars.arena.*;
import com.andrei1058.bedwars.configuration.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class GameRestartingTask implements Runnable, RestartingTask {

    private Arena arena;
    private int restarting = 13;
    private BukkitTask task;

    public GameRestartingTask(Arena arena) {
        this.arena = arena;
        task = Bukkit.getScheduler().runTaskTimer(BedWars.plugin, this, 0, 20L);
        Sounds.playSound("game-end", arena.getPlayers());
        Sounds.playSound("game-end", arena.getSpectators());
    }

    /**
     * Get task ID
     */
    public int getTask() {
        return task.getTaskId();
    }

    @Override
    public int getRestarting() {
        return restarting;
    }

    public Arena getArena() {
        return arena;
    }

    @Override
    public BukkitTask getBukkitTask() {
        return task;
    }

    @Override
    public void run() {

        restarting--;

        if (getArena().getPlayers().isEmpty() && restarting > 9) restarting = 9;
        if (restarting == 8) {
            for (Player on : new ArrayList<>(getArena().getPlayers())) {
                getArena().removePlayer(on, BedWars.getServerType() == ServerType.BUNGEE);
            }
            for (Player on : new ArrayList<>(getArena().getSpectators())) {
                getArena().removeSpectator(on, BedWars.getServerType() == ServerType.BUNGEE);
            }
        } else if (restarting == 7) {
            ShopHolo.clearForArena(getArena());
            for (Entity e : getArena().getWorld().getEntities()) {
                if (e.getType() == EntityType.PLAYER) {
                    Player p = (Player) e;
                    Misc.moveToLobbyOrKick(p);
                    if (getArena().isSpectator(p)) getArena().removeSpectator(p, false);
                    if (getArena().isPlayer(p)) getArena().removePlayer(p, false);
                }
            }
            for (IGenerator eg : getArena().getOreGenerators()) {
                eg.disable();
            }
            for (ITeam t : getArena().getTeams()) {
                for (IGenerator eg : t.getGenerators()) {
                    eg.disable();
                }
            }
        } else if (restarting == 6) {
            getArena().restart();
            task.cancel();
            arena = null;
        }
    }

    public void cancel() {
        task.cancel();
    }

}
