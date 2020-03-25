package com.andrei1058.bedwars.upgrades.upgradeaction;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.upgrades.UpgradeAction;
import org.bukkit.potion.PotionEffectType;

public class PlayerEffectAction implements UpgradeAction {

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
            this.duration *= 20;
        }
    }


    @Override
    public void onBuy(ITeam bwt) {
        if (type == ApplyType.BASE){
            bwt.addBaseEffect(potionEffectType, amplifier, duration);
        } else if (type == ApplyType.TEAM){
            bwt.addTeamEffect(potionEffectType, amplifier, duration);
        }/* else if (type == ApplyType.ENEMY_BASE_ENTER){
            bwt.addEnemyBaseEnterEffect(potionEffectType, amplifier, duration);
        }*/
    }

    public enum ApplyType {
        TEAM, BASE
    }
}
