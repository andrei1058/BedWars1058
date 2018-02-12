package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.configuration.Language;
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
        for (Arena a : Arena.getArenas()) {
            ItemStack i;
            switch (a.getStatus()) {
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
                        a.setSlot(-1);
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
                    a.setSlot(-1);
                    continue;
            }
            ItemMeta im = i.getItemMeta();
            im.setDisplayName(getMsg(p, Language.aGuiArenaName).replace("{name}", a.getDisplayName()));
            List<String> lore = new ArrayList<>();
            for (String s : getList(p, Language.aGuiArenaLore)) {
                if (!(s.contains("{group}") && a.getGroup().equalsIgnoreCase("default"))) {
                    lore.add(s.replace("{on}", String.valueOf(a.getPlayers().size())).replace("{max}",
                            String.valueOf(a.getMaxPlayers())).replace("{status}", a.getDisplayStatus())
                            .replace("{group}", a.getGroup()));
                }
            }
            im.setLore(lore);
            i.setItemMeta(im);
            if (to > from) {
                inv.setItem(from, i);
                a.setSlot(from);
                from++;
            }
        }
    }

    public static void openGui(Player p) {
        int size = config.getYml().getInt("arenaGui.settings.size");
        if (size % 9 != 0) size = 27;
        if (size > 54) size = 54;
        Inventory inv = Bukkit.createInventory(p, size, getMsg(p, Language.arenaGuiInvName));
        refreshInv(p, inv);
        refresh.put(p, inv);
        p.openInventory(inv);
    }

    public static HashMap<Player, Inventory> getRefresh() {
        return refresh;
    }
}
