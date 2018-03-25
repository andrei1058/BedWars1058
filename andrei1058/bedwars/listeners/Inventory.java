package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.ArenaGUI;
import com.andrei1058.bedwars.shop.CategoryContent;
import com.andrei1058.bedwars.shop.ShopCategory;
import com.andrei1058.bedwars.upgrades.TeamUpgrade;
import com.andrei1058.bedwars.upgrades.UpgradeGroup;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

import static com.andrei1058.bedwars.Main.config;
import static com.andrei1058.bedwars.Main.lang;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class Inventory implements Listener {

    private static HashMap lookingAtShop = new HashMap();

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (ArenaGUI.getRefresh().containsKey(p)) {
            if (e.getInventory().equals(ArenaGUI.getRefresh().get(p))) {
                ArenaGUI.getRefresh().remove(p);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack i = e.getCurrentItem();
        if (i == null) return;
        if (i.getType() == Material.AIR) return;
        if (Arena.isInArena(p)) {
            if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
                e.setCancelled(true);
                return;
            }
        }
        if (!i.hasItemMeta()) return;
        if (!i.getItemMeta().hasDisplayName()) return;
        if (p.getWorld().getName().equalsIgnoreCase(config.getLobbyWorldName())) {
            e.setCancelled(true);
        }
        if (Arena.isInArena(p)) {
            Arena a = Arena.getArenaByPlayer(p);
            if (a.getStatus() == GameState.waiting || a.getStatus() == GameState.starting) {
                e.setCancelled(true);
                return;
            }
            if (a.isSpectator(p)){
                e.setCancelled(true);
                return;
            }
            if (a.isPlayer(p)) {
                for (ShopCategory sc : ShopCategory.getShopCategories()) {
                    if (e.getInventory().getName().equalsIgnoreCase(sc.getDisplayName(p))) {
                        e.setCancelled(true);
                        for (CategoryContent cc : sc.getContent()) {
                            if (cc.getName().equalsIgnoreCase("back")) {
                                sc.getParent().openToPlayer(p);
                                return;
                            }
                            if (e.getSlot() == cc.getSlot()) {
                                cc.getContentAction().doStuff(p);
                                return;
                            }
                        }
                        break;
                    }
                }
                if (e.getInventory().getName().equalsIgnoreCase(getMsg(p, "upgrades." + UpgradeGroup.getUpgradeGroup(a.getGroup()).getName() + ".name"))) {
                    for (TeamUpgrade tu : UpgradeGroup.getUpgradeGroup(a.getGroup()).getTeamUpgrades()) {
                        if (tu.getSlot() == e.getSlot()) {
                            e.setCancelled(true);
                            tu.doAction(p, a.getTeam(p));
                            return;
                        }
                    }
                }
            }
        }
        if (ArenaGUI.getRefresh().containsKey(p)) {
            if (e.getClickedInventory().equals(ArenaGUI.getRefresh().get(p))) {
                for (Arena a : Arena.getArenas()) {
                    if (a.getSlot() == e.getSlot()) {
                        a.addPlayer(p);
                        e.setCancelled(true);
                        p.closeInventory();
                    }
                }
            }
        }
    }

    public static HashMap getLookingAtShop() {
        return lookingAtShop;
    }
}
