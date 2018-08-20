package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.api.GeneratorType;
import com.andrei1058.bedwars.api.GeneratorUpgradeEvent;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Contract;

import java.util.*;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class OreGenerator {

    private Location location;
    private int delay = 1, upgradeStage = 1, lastSpawn, max = 0, amount = 1;
    private Arena arena;
    private ItemStack ore;
    private GeneratorType type;
    private int rotate = 0, dropID = 0;
    boolean up = true;

    /**
     * Generator holograms per language <iso, holo></iso,>
     */
    private HashMap<String, HoloGram> armorStands = new HashMap<>();

    private ArmorStand item;
    public boolean stack = getGeneratorsCfg().getBoolean(ConfigPath.GENERATOR_STACK_ITEMS);

    private static List<OreGenerator> generators = new ArrayList<>();
    private static List<OreGenerator> rotation = new ArrayList<>();

    public OreGenerator(Location location, Arena arena, GeneratorType type) {
        location = location.clone().add(0, 1.3, 0);
        this.location = location;
        this.arena = arena;
        switch (type) {
            case GOLD:
                delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_GOLD_DELAY) == null ?
                        "Default." + ConfigPath.GENERATOR_GOLD_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_GOLD_DELAY);
                ore = new ItemStack(Material.GOLD_INGOT);
                amount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_GOLD_AMOUNT) == null ?
                        "Default." + ConfigPath.GENERATOR_GOLD_AMOUNT : arena.getGroup() + "." + ConfigPath.GENERATOR_GOLD_AMOUNT);
                break;
            case IRON:
                delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_IRON_DELAY) == null ?
                        "Default." + ConfigPath.GENERATOR_IRON_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_IRON_DELAY);
                amount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_IRON_AMOUNT) == null ?
                        "Default." + ConfigPath.GENERATOR_IRON_AMOUNT : arena.getGroup() + "." + ConfigPath.GENERATOR_IRON_AMOUNT);
                ore = new ItemStack(Material.IRON_INGOT);
                break;
            case DIAMOND:
                delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_I_DELAY) == null ?
                        "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_I_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_I_DELAY);
                max = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_I_MAX) == null ?
                        "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_I_MAX : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_I_MAX);
                arena.upgradeDiamondsCount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_START) == null ?
                        "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_II_START : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_START);
                ore = new ItemStack(Material.DIAMOND);

                for (Language lan : Language.getLanguages()) {
                    String iso = lan.getIso();
                    HoloGram h = armorStands.get(iso);
                    if (h == null) {
                        armorStands.put(iso, new HoloGram(iso, createArmorStand(lan.m(Messages.GENERATOR_HOLOGRAM_TIER).replace("{tier}", lan.m(Messages.FORMATTING_GENERATOR_TIER1)), location.clone().add(0, 3, 0)),
                                createArmorStand(lan.m(Messages.GENERATOR_HOLOGRAM_TIMER).replace("{seconds}", String.valueOf(lastSpawn)), location.clone().add(0, 2.4, 0)),
                                createArmorStand(lan.m(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND), location.clone().add(0, 2.7, 0)), arena.getWorld()));
                    }
                }
                for (HoloGram hg : armorStands.values()) {
                    hg.updateForAll();
                }

                item = createArmorStand(null, location.clone().add(0, 0.5, 0));
                item.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));
                rotation.add(this);
                break;
            case EMERALD:
                delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_I_DELAY) == null ?
                        "Default." + ConfigPath.GENERATOR_EMERALD_TIER_I_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_I_DELAY);
                max = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_I_MAX) == null ?
                        "Default." + ConfigPath.GENERATOR_EMERALD_TIER_I_MAX : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_I_MAX);
                arena.upgradeEmeraldsCount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_START) == null ?
                        "Default." + ConfigPath.GENERATOR_EMERALD_TIER_II_START : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_START);
                ore = new ItemStack(Material.EMERALD);

                for (Language lan : Language.getLanguages()) {
                    String iso = lang.getIso();
                    HoloGram h = armorStands.get(iso);
                    if (h == null) {
                        armorStands.put(iso, new HoloGram(iso, createArmorStand(lan.m(Messages.GENERATOR_HOLOGRAM_TIER).replace("{tier}", lan.m(Messages.FORMATTING_GENERATOR_TIER1)), location.clone().add(0, 3, 0)),
                                createArmorStand(lan.m(Messages.GENERATOR_HOLOGRAM_TIMER).replace("{seconds}", String.valueOf(lastSpawn)), location.clone().add(0, 2.4, 0)),
                                createArmorStand(lan.m(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD), location.clone().add(0, 2.7, 0)), arena.getWorld()));
                    }
                }
                for (HoloGram hg : armorStands.values()) {
                    hg.updateForAll();
                }

                item = createArmorStand(null, location.clone().add(0, 0.5, 0));
                item.setHelmet(new ItemStack(Material.EMERALD_BLOCK));
                rotation.add(this);
                break;
        }
        this.type = type;
        lastSpawn = delay;
        getGenerators().add(this);
    }

    public void upgrade() {
        switch (type) {
            case DIAMOND:
                upgradeStage++;
                if (arena.diamondTier != upgradeStage) {
                    for (Player p : arena.getPlayers()) {
                        p.sendMessage(getMsg(p, Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT).replace("{generatorType}",
                                getMsg(p, Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND)).replace("{tier}", getMsg(p, (upgradeStage == 2 ? Messages.FORMATTING_GENERATOR_TIER2 : Messages.FORMATTING_GENERATOR_TIER3))));
                    }
                    for (Player p : arena.getSpectators()) {
                        p.sendMessage(getMsg(p, Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT).replace("{generatorType}",
                                getMsg(p, Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND)).replace("{tier}", getMsg(p, (upgradeStage == 2 ? Messages.FORMATTING_GENERATOR_TIER2 : Messages.FORMATTING_GENERATOR_TIER3))));
                    }
                }
                if (upgradeStage == 2) {
                    delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_DELAY) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_II_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_DELAY);
                    max = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_MAX) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_II_MAX : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_MAX);
                    arena.upgradeDiamondsCount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_START) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_III_START : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_START);
                    arena.diamondTier = 2;
                } else if (upgradeStage == 3) {
                    delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_DELAY) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_III_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_DELAY);
                    max = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_II_MAX) == null ?
                            "Default." + ConfigPath.GENERATOR_DIAMOND_TIER_III_MAX : arena.getGroup() + "." + ConfigPath.GENERATOR_DIAMOND_TIER_III_MAX);
                    arena.diamondTier = 3;
                }
                ore = new ItemStack(Material.DIAMOND);
                for (HoloGram e : armorStands.values()) {
                    e.setTierName(Language.getLang(e.iso).m(Messages.GENERATOR_HOLOGRAM_TIER).replace("{tier}", Language.getLang(e.iso)
                            .m(upgradeStage == 2 ? Messages.FORMATTING_GENERATOR_TIER2 : Messages.FORMATTING_GENERATOR_TIER3)));
                }
                break;
            case EMERALD:
                upgradeStage++;
                if (arena.emeraldTier != upgradeStage) {
                    for (Player p : arena.getPlayers()) {
                        p.sendMessage(getMsg(p, Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT).replace("{generatorType}",
                                getMsg(p, Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD)).replace("{tier}", getMsg(p, (upgradeStage == 2 ? Messages.FORMATTING_GENERATOR_TIER2 : Messages.FORMATTING_GENERATOR_TIER3))));
                    }
                    for (Player p : arena.getSpectators()) {
                        p.sendMessage(getMsg(p, Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT).replace("{generatorType}",
                                getMsg(p, Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD)).replace("{tier}", getMsg(p, (upgradeStage == 2 ? Messages.FORMATTING_GENERATOR_TIER2 : Messages.FORMATTING_GENERATOR_TIER3))));
                    }
                }
                if (upgradeStage == 2) {
                    delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_DELAY) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_II_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_DELAY);
                    max = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_MAX) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_II_MAX : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_MAX);
                    arena.upgradeEmeraldsCount = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_START) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_III_START : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_START);
                    arena.emeraldTier = 2;
                } else if (upgradeStage == 3) {
                    delay = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_DELAY) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_III_DELAY : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_DELAY);
                    max = getGeneratorsCfg().getInt(getGeneratorsCfg().getYml().get(arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_II_MAX) == null ?
                            "Default." + ConfigPath.GENERATOR_EMERALD_TIER_III_MAX : arena.getGroup() + "." + ConfigPath.GENERATOR_EMERALD_TIER_III_MAX);
                    arena.emeraldTier = 3;
                }
                ore = new ItemStack(Material.EMERALD);
                for (HoloGram e : armorStands.values()) {
                    e.setTierName(Language.getLang(e.iso).m(Messages.GENERATOR_HOLOGRAM_TIER).replace("{tier}",
                            Language.getLang(e.iso).m(upgradeStage == 2 ? Messages.FORMATTING_GENERATOR_TIER2 : Messages.FORMATTING_GENERATOR_TIER3)));
                }
                break;
        }
        Bukkit.getPluginManager().callEvent(new GeneratorUpgradeEvent(type, location));
    }

    public void spawn() {
        if (lastSpawn == 0) {
            lastSpawn = delay;
            if (max != 0) {
                int oreCount = 0;
                for (Entity e : location.getWorld().getNearbyEntities(location, 3, 3, 3)) {
                    if (e.getType() == EntityType.DROPPED_ITEM) {
                        Item i = (Item) e;
                        if (i.getItemStack().getType() == ore.getType()) {
                            oreCount++;
                        }
                        if (oreCount >= max) return;
                    }
                }
                lastSpawn = delay;
            }
            /*if (ore.getType() == Material.IRON_INGOT || ore.getType() == Material.GOLD_INGOT){
                if(arena.getMaxInTeam() > 2){
                    for (int temp = amount; temp >= 0; temp--) {
                        ItemStack itemStack = new ItemStack(ore);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName("custom"+dropID++);
                        itemStack.setItemMeta(itemMeta);
                        location.getWorld().dropItemNaturally(location.clone().add(0, 1, 0), itemStack);
                        //location.getWorld().dropItem(location.clone().add(0.7, 0, 0), ore);
                        //location.getWorld().dropItem(location.clone().add(0, 0, 0.7), ore);
                        temp--;
                    }
                }  else {
                    for (int temp = amount; temp >= 0; temp--) {
                        ItemStack itemStack = new ItemStack(ore);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName("custom"+dropID++);
                        itemStack.setItemMeta(itemMeta);
                        location.getWorld().dropItem(location.clone().add(0, 1, 0), itemStack);
                        temp--;
                    }
                }
            } else {
                for (int temp = amount; temp >= 0; temp--) {
                    ItemStack itemStack = new ItemStack(ore);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName("custom"+dropID++);
                    itemStack.setItemMeta(itemMeta);
                    location.getWorld().dropItem(location.clone().add(0, 1, 0), itemStack);
                    temp--;
                }
            }*/
            for (int temp = amount; temp >= 0; temp--) {
                ItemStack itemStack = new ItemStack(ore);
                if (!stack) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName("custom" + dropID++);
                    itemStack.setItemMeta(itemMeta);
                }
                Item item = location.getWorld().dropItem(new Location(location.getWorld(), location.getBlockX()+0.5, location.getBlockY(), location.getBlockZ()+0.5), itemStack);
                item.setVelocity(new Vector(0, 0, 0));
                temp--;
            }
            return;
        }
        lastSpawn--;
        for (HoloGram e : armorStands.values()) {
            e.setTimerName(Language.getLang(e.iso).m(Messages.GENERATOR_HOLOGRAM_TIMER).replace("{seconds}", String.valueOf(lastSpawn)));
        }
    }

    public void setOre(ItemStack ore) {
        this.ore = ore;
    }

    public Arena getArena() {
        return arena;
    }

    @Contract(pure = true)
    public static List<OreGenerator> getGenerators() {
        return generators;
    }

    @Contract(pure = true)
    public static List<OreGenerator> getRotation() {
        return rotation;
    }

    public class HoloGram {
        String iso;
        ArmorStand tier, timer, name;
        World w;

        public HoloGram(String iso, ArmorStand tier, ArmorStand timer, ArmorStand name, World w) {
            this.tier = tier;
            this.timer = timer;
            this.name = name;
            this.iso = iso;
            this.w = w;
            //updateForAll();
        }

        public void updateForAll() {
            for (Player p2 : w.getPlayers()) {
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
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate += 3), 0));
            } else if (rotate > 470) {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate += 4), 0));
                /*item.teleport(item.getLocation().add(0, 0.005D, 0));*/
            } else if (rotate > 450) {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate += 7), 0));
                /*item.teleport(item.getLocation().add(0, 0.001D, 0));*/
            } else {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate += 8), 0));
                /*item.teleport(item.getLocation().add(0, 0.002D, 0));*/
            }
        } else {
            if (rotate <= 0) {
                up = true;
            }
            if (rotate > 120) {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate -= 8), 0));
                /*item.teleport(item.getLocation().subtract(0, 0.002D, 0));*/
            } else if (rotate > 90) {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate -= 7), 0));
                /*item.teleport(item.getLocation().add(0, 0.001D, 0));*/
            } else if (rotate > 70) {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate -= 4), 0));
                /*item.teleport(item.getLocation().add(0, 0.005D, 0));*/
            } else {
                item.setHeadPose(new EulerAngle(0, Math.toRadians(rotate -= 3), 0));
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

    public static void removeIfArena(Arena a) {
        for (int x = getGenerators().size() - 1; x >= 0; x--) {
            if (getGenerators().get(x).getArena() == a) {
                OreGenerator.getGenerators().remove(x);
            }
        }
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
}
