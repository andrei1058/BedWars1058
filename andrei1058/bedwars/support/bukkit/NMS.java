package com.andrei1058.bedwars.support.bukkit;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface NMS {

    void registerCommand(String name, Command clasa);

    void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut);

    void playAction(Player p, String text);

    void spawnNPC(EntityType entity, Location location, String name, String group);

    boolean isBukkitCommandRegistered(String command);

    ItemStack getItemInHand(Player p);

    void hideEntity(Entity e, Player... players);

    boolean isArmor(ItemStack itemStack);

    boolean isTool(ItemStack itemStack);

    boolean isSword(ItemStack itemStack);

    boolean isBow(ItemStack itemStack);

    void registerEntities();

    void spawnShop(Location loc, String name1, List<Player> players);

    double getDamage(ItemStack i);

    double getProtection(ItemStack i);

    Sound bedDestroy();

    Sound playerKill();

    Sound insufficientMoney();

    Sound bought();

    void hidePlayer(Player player, List<Player> players);
}
