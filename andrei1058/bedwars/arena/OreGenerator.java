package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.api.GeneratorType;
import com.andrei1058.bedwars.api.GeneratorUpgradeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.Contract;

import java.util.*;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class OreGenerator {

    private Location location;
    private int delay = 1, upgradeStage = 1, lastSpawn = 1, max = 0, amount = 1;
    private Arena arena;
    private String upgradeType;
    private ItemStack ore;
    private GeneratorType type;
    private int rotate = 0, dropID = 0;
    boolean up = true;
    private List<HoloGram> armorstands = new ArrayList<>();
    private ArmorStand item;
    public static boolean showDiamoundSb = true, stack = upgrades.getBoolean("settings.stackItems");
    private static boolean emeraldUpgradeAnnouncerd = false, diamondUpgradeAnnounced = false;

    private static List<OreGenerator> generators = new ArrayList<>();
    private static List<OreGenerator> rotation = new ArrayList<>();

    public OreGenerator(Location location, Arena arena, GeneratorType type) {
        this.location = location.clone().add(0, 0.5, 0);
        this.arena = arena;
        if (arena.getMaxInTeam() > 2) {
            upgradeType = "bigTeam";
        } else {
            upgradeType = "smallTeam";
        }
        switch (type) {
            case GOLD:
                delay = upgrades.getInt(upgrades.getYml().get("settings.startValues."+arena.getGroup().toLowerCase()+".goldGeneratorDelay") == null ? "settings.startValues.default.goldGeneratorDelay" :
                "settings.startValues."+arena.getGroup().toLowerCase()+".goldGeneratorDelay");
                ore = new ItemStack(Material.GOLD_INGOT);
                amount = upgrades.getInt(upgrades.getYml().get("settings.startValues."+arena.getGroup().toLowerCase()+".goldGeneratorAmount") == null ? "settings.startValues.default.goldGeneratorAmount" :
                        "settings.startValues."+arena.getGroup().toLowerCase()+".goldGeneratorAmount");
                break;
            case IRON:
                delay = upgrades.getInt(upgrades.getYml().get("settings.startValues."+arena.getGroup().toLowerCase()+".ironGeneratorDelay") == null ? "settings.startValues.default.ironGeneratorDelay" :
                        "settings.startValues."+arena.getGroup().toLowerCase()+".ironGeneratorDelay");
                amount = upgrades.getInt(upgrades.getYml().get("settings.startValues."+arena.getGroup().toLowerCase()+".ironGeneratorAmount") == null ? "settings.startValues.default.ironGeneratorAmount" :
                        "settings.startValues."+arena.getGroup().toLowerCase()+".ironGeneratorAmount");
                ore = new ItemStack(Material.IRON_INGOT);
                break;
            case DIAMOND:
                delay = config.getInt("generators.diamond.tier1.delay");
                max = config.getInt("generators.diamond.tier1.max");
                arena.upgradeDiamondsCount = config.getInt("generators.diamond.tier2.start");
                ore = new ItemStack(Material.DIAMOND);
                for (Player p : arena.getPlayers()) {
                    armorstands.add(new HoloGram(p, createArmorStand(getMsg(p, lang.generatorTier).replace("{tier}", getMsg(p, lang.tier1Format)), location.clone().add(0, 3, 0)),
                            createArmorStand(getMsg(p, lang.generatorTimer).replace("{seconds}",
                                    String.valueOf(lastSpawn)), location.clone().add(0, 2.4, 0)),
                            createArmorStand(getMsg(p, lang.generatorDiamond), location.clone().add(0, 2.7, 0))));
                }
                item = createArmorStand(null, location.clone().add(0, 1.5, 0));
                item.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));
                rotation.add(this);
                break;
            case EMERALD:
                delay = config.getInt("generators.emerald.tier1.delay");
                max = config.getInt("generators.emerald.tier1.max");
                arena.upgradeEmeraldsCount = config.getInt("generators.emerald.tier2.start");
                ore = new ItemStack(Material.EMERALD);
                for (Player p : arena.getPlayers()) {
                    armorstands.add(new HoloGram(p, createArmorStand(getMsg(p, lang.generatorTier).replace("{tier}", getMsg(p, lang.tier1Format)), location.clone().add(0, 3, 0)),
                            createArmorStand(getMsg(p, lang.generatorTimer).replace("{seconds}",
                                    String.valueOf(lastSpawn)), location.clone().add(0, 2.4, 0)),
                            createArmorStand(getMsg(p, lang.generatorEmerald), location.clone().add(0, 2.7, 0))));
                }
                item = createArmorStand(null, location.clone().add(0, 1.7, 0));
                item.setHelmet(new ItemStack(Material.EMERALD_BLOCK));
                rotation.add(this);
                break;
        }
        this.type = type;
        lastSpawn = delay;
        getGenerators().add(this);
    }

    public void upgrade() {
        upgradeStage++;
        switch (type) {
            case GOLD:
                delay = config.getInt("teamUpgrades." + upgradeType + ".forge." + (upgradeStage - 1) + ".goldDelay");
                amount = config.getInt("teamUpgrades." + upgradeType + ".forge." + (upgradeStage - 1) + ".goldAmount");
                ore = new ItemStack(Material.GOLD_INGOT);
                break;
            case IRON:
                delay = config.getInt("teamUpgrades." + upgradeType + ".forge." + (upgradeStage - 1) + ".ironDelay");
                amount = config.getInt("teamUpgrades." + upgradeType + ".forge." + (upgradeStage - 1) + ".ironAmount");
                ore = new ItemStack(Material.IRON_INGOT);
                break;
            case DIAMOND:
               try {
                   delay = config.getInt("generators.diamond.tier" + upgradeStage + ".delay");
                   max = config.getInt("generators.diamond.tier" + upgradeStage + ".max");
                   ore = new ItemStack(Material.DIAMOND);
                   if (upgradeStage == 2){
                       arena.upgradeDiamondsCount = config.getInt("generators.diamond.tier3.start");
                   }
                   for (HoloGram e : armorstands) {
                       e.setTierName(getMsg(e.p, lang.generatorTier).replace("{tier}", getMsg(e.p, upgradeStage == 2 ? lang.tier2Format : lang.tier3Format)));
                   }
                   if (upgradeStage == 3){
                       showDiamoundSb = false;
                   }
                   if (!diamondUpgradeAnnounced){
                       for (Player p : arena.getPlayers()){
                           p.sendMessage(getMsg(p, lang.generatorUpgradeMsg).replace("{generatorType}", getMsg(p, lang.generatorDiamond)).replace("{tier}", getMsg(p, (upgradeStage == 2 ? lang.tier2Format : lang.tier3Format))));
                       }
                       for (Player p : arena.getSpectators()){
                           p.sendMessage(getMsg(p, lang.generatorUpgradeMsg).replace("{generatorType}", getMsg(p, lang.generatorDiamond)).replace("{tier}", getMsg(p, (upgradeStage == 2 ? lang.tier2Format : lang.tier3Format))));
                       }
                       diamondUpgradeAnnounced = true;
                   }
                   Bukkit.getScheduler().runTaskLater(plugin, ()-> diamondUpgradeAnnounced = false, 20L);
               } catch (Exception ex){
                   ex.printStackTrace();
               }
                break;
            case EMERALD:
                delay = config.getInt("generators.emerald.tier" + upgradeStage + ".delay");
                max = config.getInt("generators.emerald.tier" + upgradeStage + ".max");
                if (upgradeStage == 2){
                    arena.upgradeEmeraldsCount = config.getInt("generators.emerald.tier3.start");
                }
                ore = new ItemStack(Material.EMERALD);
                for (HoloGram e : armorstands) {
                    e.setTierName(getMsg(e.p, lang.generatorTier).replace("{tier}", getMsg(e.p, upgradeStage == 2 ? lang.tier2Format : lang.tier3Format)));
                }
                if (!emeraldUpgradeAnnouncerd){
                    for (Player p : arena.getPlayers()){
                        p.sendMessage(getMsg(p, lang.generatorUpgradeMsg).replace("{generatorType}", getMsg(p, lang.generatorEmerald)).replace("{tier}", getMsg(p, (upgradeStage == 2 ? lang.tier2Format : lang.tier3Format))));
                    }
                    for (Player p : arena.getSpectators()){
                        p.sendMessage(getMsg(p, lang.generatorUpgradeMsg).replace("{generatorType}", getMsg(p, lang.generatorEmerald)).replace("{tier}", getMsg(p, (upgradeStage == 2 ? lang.tier2Format : lang.tier3Format))));
                    }
                    emeraldUpgradeAnnouncerd = true;
                    Bukkit.getScheduler().runTaskLater(plugin, ()-> emeraldUpgradeAnnouncerd = false, 20L);
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
                        if (i.getItemStack().getType() == ore.getType()){
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
                location.getWorld().dropItem(location.clone(), itemStack);
                temp--;
            }
            return;
        }
        lastSpawn--;
        for (HoloGram e : armorstands) {
            e.setTimerName(getMsg(e.p, lang.generatorTimer).replace("{seconds}", String.valueOf(lastSpawn)));
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
        Player p;
        ArmorStand tier, timer, name;

        public HoloGram(Player p, ArmorStand tier, ArmorStand timer, ArmorStand name) {
            this.tier = tier;
            this.timer = timer;
            this.name = name;
            this.p = p;
            for (Player p2 : p.getWorld().getPlayers()) {
                if (p2 == p) continue;
                nms.hideEntity(tier, p2);
                nms.hideEntity(timer, p2);
            }
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
        a.setCollidable(false);
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
            } else if (rotate > 450){
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
            } else if (rotate > 90){
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
        for (int x = getGenerators().size()-1; x >= 0; x--){
            if (getGenerators().get(x).getArena() == a) {
                OreGenerator.getGenerators().remove(x);
            }
        }
    }
}
