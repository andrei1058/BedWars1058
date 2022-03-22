package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.team.TeamEliminatedEvent;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class TeamEliminated implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player victim = event.getEntity();
        IArena a = Arena.getArenaByPlayer(victim);
        if ((BedWars.getServerType() == ServerType.MULTIARENA && BedWars.getLobbyWorld().equals(event.getEntity().getWorld().getName())) || a != null) {
            event.setDeathMessage(null);
        }
        if (a.getTeam(victim) == null){
            return;
        }

        ITeam deadteam = a.getTeam(victim);

        if (deadteam.isBedDestroyed() && deadteam.getMembers().isEmpty()){
            Bukkit.getPluginManager().callEvent(new TeamEliminatedEvent(a, deadteam));
        }
    }
}
