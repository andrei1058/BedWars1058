package com.andrei1058.bedwars.z_myadditions;

import com.andrei1058.bedwars.arena.team.BedWarsTeam;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class EPG_Listener implements Listener {

    private final JavaPlugin plugin;

    EPG_Listener(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (
            event.getAction() == Action.RIGHT_CLICK_BLOCK
            && event.getClickedBlock() != null
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

    @EventHandler
    private void onProjectileHit(final ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {

            Block block = event.getHitBlock();
            if (block == null) return;

            switch (block.getType()) {
                case LIME_STAINED_GLASS:
                    EPG_glassActions.transBlock(plugin, block);
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

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Block block2 = block.getLocation().subtract(0, 1, 0).getBlock();
        if (
            block.getType() == Material.SNOW
            && (
                block2.getType() == Material.SNOW
                || block2.getType() == Material.AIR
            )
        ) {
            event.setCancelled(true);
        }
    }

}
