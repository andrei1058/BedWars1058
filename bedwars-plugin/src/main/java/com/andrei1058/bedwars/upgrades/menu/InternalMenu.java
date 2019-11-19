package com.andrei1058.bedwars.upgrades.menu;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class InternalMenu implements UpgradesIndex {

    private String name;
    private HashMap<Integer, MenuContent> menuContentBySlot = new HashMap<>();

    /**
     * Create an upgrade menu for an arena group.
     *
     * @param groupName arena group name.
     */
    public InternalMenu(String groupName) {
        this.name = groupName;
    }

    @Override
    public void open(Player player) {
        if (!BedWars.getAPI().getArenaUtil().isPlaying(player)) return;
        UpgradesManager.setWatchingUpgrades(player.getUniqueId());
        Inventory inv = Bukkit.createInventory(null, 64);
        for (Map.Entry<Integer, MenuContent> entry : menuContentBySlot.entrySet()){
            inv.setItem(entry.getKey(), entry.getValue().getDisplayItem());
        }
        player.openInventory(inv);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean addContent(MenuContent content, int slot) {
        if (menuContentBySlot.get(slot) != null) return false;
        menuContentBySlot.put(slot, content);
        return true;
    }
}
