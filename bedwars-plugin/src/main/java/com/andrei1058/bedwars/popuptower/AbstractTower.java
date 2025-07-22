package com.andrei1058.bedwars.popuptower;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.configuration.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

/**
 * @author An5w1r@163.com
 */
public abstract class AbstractTower {
    private static final int BLOCKS_PER_TICK = 2;
    private static final long TICK_DELAY = 1L;

    private BukkitTask buildTask;

    public AbstractTower(Location location, Block block, TeamColor color, Player player) {
        consumeItemFromHand(player);
        List<String> coordinates = getTowerCoordinates();
        startBuilding(coordinates, block, location, color, player);
    }

    protected abstract List<String> getTowerCoordinates();

    private void consumeItemFromHand(Player player) {
        ItemStack itemInHand = BedWars.nms.getItemInHand(player);
        if (itemInHand == null) return;

        if (itemInHand.getAmount() > 1) {
            itemInHand.setAmount(itemInHand.getAmount() - 1);
        } else {
            player.setItemInHand(null);
        }
    }

    private void startBuilding(List<String> coordinates, Block block, Location location, TeamColor color, Player player) {
        final int[] currentIndex = {0};
        final int totalBlocks = coordinates.size();

        this.buildTask = Bukkit.getScheduler().runTaskTimer(
                BedWars.plugin,
                () -> {
                    Sounds.playsoundArea("pop-up-tower-build", location, 1.0F, 0.5F);

                    for (int i = 0; i < BLOCKS_PER_TICK && currentIndex[0] < totalBlocks; i++) {
                        String coordinate = coordinates.get(currentIndex[0]);
                        placeBlock(coordinate, block, color, player);
                        currentIndex[0]++;
                    }

                    if (currentIndex[0] >= totalBlocks) {
                        this.buildTask.cancel();
                    }
                },
                0L,
                TICK_DELAY
        );
    }

    private void placeBlock(String coordinate, Block chest, TeamColor color, Player player) {
        if (coordinate.contains("ladder")) {
            String[] parts = coordinate.split("ladder");
            int ladderData = Integer.parseInt(parts[1]);
            new NewPlaceBlock(chest, coordinate, color, player, true, ladderData);
        } else {
            new NewPlaceBlock(chest, coordinate, color, player, false, 0);
        }
    }
}