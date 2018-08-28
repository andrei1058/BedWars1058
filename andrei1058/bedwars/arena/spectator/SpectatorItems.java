package com.andrei1058.bedwars.arena.spectator;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Messages;
import com.andrei1058.bedwars.exceptions.InvalidMaterialException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.andrei1058.bedwars.configuration.Language.getList;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class SpectatorItems {

    public static final String NBT_SPECTATOR_TELEPORTER_ITEM = "spectatorTeleportItem";
    public static final String NBT_SPECTATOR_LEAVE_ITEM = "spectatorLeaveItem";

    /**
     * Give Teleporter Item to spectator, if enabled
     *
     * @since API 9
     */
    public static void giveTeleporter(Player p) {
        if (!Main.config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEM_TELEPORTER_ENABLED)) return;
        ItemStack i = null;
        try {
            i = Misc.createItemStack(Main.config.getString(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEM_TELEPORTER_MATERIAL),
                    Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEM_TELEPORTER_DATA),
                    getMsg(p, Messages.ARENA_SPECTATOR_TELEPORTER_ITEM_NAME), getList(p, Messages.ARENA_SPECTATOR_TELEPORTER_ITEM_LORE),
                    Main.config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEM_TELEPORTER_ENCHANTED),
                    NBT_SPECTATOR_TELEPORTER_ITEM);
        } catch (InvalidMaterialException e) {
            Main.plugin.getLogger().severe(e.getMessage());
            Main.plugin.getLogger().severe("Please fix it in config.yml!");
        }
        if (i == null) return;
        p.getInventory().setItem(Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEM_TELEPORTER_SLOT), i);
    }

    /**
     * Give Leave Item to spectator, if enabled
     *
     * @since API 9
     */
    public static void giveLeaveItem(Player p) {
        if (!Main.config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEM_LEAVE_ENABLED)) return;
        ItemStack i = null;
        try {
            i = Misc.createItemStack(Main.config.getString(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEM_LEAVE_MATERIAL),
                    Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEM_LEAVE_DATA),
                    getMsg(p, Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME), getList(p, Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE),
                    Main.config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEM_LEAVE_ENCHANTED),
                    NBT_SPECTATOR_LEAVE_ITEM);
        } catch (InvalidMaterialException e) {
            Main.plugin.getLogger().severe(e.getMessage());
            Main.plugin.getLogger().severe("Please fix it in config.yml!");
        }
        if (i == null) return;
        p.getInventory().setItem(Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEM_LEAVE_SLOT), i);
    }
}
