package com.andrei1058.bedwars.arena.despawnables;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class TargetListener implements Listener {

    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent e){
        if (!(e.getTarget() instanceof Player)) return;
        Arena arena = Arena.getArenaByName(e.getEntity().getWorld().getName());
        Player p = (Player) e.getTarget();
        if (arena == null) return;
        if (!arena.isPlayer(p)) {
            e.setCancelled(true);
            return;
        }
        if (arena.getStatus() != GameState.playing){
            e.setCancelled(true);
            return;
        }
        if (Main.nms.isDespawnable(e.getEntity())){
            if (arena.getTeam(p) == Main.nms.getDespawnablesList().get(e.getEntity().getUniqueId()).getTeam()){
                e.setCancelled(true);
            }
        }
    }
}
