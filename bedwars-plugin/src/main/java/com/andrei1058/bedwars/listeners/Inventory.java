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
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.server.SetupType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.SetupSession;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

import static com.andrei1058.bedwars.BedWars.nms;
import static org.bukkit.event.inventory.InventoryAction.HOTBAR_SWAP;
import static org.bukkit.event.inventory.InventoryAction.MOVE_TO_OTHER_INVENTORY;

public class Inventory implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (nms.getInventoryName(e).equalsIgnoreCase(SetupSession.getInvName())) {
            SetupSession ss = SetupSession.getSession(p.getUniqueId());
            if (ss != null) {
                if (ss.getSetupType() == null)
                    ss.cancel();
            }
        }
    }

    /**
     * Manage command-items when clicked in inventory
     */
    @EventHandler
    public void onCommandItemClick(InventoryClickEvent e) {
        //block moving from hotBar
        if (e.getAction() == HOTBAR_SWAP && e.getClick() == ClickType.NUMBER_KEY) {
            if (e.getHotbarButton() > -1) {
                ItemStack i = e.getWhoClicked().getInventory().getItem(e.getHotbarButton());
                if (i != null) {
                    if (isCommandItem(i)) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }

        //block moving cursor item outside
        if (e.getCursor() != null) {
            if (e.getCursor().getType() != Material.AIR) {
                if (e.getClickedInventory() == null) {
                    if (isCommandItem(e.getCursor())) {
                        e.getWhoClicked().closeInventory();
                        e.setCancelled(true);
                    }
                } else if (e.getClickedInventory().getType() != e.getWhoClicked().getInventory().getType()) {
                    if (isCommandItem(e.getCursor())) {
                        e.getWhoClicked().closeInventory();
                        e.setCancelled(true);
                    }
                } else {
                    if (isCommandItem(e.getCursor())) e.setCancelled(true);
                }
            }
        }

        //block moving current item outside
        if (e.getCurrentItem() != null) {
            if (e.getCurrentItem().getType() != Material.AIR) {
                if (e.getClickedInventory() == null) {
                    if (isCommandItem(e.getCurrentItem())) {
                        e.getWhoClicked().closeInventory();
                        e.setCancelled(true);
                    }
                } else if (e.getClickedInventory().getType() != e.getWhoClicked().getInventory().getType()) {
                    if (isCommandItem(e.getCurrentItem())) {
                        e.getWhoClicked().closeInventory();
                        e.setCancelled(true);
                    }
                } else {
                    if (isCommandItem(e.getCurrentItem())) e.setCancelled(true);
                }
            }
        }

        //block moving with shift
        if (e.getAction() == MOVE_TO_OTHER_INVENTORY) {
            if (isCommandItem(e.getCurrentItem())) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        //issue #225
        if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
            if (Arena.getArenaByPlayer((Player) e.getWhoClicked()) != null) {
                if (e.getWhoClicked().hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    e.getWhoClicked().closeInventory();
                    for (Player pl : e.getWhoClicked().getWorld().getPlayers()) {
                        BedWars.nms.hideArmor((Player) e.getWhoClicked(), pl);
                    }
                }
            }
        }

        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().

                getType() == Material.AIR) return;

        Player p = (Player) e.getWhoClicked();
        ItemStack i = e.getCurrentItem();

        /*//Prevent moving of command items
        if (nms.isCustomBedWarsItem(i)) {
            if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                e.setCancelled(true);
                return;
            }
            String[] customData = nms.getCustomData(i).split("_");
            if (customData.length >= 2) {
                if (customData[0].equals("RUNCOMMAND")) {
                    e.setCancelled(true);
                    return;
                }
            }
        }*/

        IArena a = Arena.getArenaByPlayer(p);
        if (a != null) {

            //Prevent players from moving items in stats GUI
            if (nms.getInventoryName(e).equals(Language.getMsg(p, Messages.PLAYER_STATS_GUI_INV_NAME).replace("{playername}", p.getName()).replace("{player}", p.getDisplayName()))) {
                e.setCancelled(true);
                return;
            }

            /* Make it so they can't toggle their armor */
            if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
                e.setCancelled(true);
                return;
            }
        }

        if (!i.hasItemMeta()) return;
        if (!i.getItemMeta().hasDisplayName()) return;
        if (BedWars.getServerType() == ServerType.MULTIARENA) {
            if (e.getWhoClicked().getLocation().getWorld().getName().equalsIgnoreCase(BedWars.getLobbyWorld())) {
                e.setCancelled(true);
            }
        }

        /* Check setup gui items */
        if (SetupSession.isInSetupSession(p.getUniqueId()) && nms.getInventoryName(e).equalsIgnoreCase(SetupSession.getInvName())) {
            SetupSession ss = SetupSession.getSession(p.getUniqueId());
            if (e.getSlot() == SetupSession.getAdvancedSlot()) {
                Objects.requireNonNull(ss).setSetupType(SetupType.ADVANCED);
            } else if (e.getSlot() == SetupSession.getAssistedSlot()) {
                Objects.requireNonNull(ss).setSetupType(SetupType.ASSISTED);
            }
            if (!Objects.requireNonNull(ss).startSetup()) {
                ss.getPlayer().sendMessage(ChatColor.RED + "Could not start setup session. Pleas check the console.");
            }
            p.closeInventory();
            return;
        }

        if (a != null) {
            if (a.isSpectator(p)) {
                e.setCancelled(true);
                //noinspection UnnecessaryReturnStatement
                return;
            }
        }
    }

    /**
     * Check if an item is command-item
     */
    private static boolean isCommandItem(ItemStack i) {
        if (i == null) return false;
        if (i.getType() == Material.AIR) return false;
        if (nms.isCustomBedWarsItem(i)) {
            String[] customData = nms.getCustomData(i).split("_");
            if (customData.length >= 2) {
                return customData[0].equals("RUNCOMMAND");
            }
        }
        return false;
    }

    @EventHandler
    public void onGameEnd(GameStateChangeEvent e) {
        if(e.getNewState() != GameState.restarting) return;
        e.getArena().getPlayers().forEach(Player::closeInventory); // close any open guis when the game ends (e.g. shop)
    }
}
