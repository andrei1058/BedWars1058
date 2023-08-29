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

package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.configuration.Sounds;
import com.andrei1058.bedwars.listeners.arenaselector.ArenaSelectorListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ArenaGUI {

    //Object[0] = inventory, Object[1] = group
    //private static HashMap<Player, Object[]> refresh = new HashMap<>();
    private static YamlConfiguration yml = BedWars.config.getYml();

    private static HashMap<UUID, Long> antiCalledTwice = new HashMap<>();

    //Object[0] = inventory, Object[1] = group
    public static void refreshInv(Player p, IArena arena, int players) {
        if (p == null) return;
        if (p.getOpenInventory() == null) return;
        if (!(p.getOpenInventory().getTopInventory().getHolder() instanceof ArenaSelectorHolder)) return;
        ArenaSelectorHolder ash = ((ArenaSelectorHolder) p.getOpenInventory().getTopInventory().getHolder());

        List<IArena> arenas;
        if (ash.getGroup().equalsIgnoreCase("default")) {
            arenas = new ArrayList<>(Arena.getArenas());
        } else {
            arenas = new ArrayList<>();
            for (IArena a : Arena.getArenas()) {
                if (a.getGroup().equalsIgnoreCase(ash.getGroup())) arenas.add(a);
            }
        }

        arenas = Arena.getSorted(arenas);

        int arenaKey = 0;
        for (Integer slot : getUsedSlots()) {
            ItemStack i;
            p.getOpenInventory().getTopInventory().setItem(slot, new ItemStack(Material.AIR));
            if (arenaKey >= arenas.size()) {
                continue;
            }

            String status;
            switch (arenas.get(arenaKey).getStatus()) {
                case waiting:
                    status = "waiting";
                    break;
                case playing:
                    status = "playing";
                    break;
                case starting:
                    status = "starting";
                    break;
                default:
                    continue;
            }

            i = BedWars.nms.createItemStack(yml.getString(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_MATERIAL.replace("%path%", status)),
                    1, (short) yml.getInt(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_DATA.replace("%path%", status)));
            if (yml.getBoolean(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_ENCHANTED.replace("%path%", status))) {
                ItemMeta im = i.getItemMeta();
                im.addEnchant(Enchantment.LURE, 1, true);
                im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                i.setItemMeta(im);
            }


            ItemMeta im = i.getItemMeta();
            im.setDisplayName(Language.getMsg(p, Messages.ARENA_GUI_ARENA_CONTENT_NAME).replace("{name}", arenas.get(arenaKey).getDisplayName()).replace("{map_name}", arenas.get(arenaKey).getArenaName()));
            List<String> lore = new ArrayList<>();
            for (String s : Language.getList(p, Messages.ARENA_GUI_ARENA_CONTENT_LORE)) {
                if (!(s.contains("{group}") && arenas.get(arenaKey).getGroup().equalsIgnoreCase("default"))) {
                    lore.add(s.replace("{on}", String.valueOf(arena != null ? arena == arenas.get(arenaKey) ? players : arenas.get(arenaKey).getPlayers().size() : arenas.get(arenaKey).getPlayers().size())).replace("{max}",
                                    String.valueOf(arenas.get(arenaKey).getMaxPlayers())).replace("{status}", arenas.get(arenaKey).getDisplayStatus(Language.getPlayerLanguage(p)))
                            .replace("{group}", arenas.get(arenaKey).getDisplayGroup(p)));
                }
            }
            im.setLore(lore);
            i.setItemMeta(im);
            i = BedWars.nms.addCustomData(i, ArenaSelectorListener.ARENA_SELECTOR_GUI_IDENTIFIER + arenas.get(arenaKey).getArenaName());
            p.getOpenInventory().getTopInventory().setItem(slot, i);
            arenaKey++;
        }
        p.updateInventory();
    }

    public static void openGui(Player p, String group) {
        if (preventCalledTwice(p)) return;
        updateCalledTwice(p);
        int size = BedWars.config.getYml().getInt(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_SIZE);
        if (size % 9 != 0) size = 27;
        if (size > 54) size = 54;
        ArenaSelectorHolder ash = new ArenaSelectorHolder(group);
        Inventory inv = Bukkit.createInventory(ash, size, Language.getMsg(p, Messages.ARENA_GUI_INV_NAME));
        //ash.setInv(inv);

        String skippedSlotMaterial = BedWars.config.getString(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_MATERIAL.replace("%path%", "skipped-slot"));
        if (!skippedSlotMaterial.equalsIgnoreCase("none") && !skippedSlotMaterial.equalsIgnoreCase("air")) {
            ItemStack i = BedWars.nms.createItemStack(skippedSlotMaterial,
                    1, (byte) BedWars.config.getInt(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_DATA.replace("%path%", "skipped-slot")));
            i = BedWars.nms.addCustomData(i, "RUNCOMMAND_bw join random");
            ItemMeta im = i.getItemMeta();
            assert im != null;
            im.setDisplayName(ChatColor.translateAlternateColorCodes(
                    '&',
                    Language.getMsg(p, Messages.ARENA_GUI_SKIPPED_ITEM_NAME)
                            .replaceAll(
                                    "\\{serverIp}",
                                    BedWars.config.getString(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP)
                            )
                            .replaceAll(
                                    "\\{poweredBy}",
                                    BedWars.config.getString(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_POWERED_BY)
                            )
            ));
            List<String> lore = new ArrayList<>();
            for (String line : Language.getList(p, Messages.ARENA_GUI_SKIPPED_ITEM_LORE)) {
                line = line
                        .replaceAll(
                                "\\{serverIp}",
                                BedWars.config.getString(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP)
                        )
                        .replaceAll(
                                "\\{poweredBy}",
                                BedWars.config.getString(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_POWERED_BY)
                        );
                lore.add(line);
            }
            if (lore.size() > 0) {
                im.setLore(lore);
            }
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            i.setItemMeta(im);

            List<Integer> used = getUsedSlots();
            for (int x = 0; x < inv.getSize(); x++) {
                if (used.contains(x)) continue;
                inv.setItem(x, i);
            }
        }

        p.openInventory(inv);
        refreshInv(p, null, 0);
        //refresh.put(p, new Object[]{inv, group});
        Sounds.playSound("arena-selector-open", p);
    }

    public static class ArenaSelectorHolder implements InventoryHolder {

        private String group;
        //private Inventory inv;

        public ArenaSelectorHolder(String group) {
            this.group = group;
        }

        public String getGroup() {
            return group;
        }

        @Override
        public Inventory getInventory() {
            return null;
        }

        /*public void setInv(Inventory inv) {
            this.inv = inv;
        }*/
    }

    @NotNull
    private static List<Integer> getUsedSlots() {
        List<Integer> ls = new ArrayList<>();
        for (String useSlot : BedWars.config.getString(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_USE_SLOTS).split(",")) {
            try {
                int slot = Integer.parseInt(useSlot);
                ls.add(slot);
            } catch (Exception ignored) {
            }
        }
        return ls;
    }

    private static boolean preventCalledTwice(@NotNull Player player) {
        return antiCalledTwice.getOrDefault(player.getUniqueId(), 0L) > System.currentTimeMillis();
    }

    private static void updateCalledTwice(@NotNull Player player) {
        if (antiCalledTwice.containsKey(player.getUniqueId())) {
            antiCalledTwice.replace(player.getUniqueId(), System.currentTimeMillis() + 2000);
        } else {
            antiCalledTwice.put(player.getUniqueId(), System.currentTimeMillis() + 2000);
        }
    }
}
