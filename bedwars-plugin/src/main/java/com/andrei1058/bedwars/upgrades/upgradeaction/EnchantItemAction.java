package com.andrei1058.bedwars.upgrades.upgradeaction;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.upgrades.UpgradeAction;
import org.bukkit.enchantments.Enchantment;

public class EnchantItemAction implements UpgradeAction {

    private Enchantment enchantment;
    private int amplifier;
    private ApplyType type;

    public EnchantItemAction(Enchantment enchantment, int amplifier, ApplyType type){
        this.enchantment = enchantment;
        this.amplifier = amplifier;
        this.type = type;
    }

    @Override
    public void onBuy(ITeam bwt) {
        if (type == ApplyType.ARMOR){
            bwt.addArmorEnchantment(enchantment, amplifier);
        } else if (type == ApplyType.SWORD){
            bwt.addSwordEnchantment(enchantment, amplifier);
        } else if (type == ApplyType.BOW){
            bwt.addBowEnchantment(enchantment, amplifier);
        }
    }

    public enum ApplyType {
        SWORD,
        ARMOR,
        BOW,
    }
}
