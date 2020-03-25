package com.andrei1058.bedwars.upgrades.listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class UpgradeOpenListener implements Listener {

    @EventHandler
    public void onUpgradesOpen(PlayerInteractAtEntityEvent e){
        IArena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a == null) return;
        Location l = e.getRightClicked().getLocation();
        for (ITeam t : a.getTeams()) {
            Location l2 = t.getTeamUpgrades();
            if (l.getBlockX() == l2.getBlockX() && l.getBlockY() == l2.getBlockY() && l.getBlockZ() == l2.getBlockZ()) {
                e.setCancelled(true);
                if (a.isPlayer(e.getPlayer())) {
                    UpgradesManager.getMenuForArena(a).open(e.getPlayer());
                }
            }
        }
    }
}
