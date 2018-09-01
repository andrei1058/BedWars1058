package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.configuration.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.andrei1058.bedwars.Main.config;
import static com.andrei1058.bedwars.configuration.Language.getList;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class ArenaGUI {

    private static HashMap<Player, Inventory> refresh = new HashMap<>();
    private static int start = config.getYml().getInt("arenaGui.settings.startSlot"),
            end = config.getYml().getInt("arenaGui.settings.endSlot");
    private static boolean showPlaying = config.getYml().getBoolean("arenaGui.settings.showPlaying");
    private static YamlConfiguration yml = config.getYml();

    public static void refreshInv(Player p, Inventory inv) {
        int from = start, to = end;
        List<Arena> arenas = new ArrayList<>(Arena.getArenas());
        for (int x = 0; x < arenas.size(); x++) {
            ItemStack i;
            switch (arenas.get(x).getStatus()) {
                case waiting:
                    i = new ItemStack(Material.valueOf(yml.getString("arenaGui.waiting.itemStack")), 1, (short) yml.getInt("arenaGui.waiting.data"));
                    if (yml.getBoolean("arenaGui.waiting.enchanted")){
                        ItemMeta im = i.getItemMeta();
                        im.addEnchant(Enchantment.LURE, 1, true);
                        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        i.setItemMeta(im);
                    }
                    break;
                case playing:
                    if (!showPlaying) {
                        arenas.get(x).setSlot(-1);
                        continue;
                    }
                    i = new ItemStack(Material.valueOf(yml.getString("arenaGui.playing.itemStack")), 1, (short) yml.getInt("arenaGui.playing.data"));
                    if (yml.getBoolean("arenaGui.playing.enchanted")){
                        ItemMeta im = i.getItemMeta();
                        im.addEnchant(Enchantment.LURE, 1, true);
                        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        i.setItemMeta(im);
                    }
                    break;
                case starting:
                    i = new ItemStack(Material.valueOf(yml.getString("arenaGui.starting.itemStack")), 1, (short) yml.getInt("arenaGui.starting.data"));
                    if (yml.getBoolean("arenaGui.playing.enchanted")){
                        ItemMeta im = i.getItemMeta();
                        im.addEnchant(Enchantment.LURE, 1, true);
                        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        i.setItemMeta(im);
                    }
                    break;
                default:
                    arenas.get(x).setSlot(-1);
                    continue;
            }
            ItemMeta im = i.getItemMeta();
            im.setDisplayName(getMsg(p, Messages.ARENA_GUI_ARENA_CONTENT_NAME).replace("{name}", arenas.get(x).getDisplayName()));
            List<String> lore = new ArrayList<>();
            for (String s : getList(p, Messages.ARENA_GUI_ARENA_CONTENT_LORE)) {
                if (!(s.contains("{group}") && arenas.get(x).getGroup().equalsIgnoreCase("default"))) {
                    lore.add(s.replace("{on}", String.valueOf(arenas.get(x).getPlayers().size())).replace("{max}",
                            String.valueOf(arenas.get(x).getMaxPlayers())).replace("{status}", arenas.get(x).getDisplayStatus())
                            .replace("{group}", arenas.get(x).getGroup()));
                }
            }
            im.setLore(lore);
            i.setItemMeta(im);
            if (to >= from) {
                inv.setItem(from, i);
                arenas.get(x).setSlot(from);
                from++;
            }
        }
    }

    public static void openGui(Player p) {
        int size = config.getYml().getInt("arenaGui.settings.size");
        if (size % 9 != 0) size = 27;
        if (size > 54) size = 54;
        Inventory inv = Bukkit.createInventory(p, size, getMsg(p, Messages.ARENA_GUI_INV_NAME));
        refreshInv(p, inv);
        refresh.put(p, inv);
        p.openInventory(inv);
    }

    public static HashMap<Player, Inventory> getRefresh() {
        return refresh;
    }
}
