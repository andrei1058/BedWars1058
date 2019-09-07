package com.andrei1058.bedwars.api.arena.team;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface ITeam {

    /**
     * Get team color.
     */
    TeamColor getColor();

    /**
     * Get team name.
     */
    String getName();

    /**
     * Check if is member.
     */
    boolean isMember(Player player);

    IArena getArena();

    /**
     * Get alive team members.
     */
    List<Player> getMembers();

    void defaultSword(Player p, boolean value);

    /**
     * Get bed location.
     */
    Location getBed();

    /**
     * Get enchantments to be applied on bows.
     */
    List<TeamEnchant> getBowsEnchantments();

    /**
     * Get enchantments to be applied on swords.
     */
    List<TeamEnchant> getSwordsEnchantments();

    /**
     * Get enchantments to be applied on armors.
     */
    List<TeamEnchant> getArmorsEnchantments();


    /**
     * Get team current size.
     */
    int getSize();

    /**
     * Add a new member to the team
     */
    void addPlayers(Player... players);

    /**
     * Spawn a player for the first spawn.
     */
    void firstSpawn(Player p);

    /**
     * Spawn shopkeepers for target team (if enabled).
     */
    void spawnNPCs();

    /**
     * Rejoin a team
     */
    void reJoin(Player p);

    /**
     * Gives the start inventory
     */
    void sendDefaultInventory(Player p, boolean clean);

    /**
     * Spawn the iron and gold generators
     */
    void setGenerators(Location ironGenerator, Location goldGenerator);

    /**
     * Respawn a member. This is after respawn countdown.
     */
    void respawnMember(Player p);

    /**
     * Equip a player with default armor
     */
    void sendArmor(Player p);

    /**
     * Used when someone buys a new potion effect with apply == members
     */
    void addTeamEffect(PotionEffectType pef, int amp, int duration);

    /**
     * Used when someone buys a new potion effect with apply == base
     */
    void addBaseEffect(PotionEffectType pef, int amp, int duration);

    /**
     * Used when someone buys a new potion effect with apply == enemyBaseEnter
     */
    void addEnemyBaseEnterEffect(PotionEffectType pef, int amp, int slot, int duration);

    /**
     * Used when someone buys a bew enchantment with apply == bow
     */
    void addBowEnchantment(Enchantment e, int a);

    /**
     * Used when someone buys a new enchantment with apply == sword
     */
    void addSwordEnchantment(Enchantment e, int a);

    /**
     * Used when someone buys a new enchantment with apply == armor
     */
    void addArmorEnchantment(Enchantment e, int a);

    /**
     * Check if target has played in this match.
     */
    boolean wasMember(UUID u);

    boolean isBedDestroyed();

    Location getSpawn();

    Location getShop();

    Location getTeamUpgrades();

    HashMap<Integer, Integer> getUpgradeTier();

    /**
     * This will also disable team generators if true.
     */
    void setBedDestroyed(boolean bedDestroyed);


    IGenerator getIronGenerator();

    IGenerator getGoldGenerator();

    IGenerator getEmeraldGenerator();

    void setEmeraldGenerator(IGenerator emeraldGenerator);

    @Deprecated
    boolean isTrapActive();

    @Deprecated
    void setTrapAction(boolean trapAction);

    @Deprecated
    void enableTrap(int slot);

    @Deprecated
    void disableTrap();

    @Deprecated
    void setTrapChat(boolean trapChat);

    @Deprecated
    void setTrapSubtitle(boolean trapSubtitle);

    @Deprecated
    void setTrapTitle(boolean trapTitle);

    @Deprecated
    boolean isTrapAction();

    @Deprecated
    boolean isTrapChat();

    @Deprecated
    boolean isTrapSubtitle();

    @Deprecated
    boolean isTrapTitle();

    int getDragons();

    @Deprecated
    List<Integer> getEnemyBaseEnterSlots();

    @Deprecated
    List<Player> getMembersCache();

    /**
     * Destroy team data when the arena restarts.
     */
    void destroyData();

    /**
     * Destroy bed hologram for player
     */
    @Deprecated
    void destroyBedHolo(Player player);
}
