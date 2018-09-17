package com.andrei1058.bedwars.support.bukkit;

import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.arena.SBoard;
import com.andrei1058.bedwars.exceptions.InvalidSoundException;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

import java.util.List;

public interface NMS {

    /**
     * Register a new command as bukkit command
     */
    void registerCommand(String name, Command clasa);

    /**
     * Send title, subtitle. null for empty
     */
    void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut);

    /**
     * Send action-bar message
     */
    void playAction(Player p, String text);

    /**
     * Unregister bukkit command
     */
    void unregisterCommand(String name);

    /**
     * Check if bukkit command is registered
     */
    boolean isBukkitCommandRegistered(String command);

    /**
     * Get in had item-stack
     */
    ItemStack getItemInHand(Player p);

    /**
     * Hide an entity
     */
    void hideEntity(Entity e, Player p);


    /**
     * Check if item-stack is armor
     */
    boolean isArmor(ItemStack itemStack);

    /**
     * Check if item-stack is a tool
     */
    boolean isTool(ItemStack itemStack);

    /**
     * Check if item-stack is sword
     */
    boolean isSword(ItemStack itemStack);

    /**
     * Check if item-stack is bow
     */
    boolean isBow(ItemStack itemStack);

    /**
     * Check if itemstack is Projectile
     */
    boolean isProjectile(ItemStack itemStack);

    /**
     * Register custom entities
     */
    void registerEntities();

    /**
     * Spawn shop NPC
     */
    void spawnShop(Location loc, String name1, List<Player> players, Arena arena);

    /**
     * Get item-stack damage amount
     */
    double getDamage(ItemStack i);

    /**
     * Get item-stack protection amount
     */
    double getProtection(ItemStack i);

    /**
     * Get bed-destroy sound
     */
    Sound bedDestroy();

    /**
     * Set the bed destroy sound
     */
    void setBedDestroySound(String sound) throws InvalidSoundException;

    /**
     * Get player-kill sound
     */
    Sound playerKill();

    /**
     * Set the player kill sound
     */
    void setPlayerKillsSound(String sound) throws InvalidSoundException;

    /**
     * Get insufficient money sound
     */
    Sound insufficientMoney();

    /**
     * Set the insufficient money sound
     */
    void setInsuffMoneySound(String sound) throws InvalidSoundException;

    /**
     * Get boy success sound
     */
    Sound bought();

    /**
     * Set the bought sound
     */
    void setBoughtSound(String sound) throws InvalidSoundException;

    /**
     * Get countdown sound
     */
    Sound countdownTick();

    /**
     * Set countdown tick sound
     */
    void setCountdownSound(String sound) throws InvalidSoundException;

    /**
     * Spawn silverfish for a team
     */
    void spawnSilverfish(Location loc, BedWarsTeam bedWarsTeam);

    /**
     * Spawn a iron-golem for a team
     */
    void spawnIronGolem(Location loc, BedWarsTeam bedWarsTeam);

    /**
     * Hide a player
     */
    void hidePlayer(Player whoToShow, List<Player> players);

    /**
     * Hide a player
     */
    void hidePlayer(Player whoToShow, Player p);

    /**
     * Show a player
     */
    void showPlayer(Player whoToShow, Player p);

    /**
     * Refresh iron-golem name
     */
    void refreshDespawnables();

    /**
     * Is despawnable entity
     */
    boolean isDespawnable(Entity e);

    /**
     * Own despawnable
     */
    BedWarsTeam ownDespawnable(Entity e);

    /**
     * Change item amount
     */
    void minusAmount(Player p, ItemStack i, int amount);

    /**
     * Set tnt source
     */
    void setSource(TNTPrimed tnt, Player owner);

    /**
     * Void damage with cause
     */
    void voidKill(Player p);

    /**
     * Hide player armor to a player
     */
    void hideArmor(Player p, Player p2);

    /**
     * Show a player armor
     */
    void showArmor(Player p, Player p2);

    /**
     * Spawn ender dragon
     */
    void spawnDragon(Location l, BedWarsTeam bwt);

    /**
     * Color a bed 1.12+
     */
    void colorBed(BedWarsTeam bwt);

    /**
     * Register tnt whitelist
     */
    void registerTntWhitelist();

    /**
     * Egg bridge particles
     *
     * @since API 7
     */
    Effect eggBridge();

    /**
     * Set block data
     * For 1.13 support
     *
     * @since API 7
     */
    void setBlockTeamColor(Block block, TeamColor teamColor);

    /**
     * Disable collisions in 1.9+
     *
     * @since API 9
     */
    void setCollide(Player p, Arena a, boolean value);

    /**
     * Add custom data to an ItemStack
     * Adds a NBTTag to the item with BedWars1058 key
     *
     * @since API 9
     */
    ItemStack addCustomData(ItemStack i, String data);

    /**
     * Check if an item has a BedWars1058 NBTTag
     *
     * @since API 9
     */
    boolean isCustomBedWarsItem(ItemStack i);

    /**
     * Get the NBTTag from a BedWars1058 item
     *
     * @since API 10
     */
    String getCustomData(ItemStack i);

    /**
     * Set a skull skin
     *
     * @since API 10
     */
    ItemStack setSkullOwner(ItemStack i, Player p);

    /**
     * Color an item if possible with the team's color
     *
     * @since API 10
     */
    ItemStack colourItem(ItemStack itemStack, BedWarsTeam bedWarsTeam);

    void teamCollideRule(Team team);
}
