package com.andrei1058.bedwars.z_myadditions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class EPG_Listener implements Listener {

    private final JavaPlugin plugin;

    EPG_Listener(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        System.out.println("Взрыв!");
//        event.blockList().removeIf(
//                (Block block) -> {
//                    switch (block.getType()) {
//                        case LIME_STAINED_GLASS:
//                            transBlock(block);
//                            return true;
//                        case YELLOW_STAINED_GLASS:
//                            return true;
//                        default:
//                            return false;
//                    }
//                }
//        );
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (
            event.getAction() == Action.RIGHT_CLICK_BLOCK
            && event.getClickedBlock().getType() == Material.RED_STAINED_GLASS
            && event.getItem() != null
            && event.getItem().getType() == Material.LIME_STAINED_GLASS
            && !event.getPlayer().isSneaking()
        )
        {
            event.setCancelled(true);

            ItemStack itemInHand = event.getItem();
            itemInHand.setAmount(itemInHand.getAmount() - 1);

            event.getClickedBlock().setType(Material.LIME_STAINED_GLASS);
        }
    }

/*
    @EventHandler
    private void onProjectileHit(final ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            System.out.println("Стрела!");
            Block block = event.getEntity().getLocation().getBlock();
            switch (block.getType()) {
                case LIME_STAINED_GLASS:
                    transBlock(block);
                    event.getEntity().remove();
                    break;
                case RED_STAINED_GLASS:
                    block.setType(Material.AIR);
                    event.getEntity().remove();
                    break;
                case YELLOW_STAINED_GLASS:
                    event.getEntity().remove();
                    break;
            }
        }
    }
*/

//    private void transBlock(Block block) {
//        block.getLocation().getBlock().setType(Material.YELLOW_STAINED_GLASS);
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                block.setType(Material.RED_STAINED_GLASS);
//            }
//        }.runTaskLater(plugin, 60L);
//    }

}
