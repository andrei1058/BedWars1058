package com.andrei1058.bedwars.upgrades.trapaction;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.upgrades.TrapAction;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class RemoveEffectAction implements TrapAction {

    private PotionEffectType potionEffectType;

    public RemoveEffectAction(PotionEffectType potionEffectType){
        this.potionEffectType = potionEffectType;
    }

    @Override
    public String getName() {
        return "remove-effect";
    }

    @Override
    public void onTrigger(@NotNull Player player, ITeam playerTeam, ITeam targetTeam) {
        player.removePotionEffect(potionEffectType);
    }
}
