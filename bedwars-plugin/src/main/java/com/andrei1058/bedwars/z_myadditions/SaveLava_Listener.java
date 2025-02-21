package com.andrei1058.bedwars.z_myadditions;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Levelled;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class SaveLava_Listener implements Listener {

    private final JavaPlugin plugin;

    int[] pos = new int[3];

    SaveLava_Listener(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        BlockState replacedBlock = event.getBlockReplacedState(); // Получаем заменённый блок

        if (replacedBlock.getType() == Material.LAVA) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("&cНельзя убрать лаву!");
        }
    }

    @EventHandler
    public void BlockFormEvent(BlockFormEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.LAVA) {

//            System.getLogger("SaveLava").log(System.Logger.Level.DEBUG, "ЛАВУ ПЫТАЮТСЯ ПРЕОБРАЗОВАТЬ!!!");

            int oldLevel = ((Levelled) block.getBlockData()).getLevel();

            pos[0] = block.getX();
            pos[1] = block.getY();
            pos[2] = block.getZ();

            World world = event.getBlock().getWorld();

            SaveLava_DeleteWater.deleteWater(pos, world);

            event.setCancelled(true);

            block.setType(Material.LAVA);

            Levelled lavaData = (Levelled) block.getBlockData();
            lavaData.setLevel(oldLevel);
            block.setBlockData(lavaData);
        }
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        if (event.getBucket() == Material.WATER_BUCKET) {
            Block block = event.getBlockClicked().getRelative(event.getBlockFace());

            if (block.getType() == Material.LAVA) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("Ты не можешь заливать лаву водой!");
            }
        }
    }

}
