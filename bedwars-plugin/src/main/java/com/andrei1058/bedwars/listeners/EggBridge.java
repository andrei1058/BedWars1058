package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.Main;
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

import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
public class EggBridge implements Listener {

    //Active eggBridges
    private static HashMap<Egg, EggBridgeTask> bridges = new HashMap<>();

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e) {
        if (Main.getServerType() != ServerType.BUNGEE) {
            if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(Main.getLobbyWorld())) {
                e.setCancelled(true);
                return;
            }
        }
        if (e.getEntity() instanceof Egg) {
            if (e.getEntity().getShooter() instanceof Player) {
                Player p = (Player) e.getEntity().getShooter();
                Arena a = Arena.getArenaByPlayer(p);
                if (a != null) {
                    if (a.isPlayer(p)) {
                        EggBridgeThrowEvent event = new EggBridgeThrowEvent(p, a);
                        Bukkit.getPluginManager().callEvent(event);
                        if (e.isCancelled()) {
                            e.setCancelled(true);
                            return;
                        }
                        bridges.put((Egg) e.getEntity(), new EggBridgeTask(p, e.getEntity(), a.getTeam(p).getColor()));
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
    public static HashMap<Egg, EggBridgeTask> getBridges() {
        return new HashMap<>(bridges);
    }
}
