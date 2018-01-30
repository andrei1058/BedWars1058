package com.andrei1058.bedwars.upgrades;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.enchantments.Enchantment;

import static com.andrei1058.bedwars.Main.plugin;

public class EnchantmentAction extends UpgradeAction {

    private int amplifier;
    private Enchantment enchantment;
    private String name, apply;

    public EnchantmentAction(String name, Enchantment enchantment, int amplifier, String apply){
        this.name = name;
        this.enchantment = enchantment;
        this.amplifier = amplifier;
        this.apply = apply;
        plugin.debug("loading new EnchantmentAction: "+getName());
    }

    @Override
    public void execute(BedWarsTeam bwt) {
        switch (getApply().toLowerCase()){
            case "bow":
                break;
            case "sword":
                break;
            case "armor":
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
