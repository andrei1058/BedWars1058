package com.andrei1058.bedwars.upgrades;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.potion.PotionEffectType;

import static com.andrei1058.bedwars.Main.plugin;

public class EffectAction extends UpgradeAction {

    private String name, apply;
    private PotionEffectType potionEffectType;
    private int amplifier;

    public EffectAction(String name, PotionEffectType potionEffectType, int amplifier, String apply){
        this.name = name;
        this.potionEffectType = potionEffectType;
        this.amplifier = amplifier;
        this.apply = apply;
        plugin.debug("loading new EffectAction: "+getName());
    }

    @Override
    public void execute(BedWarsTeam bwt) {
        if (apply.equalsIgnoreCase("members")){
            bwt.addTeamEffect(getName(), getPotionEffectType(), getAmplifier());
        } else if(apply.equalsIgnoreCase("base")){
            bwt.addBaseEffect(getName(), getPotionEffectType(), getAmplifier());
        } else if (apply.equalsIgnoreCase("enemybaseenter")){
            bwt.addEnemyBaseEnterEffect(getName(), getPotionEffectType(), getAmplifier());
        }
    }

    public String getName() {
        return name;
    }

    public PotionEffectType getPotionEffectType() {
        return potionEffectType;
    }

    public int getAmplifier() {
        return amplifier;
    }
}
