package com.andrei1058.bedwars.sidebar;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.player.PlayerReSpawnEvent;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class ScoreboardHealthListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDamage(EntityDamageEvent e){
        if (e == null) return;
        if (e.isCancelled()) return;
        if (!(e.getEntity() instanceof Player)) return;
        final Player player = (Player) e.getEntity();
        final IArena arena = Arena.getArenaByPlayer(player);

        int health = (int) (player.getHealth() - e.getDamage()) + 1;

        if (arena == null) return;

        for (BedWarsScoreboard scoreboard : BedWarsScoreboard.getScoreboards().values()){
            if (scoreboard.getArena().equals(arena)){
                scoreboard.getHandle().refreshHealth(player, health);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRegain(EntityRegainHealthEvent e){
        if (e == null) return;
        if (e.isCancelled()) return;
        if (!(e.getEntity() instanceof Player)) return;
        final Player player = (Player) e.getEntity();
        final IArena arena = Arena.getArenaByPlayer(player);
        if (arena == null) return;

        int health = (int) (player.getHealth() + e.getAmount()) + 1;
        if (health > player.getMaxHealth()){
            health -= 1;
        }
        for (BedWarsScoreboard scoreboard : BedWarsScoreboard.getScoreboards().values()){
            if (scoreboard.getArena().equals(arena)){
                scoreboard.getHandle().refreshHealth(player, health);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onReSpawn(PlayerReSpawnEvent e){
        if (e == null) return;
        final IArena arena = e.getArena();
        for (BedWarsScoreboard scoreboard : BedWarsScoreboard.getScoreboards().values()){
            if (scoreboard.getArena().equals(arena)){
                scoreboard.getHandle().refreshHealth(e.getPlayer(), (int) e.getPlayer().getHealth());
            }
        }
    }
}
