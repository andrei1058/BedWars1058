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

package com.andrei1058.bedwars.arena.generator;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.generator.GeneratorType;
import com.andrei1058.bedwars.api.arena.generator.IGenHolo;
import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.gameplay.GeneratorUpgradeEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.region.Cuboid;
import com.andrei1058.handyorbs.core.version.OrbActivity;
import com.andrei1058.handyorbs.core.version.OrbEntity;
import com.andrei1058.handyorbs.core.version.OrbEntityFactory;
import com.andrei1058.hologramapi.Hologram;
import com.andrei1058.hologramapi.HologramPage;
import com.andrei1058.hologramapi.content.LineTextContent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

import static com.andrei1058.bedwars.BedWars.*;

@SuppressWarnings("WeakerAccess")
public class OreGenerator implements IGenerator {

    private Location location;
    private int delay = 1, upgradeStage = 1, lastSpawn, spawnLimit = 0, amount = 1;
    private IArena arena;
    private ItemStack ore;
    private GeneratorType type;
    private int rotate = 0, dropID = 0;
    private ITeam bwt;

    public boolean stack = getGeneratorsCfg().getBoolean(ConfigPath.GENERATOR_STACK_ITEMS);

    private static final ConcurrentLinkedDeque<OreGenerator> rotation = new ConcurrentLinkedDeque<>();

    private OrbEntity orbEntity;
    private Hologram hologram;

    public OreGenerator(Location location, IArena arena, GeneratorType type, ITeam bwt) {
        if (type == GeneratorType.EMERALD || type == GeneratorType.DIAMOND) {
            this.location = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY() + 1.35, location.getBlockZ() + 0.5);
        } else {
            this.location = location.add(0, 1.3, 0);
        }
        this.arena = arena;
        this.bwt = bwt;
        this.type = type;
        loadDefaults();
        BedWars.debug("Initializing new generator at: " + location + " - " + type + " - " + (bwt == null ? "NOTEAM" : bwt.getName()));

        Cuboid c = new Cuboid(location, getArena().getConfig().getInt(ConfigPath.ARENA_GENERATOR_PROTECTION), true);
        c.setMaxY(c.getMaxY() + 5);
        c.setMinY(c.getMinY() - 2);
        arena.getRegionsList().add(c);

        if (type == GeneratorType.EMERALD || type == GeneratorType.DIAMOND) {
            this.orbEntity = OrbEntityFactory.getInstance().spawnOrbEntity(
                    this.location,
                    new ItemStack(type == GeneratorType.DIAMOND ? Material.DIAMOND_BLOCK : Material.EMERALD_BLOCK)
            );
            orbEntity.setCustomNameVisible(false);

            // disable collisions
            ((ArmorStand)orbEntity.getBukkitEntity()).setMarker(true);

            OrbActivity floating = orbEntity.getOrbActivity();
            ((ArmorStand) orbEntity.getBukkitEntity()).setSmall(false);
            orbEntity.setOrbActivity(() -> {
                floating.doTick();
                rotate();
            });
        }
    }

    @Override
    public void upgrade() {
        switch (type) {
            case DIAMOND:
                upgradeStage++;
                if (upgradeStage == 2) {
                    delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_DELAY) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_II_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_DELAY);
                    amount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_AMOUNT) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_II_AMOUNT : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_AMOUNT);
                    spawnLimit = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_SPAWN_LIMIT) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_II_SPAWN_LIMIT : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_SPAWN_LIMIT);
                } else if (upgradeStage == 3) {
                    delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_DELAY) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_III_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_DELAY);
                    amount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_AMOUNT) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_III_AMOUNT : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_AMOUNT);
                    spawnLimit = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_SPAWN_LIMIT) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_III_SPAWN_LIMIT : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_SPAWN_LIMIT);
                }
                ore = new ItemStack(Material.DIAMOND);
//                for (IGenHolo e : armorStands.values()) {
//                    e.setTierName(Language.getLang(e.getIso()).m(Messages.GENERATOR_HOLOGRAM_TIER).replace("{tier}", Language.getLang(e.getIso())
//                            .m(upgradeStage == 2 ? Messages.FORMATTING_GENERATOR_TIER2 : Messages.FORMATTING_GENERATOR_TIER3)));
//                }
                break;
            case EMERALD:
                upgradeStage++;
                if (upgradeStage == 2) {
                    delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_DELAY) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_II_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_DELAY);
                    amount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_AMOUNT) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_II_AMOUNT : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_AMOUNT);
                    spawnLimit = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_SPAWN_LIMIT) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_II_SPAWN_LIMIT : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_SPAWN_LIMIT);
                } else if (upgradeStage == 3) {
                    delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_DELAY) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_III_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_DELAY);
                    amount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_AMOUNT) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_III_AMOUNT : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_AMOUNT);
                    spawnLimit = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_SPAWN_LIMIT) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_III_SPAWN_LIMIT : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_SPAWN_LIMIT);
                }
                ore = new ItemStack(Material.EMERALD);
