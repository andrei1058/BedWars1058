package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.events.player.PlayerMoneyGainEvent;
import com.andrei1058.bedwars.api.events.player.PlayerXpGainEvent;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RewardSummary implements Listener {

    @EventHandler
    public void onGainXp(PlayerXpGainEvent e){
        if (Arena.getExperienceEarned().containsKey(e.getPlayer().getUniqueId())) {
            Arena.getExperienceEarned().put(e.getPlayer().getUniqueId(), Arena.getExperienceEarned(e.getPlayer().getUniqueId()) + e.getAmount());
        }
    }

    @EventHandler
    public void onGainMoney(PlayerMoneyGainEvent e){
        if (Arena.getCoinsEarned().containsKey(e.getPlayer().getUniqueId())) {
            Arena.getCoinsEarned().put(e.getPlayer().getUniqueId(), Arena.getCoinsEarned(e.getPlayer().getUniqueId()) + e.getAmount());
        }
    }
}
