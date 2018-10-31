package com.andrei1058.bedwars.listeners.arenaselector;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.ArenaGUI;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.language.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class ArenaSelectorListener implements Listener {

    public static final String ARENA_SELECTOR_GUI_IDENTIFIER = "arena=";

    @EventHandler
    public void onArenaSelectorClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (!ArenaGUI.getRefresh().containsKey(p)) return;
        e.setCancelled(true);
        ItemStack i = e.getCurrentItem();

        if (i == null) return;
        if (i.getType() == Material.AIR) return;

        if (!Main.nms.isCustomBedWarsItem(i)) return;
        String data = Main.nms.getCustomData(i);
        if (!data.contains(ARENA_SELECTOR_GUI_IDENTIFIER)) return;
        String arena = data.split("=")[1];
        Arena a = Arena.getArenaByName(arena);
        if (a == null) return;

        if (e.getClick() == ClickType.LEFT){
            if (a.getStatus() == GameState.waiting || a.getStatus() == GameState.starting){
                a.addPlayer(p, false);
            } else {
                p.sendMessage(Language.getMsg(p, Messages.ARENA_JOIN_DENIED_SELECTOR));
            }
        } else if (e.getClick() == ClickType.RIGHT){
            if (a.getStatus() == GameState.playing){
                a.addSpectator(p, false, null);
            } else {
                p.sendMessage(Language.getMsg(p, Messages.ARENA_SPECTATE_DENIED_SELECTOR));
            }
        }

        p.closeInventory();
    }

    @EventHandler
    public void onArenaSelectorClose(InventoryCloseEvent e) {
        ArenaGUI.getRefresh().remove(e.getPlayer());
    }
}
