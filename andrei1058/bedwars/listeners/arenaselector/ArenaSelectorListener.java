package com.andrei1058.bedwars.listeners.arenaselector;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.ArenaGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
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
        String arena = data.split("=")[0];
        Arena a = Arena.getArenaByName(arena);
        if (a == null) return;

        if (e.getAction() == InventoryAction.PICKUP_ALL){
            p.sendMessage("left click");
            if (a.getStatus() == GameState.waiting || a.getStatus() == GameState.playing){
                a.addPlayer(p, false);
            } else {
                //msg you can't join this arena right now, right click to spectate
            }
        } else if (e.getAction() == InventoryAction.PICKUP_HALF){
            p.sendMessage("right click");
            if (a.getStatus() == GameState.playing){
                a.addSpectator(p, false, null);
            } else {
                //msg you can't spectate this arena right now, left click to play
            }
        }

        p.closeInventory();
    }

    @EventHandler
    public void onArenaSelectorClose(InventoryCloseEvent e) {
        if (ArenaGUI.getRefresh().containsKey(e.getPlayer())) {
            ArenaGUI.getRefresh().remove(e.getPlayer());
        }
    }
}
