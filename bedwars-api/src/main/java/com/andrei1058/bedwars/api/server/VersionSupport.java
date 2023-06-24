/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.api.server;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.entity.Despawnable;
import com.andrei1058.bedwars.api.exceptions.InvalidEffectException;
import com.andrei1058.bedwars.api.util.BlockRay;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public abstract class VersionSupport {

    private static String name2;
    public static String PLUGIN_TAG_GENERIC_KEY = "BedWars1058";
    public static String PLUGIN_TAG_TIER_KEY = "tierIdentifier";

    private Effect eggBridge;
    protected static Random random = new Random();

    private static ConcurrentHashMap<UUID, Despawnable> despawnables = new ConcurrentHashMap<>();
    private Plugin plugin;

    public VersionSupport(Plugin plugin, String versionName) {
        name2 = versionName;
        this.plugin = plugin;
    }

    protected void loadDefaultEffects() {
        try {
            setEggBridgeEffect("MOBSPAWNER_FLAMES");
        } catch (InvalidEffectException e) {
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
     * Check if itemstack is Invisibility Potion
     */
    public abstract boolean isInvisibilityPotion(ItemStack itemStack);

    /**
     * Check if type is a Glass type material
     */
    public boolean isGlass(Material type) {
        return type != Material.AIR && (type == Material.GLASS || type.toString().contains("_GLASS"));
    }

    /**
     * Check if block is protected by blast-proof glass or an unbreakable block from a point of view
     * <p>
     * if pov is null, block is checked by {@link #isGlass(Material)} and {@link IArena#isBlockPlaced(Block)} instead.
     * Otherwise, a ray tracing is performed to check whether there is any glass block or an unbreakable block.
     * <p>
     * If arena is null, then glass only be checked.
     *
     * @param arena Arena instance.
     * @param pov   the point of view.
     * @param block the block instance.
     * @param step  how frequent to check the ray (0.25 - 0.5 recommended).
     * @return whether there's unbreakable block between the pov and the block
     */
    public boolean isProtected(IArena arena, Location pov, Block block, double step) {
        if (pov == null)
            return isGlass(block.getType()) || (arena != null && !arena.isBlockPlaced(block));

        // maybe remove this?
        if (block.getType() == Material.AIR)
            return false;

        if (isGlass(block.getType()) || (arena != null && !arena.isBlockPlaced(block)))
            return true;

        int distance = NumberConversions.ceil(pov.distanceSquared(block.getLocation()));
        if (distance == 0) {
            return isGlass(block.getType()) || (arena != null && !arena.isBlockPlaced(block));
        }

        // Trace blocks from pov to the block location
        final Location target = block.getLocation();
        BlockRay ray;

        try {
            ray = new BlockRay(block.getWorld(), pov.toVector(), target.toVector(), step);
        } catch (IllegalArgumentException ignored) {
            return isGlass(block.getType()) || (arena != null && !arena.isBlockPlaced(block));
        }

        while (ray.hasNext()) {
            Block nextBlock = ray.next();

            if (nextBlock.getType() == Material.AIR) {
                continue;
            }

            if (isGlass(nextBlock.getType())) {
                // If a block is a glass
                return true;
            }

            if (arena != null && !arena.isBlockPlaced(nextBlock)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Register custom entities
     */
    public abstract void registerEntities();

    /**
     * Spawn shop NPC
     */
    public abstract void spawnShop(Location loc, String name1, List<Player> players, IArena arena);

    /**
     * Get item-stack damage amount
     */
    public abstract double getDamage(ItemStack i);

    /**
     * Spawn silverfish for a team
     */
    public abstract void spawnSilverfish(Location loc, ITeam team, double speed, double health, int despawn, double damage);

    /**
     * Spawn a iron-golem for a team
     */
    public abstract void spawnIronGolem(Location loc, ITeam team, double speed, double health, int despawn);

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
    public abstract void hideArmor(Player victim, Player receiver);

    /**
     * Show a player armor
     */
    public abstract void showArmor(Player victim, Player receiver);

    /**
     * Spawn ender dragon
     */
    public abstract void spawnDragon(Location l, ITeam team);

    /**
     * Color a bed 1.12+
     */
    public abstract void colorBed(ITeam team);

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
    public abstract void setCollide(Player p, IArena a, boolean value);

    /**
     * Add custom data to an ItemStack
     */
    public abstract ItemStack addCustomData(ItemStack i, String data);

    public abstract ItemStack setTag(ItemStack itemStack, String key, String value);

    /**
     * Get a custom item tag.
     *
     * @return null if not present.
     */
    public abstract String getTag(ItemStack itemStack, String key);

    /**
     * Check if an item has a BedWars1058 NBTTag
     */
    public abstract boolean isCustomBedWarsItem(ItemStack i);

    /**
     * Get the NBTTag from a BedWars1058 item
     */
    public abstract String getCustomData(ItemStack i);

    /**
     * Color an item if possible with the team's color
     */
    public abstract ItemStack colourItem(ItemStack itemStack, ITeam bedWarsTeam);

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
     * Get netherite  helmet material
     */
    public abstract Material materialNetheriteHelmet();

    /**
     * Get netherite chest plate
     */
    public abstract Material materialNetheriteChestPlate();

    /**
     * Get netherite leggings
     */
    public abstract Material materialNetheriteLeggings();

    /**
     * Get elytra - supports: 1.12.2+
     */
    public abstract Material materialElytra();

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
     * This will always return true on versions major or equal 1.13
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
     *
     * @param copyTagFrom will copy nbt tag from this item.
     */
    public abstract ItemStack getPlayerHead(Player player, @Nullable ItemStack copyTagFrom);

    /**
     * This will send the player spawn packet after a player re-spawn.
     * <p>
     * Show the target player to players and spectators in the arena.
     */
    public abstract void sendPlayerSpawnPackets(Player player, IArena arena);

    /**
     * Get inventory name.
     */
    public abstract String getInventoryName(InventoryEvent e);

    /**
     * Make item unbreakable.
     */
    public abstract void setUnbreakable(ItemMeta itemMeta);

    /**
     * Get list of entities that are going to despawn based on a timer.
     */
    public ConcurrentHashMap<UUID, Despawnable> getDespawnablesList() {
        return despawnables;
    }


    public static String getName() {
        return name2;
    }

    public abstract int getVersion();

    public Plugin getPlugin() {
        return plugin;
    }

    public abstract void registerVersionListeners();

    /**
     * Get main level name.
     */
    public abstract String getMainLevel();

    public byte getCompressedAngle(float value) {
        return (byte) ((value * 256.0F) / 360.0F);
    }

    public void spigotShowPlayer(Player victim, Player receiver) {
        receiver.showPlayer(victim);
    }

    public void spigotHidePlayer(Player victim, Player receiver) {
        receiver.hidePlayer(victim);
    }

    /**
     * Make fireball go straight.
     *
     * @param fireball fireball instance;
     * @param vector   fireball direction to normalize.
     * @return modified fireball.
     */
    public abstract Fireball setFireballDirection(Fireball fireball, Vector vector);

    public abstract void playRedStoneDot(Player player);

    public abstract void clearArrowsFromPlayerBody(Player player);

    public abstract void placeTowerBlocks(Block b, IArena a, TeamColor color, int x, int y,int z);

    public abstract void placeLadder(Block b, int x, int y, int z, IArena a, int ladderdata);

    public abstract void playVillagerEffect(Player player, Location location);

    /**
     * Calculates BrokenBlocks from an explosion source (with blast proof glass and world protection)
     * <p>
     * See: {@link #calculateExplosionBlocks(IArena, Entity, Location, float, boolean, BiFunction)}
     *
     * @param arena             arena instance.
     * @param source            source of explosion, can be null.
     * @param explosionLocation the location where the explosion should be calculated from.
     * @param radius            radius of the explosion.
     * @param fire              whether blocks are set on fire or not.
     * @return A Block list of blocks that should be destroyed from the explosion.
     */
    public List<Block> calculateExplosionBlocks(IArena arena, Entity source, Location explosionLocation, float radius, boolean fire) {
        // Honestly I don't know if there's a performance gain or lost but there just in case :)
        HashMap<Block, Boolean> functionCache = new HashMap<>();

        BiFunction<Location, Block, Boolean> actualCallback = (loc, block) -> {
            if (!arena.isBlockPlaced(block))
                // the block is not placed by a player
                return true;

            // If it's protected by glass then we should skip it!
            return isProtected(arena, loc, block, 0.3);
        };

        List<Block> result = calculateExplosionBlocks(
                arena, source, explosionLocation, radius, fire, (loc, block) -> functionCache.computeIfAbsent(block, (b) -> actualCallback.apply(loc, b))
        );

        functionCache.clear();

        return result;
    }

    /**
     * Calculates BrokenBlocks from an explosion source.
     * <p>
     * The Callback takes Location of the explosion and current block and
     * Must return a boolean indicating whether to skip this block in the explosion calculation
     * <p>
     * Note: The callback could get called for the same block multiple times.
     *
     * @param arena             arena instance.
     * @param source            source of explosion, can be null.
     * @param explosionLocation the location where the explosion should be calculated from.
     * @param radius            radius of the explosion.
     * @param fire              whether blocks are set on fire or not.
     * @param callback          callback that indicates whether to skip the block.
     * @return A Block list of blocks that should be destroyed from the explosion.
     */
    public abstract List<Block> calculateExplosionBlocks(IArena arena, Entity source, Location explosionLocation, float radius, boolean fire, BiFunction<Location, Block, Boolean> callback);

    private static int normalizeInteger(int x) {
        return Integer.compare(x, 0);
    }
}
