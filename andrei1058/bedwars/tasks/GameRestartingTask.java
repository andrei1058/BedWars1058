package com.andrei1058.bedwars.tasks;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.*;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

import static com.andrei1058.bedwars.Main.config;
import static com.andrei1058.bedwars.Main.getServerType;

public class GameRestartingTask extends BukkitRunnable {

    private Arena arena;
    private int restarting = 15;

    public GameRestartingTask(Arena arena){
        this.arena = arena;
        this.runTaskTimer(Main.plugin, 0, 20L);
    }

    /**
     * Get task ID
     */
    public int getTask() {
        return this.getTaskId();
    }

    public Arena getArena() {
        return arena;
    }

    @Override
    public void run() {
        for (Player p : new ArrayList<>(getArena().getPlayers())) {
            for (int i = 0; i < 2; i++) {
                Misc.launchFirework(p);
            }
        }
        if (getArena().getPlayers().isEmpty() && restarting > 9) restarting = 9;
        restarting--;
        if (restarting == 8) {
            for (Player on : new ArrayList<>(getArena().getPlayers())) {
                getArena().removePlayer(on, Main.getServerType() == ServerType.BUNGEE);
            }
            for (Player on : new ArrayList<>(getArena().getSpectators())) {
                getArena().removeSpectator(on, Main.getServerType() == ServerType.BUNGEE);
            }
        } else if (restarting == 6){
            if (getServerType() == ServerType.BUNGEE) {
                if (Arena.getGamesBeforeRestart() <= 0) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD));
                    Bukkit.getServer().spigot().restart();
                    Arena.setGamesBeforeRestart(Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_MODE_GAMES_BEFORE_RESTART));
                    return;
                }
                Arena.setGamesBeforeRestart(Arena.getGamesBeforeRestart()-1);
            } else {
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
                for (BedWarsTeam.BedHolo bh : BedWarsTeam.getBeds().values()){
                    if (bh.getArena() == getArena()){
                        bh.destroy();
                    }
                }
                Bukkit.unloadWorld(getArena().getWorld(), false);
            }
        } else if (restarting == 3){
            World world = Bukkit.createWorld(new WorldCreator(getArena().getWorldName()));
            world.setAutoSave(false);
            getArena().setWorld(world);

            /* CLEAR HOLOGRAMS FROM MAP */
            for (Entity e : world.getEntities()) {
                if (e.getType() == EntityType.ARMOR_STAND) {
                    if (!((ArmorStand)e).isVisible()) e.remove();
                }
            }
        }
        if (restarting == 0) {
            getArena().restart();
            cancel();
        }
    }

}
