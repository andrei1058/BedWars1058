package com.andrei1058.bedwars.slow_mode;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultyInstriment_Listener implements Listener {

    private final List<List<Material>> INSTRUMENTS = Arrays.asList(
            Arrays.asList(
                    Material.WOODEN_PICKAXE,
                    Material.IRON_PICKAXE,
                    Material.GOLDEN_PICKAXE,
                    Material.DIAMOND_PICKAXE
            ),
            Arrays.asList(
                    Material.WOODEN_AXE,
                    Material.IRON_AXE,
                    Material.GOLDEN_AXE,
                    Material.DIAMOND_AXE
            )
    );


    public static void onEnable(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new MultyInstriment_Listener(), plugin);
    }

    private static void changeInstrument(Player player, ItemStack item, Material newMaterial) {
        if (item == null || item.getType() == Material.AIR) return; // Проверка на пустой слот


        item.setType(newMaterial);

//        newItem.addEnchantments(item.getEnchantments());
//
//        player.getInventory().setItemInMainHand(newItem);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();

//        System.out.println(action.toString());
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;


        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() == Material.AIR) return;

        int pickaxeIndex = INSTRUMENTS.get(0).indexOf(item.getType());
        int axeIndex = INSTRUMENTS.get(1).indexOf(item.getType());

        if (pickaxeIndex != -1) {
            changeInstrument(player, item, INSTRUMENTS.get(1).get(pickaxeIndex)); // Меняем на топор
        } else if (axeIndex != -1) {
            changeInstrument(player, item, INSTRUMENTS.get(0).get(axeIndex)); // Меняем на кирку
        }
    }
}
