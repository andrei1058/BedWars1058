package com.andrei1058.bedwars.arena.despawnables;

import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import static com.andrei1058.bedwars.Main.nms;

public class TargetListener implements Listener {

    public static final String NBTTAG_OWING_TEAM_KEY = "OwningTeam";

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
        if (!nms.hasTag(e.getEntity(), NBTTAG_OWING_TEAM_KEY)) return;
        if (arena.getTeam(p).getName().equals(nms.getStringValue(e.getEntity(), NBTTAG_OWING_TEAM_KEY))){
            e.setCancelled(true);
            return;
        }
    }
}
