package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.events.player.PlayerJoinArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import java.util.Collections;
import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class SpectatorSettingsListener implements Listener {
    private final ItemStack settingsItem;

    public SpectatorSettingsListener() {
        ItemStack item = new ItemStack(Material.LEGACY_REDSTONE_COMPARATOR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(TextUtil.colorize("&b&lSpectator Settings &7(Right Click)"));
        meta.setLore(Collections.singletonList(TextUtil.colorize("&7Right-click to change your spectator settings!")));
        item.setItemMeta(meta);
        this.settingsItem = item;
    }

    @EventHandler
    public void onFinalKill(PlayerKillEvent e) {
        if (!e.getCause().isFinalKill())
            return;
        addSettings(e.getVictim());
    }

    @EventHandler
    public void onSpecJoin(PlayerJoinArenaEvent e) {
        if (!e.isSpectator())
            return;
        addSettings(e.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getItemInHand() == null)
            return;
        if (!player.getItemInHand().getType().equals(Material.LEGACY_REDSTONE_COMPARATOR))
            return;
        Bukkit.dispatchCommand((CommandSender)player, "bw settings");
    }

    private void addSettings(Player player) {
        Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> player.getInventory().setItem(4, this.settingsItem), 30L);
    }
}
