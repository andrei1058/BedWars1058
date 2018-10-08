package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Messages;
import com.andrei1058.bedwars.listeners.arenaselector.ArenaSelectorListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

import static com.andrei1058.bedwars.Main.config;
import static com.andrei1058.bedwars.Main.nms;
import static com.andrei1058.bedwars.configuration.Language.getList;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class ArenaGUI {

    private static HashMap<Player, Inventory> refresh = new HashMap<>();
    private static YamlConfiguration yml = config.getYml();

    public static void refreshInv(Player p, Inventory inv) {

        List<Arena> arenas = new ArrayList<>(Arena.getArenas()).stream().filter(a -> a.getStatus() != GameState.restarting).sorted((a1, a2) -> {
            if (a1.getStatus() == GameState.starting && a2.getStatus() == GameState.starting) {
                if (a1.getPlayers().size() > a2.getPlayers().size()) {
                    return -1;
                }
                if (a1.getPlayers().size() == a2.getPlayers().size()) {
                    return 0;
                } else return 1;
            } else if (a1.getStatus() == GameState.starting && a2.getStatus() != GameState.starting) {
                return -1;
            } else if (a2.getStatus() == GameState.starting && a1.getStatus() != GameState.starting) {
                return 1;
            } else if (a1.getStatus() == GameState.waiting && a2.getStatus() == GameState.waiting) {
                if (a1.getPlayers().size() > a2.getPlayers().size()) {
                    return -1;
                }
                if (a1.getPlayers().size() == a2.getPlayers().size()) {
                    return 0;
                } else return 1;
            } else if (a1.getStatus() == GameState.waiting && a2.getStatus() != GameState.waiting) {
                return -1;
            } else if (a2.getStatus() == GameState.waiting && a1.getStatus() != GameState.waiting) {
                return 1;
            } else if (a1.getStatus() == GameState.playing && a2.getStatus() == GameState.playing) {
                return 0;
            } else if (a1.getStatus() == GameState.playing && a2.getStatus() != GameState.playing) {
                return -1;
            } else return 1;
        }).collect(Collectors.toList());

        int arenaKey = 0;
        for (String useSlot : config.getString(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_USE_SLOTS).split(",")) {
            int slot;
            try {
                slot = Integer.parseInt(useSlot);
            } catch (Exception e) {
                continue;
            }
            ItemStack i;
            inv.setItem(slot, new ItemStack(Material.AIR));
            if (arenaKey >= arenas.size()) {
                continue;
            }

            switch (arenas.get(arenaKey).getStatus()) {
                case waiting:
                    i = nms.createItemStack(yml.getString("arenaGui.waiting.itemStack"), 1, (short) yml.getInt("arenaGui.waiting.data"));
                    if (yml.getBoolean("arenaGui.waiting.enchanted")) {
                        ItemMeta im = i.getItemMeta();
                        im.addEnchant(Enchantment.LURE, 1, true);
                        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        i.setItemMeta(im);
                    }
                    break;
                case playing:
                    if (!config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_SHOW_PLAYING)) continue;
                    i = nms.createItemStack(yml.getString("arenaGui.playing.itemStack"), 1, (short) yml.getInt("arenaGui.playing.data"));
                    if (yml.getBoolean("arenaGui.playing.enchanted")) {
                        ItemMeta im = i.getItemMeta();
                        im.addEnchant(Enchantment.LURE, 1, true);
                        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        i.setItemMeta(im);
                    }
                    break;
                case starting:
                    i = nms.createItemStack(yml.getString("arenaGui.starting.itemStack"), 1, (short) yml.getInt("arenaGui.starting.data"));
                    if (yml.getBoolean("arenaGui.playing.enchanted")) {
                        ItemMeta im = i.getItemMeta();
                        im.addEnchant(Enchantment.LURE, 1, true);
                        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        i.setItemMeta(im);
                    }
                    break;
                default:
                    continue;
            }
            ItemMeta im = i.getItemMeta();
            im.setDisplayName(getMsg(p, Messages.ARENA_GUI_ARENA_CONTENT_NAME).replace("{name}", arenas.get(arenaKey).getDisplayName()));
            List<String> lore = new ArrayList<>();
            for (String s : getList(p, Messages.ARENA_GUI_ARENA_CONTENT_LORE)) {
                if (!(s.contains("{group}") && arenas.get(arenaKey).getGroup().equalsIgnoreCase("default"))) {
                    lore.add(s.replace("{on}", String.valueOf(arenas.get(arenaKey).getPlayers().size())).replace("{max}",
                            String.valueOf(arenas.get(arenaKey).getMaxPlayers())).replace("{status}", arenas.get(arenaKey).getDisplayStatus())
                            .replace("{group}", arenas.get(arenaKey).getGroup()));
                }
            }
            im.setLore(lore);
            i.setItemMeta(im);
            i = Main.nms.addCustomData(i, ArenaSelectorListener.ARENA_SELECTOR_GUI_IDENTIFIER + arenas.get(arenaKey).getWorldName());
            inv.setItem(slot, i);
            arenaKey++;
        }
    }

    public static void openGui(Player p) {
        int size = config.getYml().getInt(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_SIZE);
        if (size % 9 != 0) size = 27;
        if (size > 54) size = 54;
        Inventory inv = Bukkit.createInventory(p, size, getMsg(p, Messages.ARENA_GUI_INV_NAME));
        ItemStack i = nms.createItemStack(Main.config.getString("arenaGui.skippedSlot.itemStack"), 1, (byte) Main.config.getInt("arenaGui.skippedSlot.data"));

        for (int x = 0; x < inv.getSize(); x++) {
            inv.setItem(x, i);
        }

        refreshInv(p, inv);
        refresh.put(p, inv);
        p.openInventory(inv);
    }

    public static HashMap<Player, Inventory> getRefresh() {
        return refresh;
    }
}
