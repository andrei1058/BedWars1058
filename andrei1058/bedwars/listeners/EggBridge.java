package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.events.EggBridgeThrowEvent;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.HashMap;

public class EggBridge implements Listener {

    //Active eggBridges
    private static HashMap<Egg, TeamColor> bridges = new HashMap<>();

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e) {
        if (Main.getServerType() != ServerType.BUNGEE){
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
                        if (e.isCancelled()){
                            e.setCancelled(true);
                            return;
                        }
                        bridges.put((Egg) e.getEntity(), a.getTeam(p).getColor());
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
            bridges.remove(e);
        }
    }

    /**
     * Get active egg bridges
     *
     * @since API 7
     */
    public static HashMap<Egg, TeamColor> getBridges() {
        return new HashMap<>(bridges);
    }
}
