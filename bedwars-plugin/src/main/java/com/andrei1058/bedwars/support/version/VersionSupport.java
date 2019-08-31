package com.andrei1058.bedwars.support.version;

import com.andrei1058.bedwars.api.team.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.exceptions.InvalidEffectException;
import com.andrei1058.bedwars.exceptions.InvalidSoundException;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Team;

import java.util.*;

public abstract class VersionSupport {

    private String name;
    private Sound bedDestroy,
            playerKill,
            countDown,
            bought,
            insuffMoney;

    private Effect eggBridge;

    private static HashMap<UUID, Despawnable> despawnables = new HashMap<>();

    public VersionSupport(String name) {
        this.name = name;
    }

    protected void loadDefaultSounds(){
        try {
            setBedDestroySound("ENTITY_ENDER_DRAGON_GROWL");
            setPlayerKillsSound("ENTITY_WOLF_HURT");
            setCountdownSound("ENTITY_CHICKEN_EGG");
            setBoughtSound("BLOCK_ANVIL_HIT");
            setInsuffMoneySound("ENTITY_ENDERMAN_TELEPORT");
            setEggBridgeEffect("MOBSPAWNER_FLAMES");
        } catch (InvalidSoundException | InvalidEffectException e) {
            e.printStackTrace();
        }
    }


    /**
     * Register a new command as bukkit command
     */
    public abstract void registerCommand(String name, Command clasa);

