package com.andrei1058.bedwars.upgrades.menu;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class UpgradeTier implements MenuContent{

    private ItemStack displayItem;

    public UpgradeTier(String name, String material, int amount, byte data){

        // create display item
        try {
            Material.valueOf(material);
        } catch (Exception ex){
            Bukkit.getLogger().log(Level.WARNING, "Invalid material " + material + " at upgrade tier " + name);
            return;
        }
        //displayItem = BedWars.nms.createItemStack()
        displayItem = BedWars.nms.addCustomData(displayItem, "MCONT_" + name);
    }

    @Override
    public ItemStack getDisplayItem() {
        return null;
    }

    @Override
    public void onClick(ClickType clickType) {

    }
}
