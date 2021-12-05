package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.configuration.Sounds;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class EnderPearlLanded implements Listener {

    @EventHandler
    public void onPearlHit(ProjectileHitEvent e){

        if (!(e.getEntity() instanceof EnderPearl)) return;

        if (!(e.getEntity().getShooter() instanceof Player)) return;

        Player player = (Player) e.getEntity().getShooter();

        IArena iArena = Arena.getArenaByPlayer(player);

        if (!Arena.isInArena(player) || iArena.isSpectator(player)) return;

        Sound sound = Sound.valueOf(Sounds.getSounds().getYml().getString("ender-pearl-landed.sound"));

        if (!Sounds.playSound(sound, iArena.getPlayers())){
            Sounds.playSound("ender-pearl-landed", iArena.getPlayers());
        }
    }
}
