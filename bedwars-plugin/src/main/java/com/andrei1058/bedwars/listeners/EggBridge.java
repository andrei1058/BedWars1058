package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.gameplay.EggBridgeThrowEvent;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.tasks.EggBridgeTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class EggBridge implements Listener {

    //Active eggBridges
    private static HashMap<Egg, EggBridgeTask> bridges = new HashMap<>();

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event) {
        if (BedWars.getServerType() == ServerType.MULTIARENA) {
            if (event.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(BedWars.getLobbyWorld())) {
                event.setCancelled(true);
                return;
            }
        }
        if (event.getEntity() instanceof Egg) {
            Egg projectile = (Egg) event.getEntity();
            if (projectile.getShooter() instanceof Player) {
                Player shooter = (Player) projectile.getShooter();
                IArena arena = Arena.getArenaByPlayer(shooter);
                if (arena != null) {
                    if (arena.isPlayer(shooter)) {
                        EggBridgeThrowEvent throwEvent = new EggBridgeThrowEvent(shooter, arena);
                        Bukkit.getPluginManager().callEvent(throwEvent);
                        if (event.isCancelled()) {
                            event.setCancelled(true);
                            return;
                        }
                        bridges.put(projectile, new EggBridgeTask(shooter, projectile, arena.getTeam(shooter).getColor()));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Egg) {
            removeEgg((Egg) e.getEntity());
        }
    }

    /**
     * Remove an egg from the active eggs list
     *
     * @since API 7
     */
    public static void removeEgg(Egg e) {
        if (bridges.containsKey(e)) {
            if (bridges.get(e) != null) {
                bridges.get(e).cancel();
            }
            bridges.remove(e);
        }
    }

    /**
     * Get active egg bridges.
     * Modified  in api 11
     *
     * @since API 11
     */
    public static Map<Egg, EggBridgeTask> getBridges() {
        return Collections.unmodifiableMap(bridges);
    }
}
