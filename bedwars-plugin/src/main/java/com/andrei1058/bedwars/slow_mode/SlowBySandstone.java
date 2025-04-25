package com.andrei1058.bedwars.slow_mode;

import com.andrei1058.bedwars.API;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class SlowBySandstone implements Listener {
    public final static int maxNormSandstones = new API().getConfigs()
            .getMainConfig()
            .getInt(
                    ConfigPath.GENERAL_SLOW_MODE_SANDSTONE
            );

    public final static Material[] heavyBlocks = new Material[] {
            Material.CUT_SANDSTONE,
            Material.OBSIDIAN,
            Material.END_STONE,
            Material.END_STONE_BRICKS,
            Material.END_STONE_BRICK_STAIRS
    };

    @Getter
    private static final float SPEED_NORM = 0.2f;
    @Getter
    private static final float SPEED_SLOW = 0.1f;
    @Getter
    private static final float SPEED_NO_MOVE = 0.0f;

    private static void setSlow(Player player) {
//        System.out.printf("Player: %s Status: setSlow\n", player.getName());
        player.setWalkSpeed(SPEED_SLOW);
    }

    private static void removeSlow(Player player) {
//        System.out.printf("Player: %s Status: removeSlow\n", player.getName());
        player.setWalkSpeed(SPEED_NORM);
    }

    private static void setNoMove(Player player) {
//        System.out.printf("Player: %s Status: setNoMove\n", player.getName());
        player.setWalkSpeed(SPEED_NO_MOVE);
    }

    private static void removeNoMove(Player player) {
//        System.out.printf("Player: %s Status: removeNoMove\n", player.getName());
        player.setWalkSpeed(SPEED_NORM);
    }

    public static boolean isHeaveMaterial(Material material) {
        return Arrays.asList(heavyBlocks).contains(material);
    }

    public static int checkSandstone(Player player, int baseBlocks) {

        int count = baseBlocks;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && Arrays.asList(heavyBlocks).contains(item.getType())) {
                count += item.getAmount();
            }
        }

        ItemStack cursorItem = player.getItemOnCursor();
        if (cursorItem != null && isHeaveMaterial(cursorItem.getType())) {
            count += cursorItem.getAmount();
        }

        if (count > maxNormSandstones && count <= maxNormSandstones * 2) {
            return 2;
        } else if (count > maxNormSandstones * 2) {
            return 3;
        } else {
            return 1;
        }
    }

    public static int checkSandstone(Player player) {
        return checkSandstone(player, 0);
    }

    public static void updateStatus(Player player, int baseBlocks) {
        System.out.printf("----Player: %s Status: updateStatus\n", player.getName());
        int statusIndex = checkSandstone(player, baseBlocks);
        if (statusIndex == 1) {
            removeSlow(player);
            removeNoMove(player);
        } else if (statusIndex == 2) {
            removeNoMove(player);
            setSlow(player);
        } else if (statusIndex == 3) {
            removeSlow(player);
            setNoMove(player);
        }
    }

    public static void updateStatus(Player player) {
        updateStatus(player, 0);
    }

    public static boolean hasSpaceForItem(Player player, Material material) {
        Inventory inventory = player.getInventory();

        for (ItemStack item : inventory.getContents()) {
            if (item == null || item.getType() == Material.AIR) {
                return true;
            }

            if (item.getType() == material && item.getAmount() < item.getMaxStackSize()) {
                return true;
            }
        }

        return false;
    }
}
