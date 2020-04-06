package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.events.player.PlayerInvisibilityPotionEvent;

import com.andrei1058.bedwars.arena.SBoard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InvisibilityPotionListener implements Listener {

    @EventHandler
    public void onPotion(PlayerInvisibilityPotionEvent e){
        if (e.getTeam() == null) return;
        if (e.getType() == PlayerInvisibilityPotionEvent.Type.ADDED){
            if (e.getPlayer().getPassenger() == null){
                for (SBoard sb : SBoard.getScoreboards().values()){
                    if (sb.getArena() == null) continue;
                    if (sb.getArena().equals(e.getArena()) && !e.getTeam().isMember(sb.getPlayer())){
                        sb.invisibilityPotion(e.getTeam(), e.getPlayer(), true);
                    }
                }
            }
        } else {
            if (e.getPlayer().getPassenger() == null){
                for (SBoard sb : SBoard.getScoreboards().values()){
                    if (sb.getArena() == null) continue;
                    if (sb.getArena().equals(e.getArena()) && !e.getTeam().isMember(sb.getPlayer())){
                        sb.invisibilityPotion(e.getTeam(), e.getPlayer(), false);
                    }
                }
            }
        }
    }
}
