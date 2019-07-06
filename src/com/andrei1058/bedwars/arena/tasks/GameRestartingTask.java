package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.*;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

import static com.andrei1058.bedwars.Main.config;

public class GameRestartingTask implements Runnable {

    private Arena arena;
    private int restarting = 15;
    private BukkitTask task;
    private String name;

    public GameRestartingTask(Arena arena) {
        this.arena = arena;
        this.name = arena.getWorldName();
        task = Bukkit.getScheduler().runTaskTimer(Main.plugin, this, 0, 20L);
    }

    /**
     * Get task ID
     */
    public int getTask() {
        return task.getTaskId();
    }

    public Arena getArena() {
        return arena;
    }

    @Override
    public void run() {

        restarting--;

        if (getArena().getPlayers().isEmpty() && restarting > 9) restarting = 9;
        if (restarting == 8) {
            for (Player on : new ArrayList<>(getArena().getPlayers())) {
                getArena().removePlayer(on, Main.getServerType() == ServerType.BUNGEE);
            }
            for (Player on : new ArrayList<>(getArena().getSpectators())) {
                getArena().removeSpectator(on, Main.getServerType() == ServerType.BUNGEE);
            }
        } else if (restarting == 6) {
            ShopHolo.clearForArena(getArena());
            for (Entity e : getArena().getWorld().getEntities()) {
                if (e.getType() == EntityType.PLAYER) {
                    Player p = (Player) e;
                    Misc.moveToLobbyOrKick(p);
                    if (getArena().isSpectator(p)) getArena().removeSpectator(p, false);
                    if (getArena().isPlayer(p)) getArena().removePlayer(p, false);
                }
            }
            for (OreGenerator eg : getArena().getOreGenerators()) {
                eg.disable();
            }
            for (BedWarsTeam bwt : getArena().getTeams()) {
                bwt.getBeds().clear();
            }
        } else if (restarting == 3) {
            getArena().restart();
        } else if (restarting == 0) {
            if (Main.getServerType() == ServerType.BUNGEE) {
                Arena.setGamesBeforeRestart(Arena.getGamesBeforeRestart() - 1);
                if (Arena.getGamesBeforeRestart() == 0) {
                    Bukkit.getLogger().info("Dispatching command: " + config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD));
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD));
                } else {
                    if (Arena.getGamesBeforeRestart() != -1) {
                        Arena.setGamesBeforeRestart(Arena.getGamesBeforeRestart() - 1);
                    }
                    new Arena(name, null);
                }
            } else {
                new Arena(name, null);
            }
            task.cancel();
            arena = null;
        }
    }

    public void cancel() {
        task.cancel();
    }

}
