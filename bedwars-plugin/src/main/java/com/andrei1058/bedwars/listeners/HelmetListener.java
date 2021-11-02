package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReSpawnEvent;
import com.andrei1058.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;

import static com.andrei1058.bedwars.BedWars.nms;

public class HelmetListener implements Listener {
    @EventHandler
    public void onGameStart(GameStateChangeEvent e) {
        if (!e.getNewState().equals(GameState.playing))
            return;
        Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> e.getArena().getPlayers(), 1L);
    }

    @EventHandler
    public void onRespawn(PlayerReSpawnEvent e) {
        Bukkit.getScheduler().runTaskLater((Plugin)BedWars.getInstance(), () -> setPlayerHelmet(e.getPlayer(), e.getTeam()), 1L);
    }

    private void setPlayerHelmet(Player player, ITeam team) {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta)helmet.getItemMeta();
        meta.addEnchant(Enchantment.WATER_WORKER, 1, true);
        meta.setColor(team.getColor().bukkitColor());
        nms.setUnbreakable(true);
        helmet.setItemMeta((ItemMeta)meta);
        player.getEquipment().setHelmet(helmet);
    }
}

