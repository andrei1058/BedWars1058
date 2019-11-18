package com.andrei1058.bedwars.upgradesold;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.enchantments.Enchantment;

@SuppressWarnings("WeakerAccess")
public class EnchantmentAction extends UpgradeAction {

    private int amplifier;
    private Enchantment enchantment;
    private String name, apply;

    public EnchantmentAction(String name, Enchantment enchantment, int amplifier, String apply){
        this.name = name;
        this.enchantment = enchantment;
        this.amplifier = amplifier;
        this.apply = apply;
        BedWars.debug("loading new EnchantmentAction: "+getName());
    }

    @Override
    public void execute(ITeam bwt, int i) {
        switch (getApply().toLowerCase()){
            case "bow":
                bwt.addBowEnchantment(getEnchantment(), getAmplifier());
                break;
            case "sword":
                bwt.addSwordEnchantment(getEnchantment(), getAmplifier());
                break;
            case "armor":
                bwt.addArmorEnchantment(getEnchantment(), getAmplifier());
                break;
        }
    }

    public String getName() {
        return name;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public String getApply() {
        return apply;
    }
}