//                for (IGenHolo e : armorStands.values()) {
//                    e.setTierName(Language.getLang(e.getIso()).m(Messages.GENERATOR_HOLOGRAM_TIER).replace("{tier}",
//                            Language.getLang(e.getIso()).m(upgradeStage == 2 ? Messages.FORMATTING_GENERATOR_TIER2 : Messages.FORMATTING_GENERATOR_TIER3)));
//                }
                break;
        }
        Bukkit.getPluginManager().callEvent(new GeneratorUpgradeEvent(this));
    }

    @Override
    public void spawn() {
        if (arena.getStatus() != GameState.playing) {
            return;
        }

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
            if (plugin.getConfig().getBoolean(ConfigPath.GENERAL_CONFIGURATION_ENABLE_GEN_SPLIT)) {
                Object[] players = location.getWorld().getNearbyEntities(location, 1, 1, 1).stream().filter(entity -> entity.getType() == EntityType.PLAYER)
                        .filter(entity -> arena.isPlayer((Player) entity)).toArray();
                if (players.length <= 1) {
                    dropItem(location);
                    return;
                }
                for (Object o : players) {
                    Player player = (Player) o;
                    ItemStack item = ore.clone();
                    item.setAmount(amount);
                    player.playSound(player.getLocation(), Sound.valueOf(BedWars.getForCurrentVersion("ITEM_PICKUP", "ENTITY_ITEM_PICKUP", "ENTITY_ITEM_PICKUP")), 0.6f, 1.3f);
                    Collection<ItemStack> excess = player.getInventory().addItem(item).values();
                    for (ItemStack value : excess) {
                        dropItem(player.getLocation(), value.getAmount());
                    }
                }
                return;
            } else {
                dropItem(location);
                return;
            }
        }
        lastSpawn--;
