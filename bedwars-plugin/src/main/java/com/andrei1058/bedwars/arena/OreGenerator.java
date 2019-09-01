package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.arena.generator.GeneratorType;
import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.gameplay.GeneratorUpgradeEvent;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.language.Messages;
import com.andrei1058.bedwars.region.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

import static com.andrei1058.bedwars.Main.*;

@SuppressWarnings("WeakerAccess")
public class OreGenerator implements IGenerator {

    private Location location;
    private int delay = 1, upgradeStage = 1, lastSpawn, spawnLimit = 0, amount = 1;
    private Arena arena;
    private ItemStack ore;
    private GeneratorType type;
    private int rotate = 0, dropID = 0;
    private BedWarsTeam bwt;
    boolean up = true;

    /**
     * Generator holograms per language <iso, holo></iso,>
     */
    private HashMap<String, HoloGram> armorStands = new HashMap<>();

    private ArmorStand item;
    public boolean stack = getGeneratorsCfg().getBoolean(ConfigPath.GENERATOR_STACK_ITEMS);

    private static ConcurrentLinkedDeque<OreGenerator> rotation = new ConcurrentLinkedDeque<>();

    public OreGenerator(Location location, Arena arena, GeneratorType type, BedWarsTeam bwt) {
        if (type == GeneratorType.EMERALD || type == GeneratorType.DIAMOND) {
            this.location = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY() + 1.3, location.getBlockZ() + 0.5);
        } else {
            this.location = location.add(0, 1.3, 0);
        }
        this.arena = arena;
        this.bwt = bwt;
        this.type = type;
        loadDefaults();
        Main.debug("Initializing new generator at: " + location.toString() + " - " + type + " - " + (bwt == null ? "NOTEAM" : bwt.getName()));

