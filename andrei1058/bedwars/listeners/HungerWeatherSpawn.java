package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import static com.andrei1058.bedwars.Main.getServerType;

public class HungerWeatherSpawn implements Listener {

    @EventHandler
    public void e(FoodLevelChangeEvent e) {
        switch (getServerType()) {
            case SHARED:
                if (Arena.getArenaByPlayer((Player) e.getEntity()) != null) {
                    e.setCancelled(true);
                }
                break;
            default:
                e.setCancelled(true);
                break;
        }
    }

    @EventHandler
    public void W(WeatherChangeEvent e) {
        if (e.toWeatherState()) {
            switch (getServerType()) {
                case SHARED:
                    if (Arena.getArenaByName(e.getWorld().getName()) != null) {
                        e.setCancelled(true);
                    }
                    break;
                default:
                    e.setCancelled(true);
                    break;
            }
        }
    }

    @EventHandler
    public void s(CreatureSpawnEvent e){
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
            if (getServerType() == ServerType.SHARED) {
                if (Arena.getArenaByName(e.getEntity().getWorld().getName()) != null){
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    /*@EventHandler
    public void he(EntityRegainHealthEvent e){
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            Arena a = Arena.getArenaByPlayer(p);
            if (a != null) {
                if (a.isPlayer(p)) {
                    BedWarsTeam t = a.getTeam(p);
                    if (t != null) {
                        p.setPlayerListName(lang.m(lang.tablistFormat).replace("{TeamColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{TeamLetter}", t.getName().substring(0, 1).toUpperCase())
                                .replace("{TeamName}", t.getName()).replace("{PlayerName}", p.getName()).replace("{PlayerHealth}", String.valueOf((int) p.getHealth())));
                    }
                }
            }
        }
    }*/
}
