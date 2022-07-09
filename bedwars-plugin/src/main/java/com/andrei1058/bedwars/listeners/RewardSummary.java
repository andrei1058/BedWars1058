package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.player.PlayerMoneyGainEvent;
import com.andrei1058.bedwars.api.events.player.PlayerXpGainEvent;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RewardSummary implements Listener {

    @EventHandler
    public void onGainXp(PlayerXpGainEvent e){

        IArena arena = Arena.getArenaByPlayer(e.getPlayer());

        if (arena.getExperienceMap().containsKey(e.getPlayer().getUniqueId())) {
            arena.getExperienceMap().put(e.getPlayer().getUniqueId(), arena.getExperienceEarned(e.getPlayer().getUniqueId()) + e.getAmount());
        }
    }

    @EventHandler
    public void onGainMoney(PlayerMoneyGainEvent e){

        IArena arena = Arena.getArenaByPlayer(e.getPlayer());

        if (arena.getCoinsMap().containsKey(e.getPlayer().getUniqueId())) {
            arena.getCoinsMap().put(e.getPlayer().getUniqueId(), arena.getCoinsEarned(e.getPlayer().getUniqueId()) + e.getAmount());
        }
    }
}