        Cuboid c = new Cuboid(location, 1, true);
        c.setMaxY(c.getMaxY() + 5);
        c.setMinY(c.getMinY() - 2);
        arena.getRegionsList().add(c);
    }

    public void upgrade() {
        switch (type) {
            case DIAMOND:
                upgradeStage++;
                if (upgradeStage == 2) {
                    delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_DELAY) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_II_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_DELAY);
                    spawnLimit = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_SPAWN_LIMIT) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_II_SPAWN_LIMIT : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_SPAWN_LIMIT);
                    //arena.upgradeDiamondsCount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_START) == null ?
                    //        "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_III_START : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_START);
                    //arena.diamondTier = 2;
                } else if (upgradeStage == 3) {
                    delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_DELAY) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_III_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_DELAY);
                    spawnLimit = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_SPAWN_LIMIT) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_III_SPAWN_LIMIT : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_SPAWN_LIMIT);
                    // arena.diamondTier = 3;
                }
                ore = new ItemStack(Material.DIAMOND);
                for (HoloGram e : armorStands.values()) {
                    e.setTierName(Language.getLang(e.iso).m(Messages.GENERATOR_HOLOGRAM_TIER).replace("{tier}", Language.getLang(e.iso)
                            .m(upgradeStage == 2 ? Messages.FORMATTING_GENERATOR_TIER2 : Messages.FORMATTING_GENERATOR_TIER3)));
                }
                break;
            case EMERALD:
                upgradeStage++;
                if (upgradeStage == 2) {
                    delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_DELAY) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_II_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_DELAY);
                    spawnLimit = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_SPAWN_LIMIT) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_II_SPAWN_LIMIT : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_SPAWN_LIMIT);
                    //arena.upgradeEmeraldsCount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_START) == null ?
                    //        "Default." + ConfigPath.GENERATOR_EMERALD_TIER_III_START : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_START);
                    //arena.emeraldTier = 2;
                } else if (upgradeStage == 3) {
                    delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_DELAY) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_III_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_DELAY);
                    spawnLimit = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_SPAWN_LIMIT) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_III_SPAWN_LIMIT : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_SPAWN_LIMIT);
                    //arena.emeraldTier = 3;
                }
                ore = new ItemStack(Material.EMERALD);
                for (HoloGram e : armorStands.values()) {
                    e.setTierName(Language.getLang(e.iso).m(Messages.GENERATOR_HOLOGRAM_TIER).replace("{tier}",
                            Language.getLang(e.iso).m(upgradeStage == 2 ? Messages.FORMATTING_GENERATOR_TIER2 : Messages.FORMATTING_GENERATOR_TIER3)));
                }
                break;
        }
        Bukkit.getPluginManager().callEvent(new GeneratorUpgradeEvent(this));
    }

    public void spawn() {
        if (lastSpawn == 0) {
            lastSpawn = delay;
            if (spawnLimit != 0) {
                int oreCount = 0;
                for (Entity e : location.getWorld().getNearbyEntities(location, 3, 3, 3)) {
                    if (e.getType() == EntityType.DROPPED_ITEM) {
                        Item i = (Item) e;
                        if (i.getItemStack().getType() == ore.getType()) {
                            oreCount++;
                        }
                        if (oreCount >= spawnLimit) return;
                    }
                }
                lastSpawn = delay;
            }
            if (bwt == null) {
                dropItem(location);
                return;
            }
            if (bwt.getMembers().size() == 1) {
                dropItem(location);
                return;
            }
            Object[] players = location.getWorld().getNearbyEntities(location, 1, 1, 1).stream().filter(entity -> entity.getType() == EntityType.PLAYER)
                    .filter(entity -> arena.isPlayer((Player) entity)).filter(entity -> arena.getTeam((Player) entity) == bwt).toArray();
            if (players.length <= 1) {
                dropItem(location);
                return;
            }
            for (Object o : players) {
                dropItem(((Player) o).getLocation());
            }
            return;
        }
        lastSpawn--;
        for (HoloGram e : armorStands.values()) {
            e.setTimerName(Language.getLang(e.iso).m(Messages.GENERATOR_HOLOGRAM_TIMER).replace("{seconds}", String.valueOf(lastSpawn)));
        }
    }

    /**
     * Drop item stack with ID
     */
    private void dropItem(Location location) {
        for (int temp = amount; temp >= 0; temp--) {
            ItemStack itemStack = new ItemStack(ore);
            if (!stack) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName("custom" + dropID++);
                itemStack.setItemMeta(itemMeta);
            }
            Item item = location.getWorld().dropItem(location, itemStack);
            item.setVelocity(new Vector(0, 0, 0));
            temp--;
        }
    }

    public void setOre(ItemStack ore) {
        Main.debug("Changing ore for generator at " + location.toString() + " from " + this.ore + " to " + ore);
        this.ore = ore;
    }

    public Arena getArena() {
        return arena;
    }

    public static ConcurrentLinkedDeque<OreGenerator> getRotation() {
        return rotation;
    }

    @SuppressWarnings("WeakerAccess")
    public class HoloGram {
        String iso;
        ArmorStand tier, timer, name;

        @Deprecated
        public HoloGram(String iso, ArmorStand tier, ArmorStand timer, ArmorStand name) {
            this.tier = tier;
            this.timer = timer;
            this.name = name;
            this.iso = iso;
            //updateForAll();
        }

        public HoloGram(String iso) {
            this.iso = iso;
            this.tier = createArmorStand(Language.getLang(iso).m(Messages.GENERATOR_HOLOGRAM_TIER)
                    .replace("{tier}", Language.getLang(iso).m(Messages.FORMATTING_GENERATOR_TIER1)), location.clone().add(0, 3, 0));
            this.timer = createArmorStand(Language.getLang(iso).m(Messages.GENERATOR_HOLOGRAM_TIMER)
                    .replace("{seconds}", String.valueOf(lastSpawn)), location.clone().add(0, 2.4, 0));
            this.name = createArmorStand(Language.getLang(iso).m(getOre().getType() == Material.DIAMOND ? Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND
                    : Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD), location.clone().add(0, 2.7, 0));

        }

        public void updateForAll() {
            for (Player p2 : timer.getWorld().getPlayers()) {
                if (Language.getPlayerLanguage(p2).getIso().equalsIgnoreCase(iso)) continue;
                nms.hideEntity(tier, p2);
                nms.hideEntity(timer, p2);
                nms.hideEntity(name, p2);
            }
        }

        public void updateForPlayer(Player p, String lang) {
            if (lang.equalsIgnoreCase(iso)) return;
            nms.hideEntity(tier, p);
            nms.hideEntity(timer, p);
            nms.hideEntity(name, p);
        }

        public void setTierName(String name) {
            tier.setCustomName(name);
            //Location loc  = timer.getLocation().clone();
            //timer.remove();
            //timer = createArmorStand(name, loc);
        }

        public void setTimerName(String name) {
            timer.setCustomName(name);
        }

        public void destroy() {
            tier.remove();
            timer.remove();
            name.remove();
        }
    }

    private static ArmorStand createArmorStand(String name, Location l) {
        ArmorStand a = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
        a.setGravity(false);
        if (name != null) {
            a.setCustomName(name);
            a.setCustomNameVisible(true);
        }
        a.setRemoveWhenFarAway(false);
        a.setVisible(false);
        a.setCanPickupItems(false);
        a.setArms(false);
        a.setBasePlate(false);
        a.setMarker(true);
        return a;
    }

    public void rotate() {
        if (up) {
            if (rotate >= 540) {
                up = false;
            }
            if (rotate > 500) {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate += 1), 0));
            } else if (rotate > 470) {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate += 2), 0));
                /*item.teleport(item.getLocation().add(0, 0.005D, 0));*/
            } else if (rotate > 450) {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate += 3), 0));
                /*item.teleport(item.getLocation().add(0, 0.001D, 0));*/
            } else {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate += 4), 0));
                /*item.teleport(item.getLocation().add(0, 0.002D, 0));*/
            }
        } else {
            if (rotate <= 0) {
                up = true;
            }
            if (rotate > 120) {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate -= 4), 0));
                /*item.teleport(item.getLocation().subtract(0, 0.002D, 0));*/
            } else if (rotate > 90) {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate -= 3), 0));
                /*item.teleport(item.getLocation().add(0, 0.001D, 0));*/
            } else if (rotate > 70) {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate -= 2), 0));
                /*item.teleport(item.getLocation().add(0, 0.005D, 0));*/
            } else {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate -= 1), 0));
            }
        }
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Location getLocation() {
        return location;
    }

    public ItemStack getOre() {
        return ore;
    }

    /**
     * Disable generator
     */
    public void disable() {
        if (getOre().getType() == Material.EMERALD || getOre().getType() == Material.DIAMOND) {
            rotation.remove(this);
            for (HoloGram a : armorStands.values()) {
                a.destroy();
            }
        }
        armorStands.clear();
    }

    /**
     * Important for spectators to hide other lang holograms
     */
    public void updateHolograms() {
        for (HoloGram h : armorStands.values()) {
            h.updateForAll();
        }
    }

    /**
     * Update holograms for a player
     */
    public void updateHolograms(Player p, String iso) {
        for (HoloGram h : armorStands.values()) {
            h.updateForPlayer(p, iso);
        }
    }

    /**
     * Enable generator rotation.
     * Make sure it has a helmet set.
     * DIAMOND and EMERALD generator types will get
     * the rotation activated when the arena starts.
     */
    public void enableRotation() {
        //loadDefaults(false);
        //if (getType() == GeneratorType.EMERALD || getType() == GeneratorType.DIAMOND) {
        rotation.add(this);
        for (Language lan : Language.getLanguages()) {
            HoloGram h = armorStands.get(lan.getIso());
            if (h == null) {
                armorStands.put(lan.getIso(), new HoloGram(lan.getIso()));
            }
        }
        for (HoloGram hg : armorStands.values()) {
            hg.updateForAll();
        }

        item = createArmorStand(null, location.clone().add(0, 0.5, 0));
        item.setHelmet(new ItemStack(type == GeneratorType.DIAMOND ? Material.DIAMOND_BLOCK : Material.EMERALD_BLOCK));
        //}
    }

    /**
     * Set spawn limit
     *
     * @since API 10
     */
    public void setSpawnLimit(int value) {
        this.spawnLimit = value;
    }


    private void loadDefaults() {
        switch (type) {
            case GOLD:
                delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_GOLD_DELAY) == null ?
                        "Default." + ConfigPath.GENERATOR_GOLD_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_GOLD_DELAY);
                ore = new ItemStack(Material.GOLD_INGOT);
                amount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_GOLD_AMOUNT) == null ?
                        "Default." + ConfigPath.GENERATOR_GOLD_AMOUNT : arena.getGroup() + "." + ConfigPath.GENERATOR_GOLD_AMOUNT);
                spawnLimit = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_GOLD_SPAWN_LIMIT) == null ?
                        "Default." + ConfigPath.GENERATOR_GOLD_SPAWN_LIMIT : arena.getGroup() + "." + ConfigPath.GENERATOR_GOLD_SPAWN_LIMIT);
                break;
            case IRON:
                delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_IRON_DELAY) == null ?
                        "Default." + ConfigPath.GENERATOR_IRON_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_IRON_DELAY);
                amount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_IRON_AMOUNT) == null ?
                        "Default." + ConfigPath.GENERATOR_IRON_AMOUNT : arena.getGroup() + "." + ConfigPath.GENERATOR_IRON_AMOUNT);
                ore = new ItemStack(Material.IRON_INGOT);
                spawnLimit = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_IRON_SPAWN_LIMIT) == null ?
                        "Default." + ConfigPath.GENERATOR_IRON_SPAWN_LIMIT : arena.getGroup() + "." + ConfigPath.GENERATOR_IRON_SPAWN_LIMIT);
                break;
            case DIAMOND:
                delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_I_DELAY) == null ?
                        "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_I_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_I_DELAY);
                spawnLimit = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_I_SPAWN_LIMIT) == null ?
                        "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_I_SPAWN_LIMIT : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_I_SPAWN_LIMIT);
                ore = new ItemStack(Material.DIAMOND);
                break;
            case EMERALD:
                delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_I_DELAY) == null ?
                        "Default." + ConfigPath.GENERATOR_EMERALD_TIER_I_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_I_DELAY);
                spawnLimit = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_I_SPAWN_LIMIT) == null ?
                        "Default." + ConfigPath.GENERATOR_EMERALD_TIER_I_SPAWN_LIMIT : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_I_SPAWN_LIMIT);
                ore = new ItemStack(Material.EMERALD);
                break;
        }
        lastSpawn = delay;
    }

    public BedWarsTeam getBwt() {
        return bwt;
    }

    /**
     * Get generator hologram holder (armor stand) containing the rotating item.
     */
    public ArmorStand getHologramHolder() {
        return item;
    }

    /**
     * Get generator type.
     */
    public GeneratorType getType() {
        return type;
    }

    /**
     * Get the amount of items that are dropped once.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Get spawn rate delay.
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Get seconds before next item spawn.
     */
    public int getNextSpawn() {
        return lastSpawn;
    }

    /**
     * Get generator holograms.
     */
    public HashMap<String, HoloGram> getHolograms() {
        return armorStands;
    }

    /**
     * Get the spawn limit of the generators.
     * If there is this amount of items dropped near the spawner
     * it will stop spawning new items.
     */
    public int getSpawnLimit() {
        return spawnLimit;
    }

    /**
     * Set the remaining time till the next item spawn.
     */
    public void setNextSpawn(int lastSpawn) {
        this.lastSpawn = lastSpawn;
    }

    /**
     * Should the dropped items be stacked?
     */
    public void setStack(boolean stack) {
        this.stack = stack;
    }

    /**
     * Check if the dropped items can be stacked.
     */
    public boolean isStack() {
        return stack;
    }

    /**
     * Set generator type.
     * This may break things.
     */
    public void setType(GeneratorType type) {
        this.type = type;
    }

    protected void destroyData(){
        rotation.remove(this);
        location = null;
        arena = null;
        ore = null;
        bwt = null;
        boolean up = true;
        armorStands = null;
        item = null;
    }
}
