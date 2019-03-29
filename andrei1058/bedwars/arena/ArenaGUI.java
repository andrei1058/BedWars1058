package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.language.Messages;
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

public class ArenaGUI {

    //Object[0] = inventory, Object[1] = group
    private static HashMap<Player, Object[]> refresh = new HashMap<>();
    private static YamlConfiguration yml = Main.config.getYml();

    //Object[0] = inventory, Object[1] = group
    public static void refreshInv(Player p, Object[] data) {

        List<Arena> arenas;
        if (((String)data[1]).equalsIgnoreCase("default")) {
            arenas = new ArrayList<>(Arena.getArenas());
        } else {
            arenas = new ArrayList<>();
            for (Arena a : Arena.getArenas()){
                if (a.getGroup().equals(data[1])) arenas.add(a);
            }
        }

        Collections.sort(arenas);

        int arenaKey = 0;
        for (String useSlot : Main.config.getString(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_USE_SLOTS).split(",")) {
            int slot;
            try {
                slot = Integer.parseInt(useSlot);
            } catch (Exception e) {
                continue;
            }
            ItemStack i;
            ((Inventory)data[0]).setItem(slot, new ItemStack(Material.AIR));
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

            i = Main.nms.createItemStack(yml.getString(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_MATERIAL.replace("%path%", status)),
                    1, (short) yml.getInt(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_DATA.replace("%path%", status)));
            if (yml.getBoolean(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_ENCHANTED.replace("%path%", status))) {
                ItemMeta im = i.getItemMeta();
                im.addEnchant(Enchantment.LURE, 1, true);
                im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                i.setItemMeta(im);
            }


            ItemMeta im = i.getItemMeta();
            im.setDisplayName(Language.getMsg(p, Messages.ARENA_GUI_ARENA_CONTENT_NAME).replace("{name}", arenas.get(arenaKey).getDisplayName()));
            List<String> lore = new ArrayList<>();
            for (String s : Language.getList(p, Messages.ARENA_GUI_ARENA_CONTENT_LORE)) {
                if (!(s.contains("{group}") && arenas.get(arenaKey).getGroup().equalsIgnoreCase("default"))) {
                    lore.add(s.replace("{on}", String.valueOf(arenas.get(arenaKey).getGroup())).replace("{max}",
                            String.valueOf(arenas.get(arenaKey).getMaxPlayers())).replace("{status}", arenas.get(arenaKey).getDisplayStatus(Language.getPlayerLanguage(p)))
                            .replace("{group}", arenas.get(arenaKey).getGroup()));
                }
            }
            im.setLore(lore);
            i.setItemMeta(im);
            i = Main.nms.addCustomData(i, ArenaSelectorListener.ARENA_SELECTOR_GUI_IDENTIFIER + arenas.get(arenaKey).getWorldName());
            ((Inventory)data[0]).setItem(slot, i);
            arenaKey++;
        }
    }

    public static void openGui(Player p, String group) {
        int size = Main.config.getYml().getInt(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_SIZE);
        if (size % 9 != 0) size = 27;
        if (size > 54) size = 54;
        Inventory inv = Bukkit.createInventory(p, size, Language.getMsg(p, Messages.ARENA_GUI_INV_NAME));

        ItemStack i = Main.nms.createItemStack(Main.config.getString(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_MATERIAL.replace("%path%", "skipped-slot")),
                1, (byte) Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_DATA.replace("%path%", "skipped-slot")));

        for (int x = 0; x < inv.getSize(); x++) {
            inv.setItem(x, i);
        }

        refresh.put(p, new Object[]{inv, group});
        refreshInv(p, new Object[]{inv, group});
        p.openInventory(inv);
    }

    public static HashMap<Player, Object[]> getRefresh() {
        return refresh;
    }
}
