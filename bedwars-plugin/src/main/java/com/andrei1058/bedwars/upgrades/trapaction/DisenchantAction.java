package com.andrei1058.bedwars.upgrades.trapaction;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.upgrades.TrapAction;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DisenchantAction implements TrapAction {

    private Enchantment enchantment;
    private ApplyType type;

    public DisenchantAction(Enchantment enchantment, DisenchantAction.ApplyType type){
        this.enchantment = enchantment;
        this.type = type;
    }

    @Override
    public String getName() {
        return "disenchant-item";
    }

    @Override
    public void onTrigger(@NotNull Player player, ITeam playerTeam, ITeam targetTeam) {
        if (type == ApplyType.SWORD){
            for (ItemStack i : player.getInventory()){
                if (BedWars.nms.isSword(i)){
                    i.removeEnchantment(enchantment);
                }
                player.updateInventory();
            }
        } else if (type == ApplyType.ARMOR){
            for (ItemStack i : player.getInventory()){
                if (BedWars.nms.isArmor(i)){
                    i.removeEnchantment(enchantment);
                }
                player.updateInventory();
            }
            for (ItemStack i : player.getInventory().getArmorContents()){
                if (BedWars.nms.isArmor(i)){
                    i.removeEnchantment(enchantment);
                }
                player.updateInventory();
            }
        } else if (type == ApplyType.BOW){
            for (ItemStack i : player.getInventory()){
                if (BedWars.nms.isBow(i)){
                    i.removeEnchantment(enchantment);
                }
                player.updateInventory();
            }
        }
    }

    public enum ApplyType {
        SWORD,
        ARMOR,
        BOW,
    }
}
