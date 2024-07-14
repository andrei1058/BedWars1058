package com.andrei1058.bedwars.z_myadditions;

import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class EPG_Main {

    JavaPlugin plugin;

    public EPG_Main(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        Logger.getLogger("BW Addition \"ExplosionProofGlass\" is Enable!").info("BW Addition \"ExplosionProofGlass\" is Enable!");
        plugin.getServer().getPluginManager().registerEvents(new EPG_Listener(plugin), plugin);
        Objects.requireNonNull(plugin.getCommand("wall")).setExecutor(new WallCommandExecutor());
    }

    public static class WallCommandExecutor implements CommandExecutor {
        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Эту команду могут вызывать только игроки");
                return true;
            }

            if (args.length != 5) {
                sender.sendMessage("Usage: /wall <x1> <z1> <x2> <z2> <y>");
                return false;
            }

            try {
                int x1 = Integer.parseInt(args[0]);
                int z1 = Integer.parseInt(args[1]);
                int x2 = Integer.parseInt(args[2]);
                int z2 = Integer.parseInt(args[3]);
                int y = Integer.parseInt(args[4]);

                Player player = (Player) sender;
                World world = player.getWorld();

                player.sendMessage("Установка стенаы: X1=" + x1 + " Z1=" + z1 + " X2=" + x2 + " Z2=" + z2 + " Y=" + y);

                ArrayList<Integer> pos_start = new ArrayList<Integer>(Arrays.asList(x1, z1));
                ArrayList<Integer> pos_end = new ArrayList<Integer>(Arrays.asList(x2, z2));

                List<int[]> blocks_pos_xz = new Bresenham(pos_start, pos_end).algorithm();

//                System.out.println(Arrays.deepToString(blocks_pos_xz));

                for (int[] pos : blocks_pos_xz) {
                    System.out.println(Arrays.toString(pos));
                }

                blocks_pos_xz.forEach(
                        (block_pos_xz) -> {
                            Block block = world.getBlockAt(block_pos_xz[0], y, block_pos_xz[1]);
                            block.setType(Material.BEDROCK);
                        }
                );


            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid coordinates. Please enter valid numbers.");
                return false;
            }

            return true;
        }
    }
}