//        for (IGenHolo e : armorStands.values()) {
//            e.setTimerName(Language.getLang(e.getIso()).m(Messages.GENERATOR_HOLOGRAM_TIMER).replace("{seconds}", String.valueOf(lastSpawn)));
//        }
    }

    private void dropItem(Location location, int amount) {
        for (int temp = amount; temp > 0; temp--) {
            ItemStack itemStack = new ItemStack(ore);
            if (!stack) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName("custom" + dropID++);
                itemStack.setItemMeta(itemMeta);
            }
            Item item = location.getWorld().dropItem(location, itemStack);
            item.setVelocity(new Vector(0, 0, 0));
        }
    }

    /**
     * Drop item stack with ID
     */
    @Override
    public void dropItem(Location location) {
        dropItem(location, amount);
    }

    @Override
    public void setOre(ItemStack ore) {
        BedWars.debug("Changing ore for generator at " + location.toString() + " from " + this.ore + " to " + ore);
        this.ore = ore;
    }

    /**
     * Get the arena assigned to this generator.
     */
    public IArena getArena() {
        return arena;
    }

    public static ConcurrentLinkedDeque<OreGenerator> getRotation() {
        return rotation;
    }

    @Deprecated(forRemoval = true)
    @Override
    public HashMap<String, IGenHolo> getLanguageHolograms() {
        return new HashMap<>();
    }

    @Deprecated(forRemoval = true)
    @SuppressWarnings("WeakerAccess")
    public class HoloGram implements IGenHolo {
        String iso;
        ArmorStand tier, timer, name;

        public HoloGram(String iso) {
            this.iso = iso;
            this.tier = createArmorStand(Language.getLang(iso).m(Messages.GENERATOR_HOLOGRAM_TIER)
                    .replace("{tier}", Language.getLang(iso).m(Messages.FORMATTING_GENERATOR_TIER1)), location.clone().add(0, 3, 0));
            this.timer = createArmorStand(Language.getLang(iso).m(Messages.GENERATOR_HOLOGRAM_TIMER)
                    .replace("{seconds}", String.valueOf(lastSpawn)), location.clone().add(0, 2.4, 0));
            this.name = createArmorStand(Language.getLang(iso).m(getOre().getType() == Material.DIAMOND ? Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND
                    : Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD), location.clone().add(0, 2.7, 0));

        }

        @Override
        public void updateForAll() {
            for (Player p2 : timer.getWorld().getPlayers()) {
                if (Language.getPlayerLanguage(p2).getIso().equalsIgnoreCase(iso)) continue;
                nms.hideEntity(tier, p2);
                nms.hideEntity(timer, p2);
                nms.hideEntity(name, p2);
            }
        }

        @Override
        public void updateForPlayer(Player p, String lang) {
            if (lang.equalsIgnoreCase(iso)) return;
            nms.hideEntity(tier, p);
            nms.hideEntity(timer, p);
            nms.hideEntity(name, p);
        }

        @Override
        public void setTierName(String name) {
            if (tier.isDead()) return;
            tier.setCustomName(name);
        }

        @Override
        public String getIso() {
            return iso;
        }

        @Override
        public void setTimerName(String name) {
            if (timer.isDead()) return;
            timer.setCustomName(name);
        }

        @Override
        public void destroy() {
            tier.remove();
            timer.remove();
            name.remove();
        }
    }

    private static ArmorStand createArmorStand(String name, Location l) {
        ArmorStand a = (ArmorStand) l.getWorld().spawnEntity(l.clone().add(0, 0.35, 0), EntityType.ARMOR_STAND);
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

    @Override
    public void rotate() {
        ArmorStand item = (ArmorStand) orbEntity.getBukkitEntity();
        item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate += 5), 0));
    }

    @Override
    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public ItemStack getOre() {
        return ore;
    }

    @Override
    public void disable() {
        // todo this is deprecated
        if (getOre().getType() == Material.EMERALD || getOre().getType() == Material.DIAMOND) {
            rotation.remove(this);
        }
    }

    @Deprecated
    @Override
    public void updateHolograms(Player p, String iso) {
        // todo I think this is deprecated
//        for (IGenHolo h : armorStands.values()) {
//            h.updateForPlayer(p, iso);
//        }
    }

    @Override
    public void enableRotation() {
//        rotation.add(this);
//        for (Language lan : Language.getLanguages()) {
//            IGenHolo h = armorStands.get(lan.getIso());
//            if (h == null) {
//                armorStands.put(lan.getIso(), new HoloGram(lan.getIso()));
//            }
//        }
//        for (IGenHolo hg : armorStands.values()) {
//            hg.updateForAll();
//        }

        hologram = new Hologram(
                location.clone().add(0, 3.5, 0),
                3
        );

        HologramPage page1 = hologram.getPage(0);
        assert page1 != null;
        page1.setLineContent(0, new LineTextContent(player -> Language.getPlayerLanguage(player).m(Messages.GENERATOR_HOLOGRAM_TIER)
                .replace("{tier}", Language.getPlayerLanguage(player).m(Messages.FORMATTING_GENERATOR_TIER1))));
        page1.setLineContent(1, new LineTextContent(
                player -> Language.getPlayerLanguage(player).m(getOre().getType() == Material.DIAMOND ? Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND
                        : Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD)
        ));
        // setting second line content (a refreshable line)
        page1.setLineContent(2, new LineTextContent(
                (player) -> Language.getPlayerLanguage(player).m(Messages.GENERATOR_HOLOGRAM_TIMER)
                        .replace("{seconds}", String.valueOf(lastSpawn))
        ));

        // todo refreshed on lib side for now with entity#tick
//        BukkitTask taskToCancelLater = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
//            hologram.refreshLine(2);
//        }, 1L, 10L);
        // setting third line content
        hologram.allowCollisions(false);
    }

    @Override
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
                amount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_I_AMOUNT) == null ?
                        "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_I_AMOUNT : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_I_AMOUNT);
                spawnLimit = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_I_SPAWN_LIMIT) == null ?
                        "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_I_SPAWN_LIMIT : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_I_SPAWN_LIMIT);
                ore = new ItemStack(Material.DIAMOND);
                break;
            case EMERALD:
                delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_I_DELAY) == null ?
                        "Default." + ConfigPath.GENERATOR_EMERALD_TIER_I_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_I_DELAY);
                amount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_I_AMOUNT) == null ?
                        "Default." + ConfigPath.GENERATOR_EMERALD_TIER_I_AMOUNT : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_I_AMOUNT);
                spawnLimit = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_I_SPAWN_LIMIT) == null ?
                        "Default." + ConfigPath.GENERATOR_EMERALD_TIER_I_SPAWN_LIMIT : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_I_SPAWN_LIMIT);
                ore = new ItemStack(Material.EMERALD);
                break;
        }
        lastSpawn = delay;
    }

    @Override
    public ITeam getBwt() {
        return bwt;
    }

    @Override
    public ArmorStand getHologramHolder() {
        return (ArmorStand) orbEntity.getBukkitEntity();
//        return item;
    }

    @Override
    public GeneratorType getType() {
        return type;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public int getNextSpawn() {
        return lastSpawn;
    }

    @Override
    public int getSpawnLimit() {
        return spawnLimit;
    }

    @Override
    public void setNextSpawn(int nextSpawn) {
        this.lastSpawn = nextSpawn;
    }

    @Override
    public void setStack(boolean stack) {
        this.stack = stack;
    }

    @Override
    public boolean isStack() {
        return stack;
    }

    @Override
    public void setType(GeneratorType type) {
        this.type = type;
    }

    public void destroyData() {
        rotation.remove(this);
        location = null;
        arena = null;
        ore = null;
        bwt = null;
//        item = null;
    }
}
