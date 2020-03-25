package com.andrei1058.bedwars.upgrades.trapaction;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.upgrades.TrapAction;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class PlayerEffectAction implements TrapAction {

    private PotionEffectType potionEffectType;
    private int amplifier, duration;
    private ApplyType type;

    public PlayerEffectAction(PotionEffectType potionEffectType, int amplifier, int duration, ApplyType type){
        this.potionEffectType = potionEffectType;
        this.amplifier = amplifier;
        this.type = type;
        this.duration = duration;
        if (duration < 0 ) this.duration *= -1;
        /*if (type == ApplyType.ENEMY_BASE_ENTER && duration <= 0){
            this.duration = 20;
        }*/
        if (duration == 0){
            this.duration = Integer.MAX_VALUE;
        } else {
            this.duration *=20;
        }
    }

    @Override
    public String getName() {
        return "player-effect";
    }

    @Override
    public void onTrigger(@NotNull Player player, ITeam playerTeam, ITeam targetTeam) {
        if (type == ApplyType.TEAM){
            for (Player p : targetTeam.getMembers()){
                p.addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier));
            }
        } else if (type == ApplyType.BASE){
            for (Player p : targetTeam.getMembers()){
                if (p.getLocation().distance(targetTeam.getBed()) <= targetTeam.getArena().getIslandRadius()) {
                    p.addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier));
                }
            }
        } else if (type == ApplyType.ENEMY){
            player.addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier));
        }
    }

    public enum ApplyType {
        TEAM, BASE, ENEMY
    }
}
