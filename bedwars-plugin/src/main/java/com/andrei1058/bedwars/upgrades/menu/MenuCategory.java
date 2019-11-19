package com.andrei1058.bedwars.upgrades.menu;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MenuCategory implements MenuContent {

    private ItemStack displayItem;
    private String name;

    private HashMap<Integer, MenuContent> menuContentBySlot = new HashMap<>();

    public MenuCategory(String name, ItemStack displayItem){
        this.name = name;
        this.displayItem = displayItem;
    }

    /**
     * Add content to a menu.
     *
     * @param content content instance.
     * @param slot    where to put the content in the menu.
     * @return false if te given slot is in use.
     */
    public boolean addContent(MenuContent content, int slot) {
        if (menuContentBySlot.get(slot) != null) return false;
        menuContentBySlot.put(slot, content);
        return true;
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
        if (!BedWars.getAPI().getArenaUtil().isPlaying(player)) return;
        UpgradesManager.setWatchingUpgrades(player.getUniqueId());
        Inventory inv = Bukkit.createInventory(null, 64);
        for (Map.Entry<Integer, MenuContent> entry : menuContentBySlot.entrySet()){
            inv.setItem(entry.getKey(), entry.getValue().getDisplayItem());
        }
        player.openInventory(inv);
    }

}
