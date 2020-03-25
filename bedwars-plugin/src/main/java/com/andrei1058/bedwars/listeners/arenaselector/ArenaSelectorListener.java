package com.andrei1058.bedwars.listeners.arenaselector;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.ArenaGUI;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.configuration.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ArenaSelectorListener implements Listener {

    public static final String ARENA_SELECTOR_GUI_IDENTIFIER = "arena=";

    @EventHandler
    public void onArenaSelectorClick(InventoryClickEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, () -> {
            Player p = (Player) e.getWhoClicked();
            if (!(p.getOpenInventory().getTopInventory().getHolder() instanceof ArenaGUI.ArenaSelectorHolder)) return;
            e.setCancelled(true);
            ItemStack i = e.getCurrentItem();

            if (i == null) return;
            if (i.getType() == Material.AIR) return;

            if (!BedWars.nms.isCustomBedWarsItem(i)) return;
            String data = BedWars.nms.getCustomData(i);
            if (data.startsWith("RUNCOMMAND")) {
                Bukkit.dispatchCommand(p, data.split("_")[1]);
            }
            if (!data.contains(ARENA_SELECTOR_GUI_IDENTIFIER)) return;
            String arena = data.split("=")[1];
            IArena a = Arena.getArenaByName(arena);
            if (a == null) return;

            Bukkit.getScheduler().runTask(BedWars.plugin, () -> {
                if (e.getClick() == ClickType.LEFT) {
                    if ((a.getStatus() == GameState.waiting || a.getStatus() == GameState.starting) && a.addPlayer(p, false)) {
                        Sounds.playSound("join-allowed", p);
                    } else {
                        Sounds.playSound("join-denied", p);
                        p.sendMessage(Language.getMsg(p, Messages.ARENA_JOIN_DENIED_SELECTOR));
                    }
                } else if (e.getClick() == ClickType.RIGHT) {
                    if (a.getStatus() == GameState.playing && a.addSpectator(p, false, null)) {
                        Sounds.playSound("spectate-allowed", p);
                    } else {
                        p.sendMessage(Language.getMsg(p, Messages.ARENA_SPECTATE_DENIED_SELECTOR));
                        Sounds.playSound("spectate-denied", p);
                    }
                }

                p.closeInventory();
            });
        });
    }
}
