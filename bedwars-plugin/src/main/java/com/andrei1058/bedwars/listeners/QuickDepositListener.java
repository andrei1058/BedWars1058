/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
 import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Autore: Giustino C. Miglionico con l'aiuto della tecnologia peruviana
 *
 * Logica di Implementazione:
 * FASE 1 - Rilevamento interazione
 *   Obiettivo: Rilevare Shift + Click destro su una cassa del proprio team durante la partita.
 *   Condizioni: feature abilitata in config, player in partita, non spettatore, cassa nella propria base.
 *
 * FASE 2 - Trasferimento valuta
 *   Obiettivo: Trasferire ferro/oro/diamanti/smeraldi dall'inventario del player alla cassa.
 *   Scelta tecnica: Iterare l'inventario del player, spostare ogni stack di currency nella cassa.
 *   Se la cassa e' piena, il residuo resta nell'inventario del player.
 *
 * FASE 3 - Feedback visivo/sonoro
 *   Obiettivo: Notificare il player con sound e messaggio del trasferimento avvenuto.
 */
public class QuickDepositListener implements Listener {

    // Valute trasferibili — solo quelle classiche di BedWars
    private static final Set<Material> CURRENCIES = EnumSet.of(
            Material.IRON_INGOT,
            Material.GOLD_INGOT,
            Material.DIAMOND,
            Material.EMERALD
    );

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onClickChest(PlayerInteractEvent event) {
        // Solo click sinistro attiva il deposito rapido; click destro apre normalmente.
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        Material blockType = block.getType();
        boolean isChest      = blockType == Material.CHEST || blockType == Material.TRAPPED_CHEST;
        boolean isEnderChest = blockType == Material.ENDER_CHEST;
        if (!isChest && !isEnderChest) return;

        Player player = event.getPlayer();

        // Feature attiva?
        if (!BedWars.config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_ENABLE_QUICK_DEPOSIT)) return;

        IArena arena = Arena.getArenaByPlayer(player);
        if (arena == null) return;
        if (arena.isSpectator(player)) return;
        if (arena.getRespawnSessions().containsKey(player)) return;

        Inventory targetInventory;

        if (isChest) {
            // CHEST: solo se è nella base del proprio team
            ITeam playerTeam = arena.getTeam(player);
            if (playerTeam == null) return;
            int isRad = arena.getConfig().getInt(ConfigPath.ARENA_ISLAND_RADIUS);
            if (playerTeam.getSpawn().distance(block.getLocation()) > isRad) return;
            if (!(block.getState() instanceof Chest)) return;
            event.setCancelled(true);
            targetInventory = ((Chest) block.getState()).getBlockInventory();
        } else {
            // ENDER_CHEST: sempre del player
            event.setCancelled(true);
            targetInventory = player.getEnderChest();
        }

        // [DEBUG] FASE 2 - Trasferimento valute con tracking per materiale
        Map<Material, Integer> depositedPerMaterial = new LinkedHashMap<>();
        depositedPerMaterial.put(Material.IRON_INGOT, 0);
        depositedPerMaterial.put(Material.GOLD_INGOT, 0);
        depositedPerMaterial.put(Material.DIAMOND, 0);
        depositedPerMaterial.put(Material.EMERALD, 0);

        boolean chestFull = false;
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack == null || stack.getType() == Material.AIR) continue;
            if (!CURRENCIES.contains(stack.getType())) continue;

            Material mat = stack.getType();
            ItemStack toDeposit = stack.clone();
            java.util.HashMap<Integer, ItemStack> leftover = targetInventory.addItem(toDeposit);

            if (leftover.isEmpty()) {
                player.getInventory().setItem(i, null);
                depositedPerMaterial.merge(mat, toDeposit.getAmount(), Integer::sum);
            } else {
                ItemStack remaining = leftover.values().iterator().next();
                int deposited = toDeposit.getAmount() - remaining.getAmount();
                if (deposited > 0) {
                    depositedPerMaterial.merge(mat, deposited, Integer::sum);
                    ItemStack newStack = stack.clone();
                    newStack.setAmount(remaining.getAmount());
                    player.getInventory().setItem(i, newStack);
                }
                chestFull = true;
                break;
            }
        }

        // [DEBUG] FASE 3 - Feedback dettagliato al player
        int totalTransferred = depositedPerMaterial.values().stream().mapToInt(Integer::intValue).sum();
        String destLabel = isEnderChest ? "cassa dell'End" : "cassa del team";
        if (totalTransferred > 0) {
            player.updateInventory();
            // Aggiorna il block state solo per casse normali
            if (isChest && block.getState() instanceof Chest) ((Chest) block.getState()).update();


            StringJoiner joiner = new StringJoiner("§7; §f");
            for (Map.Entry<Material, Integer> entry : depositedPerMaterial.entrySet()) {
                if (entry.getValue() <= 0) continue;
                joiner.add("§e" + entry.getValue() + "§f di §e" + getMaterialDisplayName(entry.getKey()));
            }
            String suffix = chestFull ? " §c(cassa piena)" : "";
            player.sendMessage("§aDeposito rapido §7[" + destLabel + "]§r: §f" + joiner + suffix);
            print("[DEBUG] Quick deposit [" + destLabel + "]: " + player.getName() + " -> " + depositedPerMaterial);
        } else {
            player.sendMessage("§cNessuna valuta da depositare nella §e" + destLabel + (chestFull ? " §c(piena)" : "") + "§c.");
        }
    }

    /**
     * Restituisce il nome italiano del materiale per i messaggi in-game.
     *
     * @param material Materiale BedWars.
     * @return Nome leggibile in italiano.
     */
    private String getMaterialDisplayName(Material material) {
        switch (material) {
            case IRON_INGOT: return "Ferro";
            case GOLD_INGOT: return "Oro";
            case DIAMOND:    return "Diamante";
            case EMERALD:    return "Smeraldo";
            default:         return material.name();
        }
    }

    /**
     * Stampa debug solo se la modalita' debug e' attiva in config.
     *
     * @param msg Messaggio di debug.
     */
    private void print(String msg) {
        if (BedWars.config.getBoolean("debug")) {
            BedWars.plugin.getLogger().info(msg);
        }
    }
}
