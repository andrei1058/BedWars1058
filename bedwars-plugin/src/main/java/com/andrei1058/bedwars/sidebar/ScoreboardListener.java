package com.andrei1058.bedwars.sidebar;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.player.PlayerBedBreakEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReJoinEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReSpawnEvent;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class ScoreboardListener implements Listener {

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
            if (arena.equals(scoreboard.getArena())) {
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
            if (arena.equals(scoreboard.getArena())){
                scoreboard.getHandle().refreshHealth(player, health);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onReSpawn(PlayerReSpawnEvent e){
        if (e == null) return;
        final IArena arena = e.getArena();
        for (BedWarsScoreboard scoreboard : BedWarsScoreboard.getScoreboards().values()){
            if (arena.equals(scoreboard.getArena())){
                scoreboard.getHandle().refreshHealth(e.getPlayer(), (int) e.getPlayer().getHealth());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void reJoin(PlayerReJoinEvent e){
        if (e == null) return;
        if (e.isCancelled()) return;
        if (!BedWars.config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_PLAYING)) return;
        final IArena arena = e.getArena();
        final Player player = e.getPlayer();

        // re-add player to scoreboard tab list
        for (BedWarsScoreboard scoreboard : BedWarsScoreboard.getScoreboards().values()){
            if (arena.equals(scoreboard.getArena())){
                scoreboard.addToTabList(player, Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_PLAYING, Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_PLAYING);
            }
        }
    }

    @EventHandler
    public void onBedDestroy(PlayerBedBreakEvent e){
        if (e == null) return;
        final IArena arena = e.getArena();

        // refresh placeholders in case placeholders refresh is disabled
        BedWarsScoreboard.getScoreboards().values().forEach(bedWarsScoreboard -> {
            if (arena.equals(bedWarsScoreboard.getArena())){
                bedWarsScoreboard.getHandle().refreshPlaceholders();
            }
        });
    }

    @EventHandler
    public void onFinalKill(PlayerKillEvent e){
        if (e == null) return;
        if (!e.getCause().toString().contains("FINAL")) return;
        final IArena arena = e.getArena();

        // refresh placeholders in case placeholders refresh is disabled
        BedWarsScoreboard.getScoreboards().values().forEach(bedWarsScoreboard -> {
            if (arena.equals(bedWarsScoreboard.getArena())){
                bedWarsScoreboard.getHandle().refreshPlaceholders();
            }
        });
    }
}
