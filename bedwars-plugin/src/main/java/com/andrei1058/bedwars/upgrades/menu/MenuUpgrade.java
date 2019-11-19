package com.andrei1058.bedwars.upgrades.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MenuUpgrade implements MenuContent {

    private String name;
    private List<UpgradeTier> tiers = new LinkedList<>();

    /**
     * Create a new upgrade element.
     *
     * @param name identifier.
     */
    public MenuUpgrade(String name) {
        this.name = name;
    }

    @Override
    public ItemStack getDisplayItem() {
        //return next tier display item
        //todo
        return tiers.get(0).getDisplayItem();
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
        //todo
    }

    /**
     * Load a upgrade element tiers.
     *
     * @param upgradeTier tier.
     * @return false if something went wrong.
     */
    public boolean addTier(UpgradeTier upgradeTier) {
        for (UpgradeTier ut : tiers){
            if (ut.getName().equalsIgnoreCase(upgradeTier.getName())) return false;
        }
        tiers.add(upgradeTier);
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @return tiers list.
     */
    public List<UpgradeTier> getTiers() {
        return Collections.unmodifiableList(tiers);
    }
}
