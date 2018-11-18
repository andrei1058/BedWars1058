package com.andrei1058.bedwars.arena.spectator;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.configuration.language.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.andrei1058.bedwars.Main.nms;
import static com.andrei1058.bedwars.configuration.Language.getList;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class TeleporterGUI {

    //Don't remove "_" because it's used as a separator somewhere
    public static final String NBT_SPECTATOR_TELEPORTER_GUI_HEAD = "spectatorTeleporterGUIhead_";

    private static HashMap<Player, Inventory> refresh = new HashMap<>();

    /**
     * Refresh the Teleporter GUI for a player
     */
    public static void refreshInv(Player p, Inventory inv) {
        if (p.getOpenInventory() == null) return;
        Arena arena = Arena.getArenaByPlayer(p);
        if (arena == null) {
            p.closeInventory();
            return;
        }
        List<Player> players = arena.getPlayers();
        for (int i = 0; i < inv.getSize(); i++) {
            if (i < players.size()) {
                inv.setItem(i, createHead(players.get(i), p));
            } else {
                inv.setItem(i, new ItemStack(Material.AIR));
            }
        }
    }

    /**
     * Opens the Teleporter GUI to a Player
     *
     * @since API 9
     */
    public static void openGUI(Player p) {
        Arena arena = Arena.getArenaByPlayer(p);
        if (arena == null) return;
        int size = arena.getPlayers().size();
        if (size <= 9) {
            size = 9;
        } else if (size > 9 && size <= 18) {
            size = 18;
        } else if (size > 19 && size <= 27) {
            size = 27;
        } else if (size > 27 && size <= 36) {
            size = 36;
        } else if (size > 36 && size <= 45) {
            size = 45;
        } else {
            size = 54;
        }
        Inventory inv = Bukkit.createInventory(p, size, getMsg(p, Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME));
        refreshInv(p, inv);
        refresh.put(p, inv);
        p.openInventory(inv);
    }

    /**
     * Get a HashMap of players with Teleporter GUI opened
     *
     * @since API 9
     */
    public static HashMap<Player, Inventory> getRefresh() {
        return refresh;
    }

    /**
     * Refresh the Teleporter GUI for all players with it opened
     *
     * @since API 9
     */
    public static void refreshAllGUIs() {
        for (Map.Entry<Player, Inventory> e : new HashMap<>(getRefresh()).entrySet()) {
            refreshInv(e.getKey(), e.getValue());
        }
    }

    /**
     * Create a player head
     */
    private static ItemStack createHead(Player targetPlayer, Player GUIholder) {
        ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        i = nms.setSkullOwner(i, targetPlayer);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(getMsg(GUIholder, Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME)
                .replace("{prefix}", Main.getChatSupport().getPrefix(targetPlayer))
                .replace("{suffix}", Main.getChatSupport().getSuffix(targetPlayer))
                        .replace("{player}", targetPlayer.getDisplayName()));
        List<String> lore = new ArrayList<>();
        String health = String.valueOf((int)targetPlayer.getHealth() * 100 / targetPlayer.getHealthScale());
        for (String s : getList(GUIholder, Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE)) {
            lore.add(s.replace("{health}", health).replace("{food}", String.valueOf(targetPlayer.getFoodLevel())));
        }
        im.setLore(lore);
        i.setItemMeta(im);
        return nms.addCustomData(i, NBT_SPECTATOR_TELEPORTER_GUI_HEAD + targetPlayer.getName());
    }

    /**
     * Remove a player from the refresh list and close gui
     *
     * @since API 9
     */
    public static void closeGUI(Player p) {
        if (getRefresh().containsKey(p)) {
            refresh.remove(p);
            if (p.getOpenInventory() != null) p.closeInventory();
        }
    }
}
