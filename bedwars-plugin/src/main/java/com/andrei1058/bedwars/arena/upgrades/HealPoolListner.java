package com.andrei1058.bedwars.arena.upgrades;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.andrei1058.bedwars.api.events.server.ArenaDisableEvent;
import com.andrei1058.bedwars.api.events.team.TeamEliminatedEvent;
import com.andrei1058.bedwars.api.events.upgrades.UpgradeBuyEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HealPoolListner implements Listener {
    @EventHandler
    public void onTeamUpgrade(UpgradeBuyEvent e){
        if (e.getTeamUpgrade().getName().contains("heal-pool")){
            IArena a = e.getArena();
            if (a == null) return;
            ITeam bwt = a.getTeam(e.getPlayer());
            if (bwt == null) return;
            if (!HealPoolTask.exists(a, bwt)){
                new HealPoolTask(bwt);
            }
        }
    }

    @EventHandler
    public void onDisable(ArenaDisableEvent e){
        HealPoolTask.removeForArena(e.getWorldName());
    }

    @EventHandler
    public void onEnd(GameEndEvent e) {
        HealPoolTask.removeForArena(e.getArena());
    }

    @EventHandler
    public void teamDead(TeamEliminatedEvent e)
    {
        HealPoolTask.removeForTeam(e.getTeam());
    }

    @EventHandler
    public void LastLeave(PlayerLeaveArenaEvent event){
        if (event.getArena().getPlayers().isEmpty())
            HealPoolTask.removeForArena(event.getArena());
    }
}
