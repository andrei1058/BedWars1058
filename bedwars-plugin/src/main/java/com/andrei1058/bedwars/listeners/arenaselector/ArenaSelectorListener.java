package com.andrei1058.bedwars.listeners.arenaselector;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.ArenaGUI;
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
    public void onArenaSelectorClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!(player.getOpenInventory().getTopInventory().getHolder() instanceof ArenaGUI.ArenaSelectorHolder)) return;

        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (item.getType() == Material.AIR) return;
        if (!BedWars.nms.isCustomBedWarsItem(item)) return;

        String data = BedWars.nms.getCustomData(item);
        if (data.startsWith("RUNCOMMAND")) {
            Bukkit.dispatchCommand(player, data.split("_")[1]);
        }

        if (!data.contains(ARENA_SELECTOR_GUI_IDENTIFIER)) return;

        String arenaName = data.split("=")[1];
        IArena arena = Arena.getArenaByName(arenaName);
        if (arena == null) return;

        if (event.getClick() == ClickType.LEFT) {
            if ((arena.getStatus() == GameState.waiting || arena.getStatus() == GameState.starting) && arena.addPlayer(player, false)) {
                Sounds.playSound("join-allowed", player);
            } else {
                Sounds.playSound("join-denied", player);
                player.sendMessage(Language.getMsg(player, Messages.ARENA_JOIN_DENIED_SELECTOR));
            }
        } else if (event.getClick() == ClickType.RIGHT) {
            if (arena.getStatus() == GameState.playing && arena.addSpectator(player, false, null)) {
                Sounds.playSound("spectate-allowed", player);
            } else {
                player.sendMessage(Language.getMsg(player, Messages.ARENA_SPECTATE_DENIED_SELECTOR));
                Sounds.playSound("spectate-denied", player);
            }
        }

        player.closeInventory();
    }
}
