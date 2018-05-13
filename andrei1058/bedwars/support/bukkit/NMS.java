package com.andrei1058.bedwars.support.bukkit;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Bed;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

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
    void hideEntity(Entity e, Player p);

    /** Check if item-stack is armor*/
    boolean isArmor(ItemStack itemStack);

    /** Check if item-stack is a tool*/
    boolean isTool(ItemStack itemStack);

    /** Check if item-stack is sword */
    boolean isSword(ItemStack itemStack);

    /** Check if item-stack is bow */
    boolean isBow(ItemStack itemStack);

    /** Check if itemstack is Projectile */
    boolean isProjectile(ItemStack itemStack);

    /** Register custom entities */
    void registerEntities();

    /** Spawn shop NPC*/
    void spawnShop(Location loc, String name1, List<Player> players, Arena arena);

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
    void spawnSilverfish(Location loc, BedWarsTeam bedWarsTeam);

    /** Spawn a iron-golem for a team*/
    void spawnIronGolem(Location loc, BedWarsTeam bedWarsTeam);

    /** Hide a player */
    void hidePlayer(Player whoToShow, List<Player> players);

    /** Hide a player */
    void hidePlayer(Player whoToShow, Player p);

    /** Show a player */
    void showPlayer(Player whoToShow, Player p);

    /** Refresh iron-golem name */
    void refreshDespawnables();

    /** Is despawnable entity */
    boolean isDespawnable(Entity e);

    /** Own despawnable */
    BedWarsTeam ownDespawnable(Entity e);

    /** Collide with entities */
    void setCollide(Player e, boolean b);

    /** Change item amount */
    void minusAmount(Player p, ItemStack i, int amount);

    /** Team collision rule 1.9+ **/
    void teamCollideRule(Team t);

    /** Set tnt source */
    void setSource(TNTPrimed tnt, Player owner);

    /** Void damage with cause */
    void voidKill(Player p);

    /** Hide player armor to a player */
    void hideArmor(Player p, Player p2);

    /** Show a player armor */
    void showArmor(Player p, Player p2);

    /** Spawn ender dragon */
    void spawnDragon(Location l, BedWarsTeam bwt);

    /** Color a bed 1.12+ */
    void colorBed(BedWarsTeam bwt, BlockState bed);

    /** Register tnt whitelist */
    void registerTntWhitelist();
}
