package com.andrei1058.bedwars.slow_mode;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static com.andrei1058.bedwars.BedWars.plugin;

public class SlowBySandstone_Listener implements Listener {

    private static Set<Player> playerCooldownGive = new HashSet<>();
    private static Set<Player> playerCooldownJump = new HashSet<>();

    private static JavaPlugin plugin;

    public static void onEnable(JavaPlugin plugin) {
        SlowBySandstone_Listener.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new SlowBySandstone_Listener(), plugin);
    }

    @EventHandler
    public void onDropHeavyBlocks(PlayerDropItemEvent event) {
        if (!SlowMode.isSlowMode()) return;

        if (Arrays.asList(SlowBySandstone.heavyBlocks).contains(event.getItemDrop().getItemStack().getType())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if (!SlowMode.isSlowMode()) return;
        if (playerCooldownGive.contains(event.getPlayer())) return;
        if (!(event.getRightClicked() instanceof Player)) return;

        Player clickedPlayer = (Player) event.getRightClicked();
        Player player = event.getPlayer();
        IArena arena = Arena.getArenaByPlayer(player);

        if (arena == null) return;

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (!SlowBySandstone.isHeaveMaterial(itemInHand.getType())) return;

        if (SlowBySandstone.hasSpaceForItem(clickedPlayer, itemInHand.getType())) {
            itemInHand.setAmount(itemInHand.getAmount() - 1);

            ItemStack item = new ItemStack(itemInHand.getType(), 1);
            clickedPlayer.getInventory().addItem(item);
        };

        SlowBySandstone.updateStatus(player);
        SlowBySandstone.updateStatus(clickedPlayer);

        cooldownGiveBlocks(player);
    }

    private void cooldownGiveBlocks(Player player) {
        playerCooldownGive.add(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                playerCooldownGive.remove(player);
                this.cancel();
            }
        }.runTaskTimer(plugin, 20L, 5L);
    }

    @EventHandler
    public void onPlayerToggleSprintEvent(PlayerToggleSprintEvent event) {
        if (!SlowMode.isSlowMode()) return;
        if (event.getPlayer().getWalkSpeed() < 0.2f && event.isSprinting()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!SlowMode.isSlowMode()) return;
        if (event.getFrom().getX() == event.getTo().getX() && event.getFrom().getZ() == event.getTo().getZ()) {
            return;
        }

        Player player = event.getPlayer();
        if (player.isSprinting() && player.getWalkSpeed() == 0f) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent event) {
        if (!SlowMode.isSlowMode()) return;

//        System.out.printf("Jump: from=%s to=%s\n", event.getFrom(), event.getTo());

        Player player = event.getPlayer();

//        System.out.println(playerCooldownJump.toString());
//        System.out.println(player.getWalkSpeed());
//        System.out.println((player.getWalkSpeed() == 0.1f));

        if (player.getWalkSpeed() == 0f) {
//            System.out.println("No Jump");
            event.setCancelled(true);
        } else if ((player.getWalkSpeed() == 0.1f) && playerCooldownJump.contains(player)) {
            event.setCancelled(true);
//            System.out.println("No Jump");
        }
        else {
            cooldownSlowJump(player);
        }
    }

    private void cooldownSlowJump(Player player) {
        playerCooldownJump.add(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                playerCooldownJump.remove(player);
            }
        }.runTaskLater(plugin, 55L);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                SlowBySandstone.updateStatus(event.getPlayer(), 0);
            }
        }.runTaskLater(plugin, 5L);
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        event.getPlayer().setWalkSpeed(0.2f);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.getPlayer().setWalkSpeed(0.2f);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!SlowMode.isSlowMode()) return;
        event.setDropItems(false);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        SlowBySandstone.updateStatus((Player) event.getPlayer());
    }
}
