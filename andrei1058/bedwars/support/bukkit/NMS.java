package com.andrei1058.bedwars.support.bukkit;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface NMS {

    /** Register a new command as bukkit command */
    void registerCommand(String name, Command clasa);

    /** Send title, subtitle. null for empty*/
    void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut);

    /** Send action-bar message */
    void playAction(Player p, String text);

    /** Spawn a new NPC using NOai*/
    void spawnNPC(EntityType entity, Location location, String name, String group);

    /** Check if bukkit command is registered*/
    boolean isBukkitCommandRegistered(String command);

    /** Get in had item-stack */
    ItemStack getItemInHand(Player p);

    /** Hide an entity*/
    void hideEntity(Entity e, Player... players);

    /** Check if item-stack is armor*/
    boolean isArmor(ItemStack itemStack);

    /** Check if item-stack is a tool*/
    boolean isTool(ItemStack itemStack);

    /** Check if item-stack is sword */
    boolean isSword(ItemStack itemStack);

    /** Check if item-stack is bow */
    boolean isBow(ItemStack itemStack);

    /** Register custom entities */
    void registerEntities();

    /** Spawn shop NPC*/
    void spawnShop(Location loc, String name1, List<Player> players);

    /** Get item-stack damage amount*/
    double getDamage(ItemStack i);

    /** Get item-stack protection amount */
    double getProtection(ItemStack i);

    /** Get bed-destroy sound */
    Sound bedDestroy();

    /** Get player-kill sound */
    Sound playerKill();

    /** Get insufficient money sound*/
    Sound insufficientMoney();

    /** Get boy success sound */
    Sound bought();

    /** Get countdown sound */
    Sound countdownTick();

    /** Spawn silverfish for a team */
    Entity spawnSilverfish(Location loc, List<Player> exclude, String name);

    /** Spawn a iron-golem for a team*/
    void spawnIronGolem(Location loc, BedWarsTeam bedWarsTeam);

    /** Hide a player */
    void hidePlayer(Player player, List<Player> players);

    /** Refresh iron-golem name */
    void refreshDespawnables();

    /** Is despawnable entity */
    boolean isDespawnable(Entity e);

    /** Own despawnable */
    BedWarsTeam ownDespawnable(Entity e);
}
