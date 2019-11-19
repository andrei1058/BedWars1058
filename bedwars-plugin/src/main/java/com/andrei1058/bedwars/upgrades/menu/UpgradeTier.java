package com.andrei1058.bedwars.upgrades.menu;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class UpgradeTier implements MenuContent{

    private ItemStack displayItem;
    private String name;

    public UpgradeTier(String name, ItemStack displayItem, int cost, ItemStack currency){
        this.displayItem = BedWars.nms.addCustomData(displayItem, "MCONT_" + name);
        this.name = name;
    }

    @Override
    public ItemStack getDisplayItem() {
        return displayItem;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void onClick(Player player, ClickType clickType) {

    }
}
