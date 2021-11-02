package com.andrei1058.bedwars.arena.spectator.menu;

import com.andrei1058.bedwars.util.TextUtil;
import com.andrei1058.bedwars.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Menu implements InventoryHolder, ItemUtils {
    protected PlayerMenuUtility playerMenuUtility;

    protected Inventory inventory;

    public Menu(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent paramInventoryClickEvent);

    public abstract void setMenuItems();

    public void open() {
        this.inventory = Bukkit.createInventory(this, getSlots(), TextUtil.colorize(getMenuName()));
        setMenuItems();
        this.playerMenuUtility.getPlayer().openInventory(this.inventory);
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}
