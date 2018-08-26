package com.andrei1058.bedwars.arena.despawnables;

import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.HashMap;
import java.util.UUID;

import static com.andrei1058.bedwars.Main.nms;

public class TargetListener implements Listener {

    public static HashMap<UUID, String> owningTeam = new HashMap<>();

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
        if (!owningTeam.containsKey(e.getEntity().getUniqueId())) return;
        if (arena.getTeam(p).getName().equals(owningTeam.get(e.getEntity().getUniqueId()))){
            e.setCancelled(true);
            return;
        }
    }
}