    /**
     * Send title, subtitle. null for empty
     */
    public abstract void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut);

    /**
     * Send action-bar message
     */
    public abstract void playAction(Player p, String text);

    /**
     * Check if bukkit command is registered
     */
    public abstract boolean isBukkitCommandRegistered(String command);

    /**
     * Get in had item-stack
     */
    public abstract ItemStack getItemInHand(Player p);

    /**
     * Hide an entity
     */
    public abstract void hideEntity(Entity e, Player p);


    /**
     * Check if item-stack is armor
     */
    public abstract boolean isArmor(ItemStack itemStack);

    /**
     * Check if item-stack is a tool
     */
    public abstract boolean isTool(ItemStack itemStack);

    /**
     * Check if item-stack is sword
     */
    public abstract boolean isSword(ItemStack itemStack);

    /**
     * Check if item-stack is axe
     */
    public abstract boolean isAxe(ItemStack itemStack);

    /**
     * Check if item-stack is bow
     */
    public abstract boolean isBow(ItemStack itemStack);

    /**
     * Check if itemstack is Projectile
     */
    public abstract boolean isProjectile(ItemStack itemStack);

    /**
     * Register custom entities
     */
    public abstract void registerEntities();

    /**
     * Spawn shop NPC
     */
    public abstract void spawnShop(Location loc, String name1, List<Player> players, Arena arena);

    /**
     * Get item-stack damage amount
     */
    public abstract double getDamage(ItemStack i);

    /**
     * Get bed-destroy sound
     */
    public Sound bedDestroy() {
        return bedDestroy;
    }

    /**
     * Set the bed destroy sound
     */
    public void setBedDestroySound(String sound) throws InvalidSoundException {
        try {
            this.bedDestroy = Sound.valueOf(sound);
        } catch (Exception e) {
            throw new InvalidSoundException(sound);
        }
    }

    /**
     * Get player-kill sound
     */
    public Sound playerKill() {
        return playerKill;
    }

    /**
     * Set the player kill sound
     */
    public void setPlayerKillsSound(String sound) throws InvalidSoundException {
        try {
            this.playerKill = Sound.valueOf(sound);
        } catch (Exception e) {
            throw new InvalidSoundException(sound);
        }
    }

    /**
     * Get insufficient money sound
     */
    public Sound insufficientMoney() {
        return insuffMoney;
    }

    /**
     * Set the insufficient money sound
     */
    public void setInsuffMoneySound(String sound) throws InvalidSoundException {
        try {
            this.insuffMoney = Sound.valueOf(sound);
        } catch (Exception e) {
            throw new InvalidSoundException(sound);
        }
    }

    /**
     * Get boy success sound
     */
    public Sound bought() {
        return bought;
    }

    /**
     * Set the bought sound
     */
    public void setBoughtSound(String sound) throws InvalidSoundException {
        try {
            this.bought = Sound.valueOf(sound);
        } catch (Exception e) {
            throw new InvalidSoundException(sound);
        }
    }

    /**
     * Get countdown sound
     */
    public Sound countdownTick() {
        return countDown;
    }

    /**
     * Set countdown tick sound
     */
    public void setCountdownSound(String sound) throws InvalidSoundException {
        try {
            this.countDown = Sound.valueOf(sound);
        } catch (Exception e) {
            throw new InvalidSoundException(sound);
        }
    }

    /**
     * Spawn silverfish for a team
     */
    public abstract void spawnSilverfish(Location loc, BedWarsTeam bedWarsTeam);

    /**
     * Spawn a iron-golem for a team
     */
    public abstract void spawnIronGolem(Location loc, BedWarsTeam bedWarsTeam);

    /**
     * Hide a player
     */
    public abstract void hidePlayer(Player whoToShow, List<Player> players);

    /**
     * Hide a player
     */
    public abstract void hidePlayer(Player whoToShow, Player p);

    /**
     * Show a player
     *
     * @param whoToShow this player will be shown for the second param
     *                  <p>
     *                  For 1.13 is using - #showPlayer​(Plugin plugin, Player player)
     */
    public abstract void showPlayer(Player whoToShow, Player p);

    /**
     * Is despawnable entity
     */
    public boolean isDespawnable(Entity e) {
        return despawnables.get(e.getUniqueId()) != null;
    }

    /**
     * Change item amount
     */
    public abstract void minusAmount(Player p, ItemStack i, int amount);

    /**
     * Set tnt source
     */
    public abstract void setSource(TNTPrimed tnt, Player owner);

    /**
     * Void damage with cause
     */
    public abstract void voidKill(Player p);

    /**
     * Hide player armor to a player
     */
    public abstract void hideArmor(Player p, Player p2);

    /**
     * Show a player armor
     */
    public abstract void showArmor(Player p, Player p2);

    /**
     * Spawn ender dragon
     */
    public abstract void spawnDragon(Location l, BedWarsTeam bwt);

    /**
     * Color a bed 1.12+
     */
    public abstract void colorBed(BedWarsTeam bwt);

    /**
     * Register tnt whitelist
     */
    public abstract void registerTntWhitelist();

    /**
     * Egg bridge particles
     */
    public Effect eggBridge() {
        return eggBridge;
    }

    @SuppressWarnings("WeakerAccess")
    public void setEggBridgeEffect(String eggBridge) throws InvalidEffectException {
        try {
            this.eggBridge = Effect.valueOf(eggBridge);
        } catch (Exception e) {
            throw new InvalidEffectException(eggBridge);
        }
    }

    /**
     * Set block data
     * For 1.13 support
     */
    public abstract void setBlockTeamColor(Block block, TeamColor teamColor);

    /**
     * Disable collisions in 1.9+
     */
    public abstract void setCollide(Player p, Arena a, boolean value);

    /**
     * Add custom data to an ItemStack
     * Adds a NBTTag to the item with BedWars1058 key
     */
    public abstract ItemStack addCustomData(ItemStack i, String data);

    /**
     * Check if an item has a BedWars1058 NBTTag
     */
    public abstract boolean isCustomBedWarsItem(ItemStack i);

    /**
     * Get the NBTTag from a BedWars1058 item
     */
    public abstract String getCustomData(ItemStack i);

    /**
     * Set a skull skin
     */
    public abstract ItemStack setSkullOwner(ItemStack i, Player p);

    /**
     * Color an item if possible with the team's color
     */
    public abstract ItemStack colourItem(ItemStack itemStack, BedWarsTeam bedWarsTeam);

    public abstract ItemStack createItemStack(String material, int amount, short data);

    public abstract void teamCollideRule(Team team);

    /**
     * Check if is a player head
     */
    public boolean isPlayerHead(String material, int data) {
        return material.equalsIgnoreCase("PLAYER_HEAD");
    }

    /**
     * Get fireball material
     */
    public abstract Material materialFireball();

    /**
     * Player head material
     */
    public abstract Material materialPlayerHead();

    /**
     * Get snowball material
     */
    public abstract Material materialSnowball();

    /**
     * Get gold  helmet material
     */
    public abstract Material materialGoldenHelmet();

    /**
     * Get gold chest plate
     */
    public abstract Material materialGoldenChestPlate();

    /**
     * Get gold leggings
     */
    public abstract Material materialGoldenLeggings();

    /**
     * Cake material
     */
    public abstract Material materialCake();

    /**
     * Crafting table material
     */
    public abstract Material materialCraftingTable();

    /**
     * Enchanting table material
     */
    public abstract Material materialEnchantingTable();

    /**
     * Check if bed
     */
    public boolean isBed(Material material) {
        return material.toString().contains("_BED");
    }

    /**
     * Item Data compare
     * This will always return true on versions >= 1.13
     */
    public boolean itemStackDataCompare(ItemStack i, short data) {
        return true;
    }

    /**
     * Set block data
     * For versions before 1.13
     */
    public void setJoinSignBackgroundBlockData(BlockState b, byte data) {

    }

    /**
     * Change the block behind the join sign.
     */
    public abstract void setJoinSignBackground(BlockState b, Material material);

    /**
     * Wool material
     */
    public abstract Material woolMaterial();

    /**
     * Get an ItemStack identifier
     * will return null text if it does not have an identifier
     */
    public abstract String getShopUpgradeIdentifier(ItemStack itemStack);

    /**
     * Set an upgrade identifier
     */
    public abstract ItemStack setShopUpgradeIdentifier(ItemStack itemStack, String identifier);

    /**
     * Get player head with skin.
     */
    public abstract ItemStack getPlayerHead(Player player);

    /**
     * This will send the player spawn packet after a player respawn.
     * <p>
     * Show the target player to players and spectators in the arena.
     */
    public abstract void invisibilityFix(Player player, Arena arena);

    /**
     * Get inventory name.
     */
    public abstract String getInventoryName(InventoryEvent e);

    /**
     * Make item unbreakable.
     */
    public abstract void setUnbreakable(ItemMeta itemMeta);

    /**
     * Get server level name.
     */
    public abstract String getLevelName();

    /**
     * Get list of entities that are going to despawn based on a timer.
     */
    public HashMap<UUID, Despawnable> getDespawnablesList() {
        return despawnables;
    }

    /**
     * Register listeners made for this version.
     */
    public abstract void registerVersionListeners();

    public String getName() {
        return name;
    }

    public abstract int getVersion();
}
